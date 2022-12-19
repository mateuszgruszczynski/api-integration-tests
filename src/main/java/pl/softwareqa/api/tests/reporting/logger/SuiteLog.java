package pl.softwareqa.api.tests.reporting.logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
public class SuiteLog {
    String suiteName;
    Long startTime;
    Optional<Long> endTime;
    List<TestLog> tests;
    Optional<String> status;

    public SuiteLog endSuite(String status) {
        this.status = Optional.of(status);
        this.endTime = Optional.of(System.currentTimeMillis());
        return this;
    }

    //TODO: Handle missing test
    public Optional<TestLog> getTest(String name) {
        return tests.stream().filter(t -> t.getTestName() == name).findFirst();
    }

    public String getStatus() {
        if (tests.stream().filter(t -> t.getStatus().orElse("") != "SUCCESS").count() == 0) {
            return "SUCCESS";
        } else {
            return "OTHER";
        }
    }
}
