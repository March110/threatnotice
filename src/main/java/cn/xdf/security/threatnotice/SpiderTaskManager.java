package cn.xdf.security.threatnotice;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.sendmail.EmailTask;
import cn.xdf.threatnotice.security.task.AnquankeSpiderTask;
import cn.xdf.threatnotice.security.task.CNNVDSpiderTask;
import cn.xdf.threatnotice.security.task.FreebufSpiderTask;
import cn.xdf.threatnotice.security.task.NsfocusSpiderTask;

public class SpiderTaskManager {
	
	protected static final Logger logger = LoggerFactory.getLogger(SpiderTaskManager.class);

	public static void main(String[] args) {
		
		
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);//启用两个线程
		
		AnquankeSpiderTask anquankeTask = new AnquankeSpiderTask() ;
		//立即运行，任务消耗10分钟，运行结束后等待2秒。【有空余线程时】，再次运行该任务
		pool.scheduleWithFixedDelay(anquankeTask,0,10,TimeUnit.MINUTES);
		
		// 立即运行，任务消耗，运行结束后等待2秒。【有空余线程时】，再次运行该任务
		FreebufSpiderTask freebufTask  = new FreebufSpiderTask();
		pool.scheduleWithFixedDelay(freebufTask,0,10,TimeUnit.MINUTES);
		
		// 立即运行，任务消耗，运行结束后等待2秒。【有空余线程时】，再次运行该任务
		NsfocusSpiderTask nsfocusTask  = new NsfocusSpiderTask();
		pool.scheduleWithFixedDelay(nsfocusTask,0,15,TimeUnit.MINUTES);
		
		CNNVDSpiderTask cnnvdTask = new CNNVDSpiderTask();
		pool.scheduleWithFixedDelay(cnnvdTask,0,60,TimeUnit.MINUTES);
		
		EmailTask emailTask = new EmailTask();
		pool.scheduleWithFixedDelay(emailTask,0,1,TimeUnit.MINUTES);

	}
}
