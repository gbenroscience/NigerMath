/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.commandline;

import com.github.gbenroscience.parser.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import com.github.gbenroscience.math.differentialcalculus.Derivative;
import math.graph.gui.FunctionTracer;
import com.github.gbenroscience.math.matrix.expressParser.Matrix;
import com.github.gbenroscience.math.matrix.equationParser.LinearSystemParser;
import com.github.gbenroscience.math.numericalmethods.NumericalIntegral;
import com.github.gbenroscience.math.numericalmethods.RootFinder;
import com.github.gbenroscience.math.quadratic.Quadratic_Equation;
import com.github.gbenroscience.math.tartaglia.Tartaglia_Equation;
import javax.swing.SwingUtilities;
import splashyflashy.FinalSplash;
import utils.MathExpressionManager;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class CommandParser {

    private FunctionTracer tracer;
    private String commands;
    ArrayList<String> results = new ArrayList<String>();
    private static final String endOfLine = ";";
    private ArrayList<State> states = new ArrayList<State>();//records all the types of the commands entered
    private ArrayList<String> runnableCode = new ArrayList<String>();//records all the codes pertaining to each command in ArrayList, 'state'

    public CommandParser(String commands) {
        //commands=STRING.purifier(commands);
        this.commands = commands;
        getCommands();
    }

    /**
     * Scans a set of instructions homogeneous to a particular state.
     *
     * @param runnableCode The code.
     * @return an ArrayList containing the lines of instruction contained in the
     * code.
     */
    private ArrayList<String> scanIntoLines(String runnableCode) {
        ArrayList<String> instructions = new ArrayList<String>();

        String lines[] = runnableCode.split(endOfLine);
        try {
            for (int i = 0; i < lines.length; i++) {
                if (!lines[i].trim().isEmpty()) {
                    instructions.add(lines[i]);
                }
            }//end for
        }//end try
        catch (IndexOutOfBoundsException indexErr) {
            JOptionPane.showMessageDialog(null, "Attempting To Run Bad Command");
        }//end try
        catch (NullPointerException nolian) {
            JOptionPane.showMessageDialog(null, "Attempting To Run Bad Command");
        }//end try
        return instructions;
    }

    /**
     * get a list of all command names( stored as State objects in
     * ArrayList<State> state) and the code under each one stored in
     * ArrayList<String>runnableCode.
     *
     * e.g if the user typed...
     *
     * SIMULTANEOUS_EQUATIONS{ 2X+3Y=-9: Y-7X=222: ; }
     *
     * QUADRATIC_EQUATIONS{ 3x^2-3x+9=-22; x+x^2-9=22x; }
     *
     * PLOT{ y=3cos(9x-7); [2,3,-9:][4,8,12:]; } At the end of this stage, the
     * output would be: states=[SIMULTANEOUS_EQUATIONS,QUADRATIC_EQUATIONS,PLOT]
     * runnableCode=[
     *
     * 2X+3Y=-9: Y-7X=222: ;,
     *
     *
     * 3x^2-3x+9=-22; x+x^2-9=22x;
     *
     *
     * y=3cos(9x-7); [2,3,-9:][4,8,12:];
     *
     * ]
     */
    private void getCommands() {
        int I = 0;
        try {

            String myCommand = STRING.removeNewLineChar(commands);
            myCommand = myCommand.replace("}", "}~");//introduce ~ to have a splitting point that is not needed.
            String commandArray[] = myCommand.split("~");//isolates all individual command systems i.e splits the whole
            //command into a number of systems.

            for (int i = 0; i < commandArray.length; i++) {
                String cmd = commandArray[i];
                int openingbraceindex = cmd.indexOf("{");
                int closingbraceindex = cmd.indexOf("}");
                if (openingbraceindex == -1 || closingbraceindex == -1) {
                    break;
                }
                states.add(State.valueOf(cmd.substring(0, openingbraceindex))); // record all command types.
                runnableCode.add(cmd.substring(openingbraceindex + 1, closingbraceindex));//read the runnable command under each command type.
                I = i;
            }//end for loop

        }//end try
        catch (IllegalArgumentException illegality) {
            JOptionPane.showMessageDialog(null, " Unknown Command Issued." + states.get(I));
        } catch (IndexOutOfBoundsException indexErr) {
            indexErr.printStackTrace();
            JOptionPane.showMessageDialog(null, " index Command Syntax Error ");
        }//end try
        catch (NullPointerException nolian) {
            JOptionPane.showMessageDialog(null, " null Command Syntax Error ");
        }//end catch

    }

    /**
     * Handles normal calculator functions.
     *
     * @param runnableCode The code.
     */
    public void handleNormalCalculations(String runnableCode) {
        try {
            MathExpressionManager func = FinalSplash.getSplash().getCalc().getFuncMan();
            ArrayList<String> scan = scanIntoLines(runnableCode);

            for (int i = 0; i < scan.size(); i++) {
                results.add(func.solve(scan.get(i) + ";"));//populate the results store with the result of the execution of each line.
                results.add("________________________________");
            }
            JTable[] allTables = FinalSplash.getSplash().getCalc().allTables();
            func.getVariableManager().writeVariablesToTable(allTables[0], allTables[1]);

        }//end try
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles quadratic equations
     *
     * @param runnableCode The code.
     */
    public void handleQuadraticEquations(String runnableCode) {
        ArrayList<String> scan = scanIntoLines(runnableCode);
        for (int i = 0; i < scan.size(); i++) {

            Quadratic_Equation solver = new Quadratic_Equation(scan.get(i));

            String reducedForm = solver.interpretedSystem();
            String soln = solver.solutions();
            results.add("ARITHMETIC REDUCTION GIVES = \n" + reducedForm);

            String vars = solver.getParser().getUnknown();

            results.add(vars + " = ");
            results.add(soln);//populate the results store with the result of the execution of each line.
            results.add("________________________________");
        }
    }//end method

    /**
     * Handles Tartaglia's equations
     *
     * @param runnableCode The code.
     */
    public void handleTartagliaEquations(String runnableCode) {
        ArrayList<String> scan = scanIntoLines(runnableCode);
        for (int i = 0; i < scan.size(); i++) {

            Tartaglia_Equation solver = new Tartaglia_Equation(scan.get(i));

            String reducedForm = solver.interpretedSystem();
            String soln = solver.solutions();
            results.add("ARITHMETIC REDUCTION GIVES = \n" + reducedForm);

            String vars = solver.getParser().getUnknown();

            results.add(vars + " = ");
            results.add(soln);//populate the results store with the result of the execution of each line.
            results.add("________________________________");
        }
    }

    /**
     * Handles simultaneous equations
     *
     * @param runnableCode The code.
     */
    public void handleSimultaneousEquations(String runnableCode) {
        ArrayList<String> scan = scanIntoLines(runnableCode);
        for (int i = 0; i < scan.size(); i++) {

            LinearSystemParser parser = new LinearSystemParser(scan.get(i));
            Matrix solution = parser.getEquationMatrix();
            String vars = parser.getUnknowns().toString();
            String reducedForm = parser.interpretedSystem();
            results.add("ARITHMETIC REDUCTION GIVES = \n" + reducedForm);
            results.add("________________________________");
            results.add("VALUES OF UNKNOWNS " + vars + " IN ORDER ARE: ");
            results.add(solution.solveEquation().toString());//populate the results store with the result of the execution of each line.
            results.add("________________________________");
        }
    }

    public void handleIntegralCommand(String runnableCode) {
        ArrayList<String> scan = scanIntoLines(runnableCode);
        for (int i = 0; i < scan.size(); i++) {
            try {

                NumericalIntegral integrator = new NumericalIntegral(scan.get(i), NumericalIntegral.FUNCTIONAL_INTEGRATION);

                String solution = String.valueOf(integrator.findHighRangeIntegral());
                results.add("________________________________");
                results.add("For xLower = " + integrator.getxLower() + ", xUpper = " + integrator.getxUpper() + ":");
                results.add("∫(" + integrator.getFunction() + ")d" + integrator.getVariable() + " =  ");
                results.add("   " + solution);
                results.add("________________________________");

            }//end try
            catch (InputMismatchException inputError) {
                results.add("SYNTAX ERROR\n"
                        + "PLEASE CONSULT THE HELP FILE FOR VALID SYNTAX NEAR \'NUMERICAL_INTEGRATION\'");
            }//end catch

        }//end for

    }//end method

    public void handleDifferentiateCommand(String runnableCode) {
        ArrayList<String> scan = scanIntoLines(runnableCode);
        for (int i = 0; i < scan.size(); i++) {
            try {

                /*NumericalDerivative diff = new NumericalDerivative( scan.get(i) );
diff(@(x)2*x+1,4)
   String solution= diff.findDerivativeByPolynomialExpander();*/
                String solution = Derivative.eval(scan.get(i));

                results.add("________________________________");
                results.add(scan.get(i) + " = ");
                results.add("   " + solution);
                results.add("________________________________");

            }//end try
            catch (InputMismatchException inputError) {
                results.add("SYNTAX ERROR\n"
                        + "PLEASE CONSULT THE HELP FILE FOR VALID SYNTAX NEAR \'NUMERICAL_DIFFERENTIATION\'");
            }//end catch

        }//end for

    }//end method

    public void handleRootOfAnEquation(String runnableCode) {
        ArrayList<String> scan = scanIntoLines(runnableCode);
        for (int i = 0; i < scan.size(); i++) {
            try {

                RootFinder rootFinder = new RootFinder(scan.get(i) + ";");

                String solution = rootFinder.findRoots();

                results.add("________________________________");
                results.add("   " + solution);
                results.add("________________________________");

            }//end try
            catch (InputMismatchException inputError) {
                results.add("SYNTAX ERROR\n"
                        + "PLEASE CONSULT THE HELP FILE FOR VALID SYNTAX NEAR \'ROOT_OF_EQUATION\'");
            }//end catch
            catch (NullPointerException nullError) {
                results.add("NO SOLUTION FOUND.");
            }//end catch

        }//end for

    }

    public void handlePlotCommand(String runnableCode) {
        if (tracer == null) {
            tracer = new FunctionTracer(runnableCode);
            tracer.loadFunctionsOntoTables();
        } else {
            tracer.getPaper().getGrid().setFunction(runnableCode);
            tracer.loadFunctionsOntoTables();
        }
        if (!tracer.getPaper().getGrid().getGridExpressionParser().isCanPlot()) {
            results.add("PLOTTER SYNTAX ERROR. CONSULT HELP"
                    + "FOR VALID SYNTAX  TO USE NEAR \"PLOT{\n\n"
                    + "}\"");
        }

    }

    public void runCommand() {
        try {
            for (int i = 0; i < states.size(); i++) {

                if (states.get(i).isNormalCalc()) {
                    handleNormalCalculations(runnableCode.get(i));
                } else if (states.get(i).isSimultaneousEquation()) {
                    handleSimultaneousEquations(runnableCode.get(i));
                } else if (states.get(i).isQuadraticEquation()) {
                    handleQuadraticEquations(runnableCode.get(i));
                } else if (states.get(i).isTartagliaEquation()) {
                    handleTartagliaEquations(runnableCode.get(i));
                } else if (states.get(i).isPlot()) {
                    handlePlotCommand(runnableCode.get(i));
                } else if (states.get(i).isNumericalIntegration()) {
                    handleIntegralCommand(runnableCode.get(i));
                } else if (states.get(i).isNumericalDifferentiation()) {
                    handleDifferentiateCommand(runnableCode.get(i));
                } else if (states.get(i).isRoot()) {
                    handleRootOfAnEquation(runnableCode.get(i));
                }

            }
        }//end try
        catch (IllegalArgumentException exception) {

        }//end catch

    }

    public void publishResults(final JTextArea area) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SwingUtilities.invokeLater(() -> {
                        runCommand();
                        StringBuilder sb = new StringBuilder();
                        for (String res : results) {
                            sb.append(res).append("\n");
                        }
                        final String finalOutput = sb.toString();
                         area.append(finalOutput);
                    }); 

                } catch (Exception e) {
                    // SEND THE ERROR TO THE UI THREAD
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            area.append("SYNTAX ERROR\n");
                        }
                    });
                }
            }
        });
        t.start(); // Normal priority is usually better
    }

}//end class
