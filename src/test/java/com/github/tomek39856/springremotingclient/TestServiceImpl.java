package com.github.tomek39856.springremotingclient;

public class TestServiceImpl implements com.github.tomek39856.springremotingclient.TestService {
    @Override
    public String echo(String param) {
        return "echo: " + param;
    }
}