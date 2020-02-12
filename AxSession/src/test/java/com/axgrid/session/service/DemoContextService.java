package com.axgrid.session.service;

import com.axgrid.session.dto.AxContext;
import com.axgrid.session.dto.AxSession;
import com.axgrid.session.service.AxContextService;
import org.springframework.stereotype.Service;

@Service
public class DemoContextService extends AxContextService<AxContext, String> {


    @Override
    public AxContext getContext(String o) {
        AxSession session = sessionService.get(o);
        if (session == null) return null;
        AxContext ctx = new AxContext();
        ctx.setSession(session);
        return ctx;
    }
}
