/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils.commandline;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public enum State {

    CALCULATOR(CommandParserOutputState.TEXT),
    SIMULTANEOUS_EQUATIONS(CommandParserOutputState.TEXT),
    QUADRATIC_EQUATIONS(CommandParserOutputState.TEXT),
    PLOT(CommandParserOutputState.DISPLAY),
    DIFFERENTIAL_EQUATIONS(CommandParserOutputState.TEXT),
    TARTAGLIA_EQUATIONS(CommandParserOutputState.TEXT),
    STATISTICS(CommandParserOutputState.TEXT),
    ROOT_OF_EQUATION(CommandParserOutputState.TEXT),
    NUMERICAL_INTEGRATION(CommandParserOutputState.TEXT),
    NUMERICAL_DIFFERENTIATION(CommandParserOutputState.TEXT),
    MATRIX_MULTIPLICATION(CommandParserOutputState.TEXT),
    MATRIX_ADDITION(CommandParserOutputState.TEXT),
    MATRIX_SUBTRACTION(CommandParserOutputState.TEXT),
    DETERMINANT(CommandParserOutputState.TEXT),
    TRIANGULAR_MATRIX(CommandParserOutputState.TEXT),
    BASE(CommandParserOutputState.ACTION),//NEXT VALUE MUST BE THE BASE SYSTEM
    SAVE_DOCUMENT(CommandParserOutputState.ACTION),
    OPEN_DOCUMENT(CommandParserOutputState.ACTION),
    EXIT(CommandParserOutputState.ACTION),
    FORCE_EXIT(CommandParserOutputState.ACTION),
    CLEAR_INPUT(CommandParserOutputState.ACTION),
    CLEAR_OUTPUT(CommandParserOutputState.ACTION),
    SET_COLOR(CommandParserOutputState.ACTION),
    STORE_VARIABLE(CommandParserOutputState.ACTION),
    STORE_CONSTANT(CommandParserOutputState.ACTION),
    MAKE_FORMULA(CommandParserOutputState.ACTION),
    STORE_FORMULA(CommandParserOutputState.ACTION),
    TIMEQUERY(CommandParserOutputState.ACTION);

    private final CommandParserOutputState state;

    State(CommandParserOutputState state) {
        this.state = state;
    }




public boolean isNormalCalc(){
    return this==CALCULATOR;
}
    public boolean isSimultaneousEquation(){
        return this==SIMULTANEOUS_EQUATIONS;
    }

    public boolean isQuadraticEquation(){
        return this==QUADRATIC_EQUATIONS;
    }

    public boolean isPlot(){
        return this==PLOT;
    }

    public boolean isDifferentialEquation(){
        return this==DIFFERENTIAL_EQUATIONS;
    }

    public boolean isTartagliaEquation(){
        return this==TARTAGLIA_EQUATIONS;
    }
    public boolean isStatistics(){
        return this==STATISTICS;
    }
    public boolean isRoot(){
        return this==ROOT_OF_EQUATION;
    }
    public boolean isNumericalIntegration(){
        return this==NUMERICAL_INTEGRATION;
    }
    public boolean isNumericalDifferentiation(){
        return this==NUMERICAL_DIFFERENTIATION;
    }
    public boolean isMatrixMultiplication(){
        return this==MATRIX_MULTIPLICATION;
    }
    public boolean isMatrixAddition(){
        return this==MATRIX_ADDITION;
    }
    public boolean isMatrixSubtraction(){
        return this==MATRIX_SUBTRACTION;
    }
    public boolean isDeterminant(){
        return this==DETERMINANT;
    }
    public boolean isTriangularMatrix(){
        return this==TRIANGULAR_MATRIX;
    }

public boolean isBase(){
    return this==BASE;
}

public boolean isSaveDocument(){
    return this==SAVE_DOCUMENT;
}

public boolean isOpenDocument(){
    return this==OPEN_DOCUMENT;
}

public boolean isExit(){
    return this==EXIT;
}

public boolean isForceExit(){
    return this==FORCE_EXIT;
}


public boolean isSetColor(){
    return this==SET_COLOR;
}

public boolean isStoreVariable(){
    return this==STORE_VARIABLE;
}

public boolean isStoreConstant(){
    return this==STORE_CONSTANT;
}

public boolean isMakeFormula(){
    return this==MAKE_FORMULA;
}

public boolean isStoreFormula(){
    return this==STORE_FORMULA;
}

public boolean isTimeQuery(){
    return this==TIMEQUERY;
}

    public CommandParserOutputState getState() {
        return state;
    }













    

}//end enum
