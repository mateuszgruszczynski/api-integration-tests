package pl.softwareqa.api.tests.reporting.listeners;

import pl.softwareqa.api.tests.reporting.html.HTMLReporter;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.LauncherSessionListener;

public class TestRunListener implements LauncherSessionListener {

    @Override
    public void launcherSessionOpened(LauncherSession session) {
        HTMLReporter.runStarted();
    }

    @Override
    public void launcherSessionClosed(LauncherSession session) {
        HTMLReporter.runFinished();
    }
}
