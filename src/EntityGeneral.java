import java.util.List;
import processing.core.PImage;

public interface EntityGeneral {

    Point getPosition();
    void setPosition(Point point);
    List<PImage> getImages();
    int getImageIndex();
    //PImage getCurrentImage(Object entity);
    void nextImage();
    boolean adjacent(Point p1, Point p2);

}
