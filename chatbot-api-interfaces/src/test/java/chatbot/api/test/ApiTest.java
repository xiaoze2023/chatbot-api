package chatbot.api.test;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.Closeable;
import java.net.HttpCookie;

//单元测试
public class ApiTest {
    @Test
    public void query_unanswered_questions(){
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("");

    }


}
