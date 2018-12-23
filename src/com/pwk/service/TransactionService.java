package com.pwk.service;

import com.pwk.entity.Transaction;

import java.util.List;

/**
 * Created by Administrator on 2015/7/16.
 */
public interface TransactionService {
    public void add(Transaction transaction);
    public void delete(String id);
    public void deleteAll();
    public void update(Transaction transaction);

    public Transaction getById(String id);

    public List<Transaction> getByList(Integer page,Integer size);
    public List<Transaction> getByDate(String date,Integer page,Integer size);

    public List<Transaction> getByKeywordAndDate(String keyword,String startDate,String endDate,Integer page, Integer size);

    public Long getTotal();
    public Long getTotalByDate(String date);
    public Long getTotalByMonth(int year,int month);
}
