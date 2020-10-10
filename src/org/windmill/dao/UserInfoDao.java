package org.windmill.dao;

import org.windmill.model.UserInfoEntity;

public interface UserInfoDao {
    boolean addUserInfo(UserInfoEntity entity);
    UserInfoEntity findByUID(String uid);
    boolean deleteUser(UserInfoEntity entity);
    boolean deleteUserByUID(String uid);
    UserInfoEntity userLogin(UserInfoEntity entity);
}
