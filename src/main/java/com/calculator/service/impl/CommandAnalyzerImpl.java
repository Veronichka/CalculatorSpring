package com.calculator.service.impl;

import com.calculator.service.CommandAnalyzer;
import com.calculator.service.VariableMap;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CommandAnalyzerImpl implements CommandAnalyzer{

    @Autowired
    private VariableMap variableMap;

    @Override
    public boolean isOperation(String input) throws Exception{
        boolean isOperation = false;
        input = input.trim();
        if(input.length() > 3 && input.substring(0,3).equals("var") && input.charAt(input.length()-1) == ';' ) {//handle the command to add variable
            isOperation = false;

            //*** handle the command to calculate the operation
        } else if(input.length() > 5 && input.substring(0,6).equals("print(") && input.charAt(input.length()-2) == ')' && input.charAt(input.length()-1) == ';') {
            isOperation = true;

        } else {

            System.out.println("To initialize the variable use the next format: var a = 1;");
            System.out.println("To calculate the expression use the next format: print(a + 2);");
            //      throw new Exception();
        }
        return isOperation;
    }

    @Override
    public String cutVar(String input) {
        String cutInput;
        input = input.trim();
        cutInput = input.substring(3,input.length()-1); // cut the variable
        System.out.println(cutInput);
        return cutInput;
    }

    @Override
    public String cutOperation(String input) {
        String cutInput;
        input = input.trim();
        cutInput = input.substring(6, input.length() - 2); // cut the operation
        System.out.println(cutInput);
        return cutInput;
    }
}