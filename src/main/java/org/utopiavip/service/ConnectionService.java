package org.utopiavip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utopiavip.Entity.Db;
import org.utopiavip.dao.DbDao;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ConnectionService {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionService.class);

    private static Map<String, Connection> coonPool = new HashMap<>();

    @Autowired
    private DbDao dbDao;

    public Connection getConnection(String name) {
        Connection connection = coonPool.get(name);
        try {
            if (connection != null && !connection.isClosed() && connection.isValid(1000)) {
                return connection;
            }

            Db db = dbDao.findByName(name);
            if (db == null) {
                throw new IllegalArgumentException("No db config found for name[" + name + "]");
            }

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            coonPool.put(name, connection);
        } catch (Exception e) {
            logger.error("Get connection failed, name {}", name);
            logger.error("Stacktrace is ", e);
        }

        return connection;
    }
}
