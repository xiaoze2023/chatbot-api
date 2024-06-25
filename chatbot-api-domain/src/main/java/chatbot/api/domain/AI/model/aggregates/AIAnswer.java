package chatbot.api.domain.AI.model.aggregates;

import chatbot.api.domain.AI.model.vo.Choices;

import java.util.List;

public class AIAnswer {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choices> choices;
    // Additional fields if necessary

    // Getter and Setter methods for each field
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }
}