package com.calculator.service.impl;

import com.calculator.service.Calculator;
import com.calculator.service.CalcParser;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.calculator.service.impl.Operation.*;

import java.util.ArrayList;
import java.util.Stack;


@Service
public class CalculatorImpl implements Calculator {

    @Autowired
    private CalcParser parser;

    @Override
    public double calculate(String input) throws Exception{
        return counting(parser.calcParsing(input));
    }

    private double counting(ArrayList<CalcItem> output) {

        Stack<CalcNumber> stack = new Stack<CalcNumber>();

        for(CalcItem action : output) {
            if(action instanceof CalcNumber)
                stack.push((CalcNumber)action);
            else if(action instanceof Operation) {
                CalcNumber calcB = stack.peek();
                stack.pop();
                CalcNumber calcA = stack.peek();
                stack.pop();
                Operation actionOp = (Operation) action;
                stack.push(actionOp.operationExecuting(calcA, calcB));
            }
        }
        return stack.peek().getValue();
    }
}
