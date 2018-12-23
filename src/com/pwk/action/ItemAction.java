package com.pwk.action;

import com.pwk.action.base.BaseAction;
import com.pwk.entity.Brand;
import com.pwk.entity.Item;
import com.pwk.entity.Transaction;
import com.pwk.service.BrandService;
import com.pwk.service.ItemService;
import com.pwk.service.TransactionService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Administrator on 2015/7/26.
 */
public class ItemAction extends BaseAction {

    @Resource
    private ItemService itemService;
    @Resource
    private BrandService brandService;
    @Resource
    private TransactionService transactionService;

    public String add(){
        try{
            Item item = new Item();

            String brandName = getRequest().getParameter("brandName");
            String productName = getRequest().getParameter("product");
            String quantity = getRequest().getParameter("quantity");

            item.setBrandName(brandName);
            item.setProductName(productName);
            item.setCount(Integer.valueOf(quantity));

            itemService.add(item);

            JSONObject object = new JSONObject();
            object.put("id",item.getId());
            object.put("brandName",brandName);
            object.put("productName",productName);
            object.put("quantity",quantity);
            PrintWriter out = getResponse().getWriter();
            out.print(object.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getById(){
        try {
            Item item = itemService.getById(getRequest().getParameter("id"));
            if(item != null){
                JSONObject object = new JSONObject();
                object.put("id",item.getId());
                object.put("brandName",item.getBrandName());
                object.put("productName",item.getProductName());
                object.put("quantity",item.getCount());
                PrintWriter out = getResponse().getWriter();
                out.print(object.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String delete(){
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

    public String deleteAll(){
        try {
            itemService.deleteAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String update(){
        try {
            Item item = itemService.getById(getRequest().getParameter("id"));

            String brandName = getParameter("brandName");
            String product = getParameter("product");
            String quantity = getParameter("quantity");

            item.setBrandName(brandName);
            item.setProductName(product);
            item.setCount(Integer.valueOf(StringUtils.trim(quantity)));
            itemService.update(item);

            Transaction transaction = transactionService.getById(getParameter("tId"));
            for(int i = 0;i<transaction.getItems().size();i++){
                if(StringUtils.equals(transaction.getItems().get(i).getId(),item.getId())){
                    transaction.getItems().get(i).setBrandName(brandName);
                    transaction.getItems().get(i).setProductName(product);
                    transaction.getItems().get(i).setCount(Integer.valueOf(quantity));
                }
            }
            transactionService.update(transaction);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getItemHtml(){
        try {
            List<Brand> brands = brandService.getAllBrands();
            Item item = itemService.getById(getRequest().getParameter("id"));

            StringBuilder html = new StringBuilder();
            html.append("<input type='hidden' id='itemId' name='itemId' value='"+item.getId()+"'>");
            html.append("<select name=\"brandName\" id=\"brand\" class=\"form-control\">");
            html.append("<option value=\"\" disabled></option>");
            for(Brand brand:brands){
                html.append("<option value=\""+brand.getBrandName()+"\""+ (StringUtils.equals(brand.getBrandName(),item.getBrandName())?"selected":"")+">"+brand.getBrandName()+"</option>");
            }
            html.append("</select>");
            html.append("<label for=\"product\" class=\"sr-only\">Product</label>");
            html.append("<input type=\"text\" id=\"product\" name=\"product\" class=\"form-control\" placeholder=\"Product Name\" required value=\""+item.getProductName()+"\">");
            html.append(" <label for=\"quantity\" class=\"sr-only\">Quantity</label>");
            html.append("<input type=\"number\" id=\"quantity\" name=\"quantity\" class=\"form-control\" placeholder=\"Quantity\" required value=\""+item.getCount()+"\">");
            html.append("<a class=\"btn btn-warning btn-lg btn-block\" href=\"javascript:void(0)\" onclick=\"updateProduct()\">Save Product</a>");
            html.append("<br>");

            PrintWriter out = getResponse().getWriter();
            out.print(html.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
