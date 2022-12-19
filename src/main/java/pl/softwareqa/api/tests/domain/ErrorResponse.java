package pl.softwareqa.api.tests.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Jacksonized
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    String code;
    String message;
    List<Map<String, String>> fieldErrors;

    public Boolean containsError(String field, String message){
        return fieldErrors.stream().anyMatch(m -> m.get(field).equals(message));
    }
}
