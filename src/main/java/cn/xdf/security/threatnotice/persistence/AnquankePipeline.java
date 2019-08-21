package cn.xdf.security.threatnotice.persistence;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.bean.ThreatBean;
import cn.xdf.security.threatnotice.util.DBUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

// 自定义实现Pipeline接口
public class AnquankePipeline implements Pipeline {
	protected static final Logger logger = LoggerFactory.getLogger(AnquankePipeline.class);

    public AnquankePipeline() {}

    public void process(ResultItems resultitems, Task task) {
        Map<String, Object> mapResults = resultitems.getAll();
      //  Iterator<Entry<String, Object>> iter = mapResults.entrySet().iterator();
      //  Map.Entry<String, Object> entry = null;
        //数据库持久化
        if(mapResults.size() != 0) {
        	if (mapResults.get("Type") != null && !(mapResults.get("Type").toString().indexOf("头条")>-1 ||mapResults.get("Type").toString().indexOf("资讯")>-1||mapResults.get("Type").toString().indexOf("活动")>-1||mapResults.get("Type").toString().indexOf("报告")>-1||mapResults.get("Type").toString().indexOf("观点")>-1||mapResults.get("Type").toString().indexOf("招聘")>-1 ||mapResults.get("Type").toString().indexOf("公开课")>-1||mapResults.get("Type").toString().indexOf("ISC")>-1)) {
        		ThreatBean tb = new ThreatBean();
                tb.setType(mapResults.get("Type").toString());
                tb.setTitle(mapResults.get("Title").toString());
                tb.setContent(mapResults.get("Content").toString());
                tb.setSrcSite("安全客");
                tb.setLink("https://www.anquanke.com"+mapResults.get("Link").toString());
                tb.setDate(mapResults.get("Date").toString());
                
                new DBUtil().insert(tb);
        	}else {
        		logger.info("---其他安全客主题资讯---");
        	}
            
         
        }else {
        	logger.error("----没有抓到安全客数据----");
        }
    }  
    
}