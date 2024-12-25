package org.emsi.microservicesidempotentconsumerdemo;

import org.springframework.stereotype.Component;

@Component
public class RequestStateMachine {

    public Request next(Request req, Request.Status nextStatus) {
        String transitionStr = String.format("Transition: %s -> %s", req.getStatus(), nextStatus);
        switch (nextStatus) {
            case PENDING -> throw new InvalidNextStateException(transitionStr);
            case STARTED -> {
                if (Request.Status.PENDING.equals(req.getStatus())) {
                    return new Request(req.getUuid(), Request.Status.STARTED);
                }
                throw new InvalidNextStateException(transitionStr);
            }
            case COMPLETED -> {
                if (Request.Status.STARTED.equals(req.getStatus())) {
                    return new Request(req.getUuid(), Request.Status.COMPLETED);
                }
                throw new InvalidNextStateException(transitionStr);
            }
            default -> throw new InvalidNextStateException(transitionStr);
        }
    }
}
