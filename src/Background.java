import java.util.List;
import processing.core.PImage;

final class Background
{
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public int getImageIndex(){
      return this.imageIndex;
   }

   public List<PImage> getImages(){
      return this.images;
   }

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }
}
