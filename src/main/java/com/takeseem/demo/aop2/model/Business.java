package com.takeseem.demo.aop2.model;

/**
 * ҵ���߼���
 */
public class Business implements IBusiness, IBusiness2 {

    @Override
    public boolean doSomeThing() {
        System.out.println("执行业务逻辑");
        return true;
    }

    @Override
    public void doSomeThing2() {
        System.out.println("执行业务逻辑2");
    }

    public static void main(String[] args) {
        Business h = new Business();
        h.doSomeThing2();
        h.doSomeThing();
    }

}
