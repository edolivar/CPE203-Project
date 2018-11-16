public class Activity implements Action {

    private AbstractEntityMain entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Activity(AbstractEntityMain entity, WorldModel world,
                    ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = 0;
    }

    public AbstractEntityMain getEntity(){
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
        /*switch (this.entity.getClass()this.entity.getKind())*/
        if (this.entity instanceof EntityMain) {
            this.entity.executeActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
        }

        else {
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            this.getEntity().getClass()));
        }
    }


}
