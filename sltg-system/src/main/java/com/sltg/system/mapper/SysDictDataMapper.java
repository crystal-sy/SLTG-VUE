package com.sltg.system.mapper;

import java.util.List;
import com.sltg.common.core.domain.entity.SysDictData;

/**
 * 字典表 数据层
 * 
 * @author styra~
 */
public interface SysDictDataMapper {
    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataByType(String dictType);
}
