import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

public class GetRequestMaker implements Runnable{
    private int timesOfExecution;
    private RestTemplate restTemplate;
    private CountDownLatch countDownLatch;
    private String name;
    private static final Logger logger = LoggerSingleton.INSTANCE.getInstance();

    private static final String url = "httpbin.org/get";

    public GetRequestMaker(RestTemplate restTemplate, CountDownLatch countDownLatch, int timesOfExecution, String name) {
        this.countDownLatch = countDownLatch;
        this.restTemplate = restTemplate;
        this.timesOfExecution = timesOfExecution;
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
            logger.info(name + " creating get request: " + LocalTime.now());
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
