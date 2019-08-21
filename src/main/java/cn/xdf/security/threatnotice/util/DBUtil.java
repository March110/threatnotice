package cn.xdf.security.threatnotice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdf.security.threatnotice.bean.ThreatBean;


public class DBUtil {
	protected static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private Connection conn;
    
     //1.操作执行语句的对象
    private PreparedStatement ps;
    //2.存放查询操作结果语句的对象
    private ResultSet rs;
    
    private static int baseCount = 0;
    
    public Connection getConn() {
        
        try {
            //1.加载mysql连接到数据库jar包，数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.数据库所在位置以及要访问数据库的名字
            String url = "jdbc:mysql://127.0.0.1:3306/logistics";
            //3.数据库的用户名，密码
            String username = "root";
            String password = "123456";
            //4.使用驱动管理器连接到数据库
            conn = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
             e.printStackTrace();
        } catch (SQLException e) {
             e.printStackTrace();
        }
        return conn;
    }


    //关闭资源的方法
    public void clearUp(Connection conn){
        try {
            if(rs != null){
                rs.close();
            }
            if(ps != null){
                ps.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
 
    
    //增
    public void insert(ThreatBean tb){
       // String sql = "insert into threatnotice (`type`,`title`,`content`,`link`,`srcSite`,`date`) values(?,?,?,?,?)";
        String sql= "INSERT INTO threatnotice(type,title,content,link,srcSite,date) select ?,?,?,?,?,? FROM DUAL WHERE NOT EXISTS(SELECT * FROM threatnotice WHERE title=?)";
        //有连接才能执行语句
        Connection conn = new DBUtil().getConn();
        //在连接中得到执行语句的对象
        try {
            ps = conn.prepareStatement(sql);
            
            ps.setString(1,tb.getType());
            ps.setString(2, tb.getTitle());
            ps.setString(3, tb.getContent());
            ps.setString(4,tb.getLink());
            ps.setString(5,tb.getSrcSite());
            ps.setString(6,tb.getDate());
            ps.setString(7,tb.getTitle());
            int i = ps.executeUpdate();
            conn.close();
            if(i > 0){
            	logger.info("-----插入数据库Success----");
             }else {
            	logger.error("-----数据重复，插入数据库Failed----");
             }
        } catch (SQLException e) {
             e.printStackTrace();
        } finally{
            this.clearUp(conn);
        }
    }
    
    public void setConn(Connection conn) {
        this.conn = conn;
    }
   
    public boolean isChange(){
        
    	boolean isChange = false;
    	String sql = "select MAX(id) from threatnotice";
        //有连接才能执行语句
        Connection conn = new DBUtil().getConn();
        //在连接中得到执行语句的对象
        try {
            ps = conn.prepareStatement(sql);
            //执行
            rs = ps.executeQuery();
            int count= 0;
            if(rs.next()) {
            	count = rs.getInt(1);
            }
            
            if(count>baseCount) {
                baseCount = count;
                isChange = true;
            }else {
            	isChange =  false;
            }
            
        } catch (SQLException e) {
             e.printStackTrace();
        } finally{
            this.clearUp(conn);
        }
        logger.info("baseCount = "+baseCount);
        return isChange;
     }
    
    public ThreatBean findNew() {
    	ThreatBean tb = new ThreatBean();

    	String sql = "select title,content,type,srcSite,link,date from threatnotice where id="+baseCount;
    		
            //有连接才能执行语句
            Connection conn = new DBUtil().getConn();
            //在连接中得到执行语句的对象
            try {
                ps = conn.prepareStatement(sql);
                //执行
                rs = ps.executeQuery();
                if(rs.next()) {
                	 tb.setTitle(rs.getString(1));
                     tb.setContent(rs.getString(2));
                     tb.setType(rs.getString(3));
                     tb.setSrcSite(rs.getString(4));
                     tb.setLink(rs.getString(5));
                     tb.setDate(rs.getString(6));
                }
               
                
            } catch (SQLException e) {
                 e.printStackTrace();
            } finally{
                this.clearUp(conn);
            }
    	
    	return tb;
    }
    
    
    
    
   
}