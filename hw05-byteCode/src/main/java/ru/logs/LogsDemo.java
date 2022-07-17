package ru.logs;

import ru.logs.proxy.Ioc;
import ru.logs.testedClass.TestedClasses;
import ru.logs.testedClass.TestedClassImpl;

public class LogsDemo {
    public static void main(String[] args) {
        TestedClasses iTestedClasses = Ioc.createItestedClasses();
        // Если метод есть в интерфейсе, то  работает
        iTestedClasses.calculation(6);
        iTestedClasses.calculation(6,7,8);
        iTestedClasses.calculation(9,"привет");
        iTestedClasses.calculation(" без логов ",9," без логов");
        // Если метод создать только в классе и потом создать класс,
        // то воспользоваться InvocationHandler  не получается, я правильно понимаю, или что-то не так делаю?
        // попытки просто сделать потом приведение типа не увенчались успехом
        TestedClassImpl testedClass = new TestedClassImpl();
        testedClass.calculation(9,"без интерфейса");
        testedClass.calculation(1,2,3,"без интерфейса");

    }
}
