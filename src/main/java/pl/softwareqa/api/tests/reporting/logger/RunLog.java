package pl.softwareqa.api.tests.reporting.logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
public class RunLog {
    Long startTime;
    Optional<Long> endTime;
    List<SuiteLog> suites;

    public RunLog endRun() {
        this.endTime = Optional.of(System.currentTimeMillis());
        return this;
    }

    public Optional<SuiteLog> getSuite(String name) {
        return suites.stream().filter(s -> s.getSuiteName() == name).findFirst();
    }
}
