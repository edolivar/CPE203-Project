import java.util.*;

final class EventScheduler
{
   private PriorityQueue<Event> eventQueue;
   private Map<EntityGeneral, List<Event>> pendingEvents;
   private double timeScale;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }


   public void scheduleEvent(EntityGeneral entity, Action action, long afterPeriod)
   {
      long time = System.currentTimeMillis() +
              (long)(afterPeriod * timeScale);
      Event event = new Event(action, time, entity);

      eventQueue.add(event);

      // update list of pending events for the given entity
      List<Event> pending = pendingEvents.getOrDefault(entity,
              new LinkedList<>());
      pending.add(event);
      pendingEvents.put(entity, pending);
   }

   //CHANGE ENTITY TO ENTITYGENERAL
   public void unscheduleAllEvents(EntityGeneral entity)
   {
      List<Event> pending = this.pendingEvents.remove(entity);

      if (pending != null)
      {
         for (Event event : pending)
         {
            this.eventQueue.remove(event);
         }
      }
   }

   public void removePendingEvent(Event event)
   {
      List<Event> pending = this.pendingEvents.get(event.getEntity());

      if (pending != null)
      {
         pending.remove(event);
      }
   }

   public void updateOnTime(long time)
   {
      while (!this.eventQueue.isEmpty() &&
              this.eventQueue.peek().getTime() < time)
      {
         Event next = this.eventQueue.poll();

         removePendingEvent(next);

         next.getAction().executeAction(this);
      }
   }
}
