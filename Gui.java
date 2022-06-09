/**
 * @(#)Elevator.java
 *
 * Elevator application
 *
 * @author Mondestin
 * @version 2.00
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import sun.audio.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public class Gui extends JFrame  implements ActionListener
{
	private Container container; //frame's container


	//floor names and button names arrays
	private static final String[] floorNames = { "Floor 9", "Floor 8", "Floor 7", "Floor 6", "Floor 5",
												 "Floor 4", "Floor 3", "Floor 2", "Floor 1", "Ground", };

	private static final String[] floorNumbers = { "9", "8", "7", "6", "5",
												   "4", "3", "2", "1", "G" };

	private static final String[] buttonNames = { "9", "8", "7", "6", "5", "4",
												  "3", "2", "1", "G", "", "" };


	//flags used for event handling and animation
	private int floorFlagTest = 0;
	private int floorFlag = 9;
	private int currentFNum = 9;
	private boolean currentFFlag;
	private boolean p1Flag;
	private boolean p2Flag;
	private boolean goUpFlag;
	private boolean getDownFlag;
	private boolean moveUpFlag;
	private boolean moveDownFlag;
	private boolean busyFlag;
	private boolean motionFlag;
	private boolean upArrivalFlag;
	private boolean downArrivalFlag;

	//animation panel dimensions
	private final int WIDTH = 375;
    private final int HEIGHT = 715;

    //timers and delays
    private Timer timer1;
    private Timer timer2;
    private final int DELAY1 = 28;
    private final int DELAY2 = 25;

    //images coordinates in the animation panel
    private int x = 138;
    private int y = 629;
    private int[] yTest = { 8, 77, 146, 215, 284,
    						353, 422, 491, 560, 629 };
    private int yDest;
    private int xP1 = 285;
    private int yP1;
    private int xP2 = 173; //35
    private int yP2;

    //images in the animation panel
    private Image elevatorIcon1;
    private Image elevatorIcon2;
    private Image p1Icon;
    private Image p2Icon;
    private Image pStdIcon;

	private JPanel elevatorPanel;       //contains the call buttons and buttons to create a person
	private JPanel eastPanel;
	private JPanel displayPanel;		//display panel
	private JPanel buttonPanel;
	private JPanel actualButtonPanel;	//button pad
	private JPanel spacePanel;
	private JPanel helpPanel;			//contains help button
	private JPanel floorPanel;
	private JPanel iconPanel;			//contains floors' LEDs
	private JPanel fButtonsPanel;
	private JPanel pButtonsPanel;
	private JPanel floorNumPanel;
	private JPanel labelPanel;
	private JPanel animationPanel;		//animation panel

	//customized JLabels
	private RichJLabel numberLabel;		//actual display's label
	private Labels logoLabel;			//team's Logo

	private JLabel elevatorLabel;
	private JLabel background;			//background picture of the application
	private JLabel space1;
	private JLabel space2;
	private JLabel space3;
	private JLabel space4;
	private JLabel[] ledIcons;
	private RichJLabel[] floorLabels;

	private JButton[] buttons;			//up button
	private JButton[] floorButtons;		//elevator call button on floor 1
	private JButton[] personButtons;
	private JButton helpButton;			//help button

	//icons
	private Icon image;
	private Icon flag;
	private Icon floorIcon;
	private Icon personIcon;
	private Icon logo;
	private Icon helpIcon;

	//objects from other classers
	Sound sound = new Sound();
	Person person = new Person( yTest, floorFlagTest );


	//set up GUI
	public Gui()
	{
		super( "Elevator Simulator" );

		//setting the background image and frame layout
		container = getContentPane();
		setLayout( new BorderLayout() );


		background = new JLabel();
		background.setIcon( image );
		add(background);
		background.setLayout( new BorderLayout() );


		//create GUI components
		elevatorPanel = new JPanel( new BorderLayout() );
		elevatorPanel.setOpaque( false );
		background.add( elevatorPanel, BorderLayout.CENTER );

		floorPanel = new JPanel( new BorderLayout() );
		floorPanel.setPreferredSize( new Dimension( 190, 690 ) );//the floor number on the left
		floorPanel.setOpaque( false );
		floorPanel.setBorder( BorderFactory.createTitledBorder( "" ) );
		elevatorPanel.add( floorPanel, BorderLayout.WEST );


		//---------------------------- placing LEDs, floors and persons buttons on their panels --------------------------------
		iconPanel = new JPanel( new GridLayout( 10, 1, 1, 30 ) );
		iconPanel.setOpaque( false );
		iconPanel.setPreferredSize( new Dimension( 15, 715 ) );
		floorPanel.add( iconPanel, BorderLayout.WEST );

		floorNumPanel = new JPanel( new GridLayout( 1, 3 ) );
		floorNumPanel.setOpaque( false );
		floorPanel.add( floorNumPanel, BorderLayout.CENTER );

		fButtonsPanel = new JPanel( new GridLayout( 10, 1, 1, 30 ) );
		fButtonsPanel.setOpaque( false );
		floorNumPanel.add( fButtonsPanel );

		labelPanel = new JPanel( new GridLayout( 10, 1, 1, 30 ) );
		labelPanel.setOpaque( false );
		floorNumPanel.add( labelPanel );

		pButtonsPanel = new JPanel( new GridLayout( 10, 1, 1, 30 ) );
		pButtonsPanel.setOpaque( false );
		floorNumPanel.add( pButtonsPanel );

		//loading image
		try
		{
			flag = new ImageIcon( getClass().getResource( "media/icon2.png" ) );
			floorIcon = new ImageIcon( getClass().getResource( "media/floor.png" ) );
			personIcon = new ImageIcon( getClass().getResource( "media/person.png" ) );
			helpIcon = new ImageIcon( getClass().getResource( "media/help.png" ) );
		}
		catch( NullPointerException ex )
		{
			JOptionPane.showMessageDialog( null, "Please make sure your images are in the right directory !",
										  "Image loading error", JOptionPane.WARNING_MESSAGE );
		}

		ledIcons = new JLabel[ floorNames.length ];
		floorLabels = new RichJLabel[ floorNames.length ];
		floorButtons = new Buttons[ floorNames.length ];
		personButtons = new Buttons[ floorNames.length ];

		for ( int count = 0; count < floorNames.length; count++ )
		{
			ledIcons[ count ] = new JLabel( flag );
			ledIcons[ count ].setPreferredSize( new Dimension( 15, 15 ) );
			ledIcons[ count ].setEnabled( false );
			iconPanel.add( ledIcons[ count ] );

			floorLabels[ count ] = new RichJLabel( floorNames[ count ], 0 );
			floorLabels[ count ].setLeftShadow( 1, 1, new Color( 51, 153, 255 ) );
			floorLabels[ count ].setRightShadow( 0, 0, new Color( 96, 96, 96 ) );
			floorLabels[ count ].setForeground( new Color( 0, 0, 0 ) );
			floorLabels[ count ].setFont( new Font( "Serif", Font.BOLD, 15 ) );
			labelPanel.add( floorLabels[ count ] );

			floorButtons[ count ] = new Buttons();
			floorButtons[ count ].setIcon( floorIcon );
			floorButtons[ count ].setContentAreaFilled( false );
			floorButtons[ count ].setEnabled( false );
        	floorButtons[ count ].setToolTipText( "Call elevator" );
        	fButtonsPanel.add( floorButtons[ count ] );

        	personButtons[ count ] = new Buttons();
        	personButtons[ count ].setIcon( personIcon );
        	personButtons[ count ].setContentAreaFilled( false );
			personButtons[ count ].setEnabled( true );
        	personButtons[ count ].setToolTipText( "Create Person" );
        	pButtonsPanel.add( personButtons[ count ] );

        	floorButtons[ count ].addActionListener( this );
        	personButtons[ count ].addActionListener( this );

			if( count == 9 )
				ledIcons[ count ].setEnabled( true );
		}
		//-------------------------------------------------- /////////////////////// -----------------------------------------------------


		//creating timers for pictures animation
        timer1 = new Timer( DELAY1, this ); //elevator's timer
        timer2 = new Timer( DELAY2, this ); //person's timer


		//------------------------------------------------------ animation panel ---------------------------------------------------------

		//loading all images of the animation panel
		try
		{
			ImageIcon icon = new ImageIcon( "media/lift1.jpg" );
	        elevatorIcon1 = icon.getImage();

	        ImageIcon icon0 = new ImageIcon( "media/lift2.jpg" );
	        elevatorIcon2 = icon0.getImage();

	        ImageIcon icon1 = new ImageIcon( "media/person1.gif" );
	        p1Icon = icon1.getImage();

	        ImageIcon icon2 = new ImageIcon( "media/person2.gif" );
	        p2Icon = icon2.getImage();

	        ImageIcon icon3 = new ImageIcon( "media/personstand.png" );
	        pStdIcon = icon3.getImage();
		}
		catch( NullPointerException ex )
		{
			JOptionPane.showMessageDialog( null, "Please make sure your images are in the right directory !",
										  "Image loading error", JOptionPane.WARNING_MESSAGE );
		}

		//drawing on the animation panel
		animationPanel = new JPanel()
		{
			@Override
		    public void paintComponent( Graphics g )
		    {
		    	super.paintComponent( g );

		    	g.setColor( new Color( 0, 25, 51 ) );
		    	g.drawLine( 0, 56, 320, 56 );
		    	g.drawLine( 0, 56, 320, 56 );
		    	g.drawLine( 0, 125, 320, 125 );
		    	g.drawLine( 0, 194, 320, 194 );
		    	g.drawLine( 0, 263, 320, 263 );
		   		g.drawLine( 0, 332, 320, 332 );
		    	g.drawLine( 0, 401, 320, 401 );
		    	g.drawLine( 0, 470, 320, 470 );
		    	g.drawLine( 0, 539, 320, 539 );
		    	g.drawLine( 0, 608, 320, 608 );
		    	g.drawLine( 0, 677, 320, 677 );
		    	g.setColor( Color.BLACK );

		    	//person is created
		    	if( p1Flag )
		    	{
		    		Graphics2D g2d = ( Graphics2D ) g;
			        g2d.drawImage( p1Icon, xP1, yP1, this );	//draws person walking towards elevator

			        if( xP1 > 173 )
			        {
			        	g2d.drawImage( elevatorIcon2, x, y, this );	//draws elevator with open doors
			        }
			        else
			        {
			        	g2d.drawImage( elevatorIcon1, x, y, this );	//draws elevator with closed doors
			        }

			        g.dispose();
		    	}

		    	//person arrives at destination
		    	if( p2Flag )
		    	{
		    		Graphics2D g2d = ( Graphics2D ) g;
			        g2d.drawImage( p2Icon, xP2, yP2, this );	//draws person walking out of elevator

			        if( xP2 < 270 ) //230
			        {
			        	g2d.drawImage( elevatorIcon2, x, y, this );	//draws elevator with open doors
			        }
			        else
			        {
			        	g2d.drawImage( elevatorIcon1, x, y, this );	//draws elevator with closed doors
			        }

			        g.dispose();
		    	}

		    	//person created and waiting for an action to be taken
		    	if( person.personCreated() )
		    	{
		    		Graphics2D g2d = ( Graphics2D ) g;
			        g2d.drawImage( pStdIcon, person.getPerson_x(), person.getPerson_y(), this );	//draws person
			        g2d.drawImage( elevatorIcon1, x, y, this );	//draws elevator with closed doors
			        g.dispose();
		    	}

		    	Graphics2D g2d = ( Graphics2D ) g;
			    g2d.drawImage( elevatorIcon1, x, y, this );	//draws elevator with closed doors when the program start
				g.dispose();								//and when no action is taken
		    }
		};
		animationPanel.setOpaque( false );
		animationPanel.setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
		animationPanel.setDoubleBuffered( true );
		elevatorPanel.add( animationPanel, BorderLayout.CENTER );
		//-------------------------------------------------- /////////////////////// -----------------------------------------------------


		//------------------------------------------------- panel inside the elevator ----------------------------------------------------
		eastPanel = new JPanel( new BorderLayout() );
		eastPanel.setPreferredSize( new Dimension( 190, 370 ) );
		eastPanel.setOpaque( false );
		eastPanel.setBorder( BorderFactory.createTitledBorder( "Elevator's Board" ) );
		background.add( eastPanel, BorderLayout.EAST );

		buttonPanel = new JPanel( new BorderLayout( 1, 41 ) );
		buttonPanel.setPreferredSize( new Dimension( 190, 225 ) );
		buttonPanel.setOpaque( false );
		buttonPanel.setBorder( BorderFactory.createTitledBorder( "" ) );
		eastPanel.add( buttonPanel, BorderLayout.CENTER );


		//display panel
		displayPanel = new JPanel( new FlowLayout( FlowLayout.CENTER, 25, 0 ) );
		displayPanel.setBackground( Color.black );
		displayPanel.setOpaque( false );
		displayPanel.setBorder( BorderFactory.createTitledBorder( "Titre" ) );
		displayPanel.setPreferredSize( new Dimension( 208, 46 ) );
		eastPanel.add( displayPanel, BorderLayout.NORTH );


		//display label with customized font
		numberLabel = new RichJLabel( "G", 0 );
		numberLabel.setLeftShadow( 1, 1, new Color( 51, 153, 255 ) );
		numberLabel.setRightShadow( 0, 0, new Color( 96, 96, 96 ) );
		numberLabel.setForeground( new Color( 0, 0, 0 ) );
		numberLabel.setFont( new Font( "Serif", Font.BOLD, 17 ) );
		displayPanel.add( numberLabel );


		//panel which contains the buttons
		actualButtonPanel = new JPanel( new GridLayout( 4, 3 ) );
		actualButtonPanel.setOpaque( false );
		buttonPanel.add( actualButtonPanel, BorderLayout.CENTER );

		buttons = new Buttons[ buttonNames.length ];

		space3 = new JLabel( "" );
		space4 = new JLabel( "" );

		for( int count = 0; count < buttonNames.length; count++ )
		{
			if( count != 10 || count != 11)
			{
				buttons[ count ] = new Buttons();
				buttons[ count ].setText( buttonNames[ count ] );
				buttons[ count ].setEnabled( false );
				buttons[ count ].addActionListener( this );
			}
		}

		actualButtonPanel.add( buttons[ 2 ] );
		actualButtonPanel.add( buttons[ 1 ] );
		actualButtonPanel.add( buttons[ 0 ] );
		actualButtonPanel.add( buttons[ 5 ] );
		actualButtonPanel.add( buttons[ 4 ] );
		actualButtonPanel.add( buttons[ 3 ] );
		actualButtonPanel.add( buttons[ 8 ] );
		actualButtonPanel.add( buttons[ 7 ] );
		actualButtonPanel.add( buttons[ 6 ] );
		actualButtonPanel.add( space3 );
		actualButtonPanel.add( buttons[ 9 ] );
		actualButtonPanel.add( space4 );

		//panel that contains help button
		helpPanel = new JPanel( new GridLayout( 1, 3 ) );
		helpPanel.setOpaque( false );
		buttonPanel.add( helpPanel, BorderLayout.SOUTH );



		space1 = new JLabel( "" );
		space1.setPreferredSize( new Dimension( 10, 10 ) );
		helpPanel.add( space1 );

		helpButton = new Buttons();
		helpButton.setIcon( helpIcon );
		helpButton.setContentAreaFilled( false );
		helpButton.setToolTipText( "Emergency button" );
		helpPanel.add( helpButton );
		helpButton.setEnabled( false );

		helpButton.addActionListener( this );

		spacePanel = new JPanel();
		spacePanel.setOpaque( false );
		spacePanel.setPreferredSize( new Dimension( 190, 295 ) );
		eastPanel.add( spacePanel, BorderLayout.SOUTH );

		space2 = new JLabel( "" );
		spacePanel.add( space2 );
		//-------------------------------------------------- ////////////////////// -----------------------------------------------------
	}
	//------------------------------------------------------ end of Gui constructor ------------------------------------------------------

		//event handling
		public void actionPerformed( ActionEvent event )
		{
			//create person
			for( int count = 0; count < floorNames.length; count++ )
			{
				if( event.getSource() == personButtons[ count ] )
				{
					floorFlagTest = count;
					person.createPerson();
				}
			}

			if( person.personCreated() )
			{
				//int value = yTest[ floorFlagTest ] + 6;
				person.setPerson_y( ( yTest[ floorFlagTest ] + 6 ) );
				yP1 = person.getPerson_y();

				for( int count = 0; count < floorNames.length; count++ )
				{
					if( count == floorFlagTest )
						floorButtons[ floorFlagTest ].setEnabled( true );
					else
						floorButtons[ count ].setEnabled( false );

					personButtons[ count ].setEnabled( false );
				}
			}

			//call elevator
			if( event.getSource() == floorButtons[ floorFlagTest ] )
			{
				if( y < yTest[ floorFlagTest ] )
					getDownFlag = true;

				else if( y > yTest[ floorFlagTest ] )
					goUpFlag = true;

				else if( y == yTest[ floorFlagTest ] )
				{
					person.disablePerson( true );
					floorFlag = floorFlagTest;
					p1Flag = true;
				}
			}

			//elevator goes down from floor x to where it was called
			if( getDownFlag )
			{
				timer1.start();

	    		currentFFlag = true;

			   	y += 1;

			   	if( y == yTest[ floorFlagTest ] )
			   	{
			   		timer1.stop();
			   		sound.play();
			   		sound.stop();
			   		person.disablePerson( true );
			   		p1Flag = true;
			   		floorFlag = floorFlagTest;
					getDownFlag = false;
			   	}
			}

			//elevator goes up from floor x to where it was called
			if( goUpFlag )
			{
				timer1.start();

	    		currentFFlag = true;

		    	y -= 1;

		    	if( y == yTest[ floorFlagTest ] )
		    	{
		    		timer1.stop();
		    		sound.play();
		    		sound.stop();
		    		person.disablePerson( true );
		    		p1Flag = true;
		    		floorFlag = floorFlagTest;
					goUpFlag = false;
		    	}
			}

			//elevator call button pressed and person start walking towards elevator
			if( p1Flag )
			{
				timer2.start();

				xP1 -= 1;

				if( xP1 == 173 )
				{
					timer2.stop();
					p1Flag = false;
					helpButton.setEnabled( true );
					xP1 = 280;

					for( int count = 0; count < buttonNames.length; count++ )
					{
						if( ( count < 11 ) || count == 10 )
							buttons[ count ].setEnabled( true );
					}

					for( int count = 0; count < floorNames.length; count++ )
						floorButtons[ count ].setEnabled( false );
				}
			}

			if( !busyFlag )
			{
				//prevent the elevator from going down when on ground floor
				if( y == yTest[ 9 ] )
					moveDownFlag = true;
				else
					moveDownFlag = false;

				if( y == yTest[ 0 ] )
					moveUpFlag = true;
				else
					moveUpFlag = false;

				for( int count = 0; count < floorNames.length; count++ )
				{
					if( !moveDownFlag && count == 9 )
					{
						if( event.getSource() == buttons[ count ] )
						{
							floorFlagTest = count;
							yDest = yTest[ count ];
							yP2 = yDest + 6;
							motionFlag = true;
						}
					}
					else if( !moveUpFlag && count == 0 )
					{
						if( event.getSource() == buttons[ count ] )
						{
							floorFlagTest = count;
							yDest = yTest[ count ];
							yP2 = yDest + 6;
							motionFlag = true;
						}
					}
					else
					{
						if( event.getSource() == buttons[ count ] )
						{
							floorFlagTest = count;
							yDest = yTest[ count ];
							yP2 = yDest + 6;
							motionFlag = true;
						}
					}
				}
			}

			//elevator takes the person from a lower floor to a higher floor
			if( ( yDest < y ) && motionFlag )
	    	{
	    		busyFlag = true;
	    		person.disablePerson( true );

	    		timer1.start();

	    		currentFFlag = true;

	    		y -= 1;

	    		//elevator stops at destination
	    		if( y == yDest )
	    		{
	    			timer1.stop();
	    			sound.play();
	    			sound.stop();
	    			p2Flag = true;
	    			busyFlag = false;
	    			floorFlag = floorFlagTest;
	    			floorButtons[ floorFlag ].setEnabled( true );
	    			motionFlag = false;
	    		}
	    	}

	    	//elevator takes the person from a higher floor to a lower floor
	    	if( ( yDest > y ) && motionFlag )
	    	{
	    		busyFlag = true;
	    		person.disablePerson( true );

	    		timer1.start();

	    		currentFFlag = true;

	    		y += 1;

	    		//elevator stops at floor 1
	    		if( y == yDest )
	    		{
	    			timer1.stop();
	    			sound.play();
	    			sound.stop();
	    			busyFlag = false;
	    			p2Flag = true;
	    			floorFlag = floorFlagTest;
	    			floorButtons[ floorFlag ].setEnabled( true );
	    			motionFlag = false;
	    		}
	    	}

			//person walks out of the elevator
			if( p2Flag )
			{
				helpButton.setEnabled( false );

				for( int count = 0; count < buttonNames.length; count++ )
				{
					if( ( count < 11 ) || count == 10 )
						buttons[ count ].setEnabled( false );
				}

				timer2.start();

				xP2 += 1;

				if( xP2 == 300 )
				{
					timer2.stop();
					p2Flag = false;
		    		floorButtons[ floorFlag ].setEnabled( false );
					xP2 = 173;

					for( int count = 0; count < floorNames.length; count++ )
						personButtons[ count ].setEnabled( true );
				}

				//elevator call button pressed again while person walking out of the elevator
				if( event.getSource() == floorButtons[ floorFlagTest ] )
				{
					p2Flag = false;
					p1Flag = true;
					xP1 = xP2;
					xP2 = 173;
					yP1 = yP2;
				}
			}

			//LED lights up and elevator's display displays x when the elevator arrives on floor x
			if( currentFFlag )
			{
			   	switch( y )
			   	{
			   		case 8:
			   			currentFNum = 0;
			   			break;
			   		case 77:
			   			currentFNum = 1;
			   			break;
			   		case 146:
			   			currentFNum = 2;
			   			break;
			   		case 215:
			   			currentFNum = 3;
			   			break;
			   		case 284:
			   			currentFNum = 4;
			   			break;
			   		case 353:
			   			currentFNum = 5;
			   			break;
			   		case 422:
			   			currentFNum = 6;
			   			break;
			   		case 491:
			   			currentFNum = 7;
			   			break;
			   		case 560:
			   			currentFNum = 8;
			   			break;
			   		case 629:
			   			currentFNum = 9;
			   			break;
			   	}
				numberLabel.setFont( new Font( "Serif", Font.BOLD, 17 ) );
				numberLabel.setText( floorNumbers[ currentFNum ] );

				for( int count = 0; count < floorNames.length; count++ )
				{
					if( count == currentFNum )
						ledIcons[ count ].setEnabled( true );
					else
						ledIcons[ count ].setEnabled( false );
				}
			}

			//help message displayed when help button is pressed
			if( event.getSource() == helpButton )
			{
				person.disablePerson( true );
				numberLabel.setFont( new Font( "Serif", Font.PLAIN, 14 ) );
				numberLabel.setText( "Don't panic! Help is coming" );
			}

			repaint(); //refresh the animation panel
		}
	//------------------------------------------------------- end of event handling ------------------------------------------------------
}
//--------------------------------------------------------------- end Gui class ----------------------------------------------------------