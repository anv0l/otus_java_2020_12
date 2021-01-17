package homework;


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
            return this.customers.firstEntry();
        }
        else {
            return null;
        }
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        for (Map.Entry<Customer, String> entryCustomer : this.customers.entrySet()) {
            if (entryCustomer.getKey().getScores() > customer.getScores() ) {
                return entryCustomer;
            }
        }
        return null;
    }

    public void add(Customer customer, String data) {
        if (Customer.getCustomers().contains(customer)) {
            this.customers.put(customer.getCustomers().get(customer.getCustomers().indexOf(customer)), data); // зато теперь проходим тест
        }
        else {
            this.customers.put(customer, data);
        }
    }

}
