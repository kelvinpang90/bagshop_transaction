package com.pwk.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Created by wenkai.peng on 9/1/2014.
 *
 */

@Document(collection = "transaction")
public class Transaction {
    @Id
    private String id;

    private List<Item> items = new ArrayList<Item>();

    private String createTime;
    private String modifyTime;

    private String type;     //1:一次性付款 2:预存款
    private String bank;  //Maybank,Public Bank, Ambank, Hongleong Bank, RHB, HSBC, CIMB,Others
    private Float price;    //应付
    private Float paid;     //已付
    private Float payable;  //未付

    private String contactType;   //1:wechat 2:facebook 3:whatsapp 4:line 5:qq 6:wangwang 6:phone number
    private String contactName;
    private String phone;
    private String address;

    private String payStatus;
    private Integer orderStatus;//1:wait for order 2:lead time 3:delivered 4:cancelled
    private String deliveryDate;
    private String remark;
    private List<String> picPath = new ArrayList<String>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Float getPayable() {
        return payable;
    }

    public void setPayable(Float payable) {
        this.payable = payable;
    }

    public List<String> getPicPath() {
        return picPath;
    }

    public void setPicPath(List<String> picPath) {
        this.picPath = picPath;
    }

    public Float getPaid() {
        return paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "id="+id+" createTime="+createTime+" modifyTime="+modifyTime+" type="+type+" price="+price+" paid="+paid+" payable="+payable+" contactType="+contactType+
                " contactName="+contactName+" phone="+phone+" address="+address+" payStatus="+payStatus+" orderStatus="+orderStatus+" remark"+remark;
    }
}
