package pl.softwareqa.api.tests.reporting.console;

import pl.softwareqa.api.tests.clients.EventReporter;
import pl.softwareqa.api.tests.clients.EventReporting;
import org.junit.jupiter.api.extension.*;

import java.util.Optional;

public class ConsoleReportingExtension extends EventReporting implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, TestWatcher {

    //TODO: Make an abstract class that require to provide reporter interface class
    //TODO: Only one reporter is allowed, otherwise events will be duplicated

    private void writeToConsole(String message, String color){
        System.out.println(message);
    }

    private EventReporter getReporter(ExtensionContext context){
        return ((EventReporting) context.getTestInstance().get()).reporter;
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        //HTMLReporter.suiteStarted(context.getDisplayName(), "");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        //HTMLReporter.testStarted(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        //HTMLReporter.suiteFinished(context.getDisplayName(), "");
    }

    public void testSuccessful(ExtensionContext context) {
//        String uniqueId = ((EventReporting) context.getTestInstance().get()).reporter.uniqueId;
//        //HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "SUCCESS");
        writeToConsole("[+] " + context.getParent().get().getDisplayName() + " / " + context.getDisplayName() + " PASSED", "" );
    }

    public void testFailed(ExtensionContext context, Throwable cause) {
        //getReporter(context).publishEvent(new FailureEvent(cause.getMessage(), cause.getStackTrace()));
        //HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "FAILURE");
    }

    public void testAborted(ExtensionContext context, Throwable cause) {
        //getReporter(context).publishEvent(new FailureEvent(cause.getMessage(), cause.toString()));
        //HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "ABORTED");
    }

    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        //HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "DISABLED");
    }
}
