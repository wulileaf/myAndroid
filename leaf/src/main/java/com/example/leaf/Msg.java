package com.example.leaf;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

// 最底层
@Root(strict = false)
public class Msg {

    @Element(name = "MsgType", required = false)
    public String msgType;

    @Element(name = "MsgInfo", required = false)
    public String msgInfo;

}
