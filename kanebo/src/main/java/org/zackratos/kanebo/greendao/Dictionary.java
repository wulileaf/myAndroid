package org.zackratos.kanebo.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "T_Sys_Dictionary")
public class Dictionary {

    @Id(autoincrement = true)
    Long id;
    private String ServerId;
    private String DictId;
    private String DictType;
    private String DictClass;
    private String DictName;
    private String IsDel;
    private String Remark;
    private String IsLock;
    private String DictValue;
    private String ClientType;
    private String FirstLevel;
    private String INT1;

    @Generated(hash = 1738793558)
    public Dictionary(Long id, String ServerId, String DictId, String DictType,
            String DictClass, String DictName, String IsDel, String Remark,
            String IsLock, String DictValue, String ClientType, String FirstLevel,
            String INT1) {
        this.id = id;
        this.ServerId = ServerId;
        this.DictId = DictId;
        this.DictType = DictType;
        this.DictClass = DictClass;
        this.DictName = DictName;
        this.IsDel = IsDel;
        this.Remark = Remark;
        this.IsLock = IsLock;
        this.DictValue = DictValue;
        this.ClientType = ClientType;
        this.FirstLevel = FirstLevel;
        this.INT1 = INT1;
    }

    @Generated(hash = 487998537)
    public Dictionary() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerId() {
        return ServerId;
    }

    public void setServerId(String serverId) {
        ServerId = serverId;
    }

    public String getDictId() {
        return DictId;
    }

    public void setDictId(String dictId) {
        DictId = dictId;
    }

    public String getDictType() {
        return DictType;
    }

    public void setDictType(String dictType) {
        DictType = dictType;
    }

    public String getDictClass() {
        return DictClass;
    }

    public void setDictClass(String dictClass) {
        DictClass = dictClass;
    }

    public String getDictName() {
        return DictName;
    }

    public void setDictName(String dictName) {
        DictName = dictName;
    }

    public String getIsDel() {
        return IsDel;
    }

    public void setIsDel(String isDel) {
        IsDel = isDel;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getIsLock() {
        return IsLock;
    }

    public void setIsLock(String isLock) {
        IsLock = isLock;
    }

    public String getDictValue() {
        return DictValue;
    }

    public void setDictValue(String dictValue) {
        DictValue = dictValue;
    }

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType) {
        ClientType = clientType;
    }

    public String getFirstLevel() {
        return FirstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        FirstLevel = firstLevel;
    }

    public String getINT1() {
        return INT1;
    }

    public void setINT1(String INT1) {
        this.INT1 = INT1;
    }
}
