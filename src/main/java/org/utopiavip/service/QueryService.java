package org.utopiavip.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@Transactional
public class QueryService {

    public ResultSet executeQuery(Connection connection, String sql){
        try {
            return connection.prepareStatement(sql).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("ExecuteQuery failed", e);
        }
    }


}
