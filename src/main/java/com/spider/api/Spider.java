package com.spider.api;


import com.spider.bean.SpiderBean;

import java.io.IOException;

public interface Spider {

	int stepCalculator(float randomvalue, float distance);
	void message(int leg);
	boolean checkDestinationReached(SpiderBean spiderBean) ;
	void moveSpider(String[] args ) throws IOException;
}
