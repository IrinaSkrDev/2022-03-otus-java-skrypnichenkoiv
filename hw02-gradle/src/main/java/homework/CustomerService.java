package homework;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> customerServiceMap = new TreeMap<>(Comparator.comparingLong(c -> c.getScores()));
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        TreeMap<Customer, String> customerServiceMapCopy = new TreeMap<>(Comparator.comparingLong(c -> c.getScores()));
        for (Customer key : customerServiceMap.keySet()) {
            Customer customerCopy = new Customer(key.getId(), key.getName(), key.getScores());
            String val = new String();
            val = customerServiceMap.get(key);
            customerServiceMapCopy.put(customerCopy, val);
        }

        return customerServiceMapCopy.firstEntry();

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        TreeMap<Customer, String> customerServiceMapCopy = new TreeMap<>(Comparator.comparingLong(c -> c.getScores()));
        for (Customer key : customerServiceMap.keySet()) {
            Customer customerCopy = new Customer(key.getId(), key.getName(), key.getScores());
            String val = new String();
            val = customerServiceMap.get(key);
            customerServiceMapCopy.put(customerCopy, val);
        }

        return customerServiceMapCopy.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        customerServiceMap.put(customer,data);
    }
}
