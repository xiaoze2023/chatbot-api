package chatbot.api.domain.AI.model.vo;

public class Choices {
    private String finish_reason;
    private int index;
    private String message; // This will contain the content as a nested object

    // Getter and Setter methods for each field
    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}