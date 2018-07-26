/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysintg.tamiya.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Yuta
 */
public class ProductsDAO {
    
    public static JSONArray getProductsV2(){
        JSONArray products = new JSONArray();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTLINE, BUYPRICE, MSRP FROM PRODUCTS;";
        
        try{
            conn = DatabaseUtils.retrieveConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            
            JSONObject product = null;
            while(rs.next()){
                product = new JSONObject();
                product.put("productCode", rs.getString(1));
                product.put("productName", rs.getString(2));
                product.put("productLine", rs.getString(3));
                product.put("buyprice", rs.getDouble(4));
                product.put("MSRP", rs.getDouble(5));
                products.put(product);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    conn.close();
                    pStmt.close();
                    rs.close();
                }catch(Exception e){}
            }
        }
        
        return products;
    }
    
    public static JSONArray getProductsV2ByProductLine(String productLine){
        JSONArray products = new JSONArray();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTLINE, BUYPRICE, MSRP FROM PRODUCTS WHERE PRODUCTLINE = ?;";
        
        try{
            conn = DatabaseUtils.retrieveConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, productLine);
            rs = pStmt.executeQuery();
            
            JSONObject product = null;
            while(rs.next()){
                product = new JSONObject();
                product.put("productCode", rs.getString(1));
                product.put("productName", rs.getString(2));
                product.put("productLine", rs.getString(3));
                product.put("buyprice", rs.getDouble(4));
                product.put("MSRP", rs.getDouble(5));
                products.put(product);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    conn.close();
                    pStmt.close();
                    rs.close();
                }catch(Exception e){}
            }
        }
        
        return products;
    }
    
    public static JSONArray getProducts(){
        JSONArray returnJsonArray = null;
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTLINE, BUYPRICE, MSRP FROM PRODUCTS;";
        
        try{
            conn = DatabaseUtils.retrieveConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            /*
            while(rs.next()){
                System.out.println("Product Code: " + rs.getObject(1));
                System.out.println("Product Name: " + rs.getObject(2));
                System.out.println("Product Line: " + rs.getObject(3));
                System.out.println("Buy Price: " + rs.getObject(4));
                System.out.println("MSRP: " + rs.getObject(5));
            }
            */
            returnJsonArray = JsonUtils.toJSONArray(rs);
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    conn.close();
                    pStmt.close();
                    rs.close();
                }catch(Exception e){}
            }
        }
        
        return returnJsonArray;
    }
    
    public static int makeOrder(JSONArray ordersJson) throws SQLException{
        int httpCode = 200;
        PreparedStatement pStmt = null;
        Connection conn = DatabaseUtils.retrieveConnection();
        ResultSet rs = null;
        
        String orderInsert = "INSERT INTO ORDERS (ORDERDATE, REQUIREDDATE, STATUS) VALUES (NOW(), NOW(), 'In Process');";
        String orderDetailsInsert
                = "INSERT INTO TAMIYADB.ORDERDETAILS (ORDERNUMBER, PRODUCTCODE, QUANTITYORDERED, PRICEEACH, MSRP, MARKUP) VALUES (?, ?, ?, 10, 10, 0)";
        
        try{
            conn.setAutoCommit(false);
            
            pStmt = conn.prepareStatement(orderInsert, Statement.RETURN_GENERATED_KEYS);
            pStmt.executeUpdate();
            rs = pStmt.getGeneratedKeys();
            
            int orderPk = -1;
            
            if(rs.next())
                orderPk = rs.getInt(1);
            
            if(orderPk != -1){
                for(int i = 0; i < ordersJson.length(); i++){
                    JSONObject curObj = ordersJson.getJSONObject(i);
                    pStmt = conn.prepareStatement(orderDetailsInsert);
                    pStmt.setInt(1, orderPk);
                    pStmt.setString(2, curObj.getString("productCode"));
                    pStmt.setInt(3, curObj.getInt("quantityOrdered"));
                    System.out.println("Executing Update: " + pStmt.executeUpdate());
                    System.out.println("ORder PK: " + orderPk);
                }
            }
            
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
            conn.rollback();
            httpCode = 500;
        }finally{
            if(conn != null){
                try{
                    pStmt.close();
                    conn.close();
                    rs.close();
                }catch(Exception e){}
            }
        }
        System.out.println(httpCode);
        return httpCode;
    }
    
}
