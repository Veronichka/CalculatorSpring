package com.calculator.service;


public interface SyntaxAnalyzer {
    void analyzerVar(String input) throws Exception;
    void analyzerOperation(String input) throws Exception;
}
