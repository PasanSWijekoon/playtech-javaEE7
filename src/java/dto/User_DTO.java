package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pasan
 */
public class User_DTO implements Serializable{
    
    @Expose
    private String rememberMe;
    @Expose
    private String first_name;
    @Expose
    private String last_name;
    @Expose
    private String email;
    @Expose(deserialize = true, serialize = false)
    private String password;

    public User_DTO() {
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the rememberMe
     */
    public String getRememberMe() {
        return rememberMe;
    }

    /**
     * @param rememberMe the rememberMe to set
     */
    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }
    
}
