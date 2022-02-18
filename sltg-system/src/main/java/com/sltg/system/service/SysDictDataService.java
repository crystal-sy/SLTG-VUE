package com.sltg.system.service;

import com.sltg.common.core.domain.entity.SysDictData;

import java.util.List;

/**
 * 字典 业务层
 * 
 * @author styra~
 */
public interface SysDictDataService {
    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataByType(String dictType);
}
