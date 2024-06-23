package chatbot.api.domain.zsxq;

import chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

public interface IZsxqApi {

    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie)throws IOException;

    //throws IOException用于抛出异常
    boolean answer(String groupId,String cookie,String topicId,String text,boolean silenced)throws IOException;


}
