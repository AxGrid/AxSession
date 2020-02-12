package com.axgrid.session;

import com.axgrid.session.dto.AxSession;
import com.axgrid.session.repository.AxSessionRepository;
import com.axgrid.session.service.AxSessionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
public class SessionTest {

    @Autowired
    AxSessionService sessionService;

    @Autowired
    AxSessionRepository sessionRepository;

    @Test
    public void testSessionCreation() {
        String sessionUuid = UUID.randomUUID().toString();
        String userUuid = UUID.randomUUID().toString();
        AxSession session = sessionRepository.getUserBySession(sessionUuid);
        Assert.assertNull(session);
        session = sessionService.signIn(userUuid);
        sessionUuid = session.getSession();
        session = sessionService.get(sessionUuid);
        Assert.assertNotNull(session);
        Assert.assertEquals(session.getUuid(), userUuid);
        Assert.assertTrue(session.isValid());

        AxSession session2 = sessionService.signIn(userUuid);

        session = sessionService.get(sessionUuid);
        Assert.assertNotNull(session);
        Assert.assertEquals(session.getUuid(), userUuid);
        Assert.assertFalse(session.isValid());


        session = sessionService.get(session2.getSession());
        Assert.assertNotNull(session);
        Assert.assertEquals(session.getUuid(), userUuid);
        Assert.assertTrue(session.isValid());
    }



}
