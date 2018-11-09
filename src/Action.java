public interface Action {

    EntityGeneral getEntity();
    WorldModel getWorld();
    ImageStore getImageStore();
    void executeAction(EventScheduler scheduler);

}
