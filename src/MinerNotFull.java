import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class MinerNotFull extends AbstractMiner {

    public MinerNotFull(String id, int resourceLimit,
                        Point position, int actionPeriod, int animationPeriod,
                        List<PImage> images) {
        //this.kind = kind;
        super(id, resourceLimit, position, actionPeriod, animationPeriod, images);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(),
                new Ore(null, null, 0, null));

        if (!notFullTarget.isPresent() ||
                !this.moveToNotFull(world, notFullTarget.get(), scheduler) ||
                !this.transformNotFull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveToNotFull(WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        //getPosition()
        if (adjacent(this.getPosition(), target.getPosition()))
        {
            //CHECK
            setResourceCount(this.getResourceCount() + 1);
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

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

    public boolean transformNotFull(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getResourceCount() >= this.getResourceLimit())
        {
            MinerFull miner = new MinerFull(this.getID(), this.getResourceLimit(),
                    this.getPosition(), this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public Boolean instanceCheck (Entity e) {
        return e.accept(new MinerNotFullEntityVisitor());
    }

    public <R> R accept (EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }

}
