package com.sltg.quartz.util;

import java.util.Date;

import com.sltg.common.utils.DateUtils;
import com.sltg.quartz.domain.SysJob;
import com.sltg.quartz.domain.SysJobLog;
import com.sltg.quartz.service.SysJobLogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sltg.common.constant.Constants;
import com.sltg.common.constant.ScheduleConstants;
import com.sltg.common.utils.ExceptionUtil;
import com.sltg.common.utils.StringUtils;
import com.sltg.common.utils.bean.BeanUtils;
import com.sltg.common.utils.spring.SpringUtils;

/**
 * 抽象quartz调用
 *
 * @author styra~
 */
public abstract class AbstractQuartzJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQuartzJob.class);

    private static final String EXECUTE_CURRENT_DATE = "currentDate";

    /**
     * 线程本地变量
     */
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SysJob sysJob = new SysJob();
        BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
        try {
            before(context, sysJob);
            doExecute(context, sysJob);
            after(context, sysJob, null);
        } catch (Exception e) {
             LOGGER.error("任务执行异常  - ：", e);
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void before(JobExecutionContext context, SysJob sysJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setOriginalJobId(sysJob.getJobId());
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        String invokeTarget = sysJob.getInvokeTarget();
        if (invokeTarget.contains(EXECUTE_CURRENT_DATE)) {
            sysJobLog.setInvokeTarget(invokeTarget.replace(EXECUTE_CURRENT_DATE,
                DateUtils.parseDateBeforeToStr(DateUtils.YYYY_MM_DD)));
        } else {
            sysJobLog.setInvokeTarget(invokeTarget);
        }
        sysJobLog.setStartTime(startTime);
        sysJobLog.setStopTime(new Date());
        long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobRuntime(runMs + "毫秒");
        if (e != null) {
            sysJobLog.setStatus(Constants.FAIL);
            String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setJobMessage(StringUtils.substring(sysJob.getMessage(), 0, 2000));
            sysJobLog.setStatus(Constants.SUCCESS);
        }

        // 写入数据库当中
        SpringUtils.getBean(SysJobLogService.class).addJobLog(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
