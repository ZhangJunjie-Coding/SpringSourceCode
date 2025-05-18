package com.zhang.methodOverrides.replace2_deepseek_answer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("replace2.xml");
        OriginalBean bean = context.getBean("replacedBean", OriginalBean.class);
        
        String result = bean.targetMethod("Hello");
        System.out.println("最终结果: " + result);
    }
}
