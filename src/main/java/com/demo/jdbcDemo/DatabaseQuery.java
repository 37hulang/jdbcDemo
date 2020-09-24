package com.demo.jdbcDemo;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Description
 * @Author 74716
 * @Date 2020/6/10 11:02
 **/
public class DatabaseQuery {

    public static void main(String[] args) {
        System.out.println("请输入始发城市");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        if (null != s && s.length() > 0) {
            //查询航班
            String querySql = "SELECT * FROM Airline WHERE StartCity='" + s+"'";
            List<Map<String, Object>> data = exec(querySql);
            if (null!=data){
                System.out.println("始发站为"+s+"的航班有：");
                for (Map<String,Object> item:
                     data) {
                    String outString=String.format("航班：%s 始发城市为：%s 目的城市为：%s 离港时间为 %s",item.get("AirlineNo"),item.get("StartCity"),item.get("EndCity") ,item.get("LeaveTime")) ;
                    System.out.println(outString);
                }
            }else{
                System.out.println("暂无此始发城市航班信息");
            }
        }
    }

    //连接数据库
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlserver://172.16.9.85:1433;DatabaseName=test";
        String user = "sa";
        String pwd = "tospur98";
        Connection connection = DriverManager.getConnection(url, user, pwd);
        return connection;
    }

    //执行sql
    public static List<Map<String, Object>> exec(String sql) {
        LinkedList<Map<String, Object>> objects = new LinkedList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            ResultSet resultSet = connection
                    .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                    .executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                objects.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return objects;
    }
}
