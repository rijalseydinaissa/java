package org.odc.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Properties;

public class DatabaseImpl implements Database {
    private HikariDataSource dataSource;

    public DatabaseImpl() {
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.user"));
            config.setPassword(props.getProperty("db.password"));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }
    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    @Override
    public void close() throws SQLException {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}