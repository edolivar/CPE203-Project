import processing.core.PImage;

import java.util.List;

//Vein and Ore are the subclasses
public abstract class AbstractEntityNoAnimation extends AbstractEntityMain{

    public AbstractEntityNoAnimation(String id, int resourceLimit,
                              Point position, int actionPeriod, List<PImage> images) {
        super(id, resourceLimit, position, actionPeriod, images);
    }

    /*
    protected abstract void executeActivity(WorldModel world,
                                            ImageStore imageStore, EventScheduler scheduler);
    */

    protected void scheduleActions(EventScheduler scheduler,
                                   WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                this.getActionPeriod());
    }
}
