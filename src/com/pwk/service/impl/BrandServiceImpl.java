package com.pwk.service.impl;

import com.pwk.entity.Brand;
import com.pwk.service.BrandService;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2015/7/21.
 */
@Transactional
public class BrandServiceImpl implements BrandService {
    @Resource
    private SessionFactory sessionFactory;
    @Override
    public List<Brand> getAllBrands() {
        return sessionFactory.getCurrentSession().createQuery("from Brand b order by b.position ").list();
    }
}
