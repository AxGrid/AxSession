package com.axgrid.session.service;

import com.axgrid.session.dto.AxContext;
import com.axgrid.session.dto.AxSession;
import org.springframework.stereotype.Service;

@Service
public class DemoContextService extends AxBaseContextService<AxContext, String> {

    @Override
    protected AxContext getContextObject(String o) {
        if (o == null || o.equals("")) return null;
        AxSession session = sessionService.get(o);
        AxContext ctx = new AxContext();
        ctx.setSession(session);
        return ctx;
    }
}
