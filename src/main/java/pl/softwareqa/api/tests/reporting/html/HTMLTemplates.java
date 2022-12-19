package pl.softwareqa.api.tests.reporting.html;

import pl.softwareqa.api.tests.reporting.events.Event;
import pl.softwareqa.api.tests.reporting.logger.RunLog;
import pl.softwareqa.api.tests.reporting.logger.SuiteLog;
import pl.softwareqa.api.tests.reporting.logger.TestLog;
import j2html.tags.ContainerTag;
import j2html.tags.DomContent;

import java.util.Comparator;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;
import static j2html.TagCreator.a;

public class HTMLTemplates {

    public static String indexTemplate(RunLog log){
        return document(
                html(
                        head(
                                title("API tests"),
                                link().withRel("stylesheet").withHref("static/report.css"),
                                link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css")
                        ),
                        body(
                                div(
                                        div(
                                                div(
                                                        div(
                                                                div(
                                                                        img().withSrc("static/apilogo.png").withClass("logo")
                                                                ).withClass("row logo"),
                                                                div(
                                                                        HTMLTemplates.testTreeTemplate(log)
                                                                ).withClass("row tree-list flex-grow-1")
                                                        ).withClass("h-100 d-flex flex-column")
                                                ).withClass("left-panel"),
                                                div(
                                                        div(
                                                                div(
                                                                        h1("API Test Results")
                                                                ).withClass("row title"),
                                                                div(
                                                                        iframe().withSrc("timeline.html").withClass("embed-responsive-item p-0").withName("details-view").withId("details-view").attr("sandbox", "allow-scripts allow-same-origin allow-modal")
                                                                ).withClass("row main-view flex-grow-1")
                                                        ).withClass("h-100 d-flex flex-column")
                                                ).withClass("col")
                                        ).withClass("row h-100")
                                ).withClass("container-fluid h-100"),
                                script().withSrc("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"),
                                script().withSrc("static/scripts.js")
                        )
                )
        );
    }

    private static DomContent testTreeTemplate(RunLog log){
        return div(
                    div(
                            button("✔ Show/Hide passed tests").withClass("btn passed-toggle").attr("title", "Toggle show passed tests").attr("onClick", "togglePassed()")
                    ).withId("status-toggle"),
                    ul(
                    log.getSuites().stream()
                            .filter(s -> !s.getTests().isEmpty())
                            .sorted(Comparator.comparing(SuiteLog::getSuiteName))
                            .map(suite ->
                                    li(
                                            a(
                                                    h1(suite.getSuiteName())
                                            ).withHref("suites/" + suite.getSuiteName() + ".html").withTarget("details-view"),
                                            ul(
                                                    suite.getTests().stream()
                                                            .sorted(Comparator.comparing(TestLog::getTestName))
                                                            .map(test ->
                                                                    li(
                                                                            a(test.getTestName())
                                                                                    .withHref("suites/" + test.getSuiteName() + "/" + test.getTestName() + ".html")
                                                                                    .withTarget("details-view")
                                                                    ).withClass(test.getStatus().orElse("OTHER"))
                                                            ).toArray(ContainerTag[]::new)
                                            ).withClass("list-unstyled")
                                    ).withClass(suite.getStatus())
                            ).toArray(ContainerTag[]::new)
            ).withClass("m-3 list-unstyled")
        ).withClass("side-panel");
    }


    public static String testPageTemplate(TestLog log){
        return document(
                html(
                        head(
                                title(log.getTestName()),
                                link().withRel("stylesheet").withHref("../../static/report.css"),
                                link().withRel("stylesheet").withHref("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css")
                        ),
                        body(
                                div(
                                        button("⇈").attr("onClick", "scrollUp()").withClass("btn tool-btn").attr("title", "Go to first event"),
                                        button("Ξ").withClass("btn tool-btn").attr("data-bs-toggle", "collapse").attr("data-bs-target", ".log-details").attr("title", "Toggle show event details"),
                                        button("⇊").attr("onClick", "scrollDown()").withClass("btn tool-btn").attr("title", "Go to last event")
                                ).withId("test-tools").withClass("card"),
                                div(
                                        log.getRecordedEvents().stream().map(Event::toHTML).toArray(j2html.tags.DomContent[]::new)
                                ).withId("test-log"),
                                script().withSrc("https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"),
                                script().withSrc("../../static/scripts.js")
                        )
                )
        );
    }

    public static String testRunSummaryTemplate(RunLog log){
        return document(
                html(
                        head(
                                link().withRel("stylesheet").withHref("static/timeline.css")
                        ),
                        body(
                                script().withSrc("https://www.gstatic.com/charts/loader.js"),
                                script(timelineScript(log)),
                                div().withId("testTimeLine")
                        )
                )
        );
    }

    public static String suiteRunSummaryTemplate(SuiteLog log){
        return document(
                html(
                        head(
                                link().withRel("stylesheet").withHref("../static/timeline.css")
                        ),
                        body(
                                div(
                                        "BBBBBE"
                                ).withClass("card"),
                                script().withSrc("https://www.gstatic.com/charts/loader.js"),
                                script(suiteTimelineScript(log)),
                                div().withId("testTimeLine")
                        )
                )
        );
    }

    public static String timelineScript(RunLog log){
        return "google.charts.load(\"current\", {packages:[\"timeline\"]});\n" +
                "google.charts.setOnLoadCallback(drawChart);\n" +
                "\n" +
                "function drawChart() {\n" +
                "    var container = document.getElementById('testTimeLine');\n" +
                "    var chart = new google.visualization.Timeline(container);\n" +
                "    var dataTable = new google.visualization.DataTable();\n" +
                "    dataTable.addColumn({ type: 'string', id: 'N' });\n" +
                "    dataTable.addColumn({ type: 'string', id: 'Suite' });\n" +
                "    dataTable.addColumn({ type: 'date', id: 'Start' });\n" +
                "    dataTable.addColumn({ type: 'date', id: 'End' });\n" +
                "    dataTable.addRows([" +

                log.getSuites().stream()
                        .filter(s -> !s.getTests().isEmpty())
                        .map(s -> "['" + s.getSuiteName() + "', '" + s.getSuiteName() + "', new Date(" + s.getStartTime() +"), new Date(" + s.getEndTime().orElse(System.currentTimeMillis()) + ")"  + "],")
                        .collect(Collectors.joining())
                +

                "]);\n" +
                "\n" +
                "    var options = {\n" +
                "      colors: ['#79DAE8', '#0AA1DD', '#2155CD'], " +
                "      timeline: { colorByRowLabel: false, groupByRowLabel: false, alternatingRowStyle: false, showRowLabels: false },\n" +
                "      backgroundColor: '#f2f2f2'\n" +
                "    };\n" +
                "\n" +
                "    chart.draw(dataTable, options);\n" +
                "}";
    }

    public static String suiteTimelineScript(SuiteLog log){
        return "google.charts.load(\"current\", {packages:[\"timeline\"]});\n" +
                "google.charts.setOnLoadCallback(drawChart);\n" +
                "\n" +
                "function drawChart() {\n" +
                "    var container = document.getElementById('testTimeLine');\n" +
                "    var chart = new google.visualization.Timeline(container);\n" +
                "    var dataTable = new google.visualization.DataTable();\n" +
                "    dataTable.addColumn({ type: 'string', id: 'N' });\n" +
                "    dataTable.addColumn({ type: 'string', id: 'Suite' });\n" +
                "    dataTable.addColumn({ type: 'date', id: 'Start' });\n" +
                "    dataTable.addColumn({ type: 'date', id: 'End' });\n" +
                "    dataTable.addRows([" +

                log.getTests().stream()
                        .map(t -> "['" + t.getTestName() + "', '" + t.getTestName() + "', new Date(" + t.getStartTime() +"), new Date(" + t.getEndTime().orElse(System.currentTimeMillis()) + ")"  + "],")
                        .collect(Collectors.joining())
                +

                "]);\n" +
                "\n" +
                "    var options = {\n" +
                "      colors: ['#79DAE8', '#0AA1DD', '#2155CD'], " +
                "      timeline: { colorByRowLabel: false, groupByRowLabel: false, alternatingRowStyle: false, showRowLabels: false },\n" +
                "      backgroundColor: '#f2f2f2'\n" +
                "    };\n" +
                "\n" +
                "    chart.draw(dataTable, options);\n" +
                "}";
    }

}
