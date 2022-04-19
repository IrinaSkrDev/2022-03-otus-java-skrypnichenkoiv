package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private TreeMap<Customer, String> customerServiceMap = new TreeMap<>(Comparator.comparingLong(c -> c.getScores()));
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return customerServiceMap.firstEntry();

    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return customerServiceMap.ceilingEntry(customer);
    }

    public void add(Customer customer, String data) {
        customerServiceMap.put(customer, data);

    }
}
