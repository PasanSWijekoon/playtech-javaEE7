/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pasan
 */


public class Validations {

    public static boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$");
    }

    public static boolean isDouble(String price) {

        return price.matches("^\\d+(\\.\\d{2})?$");

    }

    public static boolean isInteger(String value) {

        return value.matches("^\\d+$");

    }
    
    public static boolean isMobileNumberValid(String text) {
        return text.matches("^07[01245678]{1}[0-9]{7}$");
    }

}
