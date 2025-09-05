package org.example.dto;

import org.example.entity.Customer;

import java.util.UUID;

public record CustomerDto(
        UUID customerId,
        String firstName,
        String lastName,
        String email,
        String contactNumber
) {
    public Customer mapToCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(this.customerId);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmail(this.email);
        customer.setContactNumber(this.contactNumber);
        return customer;
    }
}
