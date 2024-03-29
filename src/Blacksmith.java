import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public class Blacksmith extends Entity {

    public Blacksmith(String id, Point position, List<PImage> images) {
        super(id, 0, position, 0, images);
    }

    public void scheduleActions(EventScheduler scheduler,
                         WorldModel world, ImageStore imageStore) {
    }

    public Boolean instanceCheck (Entity e) {
        return e.accept(new BlacksmithEntityVisitor());
    }

    public <R> R accept (EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
