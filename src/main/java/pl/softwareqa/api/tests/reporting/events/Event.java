package pl.softwareqa.api.tests.reporting.events;

import j2html.tags.DomContent;

import java.util.UUID;

public abstract class Event {
    String eventId = UUID.randomUUID().toString();
    public abstract DomContent toHTML();
}
