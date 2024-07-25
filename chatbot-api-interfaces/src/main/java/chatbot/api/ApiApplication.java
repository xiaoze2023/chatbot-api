package chatbot.api;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;

//启动类
@SpringBootApplication
public class ApiApplication {
    private Logger logger = LoggerFactory.getLogger(ApiApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class,args);
    }
    @RequestMapping("/verify")
    public ResponseEntity<String> verify(String token) {

        logger.info("验证 token：{}", token);
        return ResponseEntity.status(HttpStatus.OK).body("verify success!");
    }

    @RequestMapping("/success")
    public String success(){
        return "test success by xfg";
    }

}
