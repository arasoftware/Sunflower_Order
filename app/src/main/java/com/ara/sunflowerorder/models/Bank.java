package com.ara.sunflowerorder.models;

public class Bank {

    String accounts_id,accounts_name,accounts_group_id,accounts_type;

    public Bank() {

    }

    public String getAccounts_id() {
        return accounts_id;
    }

    public void setAccounts_id(String accounts_id) {
        this.accounts_id = accounts_id;
    }

    public String getAccounts_name() {
        return accounts_name;
    }

    public void setAccounts_name(String accounts_name) {
        this.accounts_name = accounts_name;
    }

    public String getAccounts_group_id() {
        return accounts_group_id;
    }

    public void setAccounts_group_id(String accounts_group_id) {
        this.accounts_group_id = accounts_group_id;
    }

    public String getAccounts_type() {
        return accounts_type;
    }

    public void setAccounts_type(String accounts_type) {
        this.accounts_type = accounts_type;
    }

    public Bank(String accounts_id, String accounts_name, String accounts_group_id, String accounts_type) {
        this.accounts_id = accounts_id;
        this.accounts_name = accounts_name;
        this.accounts_group_id = accounts_group_id;
        this.accounts_type = accounts_type;

    }
}
