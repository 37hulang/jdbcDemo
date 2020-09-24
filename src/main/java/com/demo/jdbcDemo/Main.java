/**
 * Copyright (C), 2015-2019
 * FileName: Main
 * Author:   huhu
 * Date:     2019/5/26 21:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.demo.jdbcDemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.demo.jdbcDemo.TestPlusToMysql;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 〈jdbcDemo〉<br>
 * @author huhu
 * @create 2019/5/26
 * @since 1.0.0
 */
public class Main {

    private final String url="jdbc:mysql://rm-bp18o2lgv16a1vixeo.mysql.rds.aliyuncs.com/agent_cloud?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&allowMultiQueries=true";
    private final String user="yqyw_temp_rw";
    private final String pwd="Yqywtemprw!@#$";
    public static void main(String[] args) throws SQLException {
        Connection connection = new Main().connection();
        String sql="select * from customer_question_snapshot WHERE id=1";
        //Statement statement= connection.createStatement();
        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Integer id= resultSet.getInt("id");
            String question_option_str = resultSet.getString("question_option_str");
            StringBuilder sb=new StringBuilder();
            JSONArray jsonArray = JSONObject.parseArray(question_option_str);
            sb.append("[");
            for (int i = 0; i <jsonArray.size() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Integer questionId = jsonObject.getInteger("questionId");
                questionId=questionId+50000;
                System.out.println(questionId);
            }

        }

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 6, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
//        for (int i = 0; i < 500; i++) {
//            Runnable runnable = new TestPlusToMysql(connection);
//            executor.submit(runnable);
//        }
//        executor.shutdown();
//        System.out.println("----------------------------------------------------开始啦--------------------------------------------");
//        for (int i = 0; i <500 ; i++) {
//            Thread thread=new Thread(new TestReduceToMysql(connection));
//            thread.start();
//        }
    }

    private Connection connection() throws SQLException {
        Connection con = null;
        con = (Connection) DriverManager.getConnection(url, user, pwd);
        return con;
    }

    private void ValuePlus(){

    }

    private List<Map<String,Object>> getData() throws SQLException {
        Connection con=connection();
        PreparedStatement preparedStatement=(PreparedStatement) con.prepareStatement("select `dataln_building_id`,`metro_id` ,`metro` ,`station_id` ,`station` from building_info where dataln_building_id !=-1");
        ResultSet set= preparedStatement.executeQuery();
        int columns= set.getMetaData().getColumnCount();
        List<Map<String,Object>> list=new LinkedList<Map<String, Object>>();
        ResultSetMetaData resultSetMetaData= set.getMetaData();
        while (set.next()){
            Map<String,Object> map=new HashMap<String, Object>(columns);
            for (int i = 1; i <= columns; i++) {
                map.put(resultSetMetaData.getColumnName(i),set.getString(i));
            }
            list.add(map);
        }

        StringBuilder sb=new StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            sb.append("update building_info set metro_id='"+list.get(i).get("metro_id")+"',metro='"+list.get(i).get("metro")+"',station_id='"+list.get(i).get("station_id")+"',station='"+list.get(i).get("station")+"' where dataln_building_id='"+list.get(i).get("dataln_building_id")+"';\n");
        }
        String dd=sb.toString();
        return list;
    }

}