import java.util.List;
import java.util.Optional;
import java.util.Random;
import processing.core.PImage;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ShadowMage extends AbstractEntityAnimation {

    private static final Random rand = new Random();

    private static final String QUAKE_KEY = "quake";

    public ShadowMage(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images) {
        super(id, 0, position, actionPeriod, animationPeriod, images);
    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> shadowMageTarget = world.findNearest(this.getPosition(), new MinerNotFull("dead", 0, null, 0, 0, null));
        long nextPeriod = this.getActionPeriod();

        if (shadowMageTarget.isPresent())
        {
            Point tgtPos = shadowMageTarget.get().getPosition();

            if (this.moveToShadowMage(world, shadowMageTarget.get(), scheduler))
            {
                /*
                Quake quake = new Quake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
                */

                ShadowMiner shadowMiner = new ShadowMiner("shadowMiner", 0, tgtPos, 12000, 2000, VirtualWorld.imageStore.getImageList("shadowMiner"));
                world.addEntity(shadowMiner);
                shadowMiner.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                nextPeriod);
    }

    public boolean moveToShadowMage(WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        AstarPathingStrategy singleStep = new AstarPathingStrategy();
        Predicate<Point> canPassThrough = (point) -> world.withinBounds(point) && !world.isOccupied(point);
        BiPredicate<Point, Point> withinReach = (point1, point2) -> adjacent(point1, point2);

        if (adjacent(this.getPosition(), target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            //if path is empty, nextPost is the currentPos. return false.
            List<Point> path = singleStep.computePath(this.getPosition(), target.getPosition(), canPassThrough, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);
            Point nextPos = null;
            if (path.size() != 0) {
                nextPos = path.get(0);
            }

            //Point nextPos = nextPositionOreBlob(world, target.getPosition());
            //!this.getPosition().equals(nextPos)
            if (nextPos != null)
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                Background destroyed = new Background("destroyedGround", VirtualWorld.imageStore.getImageList("destroyedGround"));
                world.setBackground(this.getPosition(), destroyed);
                world.moveEntity(this, nextPos);
                /*
                 * world.setBackground(new Point(nextPos.x + 1, nextPos.y), destroyed);
                 * world.setBackground(new Point(nextPos.x - 1, nextPos.y), destroyed);
                 * world.setBackground(new Point(nextPos.x, nextPos.y + 1), destroyed);
                 * world.setBackground(new Point(nextPos.x, nextPos.y - 1), destroyed);
                 */
            }
            return false;

        }
    }

    /*
    public Point nextPositionOreBlob(WorldModel world,
                                     Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().instanceCheck(new Ore(null, null, 0, null)))))
        {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    //occupant.get() instanceof Ore
                    (occupant.isPresent() && !(occupant.get().instanceCheck(new Ore(null, null, 0, null)))))
            {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }
    */

    public Boolean instanceCheck (Entity e) {
        return e.accept(new OreBlobEntityVisitor());
    }

    public <R> R accept (EntityVisitor<R> visitor) {
        return visitor.visit(this);
    }


}
