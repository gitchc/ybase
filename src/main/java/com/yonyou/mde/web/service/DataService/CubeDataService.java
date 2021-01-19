package com.yonyou.mde.web.service.DataService;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.dao.CubeMapper;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.utils.MemberUtil;
import com.yonyou.mde.web.utils.MuiltCross;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import javax.annotation.Resource;
import java.util.*;

/**
 * @version 1.0
 * @Description: 类描述
 * @Author chenghch
 * @Date 2021/1/14 20:21
 */
@Service
public class CubeDataService {
    @Resource
    private CubeMapper cubeMapper;
    @Resource
    private MemberService memberService;
    @Resource
    private CubeService cubeService;

    /**
     * @description: 通过cubeid模型数据
     * @param: cubeid 模型id
     * @param: pages 页面维
     * @param: rows 行维
     * @param: cols 列维
     * @author chenghch
     */
    public List<Map<String, Object>> getData(String cubeid, String pages, String rows, String cols) throws MdeException {
        Cube cube = cubeService.getCubeById(cubeid);
        String cubeCode = cube.getCubecode();
        Map<String, String> dimMap = cubeService.getDimMap(cubeid);//获取维度id跟code的map
        String pageFindStr = pages.replaceAll("__", "#");
        String[] rowArr = rows.split("__");
        String[] colArr = cols.split("__");//只针对行分页,列暂时不用
        List<Member[]> rowDims = getDims(rowArr);//获取行的所有维度
        List<Member[]> colDims = getDims(colArr);//获取列的所有维度

        List<Member[]> rowslice = new MuiltCross(rowDims).get(0, 1000);//获取维度前1000条笛卡尔积
        List<Member[]> colslice = new MuiltCross(colDims).getAll();//获取列维度的笛卡尔积

        String queryExp = getQueryExp(pageFindStr, rowslice, colDims, dimMap);//根据前1000条获取要查询的切片组合
        Map<String, Object> datas = getDatas(cubeCode, rowArr, colArr, queryExp, dimMap);//根据切片和行列,取值
        List<String> colFields = getFileds(colslice, dimMap);//获取拼接表格的filed
        List<Map<String, Object>> results = getTableData(pageFindStr, dimMap, rowslice, datas, colFields);//获取Table的键对值
        return results;
    }

    /**
     * @description: 通过data 拼接前端表格需要的数据
     * @param: dimMap
     * @param: rowslice
     * @param: datas
     * @param: colFields
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author chenghch
     */
    private List<Map<String, Object>> getTableData(String pageFindStr, Map<String, String> dimMap, List<Member[]> rowslice, Map<String, Object> datas, List<String> colFields) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (Member[] rowMembers : rowslice) {
            Map<String, Object> rs = new HashMap<>();
            results.add(rs);
            StringBuilder sb = new StringBuilder();
            for (Member member : rowMembers) {//拼接维度成员
                String dimcode = dimMap.get(member.getDimid());
                String field = StrUtil.format("{}.{}", dimcode, member.getCode());
                if (sb.length() > 0) {
                    sb.append("#");
                }
                sb.append(field);
                rs.put(dimcode, MemberUtil.getLevelName(member));
            }
            if (StringUtils.isNotBlank(pageFindStr)) {
                rs.put("rowkey", pageFindStr.concat("#").concat(sb.toString()));
            } else {
                    rs.put("rowkey", sb.toString());
            }
            for (String colfield : colFields) {//拼接数据列
                StringBuilder newSb = new StringBuilder("#");
                newSb.append(sb);
                newSb.append("#");
                newSb.append(colfield);
                Object value = getValue(datas, newSb.toString());
                rs.put(colfield, value);
            }
        }
        return results;
    }

    /**
     * @description: 通过表达式获取Data的Map值
     * @param: cubeCode
     * @param: rowArr
     * @param: colArr
     * @param: queryExp
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @author chenghch
     */
    private Map<String, Object> getDatas(String cubeCode, String[] rowArr, String[] colArr, String queryExp, Map<String, String> dimMap) throws MdeException {
        SliceResult sliceResult = Server.getServer().getCube(cubeCode).find(queryExp);//根据切片查询出来所有值
        Table table = sliceResult.toTable();
        Map<String, Object> datas = new HashMap<>();
        for (Row row : table) {
            StringBuilder sb = new StringBuilder();
            for (String rowa : rowArr) {
                String dimCode = dimMap.get(rowa);
                sb.append("#");
                String field = StrUtil.format("{}.{}", dimCode, row.getText(dimCode));
                sb.append(field);
            }
            for (String cola : colArr) {
                String dimCode = dimMap.get(cola);
                sb.append("#");
                String field = StrUtil.format("{}.{}", dimCode, row.getText(dimCode));
                sb.append(field);
            }
            datas.put(sb.toString(), row.getDouble("value"));
        }
        return datas;
    }

    private Object getValue(Map<String, Object> datas, String key) {
        Object value = datas.get(key);
        if (value == null) {
            return "";
        } else {
            return value;
        }

    }

    /**
     * @description: 获取拼接表格的Filed
     * @param: colslice
     * @return: java.util.List<java.lang.String>
     * @author chenghch
     */
    private List<String> getFileds(List<Member[]> colslice, Map<String, String> dimMap) {
        List<String> fileds = new ArrayList<>();
        for (Member[] members : colslice) {
            StringBuilder memberFiled = new StringBuilder();
            for (Member member : members) {
                String filed = StrUtil.format("{}.{}", dimMap.get(member.getDimid()), member.getCode());
                if (memberFiled.length() > 0) {
                    memberFiled.append("#");
                }
                memberFiled.append(filed);
            }
            fileds.add(memberFiled.toString());
        }
        return fileds;
    }

    /**
     * @description: 根据 page,row拼接查询的表达式
     * @param: cubeid
     * @param: pageFindStr
     * @param: rowslice
     * @return: void
     * @author chenghch
     */
    private String getQueryExp(String pageFindStr, List<Member[]> rowslice, List<Member[]> colslice, Map<String, String> dimMap) {
        Map<String, Set<String>> findRowMembers = getFindRowMembers(rowslice);//根据笛卡尔积组合,获取到切片集合
        StringBuilder findStr = new StringBuilder();
        findStr.append(pageFindStr);
        StringBuilder colexp = getColExp(colslice, dimMap);
        StringBuilder rowexp = getRowExp(findRowMembers, dimMap);
        findStr.append(colexp);
        findStr.append(rowexp);
        if (StringUtils.isBlank(pageFindStr)) {
            findStr.deleteCharAt(0);
        }
        return findStr.toString();
    }

    private StringBuilder getColExp(List<Member[]> colslice, Map<String, String> dimMap) {
        StringBuilder colexps = new StringBuilder();
        for (Member[] members : colslice) {
            StringBuilder colexp = new StringBuilder();
            for (Member member : members) {
                if (colexp.length() == 0) {
                    colexp.append("#");
                    colexp.append(dimMap.get(member.getDimid()));
                    colexp.append(".[");
                    colexp.append(member.getCode());
                } else {
                    colexp.append(",");
                    colexp.append(member.getCode());
                }
            }
            colexp.append("]");
            colexps.append(colexp);
        }
        return colexps;
    }

    private StringBuilder getRowExp(Map<String, Set<String>> findRowMembers, Map<String, String> dimMap) {
        StringBuilder findStr = new StringBuilder();
        for (Map.Entry<String, Set<String>> entry : findRowMembers.entrySet()) {
            String dimid = entry.getKey();
            Set<String> members = entry.getValue();
            String rowFindStr = StrUtil.format("{}.[{}]", dimMap.get(dimid), StringUtils.join(members, ","));
            findStr.append("#");
            findStr.append(rowFindStr);
        }
        return findStr;
    }

    /**
     * @description: 遍历前1000条所需要的切片组合, 来查询数据
     * @param: rowslice
     * @return: void
     * @author chenghch
     */
    private Map<String, Set<String>> getFindRowMembers(List<Member[]> rowslice) {
        Map<String, Set<String>> rowSliceMap = new HashMap<>();
        for (Member[] members : rowslice) {
            for (Member member : members) {
                String dimid = member.getDimid();
                Set<String> membercodes = rowSliceMap.get(dimid);
                if (membercodes == null) {
                    membercodes = new HashSet<>();
                    rowSliceMap.put(dimid, membercodes);
                }
                membercodes.add(member.getCode());
            }
        }
        return rowSliceMap;
    }

    /**
     * @description: 通过rowid 获取所有的维度成员组合
     * @param: Arr
     * @return: java.util.List<com.yonyou.mde.web.model.Member [ ]>
     * @author chenghch
     */
    private List<Member[]> getDims(String[] Arr) {
        List<Member[]> dims = new ArrayList<>();
        for (String rowDimid : Arr) {
            List<Member> memberList = memberService.getMembersBydimid(rowDimid);
            dims.add(ArrayUtil.toArray(memberList, Member.class));
        }
        return dims;
    }

    public void setData(String cubeid, String path, String value) throws MdeException {
        Cube cube = cubeService.getCubeById(cubeid);
        String cubeCode = cube.getCubecode();
        Server.getServer().getCube(cubeCode).setData(path, value);
    }
}
