package com.axgrid.session;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AxSessionConfiguration.class})
public @interface EnableAxSession {
}
