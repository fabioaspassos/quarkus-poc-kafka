package com.acme.producer;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.stream.Stream;

@ApplicationScoped
public class MyReactiveMessagingApplication {
    static final Logger LOGGER = Logger.getLogger("ProducerApplication");

    @Inject
    @Channel("words-out")
    Emitter<String> emitter;

    /**
     * Sends message to the "words-out" channel, can be used from a JAX-RS resource or any bean of your application.
     * Messages are sent to the broker.
     **/
    void onStart(@Observes StartupEvent ev) {
//        Stream.of("Hello", "with", "SmallRye", "reactive", "message").forEach(string -> emitter.send(string));

        LOGGER.infof("Sending messages...");
        Multi.createFrom()
                .range(1, 200).map(x -> "message "+ x)
                .subscribe()
                .with(msg -> emitter.send(msg));
    }
}