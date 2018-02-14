package com.spider.impl;

import com.spider.api.Spider;
import com.spider.bean.SpiderBean;
import com.spider.util.SpiderUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class SpiderImpl implements Spider {

    public static SpiderImpl spider = new SpiderImpl();
    public static Spider spiderImpl = SpiderImpl.getInstance();
    public static SpiderUtil spiderUtil = SpiderUtil.getInstance();

    private SpiderImpl() {
    }

    public void message(int leg) {
        SpiderUtil.getInstance().LOGGER.log(Level.INFO, "The Spider moved with leg " + leg);
    }

    public boolean checkDestinationReached(SpiderBean spiderBean) {
        return spiderBean.getDistance() >= SpiderUtil.getInstance().atomicInteger.get();
    }

    public void moveSpider(final String[] args) throws IOException {
        final WatchService watchService = spiderUtil.getWatcher();
        final ExecutorService robotExecutor = spiderUtil.getRobotExecutor();
        final Path path = SpiderUtil.getInstance().getPath("/classes");
        path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (SpiderUtil.getInstance().validateUserArguments(args)) {
            monitorConfigFile(robotExecutor,path,watchService,args);
            proceedToWalk(Integer.parseInt(args[0]), Float.parseFloat(args[1]), spiderImpl, robotExecutor, watchService, false);

        }
    }

    private void monitorConfigFile(final ExecutorService robotExecutor, final Path path, final WatchService watchService, final String[] args) {
        robotExecutor.submit(new Callable<Object>() {
            public Object call() throws Exception {
                WatchKey key = spiderUtil.getWatchKey(watchService, robotExecutor);
                int newLegs = 0;
                if (spiderUtil.checkIfFileIsModified(key) ) {
                    key.reset();
                    newLegs = SpiderUtil.getInstance().getLegsFromConfigFile(path);
                    if (newLegs > Integer.parseInt(args[0])) {
                        proceedToWalk(newLegs, Float.parseFloat(args[1]), spiderImpl, robotExecutor, watchService, true);
                    } else {
                        spiderUtil.atomicLegs.set(newLegs);
                    }
                }
                return null;
            }
        });
    }


    public static SpiderImpl getInstance() {
        return spider;
    }

    public int stepCalculator(float steplength, float distance) {
        int steps = 0;
        if (steplength == 0)
            steplength = 1;
        steps = (int) (distance / steplength);
       SpiderUtil.getInstance().LOGGER.log(Level.INFO, "The number of steps required with  average step -" + steplength
                + "  and distance covered is " + Float.valueOf(distance) + "  is : " + steps);
        return steps;
    }

    private void proceedToWalk(int inputLegs, float inputDistance, Spider spiderImpl, ExecutorService robotExecutor, WatchService watchService, boolean oneStep) throws IOException {

        if (!oneStep) {

            SpiderBean spiderBean = new SpiderBean(inputLegs, inputDistance);
            SpiderUtil.getInstance().atomicLegs.set(inputLegs);
            for (int legs = 0; legs < spiderBean.getLegs(); legs++) {
                SpiderTask task = new SpiderTask(legs + 1, spiderBean, spiderImpl, SpiderUtil.getInstance(), robotExecutor, watchService);
                robotExecutor.submit(task);
            }
        } else {

            SpiderBean spiderBean = new SpiderBean(inputLegs, inputDistance);
            int oldLegs=spiderUtil.atomicLegs.get();
            SpiderUtil.getInstance().atomicLegs.set(inputLegs);
            for (int legs = oldLegs; legs <= inputLegs; legs++) {
                SpiderTask task = new SpiderTask(legs + 1, spiderBean, spiderImpl, SpiderUtil.getInstance(), robotExecutor, watchService);
                robotExecutor.submit(task);
            }
        }

    }


}
