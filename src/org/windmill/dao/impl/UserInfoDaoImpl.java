package org.windmill.dao.impl;

import org.windmill.dao.UserInfoDao;
import org.windmill.model.UserInfoEntity;
import org.windmill.util.TextUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class UserInfoDaoImpl extends BaseDaoImpl implements UserInfoDao {
    @Override
    public boolean addUserInfo(UserInfoEntity entity) {
        connect();
        String sql="insert into userinfo(uid,phone,email,password) values (?,?,?,?)";
        PreparedStatement ps=null;
        int row=0;
        try {
            ps=this.mConn.prepareStatement(sql);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2,entity.getPhone());
            ps.setString(3,entity.getEmail());
            ps.setString(4, TextUtil.getInstance().encryptByMD5(entity.getPassword()));
            row=ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disConnect();
        return false;
    }

    @Override
    public UserInfoEntity findByUID(String uid) {
        return null;
    }

    @Override
    public boolean deleteUser(UserInfoEntity entity) {
        return false;
    }

    @Override
    public boolean deleteUserByUID(String uid) {
        return false;
    }

    @Override
    public UserInfoEntity userLogin(UserInfoEntity entity) {
        connect();
        String sql="select * from userinfo where (phone=? or email=?) and password=?";
        String sql2="UPDATE userinfo SET token=? WHERE uid=? ";
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
        ResultSet result =null;
        UserInfoEntity rEntity=null;
        try {
            ps=this.mConn.prepareStatement(sql);
            ps.setString(1, entity.getPhone());
            ps.setString(2,entity.getEmail());
            ps.setString(3,TextUtil.getInstance().encryptByMD5(entity.getPassword()));
            result = ps.executeQuery();
            if(result!=null){
                rEntity=new UserInfoEntity();
                while (result.next()){
                    rEntity.setEmail(result.getString("email"));
                    rEntity.setUid(result.getString("uid"));
                    rEntity.setNickname(result.getString("nickname"));
                    rEntity.setPhone(result.getString("phone"));
                    rEntity.setSex(result.getInt("sex"));
                    rEntity.setUage(result.getInt("uage"));
                }
            }
            //生成新的token
            if(rEntity!=null&&rEntity.getUid()!=null&&!"".equals(rEntity.getUid().trim())){
                String token=updateUserToken(rEntity.getUid().trim());
                ps2=this.mConn.prepareStatement(sql2);
                ps2.setString(1, token);
                ps2.setString(2,rEntity.getUid().trim());
                int row=ps2.executeUpdate();
                if(row>0){
                    rEntity.setToken(token);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disConnect();

        return rEntity;
    }

    /**
     * 生成token
     * @param uid
     */
    private String updateUserToken(String uid){
        String token="";
        if(uid!=null&&!"".equalsIgnoreCase(uid.trim())){
            StringBuilder builder=new StringBuilder();
            builder.append(new Date().getTime());
            builder.append(uid.trim());
            token=TextUtil.getInstance().encryptByMD5(builder.toString());
            //更新token
        }
        return token;
    }

    @Override
    public int findUserByPhoneEmail(UserInfoEntity entity) {
        connect();
        String sql1="select count(id) as ucount from userinfo where phone=? or email=?";
        PreparedStatement ps1=null,ps2=null;
        ResultSet result =null;
        int count=0;
        try {
            ps1=this.mConn.prepareStatement(sql1);
            ps1.setString(1,entity.getPhone());
            ps1.setString(2,entity.getEmail());
            result = ps1.executeQuery();
            if(result!=null) {
                while (result.next()) {
                    count=result.getInt("ucount");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disConnect();
        return count;
    }
}
