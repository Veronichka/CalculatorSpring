package com.calculator.mvc;

import com.calculator.service.Calculator;
import com.calculator.service.CommandAnalyzer;
import com.calculator.service.VariableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class CalculatorController {

    @Autowired
    private Calculator calculator;

    @Autowired
    private CommandAnalyzer commandAnalyzer;

    @Autowired
    private VariableMap variableMap;

    private final static String HOME_VIEW = "index";

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return HOME_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String calculateExpression(@RequestParam("expression") String expression, ModelMap model){
//        syntaxAnalyzer.analyzeSyntax(expression);
//        model.addAttribute("result", calculator.calculate(syntaxParser.parse(expression)));

        String result;

        try {
            if(commandAnalyzer.isOperation(expression)) {
                result = "" + calculator.calculate(commandAnalyzer.cutOperation(expression));
                model.addAttribute("result", result);
            } else {
                variableMap.variableToMap(commandAnalyzer.cutVar(expression));
                model.addAttribute("result", "Variable is initialized and saved!");
            }
            //  inputReader.setInput(req.getParameter("expression"));


        } catch(Exception e) {
            model.addAttribute("result", "Bad syntax! Try again:");
        }

        return HOME_VIEW;
    }
}
