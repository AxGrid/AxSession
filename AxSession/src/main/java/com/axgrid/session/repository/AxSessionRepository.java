package com.axgrid.session.repository;

import com.axgrid.session.AxSessionConfiguration;
import com.axgrid.session.dto.AxSession;
import com.axgrid.session.exception.AxSessionExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Repository
@Slf4j
public class AxSessionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Value("${axgrid.session.ttl:300}")
    int ttlInMinutes = 300;


    //@Cacheable(cacheNames = {AxSessionConfiguration.SESSION_CACHE}, key="#session", unless = "#result == null")
    public AxSession getUserBySession(String session) {
        try {
            AxSession userSession = jdbcTemplate.queryForObject("SELECT uuid, session, date FROM ax_session WHERE session=?", new Object[]{session}, new AxSession.Mapper());
            log.info("Session is {}", userSession);
            if (userSession == null) return null;
            AxSession lastSession = getUserByUUID(userSession.getUuid());
            userSession.setValid(userSession.getSession().equals(lastSession.getSession()));
            //TODO: Check Date
            return userSession;
        }catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public AxSession getUserByUUID(String uuid) {
        try {
            AxSession userSession = jdbcTemplate.queryForObject("SELECT uuid, session, date FROM ax_session WHERE uuid=? ORDER BY date DESC LIMIT 1", new Object[]{uuid}, new AxSession.Mapper());
            if (userSession == null) return null;
            userSession.setValid(true);
            return userSession;
        }catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    @CachePut(cacheNames = {AxSessionConfiguration.SESSION_CACHE}, key="#session.session")
    public AxSession update(AxSession session) {
        AxSession oldSession = getUserBySession(session.getSession());
        if (!session.isValid()) throw new AxSessionExpiredException(session.getSession());
        if (oldSession == null) {
            jdbcTemplate.update("INSERT INTO ax_session (uuid, session, date) VALUES (?,?,?);", session.getUuid(), session.getSession(), new Date());
        }else{
            if (oldSession.isNeedUpdate())
                jdbcTemplate.update("UPDATE ax_session SET date=? WHERE session=?", new Date(), session.getSession());
        }
        return session;
    }

    @CacheEvict(cacheNames = {AxSessionConfiguration.SESSION_CACHE}, key="#result.previousSession")
    public AxSession create(String uuid) {
        AxSession previousSession = getUserByUUID(uuid);
        AxSession session = new AxSession(uuid,  UUID.randomUUID().toString().replace("-",""), previousSession != null ? previousSession.getSession() : "", new Date(), true);
        jdbcTemplate.update("INSERT INTO ax_session (uuid, session, date) VALUES (?,?,?);", session.getUuid(), session.getSession(), session.getDate());
        return session;
    }

    public void cleanOldSession() {
        Calendar cal = Calendar.getInstance();
        // remove next line if you're always using the current time.
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -ttlInMinutes);
        Date oneHourBack = cal.getTime();
        jdbcTemplate.update("DELETE FROM ax_session WHERE date<? LIMIT 500", oneHourBack);
    }

}
