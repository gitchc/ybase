package com.yonyou.mde.web.bigCube.main;

import com.yonyou.mde.api.MultiDimModelApi;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.MultiDimModel;
import com.yonyou.mde.model.graph.DimTree;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.bigCube.interfaces.ICube;

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

    public int setVal(String exp, double e) throws MdeException {
        return content.setVal(exp, e);
    }

    public boolean setValues(List<Map<String, Object>> pathValues) throws MdeException {
        return content.set(pathValues);
    }

    public Dimension getDimension(String dimName) {
        String name = dimName.toUpperCase();
        DimTree dimTree = model.getDimTree(name);
        if (dimTree != null) {
            return new Dimension(dimTree,name);
        }
        return null;
    }
}
