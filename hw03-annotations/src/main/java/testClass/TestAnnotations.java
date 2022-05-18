package testClass;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.testedClasses.ClassNeedForTesting;

public class TestAnnotations {
    int versionOfObject;
    ClassNeedForTesting classNeedForTesting;

    public TestAnnotations(int versionOfObject) {
        this.versionOfObject = versionOfObject;
    }

    @Before
    public void firstBeforeMethod() {
       // classNeedForTesting = new ClassNeedForTesting("Test me!");
        System.out.println("Метод с аннотацией Before ");
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
        System.out.println("Метод с аннотацией Test  " + classNeedForTesting.getMyFIeld());

    }

    @Test
    public void secondMethodDuble() {
        System.out.println("Метод с аннотацией Test  " + classNeedForTesting.getMyFIeld());
    }

    @Test
    public void secondMethodWithException() throws Exception {
        System.out.println("Метод с аннотацией Test и исключением  ");
        if (versionOfObject == 3) {
            throw new RuntimeException("Exception метода с аннотацией Test и исключением");
        }
    }

    @After
    public void thirdMethod() {
        System.out.println("Метод с аннотацией After" + this.classNeedForTesting.getMyFIeld());
        this.classNeedForTesting = null;
        System.out.println("Метод с аннотацией After Объект готов для GC  " + this.classNeedForTesting);
    }

}
