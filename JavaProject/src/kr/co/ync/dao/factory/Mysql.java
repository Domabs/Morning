package kr.co.ync.dao.factory;


import kr.co.ync.dao.MemberDao;
import kr.co.ync.dao.MemberDaoImpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Mysql extends DaoFactory {

    private static String URL;
    private static String DRIVER;
    private static String USERNAME;
    private static String PASSWORD;

    static {
        try (InputStream input = Mysql.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            URL = prop.getProperty("db.url");
            DRIVER = prop.getProperty("db.driver");
            USERNAME = prop.getProperty("db.username");
            PASSWORD = prop.getProperty("db.password");
        } catch (Exception e) {
            System.err.println("config.properties :");
            e.printStackTrace();
        }
    }

    @Override
    public Connection openConnection() {
        try {
            Class.forName(DRIVER).newInstance();
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public MemberDao getMemberDao() {
        return new MemberDaoImpl();
    }
}
