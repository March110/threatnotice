package cn.xdf.security.threatnotice.persistence;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.bean.ThreatBean;
import cn.xdf.security.threatnotice.util.DBUtil;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

// 自定义实现Pipeline接口
public class FreebufPipeline implements Pipeline {
	protected static final Logger logger = LoggerFactory.getLogger(FreebufPipeline.class);
    public FreebufPipeline() {}

    public void process(ResultItems resultitems, Task task) {
        Map<String, Object> mapResults = resultitems.getAll();
        //数据库持久化
        if(mapResults.size() != 0) {
        	if (mapResults.get("Type") != null && !(mapResults.get("Type").toString().indexOf("头条")>-1 ||mapResults.get("Type").toString().indexOf("人物志")>-1||mapResults.get("Type").toString().indexOf("活动")>-1||mapResults.get("Type").toString().indexOf("视频")>-1||mapResults.get("Type").toString().indexOf("观点")>-1||mapResults.get("Type").toString().indexOf("招聘")>-1 ||mapResults.get("Type").toString().indexOf("公开课")>-1)) {
        		ThreatBean tb = new ThreatBean();
                tb.setType(mapResults.get("Type").toString());
                tb.setTitle(mapResults.get("Title").toString());
                if(mapResults.get("Content")!=null && mapResults.get("Content").toString()!="") {
                	tb.setContent(mapResults.get("Content").toString());
                }else {
					tb.setContent(mapResults.get("Title").toString());
				}
                
                tb.setSrcSite("Freebuf");
                tb.setLink(mapResults.get("Link").toString());
                tb.setDate(mapResults.get("Date").toString());
                new DBUtil().insert(tb);	
			}else {
			
			}
        }

    }
    
    
}