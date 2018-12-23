package com.pwk.action;

import com.pwk.action.base.BaseAction;
import com.pwk.entity.Item;
import com.pwk.entity.Transaction;
import com.pwk.service.ItemService;
import com.pwk.service.TransactionService;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/7/16.
 */
public class TransactionAction extends BaseAction {
    @Resource
    private TransactionService transactionService;
    @Resource
    private ItemService itemService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public String add(){
        try {
            Transaction transaction = new Transaction();

            //items
            String[] ids = getRequest().getParameterValues("ids");
            List<Item> items = new ArrayList<Item>();
            if(ids != null && ids.length>0){
                for(String id:ids){
                    Item item = itemService.getById(id);
                    items.add(item);
                }
            }

            String type = getRequest().getParameter("payType");
            String bank = getRequest().getParameter("bank");
            String price = getRequest().getParameter("price");
            String paid = getRequest().getParameter("paid");
            float payable = Float.valueOf(price)-Float.valueOf(paid);

            String contactType = getRequest().getParameter("contact");
            String contactName = getRequest().getParameter("name");
            String phone = getRequest().getParameter("phone");
            String address = getRequest().getParameter("address");

            String remark = getRequest().getParameter("remark");

            String[] pics = getRequest().getParameterValues("pic");

            transaction.setItems(items);
            transaction.setType(type);
            transaction.setBank(bank);
            transaction.setPrice(Float.valueOf(StringUtils.trim(price)));
            transaction.setPaid(Float.valueOf(StringUtils.trim(paid)));
            transaction.setPayable(payable);
            transaction.setPayStatus("Unconfirmed");

            transaction.setContactType(contactType);
            transaction.setContactName(contactName);
            transaction.setPhone(phone);
            transaction.setAddress(address);
            transaction.setRemark(remark);
            transaction.setCreateTime(format.format(new Date()));
            transaction.setModifyTime(format.format(new Date()));

            if(pics != null && pics.length>0){
                List<String> pic = Arrays.asList(pics);
                transaction.setPicPath(pic);
            }

            transaction.setOrderStatus(1);

            transactionService.add(transaction);
            getResponse().sendRedirect("/orderList.jsp");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String update(){
        try {
            Transaction transaction = transactionService.getById(getRequest().getParameter("id"));

            String type = getRequest().getParameter("payType");
            String bank = getRequest().getParameter("bank");
            String price = getRequest().getParameter("price");
            String paid = getRequest().getParameter("paid");
            float payable = Float.valueOf(price)-Float.valueOf(paid);

            String contactType = getRequest().getParameter("contact");
            String contactName = getRequest().getParameter("name");
            String phone = getRequest().getParameter("phone");
            String address = getRequest().getParameter("address");
            String remark = getRequest().getParameter("remark");

            transaction.setType(type);
            transaction.setBank(bank);
            transaction.setPrice(Float.valueOf(StringUtils.trim(price)));
            transaction.setPaid(Float.valueOf(StringUtils.trim(paid)));
            transaction.setPayable(payable);

            transaction.setContactType(contactType);
            transaction.setContactName(contactName);
            transaction.setPhone(phone);
            transaction.setAddress(address);
            transaction.setRemark(remark);

            String[] pics = getRequest().getParameterValues("pic");
            if(pics !=null && pics.length>0){
                for(String pic:pics){
                    transaction.getPicPath().add(pic);
                }
            }
            transaction.setModifyTime(format.format(new Date()));
            transactionService.update(transaction);
            getResponse().sendRedirect("/orderList.jsp");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String updateDate(){
        try{
            String id = getParameter("id");
            String date = getParameter("date");
            Transaction transaction = transactionService.getById(id);
            transaction.setCreateTime(date);
            transactionService.update(transaction);
            PrintWriter out = getResponse().getWriter();
            out.print("ok");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String payStatus(){
        try {
            Transaction transaction = transactionService.getById(getRequest().getParameter("id"));
            transaction.setPayStatus("Confirmed");
            transactionService.update(transaction);
            getResponse().sendRedirect("/orderList.jsp");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String orderStatus(){
        try {
            Transaction transaction = transactionService.getById(getRequest().getParameter("id"));
            String orderStatus = getRequest().getParameter("orderStatus");
            transaction.setOrderStatus(Integer.valueOf(orderStatus));

            if(StringUtils.equals(getParameter("orderStatus"),"3")){
                transaction.setDeliveryDate(format.format(new Date()));
            }
            transactionService.update(transaction);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String delete(){
        try{
            Transaction transaction = transactionService.getById(getRequest().getParameter("id"));
            try{
                for(Item item:transaction.getItems()){
                    itemService.delete(item.getId());
                }
            }catch (Exception e){
                System.out.println("item delete error");
            }
            transactionService.delete(transaction.getId());
            getResponse().sendRedirect("/orderList.jsp");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String deleteAll(){
        try {
            transactionService.deleteAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String deleteItem(){
        try {
            Item item = itemService.getById(getRequest().getParameter("id"));
            Transaction transaction = transactionService.getById(getRequest().getParameter("tId"));
            for(Item item1:transaction.getItems()){
                if(StringUtils.equals(item1.getId(),item.getId()))
                    transaction.getItems().remove(item1);
            }
            itemService.delete(item.getId());
            transactionService.update(transaction);
            PrintWriter out = getResponse().getWriter();
            out.print("ok");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
