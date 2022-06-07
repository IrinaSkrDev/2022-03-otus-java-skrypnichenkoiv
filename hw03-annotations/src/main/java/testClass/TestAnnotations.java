package testClass;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestAnnotations {
    Integer versionOfObject;

    public TestAnnotations(Integer versionOfObject) {
        this.versionOfObject = versionOfObject;
    }

    @Before
    public void firstBeforeMethod() {
        System.out.println(" Метод с аннотацией Before Готовим инстанс с versionOfObject = " + this.versionOfObject.toString());

    }

    @Before
    public void firstBeforeMethodWithException() throws Exception {
        System.out.println("Метод с аннотацией Before и исключением  versionOfObject = " + this.versionOfObject);
        if (versionOfObject == 2) {
            throw new RuntimeException("Exception метода Before");
        }
    }

    @Test
    public String secondMethod() {
        String result = "Первый тестирующий метод " + this.versionOfObject.toString();
        System.out.println(result);
        return result;
    }

    @Test
    public String secondMethodTest() {
        String result = "Второй тестирующий метод " + this.versionOfObject.toString();
        System.out.println(result);
        return result;
    }

    @Test
    public String secondMethodWithException() throws Exception {
        String result = "Третий тестирующий метод, должен упасть с ошибкой " + this.versionOfObject.toString();
        System.out.println(result);
        if (versionOfObject == 3) {
            throw new RuntimeException("Exception метода с аннотацией Test и исключением");
        }
        return result;
    }

    @After
    public void thirdMethod() {
        System.out.println("Метод с аннотацией After  " + this.versionOfObject.toString());
        this.versionOfObject = null;
    }

}
