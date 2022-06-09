/**
 * @(#)Elevator.java
 *
 * Elevator application
 *
 * @author  Mondestin
 * @version 2.00
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.Component;
import java.awt.Shape;
import java.awt.BasicStroke;
import javax.swing.JComponent;
import javax.swing.JToolTip;


//a cool tool tip that matches the application theme
public class ToolTips extends JToolTip
{
	//constructor
	public ToolTips()
	{
		super();

		this.setOpaque( false );	// make the tool tip not fill in its background
	}
	//-------------------------------------------------- ///////////////// -----------------------------------------------------

	//create the tool tip
	public void paintComponent( Graphics g )
	{
		// set the parent to not be opaque
		Component parent = this.getParent();

		if( parent != null )
		{
			if( parent instanceof JComponent )
			{
				JComponent jparent = ( JComponent )parent;
				if( jparent.isOpaque() )
					jparent.setOpaque( false );
		   	}
	  	}

	  	// create a rectangle
		Shape round = new Rectangle2D.Float( 0, 0,
	        this.getWidth(),
	        this.getHeight() );

		// draw background with a color of your choice
		Graphics2D g2 = ( Graphics2D )g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON );
		g2.setColor( new Color( 200, 200, 200 ) );
		g2.fill( round );

		// draw the gray border
		g2.setColor( Color.blue );
		g2.setStroke( new BasicStroke( 1 ) );
		g2.draw( round );
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_DEFAULT );

		// draw the text
		String text = this.getComponent().getToolTipText();
		if( text != null )
		{
	    	FontMetrics fm = g2.getFontMetrics();
	 		g2.setColor( Color.black );
			g2.drawString( text, 7, ( this.getHeight() + 10 ) / 2 );
		}
	}
	//-------------------------------------------------- ///////////////// -----------------------------------------------------

	//adjust the dimensions of the tool tip with the component's dimensions
	public Dimension getPreferredSize()
	{
		Dimension dim = super.getPreferredSize();
		return new Dimension( ( int )dim.getWidth() + 6,
           ( int )dim.getHeight() + 2 );
	}
	//-------------------------------------------------- ///////////////// -----------------------------------------------------
}
