/**
 * Created By: cfloersch
 * Date: 1/8/14
 * Copyright 2013 XpertSoftware
 */
package xpertss.util;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * General utility functions for Queues.
 */
public final class Queues {

   private static final Queue EMPTY_QUEUE = new EmptyQueue<Object>();
   private static final Deque EMPTY_DEQUE = new EmptyDeque<Object>();

   private Queues() { }

   /**
    * Returns the empty queue (immutable). This deque is serializable.
    * <p/>
    * This example illustrates the type-safe way to obtain an empty queue:
    *
    * <blockquote><pre>
    *    Queue<String> s = Queues.emptyQueue();
    * </pre></blockquote>
    */
   @SuppressWarnings("unchecked")
   public static <E> Queue<E> emptyQueue()
   {
      return (Queue<E>) EMPTY_QUEUE;
   }

   /**
    * Returns an empty queue if the specified queue was @{code null}.
    */
   public static <T> Queue<T> emptyIfNull(Queue<T> values)
   {
      return values != null ? values : Queues.<T>emptyQueue();
   }


   /**
    * Returns the empty deque (immutable). This deque is serializable.
    * <p/>
    * This example illustrates the type-safe way to obtain an empty deque:
    * <blockquote><pre>
    *    Deque<String> s = Queues.emptyDeque();
    * </pre></blockquote>
    */
   @SuppressWarnings("unchecked")
   public static <E> Deque<E> emptyDeque()
   {
      return (Deque<E>) EMPTY_DEQUE;
   }

   /**
    * Returns an empty deque if the specified deque was {@code null}.
    */
   public static <T> Deque<T> emptyIfNull(Deque<T> values)
   {
      return values != null ? values : Queues.<T>emptyDeque();
   }



   public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int capacity)
   {
      return new ArrayBlockingQueue<>(capacity);
   }

   public static <E> ArrayDeque<E> newArrayDeque()
   {
      return new ArrayDeque<>();
   }

   public static <E> ConcurrentLinkedDeque<E> newConcurrentLinkedDeque()
   {
      return new ConcurrentLinkedDeque<>();
   }

   public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue()
   {
      return new ConcurrentLinkedQueue<>();
   }

   public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque()
   {
      return new LinkedBlockingDeque<>();
   }

   public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(int capacity)
   {
      return new LinkedBlockingDeque<>(capacity);
   }

   public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue()
   {
      return new LinkedBlockingQueue<>();
   }

   public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(int capacity)
   {
      return new LinkedBlockingQueue<>(capacity);
   }

   public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue()
   {
      return new PriorityBlockingQueue<>();
   }

   public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(Comparator<? super E> comparator)
   {
      return new PriorityBlockingQueue<>(11, comparator);
   }

   public static <E> PriorityQueue<E> newPriorityQueue()
   {
      return new PriorityQueue<>();
   }

   public static <E> PriorityQueue<E> newPriorityQueue(Comparator<? super E> comparator)
   {
      return new PriorityQueue<>(11, comparator);
   }

   public static <E> SynchronousQueue<E> newSynchronousQueue()
   {
      return new SynchronousQueue<>();
   }

   public static <E extends Delayed> DelayQueue<E> newDelayQueue()
   {
      return new DelayQueue<>();
   }


   public static boolean isEmpty(Queue<?> queue)
   {
      return (queue == null) || queue.isEmpty();
   }

   public static int size(Queue<?> queue)
   {
      return (queue == null) ? 0 : queue.size();
   }

   public static boolean isBlocking(Queue<?> queue)
   {
      return queue instanceof BlockingQueue;
   }



   private static class EmptyQueue<E> extends AbstractQueue<E> implements Serializable {

      @Override
      public Iterator<E> iterator() {
         return Collections.emptyIterator();
      }

      @Override
      public int size() { return 0; }

      @Override
      public boolean offer(E e) { throw new UnsupportedOperationException(); }

      @Override
      public E poll() { return null; }

      @Override
      public E peek() { return null; }

      public boolean equals(Object o)
      {
         return (o instanceof Queue) && ((Queue<?>)o).isEmpty();
      }

      public int hashCode() { return 1; }
   }

   private static class EmptyDeque<E> extends AbstractCollection<E> implements Deque<E>, Serializable {

      @Override
      public Iterator<E> iterator()
      {
         return Collections.emptyIterator();
      }

      @Override
      public Iterator descendingIterator()
      {
         return Collections.emptyIterator();
      }

      @Override
      public void addFirst(E o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void addLast(E o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean offerFirst(E o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean offerLast(E o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public E removeFirst() {
         throw new NoSuchElementException();
      }

      @Override
      public E removeLast() {
         throw new NoSuchElementException();
      }

      @Override
      public E pollFirst() {
         return null;
      }

      @Override
      public E pollLast() {
         return null;
      }

      @Override
      public E getFirst() {
         throw new NoSuchElementException();
      }

      @Override
      public E getLast() {
         throw new NoSuchElementException();
      }

      @Override
      public E peekFirst() {
         return null;
      }

      @Override
      public E peekLast() {
         return null;
      }

      @Override
      public boolean removeFirstOccurrence(Object o) {
         return false;
      }

      @Override
      public boolean removeLastOccurrence(Object o) {
         return false;
      }

      @Override
      public boolean add(E o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean offer(E o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public E remove() {
         throw new NoSuchElementException();
      }

      @Override
      public E poll() {
         return null;
      }

      @Override
      public E element() {
         throw new NoSuchElementException();
      }

      @Override
      public E peek() {
         return null;
      }

      @Override
      public void push(E o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public E pop() {
         throw new NoSuchElementException();
      }

      @Override
      public int size() { return 0; }

   }
}
