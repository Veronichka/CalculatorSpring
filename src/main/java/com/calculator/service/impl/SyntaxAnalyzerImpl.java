package com.calculator.service.impl;

import com.calculator.service.SyntaxAnalyzer;
import org.springframework.stereotype.Service;
import com.calculator.service.impl.Operation.Operation;

import java.util.regex.*;


@Service
public class SyntaxAnalyzerImpl implements SyntaxAnalyzer {

    private int START = 0;
    private final int NUMBER = 1;
    private final int LETTER = 2;
    private final int DOT = 3;
    private final int BRACKETOPEN = 4;
    private final int BRACKETCLOSE = 5;
    private final int OPERATOR = 6;
    private final int SPACE = 7;
    private final int EQUALITY = 8;
    private final int OTHER = -1;

    @Override
    public void analyzerVar(String input) throws Exception {
        int charFlag = START;
        int brackets = 0;
        boolean equalityState = false;

        if(input.length() == 0) throw new Exception();//the empty command

        for(int i = 0; i < input.length(); i ++) {

            String c = String.valueOf(input.charAt(i));

            switch(state(c)) {
                case NUMBER: {
                    if((charFlag==NUMBER || charFlag==EQUALITY || charFlag==OPERATOR || charFlag==DOT || charFlag==BRACKETOPEN) && equalityState) {
                        charFlag = NUMBER;
                        break;
                    } else throw new Exception();
                }
                case LETTER: {
                    if(charFlag == LETTER || charFlag == START || charFlag == EQUALITY || charFlag == BRACKETOPEN || charFlag == OPERATOR) {
                        charFlag = LETTER;
                        break;
                    } else throw new Exception();
                }
                case EQUALITY: {
                    if(charFlag == LETTER && !equalityState) {
                        charFlag = EQUALITY;
                        equalityState = true;
                        break;
                    } else throw new Exception();
                }
                case BRACKETOPEN: {
                    if(charFlag == START || charFlag == EQUALITY || charFlag == OPERATOR || charFlag == BRACKETOPEN) {
                        charFlag = BRACKETOPEN;
                        brackets ++;
                        break;
                    } else throw new Exception();
                }
                case BRACKETCLOSE: {
                    if(charFlag == NUMBER || charFlag == LETTER || charFlag == BRACKETCLOSE) {
                        charFlag = BRACKETCLOSE;
                        brackets --;
                        break;
                    } else throw new Exception();

                }
                case OPERATOR: {
                    if((charFlag == NUMBER || charFlag == LETTER) && equalityState) {
                        charFlag = OPERATOR;
                        break;
                    }
                }
                case DOT: {
                    if(charFlag == NUMBER && equalityState) {
                        charFlag = DOT;
                        break;
                    }
                }
                case SPACE: break;
                default: throw  new Exception();
            }
        }
        if(brackets != 0) throw new Exception();
    }

    @Override
    public void analyzerOperation(String input) throws Exception{
        int charFlag = START;
        int brackets = 0; //count the brackets

        if(input.length() == 0) throw new Exception(); //empty commant

        for(int i = 0; i < input.length(); i ++) {

            String c = String.valueOf(input.charAt(i));

            switch (state(c)) { //return some state that depend of the current char
                case NUMBER: { //the current char is a number
                    //TODO if(flag <= 5 && flag >=0 )
                    if(charFlag == START || charFlag == NUMBER || charFlag == DOT || charFlag == BRACKETOPEN || charFlag == OPERATOR) { //before number can be nothing or stay a number, a dot, open bracket, close bracket
                        charFlag = NUMBER;
                        break;
                    }
                    else throw new Exception();
                }
                case LETTER: {
                    if(charFlag == START || charFlag == BRACKETOPEN || charFlag == OPERATOR || charFlag == LETTER) { //before number can be nothing or stay a number, a dot, open bracket, close bracket
                        charFlag = LETTER;
                        break;
                    }
                    else throw new Exception();
                }
                case DOT : { //the current char is a dot
                    if(charFlag == NUMBER) { //before a dot can stay a number
                        charFlag = DOT;
                        break;
                    }
                    else throw new Exception();
                }
                case BRACKETOPEN : { //the current char is a open bracket
                    if(charFlag == OPERATOR || charFlag == BRACKETOPEN || charFlag == START) { //before the open bracket can stay a operator, a open bracket und it can be the beginning
                        charFlag = BRACKETOPEN;
                        brackets++; //increase the bracket counter
                        break;
                    }
                    else throw new Exception();
                }
                case BRACKETCLOSE : { //the current char is a close bracket
                    if(charFlag == NUMBER || charFlag == BRACKETCLOSE || charFlag == LETTER) { //before the close bracket can stay a number or the close bracket
                        charFlag = BRACKETCLOSE;
                        brackets--; //decrease the bracket counter
                        break;
                    }
                    else throw new Exception();
                }
                case OPERATOR : { //the current char is the legal operator
                    if(charFlag == NUMBER || charFlag == BRACKETCLOSE || charFlag == LETTER) { //before the operator can stay a number or a close bracket
                        charFlag = OPERATOR;
                        break;
                    }
                    else throw new Exception();
                }
                case SPACE : break; //the current char is a space - do nothing

                case OTHER: throw new Exception(); //the current char is something else
            }
        }

        if(brackets != 0) throw new Exception();
    }


    public int state(String c) {

        String pattern1 = "[0-9]";
        Pattern p1 = Pattern.compile(pattern1);

        if(p1.matcher(c).find()) return NUMBER;
        else if(SyntaxAnalyzerImpl.isLetter(c)) return LETTER;
        else if(c.equals(".")) return DOT;
        else if(c.equals("(")) return BRACKETOPEN;
        else if(c.equals(")")) return BRACKETCLOSE;
        else if(Operation.isOperator(c)) return OPERATOR;
        else if(c.equals(" ")) return SPACE;
        else if(c.equals("=")) return EQUALITY;
        else return OTHER;
    }


    public static boolean isLetter(String c) {
        String patternA = "[a-z]";
        Pattern pA = Pattern.compile(patternA);
        return pA.matcher(c).find();
    }
}
