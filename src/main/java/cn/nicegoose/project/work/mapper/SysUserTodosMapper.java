package cn.nicegoose.project.work.mapper;

import java.util.List;
import cn.nicegoose.project.work.domain.SysUserTodos;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 代做事项Mapper接口
 * 
 * @author nicegoose
 * @date 2023-08-10
 */
public interface SysUserTodosMapper extends BaseMapper<SysUserTodos>
{
    /**
     * 查询代做事项
     * 
     * @param todoId 代做事项主键
     * @return 代做事项
     */
    public SysUserTodos selectSysUserTodosByTodoId(Long todoId);

    /**
     * 查询代做事项列表
     * 
     * @param sysUserTodos 代做事项
     * @return 代做事项集合
     */
    public List<SysUserTodos> selectSysUserTodosList(SysUserTodos sysUserTodos);

    /**
     * 新增代做事项
     * 
     * @param sysUserTodos 代做事项
     * @return 结果
     */
    public int insertSysUserTodos(SysUserTodos sysUserTodos);

    /**
     * 修改代做事项
     * 
     * @param sysUserTodos 代做事项
     * @return 结果
     */
    public int updateSysUserTodos(SysUserTodos sysUserTodos);

    /**
     * 删除代做事项
     * 
     * @param todoId 代做事项主键
     * @return 结果
     */
    public int deleteSysUserTodosByTodoId(Long todoId);

    /**
     * 批量删除代做事项
     * 
     * @param todoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserTodosByTodoIds(Long[] todoIds);
}
