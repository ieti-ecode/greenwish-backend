package edu.eci.ieti.ecored.controller.company;

public class CompanyDto {

    private String name;

    private String description;

    private String phoneNumber;

    private String address;

    private String oppeningHours;

    public CompanyDto(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public CompanyDto() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getOppeningHours() {
        return oppeningHours;
    }
}
