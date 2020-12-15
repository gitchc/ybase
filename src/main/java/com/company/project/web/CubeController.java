package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Cube;
import com.company.project.service.CubeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2020-12-15.
 */
@RestController
@RequestMapping("/cube")
public class CubeController {
    @Resource
    private CubeService CubeService;

    @RequestMapping("/insert")
    public Result add(Cube Cube) {
        CubeService.insertCube(Cube);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/save")
    public Result save(Cube Cube) {
        CubeService.saveCube(Cube);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        CubeService.deleteCubeById(id);
        return ResultGenerator.genSuccessResult();
    }


    @RequestMapping("/update")
    public Result update(Cube Cube) {
        CubeService.update(Cube);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Cube Cube = CubeService.findById(id);
        return ResultGenerator.genSuccessResult(Cube);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        List<Cube> list = CubeService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }
}
