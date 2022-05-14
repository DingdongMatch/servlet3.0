package com.match.servlet;

import com.match.service.HelloService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

// 容器启动的时候会即将@HandlesTypes指定的这个类型下面的子类（实现类，子接口）传递过来
// 传入感兴趣的类型
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {
    /**
     * 应用启动时，会运行onStartup方法
     * 1）、使用ServletContext注册Web组件（Servlet, Filter, Listener）
     * 2）、使用编码的方式，在项目启动的时候给ServletContext里面添加组件
     *      必须在项目启动的时候来添加
     *      1）、ServletContainerInitializer得到的ServletContext
     *      2）、ServletContextListener得到的ServletContext
     * @param set 感兴趣的类型的所有子类型
     * @param sc 代表当前Web应用的servletContext，一个Web应用一个servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext sc) throws ServletException {
        System.out.println("感兴趣的类型");
        for (Class<?> clazz : set) {
            System.out.println(clazz);
        }

        // 注册组件
        ServletRegistration.Dynamic servlet = sc.addServlet("userServlet", new UserServlet());
        // 配置servlet映射信息
        servlet.addMapping("/user");
        // 注册Listener
        sc.addListener(UserListener.class);
        // 注册Filter
        FilterRegistration.Dynamic filter = sc.addFilter("userFilter", UserFilter.class);
        // 配置filter映射信息
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/* ");
    }
}
