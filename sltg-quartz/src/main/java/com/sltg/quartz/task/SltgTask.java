package com.sltg.quartz.task;

import com.sltg.common.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 定时任务调度测试
 * 
 * @author styra~
 */
@Component("sltgTask")
public class SltgTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(SltgTask.class);

    @Value("${sltg.python.lib-path}")
    private String pythonLibPath;

    @Value("${sltg.python.script-path}")
    private String pythonScriptPath;

    public void sltgMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        LOGGER.info(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void sltgParams(String params) {
        LOGGER.info("执行有参方法：" + params);
        executeScript(params);
    }

    public void sltgNoParams() {
        LOGGER.info("执行无参方法");
        executeScript("");
    }

    private void executeScript(String scriptName) {
        try {
            String[] cmd = new String[] { "cmd", "/c", pythonScriptPath + scriptName };
            Process process = Runtime.getRuntime().exec(cmd, null, new File(pythonLibPath));
            // 记录dos命令的返回信息
            StringBuilder resStr = new StringBuilder();
            // 获取返回信息的流
            BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String res;
            while ((res = bReader.readLine()) != null) {
                resStr.append(res).append("\n");
            }
            LOGGER.info(resStr.toString());
            bReader.close();
            process.getOutputStream().close();
        } catch (Exception e) {
            LOGGER.error("" + e);
        }
    }
}
