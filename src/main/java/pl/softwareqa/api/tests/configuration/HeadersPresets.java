package pl.softwareqa.api.tests.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeadersPresets {

    public static HashMap<String, String> defaultHeaders(){
        return new HashMap(Map.of(
                "correlationId", UUID.randomUUID().toString()
        ));
    }

}
