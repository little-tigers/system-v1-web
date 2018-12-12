package cn.v1.modules.system.web;

import cn.v1.framework.base.Result;
import cn.v1.framework.utils.StringUtils;
import cn.v1.system.security.filter.FormAuthenticationFilter;
import cn.v1.system.security.realm.Principal;
import cn.v1.system.security.shiro.session.dao.SessionDAO;
import cn.v1.system.security.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Auther: wr
 * @Date: 2018/11/2
 * @Description:
 */
@Controller
@RequestMapping("/console/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 管理登录
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        Principal principal = UserUtils.getPrincipal();
        if (logger.isDebugEnabled()){
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        // 如果已经登录，则跳转到管理首页
        if(principal != null && !principal.isMobileLogin()){
            return "redirect:" + "/";
        }
        return "system/login";
    }


    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result login(HttpServletRequest request){
        Result result = new Result();
        Principal principal = UserUtils.getPrincipal();
        if (logger.isDebugEnabled()){
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }
        // 如果已经登录，则跳转到管理首页
        if(principal != null){
            return result.success();
        }
        String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
            message = "用户或密码错误, 请重试.";
        }
        result.setMessage(message);
        if (logger.isDebugEnabled()){
            logger.debug("login fail, active session size: {}, message: {}, exception: {}",
                    sessionDAO.getActiveSessions(false).size(), message, exception);
        }
        return result;
    }

}
