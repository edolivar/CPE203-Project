public interface Action {

    Entity getEntity();
    WorldModel getWorld();
    ImageStore getImageStore();
    void executeAction(EventScheduler scheduler);

}
