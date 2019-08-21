package cn.xdf.security.threatnotice.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;



/**
 * @ClassName: AnquankeSpider
 * @author Secguard
 */
public class AnquankeSpider implements PageProcessor {
	protected static final Logger logger = LoggerFactory.getLogger(AnquankeSpider.class);
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

    public Site getSite() {
        return site;
    }

    public void process(Page page) {

        if (page.getUrl().regex("https://www.anquanke.com/").match()) {
        	
        	//类型 /html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/a/div/div/div/div/span
        	page.putField("Type",page.getHtml().xpath("/html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div[2]/div[1]/a/div/div/div/div/span/text()"));
        	
        	//   /html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[1]/a   title
        	
            page.putField("Title", page.getHtml().xpath("/html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[1]/a/text()").toString());

            //  /html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[3]  Content
            page.putField("Content",page.getHtml().xpath("/html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[3]/text()").toString());
            // /html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[1]/a  链接
            page.putField("Link",page.getHtml().xpath("/html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[1]/a/@href").toString());
            //   /html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[4]/div[1]/span/span  日期
            page.putField("Date",page.getHtml().xpath("/html/body/main/div/div/div[1]/div[2]/div[3]/div[1]/div/div[2]/div/div[4]/div[1]/span/span/text()").toString());
            
        }
    }

}