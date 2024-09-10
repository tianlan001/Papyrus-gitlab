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

/**
 * Protocol of a pluggable notification builder.
 * 
 * @since 2.0
 */
public interface INotificationBuilder {
	/** asynchronous, determines if the message needs or not to be synchronous with the notification */
	String ASYNCHRONOUS = "asynchronous";

	/** a message displayed in the notification */
	String MESSAGE = "message";

	/** a default action in the notification */
	String ACTION = "default_action";

	/** a delay to display if it is a temporary notification */
	String DELAY = "delay";

	/** determines if the notification is temporary */
	String TEMPORARY = "temporary";

	/** a title displayed in the notification */
	String TITLE = "title";

	/** determines if there is html content in the notification */
	String HTML = "html";

	/** determines the type according to {@link Type} */
	String TYPE = "type";

	/**
	 * Set a message for the notification
	 *
	 * @param message
	 *            , the message to display
	 * @return this
	 */
	INotificationBuilder setMessage(String message);

	/**
	 * Determines if the notification is asynchronous (don't force the user to read the notification immediately)
	 *
	 * @param asynchronous
	 *            , true if it asynchronous
	 * @return this
	 */
	INotificationBuilder setAsynchronous(boolean asynchronous);

	/**
	 * Set a default action for the notification
	 *
	 * @param runnable
	 *            , a runnable triggered when default action of the notification is selected
	 *            The first action added is the default One
	 * @return this
	 */
	INotificationBuilder addAction(NotificationRunnable runnable);

	/**
	 * Set a delay if the notification is temporary
	 *
	 * @param delayMs
	 *            , the delay in ms for visibility
	 * @return this
	 */
	INotificationBuilder setDelay(long delayMs);

	/**
	 * Set true if the notification is temporary
	 *
	 * @param temporary
	 * @return this
	 */
	INotificationBuilder setTemporary(boolean temporary);

	/**
	 * Set a title for the notification
	 *
	 * @param title
	 *            , the title
	 * @return this
	 */
	INotificationBuilder setTitle(String title);

	/**
	 * Set if the notification has to understand HTML
	 *
	 * @param useHTML
	 * @return this
	 */
	INotificationBuilder setHTML(boolean useHTML);

	/**
	 * Set the type of the notification according to {@link Type}
	 *
	 * @param type
	 *            , the desired type
	 * @return this
	 */
	INotificationBuilder setType(Type type);

	/**
	 * Allows the developer to use a specific parameter
	 *
	 * @param name
	 *            , the key of the parameter
	 * @param value
	 *            , the value
	 * @return this
	 */
	INotificationBuilder setParameter(String name, Object value);

	/**
	 * Creates a notification according to different parameters
	 */
	INotification run();

}
