package cn.nicegoose.project.work.service.Impl;

import java.util.List;

import cn.nicegoose.project.work.domain.SysUserTodos;
import cn.nicegoose.project.work.mapper.SysUserTodosMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.nicegoose.project.work.mapper.WorkItopMapper;
import cn.nicegoose.project.work.domain.WorkItop;
import cn.nicegoose.project.work.service.IWorkItopService;

/**
 * itop信息Service业务层处理
 * 
 * @author nicegoose
 * @date 2023-08-14
 */
@Service
public class WorkItopServiceImpl  extends ServiceImpl<WorkItopMapper, WorkItop> implements IWorkItopService
{
    @Autowired
    private WorkItopMapper workItopMapper;

    /**
     * 查询itop信息
     * 
     * @param UserRequestId itop信息主键
     * @return itop信息
     */
    @Override
    public WorkItop selectWorkItopByUserRequestId(Long UserRequestId)
    {
        return workItopMapper.selectWorkItopByUserRequestId(UserRequestId);
    }

    /**
     * 查询itop信息列表
     * 
     * @param workItop itop信息
     * @return itop信息
     */
    @Override
    public List<WorkItop> selectWorkItopList(WorkItop workItop)
    {
        return workItopMapper.selectWorkItopList(workItop);
    }

    /**
     * 新增itop信息
     * 
     * @param workItop itop信息
     * @return 结果
     */
    @Override
    public int insertWorkItop(WorkItop workItop)
    {
        return workItopMapper.insertWorkItop(workItop);
    }

    /**
     * 修改itop信息
     * 
     * @param workItop itop信息
     * @return 结果
     */
    @Override
    public int updateWorkItop(WorkItop workItop)
    {
        return workItopMapper.updateWorkItop(workItop);
    }

    /**
     * 批量删除itop信息
     * 
     * @param UserRequestIds 需要删除的itop信息主键
     * @return 结果
     */
    @Override
    public int deleteWorkItopByUserRequestIds(Long[] UserRequestIds)
    {
        return workItopMapper.deleteWorkItopByUserRequestIds(UserRequestIds);
    }

    /**
     * 删除itop信息信息
     * 
     * @param UserRequestId itop信息主键
     * @return 结果
     */
    @Override
    public int deleteWorkItopByUserRequestId(Long UserRequestId)
    {
        return workItopMapper.deleteWorkItopByUserRequestId(UserRequestId);
    }

    /**
    * @Description: 清空itop信息表所有
    * @Author: Riche_Gzc
    * @Date: 2023/8/14
    */
    @Override
    public int deleteWorkItopAll()
    {
        return workItopMapper.delete(null);
    }



}
