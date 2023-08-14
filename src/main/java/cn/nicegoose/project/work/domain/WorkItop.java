package cn.nicegoose.project.work.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.nicegoose.framework.aspectj.lang.annotation.Excel;
import cn.nicegoose.framework.web.domain.BaseEntity;

/**
 * itop信息对象 work_itop
 * 
 * @author nicegoose
 * @date 2023-08-14
 */
public class WorkItop
{
    private static final long serialVersionUID = 1L;

    /** 请求编号 */
    @Excel(name = "请求编号")
    private Long UserRequestId;

    /** itop链接 */
    @Excel(name = "itop链接")
    private String hyperlink;

    /** 请求时间 */
    @Excel(name = "请求时间")
    private String reportDate;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String callerName;

    /** 一级分类 */
    @Excel(name = "一级分类")
    private String servicefamilyName;

    /** 二级分类 */
    @Excel(name = "二级分类")
    private String serviceName;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String region;



    public void setUserRequestId(Long UserRequestId) 
    {
        this.UserRequestId = UserRequestId;
    }

    public Long getUserRequestId() 
    {
        return UserRequestId;
    }
    public void setHyperlink(String hyperlink) 
    {
        this.hyperlink = hyperlink;
    }

    public String getHyperlink() 
    {
        return hyperlink;
    }
    public void setReportDate(String reportDate) 
    {
        this.reportDate = reportDate;
    }

    public String getReportDate() 
    {
        return reportDate;
    }
    public void setCallerName(String callerName) 
    {
        this.callerName = callerName;
    }

    public String getCallerName() 
    {
        return callerName;
    }
    public void setServicefamilyName(String servicefamilyName) 
    {
        this.servicefamilyName = servicefamilyName;
    }

    public String getServicefamilyName() 
    {
        return servicefamilyName;
    }
    public void setServiceName(String serviceName) 
    {
        this.serviceName = serviceName;
    }

    public String getServiceName() 
    {
        return serviceName;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("UserRequestId", getUserRequestId())
            .append("hyperlink", getHyperlink())
            .append("reportDate", getReportDate())
            .append("callerName", getCallerName())
            .append("servicefamilyName", getServicefamilyName())
            .append("serviceName", getServiceName())
            .append("title", getTitle())
            .append("region", getRegion())
            .toString();
    }
}
