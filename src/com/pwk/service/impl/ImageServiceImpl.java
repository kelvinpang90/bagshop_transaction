package com.pwk.service.impl;

import com.pwk.entity.Image;
import com.pwk.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * Created by wenkai.peng on 9/19/2014.
 */
@Transactional
public class ImageServiceImpl implements ImageService {
    @Resource
    private SessionFactory sessionFactory;
    @Override
    public void add(Image image) {
        sessionFactory.getCurrentSession().save(image);
    }

    @Override
    public void update(Image image) {
        sessionFactory.getCurrentSession().update(image);
    }

    @Override
    public void delete(String projectPath,int id) {
        Image image = this.getById(id);
        try {
            FileUtils.forceDelete(new File(projectPath+"/"+image.getPath()));
            FileUtils.forceDelete(new File(projectPath+"/"+image.getMiniPath()));
            sessionFactory.getCurrentSession().delete(image);
        }catch (Exception e){
            System.out.println("文件删除时出现异常。");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByProductId(String projectPath,String type,int id) {
        List<Image> list = this.getListById(type,id);
        for(Image image:list){
            this.delete(projectPath,image.getId());
        }
    }


    @Override
    public Image getById(int id) {
        return (Image)sessionFactory.getCurrentSession().get(Image.class,id);
    }

    @Override
    public List<Image> getByList(int page, int size) {
        if(page==0||size==0){
            page = 1;
            size = 10;
        }
        Query query = sessionFactory.getCurrentSession().createQuery(" from Image where 1=1 ");
        if (page != 0 && size != 0) {
            query.setFirstResult(size * (page - 1));
            query.setMaxResults(size);
        }
        return query.list();
    }

    @Override
    public int getTotal() {
        return sessionFactory.getCurrentSession().createQuery(" from Image ").list().size();
    }

    @Override
    public List<Image> getListById(String type, int productId) {
        Query query = sessionFactory.getCurrentSession().createQuery(" from Image i where i.type=:type and i.parentId=:parentId ");
        query.setParameter("type",type, Hibernate.STRING);
        query.setParameter("parentId",productId, Hibernate.INTEGER);
        return query.list();
    }
}
