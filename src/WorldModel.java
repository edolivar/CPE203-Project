import processing.core.PImage;

import java.util.*;

final class WorldModel {
   private int numRows;
   private int numCols;
   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   private Background background[][];
   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   private EntityGeneral occupancy[][];
   private Set<EntityGeneral> entities;

   private static final int PROPERTY_KEY = 0;

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_ID = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final String MINER_KEY = "miner";
   private static final int MINER_NUM_PROPERTIES = 7;
   private static final int MINER_ID = 1;
   private static final int MINER_COL = 2;
   private static final int MINER_ROW = 3;
   private static final int MINER_LIMIT = 4;
   private static final int MINER_ACTION_PERIOD = 5;
   private static final int MINER_ANIMATION_PERIOD = 6;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_ID = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final int ORE_NUM_PROPERTIES = 5;
   private static final int ORE_ID = 1;
   private static final int ORE_COL = 2;
   private static final int ORE_ROW = 3;
   private static final int ORE_ACTION_PERIOD = 4;

   private static final String SMITH_KEY = "blacksmith";
   private static final int SMITH_NUM_PROPERTIES = 4;
   private static final int SMITH_ID = 1;
   private static final int SMITH_COL = 2;
   private static final int SMITH_ROW = 3;

   private static final String VEIN_KEY = "vein";
   private static final int VEIN_NUM_PROPERTIES = 5;
   private static final int VEIN_ID = 1;
   private static final int VEIN_COL = 2;
   private static final int VEIN_ROW = 3;
   private static final int VEIN_ACTION_PERIOD = 4;
   //public static final int VEIN_ACTION_PERIOD = 4;

   private static final String ORE_KEY = "ore";

   private static final int ORE_REACH = 1;

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public Set<EntityGeneral> getEntities() {
      return this.entities;
   }

   public int getNumRows() {
      return this.numRows;
   }

   public int getNumCols() {
      return this.numCols;
   }

   public WorldModel(int numRows, int numCols, Background defaultBackground) {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
      this.occupancy = new EntityGeneral[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++) {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public Optional<EntityGeneral> findNearest(Point pos,
                                       Class kind) {
      List<EntityGeneral> ofType = new LinkedList<>();
      for (EntityGeneral entity : entities) {
         if (entity.getClass() == kind) {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public void removeEntity(EntityGeneral entity) {
      removeEntityAt(entity.getPosition());
   }


   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public void addEntity(EntityGeneral entity) {
      if (withinBounds(entity.getPosition())) {
         setOccupancyCell(entity.getPosition(), entity);
         this.entities.add(entity);
      }
   }

   public boolean isOccupied(Point pos) {
      return withinBounds(pos) &&
              getOccupancyCell(pos) != null;
   }

   public boolean withinBounds(Point pos) {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public EntityGeneral getOccupancyCell(Point pos) {
      return this.occupancy[pos.y][pos.x];
   }

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public void setOccupancyCell(Point pos, EntityGeneral entity) {
      this.occupancy[pos.y][pos.x] = entity;
   }

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public Optional<EntityGeneral> getOccupant(Point pos) {
      if (isOccupied(pos)) {
         return Optional.of(getOccupancyCell(pos));
      } else {
         return Optional.empty();
      }
   }

   public void removeEntityAt(Point pos) {
      if (withinBounds(pos)
              && getOccupancyCell(pos) != null) {
         //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
         EntityGeneral entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell(pos, null);
      }
   }

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public void moveEntity(EntityGeneral entity, Point pos) {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos)) {
         setOccupancyCell(oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell(pos, entity);
         entity.setPosition(pos);
      }
   }

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public void tryAddEntity(EntityGeneral entity) {
      if (isOccupied(entity.getPosition())) {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }

   public Optional<Point> findOpenAround(Point pos) {
      for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
         for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (withinBounds(newPt) &&
                    !this.isOccupied(newPt)) {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }

   public Background getBackgroundCell(Point pos) {
      return this.background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos, Background background) {
      this.background[pos.y][pos.x] = background;
   }

   public void setBackground(Point pos, Background background) {
      if (this.withinBounds(pos)) {
         setBackgroundCell(pos, background);
      }
   }


   public Optional<PImage> getBackgroundImage(Point pos) {
      if (this.withinBounds(pos)) {
         return Optional.of(Background.getCurrentImage(this.getBackgroundCell(pos)));
      } else {
         return Optional.empty();
      }
   }


   public boolean parseBackground(String[] properties,
                                  WorldModel world, ImageStore imageStore) {
      if (properties.length == BGND_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         this.setBackground(pt,
                 new Background(id, imageStore.getImageList(id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }

   public boolean parseMiner(String[] properties, WorldModel world,
                             ImageStore imageStore) {
      if (properties.length == MINER_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                 Integer.parseInt(properties[MINER_ROW]));
         EntityGeneral entity = new MinerNotFull(properties[MINER_ID],
                 Integer.parseInt(properties[MINER_LIMIT]),
                 pt,
                 Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                 Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                 imageStore.getImageList(MINER_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == MINER_NUM_PROPERTIES;
   }

   public boolean parseObstacle(String[] properties, WorldModel world,
                                ImageStore imageStore) {
      if (properties.length == OBSTACLE_NUM_PROPERTIES) {
         Point pt = new Point(
                 Integer.parseInt(properties[OBSTACLE_COL]),
                 Integer.parseInt(properties[OBSTACLE_ROW]));
         Obstacle entity = new Obstacle(properties[OBSTACLE_ID],
                 pt, imageStore.getImageList(OBSTACLE_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == OBSTACLE_NUM_PROPERTIES;
   }

   public boolean parseOre(String[] properties, WorldModel world,
                           ImageStore imageStore) {
      if (properties.length == ORE_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                 Integer.parseInt(properties[ORE_ROW]));
         Ore entity = new Ore(properties[ORE_ID],
                 pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                 imageStore.getImageList(ORE_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == ORE_NUM_PROPERTIES;
   }

   public boolean parseSmith(String[] properties, WorldModel world,
                             ImageStore imageStore) {
      if (properties.length == SMITH_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                 Integer.parseInt(properties[SMITH_ROW]));
         Blacksmith entity = new Blacksmith(properties[SMITH_ID],
                 pt, imageStore.getImageList(SMITH_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == SMITH_NUM_PROPERTIES;
   }

   public boolean parseVein(String[] properties, WorldModel world,
                            ImageStore imageStore) {
      if (properties.length == VEIN_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                 Integer.parseInt(properties[VEIN_ROW]));
         Vein entity = new Vein(properties[VEIN_ID],
                 pt,
                 Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                 imageStore.getImageList(VEIN_KEY));
         world.tryAddEntity(entity);
      }

      return properties.length == VEIN_NUM_PROPERTIES;
   }

   public boolean processLine(String line, ImageStore imageStore) {
      String[] properties = line.split("\\s");
      if (properties.length > 0) {
         switch (properties[PROPERTY_KEY]) {
            case BGND_KEY:
               return parseBackground(properties, this, imageStore);
            case MINER_KEY:
               return parseMiner(properties, this, imageStore);
            case OBSTACLE_KEY:
               return parseObstacle(properties, this, imageStore);
            case ORE_KEY:
               return parseOre(properties, this, imageStore);
            case SMITH_KEY:
               return parseSmith(properties, this, imageStore);
            case VEIN_KEY:
               return parseVein(properties, this, imageStore);
         }
      }

      return false;
   }

   //CHANGE ENTITY TO ENTITYGENERAL INTERFACE
   public static Optional<EntityGeneral> nearestEntity(List<EntityGeneral> entities,
                                                Point pos) {
      if (entities.isEmpty()) {
         return Optional.empty();
      } else {
         EntityGeneral nearest = entities.get(0);
         int nearestDistance = distanceSquared(nearest.getPosition(), pos);

         for (EntityGeneral other : entities) {
            int otherDistance = distanceSquared(other.getPosition(), pos);

            if (otherDistance < nearestDistance) {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   public static int distanceSquared(Point p1, Point p2) {
      int deltaX = p1.x - p2.x;
      int deltaY = p1.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

}
