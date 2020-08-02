/**
 * Create on 2011-10-12 ����02:03:12 by tengfei.fangtf
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * 
 * All rights reserved.
 */
package com.takeseem.demo.aop2.staticProxy;

import com.takeseem.demo.aop2.dynamicProxy.DynamicProxyDemo;
import com.takeseem.demo.aop2.model.Business;
import com.takeseem.demo.aop2.model.IBusiness;
import com.takeseem.demo.aop2.model.IBusiness2;

import java.lang.reflect.Method;


/**
 * ֯�������ɵĴ�����
 * 
 * @author tengfei.fangtf
 */
public class ProxyBusiness implements IBusiness, IBusiness2 {

    private DynamicProxyDemo.LogInvocationHandler h;

    @Override
    public void doSomeThing2() {
        try {
            Method m = (h.target).getClass().getMethod("doSomeThing", null);
            h.invoke(this, m, null);
        } catch (Throwable e) {
            // �쳣���� 1���ԣ�
        }
    }

    @Override
    public boolean doSomeThing() {
        try {
            Method m = (h.target).getClass().getMethod("doSomeThing2", null);
            return (Boolean) h.invoke(this, m, null);
        } catch (Throwable e) {
            // �쳣���� 1���ԣ�
        }
        return false;
    }

    public ProxyBusiness(DynamicProxyDemo.LogInvocationHandler h) {
        this.h = h;
    }

    //������
    public static void main(String[] args) {
        //����AOP��Advice
        staticDynamic();
    }

    public static void staticDynamic() {
        DynamicProxyDemo.LogInvocationHandler handler = new DynamicProxyDemo.LogInvocationHandler(new Business());
        new ProxyBusiness(handler).doSomeThing();
        new ProxyBusiness(handler).doSomeThing2();
    }

}
