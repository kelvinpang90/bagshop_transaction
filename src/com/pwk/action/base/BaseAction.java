package com.pwk.action.base;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-2-12
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */
public class BaseAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    protected void setContextValue(Object key, Object value) {
        ActionContext.getContext().put(String.valueOf(key), value);
    }

    protected void sendRedirect(String url) throws IOException {
        if (url.indexOf("http://") != -1)
            ServletActionContext.getResponse().sendRedirect(url);
        else
            ServletActionContext.getResponse().sendRedirect(getRequest().getContextPath() + url);
    }

    protected Map getParameterMap() {
        return ActionContext.getContext().getParameters();
    }

    public String getParameter(String name) {
        String[] params = (String[]) getParameterMap().get(name);
        return (params != null && params.length > 0 ? params[0] : null);
    }

    /**
     * 返回当前 HttpServletRequest 对象.
     *
     * @return HttpServletRequest
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 返回当前 HttpServletResponse 对象.
     *
     * @return HttpServletResponse
     */
    protected HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * 返回当前 HttpSession 对象.
     *
     * @return HttpSession
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected String[] getParameterValues(String name) {
        return getRequest().getParameterValues(name);
    }

    /**
     * 取得文件扩展名
     *
     * @param fileName
     * @return
     */
    protected String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

}
