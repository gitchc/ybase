package com.yonyou.mde.web.script;

import cn.hutool.db.Entity;
import com.yonyou.mde.web.script.Utils.DB;
import com.yonyou.mde.web.core.ScriptException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenghch
 * 脚本接口
 * 2020/12/16
 */
public interface IScript {

    public Map<String, Object> execute(Map<String, Object> vars) throws Exception;

    public long getVersion();
    public void setVersion(long version);
    /**
     * 方法说明： 创建维度成员
     * <br>代码示例:
     * MemberCreate("测试维度","测试维度成员","");
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @param Pmember    父项节点，为空或者传入dimName，增加在维度根节点
     * @return memberid 维度成员id
     */
    public Long MemberAdd(String dimName, String memberName, String Pmember);


    /**
     * 方法说明： 移动成员，只允许同维度之间移动
     * <br>代码示例:
     * <br>MemberMove("测试维度","测试维度成员","");
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @param Pmember    父项节点，为空或者传入dimName，移动到维度根节点
     * @return true/false
     */
    public boolean MemberMove(String dimName, String memberName, String Pmember);

    /**
     * 方法说明： 删除维度成员
     * <br>代码示例:
     * <br>MemberDestroy("测试维度","测试维度成员");
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @return true/false
     */
    public boolean MemberDelete(String dimName, String memberName);


    /**
     * 方法说明： 判断成员是否存在
     * <br>代码示例:
     * <br>MemberExists("测试维度","测试维度成员");
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @return true/false
     */
    public boolean MemberExists(String dimName, String memberName);


    /**
     * 方法说明： 判断维度存不存在
     * <br>代码示例:
     * <br>DimensionExists("测试维度");
     *
     * @param dimName 维度名称
     * @return true/false
     */
    public boolean DimensionExists(String dimName);

    /**
     * 方法说明： 创建维度
     * <br>代码示例:
     * <br>DimensionCreate("测试维度");
     *
     * @param dimName 维度名称
     * @return true/false
     */
    public boolean DimensionAdd(String dimName);

    /**
     * 方法说明： 删除维度
     * <br>代码示例:
     * <br>DimensionDestroy("测试维度");
     *
     * @param dimName 维度名称
     * @return true/false
     */
    public boolean DimensionDestroy(String dimName);


    /**
     * 方法说明： 取值函数同CellGetS，仅返回类型不同
     * <br>代码示例:
     * <br>Double value = CellGet("测试cube","维度a成员,维度b成员，维度c成员");
     *
     * @param cubeName 模型名称
     * @param path     路径，需要按照模型的维度顺序传入，例"a,b,c,d"
     * @return double类型值
     */
    public String CellGet(String cubeName, String path);

    public String CellGet(String cubeName, String... paths);


    /**
     * 方法说明： 批量取值函数同CellsGet
     * <br>代码示例:
     * <br>String[] paths = new String[]{"a","b"};
     * List<String[]> allPaths = new ArrayList<>();
     * allPaths.add(paths);
     * <br>List<String> values = CellsGet("测试cube",allPaths);
     *
     * @param cubeName 模型名称
     * @param paths    路径，需要按照模型的维度顺序传入
     * @return Stirng类型值
     */
    public List<String> CellsGet(String cubeName, List<String[]> paths);

    /**
     * 方法说明： 赋值函数
     * <br>代码示例:
     * <br>CellSet("测试cube","维度a成员,维度b成员，维度c成员",123);
     *
     * @param cubeName 模型名称
     * @param path     路径，需要按照模型的维度顺序传入，例"a,b,c,d"
     * @param value    可以传double,long,String,int类型
     */
    public void CellSet(String cubeName, String path, Object value);

    /**
     * 方法说明： 清空cube数据
     * <br>代码示例:
     * <br>CubeClearData("测试cube");
     *
     * @param cubename 模型名称
     * @return true/false
     */
    public boolean CubeClearData(String cubename);

    /**
     * 方法说明： 判断cube是否存在
     * <br>代码示例:
     * <br>CubeExists("测试cube");
     *
     * @param cubeName 模型名称
     * @return true/false
     */
    public boolean CubeExists(String cubeName);

    /**
     * 方法说明： 创建cube
     * <br>代码示例:
     * <br>CubeCreate("testcube","A","B","C");
     * <br>或者
     * <br>CubeCreate("testcube",new String[]{"A","B","C"});
     *
     * @param cubeName 模型名称
     * @param dims     维度名称组合
     * @return true/false
     */
    public boolean CubeCreate(String cubeName, String... dims);

    /**
     * 把对象转成Json字符串
     *
     * @param object
     * @return
     */
    public String ToJSON(Object object);

    /**
     * 判断变量是否正确替换或者替换为空
     *
     * @param object 判断的对象
     * @return true/false
     */
    public boolean IsNull(Object object);

    /**
     * 方法说明： 时间函数 Date转字符串
     * <br>代码示例:
     * <br>String datestr = DateFormat(new Date(),"yyyy-MM-dd");
     *
     * @param date   时间格式
     * @param format 格式化字符串，yyyy-MM-dd
     * @return 值
     */
    public String DateFormat(Date date, String format);

    /**
     * 方法说明： 时间函数 字符串转Date
     * <br>代码示例:
     * <br>Date date = StrToDate("2012-02-12","yyyy-MM-dd");
     *
     * @param date   时间格式
     * @param format 格式化字符串，yyyy-MM-dd
     * @return Date类型的值
     */
    public Date StrToDate(String date, String format);


    /**
     * 方法说明： 执行活动脚本
     * <br>代码示例:
     * <br>RunScript("ScriptName",new HashMap());
     *
     * @param scriptName 活动脚本名称
     * @param params     参数 new String[]{"a","b"}
     * @return 成功：success,失败：错误原因
     */
    public String RunScript(String scriptName, Map<String, Object> params);

    /**
     * 方法说明： 执行活动脚本
     * <br>代码示例:
     * <br>RunScript("ScriptName");
     *
     * @param scriptName 活动脚本名称
     * @return 成功：success,失败：错误原因
     */
    public String RunScript(String scriptName);


    /**
     * 方法说明： 执行服务器上bat文件
     * <br>代码示例:
     * <br>//linux:
     * <br>String cmdPath="/export/home/text.sh";
     * <br>//win:
     * <br>String cmdPath = "D:\\test.bat";
     * <br>RunCmd(cmdPath,"参数");
     *
     * @param cmdPath 文件地址
     * @return 执行信息
     */
    public String RunCmd(String cmdPath, String... params);

    /**
     * 方法说明： 返回信息
     * <br>代码示例:
     * <br>Print("我要打印这句话！");
     *
     * @param object 打印信息
     */
    public void Print(Object object);


    /**
     * 方法说明： 用于脚本的返回值
     * <br>代码示例:
     * <br>SetValues("result","我只返回这句话！");
     *
     * @param object 打印信息
     */
    public void SetValues(String key, Object object);


    /**
     * 方法说明： 创建oracle数据源
     * <br>代码示例:
     * <br>String url = "jdbc:oracle:thin:@192.168.2.31:1521:orcl";
     * <br>String user = "plnconsole";
     * <br>String password = "plnconsole";
     * <br>DB Connect = CreateOracleConnect(url,user,password);
     * <br>List &lt;Entity&gt; rets = Connect.query("select * from table");
     * <br>for(Object[] ret : rets){
     * <br>    String colum1 = ret[0].toString();
     * <br>    String colum2 = ret[1].toString();
     * <br>}
     * <br>boolean rst = Connect.excute("update table set a='1'");
     *
     * @param url      数据源地址
     * @param user     登录用户名
     * @param password 密码
     * @return DB对象，包含两个方法:查询[query(sql)-返回值List &lt;Object[]&gt;];执行：excute(sql);
     */
    public DB CreateDB(String url, String user, String password);

    /**
     * 方法说明： 向指定URL发送POST方法的请求，使用的发送格式为"Content-type", "application/x-www-form-urlencoded"
     * <br>代码示例:
     * <br>String resStrs = SendPost("http://www.ttt.com","a=1&b=1");
     * 或者
     * <br>String resStrs = SendPost("http://www.ttt.com","{\"a\":\"1\"}");
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是键对值json格式"{\"a\":\"1\"}"或者字符串a=1&b=1形式;
     * @return 所代表远程资源的响应结果
     */
    public String SendPost(String url, String params);

    /**
     * 方法说明： 向指定URL发送POST方法的请求，使用的发送格式为"Content-type", "application/x-www-form-urlencoded"
     * <br>代码示例:
     * <br>Map<String,String>headers = new HashMap();
     * headers.put("key","value");
     * <br>String resStrs = SendPost("http://www.ttt.com","a=1&b=1",headers);
     * 或者
     * <br>String resStrs = SendPost("http://www.ttt.com","{\"a\":\"1\"}",headers);
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是键对值json格式"{\"a\":\"1\"}"或者字符串a=1&b=1形式;
     * @return 所代表远程资源的响应结果
     */
    public String SendPost(String url, String params, Map<String, String> headers);

    /**
     * 方法说明： 向指定URL发送POST方法的请求,使用的发送格式为"Content-Type", "application/json;charset=utf-8"
     * <br>代码示例:
     * <br>Map<String,String>headers = new HashMap();
     * headers.put("key","value");
     * <br>String resStrs = SendPost("http://www.ttt.com","{\"a\":\"1\"}",headers);
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是键对值json格式"{\"a\":\"1\"}";
     * @return 所代表远程资源的响应结果
     */
    public String SendPostJSON(String url, String params, Map<String, String> headers);

    /**
     * 方法说明： 向指定URL发送Get方法的请求
     * <br>代码示例:
     * <br>String resStrs = SendGet("http://www.ttt.com?a=11&amp;b=2");
     *
     * @param url 发送请求的URL
     * @return 所代表远程资源的响应结果
     */
    public String SendGet(String url);

    /**
     * 方法说明： 调用webservice请求
     * <br>代码示例:
     * <br>String resStrs = SendWs("http://www.ttt.com.ws"," &lt;tw1:TransName&gt;name &lt;/tw1:TransName&gt;","getName.v1");
     *
     * @param url    发送请求的URL
     * @param param  soap参数
     * @param method 方法名
     * @return 所代表远程资源的响应结果
     */
    public String SendWs(String url, String param, String method);

    /**
     * 方法说明： 获取属性值
     * <br>代码示例:
     * <br>String color = AttrValue("测试维度","测试维度成员","颜色");
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @param attrName   属性名称
     * @return 属性值
     */
    public String AttrValue(String dimName, String memberName, String attrName);

    /**
     * 方法说明： 删除属性
     * <br>代码示例:
     * <br>AttrDelete("测试维度","颜色");
     *
     * @param dimName  维度名称
     * @param attrName 属性名称
     * @return true/false
     */
    public boolean AttrDelete(String dimName, String attrName);

    /**
     * 方法说明： 插入属性
     * <br>代码示例:
     * <br>AttrInsert("测试维度","颜色");
     *
     * @param dimName  维度名称
     * @param attrName 属性名称
     * @return true/false
     */
    public boolean AttrInsert(String dimName, String attrName);

    /**
     * 方法说明： 插入属性值
     * <br>代码示例:
     * <br>AttrPut("测试维度","测试维度成员","颜色","红色");
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @param attrName   属性名称
     * @param value      属性值
     * @return true/false
     */
    public boolean AttrPut(String dimName, String memberName, String attrName, String value);

    /**
     * 方法说明： 读取文件夹内容
     * <br>代码示例:
     * <br>String textValue = ReadFromFile("D:\\file\\test.txt");
     *
     * @param filepath 文件地址
     * @return String类型的文本内容
     */
    public String ReadFromFile(String filepath);

    /**
     * 方法说明： 写入文件，文件不存在则会创建
     * <br>代码示例:
     * <br>WriteToFile("把这段内容写到文件里面","D:\\file\\test.txt");
     *
     * @param content  写入内容
     * @param filepath 文件地址
     * @return true/false
     */
    public boolean WriteToFile(String content, String filepath);

    /**
     * 方法说明： 追加写入文件，文件不存在则会创建
     * <br>代码示例:
     * <br>AppendToFile("把这段内容写到文件里面","D:\\file\\test.txt");
     *
     * @param content  写入内容
     * @param filepath 文件地址
     * @return true/false
     */
    public boolean AppendToFile(String content, String filepath);

    /**
     * 方法说明： 文件是否存在
     * <br>代码示例:
     * <br>FileExists("D:\\file\\test.txt");
     *
     * @param filepath 例如：D:\\file\\test.txt
     * @return true/false
     */
    public boolean FileExists(String filepath);

    /**
     * 方法说明： 数据复制
     * <br>代码示例:
     * <br>String copyrest = VersionCopy("测试cube","年","2016年","2017年");
     *
     * @param cubeCode 多维模型编码
     * @param dimName  维度名称
     * @param smember  源维度成员名称
     * @param tmember  目标维度成员名称
     * @return 数据复制信息
     */
    public String VersionCopy(String cubeCode, String dimName, String smember, String tmember);


    /**
     * 方法说明：获取所有Cube名称
     * <br>代码示例:
     * <br>List names = GetCubeNames();
     *
     * @return cube名称集合
     */
    public List<String> GetCubeNames();

    /**
     * 方法说明：获取所有维度名称
     * <br>代码示例:
     * <br>List names = GetDimNames();
     *
     * @return dim名称集合
     */
    public List<String> GetDimNames();


    /**
     * 清除指定Cube的数据
     *
     * @param cubecode cube编码
     * @return 状态
     */
    public boolean ClearCubeData(String cubecode);

    /**
     * 当前项目Sql查询
     *
     * @param sql    sql语句
     * @param values 参数
     * @return 数据对象
     */
    public List<Entity> QuerySql(String sql, Object... values);

    /**
     * 当前项目 执行Sql
     *
     * @param sql    sql语句
     * @param values 参数
     */
    public void ExecuteSql(String sql, Object... values);

    /**
     * 当前项目 批量执行Sql
     *
     * @param sqls 多条sql
     */
    public void ExecuteSqls(List<String> sqls);


    /**
     * 获取维度成员
     *
     * @param dimName 维度名称
     * @param mdxStr  表达式，为空取所有,参考操作表单参数介绍
     * @return 返回维度成员默认名称
     */
    public List<String> GetMembers(String dimName, String... mdxStr);

    /**
     * 根据维度名称，成员名称获取维度id
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @return 维度id
     */
    public String getMemberid(String dimName, String memberName);

    /**
     * 根据维度名称/agcode，成员名称获取维度默认名称
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @return 维度id
     */
    public String getMemberName(String dimName, String memberName);

    /**
     * 根据维度名称/agcode，成员名称获取父项默认名称
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @return 维度id
     */
    public String getParentMemberName(String dimName, String memberName);

    /**
     * 判断维度成员是不是叶子节点
     *
     * @param dimName    维度名称
     * @param memberName 成员名称
     * @return true/false
     */
    public boolean isLeaf(String dimName, String memberName);

    /**
     * 利用系统内部邮件配置发送邮件
     *
     * @param address      收信人地址，如果有多个地址已逗号“，”分隔
     * @param otherAddress 知会人地址，如果有多个地址已逗号“，”分隔
     * @param title        邮件主题
     * @param content      邮件内容
     * @param filesPath    附件地址
     * @return
     */
    public boolean SendMail(String address, String title,
                            String content, String otherAddress, String... filesPath);

    //根据条件合并组
    public List<String[]> MergeGroups(List<String[]> choicelist);

    //根据切片取值
    public List<String> CellsGetBySlices(String cubeName, List<String[]> slices);

    //根据切片求笛卡尔积
    public List<String[]> GetPathsBySlices(List<String[]> slices);


    public void TabaseMetaToPLN();

    /**
     * 刷新指定维度的或者表单的排序
     *
     * @param name
     * @return
     */
    public void FreshUniPosition(String name);

    /**
     * 刷新指定维度的或者表单的排序
     *
     * @param name
     * @return
     */
    public void FreshUniCode(String name);


    /**
     * 随机造数,1到10000,只能给数字单元格造数
     *
     * @param cubeCode 多维库编码
     * @param size     总共造多少数
     */
    public void MockRandomData(String cubeCode, int size);

    /**
     * 随机造数,1到10000,只能给数字单元格造数
     *
     * @param cubeCode 多维库编码
     * @param size     总共造多少数
     * @param value    固定值
     */
    public void MockData(String cubeCode, int size, double value);


}
