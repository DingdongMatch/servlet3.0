package com.match.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/async", asyncSupported = true)
public class HelloAysncServelet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1、支持异步处理asyncSupported = true
        // 2、开启异步模式
        System.out.println("主线程开始……" + Thread.currentThread() + " ==> " + System.currentTimeMillis());
        AsyncContext startAsync = req.startAsync();
        // 3、业务逻辑进行异步处理，开始异步处理
        startAsync.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("副线程开始……" + Thread.currentThread() + " ==> " + System.currentTimeMillis());
                    sayHello();
                    // 获取到异步的上下文
                    AsyncContext asyncContext = req.getAsyncContext();
                    ServletResponse response = asyncContext.getResponse();
                    response.getWriter().write("hello async……");
                    startAsync.complete();
                    System.out.println("副线程结束……" + Thread.currentThread() + " ==> " + System.currentTimeMillis());
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("主线程结束……" + Thread.currentThread() + " ==> " + System.currentTimeMillis());
    }

    private void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread() + " processing……");
        Thread.sleep(3000);
    }
}
