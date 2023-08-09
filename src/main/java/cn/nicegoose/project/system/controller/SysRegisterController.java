package cn.nicegoose.project.system.controller;

import cn.nicegoose.common.utils.StringUtils;
import cn.nicegoose.framework.security.RegisterBody;
import cn.nicegoose.framework.security.service.SysRegisterService;
import cn.nicegoose.framework.web.controller.BaseController;
import cn.nicegoose.framework.web.domain.AjaxResult;
import cn.nicegoose.project.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController
{
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ISysConfigService configService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
