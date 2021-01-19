package com.yonyou.mde.bigCube.main;

import com.yonyou.mde.api.MultiDimModelApi;
import com.yonyou.mde.bigCube.interfaces.ICube;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.MultiDimModel;
import com.yonyou.mde.model.graph.DimTree;
import com.yonyou.mde.model.result.SliceResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cube implements ICube {
    private MultiDimModelApi content;
    private MultiDimModel model;

    public Cube(MultiDimModelApi content, MultiDimModel model) {
        this.content = content;
        this.model = model;
    }

    public SliceResult find(String exp) throws MdeException {
        return content.find(exp);
    }

    public double findVal(String exp) throws MdeException {
        return content.findVal(exp);
    }

    public List<Map<String, Object>> findTxt(String exp) throws MdeException {
        return content.findTxt(exp);
    }

    public SliceResult exp(String exp) throws MdeException {
        return content.exp(exp);
    }

    public SliceResult calc(String exp) throws MdeException {
        return content.calc(exp);
    }

    public int clear(String exp) throws MdeException {
        return content.clear(exp);
    }

    public int setVal(String exp, Object value) throws MdeException {
        Double vl = 0d;
        if (value instanceof String) {
            vl = Double.parseDouble(value.toString());
        } else if (value instanceof Double) {
            vl = (Double) value;
        }
        return content.setVal(exp, vl);
    }

    public void setData(String exp, Object value) throws MdeException {
        Double vl = 0d;
        if (value instanceof String) {
            vl = Double.parseDouble(value.toString());
        } else if (value instanceof Double) {
            vl = (Double) value;
        }
        Map<String, Object> keyvalue = new HashMap<>();
        for (String dimexp : exp.split("#")) {
            String[] dimToValues = dimexp.split("\\.");
            keyvalue.put(dimToValues[0], dimToValues[1]);
        }
        keyvalue.put("VALUE", vl);
        content.set(Arrays.asList(keyvalue),true);
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
