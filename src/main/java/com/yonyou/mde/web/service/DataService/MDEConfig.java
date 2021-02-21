package com.yonyou.mde.web.service.DataService;

import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.model.Dimension;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author:chenghch
 * @Description:
 * @Date:First Created 2021/2/21
 */
@Data
@Builder
public class MDEConfig {
    private String modelName;//模型名称
    private String tableName;//业务表名称
    private String loadsql;//load数据sql
    private DataSourceConfig config;//业务数据源
    private List<Dimension> dims;//维度信息
    private Map<String, List<DimColumn>> members;//成员信息

}
