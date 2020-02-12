package com.axgrid.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
@AllArgsConstructor
public class AxSession {

    String uuid;
    String session;
    String previousSession;
    Date date;
    boolean valid = false;

    public boolean isLoggedIn() { return uuid != null && valid; }

    public boolean isNeedUpdate() {
        return true;
    }

    public static class Mapper implements RowMapper<AxSession> {

        @Override
        public AxSession mapRow(ResultSet resultSet, int i) throws SQLException {
            return new AxSession(
                    resultSet.getString("uuid"),
                    resultSet.getString("session"),
                    "",
                    resultSet.getTimestamp("date"),
                    false
            );
        }
    }
}
