package math.graph.alpha;

import com.github.gbenroscience.math.graph.DrawingContext;
import com.github.gbenroscience.math.graph.tools.FontStyle;
import static com.github.gbenroscience.math.graph.tools.FontStyle.BOLD;
import static com.github.gbenroscience.math.graph.tools.FontStyle.BOLD_ITALIC;
import static com.github.gbenroscience.math.graph.tools.FontStyle.ITALIC;
import com.github.gbenroscience.math.graph.tools.GraphColor;
import com.github.gbenroscience.math.graph.tools.GraphFont;
import com.github.gbenroscience.math.graph.tools.TextDimensions;
import java.awt.*;

public class SwingDrawingContext implements DrawingContext {

    private final Graphics2D g;
    private final float scale = 1.0f;

    public SwingDrawingContext(Graphics2D g) {
        this.g = g;
    }

    @Override
    public void setColor(GraphColor c) {
        g.setColor(new Color(c.r, c.g, c.b, c.a));
    }

    @Override
    public void setStrokeWidth(float w) {
        g.setStroke(new BasicStroke(w));
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        g.drawOval(x, y, width, height);
    }

    @Override
    public void fillOval(int x, int y, int width, int height) {
        g.fillOval(x, y, width, height);
    }

    @Override
    public void setFont(com.github.gbenroscience.math.graph.tools.GraphFont f) {
        //String family, FontStyle style, float size
        int awtStyle;
        switch (f.getStyle()) {
            case BOLD:
                awtStyle = Font.BOLD;
                break;
            case ITALIC:
                awtStyle = Font.ITALIC;
                break;
            case BOLD_ITALIC:
                awtStyle = Font.BOLD | Font.ITALIC;
                break;
            default:
                awtStyle = Font.PLAIN;
                break;
        }
        g.setFont(new Font(f.getFamily(), awtStyle, (int) f.getSize()));
    }

    public GraphFont getGraphFont(Font f) {
        FontStyle fontStyle;
        switch (f.getStyle()) {
            case Font.BOLD:
                fontStyle = FontStyle.BOLD;
                break;
            case Font.ITALIC:
                fontStyle = FontStyle.ITALIC;
                break;
            case (Font.BOLD | Font.ITALIC):
                fontStyle = FontStyle.BOLD_ITALIC;
                break;
            default:
                fontStyle = FontStyle.PLAIN;
                break;
        }
        return new GraphFont(f.getFamily(), fontStyle, f.getSize2D());
    }

    public Font getAWTFont(GraphFont f) {
         int awtStyle;
        switch (f.getStyle()) {
            case BOLD:
                awtStyle = Font.BOLD;
                break;
            case ITALIC:
                awtStyle = Font.ITALIC;
                break;
            case BOLD_ITALIC:
                awtStyle = Font.BOLD | Font.ITALIC;
                break;
            default:
                awtStyle = Font.PLAIN;
                break;
        }
        return new Font(f.getFamily(), awtStyle, (int) f.getSize());
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2) {
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    @Override
    public void drawRect(float x, float y, float w, float h) {
        g.drawRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void fillRect(float x, float y, float w, float h) {
        g.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    @Override
    public void drawText(String text, float x, float y) {
        g.drawString(text, (int) x, (int) y);
    }

    @Override
    public TextDimensions measureText(String text) {
        FontMetrics m = g.getFontMetrics(g.getFont());
        return new TextDimensions(m.stringWidth(text), m.getHeight());
    }

    @Override
    public float getScale() {
        return scale;
    }
}
