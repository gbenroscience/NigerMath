/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * GraphProperties.java
 *
 * Created on 22-Oct-2010, 07:33:48
 */
package math.graph.gui;

import com.github.gbenroscience.math.graph.GraphElement;
import com.github.gbenroscience.math.graph.GridExpressionParser;
import com.github.gbenroscience.math.graph.tools.GraphColor;
import com.github.gbenroscience.math.graph.tools.GraphFont;
import gui.FontChooser;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import math.graph.gui.adapter.SwingDrawingContext;
import utils.ImageUtilities;
import utils.TableUtils;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class FunctionTracer extends javax.swing.JFrame implements Runnable {

    private FontChooser chooser = new FontChooser();
    private Color color;
    private Thread timer;
    private JFileChooser saveChooser;
    /**
     * Detects when a change has occurred to a property of this class.
     */
    private boolean changed = false;

    private boolean busy;

    /**
     * Creates new form GraphProperties
     *
     * @param function
     */
    public FunctionTracer(String function) {
        super("TRACING OUT FUNCTIONS THE NAIJA WAY...");
        initComponents();

        addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                // Check if the event is a HIERARCHY_CHANGED event
                // and specifically a PARENT_CHANGED event
                if ((e.getChangeFlags() & HierarchyEvent.PARENT_CHANGED) != 0) {
                    Container parent = paper.getParent();
                    // Check if the new parent is not null
                    if (parent != null) {
                        Dimension size = getParent().getPreferredSize();
                        com.github.gbenroscience.math.Point locationOfOrigin = new com.github.gbenroscience.math.Point(size.width / 2, size.height / 2);
                        paper.setLocationOfOrigin(locationOfOrigin);
                        paper.getGrid().setLocationOfOrigin(locationOfOrigin);
                        repaint();
                        parent.removeHierarchyListener(this);
                    } else {
                        System.out.println("Parent is null again (e.g., component was removed).");
                    }
                }
            }
        });

        saveChooser = new JFileChooser();
        saveChooser.setVisible(false);
        saveChooser.setDialogTitle("Save Your Plot");

        saveChooser.setMultiSelectionEnabled(false);
        saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        paper.getGrid().setFunction(function);
        timer = new Thread(this);
        ButtonGroup group = new ButtonGroup();
        group.add(degRadioButton);
        group.add(radRadioButton);
        group.add(gradRadioButton);
        functionTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 9));
        verticesTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 9));

        functionTable.setFont(new Font("Times New Roman", Font.BOLD, 12));
        verticesTable.setFont(new Font("Times New Roman", Font.BOLD, 12));

        setVisible(true);
        setResizable(false);
        Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(sz);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    setVisible(false);
                } catch (NullPointerException nolian) {

                }
            }
        });

        chooser.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    SwingDrawingContext sdc = paper.getContext();
                    GraphFont f = sdc.getGraphFont(chooser.selectedFont());
                    sdc.setFont(f);
                    paper.getContext().setFont(f);
                    paper.getGrid().setFont(f);
                    chooser.setVisible(false);
                } catch (NullPointerException nolian) {
                    nolian.printStackTrace();
                }
            }
        });
        colorCombo.removeAllItems();
        colorCombo.addItem("Graph Ticks Color");
        colorCombo.addItem("Major Axes Color");
        colorCombo.addItem("Grid Color");
        colorCombo.addItem("Plot Color");
        colorCombo.addItem("Graph Background Color");

        gridSizeSpinner.setModel(new SpinnerNumberModel(10, 1, 80, 1));
        loadValuesToControllerFromProgram();

        Point p = new Point(paper.getWidth() / 2, paper.getHeight() / 2);

        paper.getGrid().setLocationOfOrigin(new com.github.gbenroscience.math.Point(p.x, p.y));
        try {
            loadFunctionsOntoTables();
        } catch (NullPointerException nol) {

        }
        timer.start();
    }//end constructor

    /**
     * Used by the two JTable objects to communicate with the GraphPaper object.
     */
    private void applySettings() {
        setGraphHeight();
        setGraphWidth();
        setGridSize();
        setShowGridLines();
        setXLower();
        setXUpper();
        setHorizontalScale();
        setVerticalScale();
        setAutoScaleOn();
        setMinorTickLength();
        setMajorTickLength();
        setLabelAxes();
        setDRG();
        createCompoundFunctionFromTableEntries();
    }

    /**
     * Retrieves the current values from the program objects to the controller
     * object. In essence, this is a notifier.
     */
    private void loadValuesToControllerFromProgram() {
        SwingDrawingContext sdc = paper.getContext();
        Font f = sdc.getAWTFont(paper.getGrid().getFont());
        chooser.setFontValue(f);

        degRadioButton.setSelected(paper.getGrid().getDRG() == 0);
        radRadioButton.setSelected(paper.getGrid().getDRG() == 1);
        gradRadioButton.setSelected(paper.getGrid().getDRG() == 2);

        autoScaleOnCheckBox.setSelected(paper.getGrid().isAutoScaleOn());

        widthField.setText(String.valueOf(paper.getWidth()));
        heightField.setText(String.valueOf(paper.getHeight()));

        majorTicksTextField.setText(String.valueOf(paper.getGrid().getMajorTickLength()));
        minorTicksTextField.setText(String.valueOf(paper.getGrid().getMinorTickLength()));

        xLowerTextField.setText(String.valueOf(paper.getGrid().getLowerXLimit()));
        xUpperTextField.setText(String.valueOf(paper.getGrid().getUpperXLimit()));

        horizontalScaleTextField.setText(String.valueOf(paper.getGrid().getxStep()));
        verticalScaleTextField.setText(String.valueOf(paper.getGrid().getyStep()));

        gridSizeSpinner.setValue(paper.getGrid().getGridSize().width);

        showGridCheckBox.setSelected(paper.getGrid().isShowGridLines());
        calibrateAxesCheckBox.setSelected(paper.getGrid().isLabelAxis());

        for (int i = 0; i < verticesTable.getRowCount(); i++) {
            verticesTable.setValueAt(i + 1, i, 0);
        }
        for (int i = 0; i < functionTable.getRowCount(); i++) {
            functionTable.setValueAt(i + 1, i, 0);
        }

    }//end method

    /**
     * Loads a function or sequence of functions onto the relevant table
     */
    public void loadFunctionsOntoTables() {

        GridExpressionParser gep = paper.getGrid().getGridExpressionParser();
//ArrayList<GraphType> status = gep.getGraphType(); 

        int i = 0;
        for (GraphElement elem : gep.getGraphElements()) {
            if (elem.getGraphType().isVerticePlot()) {
                if (i >= verticesTable.getRowCount()) {
                    try {
                        Vector obj = new Vector();
                        DefaultTableModel mod = (DefaultTableModel) verticesTable.getModel();
                        mod.addRow(obj);
                        verticesTable.setModel(mod);
                        verticesTable.setValueAt(i + 1, i, 0);
                    } catch (NullPointerException nolian) {
                        nolian.printStackTrace();
                    } catch (IndexOutOfBoundsException indErr) {
                        indErr.printStackTrace();
                    }
                }//end if
                verticesTable.setValueAt(i + 1, i, 0);
                verticesTable.setValueAt(elem.getFunction().substring(0, elem.getFunction().indexOf("]") + 1), i, 1);
                verticesTable.setValueAt(elem.getFunction().substring(elem.getFunction().indexOf("]") + 1), i, 2);

            } else if (elem.getGraphType().isFunctionPlot()) {
                if (i >= functionTable.getRowCount()) {
                    try {
                        Vector obj = new Vector();
                        DefaultTableModel mod = (DefaultTableModel) functionTable.getModel();
                        mod.addRow(obj);
                        functionTable.setModel(mod);
                        functionTable.setValueAt(i + 1, i, 0);
                    } catch (NullPointerException nolian) {
                        nolian.printStackTrace();
                    } catch (IndexOutOfBoundsException indErr) {
                        indErr.printStackTrace();
                    }
                }//end if

                functionTable.setValueAt(i + 1, i, 0);
                functionTable.setValueAt(elem.getFunction(), i, 1);
            }//end else if
            else {

            }//end else

            i++;

        }//end for

    }//end method

    /**
     * Gets all entries from the tables and uses them to create a compound
     * function which is what objects of this class can process.
     */
    public void createCompoundFunctionFromTableEntries() {
        StringBuilder compound = new StringBuilder();

        for (int rows = 0; rows < verticesTable.getRowCount(); rows++) {
            try {
                //hors and vars must have the format: [num1,num2,num3,...num_i:]
                String hors = (String) verticesTable.getValueAt(rows, 1);
                String vers = (String) verticesTable.getValueAt(rows, 2);
                if(!hors.trim().isEmpty() && !vers.isEmpty()){
                    compound.append(hors).append(vers).append(";");
                }
            }//end try
            catch (NullPointerException nolian) {
                nolian.printStackTrace();
            }
        }//end for
        for (int rows = 0; rows < functionTable.getRowCount(); rows++) {
            try {
                String fun = (String) functionTable.getValueAt(rows, 1);
                if (!fun.trim().isEmpty()) {
                    compound.append(fun).append(";");
                }//end if
            }//end try
            catch (NullPointerException nol) {

            }
        }//end for
        paper.getGrid().setFunction(compound.toString());
    }//end method

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChooser(FontChooser chooser) {
        this.chooser = chooser;
    }

    public FontChooser getChooser() {
        return chooser;
    }

    public void setPanel(GraphPanel paper) {
        this.paper = paper;

        SwingDrawingContext sdc = paper.getContext();
        Font f = sdc.getAWTFont(paper.getGrid().getFont());
        chooser.setFontObject(f);
    }

    public GraphPanel getPaper() {
        return paper;
    }

    public JTable getVerticesTable() {
        return verticesTable;
    }

    public JTable getFunctionTable() {
        return functionTable;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        paper = new math.graph.gui.GraphPanel();
        panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel(); 
        jLabel6 = new javax.swing.JLabel();
        majorTicksTextField = new javax.swing.JTextField();
        minorTicksTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        calibrateAxesCheckBox = new javax.swing.JCheckBox();
        showGridCheckBox = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        gridSizeSpinner = new javax.swing.JSpinner();
        autoScaleOnCheckBox = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        xUpperTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        xLowerTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        verticalScaleTextField = new javax.swing.JTextField();
        horizontalScaleTextField = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        gradRadioButton = new javax.swing.JRadioButton();
        degRadioButton = new javax.swing.JRadioButton();
        radRadioButton = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        heightField = new javax.swing.JTextField();
        widthField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        graphFontButton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        colorButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        colorCombo = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        verticesTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        functionTable = new javax.swing.JTable();
        plotAllButton = new javax.swing.JButton();
        insertRowOnVerticesTableButton = new javax.swing.JButton();
        insertRowOnFunctionsTableButton = new javax.swing.JButton();
        saveGraphButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(nigermath.NigerMathApp.class).getContext().getResourceMap(FunctionTracer.class);
        panel.setBackground(resourceMap.getColor("panel.background")); // NOI18N
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        panel.setName("panel"); // NOI18N
        panel.setPreferredSize(new java.awt.Dimension(632, 310));

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel6.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        majorTicksTextField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        majorTicksTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        majorTicksTextField.setName("majorTicksTextField"); // NOI18N
        majorTicksTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                majorTicksTextFieldActionPerformed(evt);
            }
        });

        minorTicksTextField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        minorTicksTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        minorTicksTextField.setName("minorTicksTextField"); // NOI18N
        minorTicksTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minorTicksTextFieldActionPerformed(evt);
            }
        });

        jLabel5.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel5.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(26, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(minorTicksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(majorTicksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{majorTicksTextField, minorTicksTextField});

        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(majorTicksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(minorTicksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{majorTicksTextField, minorTicksTextField});

        jPanel3.setBackground(resourceMap.getColor("jPanel3.background")); // NOI18N
        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        jPanel3.setName("jPanel3"); // NOI18N

        calibrateAxesCheckBox.setBackground(resourceMap.getColor("calibrateAxesCheckBox.background")); // NOI18N
        calibrateAxesCheckBox.setFont(resourceMap.getFont("calibrateAxesCheckBox.font")); // NOI18N
        calibrateAxesCheckBox.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        calibrateAxesCheckBox.setSelected(true);
        calibrateAxesCheckBox.setText(resourceMap.getString("calibrateAxesCheckBox.text")); // NOI18N
        calibrateAxesCheckBox.setName("calibrateAxesCheckBox"); // NOI18N
        calibrateAxesCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                calibrateAxesCheckBoxItemStateChanged(evt);
            }
        });

        showGridCheckBox.setBackground(resourceMap.getColor("showGridCheckBox.background")); // NOI18N
        showGridCheckBox.setFont(resourceMap.getFont("calibrateAxesCheckBox.font")); // NOI18N
        showGridCheckBox.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        showGridCheckBox.setSelected(true);
        showGridCheckBox.setText(resourceMap.getString("showGridCheckBox.text")); // NOI18N
        showGridCheckBox.setName("showGridCheckBox"); // NOI18N
        showGridCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                showGridCheckBoxItemStateChanged(evt);
            }
        });

        jLabel9.setFont(resourceMap.getFont("calibrateAxesCheckBox.font")); // NOI18N
        jLabel9.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        gridSizeSpinner.setFont(resourceMap.getFont("gridSizeSpinner.font")); // NOI18N
        gridSizeSpinner.setName("gridSizeSpinner"); // NOI18N
        gridSizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                gridSizeSpinnerStateChanged(evt);
            }
        });

        autoScaleOnCheckBox.setBackground(resourceMap.getColor("autoScaleOnCheckBox.background")); // NOI18N
        autoScaleOnCheckBox.setFont(resourceMap.getFont("calibrateAxesCheckBox.font")); // NOI18N
        autoScaleOnCheckBox.setForeground(resourceMap.getColor("autoScaleOnCheckBox.foreground")); // NOI18N
        autoScaleOnCheckBox.setSelected(true);
        autoScaleOnCheckBox.setText(resourceMap.getString("autoScaleOnCheckBox.text")); // NOI18N
        autoScaleOnCheckBox.setName("autoScaleOnCheckBox"); // NOI18N
        autoScaleOnCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                autoScaleOnCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(autoScaleOnCheckBox)
                                        .addComponent(showGridCheckBox)
                                        .addComponent(calibrateAxesCheckBox))
                                .addContainerGap(109, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(gridSizeSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(autoScaleOnCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(showGridCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(calibrateAxesCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(gridSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBackground(resourceMap.getColor("jPanel4.background")); // NOI18N
        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        jPanel4.setName("jPanel4"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        xUpperTextField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        xUpperTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        xUpperTextField.setName("xUpperTextField"); // NOI18N
        xUpperTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xUpperTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel4.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        xLowerTextField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        xLowerTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        xLowerTextField.setName("xLowerTextField"); // NOI18N
        xLowerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xLowerTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel8.setForeground(resourceMap.getColor("jLabel7.foreground")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        verticalScaleTextField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        verticalScaleTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        verticalScaleTextField.setName("verticalScaleTextField"); // NOI18N
        verticalScaleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalScaleTextFieldActionPerformed(evt);
            }
        });

        horizontalScaleTextField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        horizontalScaleTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        horizontalScaleTextField.setName("horizontalScaleTextField"); // NOI18N
        horizontalScaleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horizontalScaleTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(xLowerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(horizontalScaleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(verticalScaleTextField)
                                        .addComponent(xUpperTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{horizontalScaleTextField, verticalScaleTextField, xLowerTextField, xUpperTextField});

        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(horizontalScaleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(verticalScaleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(xLowerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(xUpperTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
        );

        jPanel8.setBackground(resourceMap.getColor("jPanel8.background")); // NOI18N
        jPanel8.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        jPanel8.setName("jPanel8"); // NOI18N

        gradRadioButton.setBackground(resourceMap.getColor("gradRadioButton.background")); // NOI18N
        gradRadioButton.setFont(resourceMap.getFont("degRadioButton.font")); // NOI18N
        gradRadioButton.setForeground(resourceMap.getColor("gradRadioButton.foreground")); // NOI18N
        gradRadioButton.setText(resourceMap.getString("gradRadioButton.text")); // NOI18N
        gradRadioButton.setName("gradRadioButton"); // NOI18N
        gradRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                gradRadioButtonItemStateChanged(evt);
            }
        });

        degRadioButton.setBackground(resourceMap.getColor("degRadioButton.background")); // NOI18N
        degRadioButton.setFont(resourceMap.getFont("degRadioButton.font")); // NOI18N
        degRadioButton.setForeground(resourceMap.getColor("degRadioButton.foreground")); // NOI18N
        degRadioButton.setText(resourceMap.getString("degRadioButton.text")); // NOI18N
        degRadioButton.setName("degRadioButton"); // NOI18N
        degRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                degRadioButtonItemStateChanged(evt);
            }
        });

        radRadioButton.setBackground(resourceMap.getColor("radRadioButton.background")); // NOI18N
        radRadioButton.setFont(resourceMap.getFont("degRadioButton.font")); // NOI18N
        radRadioButton.setForeground(resourceMap.getColor("degRadioButton.foreground")); // NOI18N
        radRadioButton.setSelected(true);
        radRadioButton.setText(resourceMap.getString("radRadioButton.text")); // NOI18N
        radRadioButton.setName("radRadioButton"); // NOI18N
        radRadioButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radRadioButtonItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(degRadioButton)
                                .addGap(0, 0, 0)
                                .addComponent(radRadioButton)
                                .addGap(0, 0, 0)
                                .addComponent(gradRadioButton)
                                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{degRadioButton, gradRadioButton, radRadioButton});

        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(degRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(radRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(gradRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.setBackground(resourceMap.getColor("jPanel6.background")); // NOI18N
        jPanel6.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)))));
        jPanel6.setName("jPanel6"); // NOI18N

        heightField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        heightField.setText(resourceMap.getString("heightField.text")); // NOI18N
        heightField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        heightField.setName("heightField"); // NOI18N
        heightField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightFieldActionPerformed(evt);
            }
        });

        widthField.setFont(resourceMap.getFont("widthField.font")); // NOI18N
        widthField.setText(resourceMap.getString("widthField.text")); // NOI18N
        widthField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        widthField.setName("widthField"); // NOI18N
        widthField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                widthFieldActionPerformed(evt);
            }
        });

        jLabel11.setFont(resourceMap.getFont("jLabel11.font")); // NOI18N
        jLabel11.setForeground(resourceMap.getColor("jLabel11.foreground")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jLabel12.setFont(resourceMap.getFont("jLabel12.font")); // NOI18N
        jLabel12.setForeground(resourceMap.getColor("jLabel12.foreground")); // NOI18N
        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(heightField)
                                        .addComponent(widthField, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(widthField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12)
                                        .addComponent(heightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        graphFontButton.setBackground(resourceMap.getColor("graphFontButton.background")); // NOI18N
        graphFontButton.setFont(resourceMap.getFont("graphFontButton.font")); // NOI18N
        graphFontButton.setText(resourceMap.getString("graphFontButton.text")); // NOI18N
        graphFontButton.setName("graphFontButton"); // NOI18N
        graphFontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphFontButtonActionPerformed(evt);
            }
        });

        jPanel7.setBackground(resourceMap.getColor("jPanel7.background")); // NOI18N
        jPanel7.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)))));
        jPanel7.setName("jPanel7"); // NOI18N

        colorButton.setBackground(resourceMap.getColor("colorButton.background")); // NOI18N
        colorButton.setFont(resourceMap.getFont("colorButton.font")); // NOI18N
        colorButton.setText(resourceMap.getString("colorButton.text")); // NOI18N
        colorButton.setName("colorButton"); // NOI18N
        colorButton.setRequestFocusEnabled(false);
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setForeground(resourceMap.getColor("jLabel10.foreground")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        colorCombo.setBackground(resourceMap.getColor("colorCombo.background")); // NOI18N
        colorCombo.setFont(resourceMap.getFont("colorCombo.font")); // NOI18N
        colorCombo.setForeground(resourceMap.getColor("colorCombo.foreground")); // NOI18N
        colorCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        colorCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        colorCombo.setName("colorCombo"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(colorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(51, 51, 51)
                                                .addComponent(colorButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(colorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(colorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED))));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(420, 244));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        verticesTable.setBorder(new javax.swing.border.MatteBorder(null));
        verticesTable.setFont(resourceMap.getFont("verticesTable.font")); // NOI18N
        verticesTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "INDEX", "xPoints", "yPoints"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        verticesTable.setName("verticesTable"); // NOI18N
        verticesTable.getTableHeader().setReorderingAllowed(false);
        verticesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verticesTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(verticesTable);
        if (verticesTable.getColumnModel().getColumnCount() > 0) {
            verticesTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("verticesTable.columnModel.title0")); // NOI18N
            verticesTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("verticesTable.columnModel.title1")); // NOI18N
            verticesTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("verticesTable.columnModel.title2")); // NOI18N
        }

        jScrollPane2.setBorder(null);
        jScrollPane2.setFont(resourceMap.getFont("jScrollPane2.font")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        functionTable.setBorder(new javax.swing.border.MatteBorder(null));
        functionTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null}
                },
                new String[]{
                    "INDEX", "      FUNCTION"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        functionTable.setName("functionTable"); // NOI18N
        functionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                functionTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(functionTable);
        if (functionTable.getColumnModel().getColumnCount() > 0) {
            functionTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("functionTable.columnModel.title0")); // NOI18N
            functionTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("functionTable.columnModel.title1")); // NOI18N
        }

        plotAllButton.setBackground(resourceMap.getColor("plotAllButton.background")); // NOI18N
        plotAllButton.setFont(resourceMap.getFont("plotAllButton.font")); // NOI18N
        plotAllButton.setText(resourceMap.getString("plotAllButton.text")); // NOI18N
        plotAllButton.setName("plotAllButton"); // NOI18N
        plotAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotAllButtonActionPerformed(evt);
            }
        });

        insertRowOnVerticesTableButton.setBackground(resourceMap.getColor("insertRowOnVerticesTableButton.background")); // NOI18N
        insertRowOnVerticesTableButton.setFont(resourceMap.getFont("insertRowOnVerticesTableButton.font")); // NOI18N
        insertRowOnVerticesTableButton.setText(resourceMap.getString("insertRowOnVerticesTableButton.text")); // NOI18N
        insertRowOnVerticesTableButton.setName("insertRowOnVerticesTableButton"); // NOI18N
        insertRowOnVerticesTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRowOnVerticesTableButtonActionPerformed(evt);
            }
        });

        insertRowOnFunctionsTableButton.setBackground(resourceMap.getColor("insertRowOnFunctionsTableButton.background")); // NOI18N
        insertRowOnFunctionsTableButton.setFont(resourceMap.getFont("insertRowOnFunctionsTableButton.font")); // NOI18N
        insertRowOnFunctionsTableButton.setText(resourceMap.getString("insertRowOnFunctionsTableButton.text")); // NOI18N
        insertRowOnFunctionsTableButton.setName("insertRowOnFunctionsTableButton"); // NOI18N
        insertRowOnFunctionsTableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertRowOnFunctionsTableButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(2, 2, 2)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addComponent(insertRowOnVerticesTableButton)
                                                .addGap(70, 70, 70)
                                                .addComponent(plotAllButton)
                                                .addGap(47, 47, 47)
                                                .addComponent(insertRowOnFunctionsTableButton)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                                        .addComponent(insertRowOnVerticesTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(insertRowOnFunctionsTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(plotAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{insertRowOnFunctionsTableButton, insertRowOnVerticesTableButton, plotAllButton});

        saveGraphButton.setBackground(resourceMap.getColor("saveGraphButton.background")); // NOI18N
        saveGraphButton.setFont(resourceMap.getFont("saveGraphButton.font")); // NOI18N
        saveGraphButton.setText(resourceMap.getString("saveGraphButton.text")); // NOI18N
        saveGraphButton.setName("saveGraphButton"); // NOI18N
        saveGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGraphButtonActionPerformed(evt);
            }
        });

        printButton.setBackground(resourceMap.getColor("printButton.background")); // NOI18N
        printButton.setFont(resourceMap.getFont("printButton.font")); // NOI18N
        printButton.setText(resourceMap.getString("printButton.text")); // NOI18N
        printButton.setName("printButton"); // NOI18N
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(saveGraphButton)
                                                .addGap(25, 25, 25)
                                                .addComponent(printButton)
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelLayout.createSequentialGroup()
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addContainerGap())
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(100, 100, 100)))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(158, 158, 158))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLayout.createSequentialGroup()
                                                                .addGap(115, 115, 115)
                                                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(81, 81, 81))
                                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(38, 38, 38))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(23, 23, 23))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                                .addComponent(graphFontButton)
                                                .addGap(143, 143, 143))))
        );
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                                .addGap(3, 3, 3)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(graphFontButton, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(saveGraphButton, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24))
        );

        Color gridCol = resourceMap.getColor("paper.gridColor");
        GraphColor gc = new GraphColor(gridCol.getRed(), gridCol.getGreen(), gridCol.getBlue());
        Color majAxesCol = resourceMap.getColor("paper.majorAxesColor");
        GraphColor mc = new GraphColor(majAxesCol.getRed(), majAxesCol.getGreen(), majAxesCol.getBlue());
        Color plotCol = resourceMap.getColor("paper.plotColor");
        GraphColor pc = new GraphColor(plotCol.getRed(), plotCol.getGreen(), plotCol.getBlue());
        Color tickCol = resourceMap.getColor("paper.tickColor");
        GraphColor tc = new GraphColor(tickCol.getRed(), tickCol.getGreen(), tickCol.getBlue());

        paper.setBackground(resourceMap.getColor("paper.background")); // NOI18N
        paper.setFont(resourceMap.getFont("paper.font")); // NOI18N
        paper.setFunction(resourceMap.getString("paper.function")); // NOI18N
        paper.setGridColor(gc); // NOI18N
        paper.setGridSize(10);
        paper.setLabelAxis(true);
        paper.setLowerXLimit(-1000.0);
        paper.setMajorAxesColor(mc); // NOI18N
        paper.setMajorTickLength(12);
        paper.setName("paper"); // NOI18N
        paper.setPlotColor(pc); // NOI18N
        paper.setShowGridLines(true);
        paper.setTickColor(tc); // NOI18N
        paper.setUpperXLimit(1000.0);
        paper.setxStep(10.0);
        paper.setyStep(0.08);

        javax.swing.GroupLayout paperLayout = new javax.swing.GroupLayout(paper);
        paper.setLayout(paperLayout);
        paperLayout.setHorizontalGroup(
                paperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 799, Short.MAX_VALUE)
        );
        paperLayout.setVerticalGroup(
                paperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 733, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(paper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void majorTicksTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setMajorTickLength();
    }

    private void xLowerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setXLower();
    }

    private void verticalScaleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setVerticalScale();
    }

    private void horizontalScaleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setHorizontalScale();
    }

    private void graphFontButtonActionPerformed(java.awt.event.ActionEvent evt) {
        chooser.setVisible(true);
        SwingDrawingContext sdc = paper.getContext();
        Font f = sdc.getAWTFont(paper.getGrid().getFont());
        chooser.setFontValue(f);
    }

    private void widthFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setGraphWidth();
    }

    private void heightFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setGraphHeight();
    }

    private void minorTicksTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setMinorTickLength();
    }

    private void calibrateAxesCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        setLabelAxes();
    }

    private void showGridCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        setShowGridLines();
    }

    private void gridSizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {
        setGridSize();
        setTextFontSize();
    }

    private void xUpperTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        setXUpper();
    }

    private void colorButtonActionPerformed(java.awt.event.ActionEvent evt) {
        color = JColorChooser.showDialog(this, "SET GRAPH COMPONENT COLOR", color);
        GraphColor c = new GraphColor(color.getRed(), color.getGreen(), color.getBlue());
        if (colorCombo.getSelectedIndex() == 0) {
            paper.getGrid().setTickColor(c);
        } else if (colorCombo.getSelectedIndex() == 1) {
            paper.getGrid().setMajorAxesColor(c);
        } else if (colorCombo.getSelectedIndex() == 2) {
            paper.getGrid().setGridColor(c);
        } else if (colorCombo.getSelectedIndex() == 3) {
            paper.getGrid().setPlotColor(c);
        } else if (colorCombo.getSelectedIndex() == 4) {
            paper.setBackground(color);
        }

    }

    private void autoScaleOnCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {
        setAutoScaleOn();
    }

    private void degRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {
        setDRG();
    }

    private void radRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {
        setDRG();
    }

    private void gradRadioButtonItemStateChanged(java.awt.event.ItemEvent evt) {
        setDRG();
    }

    private void functionTableMouseClicked(java.awt.event.MouseEvent evt) {
        if (!evt.isMetaDown() && functionTable.getSelectedColumn() != 0) {

            final int row = functionTable.getSelectedRow();
            final JOptionPane pane = new JOptionPane("Please Enter A Function Of Type y = f(x)",
                    JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
            pane.setWantsInput(true);

            pane.setInitialSelectionValue(functionTable.getValueAt(row, 1));
            JDialog dialog = pane.createDialog("Function Prompt");
            dialog.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            dialog.setVisible(true);
            dialog.addWindowListener(new WindowAdapter() {
//Set Values In Table As Relevant When User Presses Close Button To Close Dialog
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                        functionTable.setValueAt(pane.getInputValue(), row, 1);
                    } else {
                        functionTable.setValueAt("", row, 1);
                    }

                }

            });
//Set Values In Table As Relevant When User Presses Ok Or Cancel Button To Close Dialog
            if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                functionTable.setValueAt(pane.getInputValue(), row, 1);
            } else {
                functionTable.setValueAt("", row, 1);
            }
        }
    }

    private void verticesTableMouseClicked(java.awt.event.MouseEvent evt) {
        if (!evt.isMetaDown() && verticesTable.getSelectedColumn() != 0) {

            if (verticesTable.getSelectedColumn() == 1) {
                final int row = verticesTable.getSelectedRow();
                final JOptionPane pane = new JOptionPane("Please Enter An Array \nOf X Coordinates\n In "
                        + "The Format: [num1,num2,.  :   ]\n"
                        + "Note That A Colon \":\"  \n"
                        + "Must Be The Last Item In The Array.",
                        JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
                pane.setWantsInput(true);

                pane.setInitialSelectionValue(verticesTable.getValueAt(row, 1));
                JDialog dialog = pane.createDialog("Array Of Horizontal Points Prompt");
                dialog.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
                dialog.setVisible(true);

                dialog.addWindowListener(new WindowAdapter() {
//Set Values In Table As Relevant When User Presses Close Button To Close Dialog
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                            verticesTable.setValueAt(pane.getInputValue(), row, 1);
                        } else {
                            verticesTable.setValueAt("", row, 1);
                        }

                    }

                });
//Set Values In Table As Relevant When User Presses Ok or Cancel Button To Close Dialog
                if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                    verticesTable.setValueAt(pane.getInputValue(), row, 1);
                } else {
                    verticesTable.setValueAt("", row, 1);
                }

            }//end if
            else if (verticesTable.getSelectedColumn() == 2) {

                final int row = verticesTable.getSelectedRow();
                final JOptionPane pane = new JOptionPane("Please Enter An Array \nOf Y Coordinates\n In "
                        + "The Format: [num1,num2,.  :   ]\n"
                        + "Note That A Colon \":\"  \n"
                        + "Must Be The Last Item In The Array.",
                        JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
                pane.setWantsInput(true);

                pane.setInitialSelectionValue(verticesTable.getValueAt(row, 2));
                JDialog dialog = pane.createDialog("Array Of Vertical Points Prompt");
                dialog.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
                dialog.setBackground(Color.green);
                dialog.setVisible(true);

                dialog.addWindowListener(new WindowAdapter() {

//Set Values In Table As Relevant When User Presses Close Button To Close Dialog
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                            verticesTable.setValueAt(pane.getInputValue(), row, 2);
                        } else {
                            verticesTable.setValueAt("", row, 2);
                        }
                    }

                });
//Set Values In Table As Relevant When User Presses Ok Or Cancel To Close Dialog
                if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                    verticesTable.setValueAt(pane.getInputValue(), row, 2);
                } else {
                    verticesTable.setValueAt("", row, 2);
                }

            }//end else if
        }
    }

    private void plotAllButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (!busy) {
            busy = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> { 
                        applySettings();
                        busy = false;
                    });
                }
            }).start();
        } else {
            JOptionPane.showMessageDialog(this, "A plot is in progress!", "Please wait!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void insertRowOnVerticesTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
        TableUtils.addRow();
        verticesTable.setValueAt(verticesTable.getRowCount(), verticesTable.getRowCount() - 1, 0);
    }

    private void insertRowOnFunctionsTableButtonActionPerformed(java.awt.event.ActionEvent evt) {
        new TableUtils(functionTable).addRow();
        functionTable.setValueAt(functionTable.getRowCount(), functionTable.getRowCount() - 1, 0);
    }

    private void saveGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {

        saveChooser.setVisible(true);
        int choice = saveChooser.showSaveDialog(this);

        if (choice == JFileChooser.APPROVE_OPTION) {

            String fileName = saveChooser.getSelectedFile().getName();
            fileName = (fileName.endsWith(".png") || fileName.endsWith(".jpg")) ? fileName : fileName.concat(".png");
            File dir = saveChooser.getCurrentDirectory();
            File f = new File(dir, fileName);

            try {
                BufferedImage graphImage = ImageUtilities.createSwingObjectImage(paper);
                ImageUtilities.saveImage(graphImage, f.getAbsolutePath());
                 JOptionPane.showMessageDialog(saveChooser, "Graph saved!");
            } catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(saveChooser, "Error in saving image");
            }//end catch
        }//end if
        else {
            JOptionPane.showMessageDialog(saveChooser, "Save Action Aborted");
        }
    }

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {

        // Get a PrinterJob
        PrinterJob job = PrinterJob.getPrinterJob();
        // Create a landscape page format
        PageFormat landscape = job.defaultPage();
        landscape.setOrientation(PageFormat.PORTRAIT);
        // Set up a book
        Book bk = new Book();
        bk.append(paper, landscape);
        // Pass the book to the PrinterJob
        job.setPageable(bk);
        // Put up the dialog box
        if (job.printDialog()) {
            // Print the job if the user didn't cancel printing
            try {
                job.print();
            } catch (Exception exc) {
                /* Handle Exception */ }
        }

    }

    private void setHorizontalScale() {
        try {
            double horizontal = Double.parseDouble(horizontalScaleTextField.getText());
            paper.getGrid().setxStep(horizontal);

            if (!verticalScaleTextField.getText().isEmpty()) {
                double vertical = Double.parseDouble(verticalScaleTextField.getText());
                paper.getGrid().setyStep(vertical);
            } else {
                verticalScaleTextField.setText(String.valueOf(paper.getGrid().getyStep()));

            }
            loadXStep();
        } catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A POSITIVE INTEGER!.", "BAD VALUE(S)", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setVerticalScale() {
        try {
            double vertical = Double.parseDouble(verticalScaleTextField.getText());
            paper.getGrid().setyStep(vertical);

            if (!horizontalScaleTextField.getText().isEmpty()) {
                double horizontal = Double.parseDouble(horizontalScaleTextField.getText());
                paper.getGrid().setxStep(horizontal);
            }//end if
            else {
                horizontalScaleTextField.setText(String.valueOf(paper.getGrid().getxStep()));
            }//end else
            loadXStep();
        }//end try
        catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A POSITIVE INTEGER!.", "BAD VALUE(S)", JOptionPane.ERROR_MESSAGE);
        }//end catch
    }//end method

    private void setGraphWidth() {
        try {
            int width = Integer.parseInt(widthField.getText());
            paper.setSize((width <= 3000) ? width : 2000, paper.getHeight());

            if (!heightField.getText().isEmpty()) {
                int height = Integer.parseInt(heightField.getText());
                paper.setSize(paper.getWidth(), (height <= 3000) ? height : 2000);
            } else {
                heightField.setText(String.valueOf(paper.getHeight()));
            }
        } catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A VALID NUMBER BETWEEN 0 AND 2000", "BAD VALUE(S)", JOptionPane.ERROR_MESSAGE);
        }
    }//end method

    private void setGraphHeight() {
        try {
            int height = Integer.parseInt(heightField.getText());
            paper.setSize(paper.getWidth(), (height <= 3000) ? height : 2000);
            if (!widthField.getText().isEmpty()) {
                int width = Integer.parseInt(widthField.getText());
                paper.setSize((width <= 3000) ? width : 2000, height);
            } else {
                widthField.setText(String.valueOf(paper.getWidth()));
            }
        } catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A VALID VALUE BETWEEN 0 AND 2000", "BAD HEIGHT", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateNumber(String number) {
        boolean isValid = true;
        try {
            double num = Double.parseDouble(number);
            return true;
        } catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A VALID NUMBER", "BAD VALUE", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    private void setGridSize() {
        paper.getGrid().setGridSize((Integer) gridSizeSpinner.getValue(), (Integer) gridSizeSpinner.getValue());
    }

    private void setTextFontSize() {
        try {

            SwingDrawingContext sdc = paper.getContext();
            Font font = sdc.getAWTFont(paper.getGrid().getFont());

            double size = font.getSize();
            double next = Double.parseDouble(gridSizeSpinner.getNextValue().toString());
            if (size > next) {
                size--;
            } else {
                size++;
            }
            Font f = new Font(font.getFamily(), font.getStyle(), (int) size);
            GraphFont gf = sdc.getGraphFont(f);
            paper.getGrid().setFont(gf);
            chooser.setFont(font);
        }//end try
        catch (NullPointerException nolian) {

        }
    }

    private void setMinorTickLength() {
        try {
            int minor = Integer.parseInt(minorTicksTextField.getText());
            paper.getGrid().setMinorTickLength(minor);

            if (!majorTicksTextField.getText().isEmpty()) {
                int major = Integer.parseInt(majorTicksTextField.getText());
                paper.getGrid().setMajorTickLength(major);
            } else {
                majorTicksTextField.setText(String.valueOf(paper.getGrid().getMajorTickLength()));

            }
        }//end try
        catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A POSITIVE INTEGER!.", "BAD VALUE(S)", JOptionPane.ERROR_MESSAGE);
        }
    }//end method

    private void setMajorTickLength() {

        try {
            int major = Integer.parseInt(majorTicksTextField.getText());
            paper.getGrid().setMajorTickLength(major);

            if (!minorTicksTextField.getText().isEmpty()) {
                int minor = Integer.parseInt(minorTicksTextField.getText());
                paper.getGrid().setMinorTickLength(minor);
            } else {
                minorTicksTextField.setText(String.valueOf(paper.getGrid().getMinorTickLength()));

            }
        } catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A POSITIVE INTEGER!.", "BAD VALUE(S)", JOptionPane.ERROR_MESSAGE);
        }
    }//end method

    private void setXUpper() {
        try {
            double xUpper = Double.parseDouble(xUpperTextField.getText());
            paper.getGrid().setUpperXLimit(xUpper);

            if (!xLowerTextField.getText().isEmpty()) {
                double xLower = Double.parseDouble(xLowerTextField.getText());
                paper.getGrid().setLowerXLimit(xLower);
            } else {
                xLowerTextField.setText(String.valueOf(paper.getGrid().getLowerXLimit()));

            }
            loadXStep();
        }//end try
        catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A POSITIVE INTEGER!.", "BAD VALUE(S)", JOptionPane.ERROR_MESSAGE);
        }//end catch
    }//end method

    private void setXLower() {
        try {
            double xLower = Double.parseDouble(xLowerTextField.getText());
            paper.getGrid().setLowerXLimit(xLower);

            if (!xUpperTextField.getText().isEmpty()) {
                double xUpper = Double.parseDouble(xUpperTextField.getText());
                paper.getGrid().setUpperXLimit(xUpper);
            }//end if
            else {
                xUpperTextField.setText(String.valueOf(paper.getGrid().getUpperXLimit()));

            }//end else
            loadXStep();
        }//end try
        catch (NumberFormatException numErr) {
            JOptionPane.showMessageDialog(null, "PLEASE ENTER A POSITIVE INTEGER!.", "BAD VALUE(S)", JOptionPane.ERROR_MESSAGE);
        }//end catch
    }//end method

    private void setShowGridLines() {
        paper.getGrid().setShowGridLines(showGridCheckBox.isSelected());
    }

    private void setLabelAxes() {
        paper.getGrid().setLabelAxis(calibrateAxesCheckBox.isSelected());
    }

    private void setAutoScaleOn() {
        paper.getGrid().setAutoScaleOn(autoScaleOnCheckBox.isSelected());
    }

    private void setDRG() {
        paper.getGrid().setDRG((degRadioButton.isSelected()) ? 0 : ((radRadioButton.isSelected()) ? 1 : 2));
    }

    private void loadXStep() {
        horizontalScaleTextField.setText(String.valueOf(paper.getGrid().getxStep()));
    }

    @Override
    public void run() {
        Thread me = Thread.currentThread();
        while (timer == me) {
            try {
                Thread.sleep(1000);
            }//end catch
            catch (InterruptedException exception) {

            }//end catch
            if (paper.getHeight() != panel.getHeight()) {
                paper.setSize(paper.getWidth(), panel.getHeight());
            }
            paper.repaint();
        }//end while

    }//end method run.

    public static void main(String args[]) {

        FunctionTracer props = new FunctionTracer("y=@(x)cos(x)");
        props.setVisible(true);
    }

    // Variables declaration - do not modify                     
    private javax.swing.JCheckBox autoScaleOnCheckBox;
    private javax.swing.JCheckBox calibrateAxesCheckBox;
    private javax.swing.JButton colorButton;
    private javax.swing.JComboBox colorCombo;
    private javax.swing.JRadioButton degRadioButton;
    private javax.swing.JTable functionTable;
    private javax.swing.JRadioButton gradRadioButton;
    private javax.swing.JButton graphFontButton;
    private javax.swing.JSpinner gridSizeSpinner;
    private javax.swing.JTextField heightField;
    private javax.swing.JTextField horizontalScaleTextField;
    private javax.swing.JButton insertRowOnFunctionsTableButton;
    private javax.swing.JButton insertRowOnVerticesTableButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField majorTicksTextField;
    private javax.swing.JTextField minorTicksTextField;
    private javax.swing.JPanel panel;
    private math.graph.gui.GraphPanel paper;
    private javax.swing.JButton plotAllButton;
    private javax.swing.JButton printButton;
    private javax.swing.JRadioButton radRadioButton;
    private javax.swing.JButton saveGraphButton;
    private javax.swing.JCheckBox showGridCheckBox;
    private javax.swing.JTextField verticalScaleTextField;
    private javax.swing.JTable verticesTable;
    private javax.swing.JTextField widthField;
    private javax.swing.JTextField xLowerTextField;
    private javax.swing.JTextField xUpperTextField;
    // End of variables declaration                   

}
