package at.farizio.echoWebhook;

import java.util.List;

public class JsonFile {
    List<Request> requests;

    public JsonFile() {
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
