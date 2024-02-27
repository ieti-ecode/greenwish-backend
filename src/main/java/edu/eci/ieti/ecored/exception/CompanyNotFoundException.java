package edu.eci.ieti.ecored.exception;

public class CompanyNotFoundException extends Exception{
    public CompanyNotFoundException(){
        super("Company not found");
    }
}
