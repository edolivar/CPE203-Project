public interface EntityMain extends EntityGeneral{
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler);
    void scheduleActions(EventScheduler scheduler,
                         WorldModel world, ImageStore imageStore);

}
