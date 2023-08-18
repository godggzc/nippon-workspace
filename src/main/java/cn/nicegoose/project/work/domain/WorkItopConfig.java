package cn.nicegoose.project.work.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.nicegoose.framework.aspectj.lang.annotation.Excel;
import cn.nicegoose.framework.web.domain.BaseEntity;

/**
 * itop用户配置对象 work_itop_config
 *
 * @author nicegoose
 * @date 2023-08-17
 */
public class WorkItopConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户配置id */
    private Long configId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 二级分类 */
    @Excel(name = "二级分类")
    private String serviceName;

    /** 所属区域 */
    @Excel(name = "所属区域")
    private String region;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 是否进行同步（0停用 1正常） */
    @Excel(name = "是否进行同步", readConverterExp = "0=停用,1=正常")
    private Integer syncStatus;

    /** 一级分类 */
    @Excel(name = "一级分类")
    private String servicefamilyName;

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    public Long getConfigId()
    {
        return configId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServiceName()
    {
        return serviceName;
    }
    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getRegion()
    {
        return region;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setSyncStatus(Integer syncStatus)
    {
        this.syncStatus = syncStatus;
    }

    public Integer getSyncStatus()
    {
        return syncStatus;
    }
    public void setServicefamilyName(String servicefamilyName)
    {
        this.servicefamilyName = servicefamilyName;
    }

    public String getServicefamilyName()
    {
        return servicefamilyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("configId", getConfigId())
                .append("userId", getUserId())
                .append("serviceName", getServiceName())
                .append("region", getRegion())
                .append("userName", getUserName())
                .append("syncStatus", getSyncStatus())
                .append("servicefamilyName", getServicefamilyName())
                .toString();
    }
}
