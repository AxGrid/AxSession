package com.axgrid.session.service;

import com.axgrid.session.dto.AxContext;
import com.axgrid.session.dto.MultiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class DemoMultiContextService extends AxContextService<MultiContext, String> {

    @Autowired
    DemoContextService contextService;

    final static Random rnd = new Random(new Date().getTime());

    @Override
    public MultiContext getContext(String o) {
        if (o == null) return null;
        AxContext ctx = contextService.getContext(o);
        MultiContext res = new MultiContext(ctx);
        res.setRnd(rnd.nextInt(100) + 1);
        return res;
    }
}
