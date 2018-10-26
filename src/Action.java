final class Action
{
   private ActionKind kind;
   private Entity entity;
   private WorldModel world;
   private ImageStore imageStore;
   private int repeatCount;

   public Action(ActionKind kind, Entity entity, WorldModel world,
      ImageStore imageStore, int repeatCount)
   {
      this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   //ALL FUNCTIONS TESTED AND DEBUGGED

   public Entity getEntity(){
      return this.entity;
   }

   public WorldModel getWorld(){
      return this.world;
   }

   public ImageStore getImageStore(){
      return this.imageStore;
   }

   public void executeAction(EventScheduler scheduler)
   {
      switch (this.kind)
      {
         case ACTIVITY:
            this.executeActivityAction(scheduler);
            break;

         case ANIMATION:
            this.executeAnimationAction(scheduler);
            break;
      }
   }

   public void executeActivityAction(EventScheduler scheduler)
   {
      switch (this.entity.getKind())
      {
         case MINER_FULL:
            this.entity.executeMinerFullActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
            break;

         case MINER_NOT_FULL:
            this.entity.executeMinerNotFullActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
            break;

         case ORE:
            this.entity.executeOreActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
            break;

         case ORE_BLOB:
            this.entity.executeOreBlobActivity(this.getWorld(),
                    this.getImageStore(), scheduler);
            break;

         case QUAKE:
            this.entity.executeQuakeActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
            break;

         case VEIN:
            this.entity.executeVeinActivity(this.getWorld(), this.getImageStore(),
                    scheduler);
            break;

         default:
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            this.getEntity().getKind()));
      }
   }


   public void executeAnimationAction(EventScheduler scheduler)
   {
      this.entity.nextImage();

      if (this.repeatCount != 1)
      {
         scheduler.scheduleEvent(this.entity,
                 createAnimationAction(this.entity,
                         Math.max(this.repeatCount - 1, 0)),
                 this.entity.getAnimationPeriod());
      }
   }

   public static Action createActivityAction(Entity entity, WorldModel world,
                                             ImageStore imageStore)
   {
      return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
   }

   public static Action createAnimationAction(Entity entity, int repeatCount)
   {
      return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
   }
}
