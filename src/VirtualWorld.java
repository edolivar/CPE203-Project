import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import processing.core.*;

public final class VirtualWorld
   extends PApplet
{
   private static final int TIMER_ACTION_PERIOD = 100;

   private static final int VIEW_WIDTH = 640;//1200;
   private static final int VIEW_HEIGHT = 480;//720;//
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;

   private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String LOAD_FILE_NAME = "gaia.sav";

   private static final String FAST_FLAG = "-fast";
   private static final String FASTER_FLAG = "-faster";
   private static final String FASTEST_FLAG = "-fastest";
   private static final double FAST_SCALE = 0.5;
   private static final double FASTER_SCALE = 0.25;
   private static final double FASTEST_SCALE = 0.10;

   private static double timeScale = 1.0;

   public static ImageStore imageStore;
   private WorldModel world;
   private WorldView view;
   private EventScheduler scheduler;

   private long next_time;

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup()
   {
      this.imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
         createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
         TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         this.scheduler.updateOnTime(time);
         next_time = time + TIMER_ACTION_PERIOD;
      }

      view.drawViewport();
   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               break;
            case DOWN:
               dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }
         view.shiftView(dx, dy);
      }
   }

   public void mouseClicked() {
      //#ff4081
      //#6a1b9a
      int tileX = (int)mouseX/TILE_WIDTH;
      int tileY = (int)mouseY/TILE_HEIGHT;
      int currentTileX = tileX + view.getViewport().getCol();
      int currentTileY = tileY + view.getViewport().getRow();
      Point tile = new Point(currentTileX, currentTileY);
      String destroyedGround = "destroyedGround";
      Background destroyed = new Background(destroyedGround, imageStore.getImageList(destroyedGround));
      world.setBackground(tile, destroyed);
      world.setBackground(new Point(tile.x + 1, tile.y), destroyed);
      world.setBackground(new Point(tile.x - 1, tile.y), destroyed);
      world.setBackground(new Point(tile.x, tile.y + 1), destroyed);
      world.setBackground(new Point(tile.x, tile.y - 1), destroyed);
      //world.setBackground(new Point(tile.x + 1, tile.y + 1), destroyed);
      //world.setBackground(new Point(tile.x - 1, tile.y + 1), destroyed);
      //world.setBackground(new Point(tile.x + 1, tile.y - 1), destroyed);
      //world.setBackground(new Point(tile.x - 1, tile.y - 1), destroyed);
      //world.setBackground(new Point(tile.x, tile.y + 2), destroyed);
      //world.setBackground(new Point(tile.x + 2, tile.y), destroyed);
      //world.setBackground(new Point(tile.x, tile.y - 2), destroyed);
      //world.setBackground(new Point(tile.x - 2, tile.y), destroyed);
      world.setBackground(new Point(tile.x - 2, tile.y + 1), destroyed);
      world.setBackground(new Point(tile.x - 1, tile.y + 2), destroyed);
      world.setBackground(new Point(tile.x, tile.y + 3), destroyed);
      world.setBackground(new Point(tile.x + 2, tile.y + 1), destroyed);
      world.setBackground(new Point(tile.x + 1, tile.y + 2), destroyed);
      world.setBackground(new Point(tile.x + 3, tile.y), destroyed);
      world.setBackground(new Point(tile.x - 2, tile.y - 1), destroyed);
      world.setBackground(new Point(tile.x - 1, tile.y - 2), destroyed);
      world.setBackground(new Point(tile.x, tile.y - 3), destroyed);
      world.setBackground(new Point(tile.x + 2, tile.y - 1), destroyed);
      world.setBackground(new Point(tile.x + 1, tile.y - 2), destroyed);
      world.setBackground(new Point(tile.x - 3, tile.y), destroyed);
      world.removeEntityAt(tile);
      //scheduler.unscheduleAllEvents(tile);
      String shadowMage = "shadowMage";
      ShadowMage mainShadow = new ShadowMage(shadowMage, tile, 1000, 0, imageStore.getImageList(shadowMage));
      world.addEntity(mainShadow);
      mainShadow.scheduleActions(scheduler, world, imageStore);
      //System.out.printf("%d, %d\n%d, %d\n%d, %d\n", mouseX, mouseY, tileX, tileY, currentTileX, currentTileY);
   }

   public static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
              imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   public static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
          imageStore.loadImages(in, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.load(in, world);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public void scheduleActions(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.getEntities())
      {
         entity.scheduleActions(scheduler, world, imageStore);
      }
   }

   public static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
}
