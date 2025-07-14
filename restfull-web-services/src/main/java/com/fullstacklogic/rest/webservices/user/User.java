package com.fullstacklogic.rest.webservices.user;

import java.time.LocalDate;

public class User {

    private Integer id;
    private String name;
    private String phoneNo;
    private String mailId;
    private LocalDate birthDate;

    public User() {
        // Default constructor
    }

    public User(Integer id, String name, String phoneNo, String mailId, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.mailId = mailId;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", phoneNo=" + phoneNo +
                ", mailId=" + mailId + ", birthDate=" + birthDate + "]";
    }
}
