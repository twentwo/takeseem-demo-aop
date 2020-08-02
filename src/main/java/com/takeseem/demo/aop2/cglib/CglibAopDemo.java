package com.takeseem.demo.aop2.cglib;

import com.takeseem.demo.aop2.model.Business;
import com.takeseem.demo.aop2.model.IBusiness2;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * �ֽ������ɻ�����ʾAOP
 *
 * @author tengfei.fangtf
 */
public class CglibAopDemo {

    public static void main(String[] args) {
        byteCodeGe();
    }

    public static void byteCodeGe() {
        //创建一个织入器
        Enhancer enhancer = new Enhancer();
        //设置父类
        enhancer.setSuperclass(Business.class);
        //设置需要织入的逻辑
        enhancer.setCallback(new LogIntercept());
        //使用织入器创建子类
        IBusiness2 newBusiness = (IBusiness2) enhancer.create();
        newBusiness.doSomeThing2();
    }

    /**
     * 记录日志
     */
    public static class LogIntercept implements MethodInterceptor {

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            //执行原有逻辑，注意这里是invokeSuper
            Object rev = proxy.invokeSuper(target, args);
            //执行织入的日志
            if (method.getName().equals("doSomeThing2")) {
                System.out.println("记录日志");
            }
            return rev;
        }
    }

}
