import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends AbstractEntityAnimation {

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

    //private static final Random rand = new Random();

    public MinerNotFull(String id, int resourceLimit,
                        Point position, int actionPeriod, int animationPeriod,
                        List<PImage> images) {
        //this.kind = kind;
        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    /*
    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point point) {
        this.position = point;
    }

    public List<PImage> getImages() { return this.images; }

    public int getImageIndex() { return this.imageIndex; }

    public int getAnimationPeriod() { return this.animationPeriod; }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    public boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }

    */
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(),
                Ore.class);

        if (!notFullTarget.isPresent() ||
                !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveToNotFull(WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        //getPosition()
        if (adjacent(this.getPosition(), target.getPosition()))
        {
            //CHECK
            setResourceCount(this.getResourceCount() + 1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            //getPosition()
            Point nextPos = this.nextPositionMiner(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public boolean transformNotFull(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getResourceCount() >= this.getResourceLimit())
        {
            MinerFull miner = new MinerFull(this.getID(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public Point nextPositionMiner(WorldModel world,
                                   Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x,
                    this.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }

    //animationPeriod
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
                scheduler.scheduleEvent(this,
                        new Activity(this, world, imageStore),
                        this.getActionPeriod());
                scheduler.scheduleEvent(this,
                        new Animation(this, 0), this.getAnimationPeriod());
    }

}
