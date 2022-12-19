package pl.softwareqa.api.tests.reporting.poc;

public class DummyReporter extends ReporterInstance{
    @Override
    public void handleEvent(String e) {
        System.out.println("Received event: " + e);
    }
}
