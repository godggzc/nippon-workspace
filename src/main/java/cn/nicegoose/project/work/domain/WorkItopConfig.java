package cn.nicegoose.project.work.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.nicegoose.framework.aspectj.lang.annotation.Excel;
import cn.nicegoose.framework.web.domain.BaseEntity;

/**
 * itop用户配置对象 work_itop_config
 * 
 * @author nicegoose
 * @date 2023-08-14
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("userId", getUserId())
            .append("serviceName", getServiceName())
            .append("region", getRegion())
            .toString();
    }
}
