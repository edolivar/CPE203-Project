import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public class Blacksmith implements EntityGeneral {

    //private EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    //private static final Random rand = new Random();

    public Blacksmith(String id, Point position, List<PImage> images) {
        //this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = 0;
        this.resourceCount = 0;
        this.actionPeriod = 0;
        this.animationPeriod = 0;
    }
/*
        public EntityKind getKind(){
            return this.kind;
        }
*/

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point point) {
        this.position = point;
    }

    public List<PImage> getImages() { return this.images; }

    public int getImageIndex() { return this.imageIndex; }

    public PImage getCurrentImage(Object entity) {
        if (entity instanceof Background) {
            return ((Background) entity).getImages()
                    .get(((Background) entity).getImageIndex());
        } else if (entity instanceof Entity) {
            return ((Entity) entity).getImages().get(((Entity) entity).getImageIndex());
        } else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }

}
