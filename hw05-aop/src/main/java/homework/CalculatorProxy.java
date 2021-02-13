package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CalculatorProxy {
    private static final Class<? extends Annotation> LOGGER_ANNOTATION = Log.class;
    private static Set<String> logMethods;

    private CalculatorProxy() {
    }

    // Создаём прокси-объект, получаем методы и параметры, на которых стоит аннотация @log
    static Calculator createCalculator() {
        InvocationHandler handler = new ProxyInvocationHandler(new MyCalculator());
        Method[] methods = MyCalculator.class.getMethods();
        logMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(LOGGER_ANNOTATION))
                .map(CalculatorProxy::getMethodParams)
                .collect(Collectors.toSet());
        return (Calculator) Proxy.newProxyInstance(CalculatorProxy.class.getClassLoader(), new Class<?>[]{Calculator.class}, handler);
    }

    private static String getMethodParams(Method method) {
        return method.getName() + Arrays.asList(method.getParameterTypes()).toString();
    }

    static class ProxyInvocationHandler implements InvocationHandler {
        private final Calculator myCalculator;

        ProxyInvocationHandler(Calculator myCalculator) {this.myCalculator = myCalculator;}

        private void log(Method method, Object[] args) {
            String methodParams = getMethodParams(method);
            if (logMethods.contains(methodParams)) {
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
