import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MinerFull extends AbstractMiner {

    public MinerFull(String id, int resourceLimit,
                     Point position, int actionPeriod, int animationPeriod,
                     List<PImage> images) {

        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    public void executeActivity(WorldModel world,
                                         ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(),
                new Blacksmith(null, null, null));

        if (fullTarget.isPresent() &&
                this.moveToFull(world, fullTarget.get(), scheduler))
        {
            this.transformFull( world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveToFull(WorldModel world,
                              Entity target, EventScheduler scheduler)
    {
        //getPosition()
        if (adjacent(this.getPosition(), target.getPosition()))
        {
            return true;
        }
        else
        {
            //getPosition()
            //Point nextPos = this.nextPositionMiner(world, target.getPosition());
            AstarPathingStrategy singleStep = new AstarPathingStrategy();
            Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
            BiPredicate<Point, Point> withinReach = (point1, point2) -> adjacent(point1, point2);

            List<Point> path = singleStep.computePath(this.getPosition(), target.getPosition(), canPassThrough, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);
            Point nextPos = null;
            if (path.size() != 0) {
                nextPos = path.get(0);
            }
            /*
            else {
                return false;
            }
            */

            if (nextPos != null)
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public void transformFull(WorldModel world,
                              EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = new MinerNotFull(this.getID(), this.getResourceLimit(),
                this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                this.getImages());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public Boolean instanceCheck (Entity e) {
        return e.accept(new MinerFullEntityVisitor());
    }

    public <R> R accept (EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
