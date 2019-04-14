package com.takeseem.demo.aop;

import java.text.DecimalFormat;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.takeseem.demo.aop.service.CountService;

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
