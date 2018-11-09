final class Event
{
   private Action action;
   private long time;
   private EntityGeneral entity;

   public Event(Action action, long time, EntityGeneral entity)
   {
      this.action = action;
      this.time = time;
      this.entity = entity;
   }

   public long getTime(){
      return this.time;
   }

   public EntityGeneral getEntity(){
      return this.entity;
   }

   public Action getAction(){
      return this.action;
   }
}
