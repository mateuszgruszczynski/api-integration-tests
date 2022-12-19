package pl.softwareqa.api.tests.reporting.logger;

import pl.softwareqa.api.tests.reporting.events.Event;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Keeps log of events produced during tests and notifies reporters about events
 *
 * Eventy
 *  - runStarted
 *  - suiteStarted
 *  - suitePassed
 *  - suiteFinished
 *  - testStarted
 *
 * Reporter
 *  - wysyła
 *
 *
 *
 */
public class TestLogHandler {

        private static RunLog runLog;

        // Handle run logs

        public static void runStarted(){
            runLog = new RunLog(System.currentTimeMillis(), Optional.empty(), new ArrayList<SuiteLog>());
        }

        public static void runFinished(){
            runLog.endRun();
//            generateReport();
            //TODO: This should somehow trigger all post test report generation
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



//        public static void generateReport(){
//            generateReport(runLog);
//        }

}
