package homework;


import com.sun.source.tree.Tree;

import java.util.*;

public class CustomerService {
    private TreeMap<Customer, String> customers;

    public CustomerService() {
        customers = new TreeMap<>(new Comparator<>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o1.getScores().compareTo(o2.getScores());
            }
        });
    }

    public Map.Entry<Customer, String> getSmallest() {
        if (!this.customers.isEmpty()) {
            Map.Entry<Customer, String> localEntry = customers.firstEntry();
            Customer localCustomer = new Customer(localEntry.getKey().getId(), localEntry.getKey().getName(), localEntry.getKey().getScores());
            return new AbstractMap.SimpleEntry<Customer, String>(localCustomer, localEntry.getValue());
        }
        else {
            return null;
        }
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        for (Map.Entry<Customer, String> entryCustomer : this.customers.entrySet()) {
            if (entryCustomer.getKey().getScores() > customer.getScores() ) {
                Customer localCustomer = new Customer(entryCustomer.getKey().getId(), entryCustomer.getKey().getName(), entryCustomer.getKey().getScores());
                return new AbstractMap.SimpleEntry<Customer, String>(localCustomer, entryCustomer.getValue());
            }
        }
        return null;
    }

    public void add(Customer customer, String data) {
        this.customers.put(customer, data);
    }

}
