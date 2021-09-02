package org.zackratos.kanebo.xml;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


// 中间层
@Root(name = "MsgTypeItem", strict = false)
public class TypeList {

    @ElementList(entry = "MsgTypeItem", inline = true)
    public List<Msg> typeMsg;

    public List<Msg> getList() {
        return typeMsg;
    }

    public void setList(List<Msg> list) {
        this.typeMsg = list;
    }

}
