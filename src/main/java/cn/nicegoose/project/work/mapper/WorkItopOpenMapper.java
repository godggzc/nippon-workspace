package cn.nicegoose.project.work.mapper;

import java.util.List;

import cn.nicegoose.project.work.domain.WorkItop;
import cn.nicegoose.project.work.domain.WorkItopOpen;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * itop信息未处理Mapper接口
 * 
 * @author nicegoose
 * @date 2023-08-15
 */
public interface WorkItopOpenMapper extends BaseMapper<WorkItopOpen>
{
    /**
     * 查询itop信息未处理
     * 
     * @param userRequestId itop信息未处理主键
     * @return itop信息未处理
     */
    public WorkItopOpen selectWorkItopOpenByUserRequestId(Long userRequestId);

    /**
     * 查询itop信息未处理列表
     * 
     * @param workItopOpen itop信息未处理
     * @return itop信息未处理集合
     */
    public List<WorkItopOpen> selectWorkItopOpenList(WorkItopOpen workItopOpen);

    /**
     * 新增itop信息未处理
     * 
     * @param workItopOpen itop信息未处理
     * @return 结果
     */
    public int insertWorkItopOpen(WorkItopOpen workItopOpen);

    /**
     * 修改itop信息未处理
     * 
     * @param workItopOpen itop信息未处理
     * @return 结果
     */
    public int updateWorkItopOpen(WorkItopOpen workItopOpen);

    /**
     * 删除itop信息未处理
     * 
     * @param userRequestId itop信息未处理主键
     * @return 结果
     */
    public int deleteWorkItopOpenByUserRequestId(Long userRequestId);

    /**
     * 批量删除itop信息未处理
     * 
     * @param userRequestIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkItopOpenByUserRequestIds(Long[] userRequestIds);

    @Override
    @InterceptorIgnore(blockAttack = "true")
    int delete(Wrapper<WorkItopOpen> queryWrapper);
}
