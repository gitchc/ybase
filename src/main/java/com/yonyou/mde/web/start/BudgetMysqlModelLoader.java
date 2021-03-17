package com.yonyou.mde.web.start;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.mde.Mde;
import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.Dimension;
import com.yonyou.mde.model.dataloader.DataLoaderTemplate;
import com.yonyou.mde.model.dataloader.DefaultLoaderConfig;
import com.yonyou.mde.model.dataloader.config.LoadType;
import com.yonyou.mde.model.meta.CubeMeta;
import com.yonyou.mde.model.meta.CubeStruct;
import com.yonyou.mde.util.JsonUtil;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.service.DataService.CubeLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class BudgetMysqlModelLoader {
    final ObjectMapper mapper = new ObjectMapper();
    private static final String DEFAULT_MEASURE_COLUMN = CubeStruct.DEFAULT_MEASURE_COLUMN_NAME;
    private static final String DEFAULT_TXT_VALUE_COLUMN = CubeStruct.DEFAULT_TXT_VAL_COLUMN_NAME;
    private static final String DEFAULT_PK_COLUMN = "PK_OBJ";
    private static final String PK_PREFIX = "PK_";
    private static final String CODE_PREFIX = "CODE_";
    private static final String cubeName = "EPM";

    public void load(DataSourceConfig dataSourceConfig) {
        String dimStr = null;
        try {
            dimStr = FileUtils.readFileToString(new File("D://BudgetMysqlDim.json"));

            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class, String.class);
            List<String> dims = mapper.readValue(dimStr, javaType);
            log.info("{}", dims);
            //所有维度
            String dimInfoStr = FileUtils
                    .readFileToString(new File("D://BudgetMysqlDiminfo.json"));
            Map<String, List<DimMemberDTO>> data = (Map<String, List<DimMemberDTO>>) JsonUtil
                    .parse(dimInfoStr, Map.class);
            List<Map<String, List<DimColumn>>> dimColumns = new ArrayList<>();
            for (Map.Entry<String, List<DimMemberDTO>> entry : data.entrySet()) {
                Map<String, List<DimColumn>> dimColumn = loadDim(cubeName, entry.getKey(),
                        entry.getValue());
                dimColumns.add(dimColumn);
            }
            CubeMeta config = createCubeMeta(cubeName, "tb_cube_03r", dims);
            // 加载维度信息
            DefaultLoaderConfig configf = new DefaultLoaderConfig(CubeLoader.getDataSourceInfo(dataSourceConfig), cubeName, config,
                    null);
            configf.getLoadConfig().setLoadType(LoadType.DYNAMIC_LOAD);
            DataLoaderTemplate.getInstance().loadModel(configf);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public Map<String, List<DimColumn>> loadDim(String cubeCode, String dimcode, List dimColList)
            throws MdeException {
        List<DimMemberDTO> dtos = new ArrayList<>();
        dimColList.forEach(dc -> {
            if (dc instanceof Map) {
                dtos.add(JsonUtil.parse(JsonUtil.toJson(dc), DimMemberDTO.class));
            }
        });
        Map<String, List<DimColumn>> dimcols = MdeUtil.transDimcol(dimcode, dtos);
        boolean isRollUp = false;
        for (Map.Entry<String, List<DimColumn>> entry : dimcols.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("C1")
                    || entry.getKey().equalsIgnoreCase("C2")
                    || entry.getKey().equalsIgnoreCase("C3")
                    || entry.getKey().equalsIgnoreCase("C4")
                    || entry.getKey().equalsIgnoreCase("C5")
                    || entry.getKey().equalsIgnoreCase("TRAIL")
                    || entry.getKey().equalsIgnoreCase("ICP")
                    || entry.getKey().equalsIgnoreCase("ACCOUNT")) {
                isRollUp = true;
            }
            Mde.loadDim(cubeCode, entry.getKey(), entry.getValue(), isRollUp);
        }
        return dimcols;
    }

    public CubeMeta createCubeMeta(String cubeName, String factTableName, List<String> dims) {
        // 维度信息
        List<Dimension> dimensions = dims.stream()
                .map(dimName -> new Dimension(dimName, PK_PREFIX + dimName, CODE_PREFIX + dimName))
                .collect(Collectors.toList());

        return CubeMeta.builder()
                .modelName(cubeName)
                .cubeName(cubeName)
                .tableName(factTableName)
                .tablePkColName(DEFAULT_PK_COLUMN)
                .measureColName(DEFAULT_MEASURE_COLUMN)
                .txtValueColName(DEFAULT_TXT_VALUE_COLUMN)
                .dimensions(dimensions)
                .build();
    }

}
