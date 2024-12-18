import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class PostRequestMaker implements Runnable{
    private static final Gson jsonConverter = new Gson();
    private int timesOfExecution;
    private RestTemplate restTemplate;
    private CountDownLatch countDownLatch;
    String name;

    private static final String url = "http://foo-ec2-public-lb-2620953089-1374846133.eu-west-1.elb.amazonaws.com:8080/users/";
//    private static final String url = "http://foo-dev-public-lb-2620953089-983412671.eu-west-1.elb.amazonaws.com/users/";
    private static final Logger logger = LoggerSingleton.INSTANCE.getInstance();

    public PostRequestMaker(int timesOfExecution, RestTemplate restTemplate, CountDownLatch countDownLatch, String name) {
        this.timesOfExecution = timesOfExecution;
        this.restTemplate = restTemplate;
        this.countDownLatch = countDownLatch;
        this.name = name;
    }

    @Override
    public void run() {
        try{
            countDownLatch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for(int i = 1; i<=timesOfExecution; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            logger.info(name + " creating  post request: " + LocalTime.now());

            String json = jsonConverter.toJson(new User( "Andrew " + i, "123123","load"));
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
            //logger.info(responseEntity.getBody());
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
