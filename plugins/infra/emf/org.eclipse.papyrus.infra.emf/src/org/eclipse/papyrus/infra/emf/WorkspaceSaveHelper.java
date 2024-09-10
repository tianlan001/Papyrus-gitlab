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

package org.eclipse.papyrus.infra.emf;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ISavedState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.common.collect.ImmutableList;

/**
 * Helper class for delegating workspace save participation.
 */
class WorkspaceSaveHelper {

	/**
	 * Initializes me.
	 */
	WorkspaceSaveHelper() {
		super();
	}

	void initializeSaveDelegates(ISavedState state, List<SaveDelegate> saveDelegates) throws CoreException {
		SaveDelegate[] currentDelegate = new SaveDelegate[] { null };
		state = delegatingSavedState(state, () -> currentDelegate[0]);

		for (SaveDelegate next : saveDelegates) {
			currentDelegate[0] = next;
			next.initializer.accept(state);
		}
	}

	ISaveParticipant createSaveParticipant(List<SaveDelegate> saveDelegates) {
		return new DelegatingSaveParticipant(saveDelegates);
	}

	/**
	 * Creates a save context that provides a view of path mappings specific to the current
	 * save delegate in the sequence.
	 * 
	 * @param context
	 *            the real save context
	 * @param currentDelegate
	 *            a supplier of the current save delegate
	 * 
	 * @return the delegating save context
	 */
	private ISaveContext delegatingSaveContext(ISaveContext context, Supplier<? extends SaveDelegate> currentDelegate) {
		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getDeclaringClass() == ISaveContext.class) {
					switch (method.getName()) {
					case "getFiles":
						if (method.getParameterCount() == 0) {
							// This is our getFiles
							return getFiles();
						}
						break;
					case "map":
						if (method.getParameterCount() == 2) {
							// This is our map(IPath, IPath)
							return map((IPath) args[0], (IPath) args[1]);
						}
						break;
					}
				}

				return method.invoke(context, args);
			}

			private IPath[] getFiles() {
				// Get only those with our particular prefix and strip that prefix
				IPath prefix = currentDelegate.get().pathPrefix;
				return Stream.of(context.getFiles())
						.filter(prefix::isPrefixOf)
						.map(p -> p.makeRelativeTo(prefix))
						.toArray(IPath[]::new);
			}

			private Void map(IPath path, IPath location) {
				// Prepend the supplied path key with our unique prefix
				context.map(currentDelegate.get().pathPrefix.append(path), location);
				return null;
			}
		};

		return (ISaveContext) Proxy.newProxyInstance(getClass().getClassLoader(),
				new Class<?>[] { ISaveContext.class },
				handler);
	}

	/**
	 * Creates a saved state that provides a view of path mappings specific to the current
	 * save delegate in the sequence.
	 * 
	 * @param state
	 *            the real saved state
	 * @param currentDelegate
	 *            a supplier of the current save delegate
	 * 
	 * @return the delegating saved state
	 */
	private ISavedState delegatingSavedState(ISavedState state, Supplier<? extends SaveDelegate> currentDelegate) {
		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if (method.getDeclaringClass() == ISavedState.class) {
					switch (method.getName()) {
					case "getFiles":
						if (method.getParameterCount() == 0) {
							// This is our getFiles
							return getFiles();
						}
						break;
					case "lookup":
						if (method.getParameterCount() == 1) {
							// This is our lookup(IPath)
							return lookup((IPath) args[0]);
						}
						break;
					}
				}

				return method.invoke(state, args);
			}

			private IPath[] getFiles() {
				// Get only those with our particular prefix and strip that prefix
				IPath prefix = currentDelegate.get().pathPrefix;
				return Stream.of(state.getFiles())
						.filter(prefix::isPrefixOf)
						.map(p -> p.makeRelativeTo(prefix))
						.toArray(IPath[]::new);
			}

			private IPath lookup(IPath path) {
				// Prepend the supplied path key with our unique prefix
				return state.lookup(currentDelegate.get().pathPrefix.append(path));
			}
		};

		return (ISavedState) Proxy.newProxyInstance(getClass().getClassLoader(),
				new Class<?>[] { ISavedState.class },
				handler);
	}

	//
	// Nested types
	//

	final static class SaveDelegate {
		final IPath pathPrefix;
		final ISaveParticipant participant;
		final InitAction initializer;

		SaveDelegate(String pathPrefix, ISaveParticipant participant, InitAction initializer) {
			super();

			this.pathPrefix = new Path(pathPrefix);
			this.participant = participant;
			this.initializer = initializer;
		}
	}

	// This delegating participant only handles full saves
	private class DelegatingSaveParticipant implements ISaveParticipant {
		private final List<SaveDelegate> delegates;

		DelegatingSaveParticipant(Collection<? extends SaveDelegate> delegates) {
			super();

			this.delegates = ImmutableList.copyOf(delegates);
		}

		@Override
		public void prepareToSave(ISaveContext context) throws CoreException {
			if (context.getKind() == ISaveContext.FULL_SAVE) {
				iterate(context, ISaveParticipant::prepareToSave);
			}
		}

		@Override
		public void saving(ISaveContext context) throws CoreException {
			if (context.getKind() == ISaveContext.FULL_SAVE) {
				iterate(context, ISaveParticipant::saving);

				// Declare full participation to increment the save number
				context.needSaveNumber();
			}
		}

		@Override
		public void doneSaving(ISaveContext context) {
			if (context.getKind() == ISaveContext.FULL_SAVE) {
				safeIterate(context, ISaveParticipant::doneSaving);
			}
		}

		@Override
		public void rollback(ISaveContext context) {
			if (context.getKind() == ISaveContext.FULL_SAVE) {
				safeIterate(context, ISaveParticipant::rollback);
			}
		}

		void iterate(ISaveContext context, SaveAction saveAction) throws CoreException {
			SaveDelegate[] current = { null };
			ISaveContext privateContext = delegatingSaveContext(context, () -> current[0]);

			for (SaveDelegate next : delegates) {
				current[0] = next;
				saveAction.accept(next.participant, privateContext);
			}
		}

		void safeIterate(ISaveContext context, BiConsumer<? super ISaveParticipant, ? super ISaveContext> saveAction) {
			SaveDelegate[] current = { null };
			ISaveContext privateContext = delegatingSaveContext(context, () -> current[0]);

			for (SaveDelegate next : delegates) {
				current[0] = next;
				saveAction.accept(next.participant, privateContext);
			}
		}
	}

	@FunctionalInterface
	interface InitAction {
		void accept(ISavedState state) throws CoreException;
	}

	@FunctionalInterface
	interface SaveAction {
		void accept(ISaveParticipant participant, ISaveContext context) throws CoreException;
	}
}
