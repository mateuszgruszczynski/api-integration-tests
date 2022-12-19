package pl.softwareqa.api.tests.reporting.logger;

import pl.softwareqa.api.tests.reporting.events.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
public class TestLog {
    String testName;
    String suiteName;
    Long startTime;
    Optional<Long> endTime;
    List<Event> recordedEvents;
    Optional<String> status;
    Optional<Throwable> failReason;
    String reportingContext;

    public TestLog endTest(String status) {
        this.endTime = Optional.of(System.currentTimeMillis());
        this.status = Optional.of(status);
        return this;
    }
}
