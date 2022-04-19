package homework;


import java.util.*;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    ArrayList<Customer> reversList = new ArrayList<>();

    public void add(Customer customer) {
        reversList.add(customer);

    }

    public Customer take() {
        return reversList.remove(reversList.size() - 1);
    }
}
