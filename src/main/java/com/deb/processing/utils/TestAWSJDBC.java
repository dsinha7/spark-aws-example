package com.deb.processing.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestAWSJDBC {

    public static void main(String[] args) {
        /*

         .option("driver", "org.postgresql.Driver")
                .option("url", "jdbc:postgresql://database-1.cm52utzmehtn.us-east-2.rds.amazonaws.com")
                .option("dbtable", "schema.accounts")
                .option("user", "postgres")
                .option("password", "postgres").load();

         */

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager
                    .getConnection(
                    "jdbc:postgresql://database-1.cm52utzmehtn.us-east-2.rds.amazonaws.com/",
                            "postgres",
                            "postgres");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from accounts");

            while(rs.next()){
                System.out.println(rs.getString("username"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
