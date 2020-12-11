package com.company.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public void redirect(HttpServletRequest rq, HttpServletResponse rp) throws IOException {
        rp.sendRedirect("/static/index.html");  //跳转的指定页面。
    }


}
