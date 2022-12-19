package pl.softwareqa.api.tests.reporting.poc;

import org.junit.jupiter.api.extension.Extension;

public abstract class ReporterInstance implements Extension {

    {
        EventObserver.registerInstance(this);
    }

    public abstract void handleEvent(String e);

}
