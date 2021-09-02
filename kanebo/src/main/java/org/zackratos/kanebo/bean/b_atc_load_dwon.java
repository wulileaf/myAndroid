package org.zackratos.kanebo.bean;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

public class b_atc_load_dwon {

    private String name;// 名字
    private String img;// 照片

//    @Id(autoincrement = true)
//    Long id;

//    @Generated(hash = 1756009312)
//    public b_atc_load_dwon(String name, int img, Long id) {
//        this.name = name;
//        this.img = img;
//        this.id = id;
//    }
//
//    @Generated(hash = 1849956685)
//    public b_atc_load_dwon() {
//    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

//    public Long getId() {
//        return this.id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
}
