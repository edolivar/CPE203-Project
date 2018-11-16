import processing.core.PImage;

import java.util.List;

//MinerFull, MinerNotFull, OreBlob, and Quake are the subclasses
public abstract class AbstractEntityAnimation extends AbstractEntityMain{

    private int animationPeriod;

    public AbstractEntityAnimation(String id, int resourceLimit,
                        Point position, int actionPeriod, int animationPeriod,
                        List<PImage> images) {
        //this.kind = kind;
        /*
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        */
        super(id, resourceLimit, position, actionPeriod, images);
        this.animationPeriod = animationPeriod;
    }

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
