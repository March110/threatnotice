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
public class NsfocusPipeline implements Pipeline {
	protected static final Logger logger = LoggerFactory.getLogger(NsfocusPipeline.class);
    public NsfocusPipeline() {}

    public void process(ResultItems resultitems, Task task) {
        Map<String, Object> mapResults = resultitems.getAll();
        
        //数据库持久化
        if(mapResults.size() != 0) {
            ThreatBean tb = new ThreatBean();
            tb.setType(mapResults.get("Type").toString());
            tb.setTitle(mapResults.get("Title").toString());
            tb.setContent(mapResults.get("Content").toString());
            tb.setSrcSite("Nsfocus");
            tb.setLink(mapResults.get("Link").toString());
            tb.setDate(mapResults.get("Date").toString());
            new DBUtil().insert(tb);
        }
    }
    
    
}