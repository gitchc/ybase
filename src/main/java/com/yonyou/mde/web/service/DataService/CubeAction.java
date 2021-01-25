package com.yonyou.mde.web.service.DataService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.yonyou.mde.Mde;
import com.yonyou.mde.config.MdeConfiguration;
import com.yonyou.mde.datasource.DataSourceInfo;
import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.Dimension;
import com.yonyou.mde.model.dataloader.DataLoaderTemplate;
import com.yonyou.mde.model.dataloader.DefaultLoaderConfig;
import com.yonyou.mde.model.dataloader.config.LoadType;
import com.yonyou.mde.model.meta.CubeMeta;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.model.types.DataType;
import com.yonyou.mde.web.model.entity.Dim;
import com.yonyou.mde.web.model.Member;
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
public class CubeAction {
    protected static final String DEFAULT_MEASURE_COLUMN = "VALUE";
    protected static final String DEFAULT_TXT_VALUE_COLUMN = "TXTVALUE";
    private DataSourceInfo info;
    private String cubeName;
    private String tableName;
    private Map<String, List<DimColumn>> members;
    private String loadSql;
    private List<Member> dims;

    public CubeAction(DataSourceConfig config, String cubeName, String tableName, String loadSql, List<Member> dims, Map<String, List<DimColumn>> members) {
        this.cubeName = cubeName;
        this.tableName = tableName;
        this.members = members;
        this.loadSql = loadSql;
        this.dims = dims;
        DataSourceInfo info = new DataSourceInfo();
        info.setUrl(config.getUrl());
        info.setUsername(config.getUsername());
        info.setSchema(config.getSchema());
        info.setPassword(config.getPassword());
        this.info = info;
    }

    public void loadCubeData() throws MdeException {
        MdeConfiguration configuration = new MdeConfiguration();
        configuration.setDistributed(false);//是否启用分布式
        configuration.setWriteBackByBiz(true);//回写库
        configuration.setModelInitializer((cubeName, map) -> {
            try {
                CubeAction.this.loadCubeMeta();
            } catch (MdeException e) {
                e.printStackTrace();
            }
        });
        configuration.setProcessor(new WriteBackProcesser());//自定义回写数据库方法
        Mde.init(configuration);
        loadCubeMeta();//加载元数据信息
    }

    //根据Cube信息加载模型
    private void loadCubeMeta() throws MdeException {
        //所有维度
        List<String> dimCodes = new ArrayList<>();
        List<Dimension> dimensions = new ArrayList<>();

        for (Member dim : dims) {
            String code = dim.getCode();
            boolean isRollUp = dim.getDatatype() == DataType.AUTOROLLUP;
            dimensions.add(new Dimension(code, code, code));
            dimCodes.add(code);
            Mde.setModelDimTree(cubeName, code, members.get(code), isRollUp);
        }
        if (StringUtils.isBlank(loadSql)) {
            this.loadSql = "select id," + StringUtils.join(dimCodes, ",") + ",value,txtvalue from " + tableName+" where isdeleted=0";
        } else {
            this.tableName = getTableName(loadSql);
        }

//        CreateLoadFile(dimCodes);//造数据文件

        // 加载维度信息
        DefaultLoaderConfig configf = new DefaultLoaderConfig(info, cubeName, createCubeMeta(cubeName, tableName, "id", dimensions),
                null);
        configf.getLoadConfig().setLoadType(LoadType.DYNAMIC_LOAD);
        DataLoaderTemplate.getInstance().loadModel(configf);
    }

    private CubeMeta createCubeMeta(String cubeName, String factTableName,
                                      String tablePkColName, List<Dimension> dimensions) {
        return CubeMeta.builder()
                .modelName(cubeName)
                .cubeName(cubeName)
                .tableName(factTableName)
                .tablePkColName(tablePkColName)
                .measureColName(DEFAULT_MEASURE_COLUMN)
                .txtValueColName(DEFAULT_TXT_VALUE_COLUMN)
                .dimensions(dimensions)
                .build();
    }

    //创建Load文件,方便测试
    private void CreateLoadFile(List<String> dimCodes) {
        String dirPath = "D:\\mock\\meta\\" + cubeName + "\\";
        String dimPath = dirPath + "dim.json";
        String dimInfoPath = dirPath + "dimInfo.json";
        String loadSqlPath = dirPath + "loadsql.txt";
        System.out.println(dirPath);
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
    }

    public static void main(String[] args) {
        String sql = "select ttt from aaa a leftjoin (select ttt from bbb )b on a.id = b.id";
        System.out.println(getTableName(sql));
    }

    private static final Pattern pattern = Pattern.compile("\\bfrom\\s*\\S*");

    private static String getTableName(String loadSql) {
        Matcher matcher = pattern.matcher(loadSql);
        if (matcher.find()) {
            return matcher.group().trim().replace("from","");
        }
        return "";
    }
}
