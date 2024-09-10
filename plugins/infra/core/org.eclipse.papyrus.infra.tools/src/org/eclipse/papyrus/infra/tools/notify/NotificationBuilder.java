/*****************************************************************************
 * Copyright (c) 2010, 2016 ATOS ORIGIN, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (ATOS ORIGIN INTEGRATION) tristan.faure@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.tools.notify;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.papyrus.infra.tools.Activator;
import org.eclipse.papyrus.infra.tools.spi.INotificationBuilderFactory;


/**
 * A class creating a notification,
 * the run method launch the message according to the value of the attributes
 *
 * @author tristan faure
 * @since 2.0
 *
 */
public class NotificationBuilder implements INotificationBuilder {

	/** The parameters of the notification with the corresponding values */
	protected Map<String, Object> parameters = new HashMap<String, Object>();

	private static final int YES = 1 << 6; // SWT.YES

	private static final int NO = 1 << 7; // SWT.NO

	/**
	 * Set a message for the notification
	 *
	 * @param message
	 *            , the message to display
	 * @return this
	 */
	@Override
	public NotificationBuilder setMessage(String message) {
		parameters.put(MESSAGE, message);
		return this;
	}

	/**
	 * Determines if the notification is asynchronous (don't force the user to read the notification immediately)
	 *
	 * @param asynchronous
	 *            , true if it asynchronous
	 * @return this
	 */
	@Override
	public NotificationBuilder setAsynchronous(boolean asynchronous) {
		parameters.put(ASYNCHRONOUS, asynchronous);
		return this;
	}

	/**
	 * Set a default action for the notification
	 *
	 * @param runnable
	 *            , a runnable triggered when default action of the notification is selected
	 *            The first action added is the default One
	 * @return this
	 */
	@Override
	@SuppressWarnings("unchecked")
	public NotificationBuilder addAction(NotificationRunnable runnable) {
		Collection<NotificationRunnable> runnables = (Collection<NotificationRunnable>) parameters.get(ACTION);
		if (runnables == null) {
			runnables = new LinkedList<NotificationRunnable>();
			parameters.put(ACTION, runnables);
		}
		runnables.add(runnable);
		return this;
	}

	/**
	 * Set a delay if the notification is temporary
	 *
	 * @param delayMs
	 *            , the delay in ms for visibility
	 * @return this
	 */
	@Override
	public NotificationBuilder setDelay(long delayMs) {
		parameters.put(DELAY, delayMs);
		return this;
	}

	/**
	 * Set true if the notification is temporary
	 *
	 * @param temporary
	 * @return this
	 */
	@Override
	public NotificationBuilder setTemporary(boolean temporary) {
		parameters.put(TEMPORARY, temporary);
		return this;
	}

	/**
	 * Set a title for the notification
	 *
	 * @param title
	 *            , the title
	 * @return this
	 */
	@Override
	public NotificationBuilder setTitle(String title) {
		parameters.put(TITLE, title);
		return this;
	}

	/**
	 * Set if the notification has to understand HTML
	 *
	 * @param useHTML
	 * @return this
	 */
	@Override
	public NotificationBuilder setHTML(boolean useHTML) {
		parameters.put(HTML, useHTML);
		return this;
	}

	/**
	 * Set the type of the notification according to {@link Type}
	 *
	 * @param type
	 *            , the desired type
	 * @return this
	 */
	@Override
	public NotificationBuilder setType(Type type) {
		parameters.put(TYPE, type);
		return this;
	}

	/**
	 * Allows the developer to use a specific parameter
	 *
	 * @param name
	 *            , the key of the parameter
	 * @param value
	 *            , the value
	 * @return this
	 */
	@Override
	public NotificationBuilder setParameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

	/**
	 * Creates a notification according to different parameters
	 */
	@Override
	public INotification run() {
		INotification result;

		INotificationBuilderFactory delegator = Activator.getDefault().getNotificationBuilderFactory();
		if (delegator != null) {
			// Create the delegate
			INotificationBuilder delegate = delegator.createNotificationBuilder();

			// Fill it up
			parameters.forEach(delegate::setParameter);

			// And run it
			result = delegate.run();
		} else {
			// Just a simple log notification
			result = new LogNotification(
					(Type) parameters.get(TYPE),
					(String) parameters.get(MESSAGE));
		}

		return result;
	}

	/**
	 * Creates a notification builder already configured to display an information builder
	 *
	 * @return a notification builder
	 */
	public static NotificationBuilder createInformationBuilder() {
		NotificationBuilder builder = new NotificationBuilder();
		return builder;
	}

	/**
	 * Creates a notification builder already configured to display an asynchronous popup
	 *
	 * @param text
	 *            , the text to display
	 * @return a notification builder
	 */
	public static NotificationBuilder createAsyncPopup(String text) {
		return new NotificationBuilder().setAsynchronous(true).setTemporary(true).setMessage(text).setDelay(2000);
	}

	/**
	 * Creates a notification builder already configured to display an asynchronous popup with a specified title
	 *
	 * @param text
	 *            , the text to display
	 * @param title
	 *            , the title of the popup
	 * @return a notification builder
	 */
	public static NotificationBuilder createAsyncPopup(String title, String text) {
		return new NotificationBuilder().setAsynchronous(true).setTemporary(true).setMessage(text).setTitle(title).setDelay(2000);
	}

	/**
	 * Creates a notification builder already configured to display an information popup
	 *
	 * @param text
	 *            , the text to display
	 * @return a notification builder
	 */
	public static NotificationBuilder createInfoPopup(String text) {
		return new NotificationBuilder().setAsynchronous(false).setTemporary(false).setMessage(text).setType(Type.INFO);
	}

	/**
	 * Creates a notification builder already configured to display an warning popup
	 *
	 * @param text
	 *            , the text to display
	 * @return a notification builder
	 */
	public static NotificationBuilder createWarningPopup(String text) {
		return new NotificationBuilder().setAsynchronous(false).setTemporary(false).setMessage(text).setType(Type.WARNING);
	}

	/**
	 * Creates a notification builder already configured to display a popup with question icon
	 *
	 * @param text
	 *            , the text to display
	 * @return a notification builder
	 */
	public static NotificationBuilder createQuestionPopup(String text) {
		return new NotificationBuilder().setAsynchronous(false).setTemporary(false).setMessage(text).setType(Type.QUESTION);
	}

	/**
	 * Creates a notification builder already configured to display a popup with error icon
	 *
	 * @param text
	 *            , the text to display
	 * @return a notification builder
	 */
	public static NotificationBuilder createErrorPopup(String text) {
		return new NotificationBuilder().setAsynchronous(false).setTemporary(false).setMessage(text).setType(Type.ERROR);
	}

	/**
	 * Creates a notification builder already configured to display a yes no question
	 *
	 * @param yes
	 *            , the action to launch if yes is selected
	 * @param no
	 *            , the action to launch if no is selected
	 * @return a notification builder
	 */
	public static NotificationBuilder createYesNo(String message, final Runnable yes, final Runnable no) {
		return new NotificationBuilder().setType(Type.QUESTION).setAsynchronous(false).setTemporary(false).setMessage(message).addAction(new NotificationRunnable() {

			@Override
			public void run(IContext context) {
				if (yes != null) {
					context.put(IContext.ACTION_ID, YES);
					yes.run();
				}
			}

			@Override
			public String getLabel() {
				return "Yes";
			}
		}).addAction(new NotificationRunnable() {

			@Override
			public void run(IContext context) {
				if (no != null) {
					context.put(IContext.ACTION_ID, NO);
					no.run();
				}
			}

			@Override
			public String getLabel() {
				return "No";
			}
		});
	}

	/**
	 * Creates a notification builder already configured to display a yes no question, no runnables are necesary as the user just want the
	 * PopupNotification result
	 * This NotificationRunnable is not intended to be changed to an asynchronous notification for example
	 * When the run method is called use getRsult method in {@link PopupNotification} and test if the value is SWT.YES or SWT.NO
	 *
	 * @param message
	 *            , the message to display
	 *
	 * @return a notification builder
	 */
	public static NotificationBuilder createYesNo(String message) {
		return new NotificationBuilder().setType(Type.QUESTION).setAsynchronous(false).setTemporary(false).setMessage(message).addAction(new NotificationRunnable() {

			@Override
			public void run(IContext context) {
				context.put(IContext.ACTION_ID, YES);
			}

			@Override
			public String getLabel() {
				return "Yes";
			}
		}).addAction(new NotificationRunnable() {

			@Override
			public void run(IContext context) {
				context.put(IContext.ACTION_ID, NO);
			}

			@Override
			public String getLabel() {
				return "No";
			}
		});
	}

	/**
	 * Creates a notification builder already configured to display a yes no question
	 *
	 * @param yes
	 *            , the action to launch if yes is selected
	 * @param no
	 *            , the action to launch if no is selected
	 * @return a notification builder
	 */
	public static NotificationBuilder createYesNo(String message, final NotificationRunnable yes, final NotificationRunnable no) {
		return new NotificationBuilder().setType(Type.QUESTION).setAsynchronous(false).setTemporary(false).setMessage(message).addAction(new NotificationRunnable() {

			@Override
			public void run(IContext context) {
				if (yes != null) {
					context.put(IContext.ACTION_ID, YES);
					yes.run(context);
				}
			}

			@Override
			public String getLabel() {
				return "Yes";
			}
		}).addAction(new NotificationRunnable() {

			@Override
			public void run(IContext context) {
				if (no != null) {
					context.put(IContext.ACTION_ID, NO);
					no.run(context);
				}
			}

			@Override
			public String getLabel() {
				return "No";
			}
		});
	}
}
