package com.axgrid.session.dto;

import lombok.Data;

@Data
public class MultiContext extends AxContext {

    int rnd = 0;

    public MultiContext(AxContext ctx) {
        setSession(ctx.getSession());

    }

}
