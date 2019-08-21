package cn.xdf.security.threatnotice.spider;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.persistence.CNNVDPipeline;
import us.codecraft.webmagic.Page;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @ClassName: CNVDSpider
 * @author Secguard
 */
public class CNNVDSpider implements PageProcessor {
	 protected static final Logger logger = LoggerFactory.getLogger(CNNVDSpider.class);

	// 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site
			.me()
//			.addHeader("Host","www.cnnvd.org.cn")
//			.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0")
//			.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n")
//			.addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
//			.addHeader("Accept-Encoding","gzip, deflate, br")
//			.addHeader("Referer","http://www.cnnvd.org.cn/")
//			.addHeader("Connection","keep-alive")
//			.addHeader("Cookie","SESSION=0efbca41-b2d6-4da2-a318-157de07d4c95; topcookie=a6")
//			.addHeader("Upgrade-Insecure-Requests","1")
			.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0")
			.setRetryTimes(3)
			.setSleepTime(5000);
	


	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if (page.getUrl().regex("http://cnnvd.org.cn/web/cnnvdnotice/querylist.tag").match()) {

		// title  /html/body/div[4]/div[1]/div/div[1]/div[2]/ul/li[1]/div[1]/a
	
		page.putField("Title",
				page.getHtml().xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/ul/li[1]/div[1]/a/text()").toString());
		page.putField("Content",
				page.getHtml().xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/ul/li[1]/div[1]/a/text()").toString());

		//  链接
		// /html/body/div[4]/div[1]/div/div[1]/div[2]/ul/li[1]/div[1]/a
		page.putField("Link","http://www.cnnvd.org.cn"+
				page.getHtml().xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/ul/li[1]/div[1]/a/@href").toString());
		 
		//日期 /html/body/div[4]/div[1]/div/div[1]/div[2]/ul/li[1]/div[2]
		page.putField("Date",
				page.getHtml().xpath("/html/body/div[4]/div[1]/div/div[1]/div[2]/ul/li[1]/div[2]/text()").toString());
		}else {
			logger.info("---》CNNVD 目标链接不匹配");
		}
	}
	
	public static void main(String[] args) {
		logger.info("----CNNVDSpiderTask start--------"+new Date().toLocaleString());
		Spider.create(new CNNVDSpider()).addUrl("http://cnnvd.org.cn/web/cnnvdnotice/querylist.tag").addPipeline(new CNNVDPipeline())
	        .thread(1).run();
		logger.info("----CNNVDSpiderTask end--------"+new Date().toLocaleString());
	}

}