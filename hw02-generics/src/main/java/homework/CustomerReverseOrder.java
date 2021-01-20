package homework;

import java.util.*;

public class CustomerReverseOrder {
    private final Deque<Customer> customers;

    public CustomerReverseOrder() {
        customers = new ArrayDeque<>();
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
