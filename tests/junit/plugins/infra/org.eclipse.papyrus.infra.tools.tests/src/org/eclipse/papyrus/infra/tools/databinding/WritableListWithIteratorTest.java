/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.databinding;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable.AutoReleasePool;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test cases for the {@link WritableListWithIterator} class.
 */
@RunWith(RealmRunner.class)
public class WritableListWithIteratorTest {

	protected static Realm realm;

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	private WritableListWithIterator<String> fixture;

	@Test
	public void testRetain() {
		fixture.retain();
		fixture.retain();

		fixture.release();

		assertThat(fixture.isDisposed(), is(false));
	}

	@Test
	public void testRelease() {
		fixture.retain();

		fixture.release();

		assertThat(fixture.isDisposed(), is(true));
	}

	@Test
	public void testAutorelease() throws Exception {
		fixture.retain();

		fixture.autorelease();

		assertThat(fixture.isDisposed(), is(false));

		// Drain the pending autorelease pool
		AutoReleasePool.get(realm).release();

		assertThat(fixture.isDisposed(), is(true));
	}

	@Test
	public void testIterator() {
		fixture.addAll(Arrays.asList("a", "b", "c"));

		ListChangeAssertion<String> changes = ListChangeAssertion.on(fixture);

		Iterator<String> iter = fixture.iterator();
		assertThat(iter.next(), is("a"));
		assertThat(iter.next(), is("b"));
		iter.remove();
		changes.assertRemoved("b");
		assertThat(iter.next(), is("c"));
		assertThat(iter.hasNext(), is(false));
	}

	@Test
	public void testListIterator() {
		fixture.addAll(Arrays.asList("a", "b", "c"));

		ListChangeAssertion<String> changes = ListChangeAssertion.on(fixture);

		ListIterator<String> iter = fixture.listIterator();
		assertThat(iter.next(), is("a"));
		assertThat(iter.next(), is("b"));
		iter.remove();
		changes.assertRemoved("b");
		iter.add("d");
		changes.assertAdded("d");
		assertThat(iter.next(), is("c"));
		iter.set("e");
		changes.assertRemoved("c");
		changes.assertAdded("e");
		assertThat(iter.hasNext(), is(false));
	}

	@Test
	public void testListIterator_int() {
		fixture.addAll(Arrays.asList("a", "b", "c"));

		ListChangeAssertion<String> changes = ListChangeAssertion.on(fixture);

		ListIterator<String> iter = fixture.listIterator(3);
		assertThat(iter.previous(), is("c"));
		assertThat(iter.previous(), is("b"));
		iter.remove();
		changes.assertRemoved("b");
		iter.add("d");
		changes.assertAdded("d");
		assertThat(iter.previous(), is("d"));
		assertThat(iter.previous(), is("a"));
		iter.set("e");
		changes.assertRemoved("a");
		changes.assertAdded("e");
		assertThat(iter.hasPrevious(), is(false));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = houseKeeper.cleanUpLater(new WritableListWithIterator<>(realm));
	}

	static class ListChangeAssertion<E> implements IListChangeListener<E> {
		private List<E> added = new ArrayList<>();
		private List<E> removed = new ArrayList<>();

		public static <E> ListChangeAssertion<E> on(IObservableList<E> list) {
			ListChangeAssertion<E> result = new ListChangeAssertion<>();
			list.addListChangeListener(result);
			return result;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void handleListChange(ListChangeEvent<? extends E> event) {
			event.diff.accept((ListDiffVisitor) new ListDiffVisitor<E>() {
				@Override
				public void handleAdd(int index, E element) {
					added.add(element);
				}

				@Override
				public void handleRemove(int index, E element) {
					removed.add(element);
				}
			});
		}

		public void assertAdded(E element) {
			assertThat(added, hasItem(element));
		}

		public void assertRemoved(E element) {
			assertThat(removed, hasItem(element));
		}

		public void reset() {
			added.clear();
			removed.clear();
		}
	}
}
