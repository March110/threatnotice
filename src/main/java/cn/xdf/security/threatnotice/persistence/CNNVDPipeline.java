package cn.xdf.security.threatnotice.persistence;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.xdf.security.threatnotice.bean.ThreatBean;
import cn.xdf.security.threatnotice.spider.CNNVDSpider;
import cn.xdf.security.threatnotice.util.DBUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

// 自定义实现Pipeline接口
public class CNNVDPipeline implements Pipeline {
	protected static final Logger logger = LoggerFactory.getLogger(CNNVDSpider.class);

    public CNNVDPipeline() {}

    public void process(ResultItems resultitems, Task task) {
     	
         Map<String, Object> mapResults = resultitems.getAll();

        
      //  Iterator<Entry<String, Object>> iter = mapResults.entrySet().iterator();
      //  Map.Entry<String, Object> entry = null;
        //数据库持久化
        if(mapResults.size() != 0) {
            ThreatBean tb = new ThreatBean();
            tb.setType("漏洞预警");
            tb.setTitle(mapResults.get("Title").toString());
            tb.setContent(mapResults.get("Content").toString());
          
            tb.setSrcSite("CNNVD");
            tb.setLink(mapResults.get("Link").toString());
            tb.setDate(mapResults.get("Date").toString());
            
            new DBUtil().insert(tb);
            logger.info("---》"+mapResults.get("Title"));
        }else {
        	logger.info("----没有抓到CNNVD数据-----");
        }     
   
    }
    
    
}