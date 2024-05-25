package edu.eci.ieti.greenwish.models.domain;

import lombok.Getter;

@Getter
public enum Role {

    /**
     * Represents the role of an administrator.
     */
    ADMINISTRATOR("Administrator"),

    /**
     * Represents the role of a company.
     */
    COMPANY("Company"),

    /**
     * Represents the role of a customer.
     */
    CUSTOMER("Customer");

    private final String name;

    /**
     * Constructs a new Role with the specified name.
     *
     * @param name the name of the role
     */
    Role(String name) {
        this.name = name;
    }

}
