package cn.xdf.security.threatnotice.sendmail;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.bean.ThreatBean;
import cn.xdf.security.threatnotice.util.DBUtil;

public class EmailTask extends TimerTask{
	protected static final Logger logger = LoggerFactory.getLogger(EmailTask.class);

	@Override
	public void run() {
		DBUtil dbutil = new DBUtil();
		boolean isChange = dbutil.isChange();
		if(isChange) {
			ThreatBean tb = dbutil.findNew();
			try {
				SendMail.sendMessage(tb);
			} catch (Exception e) {
 				e.printStackTrace();
			}
			logger.info("。。。有新增数据。。。");
		}else {
			logger.info("。。。无新增数据。。。");
		}
	}

}
