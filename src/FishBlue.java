public class FishBlue extends MoveGameElement {
    private int pos_x;
    private int pos_y;
    private int speed;
    private String direction;

    public FishBlue(int pos_x, int pos_y) {
        super(pos_x, pos_y);
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.speed = 8; // Vitesse par défaut
        this.direction = "right"; // Direction par défaut
    }

    @Override
    public String getDirection() {
        return direction;
    }

    @Override
    public String getTag() {
        return "fishBlue";
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
		return "surgeon-fish.png";
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
