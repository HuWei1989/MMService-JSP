package org.windmill.dao.impl;

import org.windmill.common.Config;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaseDaoImpl {
    protected Connection mConn=null;
    protected void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动，注册到驱动管理器
            mConn= DriverManager.getConnection(Config.DATABASE_CONNECT,Config.DATABASE_UNAME,Config.DATABASE_PSW);//创建Connection连接
        }catch (Exception ex){

        }
    }

    protected void disConnect(){
        if(mConn!=null){
            try{
                mConn.close();
            }catch (Exception ex){

            }
        }
    }
}
