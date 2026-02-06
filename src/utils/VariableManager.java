/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel; 
import com.github.gbenroscience.parser.Variable;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class VariableManager extends com.github.gbenroscience.util.VariableManager{
 

    /**
     * displays the Variables managed by objects of this class on a table
     *
     * @param varTable the table that displays the Variables
     * @param constTable the table that displays the constants
     */
    public void writeVariablesToTable(JTable varTable, JTable constTable) {
        /**
         * Write to variables table
         */
        clearTableData(varTable);

//separate variables and constants
        ArrayList<Variable> vars = new ArrayList<Variable>();
        ArrayList<Variable> consts = new ArrayList<Variable>();

        Collection<Variable> allVars = VARIABLES.values();
        synchronized (VARIABLES) {
            for (Variable v : allVars) {
                if (v.isConstant()) {
                    consts.add(v);
                } else {
                    vars.add(v);
                }
            }
        }

        for (int j = 0; j < varTable.getColumnCount(); j++) {
            for (int i = 0; i < vars.size(); i++) {
                if (i >= varTable.getRowCount()) {
                    try {
                        Vector<Object> obj = new Vector<Object>();
                        DefaultTableModel mod = (DefaultTableModel) varTable.getModel();
                        mod.addRow(obj);
                        varTable.setModel(mod);
                    } catch (NullPointerException nolian) {

                    } catch (IndexOutOfBoundsException indErr) {

                    }
                }//end if

                if (!vars.get(i).isConstant()) {
                    if (j == 0) {
                        varTable.setValueAt(i, i, j);
                    } else if (j == 1) {
                        varTable.setValueAt(vars.get(i).getName(), i, j);
                    } else if (j == 2) {
                        varTable.setValueAt(vars.get(i).getValue(), i, j);
                    }
                }//end if VARIABLES
            }//end inner for
        }//end outer for

        /**
         * Write to constants table
         */
        clearTableData(constTable);
        for (int j = 0; j < constTable.getColumnCount(); j++) {
            for (int i = 0; i < consts.size(); i++) {
                if (i >= constTable.getRowCount()) {
                    try {
                        Vector<Object> obj = new Vector<Object>();
                        DefaultTableModel mod = (DefaultTableModel) constTable.getModel();
                        mod.addRow(obj);
                        constTable.setModel(mod);
                    } catch (NullPointerException nolian) {

                    } catch (IndexOutOfBoundsException indErr) {

                    }
                }//end if

                if (consts.get(i).isConstant()) {
                    if (j == 0) {
                        constTable.setValueAt(i, i, j);
                    } else if (j == 1) {
                        constTable.setValueAt(consts.get(i).getName(), i, j);
                    } else if (j == 2) {
                        constTable.setValueAt(consts.get(i).getValue(), i, j);
                    }

                }//end if VARIABLES
            }//end inner for
        }//end outer for

    }//end method writeVariablesToTable

    /**
     * CAREFUL!!! This method deletes all entries from a JTable
     *
     * @param table the JTable to be manipulated
     */
    public void clearTableData(JTable table) {
        DefaultTableModel mod = (DefaultTableModel) table.getModel();
        int size = mod.getDataVector().size();
        int i = 0;
        while (i < size) {
            mod.setValueAt("", i, 0);
            mod.setValueAt("", i, 1);
            mod.setValueAt("", i, 2);
            i++;
        }
    }

    /**
     * This method deletes a single entry from a JTable
     *
     * @param table the JTable to be manipulated
     * @param row the row whose entries are to be deleted
     */
    public void removeEntry(JTable table, int row) {
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
    public void clearTableRows(JTable table) {
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
    public void removeRow(JTable table, int row) {
        DefaultTableModel mod = (DefaultTableModel) table.getModel();
        mod.removeRow(row);
    }
 

      
  

    public static void main(String args[]) {
        String cmd = "var a,b,c,d,e=20.213;const a1=a; a2=6/(9-5.12424a);a=98.90;const t=a+1/2;b12=sin(a+b)/cos(a+b);"
                + "c1=1/c1;";
        VariableManager manager = new VariableManager();

        manager.parseCommand(cmd);
        System.out.println(" varStore = " + VariableManager.VARIABLES);

        int i = 0;
        while (true) {
            try {
                System.out.println(" Enter Command " + (i + 1));
                String comd = new java.util.Scanner(System.in).nextLine();
                manager.parseCommand(comd);
                System.out.println(" varStore = " + VariableManager.VARIABLES);
                ++i;
            }//end try
            catch (Exception e) {
                break;
            }
        }

    }

}
