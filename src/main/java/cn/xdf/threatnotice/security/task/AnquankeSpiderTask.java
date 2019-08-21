package cn.xdf.threatnotice.security.task;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.persistence.AnquankePipeline;
import cn.xdf.security.threatnotice.spider.AnquankeSpider;
import us.codecraft.webmagic.Spider;



public class AnquankeSpiderTask extends TimerTask{
	protected static final Logger logger = LoggerFactory.getLogger(AnquankeSpiderTask.class);
	@Override
	public void run() {
		logger.info("----anquankeSpiderTask start--------"+new Date().toLocaleString());
		Spider.create(new AnquankeSpider()).addUrl("https://www.anquanke.com/").addPipeline(new AnquankePipeline())
	        .thread(1).run();
		logger.info("----anquankeSpiderTask end--------"+new Date().toLocaleString());
	}

    
}