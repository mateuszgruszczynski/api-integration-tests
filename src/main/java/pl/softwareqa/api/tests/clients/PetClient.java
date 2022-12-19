package pl.softwareqa.api.tests.clients;

public class PetClient extends RESTClient{

    public PetClient(EventReporter reporter){
        this.reporter = reporter;
    }

    static String petsUrl = "/v2/pet/";

    public ResponseValidator getPet(Integer id){
        return get(petsUrl + id)
                .withHeader("content-type", "application/json")
                .getResponse();
    }

}
