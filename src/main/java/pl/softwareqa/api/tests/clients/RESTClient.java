package pl.softwareqa.api.tests.clients;

abstract class RESTClient extends EventReporting {

    protected RequestBuilder get(String url){
        return new RequestBuilder(reporter)
                .withMethod("GET")
                .withUrl(url);
    }

    protected RequestBuilder post(String url){
        return new RequestBuilder(reporter)
                .withMethod("POST")
                .withUrl(url);
    }
}

