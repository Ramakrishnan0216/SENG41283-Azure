package se.web.ramakrishnan.employeeservice.model;

public class StorageResponse {
    private String uri;
    private boolean error;
    private String message;

    public StorageResponse() {
    }

    public StorageResponse(String message) {
        this.message = message;
    }

    public StorageResponse(String uri, boolean error, String message) {
        this.uri = uri;
        this.error = error;
        this.message = message;
    }

    public StorageResponse(String uri, boolean error) {
        this.uri = uri;
        this.error = error;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
