package se.web.ramakrishnan.employeeservice.model;

public class ResponseDelete {
    private boolean deleted;

    public ResponseDelete(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
