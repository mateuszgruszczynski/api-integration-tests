package pl.softwareqa.api.tests.configuration;

import io.restassured.RestAssured;
import pl.softwareqa.api.tests.clients.EventReporter;
import pl.softwareqa.api.tests.clients.EventReporting;
import pl.softwareqa.api.tests.reporting.console.ConsoleReportingExtension;
import pl.softwareqa.api.tests.reporting.listeners.ReportingExtension;
import pl.softwareqa.api.tests.reporting.poc.DummyReporter;
import pl.softwareqa.api.tests.reporting.poc.EventObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ReportingExtension.class)
@ExtendWith(ConsoleReportingExtension.class)
@ExtendWith(EventObserver.class)
@ExtendWith(DummyReporter.class)
public class BaseTest extends EventReporting {

    public BaseTest(){
         this.reporter = new EventReporter();
    }

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = APIConfiguration.rootUrl;
    }

}