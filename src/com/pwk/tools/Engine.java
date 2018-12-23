package com.pwk.tools;

import com.pwk.service.BrandService;
import com.pwk.service.ItemService;
import com.pwk.service.TransactionService;

/**
 * Created by Administrator on 2015/7/17.
 */
public class Engine {
    public static TransactionService transactionService;
    public static BrandService brandService;
    public static ItemService itemService;

    public void setTransactionService(TransactionService transactionService) {
        Engine.transactionService = transactionService;
    }

    public void setBrandService(BrandService brandService) {
        Engine.brandService = brandService;
    }

    public void setItemService(ItemService itemService) {
        Engine.itemService = itemService;
    }
}
