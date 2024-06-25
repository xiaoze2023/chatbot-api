package chatbot.api.test;


import chatbot.api.domain.AI.IOpenAI;
import chatbot.api.domain.zsxq.IZsxqApi;
import chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    //日志
    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);


    @Value("${chatbot-api.groupId}")
    //调用app.yml的配置值
    private String groupId;
    @Value("${chatbot-api.cookie}")
    //调用app.yml的配置值
    private String cookie;

    @Resource
    //引用zsxq接口类,因为它是另一个包的,记得在pom.xml中引入
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    @Test
    public void test_zsxqApi() throws IOException {
        //调用zsxq的获取问题类
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果: {}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();

        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();//具体问题是啥
            logger.info("topicId: {} text:{}", topicId, text);
            //回答问题
            zsxqApi.answer(groupId, cookie, topicId,text,false);
        }
    }


    //chatGPT接口测试结果
    @Test
    public void test_openAi() throws IOException {
        String response = openAI.doChatGPT("帮我写一个java冒泡排序");
        logger.info("测试结果：{}",response);
    }


}
