package com.yonyou.mde.web.start;


import com.yonyou.mde.context.MdeContext;
import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zhangpanm
 * @Date: 2020/8/28
 * @Description: mde融合工具类
 */
public class MdeUtil {
    public static String LONG_PRE="long_";
    public static String ROOT="~";
    /**
     * 维度数据转换
     * @param code 维度code
     * @param data
     * @return
     */
    public static Map<String,List<DimColumn>> transDimcol(String code,List<DimMemberDTO> data){
        if(null == data||data.isEmpty()){
            return null;
        }
        List<DimColumn> dimCols = new ArrayList<>(data.size());
        Map<String,List<DimColumn>> res = new HashMap<>();
        if(data.get(0).getLevelValues().isEmpty()){//非时间维度
            data.forEach(d ->{
                dimCols.add(transDimcol(d));
            });
        }else{//时间维度
            data.forEach(d ->{
                dimCols.add(transTimeDimcol(d));
                for (Map.Entry<String, LevelValueDTO> es : d.getLevelValues().entrySet()) {
                    String inkey =es.getKey();
                    if(!res.containsKey(inkey)){
                        List<DimColumn> innerCols = new ArrayList<>();
                        res.put(inkey,innerCols);
                    }
                    res.get(inkey).add(transDimcol(es.getValue(),d.getWeight(),d.getUniqKeyLong()));
                }
            });
        }
        res.put(code,dimCols);
        return res;
    }

    public static DimColumn transTimeDimcol(DimMemberDTO dto){
        Map<String,String> ext=new HashMap<>();
        ext.put(dto.getUniqKey(), String.valueOf(dto.getUniqKeyLong()));
        DimColumn col = DimColumn.builder().pk(dto.getUniqKey()).code(dto.getUniqKey()).parentPk(dto.getParentPk()).weight(dto.getWeight())
                .parentCode(dto.getParentCode()).rollUp(true).extraParam(ext).build();
        if(StringUtil.isEmpty(col.getParentPk())){
            col.setParentPk(ROOT);
        }
        if(StringUtil.isEmpty(col.getParentCode())){
            col.setParentCode(ROOT);
        }
        return col;
    }

    /**
     * 维度转换
     * @param dto
     * @return
     */
    public static DimColumn  transDimcol(DimMemberDTO dto){
        if(null == dto){
            return null;
        }
        Map<String,String> ext=new HashMap<>();
        ext.put(dto.getPk(),String.valueOf(dto.getUniqKeyLong()));
        ext.put(dto.getCode(),String.valueOf(dto.getUniqKeyLong()));
        DimColumn col = DimColumn.builder().pk(dto.getPk()).code(dto.getCode()).parentPk(dto.getParentPk()).weight(dto.getWeight())
                .parentCode(dto.getParentCode()).rollUp(true).extraParam(ext).build();
        if(StringUtil.isEmpty(col.getParentPk())){
            col.setParentPk(ROOT);
        }
        if(StringUtil.isEmpty(col.getParentCode())){
            col.setParentCode(ROOT);
        }
        return col;
    }

    /**
     * 时间维度转换
     * @param ldto
     * @param weight
     * @param lv
     * @return
     */
    public static DimColumn transDimcol(LevelValueDTO ldto ,float weight,long lv){
        if(null == ldto){
            return null;
        }
        Map<String,String> ext=new HashMap<>();
        ext.put(ldto.getCode(),String.valueOf(lv));
        ext.put(ldto.getPk(),String.valueOf(lv));
        DimColumn col = DimColumn.builder().pk(ldto.getPk()).code(ldto.getCode()).parentPk(ROOT).weight(weight)
                .parentCode(ROOT).rollUp(true).extraParam(ext).build();
        return col;
    }

    /**
     * 根据条件在维度树上查找维度的long值
     * @param cubeCode 模型code
     * @param longColumn 维度long值列名
     * @param dimPk 维度的pk值
     * @return
     */
    public static long getDimLongValue(String cubeCode, String longColumn, String dimPk)
        throws MdeException {
        if (StringUtil.isAnyEmpty(cubeCode, longColumn, dimPk)) {
            throw new MdeException("params cannot be empty");
        }

        String dimCode = longColumn.replace(LONG_PRE, "");
        Map<String, String> map = MdeContext.getInstance().getDimNodeExtraParamsByPk(cubeCode, dimCode, dimPk);

        return Long.parseLong(map.get(dimPk));
    }

}
