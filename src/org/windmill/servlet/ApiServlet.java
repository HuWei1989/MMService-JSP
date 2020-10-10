package org.windmill.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.windmill.common.HttpCode;
import org.windmill.common.HttpMethod;
import org.windmill.dao.UserInfoDao;
import org.windmill.dao.impl.UserInfoDaoImpl;
import org.windmill.model.ParamsEntity;
import org.windmill.model.ResponeEntity;
import org.windmill.model.UserInfoEntity;
import com.alibaba.fastjson.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
//@WebServlet(name = "ApiServlet",urlPatterns = "/apiServlet")
public class ApiServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        BufferedReader reader=null;
        StringBuilder builder=new StringBuilder();
        try{
            reader=request.getReader();
            String str=null;
            while ((str=reader.readLine())!=null){
                builder.append(str);
            }
        }catch (Exception ex){

        }finally {
            if(reader!=null){
                reader.close();
            }
        }
        ParamsEntity<UserInfoEntity> mParamsEntity=null;
        try{
            //mParamsEntity= (ParamsEntity<UserInfoEntity>)JSON.parseObject(builder.toString(),ParamsEntity.class);
            mParamsEntity= (ParamsEntity<UserInfoEntity>)JSON.parseObject(builder.toString(),new TypeReference<ParamsEntity<UserInfoEntity>>(){});
        }catch (Exception ex){

        }
        System.out.println("params="+builder.toString());
        Properties ps=new Properties();
        ResponeEntity<Object> entity=new ResponeEntity<>();
        //ServletContext cxt=config.getServletContext();
        //InputStream in=cxt.getResourceAsStream("/WEB-INF/language.properties");
        if(mParamsEntity!=null){
            entity.setCode(HttpCode.CODE_OK);
            if(HttpMethod.USER_REG.equalsIgnoreCase(mParamsEntity.getMethod())){
                //注册用户
                if(mParamsEntity.getData()!=null&&(mParamsEntity.getData() instanceof UserInfoEntity)){
                    UserInfoDao userInfoDao=new UserInfoDaoImpl();
                    userInfoDao.addUserInfo((UserInfoEntity)mParamsEntity.getData());
                }else {
                    entity.setCode(HttpCode.CODE_USER_INFO_ERROR);
                }
            }else if(HttpMethod.USER_LOGIN.equalsIgnoreCase(mParamsEntity.getMethod())){
                //用户登录
                if(mParamsEntity.getData()!=null&&(mParamsEntity.getData() instanceof UserInfoEntity)){
                    UserInfoDao userInfoDao=new UserInfoDaoImpl();
                    UserInfoEntity entity1=userInfoDao.userLogin((UserInfoEntity)mParamsEntity.getData());
                    entity.setData(entity1);
                }else {
                    entity.setCode(HttpCode.CODE_USER_INFO_CODE_PSW_ERROR);
                }
            }else {
                entity.setCode(HttpCode.CODE_METHOD_ERROR);
            }
        }else {
            entity.setCode(HttpCode.CODE_PARAMS_ERROR);
            //entity.setMsg();
        }
        PrintWriter out = response.getWriter();
        out.write(JSONObject.toJSONString(entity));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }


}
