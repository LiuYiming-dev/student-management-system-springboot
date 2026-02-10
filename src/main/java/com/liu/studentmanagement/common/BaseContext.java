package com.liu.studentmanagement.common;

/**
 * 封装 ThreadLocal，用于保存和获取当前登录用户的ID
 */
public class BaseContext {

    // 创建一个 ThreadLocal 容器
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    // 存入当前用户ID
    public static void setCurrentId(Integer id) {
        threadLocal.set(id);
    }

    // 获取当前用户ID
    public static Integer getCurrentId() {
        return threadLocal.get();
    }

    // 🌟 非常重要：移除当前用户ID，防止内存泄漏
    public static void remove() {
        threadLocal.remove();
    }
}