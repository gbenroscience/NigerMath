/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.github.gbenroscience.parser.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JIBOYE OLUWAGBEMIRO OLAOLUWA
 */
public class FunctionManager {

    public static final Map<String, Function> FUNCTIONS = Collections.synchronizedMap(new HashMap<String, Function>());

    /**
     *
     * @param fName The name of the dependent variable of the function or the
     * full name of the function which is a combination of the name of its
     * dependent variable and its independent variables enclosed in circular
     * parentheses. e.g in y = x^3, either y or y(x) may be supplied.
     * @return true if a Function exists by the name supplied.
     */
    public static boolean contains(String fName) {
        return lookUp(fName) != null;
    }//end method

    /**
     *
     * @param fName The name of the dependent variable of the function.
     * @return the Function object that has the name supplied if it exists. If
     * no such Function object exists, then it returns null.
     */
    public static Function getFunction(String fName) {
        return FUNCTIONS.get(fName);
    }//end method

    /**
     * Attempts to retrieve a Function object from a FunctionManager based on
     * its name.
     *
     * @param functionName The name of the Function object.
     * @return the Function object that has that name or null if the Function is
     * not found.
     */
    public static Function lookUp(String functionName) {
        return FUNCTIONS.get(functionName);
    }//end method

    /**
     * Adds a Function object to this FunctionManager.
     *
     * @param expression The expression that creates the Function to add. The
     * form is:F=@(x,y,z,...)mathexpr. e.g y=@(x)3x-x^2; Functions take
     * precedence over variables.. so if a function called sin_func is created
     * and there exists a variable with that name, the system discards that
     * variable
     */
    public static void add(String expression) {
        add(new Function(expression));
    }//end method

    /**
     *
     * @param f The Function object to add to this object.
     */
    public static void add(Function f) {
        String fName = f.getName();

        Function oldFunc = FUNCTIONS.get(fName);
        if (oldFunc == null) {//function does not exist in registry
            Variable v = VariableManager.lookUp(fName);//check if a Variable has this name in the Variables registry
            if (v != null) {
                VariableManager.delete(fName);//if so delete it.
            }//end if
            FUNCTIONS.put(fName, f);
        } else {
            update(f.toString());
        }

    }//end method

    /**
     * Loads the {@link Function} objects in this {@link Map} into the
     * {@link FunctionManager#FUNCTIONS} {@link Map}.
     *
     * @param functions A {@link Map} of {@link Function} objects.
     */
    public static void load(Map<String, Function> functions) {
        load(functions, false);
    }

    /**
     * Loads the {@link Function} objects in this {@link Map} into the
     * {@link FunctionManager#FUNCTIONS} {@link Map}.
     *
     * @param functions A {@link Map} of {@link Function} objects.
     * @param clearFirst If true, then the {@link FunctionManager#FUNCTIONS} is
     * cleared first before new content is loaded into it.
     */
    public static void load(Map<String, Function> functions, boolean clearFirst) {
        synchronized (FUNCTIONS) {
            if (clearFirst) {
                FUNCTIONS.clear();
            }

            FUNCTIONS.putAll(functions);

        }
    }

    /**
     * Removes a Function object from this FunctionManager.
     *
     * @param fName The name of the {@link Variable}
     */
    public static void delete(String fName) {
        FUNCTIONS.remove(fName);
        update();
    }//end method

    /**
     * Updates a Function object in this FunctionManager.
     */
    public static void update(String expression) {
        try {
            Function f = new Function(expression);
            String name = f.getName();
            FUNCTIONS.put(name, f);
        } catch (Exception ex) {
            Logger.getLogger(FunctionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        update();
    }//end method

    /**
     * Deletes all anonymous functions
     */
    public static void clearAnonymousFunctions() {
        List<String> anonKeys = new ArrayList<>();
        java.util.Set<Map.Entry<String, Function>> entrySet = FUNCTIONS.entrySet();
        synchronized (entrySet) {
            for (Map.Entry<String, Function> entry : entrySet) {
                Function function = entry.getValue();
                if (function.isAnonymous()) {
                    anonKeys.add(entry.getKey());
                }
            }//end for
            FUNCTIONS.keySet().removeAll(anonKeys);
        }
    }

    /**
     * Passes changes to the FUNCTIONS list to the UI(interface)
     */
    public static void update() {

    }

    /**
     *
     * @return the number of anonymous functions in the FunctionManager.
     */
    public static int countAnonymousFunctions() {
        int count = 0;
        synchronized (FUNCTIONS) {
            for (Map.Entry<String, Function> entry : FUNCTIONS.entrySet()) {
                Function function = entry.getValue();
                if (function.getName().startsWith("anon")) {
                    ++count;
                }//end if
            }//end for
        }
        return count;
    }

    /**
     *
     * @return An {@link ArrayList} of all non-anonymous functions.
     */
    public static final ArrayList<Function> getDefinedFunctions() {
        ArrayList<Function> functions = new ArrayList<>();

        synchronized (FUNCTIONS) {
            for (Map.Entry<String, Function> entry : FUNCTIONS.entrySet()) {
                Function function = entry.getValue();
                if (function != null && !function.isAnonymous()) {
                    functions.add(function);
                }
            }//end for
        }
        return functions;
    }

    /**
     * displays the Variables managed by objects of this class on a table
     *
     * @param functionsTable the table that displays the functions
     */
    public static void writeFunctionsToTable1(JTable functionsTable) {
        /**
         * Write to variables table
         */
        clearTableData(functionsTable);

        for (int j = 0; j < functionsTable.getColumnCount(); j++) {
            synchronized (FUNCTIONS) {
                java.util.Set<Map.Entry<String, Function>> entries = FUNCTIONS.entrySet();
                int i = 0;
                for (Map.Entry<String, Function> entry : entries) {
                    Function f = entry.getValue();
                    if (i >= functionsTable.getRowCount()) {
                        try {
                            Vector<Object> obj = new Vector<Object>();
                            DefaultTableModel mod = (DefaultTableModel) functionsTable.getModel();
                            mod.addRow(obj);
                            functionsTable.setModel(mod);
                        } catch (NullPointerException nolian) {

                        } catch (IndexOutOfBoundsException indErr) {

                        }
                    }//end if

                    if (j == 0) {
                        functionsTable.setValueAt(i, i, j);
                    } else if (j == 1) {
                        functionsTable.setValueAt(f.getName(), i, j);
                    } else if (j == 2) {
                        functionsTable.setValueAt(f.expressionForm(), i, j);
                    }
                    i++;
                }//end inner for
            }
        }//end outer for

    }//end method writeFunctionsToTable
        /**
     * CAREFUL!!! This method deletes all entries from a JTable
     *
     * @param table the JTable to be manipulated
     */
    public static void clearTableData1(JTable table) {
        DefaultTableModel mod = (DefaultTableModel) table.getModel();
        int size = mod.getDataVector().size();
        int i = 0;
        while (i < size) {
            mod.setValueAt("", i, 0);
            mod.setValueAt("", i, 1);
            mod.setValueAt("", i, 2);
            i++;
        }//end while
    }
public static void writeFunctionsToTable(final JTable functionsTable) {
    // 1. Get the data first (Synchronized to prevent ConcurrentModificationException)
    final List<Object[]> data = new ArrayList<>();
    synchronized (FUNCTIONS) {
        int i = 0;
        for (Map.Entry<String, Function> entry : FUNCTIONS.entrySet()) {
            Function f = entry.getValue();
            // Prepare a row of data: [Index, Name, Expression]
            data.add(new Object[]{ i, f.getName(), f.expressionForm() });
            i++;
        }
    }

    // 2. Push the update to the UI Thread
    SwingUtilities.invokeLater(() -> {
        DefaultTableModel model = (DefaultTableModel) functionsTable.getModel();
        model.setRowCount(0); // Clear the table in one go

        for (Object[] row : data) {
            model.addRow(row); // Add the full data row
        }
        
        // No need to call setModel(model) again; the model is already linked!
    });
}

public static void clearTableData(JTable table) {
    final DefaultTableModel mod = (DefaultTableModel) table.getModel();
    
    // Ensure this happens on the Event Dispatch Thread
    if (SwingUtilities.isEventDispatchThread()) {
        mod.setRowCount(0); 
    } else {
        SwingUtilities.invokeLater(() -> mod.setRowCount(0));
    }
}
    /**
     * This method deletes a single entry from a JTable
     *
     * @param table the JTable to be manipulated
     * @param row the row whose entries are to be deleted
     */
    public static void removeEntry(JTable table, int row) {
        DefaultTableModel mod = (DefaultTableModel) table.getModel();
        mod.setValueAt("", row, 0);
        mod.setValueAt("", row, 1);
        mod.setValueAt("", row, 2);

    }

    /**
     * CAREFUL!!! This method deletes all rows from a JTable
     *
     * @param table the JTable to be manipulated
     */
    public static void clearTableRows(JTable table) {
        DefaultTableModel mod = (DefaultTableModel) table.getModel();
        int size = mod.getDataVector().size();
        while (size > 0) {
            mod.removeRow(size - 1);
            --size;
        }
    }

    /**
     * This method deletes a single row from a JTable
     *
     * @param table the JTable to be manipulated
     * @param row the row whose entries are to be deleted
     */
    public static void removeRow(JTable table, int row) {
        DefaultTableModel mod = (DefaultTableModel) table.getModel();
        mod.removeRow(row);
    }

    @Override
    public String toString() {
        return FUNCTIONS.toString();
    }//end method

}//end class
