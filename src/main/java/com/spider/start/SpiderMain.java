package com.spider.start;

import com.spider.api.Spider;
import com.spider.impl.SpiderImpl;
import com.spider.util.SpiderUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class SpiderMain {

    public static Spider spiderImpl = SpiderImpl.getInstance();
    public static SpiderUtil spiderUtil = SpiderUtil.getInstance();

    public static void main(String[] args) throws InterruptedException, IOException {
        try {
            ExecutorService robotExecutor = spiderUtil.getRobotExecutor();
            spiderImpl.moveSpider(args);
            robotExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            SpiderUtil.getInstance().LOGGER.log(Level.INFO, "The number of steps walked by robot is " + spiderUtil.atomicInteger.get());
        } catch (InterruptedException e) {
            SpiderUtil.getInstance().LOGGER.log(Level.INFO, "INTERRUPT");
        }
    }

}
