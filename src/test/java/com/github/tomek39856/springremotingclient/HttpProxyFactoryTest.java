package com.github.tomek39856.springremotingclient;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class HttpProxyFactoryTest {
    private static int port = 1488;

    @BeforeClass
    public static void setUp() throws IOException {
       ServerManager.startServer(port);
    }

    @AfterClass
    public static void tearDown() {
        ServerManager.stopServer();
    }

    @Test
    public void shouldBuildEchoServiceClientThatCallsSpringRemotingServiceAndDeserializeResponse() throws Exception {
        //given:
        TestService testInterface = new HttpProxyFactory()
                .buildProxy(TestService.class, "http://localhost:" + port + "/test", new HashMap<>());

        //when:
        String response = testInterface.echo("test");

        //then:
        Assert.assertEquals("echo: test", response);
    }
}