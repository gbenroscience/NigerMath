/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package security;

/**
 *
 * @author GBENRO
 */
public class User {
    private String userName;
    private String key;

    public User(String userName, String key) {
        this.userName = userName;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }







}