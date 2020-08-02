package com.takeseem.demo.aop;

import java.text.DecimalFormat;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.takeseem.demo.aop.service.CountService;


/**
 * ----------------
 * 2 Run NO Proxy: 25 ms, 56,402,616 t/s
 * 1 Run lambda(static): 24 ms, 58,752,725 t/s
 * 3 Run lambda(this): 26 ms, 54,233,284 t/s
 * 8 Run JDK Proxy: 79 ms, 17,848,929 t/s
 * 7 Run CGLIB Proxy: 51 ms, 27,648,341 t/s
 *10 Run Spring interface Proxy: 231 ms, 6,104,179 t/s
 * 6 Run Spring class Proxy: 31 ms, 45,485,980 t/s
 * 9 Run JAVAASSIST Proxy: 103 ms, 13,689,955 t/s
 * 4 Run JAVAASSIST Bytecode Proxy: 28 ms, 50,359,478 t/s
 * 5 Run ASM Bytecode Proxy: 29 ms, 48,622,945 t/s
 * ----------------
 */
public class ProxyPerformanceTest {


    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
        long time = System.currentTimeMillis();
        CountService service = (CountService) ac.getBean("countServiceImpl");
        time = System.currentTimeMillis() - time;
        System.out.println("Create No Proxy: " + time + " ms " + service.getClass());

        time = System.currentTimeMillis();
        CountService jdkProxy = UtilProxy.createJdkProxy(service, CountService.class);
        time = System.currentTimeMillis() - time;
        System.out.println("Create JDK Proxy: " + time + " ms " + jdkProxy.getClass());

        time = System.currentTimeMillis();
        CountService cglibProxy = UtilProxy.createCglibProxy(service);
        time = System.currentTimeMillis() - time;
        System.out.println("Create CGLIB Proxy: " + time + " ms " + cglibProxy.getClass());

        time = System.currentTimeMillis();
        CountService springInterfaceProxy = (CountService) ac.getBean("countServiceInterface");
        time = System.currentTimeMillis() - time;
        System.out.println("Create Spring interface Proxy: " + time + " ms " + springInterfaceProxy.getClass());

        time = System.currentTimeMillis();
        CountService springClassProxy = (CountService) ac.getBean("countServiceClass");
        time = System.currentTimeMillis() - time;
        System.out.println("Create Spring class Proxy: " + time + " ms " + springClassProxy.getClass());

        time = System.currentTimeMillis();
        CountService javassistProxy = UtilProxy.createJavassistProxy(service);
        time = System.currentTimeMillis() - time;
        System.out.println("Create JAVAASSIST Proxy: " + time + " ms " + javassistProxy.getClass());

        time = System.currentTimeMillis();
        CountService javassistBytecodeProxy = UtilProxy.createJavassistBytecodeProxy(service);
        time = System.currentTimeMillis() - time;
        System.out.println("Create JAVAASSIST Bytecode Proxy: " + time + " ms " + javassistBytecodeProxy.getClass());

        time = System.currentTimeMillis();
        CountService asmBytecodeProxy = UtilProxy.createAsmProxy(service);
        time = System.currentTimeMillis() - time;
        System.out.println("Create ASM Proxy: " + time + " ms " + asmBytecodeProxy.getClass());

        System.out.println("================");

        ProxyPerformanceTest test = new ProxyPerformanceTest();
        for (int i = 0; i < 10; i++) {
            test(service, "Run NO Proxy: ");
            test(ProxyPerformanceTest::count, "Run lambda(static): ");
            test(test::count2, "Run lambda(this): ");
            test(jdkProxy, "Run JDK Proxy: ");
            test(cglibProxy, "Run CGLIB Proxy: ");
            test(springInterfaceProxy, "Run Spring interface Proxy: ");
            test(springClassProxy, "Run Spring class Proxy: ");
            test(javassistProxy, "Run JAVAASSIST Proxy: ");
            test(javassistBytecodeProxy, "Run JAVAASSIST Bytecode Proxy: ");
            test(asmBytecodeProxy, "Run ASM Bytecode Proxy: ");
            System.out.println("----------------");
        }
    }

    private static void test(CountService service, String label) throws Exception {
        service.count(); // warm up
        int count = 1000 * 10000;
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            service.count();
        }
        time = System.currentTimeMillis() - time;
        System.out.println(label + time + " ms, " + new DecimalFormat().format(count * 1000 / time) + " t/s");
    }

    public static void count() {
    }
    public void count2() {
    }
}
