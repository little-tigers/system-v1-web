package cn.v1.modules.system.web;

import cn.v1.system.security.shiro.session.dao.SessionDAO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wr
 * @Date: 2018/12/3
 * @Description:
 */
@Controller
@RequestMapping("/console")
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        String url = "system/index";
        ModelAndView modelAndView = new ModelAndView(url);
        if (logger.isDebugEnabled()){
            logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }
       /* String logged = CookieUtil.getCookie(request, "LOGGED");
        if(StringUtils.isBlank(logged) || SysConfig.STR_FALSE.equals(logged)){
            CookieUtil.setCookie(response, "LOGGED", SysConfig.STR_TRUE);
        }else if(SysConfig.STR_TRUE.equals(logged)){
            UserUtils.logout(true);
            return new ModelAndView("redirect:/console/login");
        }*/
        return modelAndView;
    }
}
