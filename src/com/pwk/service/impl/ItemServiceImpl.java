package com.pwk.service.impl;

import com.pwk.entity.Item;
import com.pwk.service.ItemService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/7/26.
 */
public class ItemServiceImpl implements ItemService {

    @Resource
    private MongoOperations mongoOperations;

    @Override
    public void add(Item item) {
        mongoOperations.save(item);
    }

    @Override
    public void delete(String id) {
        mongoOperations.remove(this.getById(id));
    }

    @Override
    public void deleteAll() {
        mongoOperations.findAllAndRemove(new Query(Criteria.where("_id").exists(true)),Item.class);
    }

    @Override
    public void update(Item item) {
        mongoOperations.save(item);
    }

    @Override
    public Item getById(String id) {
        return mongoOperations.findById(id,Item.class);
    }
}
