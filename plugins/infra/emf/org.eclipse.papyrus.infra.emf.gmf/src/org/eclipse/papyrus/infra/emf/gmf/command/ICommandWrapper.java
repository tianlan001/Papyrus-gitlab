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

package org.eclipse.papyrus.infra.emf.gmf.command;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;

/**
 * A protocol for wrappers that adapt commands of one framework to another.
 *
 * @param <T>
 *            the wrapped command type
 */
public interface ICommandWrapper<T> {
	Registry REGISTRY = new Registry();

	/**
	 * Unwraps the wrapper to get the wrapped command of the other framework.
	 * 
	 * @return the wrapped command (never {@code null})
	 */
	T getWrappedCommand();

	/**
	 * Wraps a {@code command} as another {@code type}. This also handles wrappers
	 * that are not {@link ICommandWrapper}s, such as those provided by GMF or
	 * other projects externally to Papyrus.
	 * 
	 * @param command
	 *            a command to wrap
	 * @param type
	 *            the command type to obtain
	 * 
	 * @return the wrapped command
	 * 
	 * @throws IllegalArgumentException
	 *             if no wrapper is available of the required {@code type}
	 */
	static <F, T> T wrap(F command, Class<T> type) {
		return REGISTRY.getWrapper(command, type).apply(command);
	}

	/**
	 * Queries whether a {@code command} is a wrapper of some other type of command.
	 * This also handles wrappers that are not {@link ICommandWrapper}s, such as
	 * those provided by GMF or other projects externally to Papyrus.
	 * 
	 * @param command
	 *            a command to wrapper
	 * @param type
	 *            the command type that perhaps it wraps
	 * 
	 * @return the wrapped command
	 * 
	 * @throws IllegalArgumentException
	 *             if no wrapper is available of the required {@code type}
	 */
	static <F, T> boolean isWrapper(T command, Class<F> ofType) {
		return REGISTRY.hasUnwrapper(command, ofType);
	}

	/**
	 * Unwraps a {@code command} as another {@code type}. This also handles wrappers
	 * that are not {@link ICommandWrapper}s, such as those provided by GMF or
	 * other projects externally to Papyrus.
	 * 
	 * @param command
	 *            a command to unwrap
	 * @param type
	 *            the command type to obtain
	 * 
	 * @return the wrapped command
	 * 
	 * @throws IllegalArgumentException
	 *             if no wrapper is available of the required {@code type}
	 */
	static <F, T> F unwrap(T command, Class<F> type) {
		return REGISTRY.getUnwrapper(command, type).apply(command);
	}

	//
	// Nested types
	//

	class Registry {
		private final ConcurrentMap<Class<?>, ConcurrentMap<Class<?>, Function<?, ?>>> wrappers = new ConcurrentHashMap<>();
		private final ConcurrentMap<Class<?>, ConcurrentMap<Class<?>, Function<?, ?>>> unwrappers = new ConcurrentHashMap<>();

		private Registry() {
			super();

			registerWrapper(Command.class, ICommand.class, EMFtoGMFCommandWrapper::wrap);
			registerUnwrapper(EMFtoGMFCommandWrapper.class, Command.class, EMFtoGMFCommandWrapper::getWrappedCommand);
			registerWrapper(ICommand.class, Command.class, GMFtoEMFCommandWrapper::wrap);
			registerUnwrapper(GMFtoEMFCommandWrapper.class, ICommand.class, GMFtoEMFCommandWrapper::getWrappedCommand);
		}

		/**
		 * Registers a functions to wrap commands of a source type as a target type.
		 * 
		 * @param fromType
		 *            the source command type
		 * @param toType
		 *            the target command type
		 * @param wrapper
		 *            the wrapper function
		 * 
		 * @throws IllegalStateException
		 *             if this pair of {@code fromType} and {@code toType} already has a wrapper registered
		 */
		public <F, T> void registerWrapper(Class<F> fromType, Class<T> toType, Function<? super F, ? extends T> wrapper) {
			ConcurrentMap<Class<?>, Function<?, ?>> wrappers = this.wrappers.computeIfAbsent(fromType, key -> new ConcurrentHashMap<>());
			if (wrappers.putIfAbsent(toType, wrapper) != null) {
				throw new IllegalStateException(String.format("Wrapper already registered for %s -> %s", fromType.getSimpleName(), toType.getSimpleName()));
			}
		}

		/**
		 * Registers a function to unwrap commands of a source type to obtain the original command type.
		 * 
		 * @param fromType
		 *            the source command type
		 * @param toType
		 *            the target command type
		 * @param unwrapper
		 *            the unwrapper function
		 * 
		 * @throws IllegalStateException
		 *             if this pair of {@code fromType} and {@code toType} already has an unwrapper registered
		 */
		public <F, T> void registerUnwrapper(Class<F> fromType, Class<T> toType, Function<? super F, ? extends T> unwrapper) {
			ConcurrentMap<Class<?>, Function<?, ?>> unwrappers = this.unwrappers.computeIfAbsent(fromType, key -> new ConcurrentHashMap<>());
			if (unwrappers.putIfAbsent(toType, unwrapper) != null) {
				throw new IllegalStateException(String.format("Unwrapper already registered for %s <- %s", toType.getSimpleName(), fromType.getSimpleName()));
			}
		}

		@SuppressWarnings("unchecked")
		<F, T> Function<F, T> getWrapper(F command, Class<T> type) {
			return (Function<F, T>) wrappers.entrySet().stream()
					.filter(e -> e.getKey().isInstance(command))
					.flatMap(e -> e.getValue().entrySet().stream())
					.filter(e -> type.isAssignableFrom(e.getKey()))
					.map(Map.Entry::getValue)
					.findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}

		<F, T> Function<T, F> getUnwrapper(T command, Class<F> type) {
			return maybeGetUnwrapper(command, type).orElseThrow(IllegalArgumentException::new);
		}

		boolean hasUnwrapper(Object command, Class<?> type) {
			return maybeGetUnwrapper(command, type).isPresent();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		<F, T> Optional<Function<T, F>> maybeGetUnwrapper(T command, Class<F> type) {
			return (Optional) unwrappers.entrySet().stream()
					.filter(e -> e.getKey().isInstance(command))
					.flatMap(e -> e.getValue().entrySet().stream())
					.filter(e -> type.isAssignableFrom(e.getKey()))
					.map(Map.Entry::getValue)
					.findFirst();
		}
	}
}
