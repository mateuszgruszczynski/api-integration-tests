package pl.softwareqa.api.tests.reporting.events;

import j2html.tags.DomContent;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssertionEvent extends Event {

    public String clue;
    public Boolean result;
    public String message;
    public String stackTrace;

    @Override
    public DomContent toHTML() {
        return null;
    }
}
