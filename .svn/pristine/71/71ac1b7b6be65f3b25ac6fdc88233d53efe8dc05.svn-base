package com.vrv.cems.service.updownload.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommomThreadPool {
	public static void joinThreadPool(List<Runnable> rs){
		ExecutorService threadPool = Executors.newFixedThreadPool(10); 
		for(Runnable r : rs){
			threadPool.execute(r);
		}
		threadPool.shutdown();
	}
	public static void joinThreadPool(Runnable r){
		ExecutorService threadPool = Executors.newFixedThreadPool(1);
		threadPool.execute(r);
		threadPool.shutdown();
	}
}
