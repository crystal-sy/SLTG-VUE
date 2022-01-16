package com.sltg.quartz.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import com.sltg.common.utils.StringUtils;
import com.sltg.common.utils.spring.SpringUtils;
import com.sltg.quartz.domain.SysJob;

/**
 * 任务执行工具
 *
 * @author styra~
 */
public class JobInvokeUtil {
    /**
     * 执行方法
     *
     * @param sysJob 系统任务
     */
    public static void invokeMethod(SysJob sysJob) throws Exception {
        String invokeTarget = sysJob.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        Object bean;
        Object result;
        if (!isValidClassName(beanName)) {
            bean = SpringUtils.getBean(beanName);
            result = invokeMethod(bean, methodName, methodParams);
        } else {
            bean = Class.forName(beanName).newInstance();
            result =invokeMethod(bean, methodName, methodParams);
        }

        if (result != null) {
            sysJob.setMessage(result.toString());
        }
    }

    /**
     * 调用任务方法
     *
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
     */
    private static Object invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
        throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException {
        Object result;
        if (StringUtils.isNotNull(methodParams) && methodParams.size() > 0) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            result = method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            result = method.invoke(bean);
        }
        return result;
    }

    /**
     * 校验是否为为class包名
     * 
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取bean名称
     * 
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     * 
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 获取method方法参数相关列表
     * 
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr)) {
            return null;
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> methods = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StringUtils.trimToEmpty(methodParam);
            // String字符串类型，包含'
            if (StringUtils.contains(str, "'")) {
                methods.add(new Object[]{StringUtils.replace(str, "'", ""), String.class});
            } else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) { // boolean布尔类型，等于true或者false
                methods.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            } else if (StringUtils.containsIgnoreCase(str, "L")) { // long长整形，包含L
                methods.add(new Object[]{Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class});
            } else if (StringUtils.containsIgnoreCase(str, "D")) { // double浮点类型，包含D
                methods.add(new Object[]{Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class});
            } else { // 其他类型归类为整形
                methods.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return methods;
    }

    /**
     * 获取参数类型
     * 
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] methods = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            methods[index] = (Class<?>) os[1];
            index++;
        }
        return methods;
    }

    /**
     * 获取参数值
     * 
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] methods = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            methods[index] = os[0];
            index++;
        }
        return methods;
    }
}
