/**
 * Property of XpertSoftware. All rights reserved.
 * User: Admin
 * Date: Apr 15, 2005 - 5:32:21 PM
 */
package xpertss.threads;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.function.Predicate;

/**
 * Utility methods for working with threads, locks, and thread factories.
 */
@SuppressWarnings("ALL")
public final class Threads {

   private static final Map<String,ThreadFactory> threadFactories = new ConcurrentHashMap<>();

   private Threads() { }


   /**
    * A static runnable that does nothing simulating a traditional NO OP
    */
   public static final Runnable NO_OP = new Runnable() { @Override public void run() { /* Do Nothing */ } };


   /**
    * Causes the current thread to wait until it is signalled or interrupted.
    * <p>
    * This method returns {@code true} if the wait was released due to being
    * signaled, {@code false} if the thread is interrupted externally.
    * <p>
    * The calling thread's interrupted status will reflect whether it was
    * interrupted.
    * <p>
    * If the calling thread does not hold the lock on the specified object
    * an {@link IllegalMonitorStateException} will be thrown.
    *
    * @throws NullPointerException If the {@code condition} is {@code null}
    * @throws IllegalMonitorStateException if the calling thread does not
    *       hold the lock on the given object.
    */
   public static boolean await(Condition condition)
   {
      try {
         condition.await();
         return true;
      } catch(InterruptedException e) {
         Thread.currentThread().interrupt();
      }
      return false;
   }

   /**
    * Causes the current thread to wait until it is signalled or interrupted,
    * or the specified waiting time elapses.
    * <p>
    * This method returns {@code true} if the condition was released due to
    * being signaled, {@code false} if the timeout had expired or the thread
    * was interrupted externally.
    * <p>
    * The calling thread's interrupted status will reflect whether it was
    * interrupted.
    *
    * @throws NullPointerException If either {@code condition} or {@code unit}
    *       are {@code null}
    * @throws IllegalMonitorStateException if the calling thread does not
    *       hold the lock on the given object.
    */
   public static boolean await(Condition condition, long timeout, TimeUnit unit)
   {
      try {
         return condition.await(timeout, unit);
      } catch(InterruptedException e) {
         Thread.currentThread().interrupt();
      }
      return false;
   }



   /**
    * Causes the calling thread to sleep for the specified amount of time.
    * It returns {@code true} if it waited for the specified time or {@code
    * false} if the sleep was interrupted.
    * <p>
    * The calling thread's interrupted status will reflect whether it was
    * interrupted.
    *
    * @throws NullPointerException if the given {@code unit} is {@code null}
    */
   public static boolean sleep(long time, TimeUnit unit)
   {
      try {
         unit.sleep(time);
         return true;
      } catch(InterruptedException ie) {
         Thread.currentThread().interrupt();
      }
      return false;
   }



   /**
    * Join with the specified thread. This will return {@code true} if the join
    * was accomplished or {@code false} if the join was interrupted.
    * <p>
    * A {@link ThreadAccessException} is thrown if the calling thread is the
    * same as the specified thread.
    * <p>
    * The calling thread's interrupted status will reflect whether it was
    * interrupted.
    *
    * @throws NullPointerException If the given {@code thread} is {@code null}
    * @throws ThreadAccessException If the calling thread is attempting to join
    *       with itself.
    */
   public static boolean join(Thread thread)
   {
      if(thread == Thread.currentThread()) throw new ThreadAccessException();
      try {
         thread.join();
         return isTerminated(thread);
      } catch(InterruptedException ie) {
         Thread.currentThread().interrupt();
      }
      return false;
   }

   /**
    * Join on the specified thread if it can be done in the specified timeout. It
    * returns {@code true} if the join was successful, {@code false} if the join
    * timed out or was interrupted externally.
    * <p>
    * A {@link ThreadAccessException} is thrown if the calling thread is the same
    * as the specified thread.
    * <p>
    * The calling thread's interrupted status will reflect whether it was
    * interrupted.
    *
    * @throws NullPointerException If either {@code thread} or {@code unit} are
    *       {@code null}
    * @throws ThreadAccessException If the calling thread is attempting to join
    *       with itself.
    */
   public static boolean join(Thread thread, long timeout, TimeUnit unit)
   {
      if(thread == Thread.currentThread()) throw new ThreadAccessException();
      try {
         unit.timedJoin(thread, timeout);
         return isTerminated(thread);
      } catch(InterruptedException ie) {
         Thread.currentThread().interrupt();
      }
      return false;
   }




   /**
    * Null-safe method that returns {@code true} if the specified thread is
    * {@code null} or its current state reflects the terminated state.
    */
   public static boolean isTerminated(Thread thread)
   {
      return thread == null || thread.getState() == Thread.State.TERMINATED;
   }


   /**
    * Returns {@code true} if the calling thread is the <b>main</b> thread,
    * {@code false} otherwise.
    */
   public static boolean isMainThread()
   {
      return Thread.currentThread().getId() == 1;
   }


   /**
    * Report an uncaught exception on this thread's uncaught exception handler.
    *
    * @param t The exception or error to report
    */
   public static void report(Throwable t)
   {
      Thread current = Thread.currentThread();
      current.getUncaughtExceptionHandler().uncaughtException(current, t);
   }


   /**
    * Utility method that returns the current thread's thread group. This method
    * is the analog of {@link Thread#currentThread()}.
    */
   public static ThreadGroup currentGroup()
   {
      return Thread.currentThread().getThreadGroup();
   }




   /**
    * Returns a snapshot set of threads that belong to the current thread's thread
    * group.
    */
   public static Set<Thread> getThreads()
   {
      return getThreads(currentGroup(), (Predicate) null);
   }

   /**
    * Returns a snapshot set of threads that belong to the given thread group.
    *
    * @throws NullPointerException if the given group is {@code null}
    */
   public static Set<Thread> getThreads(ThreadGroup group)
   {
      return getThreads(group, (Predicate) null);
   }


   /**
    * Returns a snapshot set of threads that belong to the current thread's thread
    * group and that comply with the given predicate. This will return all threads
    * within the current thread group if the given predicate is {@code null}.
    */
   public static Set<Thread> getThreads(Predicate<Thread> predicate)
   {
      return getThreads(currentGroup(), predicate);
   }

   /**
    * Returns a snapshot set of threads that belong to the given thread group and
    * comply with the given predicate. This will return all threads within the
    * group if the given predicate is {@code null}.
    *
    * @throws NullPointerException if the given group is {@code null}
    */
   public static Set<Thread> getThreads(ThreadGroup group, Predicate<Thread> predicate)
   {
      Set<Thread> result = new LinkedHashSet<>(group.activeCount());
      Thread[] threads = new Thread[group.activeCount()];
      int size = group.enumerate(threads, false);
      for(int i = 0; i < size; i++) {
         if(predicate == null || predicate.test(threads[i])) result.add(threads[i]);
      }
      return Collections.unmodifiableSet(result);
   }




   /**
    * Find the first thread with the given name.
    * <p>
    * This will always scan the current thread's thread group and may also
    * include its descendants if requested to do so.
    *
    * @param name The name of the thread to find.
    * @param recurse Boolean indicating if the current thread's descendant
    *                groups should also be scanned recursively.
    * @return The first thread with the given name or {@code null} if one
    *          couldn't be found
    */
   public static Thread findThread(String name, boolean recurse)
   {
      return findThread(currentGroup(), name, recurse);
   }

   /**
    * Find the first thread with the given name.
    * <p>
    * This will always scan the specified thread group and may also include its
    * descendants if requested to do so.
    *
    * @param group The thread group to being the search
    * @param name The name of the thread to find.
    * @param recurse Boolean indicating if the specified thread group's
    *                descendants should also be scanned recursively.
    * @return The first thread with the given name or {@code null} if one
    *          couldn't be found
    * @throws NullPointerException If the given thread group is {@code null}
    */
   public static Thread findThread(ThreadGroup group, String name, boolean recurse)
   {
      Thread[] threads = new Thread[group.activeCount()];
      int size = group.enumerate(threads, false);
      for(int i = 0; i < size; i++) {
         if(threads[i].getName().equals(name)) return threads[i];
      }
      if(recurse) {
         Thread result = null;
         for(ThreadGroup g : getGroups(group)) {
            if((result = findThread(g, name, recurse)) != null) {
               return result;
            }
         }
      }
      return null;
   }


   /**
    * Find the thread with the given id.
    * <p>
    * This will always scan the current thread's thread group and may also
    * include its descendants if requested to do so.
    *
    * @param id The id of the thread to find.
    * @param recurse Boolean indicating if the current thread's descendant
    *                groups should also be scanned recursively.
    * @return The thread with the given id or {@code null} if it couldn't be
    *          found
    */
   public static Thread findThread(long id, boolean recurse)
   {
      return findThread(currentGroup(), id, recurse);
   }

   /**
    * Find the first thread with the given name.
    * <p>
    * This will always scan the specified thread group and may also include its
    * descendants if requested to do so.
    *
    * @param group The thread group to being the search
    * @param id The id of the thread to find.
    * @param recurse Boolean indicating if the specified thread group's descendants
    *                should also be scanned recursively.
    * @return The thread with the given id or {@code null} if it couldn't be
    *          found
    * @throws NullPointerException If the given thread group is {@code null}
    */
   public static Thread findThread(ThreadGroup group, long id, boolean recurse)
   {
      Thread[] threads = new Thread[group.activeCount()];
      int size = group.enumerate(threads, false);
      for(int i = 0; i < size; i++) {
         if(threads[i].getId() == id) return threads[i];
      }
      if(recurse) {
         Thread result = null;
         for(ThreadGroup g : getGroups(group)) {
            if((result = findThread(g, id, recurse)) != null) {
               return result;
            }
         }
      }
      return null;
   }










   /**
    * Utility method to return the root thread group.
    */
   public static ThreadGroup rootGroup()
   {
      for(ThreadGroup g = currentGroup(); ; g = g.getParent()) if(g.getParent() == null) return g;
   }



   /**
    * Returns the first thread group with the given name.
    * <p>
    * This will always scan the current thread's thread group and it's
    * descendants.
    *
    * @param name The name of the thread group to find.
    * @return The thread group or {@code null} if one could not be found.
    */
   public static ThreadGroup findGroup(String name)
   {
      return findGroup(currentGroup(), name);
   }

   /**
    * Returns the first thread group with the given name.
    * <p>
    * This will always scan the specified thread group and it's descendants.
    *
    * @param name The name of the thread group to find.
    * @return The thread group or {@code null} if one could not be found.
    * @throws NullPointerException if the group is {@code null}
    */
   public static ThreadGroup findGroup(ThreadGroup group, String name)
   {
      if(group.getName().equals(name)) return group;
      for(ThreadGroup g : getGroups(group)) {
         ThreadGroup ret = findGroup(g, name);
         if(ret != null) return ret;
      }
      return null;
   }




   /**
    * Returns a possibly empty set of thread groups that are immediate descendants
    * of the current thread's thread group.
    */
   public static Set<ThreadGroup> getGroups()
   {
      return getGroups(currentGroup(), null);
   }

   /**
    * Returns a possibly empty set of thread groups that are immediate descendants of
    * the specified thread group.
    *
    * @param parent The group to obtain descendant groups from
    * @throws NullPointerException If the thread group is {@code null}
    */
   public static Set<ThreadGroup> getGroups(ThreadGroup parent)
   {
      return getGroups(parent, null);
   }

   /**
    * Returns a possibly empty set of thread groups that are immediate descendants
    * of the current thread's thread group and that comply with the given predicate.
    *
    * @param predicate The predicate a descendant must comply with
    */
   public static Set<ThreadGroup> getGroups(Predicate<ThreadGroup> predicate)
   {
      return getGroups(currentGroup(), predicate);
   }

   /**
    * Returns a possibly empty set of thread groups that are immediate descendants
    * of the specified thread group and that comply with the given predicate.
    *
    * @param group The group to obtain descendant groups from
    * @param predicate The predicate a descendant must comply with
    * @throws NullPointerException If the given group is {@code null}
    */
   public static Set<ThreadGroup> getGroups(ThreadGroup group, Predicate<ThreadGroup> predicate)
   {
      Set<ThreadGroup> result = new LinkedHashSet<>(group.activeGroupCount());
      ThreadGroup[] groups = new ThreadGroup[group.activeGroupCount()];
      int size = group.enumerate(groups, false);
      for(int i = 0; i < size; i++) {
         if(predicate == null || predicate.test(groups[i])) result.add(groups[i]);
      }
      return Collections.unmodifiableSet(result);
   }







   /**
    * Returns an {@link Comparator} that will order threads according to their thread
    * priority in descending order, or highest priority to lowest priority.
    * <p>
    * The given ordering does not support {@code null} elements.
    */
   public static Comparator<Thread> decending()
   {
      return PriorityOrdering.DESCENDING;
   }

   /**
    * Returns an {@link Comparator} that will order threads according to their thread
    * priority in ascending order, or lowest priority to highest priority.
    * <p>
    * The given ordering does not support {@code null} elements.
    */
   public static Comparator<Thread> ascending()
   {
      return PriorityOrdering.ASCENDING;
   }

   private static abstract class PriorityOrdering implements Comparator<Thread> {
      private static final Comparator<Thread> DESCENDING = new PriorityOrdering() {
         @Override public int compare(Thread left, Thread right)
         {
            return Integer.signum(right.getPriority() - left.getPriority());
         }
      };
      private static final Comparator<Thread> ASCENDING = new PriorityOrdering() {
         @Override public int compare(Thread left, Thread right)
         {
            return Integer.signum(left.getPriority() - right.getPriority());
         }
      };
      public abstract int compare(Thread left, Thread right);
   }





   /**
    * Predicate which returns {@code true} if the supplied thread is
    * alive, {@code false} otherwise.
    */
   public static Predicate<Thread> alive()
   {
      return AlivePredicate.INSTANCE;
   }

   private enum AlivePredicate implements Predicate<Thread> {
      INSTANCE;
      @Override public boolean test(Thread input) {
         return input.isAlive();
      }
   }


   /**
    * Predicate which returns {@code true} if the supplied thread is a
    * daemon thread, {@code false} otherwise.
    */
   public static Predicate<Thread> daemon()
   {
      return DaemonPredicate.INSTANCE;
   }

   private enum DaemonPredicate implements Predicate<Thread> {
      INSTANCE;
      @Override public boolean test(Thread input) {
         return input.isDaemon();
      }
   }








   /**
    * Create a new thread factory that will create threads named with the
    * specified prefix. Threads created by this factory will have a
    * normal priority and be non-daemon.
    *
    * @param prefix The thread name prefix to use on all created threads
    */
   public static ThreadFactory newThreadFactory(String prefix)
   {
      return newThreadFactory(prefix, Thread.NORM_PRIORITY, false);
   }

   /**
    * Create a new thread factory that will create threads named with the
    * specified prefix and with the specified daemon state. Threads created
    * by this factory will have a normal priority.
    *
    * @param prefix The thread name prefix to use on all created threads
    * @param daemon The daemon status to create threads as
    */
   public static ThreadFactory newThreadFactory(String prefix, boolean daemon)
   {
      return newThreadFactory(prefix, Thread.NORM_PRIORITY, daemon);
   }

   /**
    * Create a new thread factory that will create threads named with the
    * specified prefix and priority. Threads created by this factory will be
    * non-daemon.
    *
    * @param prefix The thread name prefix to use on all created threads
    * @param priority The priority to create threads with
    */
   public static ThreadFactory newThreadFactory(String prefix, int priority)
   {
      return newThreadFactory(prefix, priority, false);
   }

   /**
    * Create a new thread factory that will create threads with the specified
    * name prefix, the daemon state, and priority.
    *
    * @param prefix The thread name prefix to use on all created threads
    * @param priority The priority to create threads with
    * @param daemon The daemon status to create threads as
    */
   public static ThreadFactory newThreadFactory(String prefix, final int priority, final boolean daemon)
   {
      final ThreadFactory base = getThreadFactory(prefix);
      return new ThreadFactory() {
         @Override public Thread newThread(Runnable r)
         {
            Thread t = base.newThread(r);
            t.setDaemon(daemon);
            t.setPriority(priority);
            return t;
         }
      };
   }

   private static ThreadFactory getThreadFactory(String prefix)
   {
      synchronized(prefix.intern()) {
         ThreadFactory factory = threadFactories.get(prefix);
         if(factory == null) {
            factory = new DefaultThreadFactory(prefix);
            threadFactories.put(prefix, factory);
         }
         return factory;
      }
   }



   private static ThreadGroup threadGroup()
   {
      SecurityManager s = System.getSecurityManager();
      return (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
   }


   /**
    * The default thread factory
    */
   static class DefaultThreadFactory implements ThreadFactory {
      final ThreadGroup group;
      final AtomicInteger threadNumber = new AtomicInteger(0);
      final String prefix;

      DefaultThreadFactory(String prefix)
      {
         group = new ThreadGroup(threadGroup(), prefix);
         this.prefix = prefix;
      }

      @Override
      public Thread newThread(Runnable r)
      {

         return new Thread(group, r, String.format("%s-%d", group.getName(), threadNumber.getAndIncrement()));
      }
   }

}
