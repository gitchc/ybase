package com.yonyou.mde.bigCube.main;

import com.yonyou.mde.bigCube.interfaces.ICube;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.MultiDimModel;
import com.yonyou.mde.model.api.MultiDimModelApi;
import com.yonyou.mde.model.graph.DimTree;
import com.yonyou.mde.model.result.SliceResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cube implements ICube {
    private MultiDimModelApi modelApi;
    private MultiDimModel model;

    public Cube(MultiDimModelApi modelApi, MultiDimModel model) {
        this.modelApi = modelApi;
        this.model = model;
    }

    public SliceResult find(String exp) throws MdeException {
        return modelApi.find(exp);
    }

    public double findVal(String exp) throws MdeException {
        return modelApi.findVal(exp);
    }

    public List<Map<String, Object>> findTxt(String exp) throws MdeException {
        return modelApi.findTxt(exp);
    }

    public SliceResult exp(String exp) throws MdeException {
        return modelApi.exp(exp);
    }

    public SliceResult calc(String exp) throws MdeException {
        return modelApi.calc(exp);
    }

    public int clear(String exp) throws MdeException {
        return modelApi.clear(exp);
    }

    public int setVal(String exp, Object value) throws MdeException {
        Double vl = 0d;
        if (value instanceof String) {
            vl = Double.parseDouble(value.toString());
        } else if (value instanceof Double) {
            vl = (Double) value;
        }
        return modelApi.setVal(exp, vl);
    }

    public void setData(String exp, Object value) throws MdeException {
        Double vl = 0d;
        if (value instanceof String) {//区分 0 跟 空
            String vlstr = value.toString();
            if (StringUtils.isNotBlank(vlstr)) {
                vl = Double.parseDouble(vlstr);
            }else {
                modelApi.clear(exp);//清空数据
            }
        } else if (value instanceof Double) {
            vl = (Double) value;
        }
        Map<String, Object> keyvalues = new HashMap<>();
        for (String dimexp : exp.split("#")) {
            String[] dimToValues = dimexp.split("\\.");
            keyvalues.put(dimToValues[0], dimToValues[1]);
        }
        keyvalues.put("VALUE", vl);
        modelApi.set(Arrays.asList(keyvalues));
    }

    public Dimension getDimension(String dimName) {
        String name = dimName.toUpperCase();
        DimTree dimTree = model.getDimTree(name);
        if (dimTree != null) {
            return new Dimension(dimTree, name);
        }
        return null;
    }
}
