package org.zackratos.kanebo.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "product")
public class ProductBean {

    @Id(autoincrement = true)
    private Long id;
    private String serverId;
    private String productId;
    private String productCode;
    private String fullName;
    private String isNew;
    private String bigClass;
    private String smallClass;
    private String safeStock;
    private String str1;
    @Generated(hash = 1100769492)
    public ProductBean(Long id, String serverId, String productId,
            String productCode, String fullName, String isNew, String bigClass,
            String smallClass, String safeStock, String str1) {
        this.id = id;
        this.serverId = serverId;
        this.productId = productId;
        this.productCode = productCode;
        this.fullName = fullName;
        this.isNew = isNew;
        this.bigClass = bigClass;
        this.smallClass = smallClass;
        this.safeStock = safeStock;
        this.str1 = str1;
    }
    @Generated(hash = 669380001)
    public ProductBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getServerId() {
        return this.serverId;
    }
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
    public String getProductId() {
        return this.productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductCode() {
        return this.productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getFullName() {
        return this.fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getIsNew() {
        return this.isNew;
    }
    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
    public String getBigClass() {
        return this.bigClass;
    }
    public void setBigClass(String bigClass) {
        this.bigClass = bigClass;
    }
    public String getSmallClass() {
        return this.smallClass;
    }
    public void setSmallClass(String smallClass) {
        this.smallClass = smallClass;
    }
    public String getSafeStock() {
        return this.safeStock;
    }
    public void setSafeStock(String safeStock) {
        this.safeStock = safeStock;
    }
    public String getStr1() {
        return this.str1;
    }
    public void setStr1(String str1) {
        this.str1 = str1;
    }
}
