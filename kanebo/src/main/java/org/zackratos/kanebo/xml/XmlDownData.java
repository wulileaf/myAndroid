package org.zackratos.kanebo.xml;


import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "DownDataResult", strict = false)
public class XmlDownData {

    @Element(name = "Type", required = false)
    public String type;

    @Element(name = "isAll", required = false)
    public String isall;

    @Element(name = "NextStartRow", required = false)
    public String nextStartRow;

    @Element(name = "Done", required = false)
    public String done;

    @Element(name = "Success", required = false)
    public int success;

    @Element(name = "TotalCount", required = false)
    public String totalCount;

    @Element(name = "TotalPage", required = false)
    public String totalPage;

    @Element(name = "ClientTable", required = false)
    public String clientTable;


}
