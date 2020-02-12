package com.axgrid.session;


import com.axgrid.session.dto.AxSession;
import com.axgrid.session.dto.MultiContext;
import com.axgrid.session.service.AxSessionService;
import com.axgrid.session.service.DemoMultiContextService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@Import(TestApplication.class)
@ActiveProfiles("test")
@TestPropertySource(properties = "debug=true")
@Slf4j
public class ContextTest {


    @Autowired
    DemoMultiContextService service;

    @Autowired
    AxSessionService sessionService;

    String sessionUuid;
    String userUuid;

    @Before
    public void createSession() {

        userUuid = UUID.randomUUID().toString();
        AxSession session = sessionService.signIn(userUuid);
        sessionUuid = session.getSession();
        log.info("Create session {} for user {}", session.getUuid(), session.getSession());
    }

    @Test
    public void testContextCreation() {

        MultiContext ctx = service.getContext(sessionUuid);
        Assert.assertNotNull(ctx);
        Assert.assertEquals(ctx.getUuid(), userUuid);
        Assert.assertNotEquals(ctx.getRnd(), 0);

    }


}
