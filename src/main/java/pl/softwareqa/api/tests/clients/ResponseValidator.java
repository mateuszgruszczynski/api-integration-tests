package pl.softwareqa.api.tests.clients;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import pl.softwareqa.api.tests.domain.ErrorResponse;

import static org.hamcrest.MatcherAssert.assertThat;

//TODO: Not sure if we need that validator, or maybe we rather use RestAssured dsl directly
public class ResponseValidator {

    public ValidatableResponse response;

    ResponseValidator(ValidatableResponse response){
        this.response = response;
    }

    public ResponseValidator assertStatusOK(){
        response.statusCode(200);
        return this;
    }

    public ResponseValidator assertError(Integer statusCode, String message){
        assertThat(
                "System should respond with " + statusCode + " status code",
                response.extract().statusCode(),
                Matchers.equalTo(statusCode)
        );
        assertThat(
                "Response should contain valid message",
                response.extract().body().as(ErrorResponse.class).getMessage(),
                Matchers.equalTo(message)
        );
        return this;
    }

    public <T> T getContentAs(Class<T> cls){
        return response.extract().body().as(cls);
    }

    public <T> T getContentAs(TypeRef<T> tr){
        return response.extract().body().as(tr);
    }

}
