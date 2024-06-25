package chatbot.api.domain.AI.service;

import chatbot.api.domain.AI.IOpenAI;
import chatbot.api.domain.AI.model.aggregates.AIAnswer;
import chatbot.api.domain.AI.model.vo.Choices;
import cn.hutool.core.net.SSLContextBuilder;
import com.alibaba.fastjson.JSON;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.core.httpclient.ApacheHttpClientTransport;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.util.List;


@Service
public class OpenAI implements IOpenAI {

    private Logger logger = LoggerFactory.getLogger(OpenAI.class);
    @Value("${chatbot-api.openAikey}")
    private String openAikey;

    @Override
    public String doChatGPT(String question) throws IOException {

        {

            //设置TLS协议版本
//            System.setProperty("https.protocols", "TLSv1.2");
//            HttpsURLConnection.setDefaultSSLSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
            //chatGPT                 //https://api.openai.com/v1/completions
//            HttpPost post = new HttpPost("https://pro-share-aws-api.zcyai.com");
//            //api验证
//            post.addHeader("Content-Type:","application/json");
//            post.addHeader("Authorization:","Bearer "+openAikey);
//
//            String  paramJson ="{\n" +
//                    "   \"model\": \"gpt-3.5-turbo\",\n" +
//                    "   \"messages\": [{\"role\": \"user\", \"content\": \""+question+"\"}],\n" +
//                    "  \"temperature\": 0.7\n" +
//                    " }";
        // 智谱AI
//        ClientV4 httpClient = new ClientV4.Builder("{openAikey}").build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v4/chat/completions");

        post.addHeader("Authorization", "Bearer " + openAikey);
        post.addHeader("Content-Type","application/json");
        String paramJson = "{\n" +
                "    \"model\": \"glm-4\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \""+question+"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}}";

            StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
            post.setEntity(stringEntity);

            CloseableHttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String jsonStr = EntityUtils.toString(response.getEntity());
                logger.info("Raw JSON response: {}", jsonStr);
                AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
                StringBuilder answers = new StringBuilder();
                List<Choices> choices = aiAnswer.getChoices();
                for (Choices choice : choices) {
                    String message = choice.getMessage();
                    // 解析 message 对象，获取 content 字段
                    String content = JSON.parseObject(message).getString("content");
                    answers.append(content);
                }
                return answers.toString();
            } else {
                throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
            }
        }

    }
}
