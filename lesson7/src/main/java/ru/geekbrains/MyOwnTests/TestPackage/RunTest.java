package ru.geekbrains.MyOwnTests.TestPackage;

import ru.geekbrains.MyOwnTests.ClassesToTest.ClassForTest;
import ru.geekbrains.MyOwnTests.ClassesToTest.Init;
import ru.geekbrains.MyOwnTests.ClassesToTest.TrajectoryCount;
import ru.geekbrains.MyOwnTests.ClassesToTest.WillDropExceptionOnTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RunTest {

    // constructorParameters - параметры для конструктора класса
    // все классы, которые хотят тестироваться через данный тестовый класс
    // должны в конструкторе либо не принимать ничего, либо принимать массив
    public static void start(Class classToTest, Double[] constructorParameters) throws Exception {
        int beforeSuiteMethodNumber = -1;
        int afterSuiteMethodNumber = -1;
        Method[] methods = classToTest.getDeclaredMethods();
        // отсортировать массив методов по значениям value, чтобы потом в нужном порядке вызывать их
        // тех, у кого нет аннотации test, выношу в самое начало массива (считаю, что value=10)
        for (int i = methods.length - 1; i > 0; i--) {
            for (int k = 0; k < i; k++) {
                int currentPriority;
                int nextPriority;
                if (methods[k].isAnnotationPresent(Test.class)) {
                    Test test = methods[k].getAnnotation(Test.class);
                    currentPriority = test.value();
                } else {
                    currentPriority = 10;
                }
                if (methods[k + 1].isAnnotationPresent(Test.class)) {
                    Test test = methods[k + 1].getAnnotation(Test.class);
                    nextPriority = test.value();
                } else {
                    nextPriority = 10;
                }
                if (nextPriority > currentPriority) {
                    Method tmp = methods[k];
                    methods[k] = methods[k + 1];
                    methods[k + 1] = tmp;
                }

            }
        }
        // поиск методов с аннотациями BeforeSuite и AfterSuite
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethodNumber != -1) { // если не -1, значит, ранее это значение было изменено
                    throw new RuntimeException("More than one " + BeforeSuite.class.getSimpleName() + " annotation presents!");
                }
                beforeSuiteMethodNumber = i;
            } else if (methods[i].isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethodNumber != -1) {
                    throw new RuntimeException("More than one " + AfterSuite.class.getSimpleName() + " annotation presents!");
                }
                afterSuiteMethodNumber = i;
            }
        }
        // тестируемый объект может быть с параметрами для конструктора или без них
        Object testObject = null;
        if (constructorParameters == null) {
            testObject = classToTest.newInstance();
        } else {
            Constructor constructor = classToTest.getConstructor(Double[].class);
            testObject = constructor.newInstance((Object) constructorParameters); // почему тут нужен каст в объект???
        }

        methods[beforeSuiteMethodNumber].invoke(testObject);
        for (int i = 0; i < methods.length; i++) {
            if (i == beforeSuiteMethodNumber || i == afterSuiteMethodNumber) {
                continue;
            } else if (methods[i].isAnnotationPresent(Test.class)) {
                methods[i].invoke(testObject);
            }
        }
        methods[afterSuiteMethodNumber].invoke(testObject);
    }

    // если по имени, то ищем класс и запускаем основной метод для тестирования
    public static void start(String className, Object[] constructorParameters) throws Exception {
        Class classToTest = Class.forName(className);
//        start(classToTest, constructorParameters);
    }

    public static void main(String[] args) {

        try {
            RunTest.start(ClassForTest.class, null);
            RunTest.start(ClassForTest.class.getName(), null);

            Double[] constructorParameters = new Double[4];
            constructorParameters[Init.MASS] = 5.4;
            constructorParameters[Init.HEIGHT] = Double.valueOf(10);
            constructorParameters[Init.ANGLE] = Double.valueOf(45);
            constructorParameters[Init.VELOCITY] = 4.5;


            RunTest.start(TrajectoryCount.class, constructorParameters);

            RunTest.start(WillDropExceptionOnTest.class, null);
            RunTest.start(WillDropExceptionOnTest.class.getName(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
