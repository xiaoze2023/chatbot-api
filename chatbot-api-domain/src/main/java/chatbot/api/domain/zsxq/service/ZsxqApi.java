package chatbot.api.domain.zsxq.service;

import chatbot.api.domain.zsxq.IZsxqApi;
import chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import chatbot.api.domain.zsxq.model.req.AnswerReq;
import chatbot.api.domain.zsxq.model.req.ReqData;
import chatbot.api.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ZsxqApi implements IZsxqApi {


    //获取提问的数据
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=unanswered_questions&count=20");
            get.addHeader("cookie",cookie);
            get.addHeader("Content-Type","application/json, text/plain, */*");

            CloseableHttpResponse response = httpClient.execute(get);
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String jsonStr = EntityUtils.toString(response.getEntity());
                logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);
                return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);

            }else{
                throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
            }


    }

    //回答问题的结果
    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();//用于创建发送HTTP请求

        //实例化post 指定
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/answer");
        post.addHeader("cookie",cookie);
        post.addHeader("Content-Type","application/json, text/plain, */*");
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 Edg/126.0.0.0");

//       测试 String patamJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"我真不会\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"silenced\": false\n" +
//                "  }\n" +
//                "}";
        AnswerReq answerReq = new AnswerReq(new ReqData(text,silenced));

        String paramJson = JSONObject.toJSONString(answerReq);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        //它创建了一个StringEntity对象，该对象包含了将要发送到服务器的JSON数据，并指定了内容类型和字符编码
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            //添加日志
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr,AnswerRes.class);
            return answerRes.isSucceeded();
        }else{
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }

    }
}
