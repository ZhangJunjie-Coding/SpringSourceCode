package com.zhang.methodOverrides.replace2_deepseek_answer;

import org.springframework.beans.factory.support.MethodReplacer;
import java.lang.reflect.Method;

public class CustomMethodReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        // 1. 获取原始类（父类）的方法
        Class<?> superClass = obj.getClass().getSuperclass();
        Method originalMethod = superClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
        
        // 2. 调用原始方法
        Object result = originalMethod.invoke(obj, args);
        
        // 3. 添加自定义逻辑
        System.out.println("原方法返回值: " + result);
        
        // 4. 返回修改后的结果（或原结果）
        return "替换后的结果: " + result;
    }
}