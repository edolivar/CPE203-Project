import java.util.*;

import processing.core.PApplet;
import processing.core.PImage;

final class ImageStore
{
   private Map<String, List<PImage>> images;
   private List<PImage> defaultImages;

    private static final int COLOR_MASK = 0xffffff;
    private static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

   public ImageStore(PImage defaultImage)
   {
      this.images = new HashMap<>();
      defaultImages = new LinkedList<>();
      defaultImages.add(defaultImage);
   }

   public List<PImage> getImageList(String key)
   {
      return images.getOrDefault(key, defaultImages);
   }

    public void loadImages(Scanner in, PApplet screen)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                processImageLine(in.nextLine(), screen);
            }
            catch (NumberFormatException e)
            {
                System.out.println(String.format("Image format error on line %d",
                        lineNumber));
            }
            lineNumber++;
        }
    }

    public void processImageLine(String line, PApplet screen)
    {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2)
        {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1)
            {
                List<PImage> imgs = getImages(key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN)
                {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    public List<PImage> getImages(String key)
    {
        List<PImage> imgs = this.images.get(key);
        if (imgs == null)
        {
            imgs = new LinkedList<>();
            this.images.put(key, imgs);
        }
        return imgs;
    }

    public static void setAlpha(PImage img, int maskColor, int alpha)
    {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++)
        {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
            {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

    public void load(Scanner in, WorldModel world)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                if (!world.processLine(in.nextLine(), this))
                {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
            }
            catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s",
                        lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }
}
