public class Demo {
    public static void main(String[] args) {
        System.out.println(Images.AIRPLANE[0].getImageLoadStatus());
        FlyingObjects planes = new BigPlane();
        System.out.println(planes.toString());
    }
}
