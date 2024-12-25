package org.emsi.microservicesidempotentconsumerdemo;

import java.util.UUID;

public class RequestNotFoundException extends RuntimeException {
    RequestNotFoundException(UUID uuid) {
        super(String.format("Request %s not found", uuid));
    }
}
