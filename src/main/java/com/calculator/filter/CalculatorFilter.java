package com.calculator.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import com.calculator.service.*;
import com.calculator.service.impl.CommandAnalyzerImpl;
import com.calculator.service.impl.SyntaxAnalyzerImpl;


public class CalculatorFilter implements Filter {
    private FilterConfig fc;
    CommandAnalyzer commandAnalyzer = new CommandAnalyzerImpl();
    SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzerImpl();

    public void init(FilterConfig config) throws ServletException {
        this.fc = config;
    }
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain)
        throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;

        String input = httpReq.getParameter("expression");

        if(httpReq.getMethod().equalsIgnoreCase("POST")) {
            try {
                if (commandAnalyzer.isOperation(input)) {
                    syntaxAnalyzer.analyzerOperation(commandAnalyzer.cutOperation(input));
                } else {
                    syntaxAnalyzer.analyzerVar(commandAnalyzer.cutVar(input));
                }
                chain.doFilter(req, resp);
            } catch (Exception e) {
               // e.printStackTrace();
                resp.getWriter().println("Bad syntax!");
            }
        }
        else chain.doFilter(req, resp);

    }
    public void destroy() {

    }
}