package cn.nicegoose.framework.config;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import cn.nicegoose.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @program: nippon-workspace
 * @description: 自动补全create_time等信息
 * @author: Riche_Gzc
 * @create: 2023-08-07 20:38
 **/
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        String operator = null;
        try {
            operator = SecurityUtils.getUsername();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.setFieldValByName("createBy", operator, metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", operator, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String operator = null;
        try {
            operator = SecurityUtils.getUsername();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.setFieldValByName("updateBy", operator, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}

