package cn.nicegoose.project.work.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
    @PreAuthorize("@ss.hasAnyPermi('work:itop:list,workspace')")
    @GetMapping("/workspace_itop_list")
    public AjaxResult workspace_list(WorkItop workItop)
    {   //先new一个query
        QueryWrapper<WorkItop> queryWrapper = new QueryWrapper<>();
        //然后解析workItop的serviceName
        String serviceName = workItop.getServiceName();
        //如果serviceName不为空，尝试通过+号分割
        if(serviceName!=null){
            String[] serviceNameList = serviceName.split("\\+");
            //如果分割后的数组长度大于1，说明有多个serviceName
            if(serviceNameList.length>1){
                //遍历数组，将每个serviceName都加入到queryWrapper中
                queryWrapper.and( wrapper -> {
                    for(String serviceNameItem:serviceNameList){
                        //如果是第一个，就用eq，否则用or
                        if(serviceNameItem.equals(serviceNameList[0])){
                            wrapper.like("service_name",serviceNameItem);
                        }else{
                            wrapper.or().like("service_name",serviceNameItem);
                        }

                    }
                });
        }else{
                //如果分割后的数组长度等于1，说明只有一个serviceName
                queryWrapper.and( wrapper -> wrapper.like("service_name",serviceName) );

            }
        };

        //然后解析workItop的region
        String region = workItop.getRegion();
        //如果region不为空，尝试通过+号分割
        if(region!=null){
            String[] regionList = region.split("\\+");
           // System.out.println("regionList = " + Arrays.toString(regionList));
            //如果分割后的数组长度大于1，说明有多个region
            if(regionList.length>1){
                //遍历数组，将每个region都加入到queryWrapper中
                queryWrapper.and( wrapper -> {
                    for(String regionItem:regionList){
                        //如果是第一个，就用eq，否则用or
                        if(regionItem.equals(regionList[0])){
                            wrapper.like("region",regionItem);
                        }else{
                            wrapper.or().like("region",regionItem);
                        }
                        wrapper.or().eq("region","");
                    }
                });
            }else{
                //如果分割后的数组长度等于1，说明只有一个region
                queryWrapper.and( wrapper -> wrapper.like("region",region).or().eq("region","") );

            }
        };
        queryWrapper.orderByDesc("user_request_id");

        //解析servicefamilyName
        String servicefamilyName = workItop.getServicefamilyName();
        //如果servicefamilyName不为空，尝试通过+号分割
        if(servicefamilyName!=null){
            String[] servicefamilyNameList = servicefamilyName.split("\\+");
            //如果分割后的数组长度大于1，说明有多个servicefamilyName
            if(servicefamilyNameList.length>1){
                //遍历数组，将每个servicefamilyName都加入到queryWrapper中
                queryWrapper.and( wrapper -> {
                    for(String servicefamilyNameItem:servicefamilyNameList){
                        //如果是第一个，就用eq，否则用or
                        if(servicefamilyNameItem.equals(servicefamilyNameList[0])){
                            wrapper.like("servicefamily_name",servicefamilyNameItem);
                        }else{
                            wrapper.or().like("servicefamily_name",servicefamilyNameItem);
                        }
                        wrapper.or().eq("servicefamily_name","");
                    }
                });
            }else{
                //如果分割后的数组长度等于1，说明只有一个servicefamilyName
                queryWrapper.and( wrapper -> wrapper.like("servicefamily_name",servicefamilyName).or().eq("servicefamily_name","") );

            }
        };

        //解析完成后，将queryWrapper传入selectList方法
        List<WorkItop> list = workItopService.list(queryWrapper);
        return success(list);
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
