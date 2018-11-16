import processing.core.PImage;

import java.util.List;

public class Quake extends AbstractEntityAnimation{

    /*
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    */
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;

    //private static final Random rand = new Random();

    public Quake(Point position, List<PImage> images) {
        //this.kind = kind;
        /*
        this.id = QUAKE_ID;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = 0;
        this.resourceCount = 0;
        this.actionPeriod = QUAKE_ACTION_PERIOD;
        this.animationPeriod = QUAKE_ANIMATION_PERIOD;
        */
        super(QUAKE_ID, 0, position, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD, images);
    }
/*
        public EntityKind getKind(){
            return this.kind;
        }
*/

    /*
    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point point) {
        this.position = point;
    }

    public List<PImage> getImages() { return this.images; }

    public int getImageIndex() { return this.imageIndex; }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public int getAnimationPeriod() { return this.animationPeriod; }

    public boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }
    */

    public void executeActivity(WorldModel world,
                                     ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    /*
    //animationPeriod
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                this.actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation(this, QUAKE_ANIMATION_REPEAT_COUNT),
                this.getAnimationPeriod());

    }
    */
}
