package org.zackratos.kanebo.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class StoreMsg {

    @Element(name = "Fullname", required = false)
    public String fullname;

    @Element(name = "StoreId", required = false)
    public String storeId;
}
