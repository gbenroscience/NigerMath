/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * NaijaCalc.java
 *
 * Created on 28-Oct-2010, 15:48:23
 */
package gui;

import utils.HistoryManager;
import utils.FormulaeManager;
import utils.WindowList;
import utils.MathExpressionManager;
import utils.ErrorLog;
import utils.Mode;
import utils.MatrixFunctionManager;
import utils.FunctionManager;
import com.github.gbenroscience.parser.methods.Method;
import com.github.gbenroscience.parser.*;
import help.HelpLoader;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import com.github.gbenroscience.math.matrix.MatrixVariableManager;
import utils.commandline.CommandParser;
import utils.commandline.State;
import static com.github.gbenroscience.parser.STRING.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class NaijaCalc extends javax.swing.JFrame implements Runnable {

    private Mode mode = Mode.CALCULATOR;// The usage mode of this tool..either via the command line or in the calculator mode.
    private KeyPad keyboard;
    public String expr;//the input
    private static WindowList list;
    private FontChooser fontChooser;
    public static final ErrorLog processLogger = new ErrorLog();//Records all errors.
    /**
     * Creates new form NaijaCalc
     */
    private Thread timer;
    private HistoryManager histMan = new HistoryManager();
    private FormulaeManager formMan = new FormulaeManager();
    private MathExpressionManager mathMan = new MathExpressionManager();
    private OperatingSystem manager = new OperatingSystem();
    private MatrixFunctionManager matrixFuncMan = new MatrixFunctionManager();
    private MatrixVariableManager matrixVarMan = new MatrixVariableManager();
    static NaijaCalc.Task task;

    public NaijaCalc(String title) {
        super(title);
    }

    public NaijaCalc() {
        this("MathematicalNaija.YOUR SUPER-DUPER MATHEMATICAL MESSENGER.");
        initComponents();

        setVisible(false);
        Caret caret = calculatorScreen.getCaret();
        caret.setBlinkRate(30);
        calculatorScreen.setCaret(caret);
        calculatorScreen.setCaretColor(Color.GREEN);
        resetCalc();
        setResizable(false);
        fontChooser = new FontChooser();

        varTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        varTable.getTableHeader().setForeground(Color.darkGray);
        constantsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        constantsTable.getTableHeader().setForeground(Color.darkGray);
        formTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        formTable.getTableHeader().setForeground(Color.darkGray);
        historyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        historyTable.getTableHeader().setForeground(Color.darkGray);
        matrixTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        matrixTable.getTableHeader().setForeground(Color.BLACK);

        ButtonGroup group = new ButtonGroup();
        group.add(commandLineModeRadioButtonMenuItem);
        group.add(calculatorModeRadioButtonMenuItem);

        ButtonGroup menuGroup = new ButtonGroup();
        menuGroup.add(degsRadioMenuItem);
        menuGroup.add(radsRadioMenuItem);
        menuGroup.add(gradsRadioMenuItem);

        evaluatedCommands.setEditable(false);

        timer = new Thread(this);
        timer.start();
        java.awt.Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(dim);
        pack();
    }//end constructor NaijaCalc.

    private void resetCalc() {
        calculatorScreen.setText("");
        commandLineOutput.setText("OUTPUT>>>>>>>>>\n_______________\n\n\n");
    }

    public void setMatrixFuncMan(MatrixFunctionManager matrixFuncMan) {
        this.matrixFuncMan = matrixFuncMan;
    }

    public MatrixFunctionManager getMatrixFuncMan() {
        return matrixFuncMan;
    }

    public void setMatrixVarMan(MatrixVariableManager matrixVarMan) {
        this.matrixVarMan = matrixVarMan;
    }

    public MatrixVariableManager getMatrixVarMan() {
        return matrixVarMan;
    }

    /**
     *
     * @return the OperatingSystem object
     */
    public OperatingSystem getManager() {
        return manager;
    }

    /**
     *
     * @param manager sets the Operating System Manager object
     */
    void setManager(OperatingSystem manager) {
        this.manager = manager;
    }

    /**
     *
     * @return the MathExpressionManager object
     */
    public MathExpressionManager getFuncMan() {
        return mathMan;
    }

    /**
     *
     * @param mathMan sets the MathExpressionManager object
     */
    public void setFuncMan(MathExpressionManager funcMan) {
        this.mathMan = funcMan;
    }

    /**
     *
     * @return the KeyPad attribute of objects of this class
     */
    public KeyPad getKeyboard() {
        return keyboard;
    }

    /**
     *
     * @param keyboard sets the KeyPad attribute of objects of this class
     */
    public void setKeyboard(KeyPad keyboard) {
        this.keyboard = keyboard;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    /**
     * Switches the color of the command line label
     */
    public void commandLineTextActivator(boolean activated) {//com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel//
        if (activated) {
            commandLineLabel.setForeground(Color.red);
        } else {
            commandLineLabel.setForeground(Color.black);
        }
    }

    @Override
    public void run() {

        Thread me = Thread.currentThread();
        while (timer == me) {
            if (keyboard == null) {
                keyboard = new KeyPad();
            }
            try {
                Thread.currentThread().sleep(100);
                repaint();
                if (keyboard != null) {
                    Thread.currentThread().sleep(400000000000L);
                }
            } catch (InterruptedException e) {
            }
        }

    }//end method run.

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        parentPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        calculatorScreen = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        commandLineLabel = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        commandLineInput = new javax.swing.JTextArea();
        solveButton = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        evaluatedCommands = new javax.swing.JTextArea();
        commandLineHistoryLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel13 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        keyBoardCtrl = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        formTable = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        constantsTable = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        varTable = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        matrixTable = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        funcTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        commandLineOutput = new javax.swing.JTextArea();
        resultLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        formatMenu = new javax.swing.JMenu();
        customizerMenu = new javax.swing.JMenu();
        colorMenu = new javax.swing.JMenu();
        commandLineInputBackgroundColorCtrlMenuItem = new javax.swing.JMenuItem();
        commandLineInputTextColorCtrlMenuItem = new javax.swing.JMenuItem();
        calculatorScreenBackgroundColorCtrlMenuItem = new javax.swing.JMenuItem();
        calculatorScreenTextColorCtrlMenuItem = new javax.swing.JMenuItem();
        calculatorScreenBorderBackgroundColorCtrlMenuItem = new javax.swing.JMenuItem();
        commandLineOutputBackgroundColorCtrlMenuItem = new javax.swing.JMenuItem();
        commandLineInputLabelTextColorMenuItem = new javax.swing.JMenuItem();
        commandLineOutputLabelTextColorMenuItem = new javax.swing.JMenuItem();
        commandLineOutputTextColorCtrlMenuItem = new javax.swing.JMenuItem();
        generalFrameWorkBackgroundColorMenuItem = new javax.swing.JMenuItem();
        evaluatedCommandsScreenBackgroundColorMenuItem = new javax.swing.JMenuItem();
        evaluatedCommandsScreenTextColorMenuItem = new javax.swing.JMenuItem();
        evaluatedCommandsLabelTextColorMenuItem = new javax.swing.JMenuItem();
        variablesTableBackgroundColorMenuItem = new javax.swing.JMenuItem();
        variablesTableTextColorMenuItem = new javax.swing.JMenuItem();
        constantsTableBackgroundColorMenuItem = new javax.swing.JMenuItem();
        constantsTableTextColorMenuItem = new javax.swing.JMenuItem();
        formulaTableBackgroundColorMenuItem = new javax.swing.JMenuItem();
        formulaTableTextColorMenuItem = new javax.swing.JMenuItem();
        historyTableBackgroundColorMenuItem = new javax.swing.JMenuItem();
        historyTableTextColorMenuItem = new javax.swing.JMenuItem();
        parserMessagesTextColorMenuItem = new javax.swing.JMenuItem();
        progressMonitorTextColorMenuItem = new javax.swing.JMenuItem();
        messagingScreenBackgroundColorMenuItem = new javax.swing.JMenuItem();
        messagingScreenTextColorMenuItem = new javax.swing.JMenuItem();
        fontMenu = new javax.swing.JMenu();
        calculatorScreenFontCtrlMenuItem = new javax.swing.JMenuItem();
        commandLineInputScreenFontCtrlMenuItem = new javax.swing.JMenuItem();
        commandLineOutputScreenFontCtrlMenuItem = new javax.swing.JMenuItem();
        evaluatedCommandsScreenFontCtrlMenuItem = new javax.swing.JMenuItem();
        commandLineInputScreenLabelFontCtrlMenuItem = new javax.swing.JMenuItem();
        commandLineOutputScreenLabelFontCtrlMenuItem = new javax.swing.JMenuItem();
        keyboardSwitchButtonFontCtrlMenuItem = new javax.swing.JMenuItem();
        variablesTableFontCtrlMenuItem = new javax.swing.JMenuItem();
        variablesTableHeaderFontCtrlMenuItem = new javax.swing.JMenuItem();
        constantsTableFontCtrlMenuItem = new javax.swing.JMenuItem();
        constantsTableHeaderFontCtrlMenuItem = new javax.swing.JMenuItem();
        formulaTableFontCtrlMenuItem = new javax.swing.JMenuItem();
        formulaTableHeaderFontCtrlMenuItem = new javax.swing.JMenuItem();
        historyTableFontCtrlMenuItem = new javax.swing.JMenuItem();
        historyTableHeaderFontCtrlMenuItem = new javax.swing.JMenuItem();
        messagingScreenColorCtrlMenuItem = new javax.swing.JMenuItem();
        progressMonitorFontCtrlMenuItem = new javax.swing.JMenuItem();
        parserMessagesFontCtrlMenuItem = new javax.swing.JMenuItem();
        tasksMenu = new javax.swing.JMenu();
        lockerMenuItem = new javax.swing.JMenuItem();
        switchModeMenu = new javax.swing.JMenu();
        commandLineModeRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        calculatorModeRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
        jMenu3 = new javax.swing.JMenu();
        degsRadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        radsRadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        gradsRadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        jMenu4 = new javax.swing.JMenu();
        calculatorMenuItem = new javax.swing.JMenuItem();
        simultaneousequationsMenuItem = new javax.swing.JMenuItem();
        quadraticequationsMenuItem = new javax.swing.JMenuItem();
        plottingFunctionsMenuItem = new javax.swing.JMenuItem();
        differentialequationsMenuItem = new javax.swing.JMenuItem();
        tartagliasequationsMenuItem = new javax.swing.JMenuItem();
        statisticsMenuItem = new javax.swing.JMenuItem();
        rootsOfEquationsMenuItem = new javax.swing.JMenuItem();
        numericalIntegralsMenuItem = new javax.swing.JMenuItem();
        numericalDerivativesMenuItem = new javax.swing.JMenuItem();
        matrixMultiplicationMenuItem = new javax.swing.JMenuItem();
        matrixAdditionMenuItem = new javax.swing.JMenuItem();
        matrixSubtractionMenuItem = new javax.swing.JMenuItem();
        determinantsMenuItem = new javax.swing.JMenuItem();
        triangularMatricesMenuItem = new javax.swing.JMenuItem();
        baseSystemMenu = new javax.swing.JMenu();
        base2RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base3RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base4RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base5RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base6RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base7RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base8RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base9RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        base10RadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        saveEvaluatedCommandsDocumentMenuItem = new javax.swing.JMenuItem();
        openDocumentMenuItem = new javax.swing.JMenuItem();
        _exitMenuItem = new javax.swing.JMenuItem();
        forceExitMenuItem = new javax.swing.JMenuItem();
        setColorMenuItem = new javax.swing.JMenuItem();
        storeVariableMenuItem = new javax.swing.JMenuItem();
        storeConstantMenuItem = new javax.swing.JMenuItem();
        makeFormulaMenuItem = new javax.swing.JMenuItem();
        storeFormulaMenuItem = new javax.swing.JMenuItem();
        showTimeMenuItem = new javax.swing.JMenuItem();
        clearMenu = new javax.swing.JMenu();
        commandLineInputclearMenuItem = new javax.swing.JMenuItem();
        commandLineOutputclearMenuItem = new javax.swing.JMenuItem();
        evaluatedCommandsclearMenuItem = new javax.swing.JMenuItem();
        calculatorScreenclearMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        abooutMenuItem = new javax.swing.JMenuItem();
        creditsMenuItem = new javax.swing.JMenuItem();
        chatMenu = new javax.swing.JMenu();
        connectToRadioMenuItem = new javax.swing.JRadioButtonMenuItem();
        getConnectionFromRadioMenuItem = new javax.swing.JRadioButtonMenuItem();

        jLayeredPane1.setName("jLayeredPane1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 640));
        setName("Form"); // NOI18N
        setResizable(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(nigermath.NigerMathApp.class).getContext().getResourceMap(NaijaCalc.class);
        parentPanel.setBackground(resourceMap.getColor("parentPanel.background")); // NOI18N
        parentPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        parentPanel.setName("parentPanel"); // NOI18N
        parentPanel.setPreferredSize(new java.awt.Dimension(1040, 807));

        jPanel5.setBackground(resourceMap.getColor("jPanel5.background")); // NOI18N
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(589, 620));

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setName("jPanel1"); // NOI18N

        calculatorScreen.setBackground(resourceMap.getColor("calculatorScreen.background")); // NOI18N
        calculatorScreen.setFont(resourceMap.getFont("calculatorScreen.font")); // NOI18N
        calculatorScreen.setForeground(resourceMap.getColor("calculatorScreen.foreground")); // NOI18N
        calculatorScreen.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        calculatorScreen.setName("calculatorScreen"); // NOI18N
        calculatorScreen.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                calculatorScreenCaretUpdate(evt);
            }
        });
        calculatorScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorScreenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calculatorScreen)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calculatorScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setName("jPanel2"); // NOI18N

        commandLineLabel.setBackground(resourceMap.getColor("commandLineLabel.background")); // NOI18N
        commandLineLabel.setFont(resourceMap.getFont("commandLineLabel.font")); // NOI18N
        commandLineLabel.setForeground(resourceMap.getColor("commandLineLabel.foreground")); // NOI18N
        commandLineLabel.setText(resourceMap.getString("commandLineLabel.text")); // NOI18N
        commandLineLabel.setName("commandLineLabel"); // NOI18N
        commandLineLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                commandLineLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                commandLineLabelMouseExited(evt);
            }
        });

        jPanel10.setBackground(resourceMap.getColor("jPanel10.background")); // NOI18N
        jPanel10.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED))));
        jPanel10.setName("jPanel10"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel15.setBackground(resourceMap.getColor("jPanel15.background")); // NOI18N
        jPanel15.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED))));
        jPanel15.setName("jPanel15"); // NOI18N

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        commandLineInput.setEditable(false);
        commandLineInput.setBackground(resourceMap.getColor("commandLineInput.background")); // NOI18N
        commandLineInput.setColumns(20);
        commandLineInput.setFont(resourceMap.getFont("commandLineInput.font")); // NOI18N
        commandLineInput.setForeground(resourceMap.getColor("commandLineInput.foreground")); // NOI18N
        commandLineInput.setLineWrap(true);
        commandLineInput.setRows(5);
        commandLineInput.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        commandLineInput.setCaretColor(resourceMap.getColor("commandLineInput.caretColor")); // NOI18N
        commandLineInput.setName("commandLineInput"); // NOI18N
        commandLineInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                commandLineInputKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(commandLineInput);

        solveButton.setBackground(resourceMap.getColor("solveButton.background")); // NOI18N
        solveButton.setFont(resourceMap.getFont("solveButton.font")); // NOI18N
        solveButton.setText(resourceMap.getString("solveButton.text")); // NOI18N
        solveButton.setName("solveButton"); // NOI18N
        solveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addComponent(solveButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(solveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        evaluatedCommands.setEditable(false);
        evaluatedCommands.setBackground(resourceMap.getColor("evaluatedCommands.background")); // NOI18N
        evaluatedCommands.setColumns(20);
        evaluatedCommands.setFont(resourceMap.getFont("evaluatedCommands.font")); // NOI18N
        evaluatedCommands.setForeground(resourceMap.getColor("evaluatedCommands.foreground")); // NOI18N
        evaluatedCommands.setRows(5);
        evaluatedCommands.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED))));
        evaluatedCommands.setName("evaluatedCommands"); // NOI18N
        jScrollPane6.setViewportView(evaluatedCommands);

        commandLineHistoryLabel.setFont(resourceMap.getFont("commandLineHistoryLabel.font")); // NOI18N
        commandLineHistoryLabel.setForeground(resourceMap.getColor("commandLineHistoryLabel.foreground")); // NOI18N
        commandLineHistoryLabel.setText(resourceMap.getString("commandLineHistoryLabel.text")); // NOI18N
        commandLineHistoryLabel.setName("commandLineHistoryLabel"); // NOI18N
        commandLineHistoryLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                commandLineHistoryLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                commandLineHistoryLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane6))
                        .addGap(3, 3, 3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(commandLineHistoryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(commandLineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(74, 74, 74))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(commandLineLabel)
                .addGap(14, 14, 14)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(commandLineHistoryLabel)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );

        jPanel3.setBackground(resourceMap.getColor("jPanel3.background")); // NOI18N
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(651, 620));

        jSeparator3.setBackground(resourceMap.getColor("jSeparator3.background")); // NOI18N
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setName("jSeparator3"); // NOI18N

        jPanel13.setBackground(resourceMap.getColor("jPanel13.background")); // NOI18N
        jPanel13.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createEtchedBorder()));
        jPanel13.setName("jPanel13"); // NOI18N

        jPanel9.setBackground(resourceMap.getColor("jPanel9.background")); // NOI18N
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setName("jPanel9"); // NOI18N

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        historyTable.setFont(resourceMap.getFont("historyTable.font")); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "INDEX", "HISTORY", "TIME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        historyTable.setName("historyTable"); // NOI18N
        historyTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historyTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                historyTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                historyTableMouseExited(evt);
            }
        });
        jScrollPane7.setViewportView(historyTable);
        if (historyTable.getColumnModel().getColumnCount() > 0) {
            historyTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("historyTable.columnModel.title0")); // NOI18N
            historyTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("historyTable.columnModel.title1")); // NOI18N
            historyTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("historyTable.columnModel.title2")); // NOI18N
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        keyBoardCtrl.setBackground(resourceMap.getColor("keyBoardCtrl.background")); // NOI18N
        keyBoardCtrl.setFont(resourceMap.getFont("keyBoardCtrl.font")); // NOI18N
        keyBoardCtrl.setText(resourceMap.getString("keyBoardCtrl.text")); // NOI18N
        keyBoardCtrl.setName("keyBoardCtrl"); // NOI18N
        keyBoardCtrl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyBoardCtrlActionPerformed(evt);
            }
        });

        jPanel8.setBackground(resourceMap.getColor("jPanel8.background")); // NOI18N
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.setName("jPanel8"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        formTable.setFont(resourceMap.getFont("formTable.font")); // NOI18N
        formTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "INDEX", "VARIABLE = ", "EXPRESSION", "DESCRIPTION"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        formTable.setName("formTable"); // NOI18N
        formTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formTableMouseExited(evt);
            }
        });
        jScrollPane4.setViewportView(formTable);
        if (formTable.getColumnModel().getColumnCount() > 0) {
            formTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("formTable.columnModel.title0")); // NOI18N
            formTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("formTable.columnModel.title1")); // NOI18N
            formTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("formTable.columnModel.title2")); // NOI18N
            formTable.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("formTable.columnModel.title3")); // NOI18N
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        jPanel11.setBackground(resourceMap.getColor("jPanel11.background")); // NOI18N
        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setName("jPanel11"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        constantsTable.setFont(resourceMap.getFont("constantsTable.font")); // NOI18N
        constantsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "INDEX", "CONSTANT", "VALUE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        constantsTable.setName("constantsTable"); // NOI18N
        constantsTable.getTableHeader().setReorderingAllowed(false);
        constantsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                constantsTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                constantsTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                constantsTableMouseExited(evt);
            }
        });
        jScrollPane5.setViewportView(constantsTable);
        if (constantsTable.getColumnModel().getColumnCount() > 0) {
            constantsTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("constantsTable.columnModel.title0")); // NOI18N
            constantsTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("constantsTable.columnModel.title1")); // NOI18N
            constantsTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("constantsTable.columnModel.title2")); // NOI18N
        }

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        jPanel7.setBackground(resourceMap.getColor("jPanel7.background")); // NOI18N
        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        varTable.setFont(resourceMap.getFont("varTable.font")); // NOI18N
        varTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "INDEX", "VARIABLE", "VALUE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        varTable.setName("varTable"); // NOI18N
        varTable.getTableHeader().setReorderingAllowed(false);
        varTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                varTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                varTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                varTableMouseExited(evt);
            }
        });
        jScrollPane3.setViewportView(varTable);
        if (varTable.getColumnModel().getColumnCount() > 0) {
            varTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("varTable.columnModel.title0")); // NOI18N
            varTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("varTable.columnModel.title1")); // NOI18N
            varTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("varTable.columnModel.title2")); // NOI18N
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
        );

        jPanel14.setBackground(resourceMap.getColor("jPanel14.background")); // NOI18N
        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel14.setName("jPanel14"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        matrixTable.setFont(resourceMap.getFont("matrixTable.font")); // NOI18N
        matrixTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "INDEX", "MATRIX VARIABLE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        matrixTable.setName("matrixTable"); // NOI18N
        matrixTable.getTableHeader().setReorderingAllowed(false);
        matrixTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                matrixTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                matrixTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                matrixTableMouseExited(evt);
            }
        });
        jScrollPane1.setViewportView(matrixTable);
        if (matrixTable.getColumnModel().getColumnCount() > 0) {
            matrixTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("matrixTable.columnModel.title0")); // NOI18N
            matrixTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("matrixTable.columnModel.title1")); // NOI18N
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        jPanel16.setBackground(resourceMap.getColor("jPanel16.background")); // NOI18N
        jPanel16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel16.setName("jPanel16"); // NOI18N

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        funcTable.setFont(resourceMap.getFont("funcTable.font")); // NOI18N
        funcTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "INDEX", "NAME", "FUNCTION"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        funcTable.setName("funcTable"); // NOI18N
        funcTable.getTableHeader().setReorderingAllowed(false);
        funcTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                funcTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                funcTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                funcTableMouseExited(evt);
            }
        });
        jScrollPane9.setViewportView(funcTable);
        if (funcTable.getColumnModel().getColumnCount() > 0) {
            funcTable.getColumnModel().getColumn(0).setMinWidth(60);
            funcTable.getColumnModel().getColumn(0).setPreferredWidth(60);
            funcTable.getColumnModel().getColumn(0).setMaxWidth(60);
            funcTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("matrixTable.columnModel.title0")); // NOI18N
            funcTable.getColumnModel().getColumn(1).setMinWidth(60);
            funcTable.getColumnModel().getColumn(1).setPreferredWidth(60);
            funcTable.getColumnModel().getColumn(1).setMaxWidth(60);
            funcTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("matrixTable.columnModel.title1")); // NOI18N
            funcTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("funcTable.columnModel.title2")); // NOI18N
        }

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(keyBoardCtrl, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(keyBoardCtrl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBackground(resourceMap.getColor("jScrollPane2.background")); // NOI18N
        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        commandLineOutput.setEditable(false);
        commandLineOutput.setBackground(resourceMap.getColor("commandLineOutput.background")); // NOI18N
        commandLineOutput.setColumns(20);
        commandLineOutput.setFont(resourceMap.getFont("commandLineOutput.font")); // NOI18N
        commandLineOutput.setForeground(resourceMap.getColor("commandLineOutput.foreground")); // NOI18N
        commandLineOutput.setLineWrap(true);
        commandLineOutput.setRows(5);
        commandLineOutput.setText(resourceMap.getString("commandLineOutput.text")); // NOI18N
        commandLineOutput.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        commandLineOutput.setName("commandLineOutput"); // NOI18N
        jScrollPane2.setViewportView(commandLineOutput);

        resultLabel.setFont(resourceMap.getFont("resultLabel.font")); // NOI18N
        resultLabel.setForeground(resourceMap.getColor("resultLabel.foreground")); // NOI18N
        resultLabel.setText(resourceMap.getString("resultLabel.text")); // NOI18N
        resultLabel.setName("resultLabel"); // NOI18N
        resultLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                resultLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                resultLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(resultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(resultLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
        parentPanel.setLayout(parentPanelLayout);
        parentPanelLayout.setHorizontalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        parentPanelLayout.setVerticalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jMenuBar1.add(jMenu1);

        formatMenu.setText(resourceMap.getString("formatMenu.text")); // NOI18N
        formatMenu.setName("formatMenu"); // NOI18N

        customizerMenu.setText(resourceMap.getString("customizerMenu.text")); // NOI18N
        customizerMenu.setName("customizerMenu"); // NOI18N

        colorMenu.setText(resourceMap.getString("colorMenu.text")); // NOI18N
        colorMenu.setName("colorMenu"); // NOI18N

        commandLineInputBackgroundColorCtrlMenuItem.setText(resourceMap.getString("commandLineInputBackgroundColorCtrlMenuItem.text")); // NOI18N
        commandLineInputBackgroundColorCtrlMenuItem.setName("commandLineInputBackgroundColorCtrlMenuItem"); // NOI18N
        commandLineInputBackgroundColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineInputBackgroundColorCtrlMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(commandLineInputBackgroundColorCtrlMenuItem);

        commandLineInputTextColorCtrlMenuItem.setText(resourceMap.getString("commandLineInputTextColorCtrlMenuItem.text")); // NOI18N
        commandLineInputTextColorCtrlMenuItem.setName("commandLineInputTextColorCtrlMenuItem"); // NOI18N
        commandLineInputTextColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineInputTextColorCtrlMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(commandLineInputTextColorCtrlMenuItem);

        calculatorScreenBackgroundColorCtrlMenuItem.setText(resourceMap.getString("calculatorScreenBackgroundColorCtrlMenuItem.text")); // NOI18N
        calculatorScreenBackgroundColorCtrlMenuItem.setName("calculatorScreenBackgroundColorCtrlMenuItem"); // NOI18N
        calculatorScreenBackgroundColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorScreenBackgroundColorCtrlMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(calculatorScreenBackgroundColorCtrlMenuItem);

        calculatorScreenTextColorCtrlMenuItem.setText(resourceMap.getString("calculatorScreenTextColorCtrlMenuItem.text")); // NOI18N
        calculatorScreenTextColorCtrlMenuItem.setName("calculatorScreenTextColorCtrlMenuItem"); // NOI18N
        calculatorScreenTextColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorScreenTextColorCtrlMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(calculatorScreenTextColorCtrlMenuItem);

        calculatorScreenBorderBackgroundColorCtrlMenuItem.setText(resourceMap.getString("calculatorScreenBorderBackgroundColorCtrlMenuItem.text")); // NOI18N
        calculatorScreenBorderBackgroundColorCtrlMenuItem.setName("calculatorScreenBorderBackgroundColorCtrlMenuItem"); // NOI18N
        calculatorScreenBorderBackgroundColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorScreenBorderBackgroundColorCtrlMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(calculatorScreenBorderBackgroundColorCtrlMenuItem);

        commandLineOutputBackgroundColorCtrlMenuItem.setText(resourceMap.getString("commandLineOutputBackgroundColorCtrlMenuItem.text")); // NOI18N
        commandLineOutputBackgroundColorCtrlMenuItem.setName("commandLineOutputBackgroundColorCtrlMenuItem"); // NOI18N
        commandLineOutputBackgroundColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineOutputBackgroundColorCtrlMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(commandLineOutputBackgroundColorCtrlMenuItem);

        commandLineInputLabelTextColorMenuItem.setText(resourceMap.getString("commandLineInputLabelTextColorMenuItem.text")); // NOI18N
        commandLineInputLabelTextColorMenuItem.setName("commandLineInputLabelTextColorMenuItem"); // NOI18N
        commandLineInputLabelTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineInputLabelTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(commandLineInputLabelTextColorMenuItem);

        commandLineOutputLabelTextColorMenuItem.setText(resourceMap.getString("commandLineOutputLabelTextColorMenuItem.text")); // NOI18N
        commandLineOutputLabelTextColorMenuItem.setName("commandLineOutputLabelTextColorMenuItem"); // NOI18N
        commandLineOutputLabelTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineOutputLabelTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(commandLineOutputLabelTextColorMenuItem);

        commandLineOutputTextColorCtrlMenuItem.setText(resourceMap.getString("commandLineOutputTextColorCtrlMenuItem.text")); // NOI18N
        commandLineOutputTextColorCtrlMenuItem.setName("commandLineOutputTextColorCtrlMenuItem"); // NOI18N
        commandLineOutputTextColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineOutputTextColorCtrlMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(commandLineOutputTextColorCtrlMenuItem);

        generalFrameWorkBackgroundColorMenuItem.setText(resourceMap.getString("generalFrameWorkBackgroundColorMenuItem.text")); // NOI18N
        generalFrameWorkBackgroundColorMenuItem.setName("generalFrameWorkBackgroundColorMenuItem"); // NOI18N
        generalFrameWorkBackgroundColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalFrameWorkBackgroundColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(generalFrameWorkBackgroundColorMenuItem);

        evaluatedCommandsScreenBackgroundColorMenuItem.setText(resourceMap.getString("evaluatedCommandsScreenBackgroundColorMenuItem.text")); // NOI18N
        evaluatedCommandsScreenBackgroundColorMenuItem.setName("evaluatedCommandsScreenBackgroundColorMenuItem"); // NOI18N
        evaluatedCommandsScreenBackgroundColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evaluatedCommandsScreenBackgroundColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(evaluatedCommandsScreenBackgroundColorMenuItem);

        evaluatedCommandsScreenTextColorMenuItem.setText(resourceMap.getString("evaluatedCommandsScreenTextColorMenuItem.text")); // NOI18N
        evaluatedCommandsScreenTextColorMenuItem.setName("evaluatedCommandsScreenTextColorMenuItem"); // NOI18N
        evaluatedCommandsScreenTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evaluatedCommandsScreenTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(evaluatedCommandsScreenTextColorMenuItem);

        evaluatedCommandsLabelTextColorMenuItem.setText(resourceMap.getString("evaluatedCommandsLabelTextColorMenuItem.text")); // NOI18N
        evaluatedCommandsLabelTextColorMenuItem.setName("evaluatedCommandsLabelTextColorMenuItem"); // NOI18N
        evaluatedCommandsLabelTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evaluatedCommandsLabelTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(evaluatedCommandsLabelTextColorMenuItem);

        variablesTableBackgroundColorMenuItem.setText(resourceMap.getString("variablesTableBackgroundColorMenuItem.text")); // NOI18N
        variablesTableBackgroundColorMenuItem.setName("variablesTableBackgroundColorMenuItem"); // NOI18N
        variablesTableBackgroundColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variablesTableBackgroundColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(variablesTableBackgroundColorMenuItem);

        variablesTableTextColorMenuItem.setText(resourceMap.getString("variablesTableTextColorMenuItem.text")); // NOI18N
        variablesTableTextColorMenuItem.setName("variablesTableTextColorMenuItem"); // NOI18N
        variablesTableTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variablesTableTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(variablesTableTextColorMenuItem);

        constantsTableBackgroundColorMenuItem.setText(resourceMap.getString("constantsTableBackgroundColorMenuItem.text")); // NOI18N
        constantsTableBackgroundColorMenuItem.setName("constantsTableBackgroundColorMenuItem"); // NOI18N
        constantsTableBackgroundColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constantsTableBackgroundColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(constantsTableBackgroundColorMenuItem);

        constantsTableTextColorMenuItem.setText(resourceMap.getString("constantsTableTextColorMenuItem.text")); // NOI18N
        constantsTableTextColorMenuItem.setName("constantsTableTextColorMenuItem"); // NOI18N
        constantsTableTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constantsTableTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(constantsTableTextColorMenuItem);

        formulaTableBackgroundColorMenuItem.setText(resourceMap.getString("formulaTableBackgroundColorMenuItem.text")); // NOI18N
        formulaTableBackgroundColorMenuItem.setName("formulaTableBackgroundColorMenuItem"); // NOI18N
        formulaTableBackgroundColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formulaTableBackgroundColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(formulaTableBackgroundColorMenuItem);

        formulaTableTextColorMenuItem.setText(resourceMap.getString("formulaTableTextColorMenuItem.text")); // NOI18N
        formulaTableTextColorMenuItem.setName("formulaTableTextColorMenuItem"); // NOI18N
        formulaTableTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formulaTableTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(formulaTableTextColorMenuItem);

        historyTableBackgroundColorMenuItem.setText(resourceMap.getString("historyTableBackgroundColorMenuItem.text")); // NOI18N
        historyTableBackgroundColorMenuItem.setName("historyTableBackgroundColorMenuItem"); // NOI18N
        historyTableBackgroundColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyTableBackgroundColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(historyTableBackgroundColorMenuItem);

        historyTableTextColorMenuItem.setText(resourceMap.getString("historyTableTextColorMenuItem.text")); // NOI18N
        historyTableTextColorMenuItem.setName("historyTableTextColorMenuItem"); // NOI18N
        historyTableTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyTableTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(historyTableTextColorMenuItem);

        parserMessagesTextColorMenuItem.setText(resourceMap.getString("parserMessagesTextColorMenuItem.text")); // NOI18N
        parserMessagesTextColorMenuItem.setName("parserMessagesTextColorMenuItem"); // NOI18N
        parserMessagesTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parserMessagesTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(parserMessagesTextColorMenuItem);

        progressMonitorTextColorMenuItem.setText(resourceMap.getString("progressMonitorTextColorMenuItem.text")); // NOI18N
        progressMonitorTextColorMenuItem.setName("progressMonitorTextColorMenuItem"); // NOI18N
        progressMonitorTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progressMonitorTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(progressMonitorTextColorMenuItem);

        messagingScreenBackgroundColorMenuItem.setText(resourceMap.getString("messagingScreenBackgroundColorMenuItem.text")); // NOI18N
        messagingScreenBackgroundColorMenuItem.setName("messagingScreenBackgroundColorMenuItem"); // NOI18N
        messagingScreenBackgroundColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messagingScreenBackgroundColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(messagingScreenBackgroundColorMenuItem);

        messagingScreenTextColorMenuItem.setText(resourceMap.getString("messagingScreenTextColorMenuItem.text")); // NOI18N
        messagingScreenTextColorMenuItem.setName("messagingScreenTextColorMenuItem"); // NOI18N
        messagingScreenTextColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messagingScreenTextColorMenuItemActionPerformed(evt);
            }
        });
        colorMenu.add(messagingScreenTextColorMenuItem);

        customizerMenu.add(colorMenu);

        fontMenu.setText(resourceMap.getString("fontMenu.text")); // NOI18N
        fontMenu.setName("fontMenu"); // NOI18N

        calculatorScreenFontCtrlMenuItem.setText(resourceMap.getString("calculatorScreenFontCtrlMenuItem.text")); // NOI18N
        calculatorScreenFontCtrlMenuItem.setName("calculatorScreenFontCtrlMenuItem"); // NOI18N
        calculatorScreenFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorScreenFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(calculatorScreenFontCtrlMenuItem);

        commandLineInputScreenFontCtrlMenuItem.setText(resourceMap.getString("commandLineInputScreenFontCtrlMenuItem.text")); // NOI18N
        commandLineInputScreenFontCtrlMenuItem.setName("commandLineInputScreenFontCtrlMenuItem"); // NOI18N
        commandLineInputScreenFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineInputScreenFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(commandLineInputScreenFontCtrlMenuItem);

        commandLineOutputScreenFontCtrlMenuItem.setText(resourceMap.getString("commandLineOutputScreenFontCtrlMenuItem.text")); // NOI18N
        commandLineOutputScreenFontCtrlMenuItem.setName("commandLineOutputScreenFontCtrlMenuItem"); // NOI18N
        commandLineOutputScreenFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineOutputScreenFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(commandLineOutputScreenFontCtrlMenuItem);

        evaluatedCommandsScreenFontCtrlMenuItem.setText(resourceMap.getString("evaluatedCommandsScreenFontCtrlMenuItem.text")); // NOI18N
        evaluatedCommandsScreenFontCtrlMenuItem.setName("evaluatedCommandsScreenFontCtrlMenuItem"); // NOI18N
        evaluatedCommandsScreenFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evaluatedCommandsScreenFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(evaluatedCommandsScreenFontCtrlMenuItem);

        commandLineInputScreenLabelFontCtrlMenuItem.setText(resourceMap.getString("commandLineInputScreenLabelFontCtrlMenuItem.text")); // NOI18N
        commandLineInputScreenLabelFontCtrlMenuItem.setName("commandLineInputScreenLabelFontCtrlMenuItem"); // NOI18N
        commandLineInputScreenLabelFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineInputScreenLabelFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(commandLineInputScreenLabelFontCtrlMenuItem);

        commandLineOutputScreenLabelFontCtrlMenuItem.setText(resourceMap.getString("commandLineOutputScreenLabelFontCtrlMenuItem.text")); // NOI18N
        commandLineOutputScreenLabelFontCtrlMenuItem.setName("commandLineOutputScreenLabelFontCtrlMenuItem"); // NOI18N
        commandLineOutputScreenLabelFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineOutputScreenLabelFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(commandLineOutputScreenLabelFontCtrlMenuItem);

        keyboardSwitchButtonFontCtrlMenuItem.setText(resourceMap.getString("keyboardSwitchButtonFontCtrlMenuItem.text")); // NOI18N
        keyboardSwitchButtonFontCtrlMenuItem.setName("keyboardSwitchButtonFontCtrlMenuItem"); // NOI18N
        keyboardSwitchButtonFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyboardSwitchButtonFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(keyboardSwitchButtonFontCtrlMenuItem);

        variablesTableFontCtrlMenuItem.setText(resourceMap.getString("variablesTableFontCtrlMenuItem.text")); // NOI18N
        variablesTableFontCtrlMenuItem.setName("variablesTableFontCtrlMenuItem"); // NOI18N
        variablesTableFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variablesTableFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(variablesTableFontCtrlMenuItem);

        variablesTableHeaderFontCtrlMenuItem.setText(resourceMap.getString("variablesTableHeaderFontCtrlMenuItem.text")); // NOI18N
        variablesTableHeaderFontCtrlMenuItem.setName("variablesTableHeaderFontCtrlMenuItem"); // NOI18N
        variablesTableHeaderFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variablesTableHeaderFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(variablesTableHeaderFontCtrlMenuItem);

        constantsTableFontCtrlMenuItem.setText(resourceMap.getString("constantsTableFontCtrlMenuItem.text")); // NOI18N
        constantsTableFontCtrlMenuItem.setName("constantsTableFontCtrlMenuItem"); // NOI18N
        constantsTableFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constantsTableFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(constantsTableFontCtrlMenuItem);

        constantsTableHeaderFontCtrlMenuItem.setText(resourceMap.getString("constantsTableHeaderFontCtrlMenuItem.text")); // NOI18N
        constantsTableHeaderFontCtrlMenuItem.setName("constantsTableHeaderFontCtrlMenuItem"); // NOI18N
        constantsTableHeaderFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constantsTableHeaderFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(constantsTableHeaderFontCtrlMenuItem);

        formulaTableFontCtrlMenuItem.setText(resourceMap.getString("formulaTableFontCtrlMenuItem.text")); // NOI18N
        formulaTableFontCtrlMenuItem.setName("formulaTableFontCtrlMenuItem"); // NOI18N
        formulaTableFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formulaTableFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(formulaTableFontCtrlMenuItem);

        formulaTableHeaderFontCtrlMenuItem.setText(resourceMap.getString("formulaTableHeaderFontCtrlMenuItem.text")); // NOI18N
        formulaTableHeaderFontCtrlMenuItem.setName("formulaTableHeaderFontCtrlMenuItem"); // NOI18N
        formulaTableHeaderFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formulaTableHeaderFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(formulaTableHeaderFontCtrlMenuItem);

        historyTableFontCtrlMenuItem.setText(resourceMap.getString("historyTableFontCtrlMenuItem.text")); // NOI18N
        historyTableFontCtrlMenuItem.setName("historyTableFontCtrlMenuItem"); // NOI18N
        historyTableFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyTableFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(historyTableFontCtrlMenuItem);

        historyTableHeaderFontCtrlMenuItem.setText(resourceMap.getString("historyTableHeaderFontCtrlMenuItem.text")); // NOI18N
        historyTableHeaderFontCtrlMenuItem.setName("historyTableHeaderFontCtrlMenuItem"); // NOI18N
        historyTableHeaderFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyTableHeaderFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(historyTableHeaderFontCtrlMenuItem);

        messagingScreenColorCtrlMenuItem.setText(resourceMap.getString("messagingScreenColorCtrlMenuItem.text")); // NOI18N
        messagingScreenColorCtrlMenuItem.setName("messagingScreenColorCtrlMenuItem"); // NOI18N
        messagingScreenColorCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messagingScreenColorCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(messagingScreenColorCtrlMenuItem);

        progressMonitorFontCtrlMenuItem.setText(resourceMap.getString("progressMonitorFontCtrlMenuItem.text")); // NOI18N
        progressMonitorFontCtrlMenuItem.setName("progressMonitorFontCtrlMenuItem"); // NOI18N
        progressMonitorFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progressMonitorFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(progressMonitorFontCtrlMenuItem);

        parserMessagesFontCtrlMenuItem.setText(resourceMap.getString("parserMessagesFontCtrlMenuItem.text")); // NOI18N
        parserMessagesFontCtrlMenuItem.setName("parserMessagesFontCtrlMenuItem"); // NOI18N
        parserMessagesFontCtrlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parserMessagesFontCtrlMenuItemActionPerformed(evt);
            }
        });
        fontMenu.add(parserMessagesFontCtrlMenuItem);

        customizerMenu.add(fontMenu);

        formatMenu.add(customizerMenu);

        jMenuBar1.add(formatMenu);

        tasksMenu.setText(resourceMap.getString("tasksMenu.text")); // NOI18N
        tasksMenu.setName("tasksMenu"); // NOI18N

        lockerMenuItem.setText(resourceMap.getString("lockerMenuItem.text")); // NOI18N
        lockerMenuItem.setName("lockerMenuItem"); // NOI18N
        lockerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lockerMenuItemActionPerformed(evt);
            }
        });
        tasksMenu.add(lockerMenuItem);

        switchModeMenu.setText(resourceMap.getString("switchModeMenu.text")); // NOI18N
        switchModeMenu.setName("switchModeMenu"); // NOI18N

        commandLineModeRadioButtonMenuItem.setText(resourceMap.getString("commandLineModeRadioButtonMenuItem.text")); // NOI18N
        commandLineModeRadioButtonMenuItem.setName("commandLineModeRadioButtonMenuItem"); // NOI18N
        commandLineModeRadioButtonMenuItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                commandLineModeRadioButtonMenuItemItemStateChanged(evt);
            }
        });
        switchModeMenu.add(commandLineModeRadioButtonMenuItem);

        calculatorModeRadioButtonMenuItem.setSelected(true);
        calculatorModeRadioButtonMenuItem.setText(resourceMap.getString("calculatorModeRadioButtonMenuItem.text")); // NOI18N
        calculatorModeRadioButtonMenuItem.setName("calculatorModeRadioButtonMenuItem"); // NOI18N
        calculatorModeRadioButtonMenuItem.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                calculatorModeRadioButtonMenuItemItemStateChanged(evt);
            }
        });
        switchModeMenu.add(calculatorModeRadioButtonMenuItem);

        tasksMenu.add(switchModeMenu);

        jMenu3.setText(resourceMap.getString("jMenu3.text")); // NOI18N
        jMenu3.setName("jMenu3"); // NOI18N

        degsRadioMenuItem.setSelected(true);
        degsRadioMenuItem.setText(resourceMap.getString("degsRadioMenuItem.text")); // NOI18N
        degsRadioMenuItem.setName("degsRadioMenuItem"); // NOI18N
        degsRadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                degsRadioMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(degsRadioMenuItem);

        radsRadioMenuItem.setText(resourceMap.getString("radsRadioMenuItem.text")); // NOI18N
        radsRadioMenuItem.setName("radsRadioMenuItem"); // NOI18N
        radsRadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radsRadioMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(radsRadioMenuItem);

        gradsRadioMenuItem.setText(resourceMap.getString("gradsRadioMenuItem.text")); // NOI18N
        gradsRadioMenuItem.setName("gradsRadioMenuItem"); // NOI18N
        gradsRadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradsRadioMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(gradsRadioMenuItem);

        tasksMenu.add(jMenu3);

        jMenu4.setText(resourceMap.getString("jMenu4.text")); // NOI18N
        jMenu4.setName("jMenu4"); // NOI18N

        calculatorMenuItem.setText(resourceMap.getString("calculatorMenuItem.text")); // NOI18N
        calculatorMenuItem.setName("calculatorMenuItem"); // NOI18N
        calculatorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(calculatorMenuItem);

        simultaneousequationsMenuItem.setText(resourceMap.getString("simultaneousequationsMenuItem.text")); // NOI18N
        simultaneousequationsMenuItem.setName("simultaneousequationsMenuItem"); // NOI18N
        simultaneousequationsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simultaneousequationsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(simultaneousequationsMenuItem);

        quadraticequationsMenuItem.setText(resourceMap.getString("quadraticequationsMenuItem.text")); // NOI18N
        quadraticequationsMenuItem.setName("quadraticequationsMenuItem"); // NOI18N
        quadraticequationsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quadraticequationsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(quadraticequationsMenuItem);

        plottingFunctionsMenuItem.setText(resourceMap.getString("plottingFunctionsMenuItem.text")); // NOI18N
        plottingFunctionsMenuItem.setName("plottingFunctionsMenuItem"); // NOI18N
        plottingFunctionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plottingFunctionsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(plottingFunctionsMenuItem);

        differentialequationsMenuItem.setText(resourceMap.getString("differentialequationsMenuItem.text")); // NOI18N
        differentialequationsMenuItem.setName("differentialequationsMenuItem"); // NOI18N
        differentialequationsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                differentialequationsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(differentialequationsMenuItem);

        tartagliasequationsMenuItem.setText(resourceMap.getString("tartagliasequationsMenuItem.text")); // NOI18N
        tartagliasequationsMenuItem.setName("tartagliasequationsMenuItem"); // NOI18N
        tartagliasequationsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tartagliasequationsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(tartagliasequationsMenuItem);

        statisticsMenuItem.setText(resourceMap.getString("statisticsMenuItem.text")); // NOI18N
        statisticsMenuItem.setName("statisticsMenuItem"); // NOI18N
        statisticsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(statisticsMenuItem);

        rootsOfEquationsMenuItem.setText(resourceMap.getString("rootsOfEquationsMenuItem.text")); // NOI18N
        rootsOfEquationsMenuItem.setName("rootsOfEquationsMenuItem"); // NOI18N
        rootsOfEquationsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rootsOfEquationsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(rootsOfEquationsMenuItem);

        numericalIntegralsMenuItem.setText(resourceMap.getString("numericalIntegralsMenuItem.text")); // NOI18N
        numericalIntegralsMenuItem.setName("numericalIntegralsMenuItem"); // NOI18N
        numericalIntegralsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numericalIntegralsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(numericalIntegralsMenuItem);

        numericalDerivativesMenuItem.setText(resourceMap.getString("numericalDerivativesMenuItem.text")); // NOI18N
        numericalDerivativesMenuItem.setName("numericalDerivativesMenuItem"); // NOI18N
        numericalDerivativesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numericalDerivativesMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(numericalDerivativesMenuItem);

        matrixMultiplicationMenuItem.setText(resourceMap.getString("matrixMultiplicationMenuItem.text")); // NOI18N
        matrixMultiplicationMenuItem.setName("matrixMultiplicationMenuItem"); // NOI18N
        matrixMultiplicationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matrixMultiplicationMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(matrixMultiplicationMenuItem);

        matrixAdditionMenuItem.setText(resourceMap.getString("matrixAdditionMenuItem.text")); // NOI18N
        matrixAdditionMenuItem.setName("matrixAdditionMenuItem"); // NOI18N
        matrixAdditionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matrixAdditionMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(matrixAdditionMenuItem);

        matrixSubtractionMenuItem.setText(resourceMap.getString("matrixSubtractionMenuItem.text")); // NOI18N
        matrixSubtractionMenuItem.setName("matrixSubtractionMenuItem"); // NOI18N
        matrixSubtractionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matrixSubtractionMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(matrixSubtractionMenuItem);

        determinantsMenuItem.setText(resourceMap.getString("determinantsMenuItem.text")); // NOI18N
        determinantsMenuItem.setName("determinantsMenuItem"); // NOI18N
        determinantsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                determinantsMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(determinantsMenuItem);

        triangularMatricesMenuItem.setText(resourceMap.getString("triangularMatricesMenuItem.text")); // NOI18N
        triangularMatricesMenuItem.setName("triangularMatricesMenuItem"); // NOI18N
        triangularMatricesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triangularMatricesMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(triangularMatricesMenuItem);

        baseSystemMenu.setText(resourceMap.getString("baseSystemMenu.text")); // NOI18N
        baseSystemMenu.setName("baseSystemMenu"); // NOI18N

        base2RadioMenuItem.setText(resourceMap.getString("base2RadioMenuItem.text")); // NOI18N
        base2RadioMenuItem.setName("base2RadioMenuItem"); // NOI18N
        base2RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base2RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base2RadioMenuItem);

        base3RadioMenuItem.setText(resourceMap.getString("base3RadioMenuItem.text")); // NOI18N
        base3RadioMenuItem.setName("base3RadioMenuItem"); // NOI18N
        base3RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base3RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base3RadioMenuItem);

        base4RadioMenuItem.setText(resourceMap.getString("base4RadioMenuItem.text")); // NOI18N
        base4RadioMenuItem.setName("base4RadioMenuItem"); // NOI18N
        base4RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base4RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base4RadioMenuItem);

        base5RadioMenuItem.setText(resourceMap.getString("base5RadioMenuItem.text")); // NOI18N
        base5RadioMenuItem.setName("base5RadioMenuItem"); // NOI18N
        base5RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base5RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base5RadioMenuItem);

        base6RadioMenuItem.setText(resourceMap.getString("base6RadioMenuItem.text")); // NOI18N
        base6RadioMenuItem.setName("base6RadioMenuItem"); // NOI18N
        base6RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base6RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base6RadioMenuItem);

        base7RadioMenuItem.setText(resourceMap.getString("base7RadioMenuItem.text")); // NOI18N
        base7RadioMenuItem.setName("base7RadioMenuItem"); // NOI18N
        base7RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base7RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base7RadioMenuItem);

        base8RadioMenuItem.setText(resourceMap.getString("base8RadioMenuItem.text")); // NOI18N
        base8RadioMenuItem.setName("base8RadioMenuItem"); // NOI18N
        base8RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base8RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base8RadioMenuItem);

        base9RadioMenuItem.setText(resourceMap.getString("base9RadioMenuItem.text")); // NOI18N
        base9RadioMenuItem.setName("base9RadioMenuItem"); // NOI18N
        base9RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base9RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base9RadioMenuItem);

        base10RadioMenuItem.setSelected(true);
        base10RadioMenuItem.setText(resourceMap.getString("base10RadioMenuItem.text")); // NOI18N
        base10RadioMenuItem.setName("base10RadioMenuItem"); // NOI18N
        base10RadioMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                base10RadioMenuItemActionPerformed(evt);
            }
        });
        baseSystemMenu.add(base10RadioMenuItem);

        jMenu4.add(baseSystemMenu);

        saveEvaluatedCommandsDocumentMenuItem.setText(resourceMap.getString("saveEvaluatedCommandsDocumentMenuItem.text")); // NOI18N
        saveEvaluatedCommandsDocumentMenuItem.setName("saveEvaluatedCommandsDocumentMenuItem"); // NOI18N
        saveEvaluatedCommandsDocumentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveEvaluatedCommandsDocumentMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(saveEvaluatedCommandsDocumentMenuItem);

        openDocumentMenuItem.setText(resourceMap.getString("openDocumentMenuItem.text")); // NOI18N
        openDocumentMenuItem.setName("openDocumentMenuItem"); // NOI18N
        openDocumentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDocumentMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(openDocumentMenuItem);

        _exitMenuItem.setText(resourceMap.getString("_exitMenuItem.text")); // NOI18N
        _exitMenuItem.setName("_exitMenuItem"); // NOI18N
        _exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _exitMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(_exitMenuItem);

        forceExitMenuItem.setText(resourceMap.getString("forceExitMenuItem.text")); // NOI18N
        forceExitMenuItem.setName("forceExitMenuItem"); // NOI18N
        forceExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forceExitMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(forceExitMenuItem);

        setColorMenuItem.setText(resourceMap.getString("setColorMenuItem.text")); // NOI18N
        setColorMenuItem.setName("setColorMenuItem"); // NOI18N
        setColorMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setColorMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(setColorMenuItem);

        storeVariableMenuItem.setText(resourceMap.getString("storeVariableMenuItem.text")); // NOI18N
        storeVariableMenuItem.setName("storeVariableMenuItem"); // NOI18N
        storeVariableMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeVariableMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(storeVariableMenuItem);

        storeConstantMenuItem.setText(resourceMap.getString("storeConstantMenuItem.text")); // NOI18N
        storeConstantMenuItem.setName("storeConstantMenuItem"); // NOI18N
        storeConstantMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeConstantMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(storeConstantMenuItem);

        makeFormulaMenuItem.setText(resourceMap.getString("makeFormulaMenuItem.text")); // NOI18N
        makeFormulaMenuItem.setName("makeFormulaMenuItem"); // NOI18N
        makeFormulaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeFormulaMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(makeFormulaMenuItem);

        storeFormulaMenuItem.setText(resourceMap.getString("storeFormulaMenuItem.text")); // NOI18N
        storeFormulaMenuItem.setName("storeFormulaMenuItem"); // NOI18N
        storeFormulaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeFormulaMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(storeFormulaMenuItem);

        showTimeMenuItem.setText(resourceMap.getString("showTimeMenuItem.text")); // NOI18N
        showTimeMenuItem.setName("showTimeMenuItem"); // NOI18N
        showTimeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTimeMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(showTimeMenuItem);

        tasksMenu.add(jMenu4);

        clearMenu.setText(resourceMap.getString("clearMenu.text")); // NOI18N
        clearMenu.setName("clearMenu"); // NOI18N

        commandLineInputclearMenuItem.setText(resourceMap.getString("commandLineInputclearMenuItem.text")); // NOI18N
        commandLineInputclearMenuItem.setName("commandLineInputclearMenuItem"); // NOI18N
        commandLineInputclearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineInputclearMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(commandLineInputclearMenuItem);

        commandLineOutputclearMenuItem.setText(resourceMap.getString("commandLineOutputclearMenuItem.text")); // NOI18N
        commandLineOutputclearMenuItem.setName("commandLineOutputclearMenuItem"); // NOI18N
        commandLineOutputclearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandLineOutputclearMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(commandLineOutputclearMenuItem);

        evaluatedCommandsclearMenuItem.setText(resourceMap.getString("evaluatedCommandsclearMenuItem.text")); // NOI18N
        evaluatedCommandsclearMenuItem.setName("evaluatedCommandsclearMenuItem"); // NOI18N
        evaluatedCommandsclearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evaluatedCommandsclearMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(evaluatedCommandsclearMenuItem);

        calculatorScreenclearMenuItem.setText(resourceMap.getString("calculatorScreenclearMenuItem.text")); // NOI18N
        calculatorScreenclearMenuItem.setName("calculatorScreenclearMenuItem"); // NOI18N
        calculatorScreenclearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculatorScreenclearMenuItemActionPerformed(evt);
            }
        });
        clearMenu.add(calculatorScreenclearMenuItem);

        tasksMenu.add(clearMenu);

        jMenuBar1.add(tasksMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        helpMenuItem.setText(resourceMap.getString("helpMenuItem.text")); // NOI18N
        helpMenuItem.setName("helpMenuItem"); // NOI18N
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpMenuItem);

        abooutMenuItem.setText(resourceMap.getString("abooutMenuItem.text")); // NOI18N
        abooutMenuItem.setName("abooutMenuItem"); // NOI18N
        abooutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abooutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(abooutMenuItem);

        creditsMenuItem.setText(resourceMap.getString("creditsMenuItem.text")); // NOI18N
        creditsMenuItem.setName("creditsMenuItem"); // NOI18N
        creditsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditsMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(creditsMenuItem);

        chatMenu.setText(resourceMap.getString("chatMenu.text")); // NOI18N
        chatMenu.setName("chatMenu"); // NOI18N

        connectToRadioMenuItem.setSelected(true);
        connectToRadioMenuItem.setText(resourceMap.getString("connectToRadioMenuItem.text")); // NOI18N
        connectToRadioMenuItem.setName("connectToRadioMenuItem"); // NOI18N
        chatMenu.add(connectToRadioMenuItem);

        getConnectionFromRadioMenuItem.setText(resourceMap.getString("getConnectionFromRadioMenuItem.text")); // NOI18N
        getConnectionFromRadioMenuItem.setName("getConnectionFromRadioMenuItem"); // NOI18N
        chatMenu.add(getConnectionFromRadioMenuItem);

        helpMenu.add(chatMenu);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1272, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void commandLineInputBackgroundColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineInputBackgroundColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineInputBackgroundColorCtrlMenuItemActionPerformed

    private void commandLineInputTextColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineInputTextColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineInputTextColorCtrlMenuItemActionPerformed

    private void calculatorScreenBackgroundColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorScreenBackgroundColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_calculatorScreenBackgroundColorCtrlMenuItemActionPerformed

    private void calculatorScreenTextColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorScreenTextColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_calculatorScreenTextColorCtrlMenuItemActionPerformed

    private void calculatorScreenBorderBackgroundColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorScreenBorderBackgroundColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_calculatorScreenBorderBackgroundColorCtrlMenuItemActionPerformed

    private void commandLineOutputBackgroundColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineOutputBackgroundColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineOutputBackgroundColorCtrlMenuItemActionPerformed

    private void commandLineInputLabelTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineInputLabelTextColorMenuItemActionPerformed

}//GEN-LAST:event_commandLineInputLabelTextColorMenuItemActionPerformed

    private void commandLineOutputLabelTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineOutputLabelTextColorMenuItemActionPerformed

}//GEN-LAST:event_commandLineOutputLabelTextColorMenuItemActionPerformed

    private void commandLineOutputTextColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineOutputTextColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineOutputTextColorCtrlMenuItemActionPerformed

    private void generalFrameWorkBackgroundColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalFrameWorkBackgroundColorMenuItemActionPerformed

}//GEN-LAST:event_generalFrameWorkBackgroundColorMenuItemActionPerformed

    private void evaluatedCommandsScreenBackgroundColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evaluatedCommandsScreenBackgroundColorMenuItemActionPerformed

}//GEN-LAST:event_evaluatedCommandsScreenBackgroundColorMenuItemActionPerformed

    private void evaluatedCommandsScreenTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evaluatedCommandsScreenTextColorMenuItemActionPerformed

}//GEN-LAST:event_evaluatedCommandsScreenTextColorMenuItemActionPerformed

    private void evaluatedCommandsLabelTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evaluatedCommandsLabelTextColorMenuItemActionPerformed

}//GEN-LAST:event_evaluatedCommandsLabelTextColorMenuItemActionPerformed

    private void variablesTableBackgroundColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variablesTableBackgroundColorMenuItemActionPerformed

}//GEN-LAST:event_variablesTableBackgroundColorMenuItemActionPerformed

    private void variablesTableTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variablesTableTextColorMenuItemActionPerformed

}//GEN-LAST:event_variablesTableTextColorMenuItemActionPerformed

    private void constantsTableBackgroundColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constantsTableBackgroundColorMenuItemActionPerformed

}//GEN-LAST:event_constantsTableBackgroundColorMenuItemActionPerformed

    private void constantsTableTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constantsTableTextColorMenuItemActionPerformed

}//GEN-LAST:event_constantsTableTextColorMenuItemActionPerformed

    private void formulaTableBackgroundColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formulaTableBackgroundColorMenuItemActionPerformed

}//GEN-LAST:event_formulaTableBackgroundColorMenuItemActionPerformed

    private void formulaTableTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formulaTableTextColorMenuItemActionPerformed

}//GEN-LAST:event_formulaTableTextColorMenuItemActionPerformed

    private void historyTableBackgroundColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyTableBackgroundColorMenuItemActionPerformed

}//GEN-LAST:event_historyTableBackgroundColorMenuItemActionPerformed

    private void historyTableTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyTableTextColorMenuItemActionPerformed

}//GEN-LAST:event_historyTableTextColorMenuItemActionPerformed

    private void parserMessagesTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parserMessagesTextColorMenuItemActionPerformed

}//GEN-LAST:event_parserMessagesTextColorMenuItemActionPerformed

    private void progressMonitorTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progressMonitorTextColorMenuItemActionPerformed

}//GEN-LAST:event_progressMonitorTextColorMenuItemActionPerformed

    private void messagingScreenBackgroundColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messagingScreenBackgroundColorMenuItemActionPerformed

}//GEN-LAST:event_messagingScreenBackgroundColorMenuItemActionPerformed

    private void messagingScreenTextColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messagingScreenTextColorMenuItemActionPerformed

}//GEN-LAST:event_messagingScreenTextColorMenuItemActionPerformed

    private void calculatorScreenFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorScreenFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_calculatorScreenFontCtrlMenuItemActionPerformed

    private void commandLineInputScreenFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineInputScreenFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineInputScreenFontCtrlMenuItemActionPerformed

    private void commandLineOutputScreenFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineOutputScreenFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineOutputScreenFontCtrlMenuItemActionPerformed

    private void evaluatedCommandsScreenFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evaluatedCommandsScreenFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_evaluatedCommandsScreenFontCtrlMenuItemActionPerformed

    private void commandLineInputScreenLabelFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineInputScreenLabelFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineInputScreenLabelFontCtrlMenuItemActionPerformed

    private void commandLineOutputScreenLabelFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineOutputScreenLabelFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_commandLineOutputScreenLabelFontCtrlMenuItemActionPerformed

    private void keyboardSwitchButtonFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyboardSwitchButtonFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_keyboardSwitchButtonFontCtrlMenuItemActionPerformed

    private void variablesTableFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variablesTableFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_variablesTableFontCtrlMenuItemActionPerformed

    private void variablesTableHeaderFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variablesTableHeaderFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_variablesTableHeaderFontCtrlMenuItemActionPerformed

    private void constantsTableFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constantsTableFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_constantsTableFontCtrlMenuItemActionPerformed

    private void constantsTableHeaderFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constantsTableHeaderFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_constantsTableHeaderFontCtrlMenuItemActionPerformed

    private void formulaTableFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formulaTableFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_formulaTableFontCtrlMenuItemActionPerformed

    private void formulaTableHeaderFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formulaTableHeaderFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_formulaTableHeaderFontCtrlMenuItemActionPerformed

    private void historyTableFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyTableFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_historyTableFontCtrlMenuItemActionPerformed

    private void historyTableHeaderFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyTableHeaderFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_historyTableHeaderFontCtrlMenuItemActionPerformed

    private void messagingScreenColorCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messagingScreenColorCtrlMenuItemActionPerformed

}//GEN-LAST:event_messagingScreenColorCtrlMenuItemActionPerformed

    private void progressMonitorFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progressMonitorFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_progressMonitorFontCtrlMenuItemActionPerformed

    private void parserMessagesFontCtrlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parserMessagesFontCtrlMenuItemActionPerformed

}//GEN-LAST:event_parserMessagesFontCtrlMenuItemActionPerformed

    private void lockerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lockerMenuItemActionPerformed

}//GEN-LAST:event_lockerMenuItemActionPerformed

    private void commandLineModeRadioButtonMenuItemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_commandLineModeRadioButtonMenuItemItemStateChanged
        if (commandLineModeRadioButtonMenuItem.isSelected()) {
            setMode(Mode.COMMAND);
            calculatorScreen.setEnabled(false);
            keyBoardCtrl.setEnabled(false);
            commandLineInput.setEditable(true);
            if (keyboard != null && keyboard.isVisible()) {
                keyboard.setVisible(false);
                keyboard.window.setVisible(false);
            }
        }
        commandLineTextActivator(commandLineModeRadioButtonMenuItem.isSelected());
}//GEN-LAST:event_commandLineModeRadioButtonMenuItemItemStateChanged

    private void calculatorModeRadioButtonMenuItemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_calculatorModeRadioButtonMenuItemItemStateChanged
        if (calculatorModeRadioButtonMenuItem.isSelected()) {
            setMode(Mode.CALCULATOR);
            calculatorScreen.setEnabled(true);
            keyBoardCtrl.setEnabled(true);
            commandLineInput.setEditable(false);
            evaluatedCommands.setEditable(false);
            commandLineTextActivator(commandLineModeRadioButtonMenuItem.isSelected());
        }
}//GEN-LAST:event_calculatorModeRadioButtonMenuItemItemStateChanged

    private void degsRadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_degsRadioMenuItemActionPerformed
        getKeyboard().setDRGStatus(0);
        mathMan.setDrgStatus(0);
}//GEN-LAST:event_degsRadioMenuItemActionPerformed

    private void radsRadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radsRadioMenuItemActionPerformed
        getKeyboard().setDRGStatus(1);
        mathMan.setDrgStatus(1);
}//GEN-LAST:event_radsRadioMenuItemActionPerformed

    private void gradsRadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradsRadioMenuItemActionPerformed
        getKeyboard().setDRGStatus(2);
        mathMan.setDrgStatus(2);
}//GEN-LAST:event_gradsRadioMenuItemActionPerformed

    private void simultaneousequationsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simultaneousequationsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.SIMULTANEOUS_EQUATIONS + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_simultaneousequationsMenuItemActionPerformed

    private void quadraticequationsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quadraticequationsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.QUADRATIC_EQUATIONS + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_quadraticequationsMenuItemActionPerformed

    private void plottingFunctionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plottingFunctionsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.PLOT + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_plottingFunctionsMenuItemActionPerformed

    private void differentialequationsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_differentialequationsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.DIFFERENTIAL_EQUATIONS + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_differentialequationsMenuItemActionPerformed

    private void tartagliasequationsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tartagliasequationsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.TARTAGLIA_EQUATIONS + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_tartagliasequationsMenuItemActionPerformed

    private void statisticsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.STATISTICS + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_statisticsMenuItemActionPerformed

    private void rootsOfEquationsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rootsOfEquationsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.ROOT_OF_EQUATION + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_rootsOfEquationsMenuItemActionPerformed

    private void numericalIntegralsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numericalIntegralsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.NUMERICAL_INTEGRATION + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_numericalIntegralsMenuItemActionPerformed

    private void numericalDerivativesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numericalDerivativesMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.NUMERICAL_DIFFERENTIATION + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_numericalDerivativesMenuItemActionPerformed

    private void matrixMultiplicationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matrixMultiplicationMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.MATRIX_MULTIPLICATION + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_matrixMultiplicationMenuItemActionPerformed

    private void matrixAdditionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matrixAdditionMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.MATRIX_ADDITION + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_matrixAdditionMenuItemActionPerformed

    private void matrixSubtractionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matrixSubtractionMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.MATRIX_SUBTRACTION + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_matrixSubtractionMenuItemActionPerformed

    private void determinantsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_determinantsMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.DETERMINANT + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_determinantsMenuItemActionPerformed

    private void triangularMatricesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triangularMatricesMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.TRIANGULAR_MATRIX + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_triangularMatricesMenuItemActionPerformed

    private void base2RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base2RadioMenuItemActionPerformed

}//GEN-LAST:event_base2RadioMenuItemActionPerformed

    private void base3RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base3RadioMenuItemActionPerformed

}//GEN-LAST:event_base3RadioMenuItemActionPerformed

    private void base4RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base4RadioMenuItemActionPerformed

}//GEN-LAST:event_base4RadioMenuItemActionPerformed

    private void base5RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base5RadioMenuItemActionPerformed

}//GEN-LAST:event_base5RadioMenuItemActionPerformed

    private void base6RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base6RadioMenuItemActionPerformed

}//GEN-LAST:event_base6RadioMenuItemActionPerformed

    private void base7RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base7RadioMenuItemActionPerformed

}//GEN-LAST:event_base7RadioMenuItemActionPerformed

    private void base8RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base8RadioMenuItemActionPerformed

}//GEN-LAST:event_base8RadioMenuItemActionPerformed

    private void base9RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base9RadioMenuItemActionPerformed

}//GEN-LAST:event_base9RadioMenuItemActionPerformed

    private void base10RadioMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_base10RadioMenuItemActionPerformed

}//GEN-LAST:event_base10RadioMenuItemActionPerformed

    private void saveEvaluatedCommandsDocumentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveEvaluatedCommandsDocumentMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.SAVE_DOCUMENT + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_saveEvaluatedCommandsDocumentMenuItemActionPerformed

    private void openDocumentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDocumentMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.OPEN_DOCUMENT + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_openDocumentMenuItemActionPerformed

    private void _exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__exitMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.EXIT + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event__exitMenuItemActionPerformed

    private void forceExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forceExitMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.FORCE_EXIT + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_forceExitMenuItemActionPerformed

    private void setColorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setColorMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.SET_COLOR + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_setColorMenuItemActionPerformed

    private void storeVariableMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeVariableMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.STORE_VARIABLE + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_storeVariableMenuItemActionPerformed

    private void storeConstantMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeConstantMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.STORE_CONSTANT + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_storeConstantMenuItemActionPerformed

    private void makeFormulaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeFormulaMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.MAKE_FORMULA + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_makeFormulaMenuItemActionPerformed

    private void storeFormulaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeFormulaMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.STORE_FORMULA + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_storeFormulaMenuItemActionPerformed

    private void showTimeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showTimeMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText(State.TIMEQUERY + "{\n\n"
                    + "}");
        }
}//GEN-LAST:event_showTimeMenuItemActionPerformed

    private void commandLineInputclearMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineInputclearMenuItemActionPerformed
        commandLineInput.setText("");
}//GEN-LAST:event_commandLineInputclearMenuItemActionPerformed

    private void commandLineOutputclearMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandLineOutputclearMenuItemActionPerformed
        commandLineOutput.setText("");
}//GEN-LAST:event_commandLineOutputclearMenuItemActionPerformed

    private void evaluatedCommandsclearMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evaluatedCommandsclearMenuItemActionPerformed
        evaluatedCommands.setText("");
}//GEN-LAST:event_evaluatedCommandsclearMenuItemActionPerformed

    private void calculatorScreenclearMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorScreenclearMenuItemActionPerformed
        calculatorScreen.setText("");
}//GEN-LAST:event_calculatorScreenclearMenuItemActionPerformed

    private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
        File file = new File("src/help/Help.html");
        new HelpLoader(file.toURI().toString());
}//GEN-LAST:event_helpMenuItemActionPerformed

    private void abooutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abooutMenuItemActionPerformed

}//GEN-LAST:event_abooutMenuItemActionPerformed

    private void creditsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditsMenuItemActionPerformed

}//GEN-LAST:event_creditsMenuItemActionPerformed

    private void calculatorScreenCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_calculatorScreenCaretUpdate
        expr = calculatorScreen.getText();
        KeyPad.expr = expr;
}//GEN-LAST:event_calculatorScreenCaretUpdate

    private void calculatorScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorScreenActionPerformed

        new Thread(new Runnable() {

            @Override
            public void run() {
                manager.execute(new Task(calculatorScreen.getText()));
            }
        }).start();
}//GEN-LAST:event_calculatorScreenActionPerformed

    private void commandLineInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_commandLineInputKeyPressed
        String keyText = KeyEvent.getKeyText(evt.getKeyCode()).toLowerCase();
        if (keyText.equalsIgnoreCase("escape")) {
            manager.execute(new Task(commandLineInput.getText()));
            evaluatedCommands.append(commandLineInput.getText() + "\n");
            //commandLineInput.setText("");
        }
}//GEN-LAST:event_commandLineInputKeyPressed

    private void keyBoardCtrlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyBoardCtrlActionPerformed

        if (keyboard.window.isVisible()) {
            keyboard.window.setVisible(false);
            keyBoardCtrl.setText("SWITCH KEYBOARD ON");
        }//end if
        else if (!keyboard.window.isVisible()) {
            keyboard.window.setVisible(true);
            keyboard.setVisible(true);

//keyboard.window.setLocation(100,600);
            keyBoardCtrl.setText("SWITCH KEYBOARD OFF");
        }//end else if

}//GEN-LAST:event_keyBoardCtrlActionPerformed

    private void varTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_varTableMouseClicked
        if (!evt.isMetaDown() && varTable.getSelectedColumn() != 0) {

            final int row = varTable.getSelectedRow();
            final JOptionPane pane = new JOptionPane("Create Or Update Your Variable.\n"
                    + "Use The Format..\'varName = expr\'",
                    JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
            pane.setWantsInput(true);

            pane.setInitialSelectionValue(varTable.getValueAt(row, 1) + "=" + varTable.getValueAt(row, 2));
            JDialog dialog = pane.createDialog("Variable Creator And Updater.");
            dialog.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            dialog.setVisible(true);

            dialog.addWindowListener(new WindowAdapter() {
//Set Values In Table As Relevant When User Presses Close Button To Close Dialog
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                manager.execute(new Task(pane.getInputValue().toString()));
                            }
                        }).start();
                    }
                }

            });
//Set Values In Table As Relevant When User Presses Ok or Cancel Button To Close Dialog
            if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        manager.execute(new Task(pane.getInputValue().toString()));
                    }
                }).start();
            }//end if
            else {
                varTable.setValueAt("", row, 1);
            }//end else

        }//end if

    }//GEN-LAST:event_varTableMouseClicked

    private void constantsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_constantsTableMouseClicked
        if (!evt.isMetaDown() && constantsTable.getSelectedColumn() != 0) {

            final int row = constantsTable.getSelectedRow();
            final JOptionPane pane = new JOptionPane("Create Or Update Your Variable.\n"
                    + "Use The Format..\'const:constantName = expr\'",
                    JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
            pane.setWantsInput(true);

            pane.setInitialSelectionValue(constantsTable.getValueAt(row, 1) + "=" + constantsTable.getValueAt(row, 2));
            JDialog dialog = pane.createDialog("Constants Creator..");
            dialog.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            dialog.setVisible(true);

            dialog.addWindowListener(new WindowAdapter() {
//Set Values In Table As Relevant When User Presses Close Button To Close Dialog
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);

                    final String inputValue = pane.getInputValue().toString();
                    new Thread(new Runnable() {

                        public void run() {
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    if (inputValue.substring(0, 6).equals("const:")) {
                                        manager.execute(new Task(inputValue));
                                    }// end if
                                    else {
                                        JOptionPane.showMessageDialog(null, "CONSTANT CREATION SYNTAX ERROR.\n"
                                                + "PLEASE CONSULT THE HELP FILE FOR VALID SYNTAX FOR CONSTANT CREATION.", "SYNTAX ERROR",
                                                JOptionPane.ERROR_MESSAGE);
                                    }//end else
                                }//end try
                                catch (IndexOutOfBoundsException indexErr) {
                                    indexErr.printStackTrace();
                                } catch (NullPointerException nolian) {
                                    nolian.printStackTrace();
                                }
                            });

                        }//end run
                    }//end Runnable
                    ).start();

                }
            });
//Set Values In Table As Relevant When User Presses Ok or Cancel Button To Close Dialog
            if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {

                final String inputValue = pane.getInputValue().toString();
                new Thread(new Runnable() {
                    public void run() {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                if (inputValue.substring(0, 6).equals("const:")) {
                                    manager.execute(new Task(inputValue));
                                }// end if
                                else {
                                    JOptionPane.showMessageDialog(null, "CONSTANT CREATION SYNTAX ERROR.\n"
                                            + "PLEASE CONSULT THE HELP FILE FOR VALID SYNTAX FOR CONSTANT CREATION.", "SYNTAX ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                }//end else
                            }//end try
                            catch (IndexOutOfBoundsException indexErr) {

                            } catch (NullPointerException nolian) {

                            }

                        });

                    }//end run
                }//end Runnable
                ).start();
            }//end if
            else {
                constantsTable.setValueAt("", row, 1);
            }//end else

        }//end if

    }//GEN-LAST:event_constantsTableMouseClicked

    private void formTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formTableMouseClicked

        if (!evt.isMetaDown() && formTable.getSelectedColumn() != 0) {

            final int row = formTable.getSelectedRow();
            final JOptionPane pane = new JOptionPane("Create Or Update A Formula.",
                    JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
            pane.setWantsInput(true);

            pane.setInitialSelectionValue(formTable.getValueAt(row, 1) + "=" + formTable.getValueAt(row, 2));
            JDialog dialog = pane.createDialog("Constants Creator..");
            dialog.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            dialog.setVisible(true);

            dialog.addWindowListener(new WindowAdapter() {
//Set Values In Table As Relevant When User Presses Close Button To Close Dialog
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    final String inputValue = pane.getInputValue().toString();
                    new Thread(() -> {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                if (inputValue.substring(0, 6).equals("const:")) {
                                    manager.execute(new Task(inputValue));
                                }// end if
                                else {
                                    JOptionPane.showMessageDialog(null, "CONSTANT CREATION SYNTAX ERROR.\n"
                                            + "PLEASE CONSULT THE HELP FILE FOR VALID SYNTAX FOR CONSTANT CREATION.", "SYNTAX ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                }//end else
                            }//end try
                            catch (IndexOutOfBoundsException indexErr) {

                            } catch (NullPointerException nolian) {

                            }
                        });
                    } //end run
                    //end Runnable
                    ).start();

                }
            });
//Set Values In Table As Relevant When User Presses Ok or Cancel Button To Close Dialog
            if (pane.getInputValue() != null && pane.getInputValue().toString().length() != 0) {
                // Assuming this is triggered by an ActionEvent (already on the EDT)
                final String inputValue = pane.getInputValue().toString(); // Read on EDT

                new Thread(() -> {
                    try {
                        if (inputValue.length() >= 6 && inputValue.startsWith("const:")) {
                            // Heavy logic stays on background thread
                            manager.execute(new Task(inputValue));
                        } else {
                            // UI Update moves back to the EDT
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(null, "CONSTANT CREATION SYNTAX ERROR.\n"
                                        + "PLEASE CONSULT THE HELP FILE FOR VALID SYNTAX FOR CONSTANT CREATION.", "SYNTAX ERROR",
                                        JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace(); // Never leave catch blocks empty!
                    }
                }).start();

            }//end if
            else {
                formTable.setValueAt("", row, 1);
            }//end else

        }//end if


    }//GEN-LAST:event_formTableMouseClicked

    private void historyTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyTableMouseClicked

    }//GEN-LAST:event_historyTableMouseClicked

    private void matrixTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_matrixTableMouseClicked

    }//GEN-LAST:event_matrixTableMouseClicked
    /**
     * Call method when mouse enters the JComponent object
     *
     * @param comp The JComponent to be modified
     */
    public void alterComponent(JComponent comp) {
        Font font = comp.getFont();
        comp.setFont(new Font(font.getFamily(), Font.BOLD + Font.ITALIC, font.getSize() + 5));
        if (comp instanceof JLabel) {
            comp.setForeground(Color.ORANGE);
        } else if (comp instanceof JTable) {
            comp.setForeground(Color.RED);
        }
        comp.setBorder(new BevelBorder(BevelBorder.RAISED));
    }

    /**
     * Call method when mouse exits the JComponent object
     *
     * @param comp The JComponent to be modified
     */
    public void resetComponent(JComponent comp) {
        Font font = comp.getFont();
        comp.setFont(new Font(font.getFamily(), Font.PLAIN, font.getSize() - 5));
        if (comp instanceof JLabel) {
            comp.setForeground(Color.WHITE);
        }
        if (comp == commandLineLabel && commandLineModeRadioButtonMenuItem.isSelected()) {
            comp.setForeground(Color.RED);
        }
        if (comp == commandLineLabel && !commandLineModeRadioButtonMenuItem.isSelected()) {
            comp.setForeground(Color.WHITE);
        } else if (comp instanceof JTable) {
            comp.setForeground(Color.BLACK);
        }

        comp.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
    }

    private void commandLineHistoryLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commandLineHistoryLabelMouseEntered
        alterComponent(commandLineHistoryLabel);
    }//GEN-LAST:event_commandLineHistoryLabelMouseEntered

    private void commandLineHistoryLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commandLineHistoryLabelMouseExited
        resetComponent(commandLineHistoryLabel);
    }//GEN-LAST:event_commandLineHistoryLabelMouseExited

    private void resultLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultLabelMouseEntered
        alterComponent(resultLabel);
    }//GEN-LAST:event_resultLabelMouseEntered

    private void resultLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultLabelMouseExited
        resetComponent(resultLabel);
    }//GEN-LAST:event_resultLabelMouseExited

    private void varTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_varTableMouseEntered
        alterComponent(varTable);
        alterComponent(varTable.getTableHeader());
    }//GEN-LAST:event_varTableMouseEntered

    private void varTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_varTableMouseExited
        resetComponent(varTable);
        resetComponent(varTable.getTableHeader());
    }//GEN-LAST:event_varTableMouseExited

    private void constantsTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_constantsTableMouseEntered
        alterComponent(constantsTable);
        alterComponent(constantsTable.getTableHeader());
    }//GEN-LAST:event_constantsTableMouseEntered

    private void constantsTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_constantsTableMouseExited
        resetComponent(constantsTable);
        resetComponent(constantsTable.getTableHeader());
    }//GEN-LAST:event_constantsTableMouseExited

    private void formTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formTableMouseEntered
        alterComponent(formTable);
        alterComponent(formTable.getTableHeader());
    }//GEN-LAST:event_formTableMouseEntered

    private void formTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formTableMouseExited
        resetComponent(formTable);
        resetComponent(formTable.getTableHeader());
    }//GEN-LAST:event_formTableMouseExited

    private void historyTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyTableMouseEntered
        alterComponent(historyTable);
        alterComponent(historyTable.getTableHeader());
    }//GEN-LAST:event_historyTableMouseEntered

    private void historyTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyTableMouseExited
        resetComponent(historyTable);
        resetComponent(historyTable.getTableHeader());
    }//GEN-LAST:event_historyTableMouseExited

    private void matrixTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_matrixTableMouseEntered
        alterComponent(matrixTable);
        alterComponent(matrixTable.getTableHeader());
    }//GEN-LAST:event_matrixTableMouseEntered

    private void matrixTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_matrixTableMouseExited
        resetComponent(matrixTable);
        resetComponent(matrixTable.getTableHeader());
    }//GEN-LAST:event_matrixTableMouseExited

    private void commandLineLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commandLineLabelMouseEntered
        alterComponent(commandLineLabel);
    }//GEN-LAST:event_commandLineLabelMouseEntered

    private void commandLineLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commandLineLabelMouseExited
        resetComponent(commandLineLabel);
    }//GEN-LAST:event_commandLineLabelMouseExited

    private void solveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solveButtonActionPerformed
        manager.execute(new Task(commandLineInput.getText()));
        evaluatedCommands.append("\n" + commandLineInput.getText());
        commandLineInput.setText("");
    }//GEN-LAST:event_solveButtonActionPerformed

    private void calculatorMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculatorMenuItemActionPerformed
        if (commandLineInput.isEditable() && commandLineInput.isEnabled()) {
            commandLineInput.setText("CALCULATOR{\n\n"
                    + "}");
        }

    }//GEN-LAST:event_calculatorMenuItemActionPerformed

    private void funcTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_funcTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_funcTableMouseClicked

    private void funcTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_funcTableMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_funcTableMouseEntered

    private void funcTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_funcTableMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_funcTableMouseExited

    /**
     * Convenience method used to reference the JTables used on this form.
     *
     * @return an array of the JTables used on this form. This is the order:
     * {varTable,constantsTable,formTable,historyTable,matrixTable}
     */
    public JTable[] allTables() {
        return new JTable[]{varTable, constantsTable, formTable, historyTable, matrixTable};
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NaijaCalc().setVisible(true);
            }
        });
    }//end main method

    /**
     * This class must be made a private inner class to NaijaCalc at the end of
     * coding
     *
     * All methods employed in executing tasks fed into the calculator through
     * the command line or input field are executed here.
     *
     * @author GBEMIRO
     */
    class Task {

        private String taskRequest = "";// a command within which is encoded the task to be performed.

        public Task() {
            if (list == null) {
                list = new WindowList(NaijaCalc.this, new Vector<>());
            }
        }

        public Task(String taskRequest) {
            this.taskRequest = taskRequest;
        }

        public void manageLists(MouseEvent evt) {
            if (evt.getSource() == varTable) {

                if (evt.isMetaDown()) {
                    Vector<String> listVect = new Vector<String>();
                    listVect.add("View/Edit");
                    listVect.add("Delete Row");
                    MessageFlash flash = new MessageFlash(NaijaCalc.this, evt);
                    list.setData(listVect, flash, evt);
                }//end if

            } else if (evt.getSource() == constantsTable) {

            } else if (evt.getSource() == formTable) {

            } else if (evt.getSource() == historyTable) {

            }

        }

        public void storeFormulae() {
            //command is likely to be a multi-variable initialization command
            String command = taskRequest;
            formMan.recordFormula(command);
            formMan.writeFormulaeToTable(formTable);
        }

        public void eval() {
            try {
                String command = taskRequest;
                mathMan.setDrgStatus(getKeyboard().drgStatus());
                String soln = mathMan.solve(command);
                SwingUtilities.invokeLater(() -> {
                    commandLineOutput.setText("OUTPUT>>>>>>>>>\n"
                            + "_______________\n" + soln + "\n\n");
                    histMan.writeHistoryToTable(expr, historyTable);
                    mathMan.getVariableManager().writeVariablesToTable(varTable, constantsTable);
                });

            }//end try
            catch (NullPointerException nolEx) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "User Input Error!");
                    histMan.writeHistoryToTable(expr, historyTable);
                    mathMan.getVariableManager().writeVariablesToTable(varTable, constantsTable);
                });
                nolEx.printStackTrace();
            }
        }//end method eval

        public void shutDown() {
            //perform necessary tasks and shutdown
            System.exit(0);
        }

        /**
         * Runs operations on the command line.
         */
        public void commandLineManager() {
            String command = taskRequest;
            CommandParser parser = new CommandParser(command);
            parser.publishResults(commandLineOutput);
        }

    }//end class Task

    /**
     * Objects of this class will control the running modes of the calculator.
     * And so enable the user to use a single button such as the equals button
     * to act on many kinds of input. e.g calculate,integrate,solve
     * equations,store objects and so on. This class will be involved with the
     * different threads that may be spawned by the software in future.
     *
     * @author GBEMIRO
     */
    class OperatingSystem {

        ArrayList<String> scanner = new ArrayList<String>();
        private Task task = new Task();//task executor

        /**
         *
         * @return the Task object
         */
        public Task getTask() {
            return task;
        }

        /**
         *
         * @param task sets the Task object
         */
        public void setTask(Task task) {
            this.task = task;
        }

        /**
         *
         * @param evt the event to process
         */
        public void execute(AWTEvent evt) {

        }

        /**
         * method responsible for recognizing commands entered into the command
         * line or text field and executing them.
         *
         * @param command the command to be executed.
         */
        public void execute(final Task task) {
            try {
                String request = task.taskRequest;

                String question = request;
                String funcStart = "";
                String trigger = "";

                if (mode.isCalculator()) {

                    try {
                        funcStart = question.substring(0, question.indexOf("="));
                        scanner = new MathScanner(purifier(task.taskRequest)).scanner();
                    }//end try
                    catch (IndexOutOfBoundsException boundsException) {
                    }//end catch
                    if (!scanner.isEmpty()) {
                        trigger = scanner.get(0);
                    }
                    if (Method.isMethodName(funcStart) && question.contains("@") && question.contains("=")) {
                        FunctionManager.add(question);
                        SwingUtilities.invokeLater(() -> FunctionManager.writeFunctionsToTable(funcTable));
                    }//end if
                    else if (Function.isFunctionFullName(funcStart)) {
                        FunctionManager.add(question);
                        SwingUtilities.invokeLater(() -> FunctionManager.writeFunctionsToTable(funcTable));
                    } else if (trigger.equals("store:")) {
                        SwingUtilities.invokeLater(() -> task.storeFormulae());
                    }//end else if
                    else if (trigger.equals("exit:")) {
                        task.shutDown();
                    }//end else if
                    else {
                        task.eval();
                    }//end else
                }//end if
                else if (mode.isCOMMAND()) {
                    task.commandLineManager();
                } else {
                    JOptionPane.showMessageDialog(null, "Unrecognized Operation!");
                }

            }//end try
            catch (NullPointerException nolian) {
            }//end catch
            catch (IndexOutOfBoundsException indexErr) {

            }//end catch

        }//end method execute

    }//end class OperatingSystem


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem _exitMenuItem;
    private javax.swing.JMenuItem abooutMenuItem;
    private javax.swing.JRadioButtonMenuItem base10RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base2RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base3RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base4RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base5RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base6RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base7RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base8RadioMenuItem;
    private javax.swing.JRadioButtonMenuItem base9RadioMenuItem;
    private javax.swing.JMenu baseSystemMenu;
    private javax.swing.JMenuItem calculatorMenuItem;
    private javax.swing.JRadioButtonMenuItem calculatorModeRadioButtonMenuItem;
    public static javax.swing.JTextField calculatorScreen;
    private javax.swing.JMenuItem calculatorScreenBackgroundColorCtrlMenuItem;
    private javax.swing.JMenuItem calculatorScreenBorderBackgroundColorCtrlMenuItem;
    private javax.swing.JMenuItem calculatorScreenFontCtrlMenuItem;
    private javax.swing.JMenuItem calculatorScreenTextColorCtrlMenuItem;
    private javax.swing.JMenuItem calculatorScreenclearMenuItem;
    private javax.swing.JMenu chatMenu;
    private javax.swing.JMenu clearMenu;
    private javax.swing.JMenu colorMenu;
    public static javax.swing.JLabel commandLineHistoryLabel;
    private javax.swing.JTextArea commandLineInput;
    private javax.swing.JMenuItem commandLineInputBackgroundColorCtrlMenuItem;
    private javax.swing.JMenuItem commandLineInputLabelTextColorMenuItem;
    private javax.swing.JMenuItem commandLineInputScreenFontCtrlMenuItem;
    private javax.swing.JMenuItem commandLineInputScreenLabelFontCtrlMenuItem;
    private javax.swing.JMenuItem commandLineInputTextColorCtrlMenuItem;
    private javax.swing.JMenuItem commandLineInputclearMenuItem;
    public static javax.swing.JLabel commandLineLabel;
    private javax.swing.JRadioButtonMenuItem commandLineModeRadioButtonMenuItem;
    public static javax.swing.JTextArea commandLineOutput;
    private javax.swing.JMenuItem commandLineOutputBackgroundColorCtrlMenuItem;
    private javax.swing.JMenuItem commandLineOutputLabelTextColorMenuItem;
    private javax.swing.JMenuItem commandLineOutputScreenFontCtrlMenuItem;
    private javax.swing.JMenuItem commandLineOutputScreenLabelFontCtrlMenuItem;
    private javax.swing.JMenuItem commandLineOutputTextColorCtrlMenuItem;
    private javax.swing.JMenuItem commandLineOutputclearMenuItem;
    private javax.swing.JRadioButtonMenuItem connectToRadioMenuItem;
    private javax.swing.JTable constantsTable;
    private javax.swing.JMenuItem constantsTableBackgroundColorMenuItem;
    private javax.swing.JMenuItem constantsTableFontCtrlMenuItem;
    private javax.swing.JMenuItem constantsTableHeaderFontCtrlMenuItem;
    private javax.swing.JMenuItem constantsTableTextColorMenuItem;
    private javax.swing.JMenuItem creditsMenuItem;
    private javax.swing.JMenu customizerMenu;
    public static javax.swing.JRadioButtonMenuItem degsRadioMenuItem;
    private javax.swing.JMenuItem determinantsMenuItem;
    private javax.swing.JMenuItem differentialequationsMenuItem;
    private javax.swing.JTextArea evaluatedCommands;
    private javax.swing.JMenuItem evaluatedCommandsLabelTextColorMenuItem;
    private javax.swing.JMenuItem evaluatedCommandsScreenBackgroundColorMenuItem;
    private javax.swing.JMenuItem evaluatedCommandsScreenFontCtrlMenuItem;
    private javax.swing.JMenuItem evaluatedCommandsScreenTextColorMenuItem;
    private javax.swing.JMenuItem evaluatedCommandsclearMenuItem;
    private javax.swing.JMenu fontMenu;
    private javax.swing.JMenuItem forceExitMenuItem;
    private javax.swing.JTable formTable;
    private javax.swing.JMenu formatMenu;
    private javax.swing.JMenuItem formulaTableBackgroundColorMenuItem;
    private javax.swing.JMenuItem formulaTableFontCtrlMenuItem;
    private javax.swing.JMenuItem formulaTableHeaderFontCtrlMenuItem;
    private javax.swing.JMenuItem formulaTableTextColorMenuItem;
    private javax.swing.JTable funcTable;
    private javax.swing.JMenuItem generalFrameWorkBackgroundColorMenuItem;
    private javax.swing.JRadioButtonMenuItem getConnectionFromRadioMenuItem;
    public static javax.swing.JRadioButtonMenuItem gradsRadioMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JTable historyTable;
    private javax.swing.JMenuItem historyTableBackgroundColorMenuItem;
    private javax.swing.JMenuItem historyTableFontCtrlMenuItem;
    private javax.swing.JMenuItem historyTableHeaderFontCtrlMenuItem;
    private javax.swing.JMenuItem historyTableTextColorMenuItem;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    public static javax.swing.JPanel jPanel2;
    public static javax.swing.JPanel jPanel3;
    public static javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator3;
    public static javax.swing.JButton keyBoardCtrl;
    private javax.swing.JMenuItem keyboardSwitchButtonFontCtrlMenuItem;
    private javax.swing.JMenuItem lockerMenuItem;
    private javax.swing.JMenuItem makeFormulaMenuItem;
    private javax.swing.JMenuItem matrixAdditionMenuItem;
    private javax.swing.JMenuItem matrixMultiplicationMenuItem;
    private javax.swing.JMenuItem matrixSubtractionMenuItem;
    private javax.swing.JTable matrixTable;
    private javax.swing.JMenuItem messagingScreenBackgroundColorMenuItem;
    private javax.swing.JMenuItem messagingScreenColorCtrlMenuItem;
    private javax.swing.JMenuItem messagingScreenTextColorMenuItem;
    private javax.swing.JMenuItem numericalDerivativesMenuItem;
    private javax.swing.JMenuItem numericalIntegralsMenuItem;
    private javax.swing.JMenuItem openDocumentMenuItem;
    public static javax.swing.JPanel parentPanel;
    private javax.swing.JMenuItem parserMessagesFontCtrlMenuItem;
    private javax.swing.JMenuItem parserMessagesTextColorMenuItem;
    private javax.swing.JMenuItem plottingFunctionsMenuItem;
    private javax.swing.JMenuItem progressMonitorFontCtrlMenuItem;
    private javax.swing.JMenuItem progressMonitorTextColorMenuItem;
    private javax.swing.JMenuItem quadraticequationsMenuItem;
    public static javax.swing.JRadioButtonMenuItem radsRadioMenuItem;
    public static javax.swing.JLabel resultLabel;
    private javax.swing.JMenuItem rootsOfEquationsMenuItem;
    private javax.swing.JMenuItem saveEvaluatedCommandsDocumentMenuItem;
    private javax.swing.JMenuItem setColorMenuItem;
    private javax.swing.JMenuItem showTimeMenuItem;
    private javax.swing.JMenuItem simultaneousequationsMenuItem;
    private javax.swing.JButton solveButton;
    private javax.swing.JMenuItem statisticsMenuItem;
    private javax.swing.JMenuItem storeConstantMenuItem;
    private javax.swing.JMenuItem storeFormulaMenuItem;
    private javax.swing.JMenuItem storeVariableMenuItem;
    private javax.swing.JMenu switchModeMenu;
    private javax.swing.JMenuItem tartagliasequationsMenuItem;
    private javax.swing.JMenu tasksMenu;
    private javax.swing.JMenuItem triangularMatricesMenuItem;
    private javax.swing.JTable varTable;
    private javax.swing.JMenuItem variablesTableBackgroundColorMenuItem;
    private javax.swing.JMenuItem variablesTableFontCtrlMenuItem;
    private javax.swing.JMenuItem variablesTableHeaderFontCtrlMenuItem;
    private javax.swing.JMenuItem variablesTableTextColorMenuItem;
    // End of variables declaration//GEN-END:variables

}
