package com.example.selfcheckout2.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "supermarket")
@Getter
@Setter
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public String name;
    public String collaborationStartDate;
    public String emailContact;
    public String address;
    public String account;
    public String dataFormat;

    public double latitude;
    public double longitude;

    public String endpoint;

    @OneToOne(mappedBy = "supermarket", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Schedule schedule;

    @OneToMany(mappedBy = "supermarket")
    public List<Product> products;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supermarket() {
    }

    public String getCollaborationStartDate() {
        return collaborationStartDate;
    }

    public void setCollaborationStartDate(String collaborationStartDate) {
        this.collaborationStartDate = collaborationStartDate;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public Supermarket(String name, String collaborationStartDate, String emailContact, String address, String account, String dataFormat) {
        this.name = name;
        this.collaborationStartDate = collaborationStartDate;
        this.emailContact = emailContact;
        this.address = address;
        this.account = account;
        this.dataFormat = dataFormat;
    }
}
