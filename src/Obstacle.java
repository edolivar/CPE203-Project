import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity {

    public Obstacle(String id, Point position, List<PImage> images) {
        super(id, 0, position, 0, images);
    }

    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore) {
    }

    public <R> R accept (EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
