package org.windmill.dao.impl;

import org.windmill.dao.UserInfoDao;
import org.windmill.model.UserInfoEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            ps.setString(4,entity.getPassword());
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
        PreparedStatement ps=null;
        ResultSet result =null;
        UserInfoEntity rEntity=null;
        try {
            ps=this.mConn.prepareStatement(sql);
            ps.setString(1, entity.getPhone());
            ps.setString(2,entity.getEmail());
            ps.setString(3,entity.getPassword());
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disConnect();
        return rEntity;
    }
}
