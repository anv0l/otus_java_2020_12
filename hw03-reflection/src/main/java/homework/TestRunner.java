package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestRunner {

    public static void main(String[] args) throws NoSuchMethodException, AssertionError {
        Method beforeMethodInvoke = null;
        Method afterMethodInvoke = null;
        ArrayList<Method> testMethods = new ArrayList<>();

        Class<TestClass> testClass = TestClass.class;
        String packageName = testClass.getPackageName();
        Method[] methods = testClass.getDeclaredMethods();

        String beforeMethod =  "@" + packageName + ".Before()";
        String afterMethod =  "@" + packageName + ".After()";
        String testMethod = "@" + packageName + ".Test()";

        // Получим список методов для теста
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.toString().equals(beforeMethod)) {
                    beforeMethodInvoke = method;
                }
                if (annotation.toString().equals(afterMethod)) {
                    afterMethodInvoke = method;
                }
                if (annotation.toString().equals(testMethod)) {
                    testMethods.add(method);
                }
            }
        }

        long totalTests = 0;
        long failedTests = 0;
        for (Method test : testMethods) {
            totalTests++;
            // Создаём экземпляр тестового класса
            Class[] params = {Long.class, String.class};
            Constructor<TestClass> constructor = testClass.getConstructor(params);
            try {
                TestClass testClassInstance = constructor.newInstance(totalTests, "Point #" + totalTests);
                try {
                    //@Before
                    if (beforeMethodInvoke != null) {
                        var resultBefore = beforeMethodInvoke.invoke(testClassInstance, 2D, 4D, 0.3D, -2.5D);
                    }

                    //@Test
                    var resultTest = test.invoke(testClassInstance);

                    //@After
                    if (afterMethodInvoke != null) {
                        var resultAfter = afterMethodInvoke.invoke(testClassInstance);
                    }
                    System.out.println("Test # " + totalTests + " (" + test.getName() + ") " + "PASSED.");
                } catch (InvocationTargetException | IllegalAccessException | AssertionError ite) {
                    failedTests++;
                    System.out.println("Test # " + totalTests + " (" + test.getName() + ") " + "FAILED.");
                }
            }
            catch (Exception e){
                failedTests++;
                System.out.println("Test # " + totalTests + " (" + test.getName()  +") " + "FAILED.");
            }
        }

        System.out.println("---------------------");

        if (failedTests > 0) {
            System.out.println(totalTests - failedTests + " out of " + totalTests + " test(s) PASSED.");
        }
        else {
            System.out.println("All " + totalTests + " PASSED.");
        }
    }
}
