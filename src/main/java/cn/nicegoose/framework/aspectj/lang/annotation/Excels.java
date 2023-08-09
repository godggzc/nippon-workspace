package cn.nicegoose.framework.aspectj.lang.annotation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: nippon-workspace
 * @description: Excel注解集合类，用于定义多个Excel注解，方便使用{@link ExcelHandlerAdapter}处理多个Excel注解的情况
 * @author: Riche_Gzc
 * @create: 2023-08-07 14:30
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels
{
    public Excel[] value();
}
