import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class LoadTestTool {
    private static final Logger logger = LoggerSingleton.INSTANCE.getInstance();

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        LinkedList<Thread> requestPool = new LinkedList<>();


        int startRequestNum = 10000;
        int currentRequestNum = startRequestNum;
        int endRequestNum = 50_000;
        int times = 10;

        makeGetRequests(currentRequestNum, endRequestNum,  requestPool, restTemplate, times);
//        makePostRequests(currentRequestNum, endRequestNum, requestPool, restTemplate, times);

    }

    private static void makeGetRequests(int currentRequestNum, int endRequestNum,
                                        LinkedList<Thread> requestPool,
                                        RestTemplate restTemplate, int times) {
        while (currentRequestNum <= endRequestNum) {
            logger.info("Current pool size: " + currentRequestNum);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            for (int i = 1; i <= currentRequestNum; i++) {
                requestPool.add(Thread.ofVirtual().start(
                        new GetRequestMaker(restTemplate, countDownLatch, times, "Thread " + i)));
            }
            countDownLatch.countDown();
            for (Thread request : requestPool) {
                try {
                    request.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            requestPool.clear();
            currentRequestNum *= 2;
        }
        logger.info("End executing: " + LocalTime.now());
    }

    private static void makePostRequests(int currentRequestNum, int endRequestNum,
                                         LinkedList<Thread> requestPool,
                                         RestTemplate restTemplate, int times) {
        while (currentRequestNum <= endRequestNum) {
            logger.info("Current pool size: " + currentRequestNum);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            for (int i = 1; i <= currentRequestNum; i++) {
                requestPool.add(Thread.ofVirtual().start(
                        new PostRequestMaker(times, restTemplate, countDownLatch, "Thread " + i)));
            }
            countDownLatch.countDown();
            for (Thread request : requestPool) {
                try {
                    request.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            requestPool.clear();
            currentRequestNum *= 2;
        }
        logger.info("End executing: " + LocalTime.now());
    }
}
