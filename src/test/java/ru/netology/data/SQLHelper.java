package ru.netology.data;


import lombok.SneakyThrows;

import lombok.Value;

import lombok.val;

import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }

    private static Connection getConn() throws SQLException {

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");

    }

    @SneakyThrows

    public static void cleanTable() {

        val deletePaymentEntity = "DELETE FROM payment_entity ";

        val runner = new QueryRunner();

        try (val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")) {

            runner.update(conn, deletePaymentEntity);

        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }

    }

    @SneakyThrows

    public static String getStatus(String table) {

        String sql = "SELECT status FROM " + table;

        QueryRunner runner = new QueryRunner();

        String status = null;

        Connection conn = getConn();

        status = runner.query(conn, sql, new ScalarHandler<>());

        return status;

    }

    public static String getPaymentStatus() {

        return getStatus("payment_entity");

    }

    public static String getCreditStatus() {

        return getStatus("credit_request_entity");

    }

}