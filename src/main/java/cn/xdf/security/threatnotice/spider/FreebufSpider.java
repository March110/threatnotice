package cn.xdf.security.threatnotice.spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.persistence.AnquankePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @ClassName: HupuNewsSpider
 * @author LJH
 * @date 2017年11月27日 下午4:54:48
 */
public class FreebufSpider implements PageProcessor {
	protected static final Logger logger = LoggerFactory.getLogger(FreebufSpider.class);
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(5000);

    public Site getSite() {
        return site;
    }

    public void process(Page page) {

        if (page.getUrl().regex("https://www.freebuf.com/").match()) {
        	//类别 /html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[1]/a/span
        	page.putField("Type",page.getHtml().xpath("/html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[1]/a/span/text()"));
        	
        	//标题 /html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dt/a
            page.putField("Title", page.getHtml().xpath("/html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dt/a/text()").toString());

            //内容 /html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dd[2]
            page.putField("Content",page.getHtml().xpath("/html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dd[2]/text()").toString());
            //链接 /html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dt/a/@href
            page.putField("Link",page.getHtml().xpath("/html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dt/a/@href").toString());
            //日期 /html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dd[1]/span[3]
            page.putField("Date",page.getHtml().xpath("/html/body/div[2]/div[1]/div[1]/div[5]/div[1]/div[2]/dl/dd[1]/span[3]/text()").toString());
            
        }
        
        
        
    }	
}