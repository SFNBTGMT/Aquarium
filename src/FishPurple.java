public class FishPurple extends MoveGameElement {
    private int pos_x;
    private int pos_y;
    private String direction;
    private int baseSpeed= 5;
    private int speed = baseSpeed;


    public FishPurple(int pos_x, int pos_y) {
        super(pos_x, pos_y);
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.speed = 5; // Vitesse par défaut
        this.direction = "right"; // Direction par défaut
    }
    
    public void updateSpeed(int decorationCount) {
        // Augmenter la vitesse en fonction du nombre de décorations
        this.speed = baseSpeed + decorationCount;
    }

    @Override
    public String getDirection() {
        return direction;
    }

    @Override
    public String getTag() {
		return "fishPurple";
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getWidth() {
        return 30; // Largeur de l'image du poisson
    }

    @Override
    public int getHeight() {
        return 20; // Hauteur de l'image du poisson
    }

    @Override
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    @Override
    public void setDirection(String newDirection) {
        this.direction = newDirection;
    }

    @Override
    public void triggerAction(Board board) {
        board.decreaseLife(); // Action lors d'une collision avec le joueur
    }

    public static String getPathToImage() {
		return "piranha.png";
    }

    public int getPosX() {
        return pos_x;
    }

    public int getPosY() {
        return pos_y;
    }

    public void setPosX(int pos_x) {
        this.pos_x = pos_x;
    }

    public void setPosY(int pos_y) {
        this.pos_y = pos_y;
    }
}

