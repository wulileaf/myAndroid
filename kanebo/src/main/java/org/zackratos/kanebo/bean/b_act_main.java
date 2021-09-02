package org.zackratos.kanebo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class b_act_main {

    private String name;// 名字
    private int img;// 照片

    @Id(autoincrement = true)
    Long id;

    @Generated(hash = 1756009312)
    public b_act_main(String name, int img, Long id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }

    @Generated(hash = 1849956685)
    public b_act_main() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return this.img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
