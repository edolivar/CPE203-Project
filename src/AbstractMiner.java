import processing.core.PImage;

import java.util.List;

public abstract class AbstractMiner extends AbstractEntityAnimation{

    public AbstractMiner(String id, int resourceLimit,
                                   Point position, int actionPeriod, int animationPeriod,
                                   List<PImage> images) {
        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    protected Point nextPositionMiner(WorldModel world,
                                   Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x,
                    this.getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }


}
