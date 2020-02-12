package com.axgrid.session.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode()
public class AxContext {
    AxSession session;

    public String getUuid() { return session.getUuid(); }
    public boolean isLoggedIn() { return session.isLoggedIn(); }
    public boolean isValid() { return session.isValid(); }

}
