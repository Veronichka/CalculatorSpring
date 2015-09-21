package com.calculator.service;

public interface CommandAnalyzer {
    boolean isOperation(String input)  throws Exception;
    String cutVar(String input);
    String cutOperation(String input);
}