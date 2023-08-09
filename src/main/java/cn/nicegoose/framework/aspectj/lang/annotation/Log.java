package cn.nicegoose.framework.aspectj.lang.annotation;

import cn.nicegoose.framework.aspectj.lang.enums.BusinessType;
import cn.nicegoose.framework.aspectj.lang.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @program: nippon-workspace
 * @description: Log注解接口
 * @author: Riche_Gzc
 * @create: 2023-08-07 13:29
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     * 默认为空
     */
    public String title() default "";

    /**
     * 功能
     * 默认为其它
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     * 默认为后台用户
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     * 默认为true
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     * 默认为true
     */
    public boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     * 默认为空
     */
    public String[] excludeParamNames() default {};
}

