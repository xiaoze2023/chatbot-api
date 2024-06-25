package chatbot.api.domain.AI;

import java.io.IOException;

//AI接口
public interface IOpenAI {

    String doChatGPT(String question) throws IOException;


}
