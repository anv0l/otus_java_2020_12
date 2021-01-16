package homework;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private final long id;
    private String name;
    private long scores;

    // Список уже добавленных клиентов
    private static Map<Customer, Customer> customers = new HashMap<>();

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
        if (!customers.containsKey(this)) {
            customers.put(this, this); // используется для прохождения теста на модификацию коллекции
        }
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return this.scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    public static Map<Customer, Customer> getCustomers() {
        return customers;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", scores=" + this.scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != customer.id) return false;
        if (scores != customer.scores) return false;
        return name != null ? name.equals(customer.name) : customer.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        //result = 31 * result + (int) (scores ^ (scores >>> 32));
        return result;
    }
}
