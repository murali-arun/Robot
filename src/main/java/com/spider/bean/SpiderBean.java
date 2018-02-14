package com.spider.bean;

public class SpiderBean {

	float distance;
	int legs;

	public int getLegs() {
		return legs;
	}

	public float getDistance() {
		return distance;
	}

	public SpiderBean (int legs,float distance){
		this.legs=legs;
		this.distance=distance;
	}
}
