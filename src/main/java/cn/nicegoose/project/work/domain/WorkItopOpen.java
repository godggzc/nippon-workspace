package cn.nicegoose.project.work.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.nicegoose.framework.aspectj.lang.annotation.Excel;
import cn.nicegoose.framework.web.domain.BaseEntity;

/**
 * itop信息未处理对象 work_itop_open
 * 
 * @author nicegoose
 * @date 2023-08-15
 */
public class WorkItopOpen extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 请求编号 */
    private Long userRequestId;

    /** itop链接 */
    @Excel(name = "itop链接")
    private String hyperlink;

    /** 请求时间 */
    @Excel(name = "请求时间")
    private String reportDate;

    /** 所有人名称 */
    @Excel(name = "所有人名称")
    private String agentName;

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

    public void setUserRequestId(Long userRequestId) 
    {
        this.userRequestId = userRequestId;
    }

    public Long getUserRequestId() 
    {
        return userRequestId;
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
    public void setAgentName(String agentName) 
    {
        this.agentName = agentName;
    }

    public String getAgentName() 
    {
        return agentName;
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
            .append("userRequestId", getUserRequestId())
            .append("hyperlink", getHyperlink())
            .append("reportDate", getReportDate())
            .append("agentName", getAgentName())
            .append("servicefamilyName", getServicefamilyName())
            .append("serviceName", getServiceName())
            .append("title", getTitle())
            .append("region", getRegion())
            .toString();
    }
}
