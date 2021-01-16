package homework;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomerService {
    private Map<Customer, String> customers;

    public CustomerService() {
        customers = new HashMap<Customer, String>();
    }

    public Map.Entry<Customer, String> getSmallest() {
        long minScore = Long.MAX_VALUE;

        Iterator<Map.Entry<Customer, String>> customers = this.customers.entrySet().iterator();
        Map.Entry<Customer, String> result = null;
        while (customers.hasNext()) {
            Map.Entry<Customer, String> customer = customers.next();
            if ( customer.getKey().getScores() <= minScore ) {
                result = customer;
                minScore = customer.getKey().getScores();
            }
        }
        return result;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        long minDiff = Long.MAX_VALUE;
        long originalScore = customer.getScores();
        long currentScore;

        Iterator<Map.Entry<Customer, String>> customers = this.customers.entrySet().iterator();
        Map.Entry<Customer, String> result = null;
        while (customers.hasNext()) {
            Map.Entry<Customer, String> customerData = customers.next();
            currentScore = customerData.getKey().getScores();
            if ( originalScore < customerData.getKey().getScores()) {
                if (minDiff > currentScore - originalScore) {
                    minDiff = currentScore - originalScore;
                    result = customerData;
                }
            }
        }
        return result;
    }

    public void add(Customer customer, String data) {
        if (Customer.getCustomers().containsKey(customer)) {
            customers.put(customer.getCustomers().get(customer), data); // зато теперь проходим тест
        }
        else {
            customers.put(customer, data);
        }
    }


}
