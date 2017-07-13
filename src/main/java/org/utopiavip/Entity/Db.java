package org.utopiavip.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Db {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String url;
    private String username;
    private String password;
}
