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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test cases for the {@link WritableListWithIterator.Containment} class.
 */
@RunWith(RealmRunner.class)
public class WritableListWithIteratorContainmentTest {

	protected static Realm realm;

	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	private WritableListWithIterator.Containment<MutableString> fixture;


	@Test
	public void testRetainRelease() {
		MutableString a = MutableString.of("a");
		MutableString b = MutableString.of("b");
		MutableString c = MutableString.of("c");

		c.retain(); // Claim this one

		fixture.addAll(Arrays.asList(a, b, c));

		assertThat(b.isDisposed(), is(false));

		fixture.remove(b);

		assertThat(b.isDisposed(), is(true));

		c.release();

		assertThat(c.isDisposed(), is(false)); // The list still has it

		fixture.remove(c);

		assertThat(c.isDisposed(), is(true));
	}

	@Test
	public void testDispose() {
		MutableString a = MutableString.of("a");
		MutableString b = MutableString.of("b");
		MutableString c = MutableString.of("c");

		fixture.addAll(Arrays.asList(a, b, c));

		assertThat(a.isDisposed(), is(false));
		assertThat(b.isDisposed(), is(false));
		assertThat(c.isDisposed(), is(false));

		fixture.dispose();

		assertThat(a.isDisposed(), is(true));
		assertThat(b.isDisposed(), is(true));
		assertThat(c.isDisposed(), is(true));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = houseKeeper.cleanUpLater(new WritableListWithIterator.Containment<>(realm));
	}

	static class MutableString extends ReferenceCountedObservable.Value<String> {

		private String value;

		private MutableString() {
			super(realm);
		}

		public static MutableString of(String value) {
			MutableString result = new MutableString();
			result.setValue(value);
			return result;
		}

		@Override
		public Object getValueType() {
			return String.class;
		}

		@Override
		protected void doSetValue(String value) {
			this.value = value;
		}

		@Override
		protected String doGetValue() {
			return value;
		}

	}
}
