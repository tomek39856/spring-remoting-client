package com.github.tomek39856.springremotingclient;

import java.util.Map;

@FunctionalInterface
public interface RequestPropertiesProvider {
    Map<String, String> getRequestProperties();
}