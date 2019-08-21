package cn.xdf.threatnotice.security.task;
import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.persistence.CNNVDPipeline;
import cn.xdf.security.threatnotice.spider.CNNVDSpider;
import us.codecraft.webmagic.Spider;



public class CNNVDSpiderTask extends TimerTask{
	protected static final Logger logger = LoggerFactory.getLogger(CNNVDSpiderTask.class);

	@Override
	public void run() {
		logger.info("----CNVDSpiderTask start--------"+new Date().toLocaleString());
		Spider.create(new CNNVDSpider()).addUrl("http://cnnvd.org.cn/web/cnnvdnotice/querylist.tag").addPipeline(new CNNVDPipeline())
	        .thread(1).run();
		logger.info("----CNVDSpiderTask end--------"+new Date().toLocaleString());
	}

   
}