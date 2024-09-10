/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *
 */
package org.eclipse.papyrus.infra.emf.gmf.util;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.INonDirtying;


/**
 * Utilities for working with potentially non-dirtying EMF, GEF, and GMF commands.
 *
 * @see INonDirtying
 * @see AbstractCommand.NonDirtying
 */
public class CommandUtils {

	public static final Registry REGISTRY = new Registry();

	/**
	 * Not instantiable by clients.
	 */
	private CommandUtils() {
		super();
	}

	public static String getLabel(Object command) {
		return REGISTRY.getLabeller(command).apply(command);
	}

	public static boolean isCompound(Object command) {
		return REGISTRY.hasDecomposer(command);
	}

	public static <T, C extends T> Iterable<T> getChildren(C compoundCommand) {
		return REGISTRY.<T, C> getDecomposer(compoundCommand).apply(compoundCommand);
	}

	public static <T> T chain(T command1, T command2) {
		return REGISTRY.getComposer(command1).apply(command1, command2);
	}

	public static boolean isNonDirtying(Object command) {
		return (command instanceof INonDirtying)
				|| (command instanceof AbstractCommand.NonDirtying);
	}

	public static IUndoableOperation wrap(TransactionalEditingDomain domain, Command command) {
		if (command instanceof AbstractCommand.NonDirtying) {
			return new NonDirtyingEMFCommandOperation(domain, command);
		}
		return new EMFCommandOperation(domain, command);
	}

	public static IUndoableOperation wrap(TransactionalEditingDomain domain, Command command, Map<?, ?> options) {
		if (command instanceof AbstractCommand.NonDirtying) {
			return new NonDirtyingEMFCommandOperation(domain, command, options);
		}
		return new EMFCommandOperation(domain, command, options);
	}

	public static CompoundCommand nonDirtyingEMFCompound() {
		return new NonDirtyingEMFCompoundCommand();
	}

	public static Command chain(Command command1, Command command2) {
		if ((command1 instanceof AbstractCommand.NonDirtying) && (command2 instanceof AbstractCommand.NonDirtying)) {
			return new NonDirtyingEMFCompoundCommand().chain(command1).chain(command2);
		}
		return command1.chain(command2);
	}

	public static CompositeCommand nonDirtyingGMFComposite(String label) {
		return new NonDirtyingGMFCompositeCommand(label);
	}

	public static ICommand compose(ICommand command1, ICommand command2) {
		if ((command1 instanceof INonDirtying) && (command2 instanceof INonDirtying)) {
			return new NonDirtyingGMFCompositeCommand(command1.getLabel()).compose(command1).compose(command2);
		}
		return command1.compose(command2);
	}

	//
	// Nested types
	//

	private static class NonDirtyingEMFCommandOperation extends EMFCommandOperation implements INonDirtying {

		NonDirtyingEMFCommandOperation(TransactionalEditingDomain domain, Command command, Map<?, ?> options) {
			super(domain, checkCommand(command), options);
		}

		NonDirtyingEMFCommandOperation(TransactionalEditingDomain domain, Command command) {
			super(domain, checkCommand(command));
		}

		static Command checkCommand(Command command) {
			if (!(command instanceof AbstractCommand.NonDirtying)) {
				throw new IllegalStateException("Attempt to wrap dirtying command in a non-dirtying operation."); //$NON-NLS-1$
			}
			return command;
		}
	}

	private static class NonDirtyingEMFCompoundCommand extends CompoundCommand implements AbstractCommand.NonDirtying {

		@Override
		public void append(Command command) {
			checkNonDirtying(command);
			super.append(command);
		}

		@Override
		public boolean appendAndExecute(Command command) {
			checkNonDirtying(command);
			return super.appendAndExecute(command);
		}

		@Override
		public boolean appendIfCanExecute(Command command) {
			checkNonDirtying(command);
			return super.appendIfCanExecute(command);
		}

		@Override
		public Command chain(Command command) {
			append(command);
			return this;
		}

		private void checkNonDirtying(Command command) {
			if (!(command instanceof AbstractCommand.NonDirtying)) {
				throw new IllegalArgumentException("Attempt to append a dirtying command to a non-dirtying compound."); //$NON-NLS-1$
			}
		}
	}

	private static class NonDirtyingGMFCompositeCommand extends CompositeCommand implements INonDirtying {

		NonDirtyingGMFCompositeCommand(String label) {
			super(label);
		}

		@Override
		public void add(IUndoableOperation operation) {
			checkNonDirtying(operation);
			super.add(operation);
		}

		private void checkNonDirtying(IUndoableOperation operation) {
			if (!(operation instanceof INonDirtying)) {
				throw new IllegalArgumentException("Attempt to append a dirtying operation to a non-dirtying composite."); //$NON-NLS-1$
			}
		}
	}

	//
	// Nested types
	//

	public static class Registry {
		private final ConcurrentMap<Class<?>, BiFunction<?, ?, ?>> composers = new ConcurrentHashMap<>();
		private final ConcurrentMap<Class<?>, Function<?, ? extends Iterable<?>>> decomposers = new ConcurrentHashMap<>();
		private final ConcurrentMap<Class<?>, Function<?, String>> labellers = new ConcurrentHashMap<>();

		@SuppressWarnings("unchecked")
		private Registry() {
			super();

			registerComposer(Command.class, CommandUtils::chain);
			registerDecomposer(CompoundCommand.class, CompoundCommand::getCommandList);
			registerLabeller(Command.class, Command::getLabel);

			registerComposer(ICommand.class, CommandUtils::compose);
			this.<ICommand, ICompositeCommand> registerDecomposer(ICompositeCommand.class, c -> () -> c.iterator());
			registerLabeller(IUndoableOperation.class, IUndoableOperation::getLabel);
		}

		/**
		 * Registers a function to compose two commands together into a compound of some sort.
		 * 
		 * @param commandType
		 *            the composable command type
		 * @param composer
		 *            the composer function
		 * 
		 * @throws IllegalStateException
		 *             if this {@code commandType} already has a composer registered
		 */
		public <T, C extends T> void registerComposer(Class<T> commandType, BiFunction<? super T, ? super T, ? extends C> composer) {
			if (composers.putIfAbsent(commandType, composer) != null) {
				throw new IllegalStateException(String.format("Composer already registered for %s", commandType.getSimpleName()));
			}
		}

		/**
		 * Registers a function to decompose compounds commands of some type to obtain the composed commands.
		 * 
		 * @param compoundType
		 *            the compound command type
		 * @param decomposer
		 *            the decomposer function
		 * 
		 * @throws IllegalStateException
		 *             if this {@code compoundType} already has ae decomposer registered
		 */
		public <T, C extends T> void registerDecomposer(Class<C> compoundType, Function<? super C, ? extends Iterable<? extends T>> decomposer) {
			if (decomposers.putIfAbsent(compoundType, decomposer) != null) {
				throw new IllegalStateException(String.format("Decomposer already registered for %s", compoundType.getSimpleName()));
			}
		}

		/**
		 * Registers a function to get the label of a command.
		 * 
		 * @param commandType
		 *            the labelled command type
		 * @param labeller
		 *            the labeller function
		 * 
		 * @throws IllegalStateException
		 *             if this {@code commandType} already has a labeller registered
		 */
		public <T> void registerLabeller(Class<T> commandType, Function<? super T, String> labeller) {
			if (labellers.putIfAbsent(commandType, labeller) != null) {
				throw new IllegalStateException(String.format("Labeller already registered for %s", commandType.getSimpleName()));
			}
		}

		@SuppressWarnings("unchecked")
		<T, C extends T> BiFunction<T, T, C> getComposer(T command) {
			return (BiFunction<T, T, C>) composers.entrySet().stream()
					.filter(e -> e.getKey().isInstance(command))
					.map(Map.Entry::getValue)
					.findFirst()
					.orElseThrow(IllegalArgumentException::new);
		}

		<T, C extends T> Function<? super C, ? extends Iterable<T>> getDecomposer(T command) {
			return maybeGetDecomposer(command).orElseThrow(IllegalArgumentException::new);
		}

		boolean hasDecomposer(Object command) {
			return maybeGetDecomposer(command).isPresent();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		<T, C extends T> Optional<Function<? super C, ? extends Iterable<T>>> maybeGetDecomposer(C compound) {
			return (Optional) decomposers.entrySet().stream()
					.filter(e -> e.getKey().isInstance(compound))
					.map(Map.Entry::getValue)
					.findFirst();
		}

		@SuppressWarnings("unchecked")
		<T> Function<T, String> getLabeller(T command) {
			return (Function<T, String>) labellers.entrySet().stream()
					.filter(e -> e.getKey().isInstance(command))
					.map(Map.Entry::getValue)
					.findFirst()
					.orElse(Object::toString);
		}
	}

}
