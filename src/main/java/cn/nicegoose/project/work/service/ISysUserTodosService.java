package cn.nicegoose.project.work.service;

import java.util.List;
import cn.nicegoose.project.work.domain.SysUserTodos;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 代做事项Service接口
 * 
 * @author nicegoose
 * @date 2023-08-10
 */
public interface ISysUserTodosService extends IService<SysUserTodos>
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
     * 批量删除代做事项
     * 
     * @param todoIds 需要删除的代做事项主键集合
     * @return 结果
     */
    public int deleteSysUserTodosByTodoIds(Long[] todoIds);

    /**
     * 删除代做事项信息
     * 
     * @param todoId 代做事项主键
     * @return 结果
     */
    public int deleteSysUserTodosByTodoId(Long todoId);

    /**
     * 根据用户id查询代办事项
     * @param userId
     * @return
     */
    public List<SysUserTodos> selectSysUserTodosByUserId(Long userId);


}
