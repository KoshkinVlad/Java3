package ru.geekbrains.MyOwnTests.ClassesToTest;

import ru.geekbrains.MyOwnTests.TestPackage.AfterSuite;
import ru.geekbrains.MyOwnTests.TestPackage.BeforeSuite;

import javax.swing.*;

public class WillDropExceptionOnTest {

    @BeforeSuite
    public void firstBeforeSuite() {
        System.out.println("JOTARO!");
    }

    @AfterSuite
    public void firstAfterSuite() {
        System.out.println("DIO");
    }

    @BeforeSuite
    public void secondBeforeSuite() {
        System.out.println("Yare Yare Daze");
    }

    @AfterSuite
    public void secondAfterSuite() {
        System.out.println("ZA WARUDO!");
    }
}
