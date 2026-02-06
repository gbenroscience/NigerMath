/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FontChooser.java
 *
 * Created on 08-Oct-2010, 04:56:55
 */

package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * Designed to simplify font selection in applications.
 * The user can select the font desired and then retrieve it with the
 * selectedFont() method which returns a Font object built from the user's
 * selection on the FontChooser gui.
 *
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
public class FontChooser extends javax.swing.JFrame implements Runnable{
/**
 * Used to perform time-based operations.
 */
 
private Thread timer;
private final String fontnames[]=new String[]{
"Agency FB",
"Aharoni",
"Algerian",
"Andalus",
"Angsana New",
"AngsanaUPC",
"Arabic Typesetting",
"Arial",
"Arial Black",
"Arial Narrow",
"Arial Narrow Special G1",
"Arial Narrow Special G2",
"Arial Rounded MT Bold",
"Arial Special G1",
"Arial Special G2",
"Arial Unicode MS",
"Baskerville Old Face",
"Batang",
"BatangChe",
"Bauhaus 93",
"Bell MT",
"Berlin Sans FB",
"Berlin Sans FB Demi",
"Bernard MT Condensed",
"Blackadder ITC",
"Bodoni MT",
"Bodoni MT Black",
"Bodoni MT Condensed",
"Bodoni MT Poster Compressed",
"Book Antiqua",
"Bookman Old Style",
"Bookshelf Symbol 7",
"Bradley Hand ITC",
"Britannic Bold",
"Broadway",
"Browallia New",
"BrowalliaUPC",
"Brush Script MT",
"Calibri",
"Californian FB",
"Calisto MT",
"Cambria",
"Cambria Math",
"Candara",
"Castellar",
"Centaur",
"Century",
"Century Gothic",
"Century Schoolbook",
"Chiller",
"CL_WST_CZEC_12x10",
"CL_WST_ENGL_12x10",
"CL_WST_ESTO_12x10",
"CL_WST_FREH_12x10",
"CL_WST_Germ_12x10",
"CL_WST_ITAN_12x10",
"CL_WST_LETT_12x10",
"CL_WST_POLI_12x10",
"CL_WST_RUMA_12x10",
"CL_WST_SERB_12x10",
"CL_WST_Smooth_12x10",
"CL_WST_SPAN_12x10",
"CL_WST_SWED_12x10",
"CL_WST_TURK_12x10",
"Colonna MT",
"Comic Sans MS",
"Consolas",
"Constantia",
"Cooper Black",
"Copperplate Gothic Bold",
"Copperplate Gothic Light",
"Corbel",
"Cordia New",
"CordiaUPC",
"Courier",
"Courier New",
"Curlz MT",
"DaunPenh",
"David",
"DFKai-SB",
"DilleniaUPC",
"DokChampa",
"Dotum",
"DotumChe",
"Edwardian Script ITC",
"Elephant",
"Engravers MT",
"Eras Bold ITC",
"Eras Demi ITC",
"Eras Light ITC",
"Eras Medium ITC",
"Estrangelo Edessa",
"EucrosiaUPC",
"Euphemia",
"Eurostile",
"FangSong",
"Felix Titling",
"Fixedsys",
"Footlight MT Light",
"Forte",
"Franklin Gothic Book",
"Franklin Gothic Demi",
"Franklin Gothic Demi Cond",
"Franklin Gothic Heavy",
"Franklin Gothic Medium",
"Franklin Gothic Medium Cond",
"FrankRuehl",
"FreesiaUPC",
"Freestyle Script",
"French Script MT",
"Futura Bk",
"Futura Hv",
"Futura Lt",
"Futura Md",
"Garamond",
"Gautami",
"Georgia",
"Georgia Ref",
"Gigi",
"Gill Sans MT",
"Gill Sans MT Condensed",
"Gill Sans MT Ext Condensed Bold",
"Gill Sans Ultra Bold",
"Gill Sans Ultra Bold Condensed",
"Gisha",
"Gloucester MT Extra Condensed",
"Goudy Old Style",
"Goudy Stout",
"Gulim",
"GulimChe",
"Gungsuh",
"GungsuhChe",
"Haettenschweiler",
"Harlow Solid Italic",
"Harrington",
"High Tower Text",
"Impact",
"Imprint MT Shadow",
"Informal Roman",
"IrisUPC",
"Iskoola Pota",
"JasmineUPC",
"Jokerman",
"Juice ITC",
"KaiTi",
"Kalinga",
"Kartika",
"KodchiangUPC",
"Kristen ITC",
"Kunstler Script",
"Latha",
"Leelawadee",
"Levenim MT",
"LilyUPC",
"Lucida Bright",
"Lucida Calligraphy",
"Lucida Console",
"Lucida Fax",
"Lucida Handwriting",
"Lucida Sans",
"Lucida Sans Typewriter",
"Lucida Sans Unicode",
"Magneto",
"Maiandra GD",
"Malgun Gothic",
"Mangal",
"Marlett",
"Matisse ITC",
"Matura MT Script Capitals",
"Meiryo",
"Microsoft Himalaya",
"Microsoft JhengHei",
"Microsoft Sans Serif",
"Microsoft Uighur",
"Microsoft YaHei",
"Microsoft Yi Baiti",
"MingLiU",
"MingLiU_HKSCS",
"MingLiU_HKSCS-ExtB",
"MingLiU-ExtB",
"Miriam",
"Miriam Fixed",
"Mistral",
"Modern",
"Modern No. 20",
"Mongolian Baiti",
"Monotype Corsiva",
"MoolBoran",
"MS Gothic",
"MS Mincho",
"MS Outlook",
"MS PGothic",
"MS PMincho",
"MS Reference 1",
"MS Reference 2",
"MS Reference Sans Serif",
"MS Reference Serif",
"MS Reference Specialty",
"MS Sans Serif",
"MS Serif",
"MS UI Gothic",
"MT Extra",
"MV Boli",
"Narkisim",
"Niagara Engraved",
"Niagara Solid",
"NSimSun",
"Nyala",
"OCR A Extended",
"Old English Text MT",
"Onyx",
"Palace Script MT",
"Palatino Linotype",
"Papyrus",
"Parchment",
"Perpetua",
"Perpetua Titling MT",
"Plantagenet Cherokee",
"Playbill",
"PMingLiU",
"PMingLiU-ExtB",
"Poor Richard",
"Pristina",
"Raavi",
"Rage Italic",
"Ravie",
"Ref_Icon",
"RefSpecialty",
"Rockwell",
"Rockwell Condensed",
"Rockwell Extra Bold",
"Rod",
"Roman",
"Script",
"Script MT Bold",
"Segoe Print",
"Segoe Script",
"Segoe UI",
"Showcard Gothic",
"Shruti",
"SimHei",
"Simplified Arabic",
"Simplified Arabic Fixed",
"SimSun",
"SimSun-ExtB",
"Small Fonts",
"Snap ITC",
"Stencil",
"Sylfaen",
"Symbol",
"System",
"Tahoma",
"Tempus Sans ITC",
"Terminal",
"Times New Roman",
"Times New Roman Special G1",
"Times New Roman Special G2",
"Traditional Arabic",
"Trebuchet MS",
"Tunga",
"Tw Cen MT",
"Tw Cen MT Condensed",
"Tw Cen MT Condensed Extra Bold",
"Verdana",
"Verdana Ref",
"Viner Hand ITC",
"Vivaldi",
"Vladimir Script",
"Vrinda",
"Webdings",
"Wide Latin",
"Wingdings",
"Wingdings 2",
"Wingdings 3",

   };





/**
 * An hypothetical component whose
 * font may need to be set.
 */
private Component component;
/**
 * An hypothetical graphics object
 * whose font may need to be set.
 */
private Graphics inputGraphics;
/**
 * An hypothetical font object that may need
 * to be set.
 */
private Font fontObject;
/**
 * This attribute monitors whether the present item
 * whose font is to be set is an object of class
 * Component or of class Graphics.
 */
private InputType identifyInput=InputType.Component;

int fontStyles[]=new int[]{Font.PLAIN,Font.ITALIC,Font.BOLD,Font.BOLD+Font.ITALIC};
String fontStyleNames[]= new String[]{"Plain","Italic","Bold","Bold Italic"};
    /** Creates new form FontChooser */
    public FontChooser() {
        initComponents();
loadFontNames();
loadFontStyles();
loadFontSize();
initializeFirstSelectedRowOnTables();

setSize(496, 350);
setResizable(false);
setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try{
                setVisible(false);
                if(identifyInput.isComponent()){
                getComponent().setFont(selectedFont());
                }//end if
                else if(identifyInput.isGraphics()){
                    getInputGraphics().setFont(selectedFont()); 
                }
                else if(identifyInput.isFont()){
                    fontObject = selectedFont();
                }
                }
                catch(NullPointerException nolian){
                    
                }
            }



});



setVisible(false);
setAlwaysOnTop(true);
fontNameTable.grabFocus();

timer = new Thread(this);
timer.start();

    }

    public String[] getFontnames() {
        return fontnames;
    }
    

    /**
     * Loads the names of all present fonts into
     * the font names table.
     */
private void loadFontNames(){
   for(int i=0;i<fontnames.length;i++){
       fontNameTable.setValueAt(fontnames[i], i, 0);
   }
}
    /**
     * Loads the styles of all present fonts into
     * the font styles table.
     */
private void loadFontStyles(){
       for(int i=0;i<fontStyleNames.length;i++){
       fontStyleTable.setValueAt(fontStyleNames[i], i, 0);
   }
}
    /**
     * Loads the size of all present fonts into
     * the font sizes table.
     */
private void loadFontSize(){
       for(int i=8;i<=1000;i++){
       fontSizeTable.setValueAt(i, i-8, 0);
   }
}
/**
 * Sets the row that will be selected when the
 * program first starts up.
 */
private void initializeFirstSelectedRowOnTables(){
        fontNameTable.addRowSelectionInterval( 8, 8);
            fontStyleTable.addRowSelectionInterval(2,2);
                fontSizeTable.addRowSelectionInterval(5,5);
}
/**
 *
 * @param component sets the component whose font is to be set
 * by this FontChooser object.
 */
    public void setComponent(Component component) {
        this.component = component;
        identifyInput=InputType.Component;
        setFontValue(component.getFont());
    }
/**
 *
 * @return The component whose font is to be set
 * by this Fontchooser object.
 */
    public Component getComponent() {
        return component;
    }

    public Graphics getInputGraphics() {
        return inputGraphics;
    }

    public void setInputGraphics(Graphics inputGraphics) {
        this.inputGraphics = inputGraphics;
         identifyInput=InputType.Graphics;
        setFontValue(inputGraphics.getFont()); 
    }

    public void setFontObject(Font fontObject){
        this.fontObject = fontObject;
         identifyInput=InputType.Font;
         setFontValue(fontObject);
    }

    public Font getFontObject() {
        return fontObject;
    }





    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fontNameTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        fontNameField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        fontStyleTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        fontStyleField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        fontSizeTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        fontSizeField = new javax.swing.JTextField();
        displayPanel = new javax.swing.JPanel();
        selectedFontLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(nigermath.NigerMathApp.class).getContext().getResourceMap(FontChooser.class);
        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        fontNameTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fontNameTable.setName("fontNameTable"); // NOI18N
        fontNameTable.setShowHorizontalLines(false);
        fontNameTable.getTableHeader().setReorderingAllowed(false);
        fontNameTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fontNameTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(fontNameTable);
        fontNameTable.getColumnModel().getColumn(0).setResizable(false);
        fontNameTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("fontNameTable.columnModel.title0")); // NOI18N

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        fontNameField.setEditable(false);
        fontNameField.setText(resourceMap.getString("fontNameField.text")); // NOI18N
        fontNameField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fontNameField.setName("fontNameField"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        fontStyleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fontStyleTable.setName("fontStyleTable"); // NOI18N
        fontStyleTable.setShowHorizontalLines(false);
        fontStyleTable.getTableHeader().setReorderingAllowed(false);
        fontStyleTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fontStyleTableKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(fontStyleTable);
        fontStyleTable.getColumnModel().getColumn(0).setResizable(false);
        fontStyleTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("fontnameTable.columnModel.title0")); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        fontStyleField.setEditable(false);
        fontStyleField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fontStyleField.setName("fontStyleField"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        fontSizeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        fontSizeTable.setEditingRow(5);
        fontSizeTable.setName("fontSizeTable"); // NOI18N
        fontSizeTable.setShowHorizontalLines(false);
        fontSizeTable.getTableHeader().setReorderingAllowed(false);
        fontSizeTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fontSizeTableKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(fontSizeTable);
        fontSizeTable.getColumnModel().getColumn(0).setResizable(false);
        fontSizeTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("fontnameTable.columnModel.title0")); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        fontSizeField.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        fontSizeField.setName("fontSizeField"); // NOI18N

        displayPanel.setBackground(resourceMap.getColor("displayPanel.background")); // NOI18N
        displayPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        displayPanel.setName("displayPanel"); // NOI18N

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 142, Short.MAX_VALUE)
        );

        selectedFontLabel.setText(resourceMap.getString("selectedFontLabel.text")); // NOI18N
        selectedFontLabel.setBorder(new javax.swing.border.MatteBorder(null));
        selectedFontLabel.setName("selectedFontLabel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fontNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(fontStyleField, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(fontSizeField, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                .addGap(47, 47, 47))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(selectedFontLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fontNameField, jScrollPane1});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fontStyleField, jScrollPane2});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fontSizeField, jScrollPane3});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fontNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fontStyleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fontSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))
                            .addComponent(jLabel3))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(displayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedFontLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fontNameTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fontNameTableKeyPressed
   scrollTableWithUpAndDownKeys(fontNameTable, evt);
    }//GEN-LAST:event_fontNameTableKeyPressed

    private void fontStyleTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fontStyleTableKeyPressed
scrollTableWithUpAndDownKeys(fontStyleTable, evt);
    }//GEN-LAST:event_fontStyleTableKeyPressed

    private void fontSizeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fontSizeTableKeyPressed
scrollTableWithUpAndDownKeys(fontSizeTable, evt);
    }//GEN-LAST:event_fontSizeTableKeyPressed




 /**
 *  N.B::::The JTable object must be a single column one!
 * Used to scroll up and down a JTable object
 * with the up and down keys.
 * @param table The JTable object
 * @param evt The KeyEvent object.
 */
    public void scrollTableWithUpAndDownKeys(JTable table,KeyEvent evt){

              String keytext=KeyEvent.getKeyText(evt.getKeyCode()).toLowerCase();
        if(keytext.equalsIgnoreCase( "down")){
    int index=table.getSelectedRow()-1;
    if(index<table.getRowCount()-1){
    table.addRowSelectionInterval(index+1, index+1);
    }


    }//end if

    else if(keytext.equalsIgnoreCase( "up")){
    int index=table.getSelectedRow()+1;
    if(index>0){
    table.addRowSelectionInterval(index-1, index-1);
    }


    }//end else if
}//end method
    /**
     * Uses a given font value to set the
     * font components displayed on this FontChooser object.
     * This method will be invoked from the client application to
     * tell this application about its (the client's) present font.
     * @param font
     */
    public void setFontValue(Font font){
       String name=font.getFamily();
       int style=font.getStyle();
       int size=font.getSize();

try{
    int indexOfName=indexOf(name,fontNameTable);
    int indexOfStyle=indexOf(String.valueOf(style),fontStyleTable);
    int indexOfSize=indexOf(String.valueOf(size),fontSizeTable);
    fontNameTable.addRowSelectionInterval(indexOfName, indexOfName);
    fontStyleTable.addRowSelectionInterval(indexOfStyle, indexOfStyle);
    fontSizeTable.addRowSelectionInterval(indexOfSize, indexOfSize);
}//end try
catch(IndexOutOfBoundsException indexErr){

}
   catch(IllegalArgumentException exp){
       
   }


    }

/**
 *
 * @param value The string to search for on the table.
 * @return the index at which the string occurs.
 */
    public int indexOf(String value,JTable table){
        int index=-1;
           for(int i=0;i<table.getRowCount();i++){
               if(table.getValueAt(i, 0).toString().equals(value)){
                   return i;
               }
           }
          return index;
    }
/**
 * N.B::::The JTable object must be a single column one!
 * Gets the content of the current selected row on the JTable
 * object and writes it to a JtextField object.
 * @param table The JTable object
 * @param field The JTextField object
 */
    public void writeTableRowToTextField(JTable table,JTextField field){
       field.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
    }
    /**
     *
     * @return an integer denoting the style selected by the user.
     */
public int getStyle(){
    String selectedStyle=fontStyleField.getText();
    if(selectedStyle.equals("Plain")){
        return Font.PLAIN;
    }
    else if(selectedStyle.equals("Italic")){
        return Font.ITALIC;
    }
    else if(selectedStyle.equals("Bold")){
        return Font.BOLD;
    }
    else{
        return Font.BOLD+Font.ITALIC;
    }    
}






    /**
     *
     * @return an integer denoting the size selected by the user.
     */
public int getTextSize(){
    String selectedSize=fontSizeField.getText();
int size=13;
try{
    size=Integer.valueOf(selectedSize);
    fontSizeTable.addRowSelectionInterval(size-8,size-8);//select 11 on the table row
    fontSizeField.setText(String.valueOf(size)); 
}
catch(NumberFormatException numErr){
    size=13;
    fontSizeTable.addRowSelectionInterval(5,5);//select 11 on the table row
    fontSizeField.setText(String.valueOf(size));
}
catch(NullPointerException numErr){
    size=13;
    fontSizeTable.addRowSelectionInterval(5,5);//select 11 on the table row
    fontSizeField.setText(String.valueOf(size));
}
return size;
}
/**
 *
 * @return the user's selected font.
 */
public Font selectedFont(){
        return new Font( fontNameField.getText(),getStyle(),getTextSize()  );
    }

@Override
    public void run() {
        Thread me = Thread.currentThread();
        while (timer == me){
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
            }
            //actions
        writeTableRowToTextField(fontNameTable, fontNameField);
        writeTableRowToTextField(fontStyleTable, fontStyleField);
        writeTableRowToTextField(fontSizeTable, fontSizeField);
        selectedFontLabel.setText("SELECTED FONT::: "+fontNameField.getText()+","+fontStyleField.getText()+","+fontSizeField.getText());
repaint();
        }
    }//end method.


    @Override
public void paint(Graphics g){
    super.paint(g);
       g.setFont(selectedFont());
       Point p=displayPanel.getLocation();
       Dimension dim=displayPanel.getSize();
       g.drawString("OUR DEAR FONT."  , p.x+10, (int) (p.y + dim.height / 2.0));

}
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FontChooser font = new FontChooser();
                font.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                font.setVisible(true);
            }
        });
    }






    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel displayPanel;
    private javax.swing.JTextField fontNameField;
    private javax.swing.JTable fontNameTable;
    private javax.swing.JTextField fontSizeField;
    private javax.swing.JTable fontSizeTable;
    private javax.swing.JTextField fontStyleField;
    private javax.swing.JTable fontStyleTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel selectedFontLabel;
    // End of variables declaration//GEN-END:variables

}


/**
 * Models the type of object
 * that is requesting objects of class FontChooser
 * for a font.
 * @author JIBOYE Oluwagbemiro Olaoluwa
 */
enum InputType{
   Component,Graphics,Font;

   public boolean isComponent(){
       return this == Component;
   }

   public boolean isGraphics(){
       return this == Graphics;
   }
   public boolean isFont(){
       return this == Font;
   }
}