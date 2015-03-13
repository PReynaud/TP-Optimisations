package Shape;

/**
 * Created by Pierre on 13/03/2015.
 */
public class Shape {
    private int height;
    private int width;
    private boolean isLying;

    public Shape(int height, int width, boolean isLying) {
        this.height = height;
        this.width = width;
        this.isLying = isLying;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isLying() {
        return isLying;
    }
}
