package pl.softwareqa.api.tests.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.restassured.http.Header;
import pl.softwareqa.api.tests.configuration.HeadersPresets;
import pl.softwareqa.api.tests.configuration.APIConfiguration;
import pl.softwareqa.api.tests.reporting.events.RestRequestEvent;
import pl.softwareqa.api.tests.reporting.events.RestResponseEvent;
import io.vavr.control.Try;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

//TODO: Actually probably the only reason to have this class is to handle logging, as eveything else could be done with restassured dsl
public class RequestBuilder extends EventReporting {

    ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());

    public RequestBuilder(EventReporter tc){
        this.reporter = tc;
    }

    String method;
    String url;
    Optional<String> body = Optional.empty();
    HashMap<String, String> headers = HeadersPresets.defaultHeaders(); //TODO: We may use it to different api, headers should be set in client for given service or in test
    HashMap<String, String> urlParams = new HashMap<>();

    private Map<String, String> getNonEmptyUrlParams(){
        return urlParams.entrySet().stream().filter(es -> es.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String getFullUrl(){
        return APIConfiguration.rootUrl
                + url
                + (
                        (!getNonEmptyUrlParams().isEmpty()) ?
                                "?" + getNonEmptyUrlParams().entrySet().stream().map(es -> es.getKey() + "=" + es.getValue()).collect(Collectors.joining("&"))
                                : ""
        );
    }

    RequestBuilder withUrl(String url){
        this.url = url;
        return this;
    }

    RequestBuilder withUrlParam(String key, String value){ //TODO: Handle optional params or nulls??
        this.urlParams.put(key, value);
        return this;
    }

    RequestBuilder withJsonBodyFromString(String body){
        this.body = Optional.of(body);
        return this;
    }

    <T> RequestBuilder withJsonBodyFromObject(T object) {
        this.body = Optional.of(Try.of(() -> mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object))
                .getOrElseThrow(() -> new RuntimeException("Cannot serialize given object")));
        this.withHeader("content-type", "application/json");
        return this;
    }

    RequestBuilder withMethod(String method){
        this.method = method;
        return this;
    }

    RequestBuilder withHeader(String key, String value){
        this.headers.put(key, value);
        return this;
    }

    ResponseValidator getResponse() {
        reporter.publishEvent(new RestRequestEvent(method, getFullUrl(), headers, body.orElse("")));

        var response = given()
                .body(body.orElse(""))
                .headers(headers)
                .params(getNonEmptyUrlParams())
                .request(method, url);

        Map<String, String> responseHeaders = response.headers().asList().stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));

        reporter.publishEvent(new RestResponseEvent(
                getFullUrl(), response.getStatusCode(), response.time(), responseHeaders, response.asPrettyString())
        );

        return new ResponseValidator(response.then());
    }

}
