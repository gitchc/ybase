package com.yonyou.mde.web.service.DataService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.yonyou.mde.Mde;
import com.yonyou.mde.datasource.DataSourceInfo;
import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.Dimension;
import com.yonyou.mde.model.dataloader.DataLoaderTemplate;
import com.yonyou.mde.model.dataloader.DefaultLoaderConfig;
import com.yonyou.mde.model.dataloader.config.LoadType;
import com.yonyou.mde.model.meta.CubeMeta;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.model.entity.Dim;
import com.yonyou.mde.web.model.types.DataType;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Log4j2
public class CubeLoader {
    private static final String DEFAULT_MEASURE_COLUMN = "VALUE";
    private static final String DEFAULT_TXT_VALUE_COLUMN = "TXTVALUE";
    private static final String DEFAULT_ID = "ID";

    //根据Cube信息加载模型
    public static void loadCubeData(MDEConfig mdeConfig) throws MdeException {
        String loadSql = mdeConfig.getLoadsql();
        String tableName = mdeConfig.getTableName();
        String cubeName = mdeConfig.getModelName();
        Map<String, List<DimColumn>> members = mdeConfig.getMembers();
        //所有维度
        DataSourceInfo info = getDataSourceInfo(mdeConfig.getConfig());//处理数据源数据
        List<String> dimCodes = new ArrayList<>();
        List<Dimension> dimensions = new ArrayList<>();
        for (com.yonyou.mde.web.model.Dimension dim : mdeConfig.getDims()) {
            String code = dim.getCode();
            boolean isRollUp = dim.getDatatype() == DataType.AUTOROLLUP;
            dimensions.add(new Dimension(code, code, code));
            dimCodes.add(code);
            Mde.setModelDimTree(mdeConfig.getModelName(), code, members.get(code), isRollUp);
        }

        if (StringUtils.isBlank(loadSql)) {
            loadSql = "select id," + StringUtils.join(dimCodes, ",") + ",value,txtvalue from " + tableName + " where isdeleted=0";
        } else {
            tableName = getTableName(loadSql);
        }

        CreateLoadFile(dimCodes, cubeName, loadSql, members);//造数据文件
        // 加载维度信息
        DefaultLoaderConfig configf = new DefaultLoaderConfig(info, cubeName, createCubeMeta(cubeName, tableName, dimensions, loadSql),
                null);
        configf.getLoadConfig().setLoadType(LoadType.DYNAMIC_LOAD);
        DataLoaderTemplate.getInstance().loadModel(configf);
    }

    /**
     * @description: 处理数据源信息
     * @param: config
     * @author chenghch
     */
    public static DataSourceInfo getDataSourceInfo(DataSourceConfig config) {
        DataSourceInfo info = new DataSourceInfo();
        info.setUrl(config.getUrl());
        info.setUsername(config.getUsername());
        info.setSchema(config.getSchema());
        info.setPassword(config.getPassword());
        return info;
    }

    private static CubeMeta createCubeMeta(String cubeName, String factTableName, List<Dimension> dimensions, String loadSql) {
        return CubeMeta.builder()
                .modelName(cubeName)
                .cubeName(cubeName)
                .tableName(factTableName)
                .tablePkColName(DEFAULT_ID)
                .measureColName(DEFAULT_MEASURE_COLUMN)
                .txtValueColName(DEFAULT_TXT_VALUE_COLUMN)
                .dimensions(dimensions)
                .loadSql(loadSql)
                .build();
    }

    //创建Load文件,方便测试
    private static void CreateLoadFile(List<String> dimCodes, String cubeName, String loadSql, Map<String, List<DimColumn>> members) {
        if (true) {
            return;
        }
        String dirPath = "C:\\Users\\Administrator\\Desktop\\Meta\\" + cubeName + "\\";
        String dimPath = dirPath + "dim.json";
        String dimInfoPath = dirPath + "dimInfo.json";
        String loadSqlPath = dirPath + "loadsql.txt";
        log.info(StrUtil.format("已生成配置文件:{},配置文件:{}", cubeName, dirPath));
        FileUtil.touch(dimPath);
        FileUtil.touch(dimInfoPath);
        FileUtil.touch(loadSql);
        List<Dim> dimensions = new ArrayList<>();
        for (String dimCode : dimCodes) {
            dimensions.add(new Dim(dimCode, false));
        }
        FileWriter writer = new FileWriter(dimPath);
        writer.write(JSONUtil.toJsonStr(dimensions));
        FileWriter writer1 = new FileWriter(dimInfoPath);
        writer1.write(JSONUtil.toJsonStr(members));
        FileWriter writer2 = new FileWriter(loadSqlPath);
        writer2.write(loadSql);
        ZipUtil.zip(dirPath);
        FileUtil.del(dirPath);
    }

    public static void main(String[] args) {
        String sql = "select ttt from aaa a leftjoin (select ttt from bbb )b on a.id = b.id";
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }


    private static final Pattern pattern = Pattern.compile("\\bfrom\\s*\\S*");

    private static String getTableName(String loadSql) {
        Matcher matcher = pattern.matcher(loadSql);
        if (matcher.find()) {
            return matcher.group().trim().replace("from", "");
        }
        return "";
    }

    //重新加载维度
    public static void reloadDim(String modelName, com.yonyou.mde.web.model.Dimension dim, List<DimColumn> members) throws MdeException {
        String dimCode = dim.getCode();
        boolean isRollUp = dim.getDatatype() == DataType.AUTOROLLUP;
        Mde.setModelDimTree(modelName, dimCode, members, isRollUp);
    }
}
