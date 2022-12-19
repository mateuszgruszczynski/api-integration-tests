package pl.softwareqa.api.tests.reporting.poc;

import pl.softwareqa.api.tests.clients.EventReporter;
import pl.softwareqa.api.tests.clients.EventReporting;
import org.junit.jupiter.api.extension.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventObserver extends EventReporting implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, TestWatcher {

    static List<ReporterInstance> instances = new ArrayList<>();

    public static void registerInstance(ReporterInstance i){
        System.out.println("Registering instance");
        instances.add(i);
    }

    public void notifyInstances(String e){
        instances.forEach(i -> i.handleEvent(e));
    }

    //TODO: Rename to context???
    private EventReporter getReporter(ExtensionContext context){
        return ((EventReporting) context.getTestInstance().get()).reporter; //TODO: Vavr option?
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        notifyInstances("BEFORE_ALL");
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        notifyInstances("BEFORE_EACH");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        notifyInstances("AFTER_ALL");
    }

    public void testSuccessful(ExtensionContext context) {
        notifyInstances("TEST_SUCCESSFUL");
    }

    public void testFailed(ExtensionContext context, Throwable cause) {
        notifyInstances("TEST_FAILED");
    }

    public void testAborted(ExtensionContext context, Throwable cause) {
        notifyInstances("TEST_ABORTED");
    }

    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        notifyInstances("TEST_DISABLED");
    }
}
