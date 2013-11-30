package com.zzc.activiti.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {
	private static ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	public static ExecutorService getExecutorService(){
		return executorService;
	}
}
