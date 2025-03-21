
public abstract class MoveGameElement {
	public abstract String getDirection();
	public abstract String getTag(); 
	public abstract int getSpeed();
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract void setSpeed(int newSpeed);
	public abstract void setDirection(String newDirection);
	public  abstract void triggerAction(Board board);	

	public MoveGameElement(int pos_x, int pos_y)
	{
		super();
	}
	protected abstract int getPosX();
	protected abstract int getPosY();
	protected abstract void setPosX(int newPosX);
	protected abstract void setPosY(int newPosY);


	



}
