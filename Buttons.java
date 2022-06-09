/**
 * @(#)Elevator.java
 *
 * Elevator application
 *
 * @author  Mondestin
 * @version 2.00
 */


import javax.swing.JButton;
import javax.swing.JToolTip;//a cool tool tip text that matches the application theme

//customized transluscent JButton with the new tool tip text
public class Buttons extends JButton
{
	private JToolTip _tooltip;	//new tooltip

	//null constructor
	public Buttons()
	{
		_tooltip = new ToolTips();
		_tooltip.setComponent(this);
	}
	//-------------------------------------------------- ///////////////// -----------------------------------------------------

	//apply the tool tip text to Buttons
	public JToolTip createToolTip()
	{
		return _tooltip;
	}
	//-------------------------------------------------- ///////////////// -----------------------------------------------------
}