package chatbot.api.application.job;


import chatbot.api.domain.AI.IOpenAI;
import chatbot.api.domain.zsxq.IZsxqApi;
import chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import chatbot.api.domain.zsxq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

//问题任务(寻时)
@EnableScheduling
@Configuration
public class ChatbotSchedule  {
    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    //调用app.yml的配置值
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    //引用zsxq接口类,因为它是另一个包的,记得在pom.xml中引入
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;

    @Scheduled(cron = "0/30 * * * * ?")//轮循任务 表达式: cron.qqe2.com
    public void run(){
        try{
            //让轮训不能一直发送,免得封号
            if (new Random().nextBoolean()) {
                logger.info("随机打烊中...");
                return;
            }
            //设置工作时间
//            GregorianCalendar calendar = new GregorianCalendar();
//            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//            if (hour > 22 || hour < 7) {
//                logger.info("打烊时间不工作，AI 下班了！");
//                return;
//            }


        //1.检索问题
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId,cookie);
            logger.info("测试结果: {}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        //出现异常
            if (null == topics || topics.isEmpty()) {
                logger.info("本次检索未查询到待会答问题");
                return;
            }
        //2.调用ai
            Topics topic = topics.get(0);//每次轮循只调用一次
            String answer = openAI.doChatGPT(topic.getQuestion().getText().trim());

        //问题回复
            boolean status = zsxqApi.answer(groupId,cookie,topic.getTopic_id(),answer,false);
            logger.info("编号: {} 问题: {} 回答: {} 状态: {} ",topic.getTopic_id(),topic.getQuestion().getText(),answer,status);



        }catch (Exception e){
            logger.error("自动回答问题异常",e);


        }


    }


}
