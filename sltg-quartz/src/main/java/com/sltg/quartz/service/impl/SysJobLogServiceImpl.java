package com.sltg.quartz.service.impl;

import java.util.List;

import com.sltg.common.constant.ScheduleConstants;
import com.sltg.quartz.domain.SysJob;
import com.sltg.quartz.mapper.SysJobMapper;
import com.sltg.quartz.util.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sltg.quartz.domain.SysJobLog;
import com.sltg.quartz.mapper.SysJobLogMapper;
import com.sltg.quartz.service.SysJobLogService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务调度日志信息 服务层
 * 
 * @author styra~
 */
@Service
public class SysJobLogServiceImpl implements SysJobLogService {
    @Autowired
    private SysJobLogMapper jobLogMapper;

    @Autowired
    private SysJobMapper jobMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog) {
        return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId) {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog) {
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds) {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId) {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog() {
        jobLogMapper.cleanJobLog();
    }

    /**
     * 重新运行任务
     *
     * @param jobLog 调度日志信息
     */
    @Override
    @Transactional
    public void reRun(SysJobLog jobLog) throws SchedulerException {
        Long jobLogId = jobLog.getJobLogId();
        SysJobLog sysJobLog = selectJobLogById(jobLogId);
        SysJob properties = jobMapper.selectJobById(sysJobLog.getOriginalJobId());
        properties.setInvokeTarget(sysJobLog.getInvokeTarget());

        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(properties.getJobId(), properties.getJobGroup()), dataMap);
    }
}
