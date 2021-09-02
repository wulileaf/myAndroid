package org.zackratos.kanebo.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "StoreListResult", strict = false)
public class XmlGetOutPlanHardwareStoreList {

    @Element(name = "Success", required = false)
    public int success;

    @Element(name = "ErrorMsg", required = false)
    public int errormsg;

//    @Element(name = "ClientTable", required = false)
//    public String storeData;

    @Element(name = "ClientTable", required = false)
    public String storeData;

    @Element(name = "TotalCount", required = false)
    public String totalCount;

    @Element(name = "TotalPage", required = false)
    public String totalPage;
}
