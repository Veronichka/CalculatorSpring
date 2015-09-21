package com.calculator.service.impl.Operation;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by Veronichka on 09.07.2015.
 */
public class Operation implements CalcItem  {
    //TODO always do variables as private if you don't want to share them
    private String operator;
    private static ArrayList<String> operatorsList = new ArrayList<String>();

    private static final String operatorPlus = "+";
    private static final String operatorMinus = "-";
    private static final String operatorMultipl = "*";
    private static final String operatorDivid = "/";

    static {
        operatorsList.add(operatorPlus);
        operatorsList.add(operatorMinus);
        operatorsList.add(operatorMultipl);
        operatorsList.add(operatorDivid);
    }

    private Operation(String operator){
        this.operator = operator;
    }

    public static Operation getOperation(String operator) {
        return new Operation(operator);
    }

    public static int getPriority(String s) {
        switch (s) {
            case "(" : return 0;
            case operatorPlus : return 1;
            case operatorMinus : return 1;
            case operatorMultipl : return 2;
            case operatorDivid : return 2;
            default: throw new NoSuchElementException();
        }
    }

    public static boolean isOperator(String s) {

        return (operatorsList.contains(s));
    }

    public String getOperator() {
        return operator;
    }

    public CalcNumber operationExecuting(CalcNumber calcA, CalcNumber calcB) {
        double a = calcA.getValue();
        double b = calcB.getValue();
        switch (operator) {
            case operatorPlus : return new CalcNumber(a + b);
            case operatorMinus : return new CalcNumber(a - b);
            case operatorMultipl : return new CalcNumber(a * b);
            case operatorDivid : return new CalcNumber(a / b);
        }
        throw new NoSuchElementException();
    }
}
