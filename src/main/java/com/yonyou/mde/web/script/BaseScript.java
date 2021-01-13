package com.yonyou.mde.web.script;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.extra.spring.SpringUtil;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.script.utils.ShellUtil;
import com.yonyou.mde.web.script.utils.DB;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.DataService.MockDataManager;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.service.ScriptService;
import com.yonyou.mde.web.utils.SnowID;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.script.ScriptException;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@Log4j2
public class BaseScript implements IScript {
    @Resource
    CubeService cubeService;
    @Resource
    MockDataManager mockDataManager;
    @Resource
    MemberService memberService;
    @Resource
    DataSourceConfig dataSourceConfig;
    @Resource
    ScriptService scriptService;

    private Map<String, Object> vars;
    private long version;
    private Map<String, Object> results = new HashMap<>();

    public BaseScript() {//动态注入注解
        Field[] fields = BaseScript.class.getDeclaredFields();
        List<Field[]> fieldlist = new ArrayList<>();
        fieldlist.add(fields);
        Class scln = BaseScript.class.getSuperclass();
        while (!scln.equals(Object.class)) {
            fieldlist.add(scln.getDeclaredFields());
            scln = scln.getSuperclass();
        }
        for (Field[] fieldy : fieldlist) {
            for (Field field : fieldy) {
                Resource at = field.getAnnotation(Resource.class);
                if (at != null) {
                    Class cln = field.getType();
                    Object object = SpringUtil.getBean(cln);
                    field.setAccessible(true);
                    try {
                        field.set(this, object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    field.setAccessible(false);
                }
            }
        }
    }

    @Override
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public <T> T $(String key) {
        if (vars == null) {
            return null;
        }
        return (T) vars.get(key);
    }

    public void initVars(Map<String, Object> vars) {
        this.vars = vars;
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> vars) throws Exception {
        return null;
    }

    private String getid(String dimid, String memberName) {
        return memberService.getMemberIdByCode(dimid, memberName);
    }

    private String getDimid(String dimname) throws MdeException {
        String dimid = memberService.getDimidByCode(dimname);
        if (StringUtils.isBlank(dimid)) {
            throw new MdeException(StrUtil.format("[{}]不存在", dimname));
        }
        return dimid;
    }

    @Override
    public String MemberAdd(String dimName, String memberName, String Pmember) throws MdeException {
        Member member = new Member();
        String dimid = getDimid(dimName);
        if (StringUtils.isNotBlank(Pmember)) {
            String pid = getid(dimid, Pmember);
            member.setPid(pid);
        }
        member.setId(SnowID.nextID());
        member.setDimid(dimid);
        member.setName(memberName);
        member.setCode(memberName);
        member.setMembertype(1);
        return memberService.insertMember(member);
    }

    @Override
    public boolean MemberMove(String dimName, String memberName, String Pmember) {
        return false;
    }

    @Override
    public boolean MemberDelete(String dimName, String memberName) {
        return false;
    }

    @Override
    public boolean MemberExists(String dimName, String memberName) {
        return false;
    }

    @Override
    public boolean DimensionExists(String dimName) {
        return memberService.getDimidByCode(dimName) != null;
    }

    @Override
    public boolean DimensionAdd(String dimName) {
        Member dim = new Member();
        dim.setCode(dimName);
        dim.setName(dimName);
        dim.setDatatype(10);
        try {
            memberService.insertDim(dim);
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean DimensionDestroy(String dimName) {
        String dimid = null;
        try {
            dimid = getDimid(dimName);
        } catch (MdeException e) {
            e.printStackTrace();
            return false;
        }
        memberService.delDim(dimid);
        return true;
    }

    @Override
    public String CellGet(String cubeName, String path) {
        return null;
    }

    @Override
    public String CellGet(String cubeName, String... paths) {
        return null;
    }

    @Override
    public List<String> CellsGet(String cubeName, List<String[]> paths) {
        return null;
    }

    @Override
    public void CellSet(String cubeName, String path, Object value) {

    }

    @Override
    public boolean CubeClearData(String cubename) {
        return false;
    }

    @Override
    public boolean CubeExists(String cubeName) {
        return false;
    }

    @Override
    public boolean CubeCreate(String cubeName, String... dims) {
        return false;
    }

    @Override
    public String ToJSON(Object object) {
        return null;
    }

    @Override
    public boolean IsNull(Object object) {
        if (object == null) {
            return true;
        }
        return StringUtils.isBlank(object.toString());
    }

    @Override
    public String DateFormat(Date date, String format) {
        return DateUtil.format(date, format);
    }

    @Override
    public Date StrToDate(String date, String format) {
        if (StringUtils.isBlank(format)) {
            return DateUtil.parse(date);
        }
        return DateUtil.parse(date, format);
    }

    public int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);                    //放入Date类型数据
        return calendar.get(Calendar.YEAR);                    //获取年份
    }

    public Date getDate(long time) {
        Date date = DateUtil.date(time);
        return date;
    }

    public long getTime(Date date) {
        return date.getTime();
    }

    public int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);                    //放入Date类型数据
        return calendar.get(Calendar.MONTH);                    //获取月

    }

    public int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);                    //放入Date类型数据
        return calendar.get(Calendar.DATE);                    //获取天

    }

    @Override
    public Map<String, Object> RunScript(String scriptName, Map<String, Object> params) {
        return scriptService.run(scriptName, params);
    }

    @Override
    public Map<String, Object> RunScript(String scriptName) {
        return scriptService.run(scriptName, null);
    }

    @Override
    public String RunCmd(String filepath, String... params) throws ScriptException {
        if (FileExists(filepath)) {
            return ShellUtil.runCMD(filepath + " " + StringUtils.join(params, " "));
        } else {
            throw new ScriptException("[" + filepath + "]文件不存在!");
        }
    }

    @Override
    public void Print(Object object) {
        log.info(object.toString());
        results.put("msg", object.toString());
    }

    @Override
    public void SetValues(String key, Object object) {
        results.put(key, object);
    }

    @Override
    public DB CreateDB(String url, String user, String password) {
        Db db = Db.use(new SimpleDataSource(url, user, password));
        return new DB(db);
    }


    @Override
    public String SendPost(String url, String params, Map<String, String> headers) {

        return null;
    }

    @Override
    public String SendPostJSON(String url, String params, Map<String, String> headers) {
        return null;
    }

    @Override
    public String SendGet(String url) {
        return null;
    }


    @Override
    public String AttrValue(String dimName, String memberName, String attrName) {
        return null;
    }

    @Override
    public boolean AttrDelete(String dimName, String attrName) {
        return false;
    }

    @Override
    public boolean AttrInsert(String dimName, String attrName) {
        return false;
    }

    @Override
    public boolean AttrPut(String dimName, String memberName, String attrName, String value) {
        return false;
    }

    @Override
    public String ReadFromFile(String filepath) throws ScriptException {
        if (FileExists(filepath)) {
            FileReader reader = new FileReader(new File(filepath));
            return reader.readString();
        } else {
            throw new ScriptException("[" + filepath + "]文件不存在!");
        }

    }

    @Override
    public boolean WriteToFile(String content, String filepath) throws ScriptException {
        if (FileExists(filepath)) {
            FileWriter writer = new FileWriter(new File(filepath));
            writer.write(content);
        } else {
            throw new ScriptException("[" + filepath + "]文件不存在!");
        }
        return true;
    }

    @Override
    public boolean AppendToFile(String content, String filepath) throws ScriptException {
        if (FileExists(filepath)) {
            FileWriter writer = new FileWriter(new File(filepath));
            writer.append(content);
        } else {
            throw new ScriptException("[" + filepath + "]文件不存在!");
        }
        return true;
    }

    @Override
    public boolean FileExists(String filepath) {
        return FileUtil.exist(filepath);
    }

    @Override
    public String VersionCopy(String cubeCode, String dimName, String smember, String tmember) {
        return null;
    }

    @Override
    public List<String> GetCubeNames() {
        return null;
    }

    @Override
    public List<String> GetDimNames() {
        return null;
    }

    @Override
    public boolean ClearCubeData(String cubecode) {
        return false;
    }

    @Override
    public List<Entity> QuerySql(String sql, Object... values) {
        return CreateDB(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword()).query(sql, values);
    }

    @Override
    public void ExecuteSql(String sql) {
        memberService.executeSql(sql);
    }

    @Override
    public void ExecuteSqls(List<String> sqls) {
        for (String sql : sqls) {
            memberService.executeSql(sql);
        }

    }

    @Override
    public List<String> GetMembers(String dimName, String... mdxStr) {
        return null;
    }

    @Override
    public String getMemberid(String dimName, String memberName) {
        return null;
    }

    @Override
    public String getMemberName(String dimName, String memberName) {
        return null;
    }

    @Override
    public String getParentMemberName(String dimName, String memberName) {
        return null;
    }

    @Override
    public boolean isLeaf(String dimName, String memberName) {
        return false;
    }

    @Override
    public boolean SendMail(String address, String title, String content, String otherAddress, String... filesPath) {
        return false;
    }

    @Override
    public List<String[]> MergeGroups(List<String[]> choicelist) {
        return null;
    }

    @Override
    public List<String> CellsGetBySlices(String cubeName, List<String[]> slices) {
        return null;
    }

    @Override
    public List<String[]> GetPathsBySlices(List<String[]> slices) {
        return null;
    }

    @Override
    public void TabaseMetaToPLN() {

    }

    @Override
    public void FreshUniPosition(String name) {

    }

    @Override
    public void FreshUniCode(String name) {

    }

    @Override
    public void MockRandomData(String cubeCode, int size) {
        Map<String, List<String>> cubeMembers = cubeService.getCubeMembers(cubeCode);
        List<String> dims = cubeService.getDimCodes(cubeCode);
        mockDataManager.MockRandomData(cubeCode, dims, cubeMembers, size);
    }

    @Override
    public void MockData(String cubeCode, int size) {

        Map<String, List<String>> cubeMembers = cubeService.getCubeMembers(cubeCode);
        List<String> dims = cubeService.getDimCodes(cubeCode);
        mockDataManager.MockData(cubeCode, dims, cubeMembers, size);

    }

    public Map<String, Object> getResults() {
        return results;
    }

    public void setResults(Map<String, Object> results) {
        this.results = results;
    }
}
