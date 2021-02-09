package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Logger {
    static final String LOGGER_ANNOTATION = "Log";

    private Logger() {
    }

    // не совсем понял, что тут происходит, просто скопировал код
    static Calculator createCalculator() {
        InvocationHandler handler = new LoggerInvocationHandler(new MyCalculator());
        return (Calculator) Proxy.newProxyInstance(Logger.class.getClassLoader(), new Class<?>[]{Calculator.class}, handler);
    }

    static class LoggerInvocationHandler implements InvocationHandler {
        private final Calculator myCalculator;

        LoggerInvocationHandler(Calculator myCalculator) {this.myCalculator = myCalculator;}

        private void log(Method method, Object[] args) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            String packageName = this.getClass().getPackageName();
            packageName = packageName.isBlank() ?"":packageName + ".";
            if (Arrays.asList(annotations).toString().contains( packageName + LOGGER_ANNOTATION)) {
                String arguments = String.join(",", Arrays.asList(args).toString());
                System.out.println("Залоггировано: " + method.getName() + arguments);
            }
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log(method, args);
            return method.invoke(myCalculator, args);
        }
    }
}
