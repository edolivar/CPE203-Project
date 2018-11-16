import processing.core.PImage;
import java.util.List;

public abstract class AbstractEntityMain extends Entity {

    public AbstractEntityMain(String id, int resourceLimit,
                                   Point position, int actionPeriod, List<PImage> images) {
        super(id, resourceLimit, position, actionPeriod, images);
    }

    protected abstract void executeActivity(WorldModel world,
                                            ImageStore imageStore, EventScheduler scheduler);

    /*
    protected abstract void scheduleActions(EventScheduler scheduler,
                               WorldModel world, ImageStore imageStore);
    */


}
