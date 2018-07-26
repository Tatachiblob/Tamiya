/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysintg.tamiya.rest.status;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Yuta
 */
@Path("/v1/status")
public class V1_status {
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String returnTitle(){return "<p>Welcome to Tamiya API.</p>";}
    
}
