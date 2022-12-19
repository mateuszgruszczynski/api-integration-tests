package pl.softwareqa.api.tests.reporting.listeners;

import pl.softwareqa.api.tests.clients.EventReporter;
import pl.softwareqa.api.tests.clients.EventReporting;
import pl.softwareqa.api.tests.reporting.html.HTMLReporter;
import pl.softwareqa.api.tests.reporting.events.FailureEvent;
import org.junit.jupiter.api.extension.*;

import java.util.Optional;

public class ReportingExtension extends EventReporting implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, TestWatcher {

    private EventReporter getReporter(ExtensionContext context){
        return ((EventReporting) context.getTestInstance().get()).reporter;
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        HTMLReporter.suiteStarted(context.getDisplayName(), "");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        HTMLReporter.testStarted(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        HTMLReporter.suiteFinished(context.getDisplayName(), "");
    }

    public void testSuccessful(ExtensionContext context) {
        HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "SUCCESS");
    }

    public void testFailed(ExtensionContext context, Throwable cause) {
        getReporter(context).publishEvent(new FailureEvent(cause.getMessage(), cause.getStackTrace()));
        HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "FAILURE");
    }

    public void testAborted(ExtensionContext context, Throwable cause) {
        HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "ABORTED");
    }

    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        HTMLReporter.testFinished(context.getParent().get().getDisplayName(), context.getDisplayName(), getReporter(context).uniqueId, "DISABLED");
    }
}
