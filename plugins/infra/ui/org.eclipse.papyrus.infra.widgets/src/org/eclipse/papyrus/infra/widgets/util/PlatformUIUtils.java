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

package org.eclipse.papyrus.infra.widgets.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.bindings.IBindingManagerListener;
import org.eclipse.jface.bindings.Trigger;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.bindings.keys.SWTKeySupport;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;

import com.google.common.collect.AbstractIterator;

/**
 * Some utilities for working with the Eclipse Platform UI.
 * 
 * @since 3.0
 */
public class PlatformUIUtils {
	private static final String MODEL_ELEMENT_KEY = "modelElement"; //$NON-NLS-1$

	/**
	 * Not instantiable by clients.
	 */
	private PlatformUIUtils() {
		super();
	}

	/**
	 * Obtains the parent chain, starting from the {@code control} itself, of a control.
	 * 
	 * @param control
	 *            a control
	 * 
	 * @return a stream providing the {@code control} and all of its parent chain,
	 *         in order, up to the topmost control in the shell
	 */
	public static Stream<Control> parentChain(Control control) {
		Iterator<Control> iterator = new AbstractIterator<Control>() {
			private Control next = control;

			@Override
			protected Control computeNext() {
				Control result = next;
				if (result != null) {
					next = result.getParent();
				} else {
					result = endOfData();
				}
				return result;
			}
		};

		Spliterator<Control> spliterator = Spliterators.spliteratorUnknownSize(
				iterator,
				Spliterator.DISTINCT | Spliterator.ORDERED | Spliterator.NONNULL);

		return StreamSupport.stream(spliterator, false);
	}

	/**
	 * Obtains a service, by its interface class, from a {@code control}. This is
	 * useful in contexts where it is not known what
	 * {@linkplain IWorkbenchPart workbench part} contains the {@code control}.
	 * 
	 * @param control
	 *            a control
	 * @param api
	 *            the service interface to retrieve
	 * 
	 * @return the requested service, or {@code null} if it cannot be determined
	 *         (usually because the {@code control} is not in a workbench window)
	 */
	public static <S> S getService(Control control, Class<S> api) {
		Optional<S> result = parentChain(control)
				.map(c -> c.getData(MODEL_ELEMENT_KEY))
				.filter(MPart.class::isInstance).map(MPart.class::cast)
				.map(MPart::getContext)
				.map(ctx -> ctx.get(api))
				.filter(Objects::nonNull)
				.findFirst();

		return result.orElseGet(() -> bestEffortService(api));
	}

	private static <S> S bestEffortService(Class<S> api) {
		S result;

		IWorkbench workbench = PlatformUI.getWorkbench();

		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null) {
			// Take the first available
			IWorkbenchWindow[] allWindows = workbench.getWorkbenchWindows();
			if (allWindows.length > 0) {
				window = allWindows[0];
			}
		}

		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page == null) {
				// Take the first available
				IWorkbenchPage[] allPages = window.getPages();
				if (allPages.length > 0) {
					page = allPages[0];
				}
			}

			if (page != null) {
				IWorkbenchPart activePart = page.getActivePart();
				if (activePart != null) {
					result = activePart.getSite().getService(api);
				} else {
					result = window.getService(api);
				}
			} else {
				result = window.getService(api);
			}
		} else {
			result = workbench.getService(api);
		}

		return result;
	}

	/**
	 * Installs an ad hoc handler for the specified command on a {@code control} that otherwise
	 * would not use the standard Eclipse framework for invocation of that command.
	 * 
	 * @param control
	 *            a control
	 * @param commandID
	 *            the unique identifier of the command to be handled
	 * @param defaultKeystroke
	 *            in case no keybinding is registered, a default keystroke to
	 *            listen for (per the {@link KeyStroke#getInstance(String) keystroke parser}). May be
	 *            {@link null} to respect the user's choice to unbind the command
	 * @param action
	 *            the command handler to invoke
	 */
	public static void handleCommand(Control control, String commandID, String defaultKeystroke, Runnable action) {
		IBindingService bindingService = PlatformUIUtils.getService(control, IBindingService.class);
		Consumer<IBindingService> onBindingsChanged = new Consumer<IBindingService>() {
			private KeyListener keyListener;

			@Override
			public void accept(IBindingService bindings) {
				// Remove current listener, if any
				if (keyListener != null) {
					control.removeKeyListener(keyListener);
					keyListener = null;
				}

				// Recompute our trigger
				TriggerSequence triggerSeq = bindings.getBestActiveBindingFor(commandID);
				if (triggerSeq != null) {
					try {
						Trigger trigger;

						// We can only handle single-stroke triggers
						Trigger[] triggers = triggerSeq.getTriggers();
						if (triggers.length == 1) {
							trigger = triggers[0];
						} else if (defaultKeystroke != null) {
							trigger = KeyStroke.getInstance(defaultKeystroke); // $NON-NLS-1$
						} else {
							trigger = null;
						}

						if (trigger != null) {
							keyListener = new KeyAdapter() {
								@Override
								public void keyPressed(KeyEvent e) {
									int accel = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
									KeyStroke stroke = SWTKeySupport.convertAcceleratorToKeyStroke(accel);
									if (stroke.equals(trigger)) {
										action.run();
									}
								}
							};
							control.addKeyListener(keyListener);
						}
					} catch (ParseException e) {
						Activator.log.error(String.format("Cannot represent %s trigger as a KeyStroke", defaultKeystroke), e); //$NON-NLS-1$
					}
				}
			}
		};

		IBindingManagerListener bindingListener = event -> onBindingsChanged.accept(bindingService);
		bindingService.addBindingManagerListener(bindingListener);

		// Prime the pump
		onBindingsChanged.accept(bindingService);

		control.addDisposeListener(__ -> bindingService.removeBindingManagerListener(bindingListener));
	}
}
