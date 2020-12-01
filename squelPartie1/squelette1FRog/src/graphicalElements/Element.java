package graphicalElements;

import java.awt.*;
import java.awt.image.BufferedImage;

import util.Case;


public class Element extends Case {
    public final Color color;
    public final BufferedImage sprit;

    public Element(int absc, int ord, Color color, BufferedImage sprit) {
        super(absc, ord);
        this.color = color;
        this.sprit = sprit;
    }
    
    public Element(Case c, Color color, BufferedImage sprit) {
        super(c.absc, c.ord);
        this.color = color;
        this.sprit = sprit;
    }
    
}
