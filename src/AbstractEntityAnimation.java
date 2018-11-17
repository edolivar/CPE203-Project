import processing.core.PImage;

import java.util.List;

// AbstractMiner, OreBlob, and Quake are the subclasses
public abstract class AbstractEntityAnimation extends AbstractEntityMain{

    private int animationPeriod;

    public AbstractEntityAnimation(String id, int resourceLimit,
                        Point position, int actionPeriod, int animationPeriod,
                        List<PImage> images) {

        super(id, resourceLimit, position, actionPeriod, images);
        this.animationPeriod = animationPeriod;
    }

    /*
    protected abstract void executeActivity(WorldModel world,
                                            ImageStore imageStore, EventScheduler scheduler);
    */

    int getAnimationPeriod(){ return this.animationPeriod; }

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
