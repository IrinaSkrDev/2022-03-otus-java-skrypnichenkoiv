package ru.otus;

import testClass.TestAnnotations;

import static ru.otus.runner.RunTestsWithAnnotations.runTests;

public class RunMyTests {
    public static void main(String[] args) throws Exception {
        TestAnnotations testAnnotations = new TestAnnotations(1);
        String result = runTests(testAnnotations);
        System.out.println(result);

    }


}
