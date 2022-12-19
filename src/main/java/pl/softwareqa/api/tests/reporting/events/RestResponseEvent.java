package pl.softwareqa.api.tests.reporting.events;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;

import java.util.Map;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class RestResponseEvent extends Event {

    String url;
    Integer status;
    Long responseTime;
    Map<String, String> headers;
    String body;

    public RestResponseEvent(String url, Integer status, Long responseTime, Map<String, String> headers, String body) {
        this.url = url;
        this.status = status;
        this.responseTime = responseTime;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public DomContent toHTML() {
        return div(
                div(
                        "HTTP " + status + " @ " + url + " (" + responseTime + " ms) "
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
        ).withClass("card m-3 rest-response");
    }
}
