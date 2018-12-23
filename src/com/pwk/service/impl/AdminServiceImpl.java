package com.pwk.service.impl;

import com.pwk.entity.Admin;
import com.pwk.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-25
 * Time: 下午6:57
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private SessionFactory sessionFactory;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void update(Admin admin) {
        sessionFactory.getCurrentSession().update(admin);
    }

    @Override
    public void changePassword(int id,String password) {
        Admin admin = (Admin)sessionFactory.getCurrentSession().get(Admin.class,id);
        admin.setLoginName("admin");
        admin.setPassword(password);
        sessionFactory.getCurrentSession().update(admin);
    }

    @Override
    public int findAdmin(int id, String password) throws Exception{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(password.getBytes("utf8"));
        Admin admin = (Admin)sessionFactory.getCurrentSession().get(Admin.class,id);
        if(StringUtils.equals(admin.getPassword(),new String(messageDigest.digest())))
            return 1;
        return 0;
    }

    @Override
    public Admin login(String loginName, String password) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Admin.class);
        criteria.add(Restrictions.eq("loginName",loginName));
        criteria.add(Restrictions.eq("password",password));
        return (Admin)criteria.uniqueResult();
    }

    @Override
    public Admin getUniqueAdmin() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Admin");
        List<Admin> admins = query.list();
        if(admins!=null){
            return admins.get(0);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Admin getUniqueAdminInfo() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Admin");
        Admin admin = (Admin)query.uniqueResult();
        return admin;
    }

    @Override
    public void log(int adminId, Timestamp loginDate, String ip) {
        jdbcTemplate.update(" INSERT INTO admin_log(admin_id,ip,login_date,type) VALUES (?,?,?,?) ",adminId,ip,loginDate,"pc");
    }
}
