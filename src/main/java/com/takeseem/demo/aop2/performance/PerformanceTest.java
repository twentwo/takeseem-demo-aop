package com.takeseem.demo.aop2.performance;

/**
 * Create on 2011-10-12 ����04:28:35 by tengfei.fangtf
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * 
 * All rights reserved.
 */

import com.takeseem.demo.aop2.cglib.CglibAopDemo;
import com.takeseem.demo.aop2.dynamicProxy.DynamicProxyDemo;
import com.takeseem.demo.aop2.staticProxy.ProxyBusiness;

/**
 * TODO please describe PerformanceTest.
 *
 * dynamicProxy need time:13ms
 * staticDynamic need time:1ms
 * byteCodeGe need time:118ms
 * 
 * @author tengfei.fangtf
 */
public class PerformanceTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        DynamicProxyDemo.aop();
        long end = System.currentTimeMillis();
        long dynamicProxyNeedTime = end - start;

        start = System.currentTimeMillis();
        ProxyBusiness.staticDynamic();
        end = System.currentTimeMillis();
        long staticProxyNeedTime = end - start;

        start = System.currentTimeMillis();
        CglibAopDemo.byteCodeGe();
        end = System.currentTimeMillis();
        long byteCodeGeNeedTime = end - start;

        System.out.println("dynamicProxy need time:" + dynamicProxyNeedTime + "ms");
        System.out.println("staticDynamic need time:" + staticProxyNeedTime + "ms");
        System.out.println("byteCodeGe need time:" + byteCodeGeNeedTime + "ms");
    }

}
