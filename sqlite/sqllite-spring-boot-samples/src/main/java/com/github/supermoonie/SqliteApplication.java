package com.github.supermoonie;

import com.github.supermoonie.model.Beer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * Hello world!
 *
 * @author wangc
 */
@SpringBootApplication
public class SqliteApplication implements CommandLineRunner {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SqliteApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //Create the database table:
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS beers(name VARCHAR(100))");

        //Insert a record:
        jdbcTemplate.execute("INSERT INTO beers VALUES ('Stella')");

        //Read records:
        List<Beer> beers = jdbcTemplate.query("SELECT * FROM beers",
                (resultSet, rowNum) -> new Beer(resultSet.getString("name")));

        //Print read records:
        beers.forEach(System.out::println);
    }
}
