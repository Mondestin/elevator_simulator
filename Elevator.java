/**
 * @(#)Elevator.java
 *
 * Elevator application
 *
 * @author  Mondestin
 * @version 2.00
 */


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;


//main class
public class Elevator
{
    public static void main(String[] args)
    {
    	//this method queues events with a new run method
    	EventQueue.invokeLater( new Runnable()
    	{
    		@Override
    		public void run()
    		{
    			Gui gui = new Gui();
		    	gui.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		    	gui.setSize( 700, 715 );
		    	gui.setVisible( true );
		    	gui.setResizable( false );
		    	gui.setLocationRelativeTo( null );

		    	//setting the Nimbus Look-And-Feel
		    	try
				{
					for ( UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() )
					{
						if( "Nimbus".equals( info.getName() ) )
						{
							UIManager.setLookAndFeel( info.getClassName() );
							SwingUtilities.updateComponentTreeUI( gui );
							break;
						}
					}
				}
				catch( Exception ex )
				{
					ex.printStackTrace();
				}
    		}
    	});
    }
}

