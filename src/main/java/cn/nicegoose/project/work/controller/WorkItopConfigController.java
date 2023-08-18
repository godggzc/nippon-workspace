package cn.nicegoose.project.work.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import cn.nicegoose.project.work.domain.WorkItopConfig;
import cn.nicegoose.project.work.service.IWorkItopConfigService;
import cn.nicegoose.framework.web.controller.BaseController;
import cn.nicegoose.framework.web.domain.AjaxResult;
import cn.nicegoose.common.utils.poi.ExcelUtil;
import cn.nicegoose.framework.web.page.TableDataInfo;

/**
 * itop用户配置Controller
 * 
 * @author nicegoose
 * @date 2023-08-14
 */
@RestController
@RequestMapping("/work/config")
public class WorkItopConfigController extends BaseController
{
    @Autowired
    private IWorkItopConfigService workItopConfigService;

    /**
     * 查询itop用户配置列表
     */
    @PreAuthorize("@ss.hasPermi('work:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(WorkItopConfig workItopConfig)
    {
        startPage();
        List<WorkItopConfig> list = workItopConfigService.selectWorkItopConfigList(workItopConfig);
        return getDataTable(list);
    }

    /**
     * 导出itop用户配置列表
     */
    @PreAuthorize("@ss.hasPermi('work:config:export')")
    @Log(title = "itop用户配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkItopConfig workItopConfig)
    {
        List<WorkItopConfig> list = workItopConfigService.selectWorkItopConfigList(workItopConfig);
        ExcelUtil<WorkItopConfig> util = new ExcelUtil<WorkItopConfig>(WorkItopConfig.class);
        util.exportExcel(response, list, "itop用户配置数据");
    }

    /**
     * 获取itop用户配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('work:config:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable("configId") Long configId)
    {
        return success(workItopConfigService.selectWorkItopConfigByConfigId(configId));
    }

    /**
     * 新增itop用户配置
     */
    @PreAuthorize("@ss.hasPermi('work:config:add')")
    @Log(title = "itop用户配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkItopConfig workItopConfig)
    {
        return toAjax(workItopConfigService.insertWorkItopConfig(workItopConfig));
    }

    @PreAuthorize("@ss.hasAnyPermi('work:config:add,workspace')")
    @Log(title = "itop用户配置", businessType = BusinessType.INSERT)
    @PostMapping("/user")
    public AjaxResult add1(@RequestBody WorkItopConfig workItopConfig)
    {   Long userId = getUserId();
        workItopConfig.setUserId(userId);
        //先查询是否存在
        if(workItopConfigService.selectWorkItopConfigByUserId(userId)!=null){
            //如果存在，就更新
            return toAjax(workItopConfigService.updateWorkItopConfig(workItopConfig));
        }
        return toAjax(workItopConfigService.insertWorkItopConfig(workItopConfig));
    }

    /**
     * 修改itop用户配置
     */
    @PreAuthorize("@ss.hasPermi('work:config:edit')")
    @Log(title = "itop用户配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkItopConfig workItopConfig)
    {
        return toAjax(workItopConfigService.updateWorkItopConfig(workItopConfig));
    }

    /**
     * 删除itop用户配置
     */
    @PreAuthorize("@ss.hasPermi('work:config:remove')")
    @Log(title = "itop用户配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(workItopConfigService.deleteWorkItopConfigByConfigIds(configIds));
    }

    /**
     * @Description: 根据登入用户查询itop用户配置
     * @Author: Riche_Gzc
     * @Date: 2023/8/10
     */
    @PreAuthorize("@ss.hasAnyPermi('work:config:query,workspace')")
    @GetMapping(value = "/getByLoginUser")
    public AjaxResult getByLoginUser()
    {   Long userId = getUserId();
        //如果workItopConfigService.selectWorkItopConfigByUserId(userId)存在
        if(workItopConfigService.selectWorkItopConfigByUserId(userId)!=null){
            return success(workItopConfigService.selectWorkItopConfigByUserId(userId));
        }
        return success();
    }
}
