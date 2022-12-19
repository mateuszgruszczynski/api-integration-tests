package pl.softwareqa.api.tests.reporting.html;

import pl.softwareqa.api.tests.reporting.events.Event;
import pl.softwareqa.api.tests.reporting.logger.RunLog;
import pl.softwareqa.api.tests.reporting.logger.SuiteLog;
import pl.softwareqa.api.tests.reporting.logger.TestLog;

import java.util.ArrayList;
import java.util.Optional;

public class HTMLReporter extends ReportGenerator {

    private static RunLog runLog;

    // Handle run logs

    public static void runStarted(){
        runLog = new RunLog(System.currentTimeMillis(), Optional.empty(), new ArrayList<SuiteLog>());
    }

    public static void runFinished(){
        runLog.endRun();
        generateReport();
    }

    // Handle suite logs

    public static void suiteStarted(String suiteName, String suiteClass){
        runLog.getSuites().add(
                new SuiteLog(suiteName, System.currentTimeMillis(), Optional.empty(), new ArrayList<TestLog>(), Optional.empty())
        );
    }

    public static void suiteFinished(String suiteName, String status){
        runLog.getSuites().stream().filter(s -> s.getSuiteName().contains(suiteName)).findFirst().map(
                s -> s.endSuite(status)
        );
    }

    // Handle test logs

    public static void testStarted(String suiteName, String testName, String reportingContext){
        runLog.getSuites().stream().filter(s -> s.getSuiteName().equals(suiteName)).findFirst().map(s -> s.getTests().add(
                new TestLog(testName, suiteName, System.currentTimeMillis(), Optional.empty(), new ArrayList<Event>(), Optional.empty(), Optional.empty(), reportingContext)
        ));
    }

    public static void testFinished(String suiteName, String testName, String reportingContext, String status){
        runLog.getSuite(suiteName).get().getTest(testName).get().endTest(status);
    }

    public static void testInfoProvided(String reportingContext, Event event){
        runLog.getSuites().stream()
                .filter(s -> s.getTests().stream().anyMatch(t -> t.getReportingContext().equals(reportingContext))).findFirst()
                .map(s -> s.getTests().stream().filter(t -> t.getReportingContext().equals(reportingContext)).findFirst()
                        .map(t -> t.getRecordedEvents().add(event))
                );
    }

    public static void generateReport(){
        generateReport(runLog);
    }

}

