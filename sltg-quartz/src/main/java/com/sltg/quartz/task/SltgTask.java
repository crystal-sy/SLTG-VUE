package com.sltg.quartz.task;

import com.sltg.common.utils.DateUtils;
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

    private static final String EXECUTE_SUCCESS = "执行成功";

    private static final String EXECUTE_CURRENT_DATE = "currentDate";

    @Value("${sltg.python.lib-path}")
    private String pythonLibPath;

    @Value("${sltg.python.script-path}")
    private String pythonScriptPath;

    public String sltgMultipleParams(String scriptName, String scriptDate) {
        LOGGER.info(StringUtils.format("执行多参方法： scriptName:{}, 日期:{}", scriptName, scriptDate));
        String date = scriptDate;
        if (EXECUTE_CURRENT_DATE.equals(scriptDate)) {
            date = DateUtils.parseDateBeforeToStr(DateUtils.YYYY_MM_DD);
        }
        return executePythonScript(new String[] { "cmd", "/c", "python " + pythonScriptPath + scriptName, date });
    }

    public String sltgParams(String scriptName) {
        LOGGER.info("执行有参方法：" + scriptName);
        return executePythonScript(new String[] { "cmd", "/c", "python " + pythonScriptPath + scriptName });
    }

    public String sltgNoParams() {
        LOGGER.info("执行无参方法");
        return EXECUTE_SUCCESS;
    }

    private String executePythonScript(String[] commands) {
        // 记录dos命令的返回信息
        StringBuilder resStr = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(commands, null, new File(pythonLibPath));
            // 获取返回信息的流
            BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(),
                StandardCharsets.UTF_8));
            String res;
            while ((res = bReader.readLine()) != null) {
                resStr.append(res).append("\n");
            }
            LOGGER.info(resStr.toString());
            bReader.close();
            process.getOutputStream().close();

            if (resStr.length() < 1) {
                return EXECUTE_SUCCESS;
            }
        } catch (Exception e) {
            LOGGER.error("" + e);
            resStr.append(e);
        }
        return resStr.toString();
    }
}
