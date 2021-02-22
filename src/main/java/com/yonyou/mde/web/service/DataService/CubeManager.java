package com.yonyou.mde.web.service.DataService;

import com.yonyou.mde.context.MdeContext;
import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.model.Dimension;

import java.util.List;
import java.util.Map;

/*
 *cube管理器
 **/
public class CubeManager {
    //加载Cube数据
    public static void loadCubeData(DataSourceConfig config, String cubeName, String tableName, String loadSql, List<Dimension> dims, Map<String, List<DimColumn>> members) throws MdeException {
        MDEConfig mdeconfig = MDEConfig.builder().config(config).modelName(cubeName).loadsql(loadSql).tableName(tableName).dims(dims).members(members).build();
        loadCubeData(mdeconfig);
    }

    public static void loadCubeData(MDEConfig mdeConfig) throws MdeException {
        removeData(mdeConfig.getModelName());//卸载Cube,然后重新Load
        CubeLoader.loadCubeData(mdeConfig);
    }

    //卸载数据
    public static void removeData(String cubeName) {
        MdeContext mdeContext = MdeContext.getInstance();
        mdeContext.removeModel(cubeName);
    }

    //重新load维度数据
    public static void reloadDim(String modelName, Dimension dim, List<DimColumn> members) throws MdeException {
        CubeLoader.reloadDim(modelName, dim, members);
    }
}
