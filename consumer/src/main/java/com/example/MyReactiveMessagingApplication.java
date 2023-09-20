package com.example;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.faulttolerance.api.RateLimit;
import io.smallrye.faulttolerance.api.RateLimitException;
import io.smallrye.faulttolerance.api.RateLimitType;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class MyReactiveMessagingApplication {
    static final Logger LOGGER = Logger.getLogger("ConsumerApplication");


    @Incoming("words-in")
    @RateLimit(value = 10, window = 1, windowUnit = ChronoUnit.SECONDS, type = RateLimitType.FIXED)
    @Retry(maxRetries= 12, delay = 100, delayUnit = ChronoUnit.MILLIS, retryOn = RateLimitException.class)
    @Blocking
    public void doConsume(String payload) {
        LOGGER.infof(">> MSG payload :: " + payload);
    }

//    /**
//     * Consume the uppercase channel (in-memory) and print the messages.
//     **/
//    @Incoming("words-in")
//    public void consume(String msg) {
//        System.out.println(">> " + msg);
//        if (msg.contains(",")) {
//            throw new IllegalArgumentException("Message with exception :: " + msg);
//        }
//    }
//
//    @Incoming("dead-letter-topic-words-in")
//    public CompletionStage<Void> dead(Message<String> rejected) {
//        IncomingKafkaRecordMetadata<String, String> metadata =
//                rejected.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow(
//                        () -> new IllegalArgumentException("Expected a message coming from Kafka"));
//        String reason = new String(metadata.getHeaders().lastHeader("dead-letter-reason").value());
//
//        LOGGER.infof("The message '%s' has been rejected and sent to the DLT. The reason is: '%s'.",
//                rejected.getPayload(), reason);
//
//        return rejected.ack();
//    }
}
