package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@EnableAutoConfiguration
@ComponentScan(
        basePackages = {
                "controller",
                "service",
                "filter"})
@ImportResource("extra-beans.xml")
@EnableSpringDataWebSupport
@SpringBootApplication
@EnableJpaRepositories
@EntityScan("entity")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        try {
//            Class.forName("org.gjt.mm.mysql.Driver");     //加载MYSQL JDBC驱动程序
//            System.out.println("Success loading Mysql Driver!");
//        }
//        catch (Exception e) {
//            System.out.print("Error loading Mysql Driver!");
//            e.printStackTrace();
//        }
//
//        try {
//            Connection connect = DriverManager.getConnection(
//                    "jdbc:mysql://202.120.40.86:33306/micro_campus", "root", "oblige");
////连接URL为   jdbc:mysql//服务器地址/数据库名
////后面的2个参数分别是登陆用户名和密码
//            System.out.println("Success connect Mysql server!");
//
//            Statement stmt = connect.createStatement();
//            ResultSet rs = stmt.executeQuery("select * from USER");
//            while (rs.next()) {
//                System.out.println(rs.getString("id"));
//
//            }
//        }
//        catch (Exception e) {
//            System.out.print("get data error!");
//            e.printStackTrace();
//        }

    }
}
