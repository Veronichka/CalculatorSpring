package com.calculator.service.impl;

import com.calculator.service.CalcParser;
import com.calculator.service.impl.VariableMapImpl;
import com.calculator.service.impl.SyntaxAnalyzerImpl;
import org.springframework.stereotype.Service;
import com.calculator.service.impl.Operation.*;

import java.util.*;

@Service
public class CalcParserImpl implements CalcParser{

    @Override
    public ArrayList<CalcItem> calcParsing(String s) {

        ArrayList<CalcItem> reversePolishNotation = new ArrayList<CalcItem>(); //the method return this list with reverse polish notation
        Stack<String> stack = new Stack<String>(); //the stack is used to sort the operators

        for(int i = 0; i < s.length(); i ++) { //for each character of the input string

            if (Character.isDigit(s.charAt(i))) { //if the character is a number
                String num = "";
                while (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') {
                    num += s.charAt(i);
                    i++;

                    if (i == s.length()) break;
                }
                i--;
                CalcNumber numObject = new CalcNumber(Double.parseDouble(num));
                reversePolishNotation.add(numObject);

            }

            String el = String.valueOf(s.charAt(i));

            if(SyntaxAnalyzerImpl.isLetter(el)) {
                String variable = "";
                while(SyntaxAnalyzerImpl.isLetter(el)) {
                    variable += el;
                    i ++;

                    if(i == s.length()) {
                        i--;
                        break;
                    }
                    el = String.valueOf(s.charAt(i));
                }
//                System.out.println(variable);
                if(VariableMapImpl.mapVariables.containsKey(variable)) {

                    CalcNumber numVarObject = new CalcNumber(VariableMapImpl.mapVariables.get(variable));
                    reversePolishNotation.add(numVarObject);
                }
                else throw new NoSuchElementException();
            }

            if (el.equals("(")) {
                stack.push(el);
            } else if (el.equals(")")) {
                String o = stack.peek();
                while (!o.equals("(")) {
                    reversePolishNotation.add(Operation.getOperation(stack.peek()));
                    stack.pop();
                    o = stack.peek();
                }
                stack.pop();
            } else if (Operation.isOperator(el)) { //if the character is one of the list: operation
                if (!stack.empty() && Operation.getPriority(stack.peek()) >= Operation.getPriority(el) && Operation.getPriority(stack.peek()) != 0) {
                    reversePolishNotation.add(Operation.getOperation(stack.peek()));
                    stack.pop();
                }
                stack.push(el);
            }
        }

        while (!stack.empty()) {
            reversePolishNotation.add(Operation.getOperation(stack.peek()));
            stack.pop();
        }


        return reversePolishNotation;
    }

}