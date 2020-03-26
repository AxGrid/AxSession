package com.axgrid.session.service;

import com.axgrid.session.dto.AxContext;
import com.axgrid.session.exception.AxSessionWrongObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public abstract class AxBaseContextService<T extends AxContext, R> implements AxContextService {

    private Class<R> persistentClass;

    @Autowired
    protected AxSessionService sessionService;

    protected abstract T getContextObject(R o);

    public AxContext getContext(Object o) {
        if (o.getClass() == persistentClass)
            return getContextObject((R)o);
        throw new AxSessionWrongObject();
    }

    @SuppressWarnings("unchecked")
    public AxBaseContextService() {
        this.persistentClass = (Class<R>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }

}
