package homework;


import java.util.*;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    private Stack<Customer> reversList = new Stack<>();

    public void add(Customer customer) {
        reversList.push(customer);

    }

    public Customer take() {
        return reversList.pop();
    }
}
