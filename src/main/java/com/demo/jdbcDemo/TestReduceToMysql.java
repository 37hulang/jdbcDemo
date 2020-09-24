package com.demo.jdbcDemo;

import com.mysql.jdbc.Connection;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName TestReduceToMysql
 * @Description
 * @Author 74716
 * @Date 2019/6/5 16:56
 * @Version 1.0
 **/
public class TestReduceToMysql implements Runnable {

    private Connection connection;
    public TestReduceToMysql(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void run() {
        String sql = "select * from customer_question_snapshot LIMIT 1";
        try {
            Statement statement= connection.createStatement();
            int sucNumber= statement.executeUpdate(sql);
            System.out.println(Thread.currentThread().getName() + "-----减等于结果>>>>>" + sucNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
