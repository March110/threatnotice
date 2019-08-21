package cn.xdf.threatnotice.security.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.xdf.security.threatnotice.persistence.NsfocusPipeline;
import cn.xdf.security.threatnotice.spider.NsfocusSpider;
import java.util.Date;
import java.util.TimerTask;
import us.codecraft.webmagic.Spider;


/**
 * @ClassName: HupuNewsSpider
 * @author LJH
 * @date 2017年11月27日 下午4:54:48
 */
public class NsfocusSpiderTask extends TimerTask {
	protected static final Logger logger = LoggerFactory.getLogger(FreebufSpiderTask.class);

	@Override
	public void run() {
		logger.info("----Nsfocus SpiderTask start--------"+new Date().toLocaleString());
    	String[] urls = new String[] {"http://blog.nsfocus.net/category/threatalerts/","http://blog.nsfocus.net/category/safeshare/"};

		Spider.create(new NsfocusSpider()).addUrl(urls).addPipeline(new NsfocusPipeline())
         .thread(1).run();
		logger.info("----Nsfocus SpiderTask end--------"+new Date().toLocaleString());
	}

   
    


}