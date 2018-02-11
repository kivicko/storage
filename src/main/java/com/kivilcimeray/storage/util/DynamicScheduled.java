package com.kivilcimeray.storage.util;

import java.lang.annotation.Annotation;

import org.springframework.scheduling.annotation.Scheduled;

public class DynamicScheduled implements Scheduled {
	long fixedDelay;
	
	public DynamicScheduled(long fixedDelayValue) {
		fixedDelay = fixedDelayValue;
	}
	
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String cron() {
		// TODO Auto-generated method stub
		return null;
	}

	public String zone() {
		// TODO Auto-generated method stub
		return null;
	}

	public long fixedDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String fixedDelayString() {
		// TODO Auto-generated method stub
		return null;
	}

	public long fixedRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String fixedRateString() {
		// TODO Auto-generated method stub
		return null;
	}

	public long initialDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String initialDelayString() {
		// TODO Auto-generated method stub
		return null;
	}

}
