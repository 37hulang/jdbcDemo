package com.demo.jdbcDemo;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName TestPlusToMysql
 * @Description
 * @Author 74716
 * @Date 2019/6/5 16:28
 * @Version 1.0
 **/
public class TestPlusToMysql implements Runnable {
    private Connection connection;
    public TestPlusToMysql(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void run() {
        String sql = "update topic_vote set supporter=supporter+1 where id=1";
        try {
            Statement statement= connection.createStatement();
           int sucNumber= statement.executeUpdate(sql);
            System.out.println(Thread.currentThread().getName() + "-----加等于结果>>>>>" + sucNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
