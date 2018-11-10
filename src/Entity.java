import java.util.List;
import processing.core.PImage;

public interface Entity {

    Point getPosition();
    void setPosition(Point point);
    List<PImage> getImages();
    int getImageIndex();
    //PImage getCurrentImage(Object entity);
    void nextImage();
    boolean adjacent(Point p1, Point p2);
    void scheduleActions(EventScheduler scheduler,
                         WorldModel world, ImageStore imageStore);

}
