package com.match.servlet;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    @Override
    public void init() {
        message = "Hello World!";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(Thread.currentThread() + " start……");
        try {
            sayHello();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
        System.out.println(Thread.currentThread() + " end……");
    }

    @Override
    public void destroy() {
    }

    private void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread() + " processing……");
        Thread.sleep(3000);
    }
}