/*****************************************************************************
 * Copyright (c) 2012 Cedric Dumoulin.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.tests.utils.memoryleak;

import static org.junit.Assert.fail;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Provides an assertion method to verify that an object has been garbage collected.
 * 
 * This method forward to one of the two implementations:
 * <br/>
 * {@link #assertIsGarbageCollected0(WeakReference, long)} 
 * <br/>
 * {@link #assertIsGarbageCollected2(WeakReference, long)}
 * 
 */
public class MemoryLeakUtil {

	private static final int MAX_GC_ITERATIONS = 20;
	private static final int GC_SLEEP_TIME     = 100;

	public static void assertIsGarbageCollected(WeakReference<? extends Object> ref, long timeout)  {
		// Slower method
//		assertIsGarbageCollected0(ref, timeout);
		// Faster method
		assertIsGarbageCollected2(ref, timeout);
	}

  /**
   * Check that the specified Object (referenced through a WeakReference) is garbage collected
   * in the designated time. Otherwise throw an assertion failure.
   * <p>
   * IMPORTANT : in order for this method to work properly and success, you must get rid of all
   * strong references of your object in the method callee. For example : 
   * <pre>
   *  MyObject myObj = new MyObject();
   *  // myObj.work();
   *  // myObj.close();
   *  
   *  // 1. Create WeakReference for the object
   *  WeakReference<MyObject> objWeakRef = new WeakReference<MyObject>(pub1); 
   *  // 2. Remove strong reference for the object
   *  myObj = null;  
   *  // 3. Test object is garbage collected correctly, leaving 10 seconds for the test to succeed
   *  assertObjectIsGarbageCollected(objWeakRef, 10*1000);
   * </pre>
   * <p>
   * <b>Implementation note</b> : this method will trigger a GC through
   * <code>System.gc()</code> every second until the timeout has been
   * reached or the object is detected has garbage collected.
   *  
   * @param wRef the object WeakReference (required to prevent strong reference of object through callstack)   
   * @param timeout the timeout after which this method will consider (should be a multiple of 1000 ms with current implementation)
 * @throws Exception 
   */
  public static void assertIsGarbageCollected0(WeakReference<? extends Object> ref, long timeout)  {

    // already cleared ?
    if( ref.get() == null)
    	return;
    
    for (int i = 0; i < MAX_GC_ITERATIONS; i++) {
        s_runtime.runFinalization();
//        System.err.println("Try to garbage iter " + i);
        try {
			runGC();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (ref.get() == null)
            return ;

        // Pause for a while and then go back around the loop to try again...
        try {
            Thread.sleep(GC_SLEEP_TIME);
        } catch (InterruptedException e) {
            // Ignore any interrupts and just try again...
        }
    }

    fail("Object '" + ref.get() + "' was not be garbage collected ");

  }

  /**
   * Check that the specified Object (referenced through a WeakReference) is garbage collected
   * in the designated time. Otherwise throw an assertion failure.
   * <p>
   * IMPORTANT : in order for this method to work properly and success, you must get rid of all
   * strong references of your object in the method callee. For example : 
   * <pre>
   *  MyObject myObj = new MyObject();
   *  // myObj.work();
   *  // myObj.close();
   *  
   *  // 1. Create WeakReference for the object
   *  WeakReference<MyObject> objWeakRef = new WeakReference<MyObject>(pub1); 
   *  // 2. Remove strong reference for the object
   *  myObj = null;  
   *  // 3. Test object is garbage collected correctly, leaving 10 seconds for the test to succeed
   *  assertObjectIsGarbageCollected(objWeakRef, 10*1000);
   * </pre>
   * <p>
   * <b>Implementation note</b> : this method will trigger a GC through
   * <code>System.gc()</code> every second until the timeout has been
   * reached or the object is detected has garbage collected.
   *  
   * @param wRef the object WeakReference (required to prevent strong reference of object through callstack)   
   * @param timeout the timeout after which this method will consider (should be a multiple of 1000 ms with current implementation)
   */
  public static void assertIsGarbageCollected2(WeakReference<? extends Object> wRef, long timeout) {
    final long PERIOD = 1000; // Perform GC and check every second;

    final long startTime = System.currentTimeMillis();

    // Create a reference queue 
    ReferenceQueue<Object> rq = new ReferenceQueue<Object>();

    // Add some phantom reference object to the queue
    // the pr variable is never read, but there nothing to do with it 
    @SuppressWarnings("unused")
    PhantomReference<Object> pr = new PhantomReference<Object>(wRef.get(), rq);

    // Poll the queue until the Reference is returned or the timeout is reached.
    // If a non null value is returned by poll, it will indicate that the original
    // object has been garbage collected
    Reference<? extends Object> ref = rq.poll();
    while (ref == null) {
      System.gc(); // Trigger GC to help a bit 
      try { Thread.sleep(PERIOD); } catch (InterruptedException ex) {}
      ref = rq.poll();

      // Exit loop if timeout has been reached
      long now = System.currentTimeMillis();
      if ((now - startTime) > timeout) {
        break;
      }
    }



    // If no PhantomReference was found after the specified timeout, the garbage collection was unsuccessful
    if (ref == null) {
      // But before declaring it as a failure, check the WeakRefence
      // -> If the WeakReference has been removed, then the object is not strongly reachable 
      //    anymore so it also mean it has been garbage collected
      Object obj = wRef.get();
      if (obj == null) {
        return;
      }

      fail("Object '" + wRef.get() + "' was not be garbage collected during the timeout of " + timeout + " ms");
    }
  }
  
  
  private static void runGC () throws Exception
  {
      // It helps to call Runtime.gc()
      // using several method calls:
      for (int r = 0; r < 4; ++ r) _runGC ();
  }
  
  private static void _runGC () throws Exception
  {
      long usedMem1 = usedMemory (), usedMem2 = Long.MAX_VALUE;
      for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++ i)
      {
          s_runtime.runFinalization ();
          s_runtime.gc ();
          Thread.yield ();
          
          usedMem2 = usedMem1;
          usedMem1 = usedMemory ();
      }
  }
  private static long usedMemory ()
  {
      return s_runtime.totalMemory () - s_runtime.freeMemory ();
  }
  
  private static final Runtime s_runtime = Runtime.getRuntime ();
}
