public class Fish  {

	private int speed;
	private String direction;
	
	public Fish(int pos_x, int pos_y)
	{
		super();
	}
	public String getTag() 
	{
		return "fish";
	}

	// diminuer une vie quand on rentre en contact avec un poisson

	public void triggerAction(Board board) 
	{
		board.decreaseLife();
	}

	public void setSpeed(int newSpeed)
	{
		this.speed  = newSpeed;
	}

	public int getSpeed()
	{
		return speed;
	}

	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String newDirection)
	{
		this.direction = newDirection;

	}


}

