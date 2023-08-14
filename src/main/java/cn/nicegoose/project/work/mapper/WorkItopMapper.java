package cn.nicegoose.project.work.mapper;

import java.util.List;
import cn.nicegoose.project.work.domain.WorkItop;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * itop信息Mapper接口
 * 
 * @author nicegoose
 * @date 2023-08-14
 */
public interface WorkItopMapper extends BaseMapper<WorkItop>
{
    /**
     * 查询itop信息
     * 
     * @param UserRequestId itop信息主键
     * @return itop信息
     */
    public WorkItop selectWorkItopByUserRequestId(Long UserRequestId);

    /**
     * 查询itop信息列表
     * 
     * @param workItop itop信息
     * @return itop信息集合
     */
    public List<WorkItop> selectWorkItopList(WorkItop workItop);

    /**
     * 新增itop信息
     * 
     * @param workItop itop信息
     * @return 结果
     */
    public int insertWorkItop(WorkItop workItop);

    /**
     * 修改itop信息
     * 
     * @param workItop itop信息
     * @return 结果
     */
    public int updateWorkItop(WorkItop workItop);

    /**
     * 删除itop信息
     * 
     * @param UserRequestId itop信息主键
     * @return 结果
     */
    public int deleteWorkItopByUserRequestId(Long UserRequestId);

    /**
     * 批量删除itop信息
     * 
     * @param UserRequestIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkItopByUserRequestIds(Long[] UserRequestIds);

    @Override
    @InterceptorIgnore(blockAttack = "true")
    int delete(Wrapper<WorkItop> queryWrapper);
}
