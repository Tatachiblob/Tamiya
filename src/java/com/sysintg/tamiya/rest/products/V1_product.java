/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysintg.tamiya.rest.products;

import com.sysintg.tamiya.dao.ProductsDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Yuta
 */
@Path("/v1/product")
public class V1_product {
    
    @Path("/getAllProducts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnProducts(){
        Response res = null;
        String returnString = ProductsDAO.getProducts().toString();
        
        res = Response.ok(ProductsDAO.getProducts())
                .build();
        
        return res;
    }
    
    
    @Path("/sendOrder")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveOrder(String incomingData){
        String returnString = null;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        
        try {
            JSONArray ordersData = new JSONArray(incomingData);
            System.out.println(ordersData.toString());
            
            int http_code = ProductsDAO.makeOrder(ordersData);
            
            if(http_code == 200){
                jsonObject.put("HTTP_CODE", "200");
                jsonObject.put("msg", "Order has been successfully placed");
                returnString = jsonArray.put(jsonObject).toString();
            }else{
                jsonObject.put("HTTP_CODE", "500");
                jsonObject.put("msg", "Order unsuccessful");
                returnString = jsonArray.put(jsonObject).toString();
                return Response.status(500).entity(returnString).build();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Server was not able to process your request").build();
        }
        
        return Response.ok(returnString).build();
    }
    
}
