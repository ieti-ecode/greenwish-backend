package edu.eci.ieti.ecored.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Company {

    @Id
    private String id;

    private String name;

    private String description;

    private String phoneNumber;

    private String address;

    private String oppeningHours;

    public Company() {
    }

    public Company(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOppeningHours() {
        return oppeningHours;
    }

    public void setOppeningHours(String oppeningHours) {
        this.oppeningHours = oppeningHours;
    }
}
