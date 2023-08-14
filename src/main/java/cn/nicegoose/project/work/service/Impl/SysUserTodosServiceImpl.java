package cn.nicegoose.project.work.service.Impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.nicegoose.project.work.mapper.SysUserTodosMapper;
import cn.nicegoose.project.work.domain.SysUserTodos;
import cn.nicegoose.project.work.service.ISysUserTodosService;

/**
 * 代做事项Service业务层处理
 * 
 * @author nicegoose
 * @date 2023-08-10
 */
@Service
public class SysUserTodosServiceImpl  extends ServiceImpl<SysUserTodosMapper, SysUserTodos> implements ISysUserTodosService
{
    @Autowired
    private SysUserTodosMapper sysUserTodosMapper;

    /**
     * 查询代做事项
     * 
     * @param todoId 代做事项主键
     * @return 代做事项
     */
    @Override
    public SysUserTodos selectSysUserTodosByTodoId(Long todoId)
    {
        return sysUserTodosMapper.selectSysUserTodosByTodoId(todoId);
    }

    /**
     * 查询代做事项列表
     * 
     * @param sysUserTodos 代做事项
     * @return 代做事项
     */
    @Override
    public List<SysUserTodos> selectSysUserTodosList(SysUserTodos sysUserTodos)
    {
        return sysUserTodosMapper.selectSysUserTodosList(sysUserTodos);
    }

    /**
     * 新增代做事项
     * 
     * @param sysUserTodos 代做事项
     * @return 结果
     */
    @Override
    public int insertSysUserTodos(SysUserTodos sysUserTodos)
    {
        return sysUserTodosMapper.insertSysUserTodos(sysUserTodos);
    }

    /**
     * 修改代做事项
     * 
     * @param sysUserTodos 代做事项
     * @return 结果
     */
    @Override
    public int updateSysUserTodos(SysUserTodos sysUserTodos)
    {
        return sysUserTodosMapper.updateSysUserTodos(sysUserTodos);
    }

    /**
     * 批量删除代做事项
     * 
     * @param todoIds 需要删除的代做事项主键
     * @return 结果
     */
    @Override
    public int deleteSysUserTodosByTodoIds(Long[] todoIds)
    {
        return sysUserTodosMapper.deleteSysUserTodosByTodoIds(todoIds);
    }

    /**
     * 删除代做事项信息
     * 
     * @param todoId 代做事项主键
     * @return 结果
     */
    @Override
    public int deleteSysUserTodosByTodoId(Long todoId)
    {
        return sysUserTodosMapper.deleteSysUserTodosByTodoId(todoId);
    }

    /**
     * 根据用户id查询代办事项
     * @param userId
     * @return
     */
    @Override
    public List<SysUserTodos> selectSysUserTodosByUserId(Long userId){
    	QueryWrapper<SysUserTodos> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
            //通过status排序
        queryWrapper.orderByAsc("todo_status");
        return sysUserTodosMapper.selectList(queryWrapper);
    }
}
