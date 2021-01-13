package com.yonyou.mde.web.script.utils;

import java.io.*;

/**
 * @Author:chenghch
 * @Description:
 * @Date:First Created 2021/1/04
 */
public class ShellUtil {
    /**
     * linux: String shStr="/export/home/text.sh";
     * win: String shStr = "start D:\\test.bat";
     ***/
    public static String runCMD(String shStr) {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            callCmd(shStr);
        } else {
            callShell(shStr);
        }
        return "";

    }


    public static void callCmd(String locationCmd) {
        try {

            if (locationCmd != null && !locationCmd.trim().startsWith("cmd")) {//兼容处理手动写cmd的路径
                locationCmd = "cmd.exe /c " + locationCmd;
            }
            Process child = Runtime.getRuntime().exec(locationCmd);
            InputStream in = child.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = child.exitValue();
            if (i == 0) {
                System.out.println("执行完成!");
            } else {
                System.out.println("执行失败!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void callShell(String localshell) {

        try {
            Runtime rt = Runtime.getRuntime();
            String command = localshell;
            Process pcs = rt.exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(pcs.getInputStream()));
            String line = new String();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            try {
                pcs.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("执行失败!");
            }
            br.close();
            int ret = pcs.exitValue();
            System.out.println(ret);
            System.out.println("脚本执行完毕!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}