/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.profile.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.uml.profile.service.ui.RefreshProfileDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A JUnit rule that interacts (or not) with the editor's Profile Migration Dialog.
 */
public class DialogInteractionRule extends TestWatcher {
	private DialogInteractionKind interactionMode;
	private volatile boolean active;

	private final Lock lock = new ReentrantLock();
	private final Condition dialogInteractionDone = lock.newCondition();
	private final AtomicReference<Boolean> interactionResult = new AtomicReference<>();

	public void assertInteraction() {
		waitForInteraction();

		Boolean result = interactionResult.get();
		assertThat("No dialog interaction occurred", result, notNullValue());
		assertThat("Expected dialog interaction did not occur: " + interactionMode, result, is(true));
	}

	@Override
	protected void starting(Description description) {
		interactionMode = JUnitUtils.getAnnotation(description, DialogInteractionMode.class).value();
		active = true;

		Display.getDefault().asyncExec(new InteractionRunnable());
	}

	@Override
	protected void finished(Description description) {
		active = false;
	}

	protected void waitForInteraction() {
		Display current = Display.getCurrent();

		if (current != null) {
			// This is the UI thread. Flush it
			for (;;) {
				try {
					if (!current.readAndDispatch()) {
						break;
					}
				} catch (Exception e) {
					// It may provide diagnostic information for the test
					e.printStackTrace();
				}
			}
		} else {
			// Synchronize with the UI thread
			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					// Pass
				}
			});
		}
	}

	static <W extends Window> W findWindow(Class<W> windowType) {
		W result = null;

		for (Shell next : Display.getDefault().getShells()) {
			if (windowType.isInstance(next.getData())) {
				result = windowType.cast(next.getData());
				break;
			}
		}

		return result;
	}

	static Button findButton(Composite composite, int code) {
		Button result = null;

		Integer id = code;
		Control[] controls = composite.getChildren();
		for (int i = 0; (i < controls.length) && (result == null); i++) {
			final Control next = controls[i];

			if (next instanceof Button) {
				Button button = (Button) next;
				if (id.equals(button.getData())) {
					result = button;
				}
			} else if (next instanceof Composite) {
				result = findButton((Composite) next, code);
			}
		}

		return result;
	}

	//
	// Nested types
	//

	/**
	 * An asynchronous UI runnable that is schedule to run after the editor has opened,
	 * and so after it has shown the profile migration dialog (if any), to interact with
	 * it if it is open.
	 */
	private class InteractionRunnable implements Runnable {
		@Override
		public void run() {
			Boolean result = false;

			if (active) {
				RefreshProfileDialog dialog = findWindow(RefreshProfileDialog.class);

				switch (interactionMode) {
				case CANCEL:
					if (dialog != null) {
						result = true;
						findButton(dialog.getShell(), IDialogConstants.CANCEL_ID).notifyListeners(SWT.Selection, new Event());
					}
					break;
				case OK:
					if (dialog != null) {
						result = true;
						findButton(dialog.getShell(), IDialogConstants.OK_ID).notifyListeners(SWT.Selection, new Event());
					}
					break;
				default: // NONE
					result = dialog == null;
					break;
				}
			}

			lock.lock();

			try {
				interactionResult.set(result);
				dialogInteractionDone.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}
}
