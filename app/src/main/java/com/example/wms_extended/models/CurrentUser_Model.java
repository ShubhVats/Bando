package com.example.wms_extended.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CurrentUser_Model {
    String user;
    String token;

    public CurrentUser_Model(Serializable toString) {
        user = getUser();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<CurrentUser_Model.userData> getUserData() {
        return userData;
    }

    public void setUserData(ArrayList<CurrentUser_Model.userData> userData) {
        this.userData = userData;
    }

    ArrayList<userData> userData;

    public class userData {
        String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getWorkingArea() {
            return workingArea;
        }

        public void setWorkingArea(String workingArea) {
            this.workingArea = workingArea;
        }

        public String getUserRoles() {
            return userRoles;
        }

        public void setUserRoles(String userRoles) {
            this.userRoles = userRoles;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        String userName;
        String workingArea;
        String userRoles;
        Integer status;

    }
}
