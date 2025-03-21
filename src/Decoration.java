
public class Decoration extends FixedGameElement{

	public Decoration(int pos_x, int pos_y) {
		super(pos_x, pos_y);
		// TODO Auto-generated constructor stub
	}
	
	public static String getPathToImage() {
		return "apple.png";
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "decoration";
	}

	@Override
	public void triggerAction(Board board) {
		// TODO Auto-generated method stub
		
	}

}
