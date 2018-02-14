package com.spider.impl;

import com.spider.api.Spider;
import com.spider.bean.SpiderBean;
import com.spider.start.SpiderMain;
import com.spider.util.SpiderUtil;

import java.nio.file.WatchService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class SpiderTask implements Callable<SpiderBean> {

	private int leg;
	private Spider robotImpl;
	private SpiderBean robotBean;
	private SpiderUtil sutil;
	private ExecutorService robotExecutor;
	private WatchService watchService;

	public SpiderTask(int leg, SpiderBean robotBean, Spider robotImpl, SpiderUtil sutil, ExecutorService robotExecutor,
					  WatchService watchService) {
		this.leg = leg; // the leg
		this.robotBean = robotBean; // distance to be covered
		this.robotImpl = robotImpl;
		this.sutil = sutil;
		this.robotExecutor = robotExecutor;
		this.watchService = watchService;
	}

	public SpiderBean call() throws InterruptedException {

		try {

			boolean flag = true;
			while (leg <= sutil.atomicLegs.get()) {
				while (flag) {
					Thread.sleep(1000);
					int value = (sutil.atomicInteger.get() % sutil.atomicLegs.get()) + 1;
					if (leg == value) {

						if (sutil.getAtomicInteger() < (int) robotBean.getDistance()) {
							robotImpl.message(leg);
							sutil.atomicInteger.incrementAndGet();
						} else {
							return robotBean;
						}
					}

					if (sutil.atomicInteger.get() >= (int) robotBean.getDistance()) {
						watchService.close();
						robotExecutor.shutdown();
						return robotBean;
					}

				}
			}

		} catch (Exception e) {
		}
		return robotBean;
	}
}
