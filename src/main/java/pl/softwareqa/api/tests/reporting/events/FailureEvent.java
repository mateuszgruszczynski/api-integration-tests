package pl.softwareqa.api.tests.reporting.events;

import j2html.tags.DomContent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;
import static j2html.TagCreator.pre;

@AllArgsConstructor
@Data
public class FailureEvent extends Event {

    String message;
    StackTraceElement[] stackTrace;

    @Override
    public DomContent toHTML() {
        return div(
                div(
                        "Assertion failed: " + message
                ).withClass("card-header").attr("data-bs-toggle", "collapse").attr("href", "#log-" + eventId).attr("role", "button"),
                div(
                        ul(
                                li(
                                      pre(
                                              Arrays.stream(stackTrace).map(
                                                      s -> s.toString() + "\n"
                                              ).collect(Collectors.joining())
                                      )
                                ).withClass("list-group-item")
                        ).withClass("list-group list-group-flush")
                ).withClass("collapse log-details").withId("log-" + eventId)
        ).withClass("card m-3 assertion-failure");
    }
}
