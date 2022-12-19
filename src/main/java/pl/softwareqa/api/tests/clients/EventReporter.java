package pl.softwareqa.api.tests.clients;

import pl.softwareqa.api.tests.reporting.html.HTMLReporter;
import pl.softwareqa.api.tests.reporting.events.Event;

import java.util.UUID;

public class EventReporter {
    public String uniqueId = UUID.randomUUID().toString();

    public void publishEvent(Event e) {
        HTMLReporter.testInfoProvided(uniqueId, e);
    }
}
