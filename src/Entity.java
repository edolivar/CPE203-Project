import processing.core.PImage;

import java.util.List;

public abstract class Entity {

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    //private int animationPeriod;

    public Entity(String id, int resourceLimit,
                         Point position, int actionPeriod, List<PImage> images) {
        //this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = 0;
        this.actionPeriod = actionPeriod;
        //this.animationPeriod = animationPeriod;
    }

    protected String getID() { return this.id; }

    protected Point getPosition() {
        return this.position;
    }

    protected void setPosition(Point point) {
        this.position = point;
    }

    protected List<PImage> getImages() {
        return this.images;
    }

    protected int getImageIndex() {
        return this.imageIndex;
    }

    //PImage getCurrentImage(Object entity);

    protected void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    protected int getActionPeriod() { return this.actionPeriod; }

    protected int getResourceCount() { return this.resourceCount; }

    protected void setResourceCount(int newCount) {
        this.resourceCount = newCount;
    }

    protected int getResourceLimit() { return this.resourceLimit; }

    protected boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }

    protected abstract void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore);

}
