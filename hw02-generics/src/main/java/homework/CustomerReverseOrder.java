package homework;

import java.util.*;

public class CustomerReverseOrder {
    private LinkedList<Customer> customers;

    public CustomerReverseOrder() {
        customers = new LinkedList<>();
    }

    public void add(Customer customer) {
        customers.push(customer);
    }

    public Customer take() {
        if (!customers.isEmpty()) {
            return customers.pop();
        }
        else {
            return null;
        }
    }
}
