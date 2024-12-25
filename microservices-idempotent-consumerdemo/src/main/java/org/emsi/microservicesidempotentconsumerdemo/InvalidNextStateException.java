package org.emsi.microservicesidempotentconsumerdemo;

public class InvalidNextStateException extends RuntimeException {
    public InvalidNextStateException(String s) {
        super(s);
    }
}
