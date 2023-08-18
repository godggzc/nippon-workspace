package cn.nicegoose.project.work.service.Impl;

import java.util.List;

import cn.nicegoose.project.work.domain.WorkItop;
import cn.nicegoose.project.work.mapper.WorkItopMapper;
import cn.nicegoose.project.work.service.IWorkItopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.nicegoose.project.work.mapper.WorkItopOpenMapper;
import cn.nicegoose.project.work.domain.WorkItopOpen;
import cn.nicegoose.project.work.service.IWorkItopOpenService;

/**
 * itop信息未处理Service业务层处理
 * 
 * @author nicegoose
 * @date 2023-08-15
 */
@Service
public class WorkItopOpenServiceImpl extends ServiceImpl<WorkItopOpenMapper, WorkItopOpen> implements IWorkItopOpenService
{
    @Autowired
    private WorkItopOpenMapper workItopOpenMapper;

    /**
     * 查询itop信息未处理
     * 
     * @param userRequestId itop信息未处理主键
     * @return itop信息未处理
     */
    @Override
    public WorkItopOpen selectWorkItopOpenByUserRequestId(Long userRequestId)
    {
        return workItopOpenMapper.selectWorkItopOpenByUserRequestId(userRequestId);
    }

    /**
     * 查询itop信息未处理列表
     * 
     * @param workItopOpen itop信息未处理
     * @return itop信息未处理
     */
    @Override
    public List<WorkItopOpen> selectWorkItopOpenList(WorkItopOpen workItopOpen)
    {
        return workItopOpenMapper.selectWorkItopOpenList(workItopOpen);
    }

    /**
     * 新增itop信息未处理
     * 
     * @param workItopOpen itop信息未处理
     * @return 结果
     */
    @Override
    public int insertWorkItopOpen(WorkItopOpen workItopOpen)
    {
        return workItopOpenMapper.insertWorkItopOpen(workItopOpen);
    }

    /**
     * 修改itop信息未处理
     * 
     * @param workItopOpen itop信息未处理
     * @return 结果
     */
    @Override
    public int updateWorkItopOpen(WorkItopOpen workItopOpen)
    {
        return workItopOpenMapper.updateWorkItopOpen(workItopOpen);
    }

    /**
     * 批量删除itop信息未处理
     * 
     * @param userRequestIds 需要删除的itop信息未处理主键
     * @return 结果
     */
    @Override
    public int deleteWorkItopOpenByUserRequestIds(Long[] userRequestIds)
    {
        return workItopOpenMapper.deleteWorkItopOpenByUserRequestIds(userRequestIds);
    }

    /**
     * 删除itop信息未处理信息
     * 
     * @param userRequestId itop信息未处理主键
     * @return 结果
     */
    @Override
    public int deleteWorkItopOpenByUserRequestId(Long userRequestId)
    {
        return workItopOpenMapper.deleteWorkItopOpenByUserRequestId(userRequestId);
    }
}
