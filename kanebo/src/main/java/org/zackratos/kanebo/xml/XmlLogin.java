package org.zackratos.kanebo.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

// 登录接口xml解析
// 解析单个数据测试功能，能正常获取数据
// 参考资料链接：丢失
@Root(name = "LoginResult", strict = false)
public class XmlLogin {

    @Element(name = "Success", required = false)
    public int success;

    @Element(name = "ErrorMsg", required = false)
    public int errormsg;

    @Element(name = "userId", required = false)
    public String userid;//

    @Element(name = "userName", required = false)
    public String username;

    @Element(name = "IsBA", required = false)
    public String isba;

    @Element(name = "PostName", required = false)
    public String postname;// 职位名称

    @Element(name = "LeaderName", required = false)
    public String leadername;// 上级领导

    @Element(name = "EmpCode", required = false)
    public String empcode;// 当前登录账号

    @Element(name = "OrgName", required = false)
    public String orgname;// 大区

    // 最外层 这个是列表
    @ElementList(entry = "MsgTypeList", required = false, inline = true)
    public List<TypeList> list;

//    @Element(name = "MsgTypeList", required = false)
//    public String list;


}

// 对标接口：http://demo1.acsalpower.com:8888/DataWebService.asmx/UserLogin
// 解析对标数据格式
//<LoginResult
//    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
//            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//            xmlns="http://tempuri.org/">
//<Success>1</Success>
//<CompanyId>0</CompanyId>
//<ServerTime>2021-06-10 09:10:38</ServerTime>
//<userId>3153</userId>
//<userName>测试人员</userName>
//<Mobile/>
//<ControlType>0</ControlType>
//<IsLRL>0</IsLRL>
//<IsTeam>0</IsTeam>
//<IsBA>0</IsBA>
//<PostName>流通专职客代</PostName>
//<LeaderName>测试客户</LeaderName>
//<EmpCode>test301</EmpCode>
//<UserType>4</UserType>
//<RoleName>流通销售主管</RoleName>
//<OrgName>测试大区</OrgName>
//<OfficeName/>
//<CustomerCode>C1234567</CustomerCode>
//<CustomerName>测试客户</CustomerName>
//<SuperCustomerName>测试主管</SuperCustomerName>
//<MsgTypeList>
//<MsgTypeItem>
//<MsgType>GetDictionaryList</MsgType>
//<MsgInfo>下载字典列表</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetPlanList</MsgType>
//<MsgInfo>下载计划列表</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetCirculationStoreListByPlanTest</MsgType>
//<MsgInfo>下载流通网点数据</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetCirculationProductList</MsgType>
//<MsgInfo>下载流通产品列表</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetCirculationCustomerList</MsgType>
//<MsgInfo>下载流通客户数据</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetLTRegionList</MsgType>
//<MsgInfo>下载行政区划数据</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetOfficeList</MsgType>
//<MsgInfo>下载办事处数据</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetDataBaseFileList</MsgType>
//<MsgInfo>下载电子资料</MsgInfo>
//</MsgTypeItem>
//<MsgTypeItem>
//<MsgType>GetMessageList</MsgType>
//<MsgInfo>下载消息数据</MsgInfo>
//</MsgTypeItem>
//</MsgTypeList>
//</LoginResult>
