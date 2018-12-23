package com.pwk.service.impl;

import com.pwk.entity.Transaction;
import com.pwk.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2015/7/16.
 */
public class TransactionServiceImpl implements TransactionService {

    @Resource
    private MongoOperations mongoOperations;

    @Override
    public void add(Transaction transaction) {
        mongoOperations.save(transaction);
    }

    @Override
    public void delete(String id) {
        mongoOperations.remove(this.getById(id));
    }

    @Override
    public void deleteAll() {
        mongoOperations.findAllAndRemove(new Query(Criteria.where("_id").exists(true)),Transaction.class);
    }

    @Override
    public void update(Transaction transaction) {
        mongoOperations.save(transaction);
    }

    @Override
    public Transaction getById(String id) {
        return mongoOperations.findById(id,Transaction.class);
    }

    @Override
    public List<Transaction> getByList(Integer page, Integer size) {
        Query query = new Query();
        if(page != -1 && size != -1){
            query.skip(size * (page - 1));
            query.limit(size);
        }
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        return mongoOperations.find(query,Transaction.class);
    }

    @Override
    public List<Transaction> getByDate(String date, Integer page, Integer size) {
        Query query = new Query();
        if(page != -1 && size != -1){
            query.skip(size * (page - 1));
            query.limit(size);
        }
        query.addCriteria(Criteria.where("createTime").regex(date));
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        return  mongoOperations.find(query,Transaction.class);
    }

//    @Override
//    public List<Transaction> getByDate(String year, String month,String date,Integer page, Integer size) {
//        Query query = new Query();
//        if(page != -1 && size != -1){
//            query.skip(size * (page - 1));
//            query.limit(size);
//        }
//        query.addCriteria(Criteria.where("createTime").regex(year).regex(month).regex(date));
//        query.with(new Sort(Sort.Direction.DESC,"createTime"));
//
//        return mongoOperations.find(query,Transaction.class);
//    }
//
//    @Override
//    public List<Transaction> getByMonth(String year, String month,Integer page, Integer size) {
//        Query query = new Query();
//        if(page != -1 && size != -1){
//            query.skip(size * (page - 1));
//            query.limit(size);
//        }
//        query.addCriteria(Criteria.where("createTime").regex(year).regex(month));
//        query.with(new Sort(Sort.Direction.DESC,"createTime"));
//        return mongoOperations.find(query,Transaction.class);
//    }

//    @Override
//    public List<Transaction> getByYear(String year,Integer page, Integer size) {
//        Query query = new Query();
//        if(page != -1 && size != -1){
//            query.skip(size * (page - 1));
//            query.limit(size);
//        }
//        query.addCriteria(Criteria.where("createTime").regex(year));
//        query.with(new Sort(Sort.Direction.DESC,"createTime"));
//        return mongoOperations.find(query,Transaction.class);
//    }

    @Override
    public List<Transaction> getByKeywordAndDate(String keyword, String startDate, String endDate,Integer page, Integer size) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(keyword))
            criteria.orOperator(Criteria.where("contactName").regex(keyword), Criteria.where("phone").regex(keyword), Criteria.where("remark").regex(keyword),Criteria.where("productName").regex(keyword));
        if(StringUtils.isNotBlank(startDate)||StringUtils.isNotBlank(endDate))
            criteria.andOperator(Criteria.where("createTime").gt(startDate), Criteria.where("createTime").lt("createTime"));
        query.addCriteria(criteria);
        query.skip(size * (page - 1));
        query.limit(size);
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        return mongoOperations.find(query,Transaction.class);
    }

    @Override
    public Long getTotal() {
        return mongoOperations.count(new Query(),Transaction.class);
    }

    @Override
    public Long getTotalByDate(String date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("createTime").is(date));
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        return mongoOperations.count(query,Transaction.class);
    }

    @Override
    public Long getTotalByMonth(int year, int month) {
        return null;
    }
}
