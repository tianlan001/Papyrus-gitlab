/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
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

package org.eclipse.papyrus.junit.matchers;

import java.util.function.Predicate;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matchers for commands of various flavours, which are defined in nested classes.
 * 
 * @since 2.2
 */
public final class CommandMatchers {

	/**
	 * Not instantiable by clients.
	 */
	private CommandMatchers() {
		super();
	}

	static <C> Matcher<C> executable(Predicate<? super C> canExecute) {
		return new TypeSafeDiagnosingMatcher<C>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("executable");
			}

			@Override
			protected boolean matchesSafely(C item, Description mismatch) {
				boolean result = canExecute.test(item);

				if (!result) {
					mismatch.appendText("unexecutable");
				}

				return result;
			}
		};
	}

	/**
	 * Matchers for GMF commands.
	 * 
	 * @since 2.2
	 */
	public static final class GMF {
		/**
		 * Not instantiable by clients.
		 */
		private GMF() {
			super();
		}

		public static Matcher<org.eclipse.gmf.runtime.common.core.command.ICommand> canExecute() {
			return executable(org.eclipse.gmf.runtime.common.core.command.ICommand::canExecute);
		}
	}

	/**
	 * Matchers for EMF commands.
	 * 
	 * @since 2.2
	 */
	public static class EMF {
		/**
		 * Not instantiable by clients.
		 */
		private EMF() {
			super();
		}

		public static Matcher<org.eclipse.emf.common.command.Command> canExecute() {
			return executable(org.eclipse.emf.common.command.Command::canExecute);
		}
	}

	/**
	 * Matchers for GEF commands.
	 * 
	 * @since 2.2
	 */
	public static final class GEF {
		/**
		 * Not instantiable by clients.
		 */
		private GEF() {
			super();
		}

		public static Matcher<org.eclipse.gef.commands.Command> canExecute() {
			return executable(org.eclipse.gef.commands.Command::canExecute);
		}
	}
}
