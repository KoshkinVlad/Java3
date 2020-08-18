package ru.geekbrains.MyOwnTests.ClassesToTest;

import ru.geekbrains.MyOwnTests.TestPackage.AfterSuite;
import ru.geekbrains.MyOwnTests.TestPackage.BeforeSuite;
import ru.geekbrains.MyOwnTests.TestPackage.Test;

public class ClassForTest {
    @BeforeSuite
    public void printHello() {
        System.out.println("Hewwo!");
    }

    @Test(value = 7)
    public void printSeven() {
        System.out.println(7);
    }

    @Test(value = 5)
    public void printFive() {
        System.out.println(5);
    }

    @Test(value = 8)
    public void printEight() {
        System.out.println(8);
    }

    @AfterSuite
    public void printBye() {
        System.out.println("Goodbye!");
    }
}
