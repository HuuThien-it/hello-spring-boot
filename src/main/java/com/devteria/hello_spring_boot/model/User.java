
package com.devteria.hello_spring_boot.model;

public class User {

    private Integer idUser;
    private String userName;
    private String password;
    private String token;

    public User() {
    }

    public User(Integer idUser, String userName,
                String password, String token) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
        this.token = token;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}