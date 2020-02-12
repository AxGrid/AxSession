package com.axgrid.session.service;

import com.axgrid.session.dto.AxSession;
import com.axgrid.session.exception.AxSessionNotFoundException;
import com.axgrid.session.repository.AxSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AxSessionService {

    @Autowired
    AxSessionRepository sessionRepository;

    public AxSession get(String session) {
        AxSession res = sessionRepository.getUserBySession(session);
        if (res == null) throw new AxSessionNotFoundException(session);
        return res;
    }

    @Scheduled(fixedDelayString = "${axgrid.session.cleanTimeout:5000}")
    public void cleanOldSession() {
        sessionRepository.cleanOldSession();
    }

    public boolean valid(String sessionUuid) {
        AxSession session = get(sessionUuid);
        return session.isValid();
    }

    public AxSession signIn(String uuid) {
        return sessionRepository.create(uuid);
    }

    public void signOut(String sessionUuid) {
        AxSession session = get(sessionUuid);
        session.setUuid("");
        sessionRepository.update(session);
    }

}
