package org.zackratos.kanebo.greendao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "DB_USERMSG")
public class UserMsg {

    @Id(autoincrement = true)
    Long id;
    public String userName;
    public String userId;
    public String userSex;
    public String userCode;
    @Generated(hash = 1005346378)
    public UserMsg(Long id, String userName, String userId, String userSex,
            String userCode) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.userSex = userSex;
        this.userCode = userCode;
    }
    @Generated(hash = 500592789)
    public UserMsg() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserSex() {
        return this.userSex;
    }
    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
    public String getUserCode() {
        return this.userCode;
    }
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
