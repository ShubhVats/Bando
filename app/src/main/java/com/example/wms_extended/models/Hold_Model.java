package com.example.wms_extended.models;

import java.util.ArrayList;

public class Hold_Model {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public String[] getBins2Hold() {
        return bins2Hold;
    }

    public void setBins2Hold(String[] bins2Hold) {
        this.bins2Hold = bins2Hold;
    }

    String userId;
    String userComments;
    String[] bins2Hold;

    public class bins2Hold {
        String bin;

        public String getBin() {
            return bin;
        }

        public void setBin(String bin) {
            this.bin = bin;
        }
    }
}
