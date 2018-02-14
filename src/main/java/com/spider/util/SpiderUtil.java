package com.spider.util;

import java.util.logging.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Created by armu0517 on 2/14/2018.
 */
public class SpiderUtil {

    public final static Logger LOGGER = Logger.getLogger(SpiderUtil.class.getName());
    private static SpiderUtil spiderUtil = new SpiderUtil();
    public volatile AtomicInteger atomicInteger = new AtomicInteger(0);
    public volatile AtomicInteger atomicLegs = new AtomicInteger(0);
    private static ExecutorService robotExecutor = Executors.newCachedThreadPool();

    public boolean validateUserArguments(String[] args) {
        boolean condition = true;
        Pattern integerPattern = Pattern.compile("[0-9]+");
        Pattern floatPattern = Pattern.compile("^([+-]?\\d*\\.?\\d*)$");
        if (args.length > 0) {
            if (args.length > 2) {
                LOGGER.log(Level.INFO, "ARGUMENTS ARE GREATER THAN 2");
                System.exit(1);
                condition = false;
            } else {
                if (!integerPattern.matcher(args[0]).matches() || !floatPattern.matcher(args[1]).matches()) {
                    LOGGER.log(Level.INFO, "Arguments is wrong format.");
                    System.exit(1);
                    condition = false;
                }
            }
        }
        return condition;
    }

    public Path getPath(String directory) {
        String workingDirectory = System.getProperty("user.dir") + directory;
        File dir = new File(workingDirectory);
        Path path = dir.toPath();
        return path;
    }

    public static int getLegsFromConfigFile(Path path) throws IOException {
        Properties prop = new Properties();
        File file = new File(path + "/spider.cfg");
        InputStream inputStream = new FileInputStream(file);
        prop.load(inputStream);
        return Integer.parseInt(prop.get("legs").toString());
    }


    public WatchKey getWatchKey(WatchService watchService, ExecutorService robotExecutor) {

        try {
            return watchService.take();
        } catch (InterruptedException ex) {
            LOGGER.log(Level.INFO, "Interrupted Exception");
            robotExecutor.shutdown();
            return null;
        } catch (ClosedWatchServiceException C) {
            LOGGER.log(Level.INFO, "ClosedWatchServiceException");
            robotExecutor.shutdown();
            return null;
        }
    }

    public WatchService getWatcher() throws IOException {
        return FileSystems.getDefault().newWatchService();
    }

    public boolean checkIfFileIsModified(WatchKey key) {
        Boolean modifiedFlag = false;
        List<WatchEvent<?>> eventList = null;
        if (key != null) {
            eventList = key.pollEvents();
        }
        if (eventList != null) {
            for (WatchEvent<?> genericEvent : eventList) {
                WatchEvent.Kind<?> kind = genericEvent.kind();
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    modifiedFlag = true;
                    key.reset();
                }
            }
        }
        return modifiedFlag;
    }

    private SpiderUtil() {
    }

    public static SpiderUtil getInstance() {
        return spiderUtil;
    }

    public static ExecutorService getRobotExecutor() {
        return robotExecutor;
    }

    public int getAtomicInteger() {
        return atomicInteger.get();
    }

    public int getAtomicLegs() {
        return atomicLegs.get();
    }

}
