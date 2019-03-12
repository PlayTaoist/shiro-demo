package cn.codepeople.entity;

import lombok.Data;

@Data
public class User {
    private String userId;

    private String name;

    private String password;

    private String perms;

}