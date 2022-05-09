package ru.otus;

import testClass.TestAnnotations;

import static ru.otus.runner.RunTestsWithAnnotations.runTests;

public class RunMyTests {
    public static void main(String[] args) throws Exception {
        TestAnnotations testAnnotations = new TestAnnotations(1);
        TestAnnotations testAnnotationsSecondObj = new TestAnnotations(2);
        TestAnnotations testAnnotationsThirdObj = new TestAnnotations(3);
        String result = runTests(testAnnotations);
        System.out.println(result);
        String resultSecond = runTests(testAnnotationsSecondObj);
        System.out.println(resultSecond);
        String resultThird = runTests(testAnnotationsThirdObj);
        System.out.println(resultThird);
    }


}
