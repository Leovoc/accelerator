package org.utopiavip.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.utopiavip.Entity.Db;

public interface DbDao extends JpaRepository<Db, Long> {

    Db findByName(String name);
}
