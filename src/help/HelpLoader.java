package help;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;



public class HelpLoader extends JFrame {
protected JEditorPane mEditorPane;
public HelpLoader(String urlString) {
super("MathematicalNigerian Help");
createUI(urlString);
setVisible(true);
}
protected void createUI(String urlString) {
setSize(1200, 800);
center( );
Container content = getContentPane( );
content.setLayout(new BorderLayout( ));

// add the editor pane
mEditorPane = new JEditorPane( );
mEditorPane.setEditable(false);

content.add(new JScrollPane(mEditorPane), BorderLayout.CENTER);
// open the initial URL
openURL(urlString);

// add the plumbing to make links work
mEditorPane.addHyperlinkListener(new LinkActivator( ));
// exit the application when the window is closed
addWindowListener(new WindowAdapter( ) {
            @Override
public void windowClosing(WindowEvent e) {
            setVisible(false);
            }
});
}
protected void center( ) {
Dimension screen = Toolkit.getDefaultToolkit().getScreenSize( );
Dimension us = getSize( );
int x = (screen.width - us.width) / 2;
int y = (screen.height - us.height) / 2;
setLocation(x, y);
}
protected void openURL(String urlString) {
try {
URL url = new URL(urlString);
mEditorPane.setPage(url);

}
catch (Exception e) {
System.out.println("Couldn't open " + urlString + ":" + e);
}
}//end method
class LinkActivator implements HyperlinkListener {
        @Override
public void hyperlinkUpdate(HyperlinkEvent he) {
HyperlinkEvent.EventType type = he.getEventType( );
if (type == HyperlinkEvent.EventType.ENTERED)
mEditorPane.setCursor(
Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
else if (type == HyperlinkEvent.EventType.EXITED)
mEditorPane.setCursor(Cursor.getDefaultCursor( ));
else if (type == HyperlinkEvent.EventType.ACTIVATED)
openURL(he.getURL().toExternalForm( ));
}
}
public static void main(String[] args) {
  File file =new File("src/help/Help.html");
        new HelpLoader(file.toURI().toString());
}//end method

}//end class