package com.pwk.taglib;

import com.pwk.entity.Transaction;
import com.pwk.tools.Engine;
import com.pwk.tools.StringTools;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/7/17.
 */
public class TransactionFunction {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static Transaction getById(String id){
        return Engine.transactionService.getById(id);
    }

    public static List<Transaction> getByKeywordAndDate(String keyword, String startDate, String endDate,Integer page, Integer size){
        if(page == null || size == null){
            page = 1;
            size = 30;
        }
        return Engine.transactionService.getByKeywordAndDate(keyword,startDate,endDate,page,size);
    }

    public static List<Transaction> getByDate(String date,Integer page,Integer size){
        try{
            List<Transaction> transactions = null;
            if(page == null || size == null){
                page = 1;
                size = 30;
            }
            transactions = Engine.transactionService.getByDate(date,page, size);
            return transactions;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Float getRevenue(String date){
        try {
            List<Transaction> transactions = getByDate(date,-1,-1);
            if(transactions!=null && transactions.size()!=0){
                Float revenue = 0f;
                for(Transaction transaction:transactions){
                    //paid or not refund can add in revenue
                    if(StringUtils.equals(transaction.getPayStatus(),"Confirmed")&&transaction.getOrderStatus() != 5)
                        revenue += transaction.getPaid();
                }
                return revenue;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
