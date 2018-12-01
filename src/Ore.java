import processing.core.PImage;
import java.util.Random;
import java.util.List;

public class Ore extends AbstractEntityNoAnimation {

    private static final Random rand = new Random();

    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;


    public Ore(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, 0, position, actionPeriod, images);
    }

    public void executeActivity(WorldModel world,
                                          ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        OreBlob blob = new OreBlob(this.getID() + BLOB_ID_SUFFIX,
                pos, this.getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }

    public <R> R accept (EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
