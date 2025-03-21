public class CommandHandler {

    public void resetAquarium(Board board) {
        board.resetAquarium();
    }

    public void setTemperature(Board board, String temp) {
        board.setTemperature(temp);
    }

    public void addInsect(Board board) {
        board.addInsect();
    }

    public void addCoin(Board board) {
        board.addCoin();
    }

    public void setMode(Board board, String mode) {
        board.setMode(mode);
    }

    public void addRandomFish(Board board) {
        board.addRandomFish();
    }

    public void stopAllFishExcept(Board board, String fishType) {
        board.stopAllFishExcept(fishType);
    }


}