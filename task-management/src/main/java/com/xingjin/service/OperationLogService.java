package com.xingjin.service;

import com.xingjin.entity.OperationLog;
import com.xingjin.mapper.OperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 操作日志服务类
 * 提供操作日志的保存和查询功能
 */
@Service
public class OperationLogService {
    @Autowired
    private OperationLogMapper logMapper;

    /**
     * 保存操作日志
     * @param log 要保存的操作日志对象
     */
    public void save(OperationLog log) {
        logMapper.insert(log);
    }

    /**
     * 查询最近的操作日志
     * @param limit 查询条数限制
     * @return 最近的操作日志列表
     */
    public List<OperationLog> findRecent(int limit) {
        return logMapper.findRecent(limit);
    }
}

