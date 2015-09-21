package com.calculator.service;

import com.calculator.service.impl.Operation.CalcItem;
import java.util.*;

public interface CalcParser {
    ArrayList<CalcItem> calcParsing(String s);
}