package org.zackratos.kanebo.bean;

// 当日拜访对应的bean
public class B_Act_DayVisit {

    private String storename;// 网点名称
    private String storecode;// 网点编码
    private String isnewstore;// 是否新网点
    private String contact;// 联系人名称
    private String contactTel;// 联系人电话
    private String storelevelname;// 网点级别
    private String storetypename;// 网点类型
    private String address;// 网点地址
    private Double lat;// 维度
    private Double lng;// 经度

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getStorelevelname() {
        return storelevelname;
    }

    public void setStorelevelname(String storelevelname) {
        this.storelevelname = storelevelname;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getIsnewstore() {
        return isnewstore;
    }

    public void setIsnewstore(String isnewstore) {
        this.isnewstore = isnewstore;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getStoretypename() {
        return storetypename;
    }

    public void setStoretypename(String storetypename) {
        this.storetypename = storetypename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
