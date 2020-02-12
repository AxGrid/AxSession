package com.axgrid.session.service;

import com.axgrid.session.dto.AxContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AxContextService<T extends AxContext, R> {

    @Autowired
    protected AxSessionService sessionService;

    public abstract T getContext(R o);


}
