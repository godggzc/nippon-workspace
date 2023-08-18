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
import cn.nicegoose.project.work.domain.WorkItopOpen;
import cn.nicegoose.project.work.service.IWorkItopOpenService;
import cn.nicegoose.framework.web.controller.BaseController;
import cn.nicegoose.framework.web.domain.AjaxResult;
import cn.nicegoose.common.utils.poi.ExcelUtil;
import cn.nicegoose.framework.web.page.TableDataInfo;

/**
 * itop信息未处理Controller
 * 
 * @author nicegoose
 * @date 2023-08-15
 */
@RestController
@RequestMapping("/work/itopOpen")
public class WorkItopOpenController extends BaseController
{
    @Autowired
    private IWorkItopOpenService workItopOpenService;

    /**
     * 查询itop信息未处理列表
     */
    @PreAuthorize("@ss.hasAnyPermi('work:itopOpen:list,workspace')")
    @GetMapping("/list")
    public TableDataInfo list(WorkItopOpen workItopOpen)
    {
        startPage();
        List<WorkItopOpen> list = workItopOpenService.selectWorkItopOpenList(workItopOpen);
        return getDataTable(list);
    }

    /**
     * 导出itop信息未处理列表
     */
    @PreAuthorize("@ss.hasPermi('work:itopOpen:export')")
    @Log(title = "itop信息未处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkItopOpen workItopOpen)
    {
        List<WorkItopOpen> list = workItopOpenService.selectWorkItopOpenList(workItopOpen);
        ExcelUtil<WorkItopOpen> util = new ExcelUtil<WorkItopOpen>(WorkItopOpen.class);
        util.exportExcel(response, list, "itop信息未处理数据");
    }

    /**
     * 获取itop信息未处理详细信息
     */
    @PreAuthorize("@ss.hasPermi('work:itopOpen:query')")
    @GetMapping(value = "/{userRequestId}")
    public AjaxResult getInfo(@PathVariable("userRequestId") Long userRequestId)
    {
        return success(workItopOpenService.selectWorkItopOpenByUserRequestId(userRequestId));
    }

    /**
     * 新增itop信息未处理
     */
    @PreAuthorize("@ss.hasAnyPermi('work:itopOpen:add,workspace')")
    @Log(title = "itop信息未处理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkItopOpen workItopOpen)
    {
        return toAjax(workItopOpenService.insertWorkItopOpen(workItopOpen));
    }

    /**
     * 修改itop信息未处理
     */
    @PreAuthorize("@ss.hasAnyPermi('work:itopOpen:edit,workspace')")
    @Log(title = "itop信息未处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkItopOpen workItopOpen)
    {
        return toAjax(workItopOpenService.updateWorkItopOpen(workItopOpen));
    }

    /**
     * 删除itop信息未处理
     */
    @PreAuthorize("@ss.hasPermi('work:itopOpen:remove')")
    @Log(title = "itop信息未处理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userRequestIds}")
    public AjaxResult remove(@PathVariable Long[] userRequestIds)
    {
        return toAjax(workItopOpenService.deleteWorkItopOpenByUserRequestIds(userRequestIds));
    }
}
