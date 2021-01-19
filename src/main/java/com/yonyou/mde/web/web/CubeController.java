package com.yonyou.mde.web.web;

import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.PageDim;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.DataService.CubeDataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2020-12-15.
 */
@RestController
@RequestMapping("/cube")
public class CubeController {
    @Resource
    private CubeService cubeService;
    @Resource
    private CubeDataService cubeDataService;

    @RequestMapping("/insert")
    public Result add(Cube cube) {
        cubeService.insertCube(cube);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/save")
    public Result save(Cube cube) {
        cubeService.saveCube(cube);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        cubeService.deleteCubeById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/reloadData")
    public Result reloadData(@RequestParam String id) {
        cubeService.reloadData(id);
        return ResultGenerator.genSuccessResult();
    }


    @RequestMapping("/update")
    public Result update(Cube cube) {
        cubeService.update(cube);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Cube Cube = cubeService.findById(id);
        return ResultGenerator.genSuccessResult(Cube);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        List<Cube> list = cubeService.getAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @RequestMapping("/getPages")
    public Result getPageDims(@RequestParam String id) {
        List<PageDim> list = cubeService.getCubeDims(id);
        return ResultGenerator.genSuccessResult(list);
    }

    @RequestMapping("/getData")
    public Result getData(@RequestParam String cubeid, @RequestParam String pages, @RequestParam String rows, @RequestParam String cols, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) throws MdeException {
        List<Map<String, Object>> data = cubeDataService.getData(cubeid, pages, rows, cols);
        return ResultGenerator.genSuccessResult(data);
    }

    @RequestMapping("/setData")
    public Result getData(String cubeid, String path, String value) throws MdeException {
        cubeDataService.setData(cubeid, path, value);
        return ResultGenerator.genSuccessResult();
    }
}
