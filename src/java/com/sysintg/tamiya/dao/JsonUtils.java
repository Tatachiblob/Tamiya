package com.sysintg.tamiya.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class JsonUtils {

    public static JSONArray toJSONArray(ResultSet rs) throws Exception {
        JSONArray jsonArray = new JSONArray();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                int numColumns = rsmd.getColumnCount();
                JSONObject jsonObject = new JSONObject();

                for (int i = 1; i <= numColumns; i++) {
                    String colName = rsmd.getColumnName(i);

                    if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                        jsonObject.put(colName, rs.getArray(colName));
                        /*Debug*/ System.out.println("ToJson: ARRAY");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                        jsonObject.put(colName, rs.getInt(colName));
                        /*Debug*/ System.out.println("ToJson: BIGINT");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                        jsonObject.put(colName, rs.getBoolean(colName));
                        /*Debug*/ System.out.println("ToJson: BOOLEAN");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                        jsonObject.put(colName, rs.getBlob(colName));
                        /*Debug*/ System.out.println("ToJson: BLOB");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                        jsonObject.put(colName, rs.getDouble(colName));
                        /*Debug*/ System.out.println("ToJson: DOUBLE");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                        jsonObject.put(colName, rs.getFloat(colName));
                        /*Debug*/ System.out.println("ToJson: FLOAT");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                        jsonObject.put(colName, rs.getInt(colName));
                        /*Debug*/ System.out.println("ToJson: INTEGER");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                        jsonObject.put(colName, rs.getString(colName));
                        /*Debug*/ System.out.println("ToJson: NVARCHAR");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
                        jsonObject.put(colName, rs.getString(colName));
                        /*Debug*/ System.out.println("ToJson: VARCHAR");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                        jsonObject.put(colName, rs.getInt(colName));
                        /*Debug*/ System.out.println("ToJson: TINYINT");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                        jsonObject.put(colName, rs.getInt(colName));
                        /*Debug*/ System.out.println("ToJson: SMALLINT");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                        jsonObject.put(colName, rs.getDate(colName));
                        /*Debug*/ System.out.println("ToJson: DATE");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                        jsonObject.put(colName, rs.getTimestamp(colName));
                        /*Debug*/ System.out.println("ToJson: TIMESTAMP");
                    } else if (rsmd.getColumnType(i) == java.sql.Types.NUMERIC) {
                        jsonObject.put(colName, rs.getBigDecimal(colName));
                        // /*Debug*/ System.out.println("ToJson: NUMERIC");
                    } else {
                        jsonObject.put(colName, rs.getObject(colName));
                        /*Debug*/ System.out.println("ToJson: Object " + colName);
                    }

                }//end foreach
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

}