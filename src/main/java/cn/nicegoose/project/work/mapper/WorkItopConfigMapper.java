package cn.nicegoose.project.work.mapper;

import java.util.List;
import cn.nicegoose.project.work.domain.WorkItopConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * itop用户配置Mapper接口
 * 
 * @author nicegoose
 * @date 2023-08-14
 */
public interface WorkItopConfigMapper  extends BaseMapper<WorkItopConfig>
{
    /**
     * 查询itop用户配置
     * 
     * @param configId itop用户配置主键
     * @return itop用户配置
     */
    public WorkItopConfig selectWorkItopConfigByConfigId(Long configId);

    /**
     * 查询itop用户配置列表
     * 
     * @param workItopConfig itop用户配置
     * @return itop用户配置集合
     */
    public List<WorkItopConfig> selectWorkItopConfigList(WorkItopConfig workItopConfig);

    /**
     * 新增itop用户配置
     * 
     * @param workItopConfig itop用户配置
     * @return 结果
     */
    public int insertWorkItopConfig(WorkItopConfig workItopConfig);

    /**
     * 修改itop用户配置
     * 
     * @param workItopConfig itop用户配置
     * @return 结果
     */
    public int updateWorkItopConfig(WorkItopConfig workItopConfig);

    /**
     * 删除itop用户配置
     * 
     * @param configId itop用户配置主键
     * @return 结果
     */
    public int deleteWorkItopConfigByConfigId(Long configId);

    /**
     * 批量删除itop用户配置
     * 
     * @param configIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkItopConfigByConfigIds(Long[] configIds);
}
