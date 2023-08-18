package cn.nicegoose.project.work.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import cn.nicegoose.framework.aspectj.lang.annotation.Anonymous;
import cn.nicegoose.project.work.domain.WorkItopConfig;
import cn.nicegoose.project.work.domain.WorkItopOpen;
import cn.nicegoose.project.work.service.IWorkItopConfigService;
import cn.nicegoose.project.work.service.IWorkItopOpenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.nicegoose.framework.aspectj.lang.annotation.Log;
import cn.nicegoose.framework.aspectj.lang.enums.BusinessType;
import cn.nicegoose.project.work.domain.SysUserTodos;
import cn.nicegoose.project.work.service.ISysUserTodosService;
import cn.nicegoose.framework.web.controller.BaseController;
import cn.nicegoose.framework.web.domain.AjaxResult;
import cn.nicegoose.common.utils.poi.ExcelUtil;
import cn.nicegoose.framework.web.page.TableDataInfo;

/**
 * 代做事项Controller
 * 
 * @author nicegoose
 * @date 2023-08-10
 */
@RestController
@RequestMapping("/work/todos")
public class SysUserTodosController extends BaseController
{
    @Autowired
    private ISysUserTodosService sysUserTodosService;

    @Autowired
    private IWorkItopOpenService workItopOpenService;

    @Autowired
    private IWorkItopConfigService workItopConfigService;

    /**
     * 查询代做事项列表
     */
    @PreAuthorize("@ss.hasPermi('work:todos:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUserTodos sysUserTodos)
    {
        startPage();
        List<SysUserTodos> list = sysUserTodosService.selectSysUserTodosList(sysUserTodos);
        return getDataTable(list);
    }

    /**
     * 导出代做事项列表
     */
    @PreAuthorize("@ss.hasPermi('work:todos:export')")
    @Log(title = "代做事项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUserTodos sysUserTodos)
    {
        List<SysUserTodos> list = sysUserTodosService.selectSysUserTodosList(sysUserTodos);
        ExcelUtil<SysUserTodos> util = new ExcelUtil<SysUserTodos>(SysUserTodos.class);
        util.exportExcel(response, list, "代做事项数据");
    }

    /**
     * 获取代做事项详细信息
     */
    @PreAuthorize("@ss.hasPermi('work:todos:query')")
    @GetMapping(value = "/{todoId}")
    public AjaxResult getInfo(@PathVariable("todoId") Long todoId)
    {
        return success(sysUserTodosService.selectSysUserTodosByTodoId(todoId));
    }

    /**
     * 新增代做事项
     */
    @PreAuthorize("@ss.hasPermi('work:todos:add')")
    @Log(title = "代做事项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysUserTodos sysUserTodos)
    {

        return toAjax(sysUserTodosService.insertSysUserTodos(sysUserTodos));
    }
    /**
     * 新增代做事项
     */
    @PreAuthorize("@ss.hasAnyPermi('workspace:todos:add,workspace')")
    @Log(title = "个人添加代做事项", businessType = BusinessType.INSERT)
    @PostMapping("/user")
    public AjaxResult addbyuser(@RequestBody SysUserTodos sysUserTodos)

    {   Long userId = getUserId();
        sysUserTodos.setUserId(userId);
        return toAjax(sysUserTodosService.insertSysUserTodos(sysUserTodos));
    }

    /**
     * 修改代做事项
     */
    @PreAuthorize("@ss.hasAnyPermi('work:todos:edit,workspace')")
    @Log(title = "代做事项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserTodos sysUserTodos)
    {
        return toAjax(sysUserTodosService.updateSysUserTodos(sysUserTodos));
    }

    /**
     * 删除代做事项
     */
    @PreAuthorize("@ss.hasAnyPermi('work:todos:remove,workspace')")
    @Log(title = "代做事项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{todoIds}")
    public AjaxResult remove(@PathVariable Long[] todoIds)
    {
        return toAjax(sysUserTodosService.deleteSysUserTodosByTodoIds(todoIds));
    }

    /**
    * @Description: 根据登入用户查询代办事项
    * @Author: Riche_Gzc
    * @Date: 2023/8/10
    */
    @PreAuthorize("@ss.hasPermi('workspace')")
    @GetMapping(value = "/user")
    public AjaxResult selectSysUserTodosByUserId()
    {   startPage();
        Long userId = getUserId();
        return success(sysUserTodosService.selectSysUserTodosByUserId(userId));
    }

    /**
    * @Description: 自动设置代办事项
    * @Author: Riche_Gzc
    * @Date: 2023/8/15
    */
    @PreAuthorize("@ss.hasPermi('workspace')")
    @GetMapping(value = "/auto")
    public AjaxResult autoSetSysUserTodos()
    {
        Long userId = getUserId();
        //获取配置信息
        WorkItopConfig config = workItopConfigService.selectWorkItopConfigByUserId(userId);
        if (config == null){
            return success("没有配置信息");
        }
        //如果config.getSyncStatus() == 1 则同步
        if(config.getSyncStatus() == 1&&config.getUserName()!=null){
            //新new一个wrapper
            QueryWrapper<WorkItopOpen> queryWrapper = new QueryWrapper<>();
            if (Objects.equals(config.getUserName(), "")) {
              return success("没有配置信息");
            }
            //设置查询条件
            queryWrapper.eq("agent_name",config.getUserName());
            //查询
            List<WorkItopOpen> list = workItopOpenService.list(queryWrapper);
            //创建一个Todolist
            List<SysUserTodos> todolist = sysUserTodosService.selectSysUserTodosByUserId(userId);
            List<SysUserTodos> newtodolist = new ArrayList<>();
            for (WorkItopOpen workItopOpen : list) {
                SysUserTodos newtodo = new SysUserTodos();
                newtodo.setTodoName("itop:" + workItopOpen.getTitle());
                newtodo.setTodoComment(workItopOpen.getHyperlink());
                newtodo.setUserId(userId);
                newtodo.setTodoStatus("0");
                newtodolist.add(newtodo);
                for (SysUserTodos sysUserTodos : todolist) {
                    if (sysUserTodos.getTodoComment() != null && sysUserTodos.getTodoName().startsWith("itop:")) {
                        if (sysUserTodos.getTodoName().equals(newtodo.getTodoName())) {
                            newtodolist.remove(newtodo);
                            sysUserTodos.setTodoStatus("存在");
                        }
                    }
                }
            }
            sysUserTodosService.saveBatch(newtodolist);
            for (SysUserTodos sysUserTodos : todolist) {
                if(sysUserTodos.getTodoStatus().equals("存在")){
                    sysUserTodos.setTodoStatus("0");
                    sysUserTodosService.updateSysUserTodos(sysUserTodos);
                }else{
                    if (!Objects.equals(sysUserTodos.getTodoComment(), "") && sysUserTodos.getTodoName().startsWith("itop:")) {
                    sysUserTodos.setTodoStatus("1");
                    sysUserTodosService.updateSysUserTodos(sysUserTodos);}
                }
            }
            return success("同步成功");
        }

        return success();
    }

    /**
    * @Description: 根据用户id删除已完成的代办事项
    * @Author: Riche_Gzc
    * @Date: 2023/8/16
    */
    @PreAuthorize("@ss.hasPermi('workspace')")
    @DeleteMapping(value = "/user")
    public AjaxResult deleteSysUserTodosByUserId()
    {
       QueryWrapper<SysUserTodos> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("user_id",getUserId());
        queryWrapper.eq("todo_status","1");
        return toAjax(sysUserTodosService.remove(queryWrapper));
    }



}
