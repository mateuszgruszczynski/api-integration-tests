package pl.softwareqa.api.tests.reporting.html;

import pl.softwareqa.api.tests.reporting.logger.RunLog;
import pl.softwareqa.api.tests.reporting.logger.SuiteLog;
import pl.softwareqa.api.tests.reporting.logger.TestLog;
import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReportGenerator {

    static String reportsPath = "target/tests-reports/"; //TODO: Get from config
    static String reportPrefix = LocalDateTime.now().toString("yyyy_MM_dd_HH_mm_ss");
    static String mainPath = reportsPath + reportPrefix + "/";

    public static void generateReport(RunLog log){
        try {
            // Create target/test-reports directory if not exist
            // Create directory for new test report
            Files.createDirectories(Paths.get(mainPath));
            Files.createDirectories(Paths.get(mainPath + "static"));
            Files.copy(Paths.get("src/main/resources/static/report.css"), Paths.get(mainPath + "static/report.css")); //TODO: Move to static dir + add images + js
            Files.copy(Paths.get("src/main/resources/static/timeline.css"), Paths.get(mainPath + "static/timeline.css")); //TODO: Move to static dir + add images + js
            Files.copy(Paths.get("src/main/resources/static/apilogo.png"), Paths.get(mainPath + "static/apilogo.png")); //TODO: Move to static dir + add images + js
            Files.copy(Paths.get("src/main/resources/static/pass.png"), Paths.get(mainPath + "static/pass.png")); //TODO: Move to static dir + add images + js
            Files.copy(Paths.get("src/main/resources/static/scripts.js"), Paths.get(mainPath + "static/scripts.js")); //TODO: Move to static dir + add images + js
            Files.copy(Paths.get("src/main/resources/static/failed.png"), Paths.get(mainPath + "static/failed.png")); //TODO: Move to static dir + add images + js
            Files.copy(Paths.get("src/main/resources/static/other.png"), Paths.get(mainPath + "static/other.png")); //TODO: Move to static dir + add images + js

            // Create or overwrite the latest link
            Files.deleteIfExists(Paths.get(reportsPath + "/latest"));
            Files.createSymbolicLink(Paths.get(reportsPath + "/latest"), Paths.get(reportPrefix));
        } catch (IOException e) {
            System.out.println("CANNOT CREATE DIRS OR STATICS" + e.getMessage());
        }

        // Generate actual report
        generateRunPage(log);
        generateTestTimeLine(log);
        log.getSuites().forEach(suite -> {
            generateTestTimeLine(suite);
            suite.getTests().forEach(test -> {
                generateTestPage(test);
            });
        });
    }

    public static void generateTestPage(TestLog log){
        writePage(
                "suites/" + log.getSuiteName() + "/" + log.getTestName() + ".html",
                HTMLTemplates.testPageTemplate(log)
        );
    }

//    public static void generateSuitePage(SuiteLog log){
//        writePage(
//                "suites/" + log.getSuiteName() + ".html",
//                HTMLTemplates.suitePageTemplate(log)
//        );
//    }

    public static void generateRunPage(RunLog log){
        writePage(
                "index.html",
                HTMLTemplates.indexTemplate(log)
        );
    }

    public static void generateTestTimeLine(RunLog log){
        writePage(
                "timeline.html",
                HTMLTemplates.testRunSummaryTemplate(log)
        );
    }

    public static void generateTestTimeLine(SuiteLog log){
        writePage(
                "suites/" + log.getSuiteName() + ".html",
                HTMLTemplates.suiteRunSummaryTemplate(log)
        );
    }

    private static void writePage(String path, String content){
        Path filePath = Paths.get(mainPath + path);
        try{
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, content);
        } catch (IOException e) {
            System.out.println("CANNOT CREATE" + e.getLocalizedMessage());
        }
    }
}
