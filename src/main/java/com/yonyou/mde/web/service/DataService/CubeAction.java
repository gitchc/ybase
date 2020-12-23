package com.yonyou.mde.web.service.DataService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import com.yonyou.mde.MdeInit;
import com.yonyou.mde.api.MultiDimModelApi;
import com.yonyou.mde.config.MdeConfiguration;
import com.yonyou.mde.datasource.DataSourceInfo;
import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.dataloader.DataLoaderTemplate;
import com.yonyou.mde.model.dataloader.budget.DefaultLoaderConfig;
import com.yonyou.mde.model.dataloader.config.FactTableConfig;
import com.yonyou.mde.model.dataloader.config.LoadType;
import com.yonyou.mde.model.dataloader.entity.Dimension;
import com.yonyou.mde.model.dim.DimCacheManager;
import com.yonyou.mde.model.processor.DefalutRowGenerator;
import com.yonyou.mde.web.configurer.DataSourceConfig;
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
    private DataSourceConfig config;
    private String cubeName;
    private String tableName;
    private Map<String, List<DimColumn>> members;
    private String loadSql;
    private List<Member> dims;

    public CubeAction(DataSourceConfig config, String cubeName, String tableName, String loadSql, List<Member> dims, Map<String, List<DimColumn>> members) {
        this.config = config;
        this.cubeName = cubeName;
        this.tableName = tableName;
        this.members = members;
        this.loadSql = loadSql;
        this.dims = dims;
    }


    public void loadCubeData() throws MdeException {
        MdeConfiguration configuration = new MdeConfiguration();
        configuration.setDistributed(false);
        configuration.setProcessor(new DefalutRowGenerator());
        configuration.setAsyncWrite(true);
        configuration.setWriteBackByBiz(true);
        configuration.setInstanceId("1");
        configuration.setModelReplicaNum(2);
        configuration.setCubeInitializer((cubeName) ->{
            try {
                loadModel();
            } catch (MdeException e) {
                e.printStackTrace();
            }
        });
        MdeInit.init(configuration);
        MultiDimModelApi.addModel(cubeName);
    }

    public void loadModel() throws MdeException {
        //所有维度
        List<String> dimCodes = new ArrayList<>();
        List<Dimension> dimensions = new ArrayList<>();

        for (Member dim : dims) {
            String code = dim.getCode();
            boolean isRollUp = dim.getDatatype() == 11;
            dimensions.add(new Dimension(code, code, code));
            dimCodes.add(code);
            DimCacheManager.setCubeDimByList(cubeName, code, members.get(code), isRollUp);
        }

        if (StringUtils.isBlank(loadSql)) {
            this.loadSql = "select id," + StringUtils.join(dimCodes, ",") + ",value from " + tableName;
        } else {
            this.tableName = getTableName(loadSql);
        }
        FactTableConfig factTableConfig = FactTableConfig.builder().cubeName(cubeName).tableName(tableName).pkColumnName("id")
                .measureColumnName("value").dimensions(dimensions).build();
        factTableConfig.setLoadSql(loadSql);
        String dirPath = "D:\\mock\\meta\\" + cubeName+"\\";
        String dimPath = dirPath + "dim.json";
        String dimInfoPath = dirPath + "dimInfo.json";
        String loadSqlPath = dirPath+"loadsql.txt";
        System.out.println(dirPath);
        FileUtil.touch(dimPath);
        FileUtil.touch(dimInfoPath);
        FileUtil.touch(loadSql);
        FileWriter writer = new FileWriter(dimPath);
        writer.write(JSONUtil.toJsonStr(dimCodes));
        FileWriter writer1 = new FileWriter(dimInfoPath);
        writer1.write(JSONUtil.toJsonStr(members));
        FileWriter writer2 = new FileWriter(loadSqlPath);
        writer2.write(loadSql);
        ZipUtil.zip(dirPath);
       /* DataSourceInfo info = new DataSourceInfo();
        info.setUrl(config.getUrl());
        info.setUsername(config.getUsername());
        info.setSchema(config.getSchema());
        info.setPassword(config.getPassword());
        DefaultLoaderConfig config = new DefaultLoaderConfig(info, cubeName, factTableConfig, false);
        // 加载维度信息
        config.getLoadConfig().setLoadType(LoadType.DYNAMIC_LOAD);
        DataLoaderTemplate.getInstance().loadModel(config);*/
    }

    public static void main(String[] args) {
    }

    private String getTableName(String loadSql) {
        Pattern pattern = Pattern.compile("(.*from\\s)(\\w*)(.*)");
        Matcher matcher = pattern.matcher(loadSql);
        if (matcher.find()) {
            return matcher.group(2).trim();
        }
        return "";
    }
}
