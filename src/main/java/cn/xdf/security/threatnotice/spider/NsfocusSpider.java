package cn.xdf.security.threatnotice.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.persistence.NsfocusPipeline;
import us.codecraft.webmagic.Page;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @ClassName: HupuNewsSpider
 * @author LJH
 * @date 2017年11月27日 下午4:54:48
 */
public class NsfocusSpider implements PageProcessor {
	protected static final Logger logger = LoggerFactory.getLogger(NsfocusSpider.class);
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

    public Site getSite() {
        return site;
    }

    public void process(Page page) {

        if (page.getUrl().regex("http://blog.nsfocus.net/category/threatalerts/").match()) {
        	
        	//类别  威胁通告
        	page.putField("Type",new String("威胁通告"));
        }else {
        	//类别 安全分享
        	page.putField("Type",new String("安全分享"));
        }
        
   //   /html/body/div[1]/div[2]/section/main/article[1]/div[2]/header/h2/a   title
        page.putField("Title", page.getHtml().xpath("/html/body/div[1]/div[2]/section/main/article[1]/div[2]/header/h2/a/text()").toString());

        //  /html/body/div[1]/div[2]/section/main/article[1]/div[2]/div/p[1]  Content
        page.putField("Content",page.getHtml().xpath("/html/body/div[1]/div[2]/section/main/article[1]/div[2]/div/p[1]/text()").toString());
        //链接
        page.putField("Link",page.getHtml().xpath("/html/body/div[1]/div[2]/section/main/article[1]/div[2]/header/h2/a/@href").toString());
        //   /html/body/div[1]/div[2]/section/main/article[1]/div[2]/header/div/span[1]/a/time  日期
        page.putField("Date",page.getHtml().xpath("/html/body/div[1]/div[2]/section/main/article[1]/div[2]/header/div/span[1]/a/time/text()").toString());
   
    }

}