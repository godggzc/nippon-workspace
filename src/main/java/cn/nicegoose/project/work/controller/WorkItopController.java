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
import cn.nicegoose.project.work.domain.WorkItop;
import cn.nicegoose.project.work.service.IWorkItopService;
import cn.nicegoose.framework.web.controller.BaseController;
import cn.nicegoose.framework.web.domain.AjaxResult;
import cn.nicegoose.common.utils.poi.ExcelUtil;
import cn.nicegoose.framework.web.page.TableDataInfo;

/**
 * itop信息Controller
 * 
 * @author nicegoose
 * @date 2023-08-14
 */
@RestController
@RequestMapping("/work/itop")
public class WorkItopController extends BaseController
{
    @Autowired
    private IWorkItopService workItopService;

    /**
     * 查询itop信息列表
     */
    @PreAuthorize("@ss.hasAnyPermi('work:itop:list,workspace')")
    @GetMapping("/list")
    public TableDataInfo list(WorkItop workItop)
    {
        startPage();
        List<WorkItop> list = workItopService.selectWorkItopList(workItop);
        return getDataTable(list);
    }

    /**
     * 导出itop信息列表
     */
    @PreAuthorize("@ss.hasAnyPermi('work:itop:export,workspace')")
    @Log(title = "itop信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WorkItop workItop)
    {
        List<WorkItop> list = workItopService.selectWorkItopList(workItop);
        ExcelUtil<WorkItop> util = new ExcelUtil<WorkItop>(WorkItop.class);
        util.exportExcel(response, list, "itop信息数据");
    }

    /**
     * 获取itop信息详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('work:itop:query,workspace')")
    @GetMapping(value = "/{UserRequestId}")
    public AjaxResult getInfo(@PathVariable("UserRequestId") Long UserRequestId)
    {
        return success(workItopService.selectWorkItopByUserRequestId(UserRequestId));
    }

    /**
     * 新增itop信息
     */
    @PreAuthorize("@ss.hasPermi('work:itop:add')")
    @Log(title = "itop信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WorkItop workItop)
    {
        return toAjax(workItopService.insertWorkItop(workItop));
    }

    /**
     * 修改itop信息
     */
    @PreAuthorize("@ss.hasPermi('work:itop:edit')")
    @Log(title = "itop信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WorkItop workItop)
    {
        return toAjax(workItopService.updateWorkItop(workItop));
    }

    /**
     * 删除itop信息
     */
    @PreAuthorize("@ss.hasPermi('work:itop:remove')")
    @Log(title = "itop信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{UserRequestIds}")
    public AjaxResult remove(@PathVariable Long[] UserRequestIds)
    {
        return toAjax(workItopService.deleteWorkItopByUserRequestIds(UserRequestIds));
    }
}
