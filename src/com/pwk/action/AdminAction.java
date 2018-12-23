package com.pwk.action;

import com.pwk.action.base.BaseAction;
import com.pwk.entity.Admin;
import com.pwk.service.AdminService;
import com.pwk.tools.DigestTool;
import com.pwk.tools.Message;
import com.pwk.tools.StringTools;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-25
 * Time: 下午6:45
 * To change this template use File | Settings | File Templates.
 */
public class AdminAction extends BaseAction {

    @Resource
    private AdminService adminService;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String login() {
        try {
            String loginName = getRequest().getParameter("loginName");
            String password = getRequest().getParameter("password");
            Admin admin = adminService.login(loginName,DigestTool.digest(password));
            if (admin == null) {
                return null;
            }
            getRequest().getSession().setAttribute("admin", admin);
            getResponse().sendRedirect("../orderList.jsp");
            adminService.log(admin.getId(), Timestamp.valueOf(format.format(new Date())), StringTools.getRealIp(getRequest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String loginOut() {
        try {
            getRequest().getSession().removeAttribute("admin");
            getResponse().sendRedirect("../login.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
