package com.sltg.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author styra
 * @title: PythonUtils
 * @projectName SLTG-VUE
 * @description: 执行python脚本
 * @date 2022/2/4 22:19
 */
public class PythonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PythonUtils.class);

    private static final String EXECUTE_SUCCESS = "执行成功";

    public String executePythonScript(String[] commands, String pythonLibPath) {
        // 记录dos命令的返回信息
        StringBuilder resStr = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(commands, null, new File(pythonLibPath));
            // 获取返回信息的流
            BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));
            String res;
            while ((res = bReader.readLine()) != null) {
                if (resStr.length() != 0) {
                    resStr.append("\n");
                }
                resStr.append(res);
            }
            LOGGER.info(resStr.toString());
            bReader.close();
            process.getOutputStream().close();

            if (resStr.length() < 1) {
                return EXECUTE_SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.error("executePythonScript exception:" + e);
            resStr.append(e);
        }
        return resStr.toString();
    }
}
