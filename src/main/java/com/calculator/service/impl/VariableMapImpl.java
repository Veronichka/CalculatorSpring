package com.calculator.service.impl;

import com.calculator.service.VariableMap;
import com.calculator.service.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VariableMapImpl implements VariableMap {

    static final Map<String, Double> mapVariables = new HashMap<>();

    @Autowired
    private Calculator calculator;

    @Override
    public void variableToMap (String cutInput) throws Exception {
        String variable = "";
        String value = "";
        int i = 0;
        String c = String.valueOf(cutInput.charAt(i));

        while(!c.equals("=") && i < cutInput.length()) { // read the variable
            if (!c.equals(" ")) {
                variable += c;
            }
            i++;
            c = String.valueOf(cutInput.charAt(i));
        }

        while(i < cutInput.length()){ // read the value of the variable
            c = String.valueOf(cutInput.charAt(i));
            if (!c.equals(" ") && !c.equals("=")) {
                value += c;
//                System.out.println(value);
            }
            i++;

        }

        if(variable.isEmpty() || value.isEmpty()) { // check if the variable und value were got
            throw new Exception();
        }
        Double valueDouble = calculator.calculate(value); // calculate the variable's value. For example: var a = 1+2;
        VariableMapImpl.mapVariables.put(variable,valueDouble); // add the variable to HashMap
    }

}
