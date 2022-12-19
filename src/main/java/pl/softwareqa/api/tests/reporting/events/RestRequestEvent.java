package pl.softwareqa.api.tests.reporting.events;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;

import java.util.HashMap;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class RestRequestEvent extends Event {

    String method;
    String url;
    HashMap<String, String> headers;
    String body;

    public RestRequestEvent(String method, String url, HashMap<String, String> headers, String body) {
        this.method = method;
        this.url = url;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public DomContent toHTML() {

        return div(
                div(
                        method + " " + url
                ).withClass("card-header").attr("data-bs-toggle", "collapse").attr("href", "#log-" + eventId).attr("role", "button"),
                div(
                    ul(
                            li(
                                    pre(headers.entrySet().stream().map(h -> (h.getKey() + ": " + h.getValue()) + "\n").collect(Collectors.joining()))
                            ).withClass("list-group-item headers-list"),
                            li(
                                    pre(body)
                            ).withClass("list-group-item http-body")
                    ).withClass("list-group list-group-flush")
                ).withClass("collapse log-details").withId("log-" + eventId)
        ).withClass("card m-3 rest-request");
    }
}
