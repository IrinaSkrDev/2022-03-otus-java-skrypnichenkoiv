package testClass;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestAnnotations {
    int versionOfObject;

    public TestAnnotations(int versionOfObject) {
        this.versionOfObject = versionOfObject;
    }

    @Before
    public void firstBeforeMethod() {
        System.out.println("Метод с аннотацией Before");
    }

    @Before
    public void firstBeforeMethodWithException() throws Exception {
        System.out.println("Метод с аннотацией Before и исключением");
        if (versionOfObject == 2) {
            throw new RuntimeException("Exception метода Before");
        }
    }

    @Test
    public void secondMethod() {
        System.out.println("Метод с аннотацией Test");
    }

    @Test
    public void secondMethodDuble() {
        System.out.println("Метод с аннотацией Test");
    }

    @Test
    public void secondMethodWithException() throws Exception {
        System.out.println("Метод с аннотацией Test и исключением");
        if (versionOfObject == 3) {
            throw new RuntimeException("Exception метода с аннотацией Test и исключением");
        }
    }

    @After
    public void thirdMethod() {
        System.out.println("Метод с аннотацией After");
    }

}
