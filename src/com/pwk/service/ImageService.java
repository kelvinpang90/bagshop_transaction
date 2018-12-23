package com.pwk.service;

import com.pwk.entity.Image;

import java.util.List;

/**
 * Created by wenkai.peng on 9/19/2014.
 */
public interface ImageService {
    public void add(Image image);
    public void update(Image image);
    public void delete(String projectPath, int id);
    public void deleteByProductId(String projectPath, String type, int id);
    public Image getById(int id);
    public List<Image> getListById(String type, int productId);
    public List<Image> getByList(int page, int size);
    public int getTotal();
}
