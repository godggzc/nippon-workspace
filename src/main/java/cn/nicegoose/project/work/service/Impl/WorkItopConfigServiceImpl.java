package cn.nicegoose.project.work.service.Impl;

import java.util.List;

import cn.nicegoose.project.work.domain.SysUserTodos;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.nicegoose.project.work.mapper.WorkItopConfigMapper;
import cn.nicegoose.project.work.domain.WorkItopConfig;
import cn.nicegoose.project.work.service.IWorkItopConfigService;

/**
 * itop用户配置Service业务层处理
 * 
 * @author nicegoose
 * @date 2023-08-14
 */
@Service
public class WorkItopConfigServiceImpl extends ServiceImpl<WorkItopConfigMapper, WorkItopConfig> implements IWorkItopConfigService
{
    @Autowired
    private WorkItopConfigMapper workItopConfigMapper;

    /**
     * 查询itop用户配置
     * 
     * @param configId itop用户配置主键
     * @return itop用户配置
     */
    @Override
    public WorkItopConfig selectWorkItopConfigByConfigId(Long configId)
    {
        return workItopConfigMapper.selectWorkItopConfigByConfigId(configId);
    }

    /**
     * 查询itop用户配置列表
     * 
     * @param workItopConfig itop用户配置
     * @return itop用户配置
     */
    @Override
    public List<WorkItopConfig> selectWorkItopConfigList(WorkItopConfig workItopConfig)
    {
        return workItopConfigMapper.selectWorkItopConfigList(workItopConfig);
    }

    /**
     * 新增itop用户配置
     * 
     * @param workItopConfig itop用户配置
     * @return 结果
     */
    @Override
    public int insertWorkItopConfig(WorkItopConfig workItopConfig)
    {
        return workItopConfigMapper.insertWorkItopConfig(workItopConfig);
    }

    /**
     * 修改itop用户配置
     * 
     * @param workItopConfig itop用户配置
     * @return 结果
     */
    @Override
    public int updateWorkItopConfig(WorkItopConfig workItopConfig)
    {
        return workItopConfigMapper.updateWorkItopConfig(workItopConfig);
    }

    /**
     * 批量删除itop用户配置
     * 
     * @param configIds 需要删除的itop用户配置主键
     * @return 结果
     */
    @Override
    public int deleteWorkItopConfigByConfigIds(Long[] configIds)
    {
        return workItopConfigMapper.deleteWorkItopConfigByConfigIds(configIds);
    }

    /**
     * 删除itop用户配置信息
     * 
     * @param configId itop用户配置主键
     * @return 结果
     */
    @Override
    public int deleteWorkItopConfigByConfigId(Long configId)
    {
        return workItopConfigMapper.deleteWorkItopConfigByConfigId(configId);
    }

    /**
    * @Description: 根据用户id查询itop用户配置
    * @Author: Riche_Gzc
    * @Date: 2023/8/14
    */
    @Override
    public WorkItopConfig selectWorkItopConfigByUserId(Long userId) {
        QueryWrapper<WorkItopConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        //使用mybatis-plus的warpper
        return workItopConfigMapper.selectOne(queryWrapper);
    }

}
