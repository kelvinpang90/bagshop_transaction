package com.pwk.service;

import com.pwk.entity.Item;

/**
 * Created by Administrator on 2015/7/24.
 */
public interface ItemService {
    public void add(Item item);
    public void delete(String id);
    public void deleteAll();
    public void update(Item item);
    public Item getById(String id);

}
