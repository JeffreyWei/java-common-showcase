package com.wei.samples.classtool;

import org.junit.Test;

import java.util.List;

/**
 * Created by wei on 15/6/3.
 */
public class ClassPathScannerTest {
    @Test
    public void test() {
        List<Class<?>> list = new ClassPathScanner(
                "com.wei.samples",
                new ClassFilter() {
                    public boolean accept(Class<?> clazz) {
                        //return clazz.isInterface(); // 返回接口类
                        return true; // 返回所有类型
                    }
                }
        ).scan();
        for (Class<?> clazz : list) {
            System.out.println(clazz);
        }
    }
}