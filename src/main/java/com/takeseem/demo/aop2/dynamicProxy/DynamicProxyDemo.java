package com.takeseem.demo.aop2.dynamicProxy;

import com.takeseem.demo.aop2.model.Business;
import com.takeseem.demo.aop2.model.IBusiness;
import com.takeseem.demo.aop2.model.IBusiness2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * ʹ�ö�̬����ʵ��AOP��Demo��ʾ
 *
 * @author tengfei.fangtf
 */
public class DynamicProxyDemo {

    public static void main(String[] args) {
        aop();
    }

    public static void aop() {
        //需要代理的接口，被代理类实现的多个接口都必须在这里定义
        Class[] proxyInterface = new Class[]{IBusiness.class, IBusiness2.class};
        //构建AOP的Advice，这里需要传入业务类的实例
        LogInvocationHandler handler = new LogInvocationHandler(new Business());
        //生成代理类的字节码加载器
        ClassLoader classLoader = DynamicProxyDemo.class.getClassLoader();
        //织入器，织入代码并生成代理类
        IBusiness2 proxyBusiness = (IBusiness2) Proxy.newProxyInstance(classLoader, proxyInterface, handler);
        //使用代理类的实例来调用方法。
        proxyBusiness.doSomeThing2();
        ((IBusiness) proxyBusiness).doSomeThing();
    }


    /**
     * 打印日志的切面
     */
    public static class LogInvocationHandler implements InvocationHandler {

        public Object target; //目标对象

        public LogInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //执行原有逻辑
            Object rev = method.invoke(target, args);
            //执行织入的日志，你可以控制哪些方法执行切入逻辑
            if (method.getName().equals("doSomeThing2")) {
                System.out.println("记录日志");
            }
            return rev;
        }
    }

}
