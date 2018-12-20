package com.sucetech.yijiamei.kaikaapp.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lihh 线程池
 * 
 */
public class TaskManager {
	private static TaskManager executorServiceManager = null;
	private static ExecutorService executorService;

	private TaskManager() {
		executorService = Executors.newSingleThreadExecutor();
	}
	
	public static final TaskManager getInstance(){
		if (executorServiceManager == null) {
			executorServiceManager = new TaskManager();
		}
		return executorServiceManager;
	}
	
	public void addTask(Runnable runnable){
		executorService.execute(runnable);
	}

	public void closeTaskManager(){
		executorService.shutdownNow();
	}
}
