/**
 * @(#)Elevator.java
 *
 * Elevator application
 *
 * @author  Mondestin
 * @version 2.00
 */

import javax.swing.JLabel;
import javax.swing.JToolTip;//a cool tool tip text that matches the application theme


public class Labels extends JLabel
{
	private JToolTip _tooltip;	//new tooltip

	//constructor
	public Labels()
	{
		_tooltip = new ToolTips();
		_tooltip.setComponent( this );
	}
	//-------------------------------------------------- ///////////////// -----------------------------------------------------

	//apply the tool tip text to Labels
	public JToolTip createToolTip()
	{
		return _tooltip;
	}
	//-------------------------------------------------- ///////////////// -----------------------------------------------------
}
