package com.pwk.taglib;

import com.pwk.entity.Brand;
import com.pwk.tools.Engine;

import java.util.List;

/**
 * Created by Administrator on 2015/7/21.
 */
public class BrandFunction {
    public static List<Brand> getAllBrands(){
        return Engine.brandService.getAllBrands();
    }
}
