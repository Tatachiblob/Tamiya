/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sysintg.tamiya.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Yuta
 */
public class DatabaseUtils {
    
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tamiyadb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    
    public static Connection retrieveConnection(){
        Connection conn = null;
        try{
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            return conn;
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    
}
