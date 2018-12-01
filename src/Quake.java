import processing.core.PImage;

import java.util.List;

public class Quake extends AbstractEntityAnimation{

    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;

    public Quake(Point position, List<PImage> images) {
        super(QUAKE_ID, 0, position, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD, images);
    }

    public void executeActivity(WorldModel world,
                                     ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    public <R> R accept (EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
