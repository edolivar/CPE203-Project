public interface ActionInterface {

    Entity getEntity();
    WorldModel getWorld();
    ImageStore getImageStore();
    void executeAction(EventScheduler scheduler);

}
