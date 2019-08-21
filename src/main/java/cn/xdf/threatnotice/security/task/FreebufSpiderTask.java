package cn.xdf.threatnotice.security.task;

import cn.xdf.security.threatnotice.persistence.FreebufPipeline;
import cn.xdf.security.threatnotice.spider.FreebufSpider;

import java.util.Date;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;



/**
 * @ClassName: HupuNewsSpider
 * @author LJH
 * @date 2017年11月27日 下午4:54:48
 */
public class FreebufSpiderTask extends TimerTask {
	protected static final Logger logger = LoggerFactory.getLogger(FreebufSpiderTask.class);

	@Override
	public void run() {
		logger.info("----Freebuf SpiderTask start--------"+new Date().toLocaleString());
		Spider.create(new FreebufSpider()).addUrl("https://www.freebuf.com/").addPipeline(new FreebufPipeline())
          .thread(1).run();
		logger.info("----Freebuf SpiderTask end--------"+new Date().toLocaleString());
	}

    
    

}