/**
 * @(#)Elevator.java
 *
 * Elevator application
 *
 * @author  Mondestin
 * @version 2.00
 */

public class Person
{
	//flags
	private boolean pStdFlag;
	private int floorFlagTest;

	//coordinates
	private int[] yTest;
	private int yPstd;
	private int xPstd = 300;


	//constructor
	public Person( int[] yTest, int floorFlagTest )
	{
		this.yTest = yTest;
		this.floorFlagTest = floorFlagTest;
	}
	 //-------------------------------------------------- ///////////////// -----------------------------------------------------

	//initially creates person when program starts
	public void createPerson()
	{
		pStdFlag = true;
	}
	 //-------------------------------------------------- ///////////////// -----------------------------------------------------

	//retuns person created flag
	public boolean personCreated()
	{
		return pStdFlag;
	}
	 //-------------------------------------------------- ///////////////// -----------------------------------------------------

	//makes the person disappear
	public void disablePerson( boolean pStdFlag )
	{
		this.pStdFlag = !pStdFlag;
	}
	 //-------------------------------------------------- ///////////////// -----------------------------------------------------

	//returns x coordinate of person
	public int getPerson_x()
	{
		return xPstd;
	}
	 //-------------------------------------------------- ///////////////// -----------------------------------------------------

	//sets y coordinate of person
	public void setPerson_y( int value )
	{
		this.yPstd = value;
	}
	 //-------------------------------------------------- ///////////////// -----------------------------------------------------

	//returns y coordinate of person
	public int getPerson_y()
	{
		return yPstd;
	}
	//--------------------------------------------------- ///////////////// -----------------------------------------------------
}
