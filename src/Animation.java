public class Animation implements Action{

    private AbstractEntityAnimation entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Animation(AbstractEntityAnimation entity, int repeatCount) {
        this.entity = entity;
        this.world = null;
        this.imageStore = null;
        this.repeatCount = repeatCount;
    }

    public AbstractEntityAnimation getEntity(){
        return this.entity;
    }

    public WorldModel getWorld(){
        return this.world;
    }

    public ImageStore getImageStore(){
        return this.imageStore;
    }

    public void executeAction(EventScheduler scheduler)
    {
        this.entity.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.entity,
                    new Animation(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    this.entity.getAnimationPeriod());
        }
    }

}
