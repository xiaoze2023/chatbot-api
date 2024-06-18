package chatbot.api.test;


import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpCookie;

//单元测试
public class ApiTest {
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/5122415842244144/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie","zsxq_access_token=9D738F2E-9F64-988D-6ED0-E2F1F5E2444D_BFEDBA5CF6420F42; abtest_env=product; zsxqsessionid=c485eac159abce0b17f0d0c47d7591a2; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22585528448842584%22%2C%22first_id%22%3A%2219020de2d02db0-025ebe9ce6a5544-4c657b58-1327104-19020de2d03c40%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTkwMjBkZTJkMDJkYjAtMDI1ZWJlOWNlNmE1NTQ0LTRjNjU3YjU4LTEzMjcxMDQtMTkwMjBkZTJkMDNjNDAiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI1ODU1Mjg0NDg4NDI1ODQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22585528448842584%22%7D%2C%22%24device_id%22%3A%2219020de2d02db0-025ebe9ce6a5544-4c657b58-1327104-19020de2d03c40%22%7D");
        get.addHeader("Content-Type","application/json, text/plain, */*");

        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/2855482142154141/answer");
        post.addHeader("cookie","zsxq_access_token=9D738F2E-9F64-988D-6ED0-E2F1F5E2444D_BFEDBA5CF6420F42; abtest_env=product; zsxqsessionid=c485eac159abce0b17f0d0c47d7591a2; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22585528448842584%22%2C%22first_id%22%3A%2219020de2d02db0-025ebe9ce6a5544-4c657b58-1327104-19020de2d03c40%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTkwMjBkZTJkMDJkYjAtMDI1ZWJlOWNlNmE1NTQ0LTRjNjU3YjU4LTEzMjcxMDQtMTkwMjBkZTJkMDNjNDAiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI1ODU1Mjg0NDg4NDI1ODQifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22585528448842584%22%7D%2C%22%24device_id%22%3A%2219020de2d02db0-025ebe9ce6a5544-4c657b58-1327104-19020de2d03c40%22%7D");
        post.addHeader("Content-Type","application/json, text/plain, */*");

        String patamJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我真不会\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";
        StringEntity stringEntity = new StringEntity(patamJson,ContentType.create("text/json","UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
    }

