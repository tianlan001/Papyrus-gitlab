/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo - CEA LIST (vincent.lorenzo@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.contentprovider;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IContentChangedListener.ContentEvent;

/**
 * @author VL222926
 * @since 2.1
 *
 *        This class has been created to a part of the troubles described in the bug 540218. Now we are able to receive the full EMF notification.
 *
 */
public class NotificationContentEvent extends ContentEvent implements Notification {

	/**
	 * the initial EMF notification
	 */
	private Notification notification;


	/**
	 * Constructor.
	 *
	 * @param type
	 * @param model
	 * @param object
	 */
	public NotificationContentEvent(Notification notification, int type, Object model, Object object) {
		super(type, model, object);
		this.notification = notification;
	}

	/**
	 *
	 * @return
	 * 		the initial EMF notification
	 */
	public Notification getNotification() {
		return this.notification;
	}


	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNotifier()
	 *
	 * @return
	 */
	@Override
	public Object getNotifier() {
		return notification.getNotifier();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getEventType()
	 *
	 * @return
	 */
	@Override
	public int getEventType() {
		return notification.getEventType();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getFeatureID(java.lang.Class)
	 *
	 * @param expectedClass
	 * @return
	 */
	@Override
	public int getFeatureID(Class<?> expectedClass) {
		return notification.getFeatureID(expectedClass);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getFeature()
	 *
	 * @return
	 */
	@Override
	public Object getFeature() {
		return notification.getFeature();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldValue()
	 *
	 * @return
	 */
	@Override
	public Object getOldValue() {
		return notification.getOldValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewValue()
	 *
	 * @return
	 */
	@Override
	public Object getNewValue() {
		return notification.getNewValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#wasSet()
	 *
	 * @return
	 */
	@Override
	public boolean wasSet() {
		return notification.wasSet();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#isTouch()
	 *
	 * @return
	 */
	@Override
	public boolean isTouch() {
		return notification.isTouch();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#isReset()
	 *
	 * @return
	 */
	@Override
	public boolean isReset() {
		return notification.isReset();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getPosition()
	 *
	 * @return
	 */
	@Override
	public int getPosition() {
		return notification.getPosition();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#merge(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 * @return
	 */
	@Override
	public boolean merge(Notification notification) {
		return notification.merge(notification);
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldBooleanValue()
	 *
	 * @return
	 */
	@Override
	public boolean getOldBooleanValue() {
		return notification.getOldBooleanValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewBooleanValue()
	 *
	 * @return
	 */
	@Override
	public boolean getNewBooleanValue() {
		return notification.getNewBooleanValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldByteValue()
	 *
	 * @return
	 */
	@Override
	public byte getOldByteValue() {
		return notification.getOldByteValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewByteValue()
	 *
	 * @return
	 */
	@Override
	public byte getNewByteValue() {
		return notification.getNewByteValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldCharValue()
	 *
	 * @return
	 */
	@Override
	public char getOldCharValue() {
		return notification.getOldCharValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewCharValue()
	 *
	 * @return
	 */
	@Override
	public char getNewCharValue() {
		return notification.getNewCharValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldDoubleValue()
	 *
	 * @return
	 */
	@Override
	public double getOldDoubleValue() {
		return notification.getOldDoubleValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewDoubleValue()
	 *
	 * @return
	 */
	@Override
	public double getNewDoubleValue() {
		return notification.getNewDoubleValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldFloatValue()
	 *
	 * @return
	 */
	@Override
	public float getOldFloatValue() {
		return notification.getOldFloatValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewFloatValue()
	 *
	 * @return
	 */
	@Override
	public float getNewFloatValue() {
		return notification.getNewFloatValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldIntValue()
	 *
	 * @return
	 */
	@Override
	public int getOldIntValue() {
		return notification.getOldIntValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewIntValue()
	 *
	 * @return
	 */
	@Override
	public int getNewIntValue() {
		return notification.getNewIntValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldLongValue()
	 *
	 * @return
	 */
	@Override
	public long getOldLongValue() {
		return notification.getOldLongValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewLongValue()
	 *
	 * @return
	 */
	@Override
	public long getNewLongValue() {
		return notification.getNewLongValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldShortValue()
	 *
	 * @return
	 */
	@Override
	public short getOldShortValue() {
		return notification.getOldShortValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewShortValue()
	 *
	 * @return
	 */
	@Override
	public short getNewShortValue() {
		return notification.getNewShortValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getOldStringValue()
	 *
	 * @return
	 */
	@Override
	public String getOldStringValue() {
		return notification.getOldStringValue();
	}

	/**
	 * @see org.eclipse.emf.common.notify.Notification#getNewStringValue()
	 *
	 * @return
	 */
	@Override
	public String getNewStringValue() {
		return notification.getNewStringValue();
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append(" wraps the EMF Notification: "); //$NON-NLS-1$
		if (null != notification) {
			builder.append(notification.toString());
		} else {
			builder.append("null"); //$NON-NLS-1$
		}
		return builder.toString();
	}
}
