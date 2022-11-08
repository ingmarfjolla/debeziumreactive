/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.outbox.quarkus.it;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import io.debezium.outbox.quarkus.ExportedEvent;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class MyService {

    @Inject
    Event<ExportedEvent<?, ?>> event;

    public Uni<MyOutboxEvent> doSomething() {
        final Map<String, Object> values = new HashMap<>();
        values.put("name", "John Doe"); // illustrates additional field with no converter
        values.put("name_upper", "John Doe"); // illustrates additional field with converter
        values.put("name_no_columndef", "Jane Doe"); // illustrates default behavior with no column definition specified
        System.out.println("@@@@@@@@@@@@@@@@@@@@ myservice@@@@@@@@@@@@@@@@" + values);
        // event.fire(new MyOutboxEvent(values));
        // var name = event.fireAsync(new MyOutboxEvent(values));
        return Uni.createFrom().completionStage(
                event.fireAsync(new MyOutboxEvent(values)));
        // return Uni.createFrom().voidItem();
    }
}
