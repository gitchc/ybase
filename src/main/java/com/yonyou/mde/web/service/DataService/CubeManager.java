package com.yonyou.mde.web.service.DataService;

import com.yonyou.mde.context.MdeContext;
import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.model.Member;

import java.util.List;
import java.util.Map;

/*
 *cube管理器
 **/
public class CubeManager {
    //加载Cube数据
    public static void loadCubeData(DataSourceConfig config, String cubeName, String tableName, String loadSql, List<Dimension> dims, Map<String, List<DimColumn>> members) throws MdeException {
        removeData(cubeName);//卸载Cube,然后重新Load
        CubeAction manager = new CubeAction(config, cubeName, tableName, loadSql, dims,members);
        manager.loadCubeData();
    }

    //卸载数据
    public static void removeData(String cubeName) {
        MdeContext mdeContext = MdeContext.getInstance();
        mdeContext.removeModel(cubeName);
    }

    //重新load数据
    public static void reloadData(String cubecode) {

    }
}
