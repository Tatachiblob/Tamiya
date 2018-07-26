package com.sysintg.tamiya.rest.products;


import com.sysintg.tamiya.dao.ProductsDAO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yuta
 */
@Path("/v2/product")
public class V2_product {
    
    @GET
    @Path("/getAllProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnProducts(){
        Response response = null;
        response = Response.ok(ProductsDAO.getProductsV2()).build();
        return response;
    }
    
    @GET
    @Path("/getAllProducts/{productLine}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnProductsByProductLine(
                                                @PathParam("productLine") String productLine){
        Response response = null;
        response = Response.ok(ProductsDAO.getProductsV2ByProductLine(productLine)).build();
        return response;
    }
}
