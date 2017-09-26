package com.github.tomek39856.springremotingclient;

public class TestServiceImpl implements TestService {
    @Override
    public String echo(String param) {
        return "echo: " + param;
    }
}