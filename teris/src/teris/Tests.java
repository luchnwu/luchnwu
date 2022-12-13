package teris;

public class Tests {
    public static void main(String[] args) {
//        System.out.println(Images.T.getImageLoadStatus());
        Tetromino tetromino = Tetromino.randomOne();
        System.out.println(tetromino.toString());
    }
}
