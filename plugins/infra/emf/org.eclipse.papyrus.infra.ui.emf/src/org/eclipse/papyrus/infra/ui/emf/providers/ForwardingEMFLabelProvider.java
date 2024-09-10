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

package org.eclipse.papyrus.infra.ui.emf.providers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IViewerNotification;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;

import com.google.common.collect.MapMaker;

/**
 * A specialized {@link StandardEMFLabelProvider} that forwards change notifications
 * from the label-providers to which it delegates. By default, it
 * {@linkplain #isFireLabelUpdateNotifications() fires label update notifications}
 * because that is the presumed purpose of using this class in the first place.
 */
public class ForwardingEMFLabelProvider extends StandardEMFLabelProvider {

	// Need to track this because EMF's change notifiers don't prevent multiple attachment
	// of the same listener
	private final ConcurrentMap<IChangeNotifier, INotifyChangedListener> forwards = new MapMaker().weakKeys().weakValues().makeMap();

	private INotifyChangedListener forwardingListener;

	public ForwardingEMFLabelProvider() {
		super();

		// I need to support re-entrant changes during notification
		labelProviderListeners = new CopyOnWriteArrayList<>(labelProviderListeners);

		// I am used in contexts where JFace label provider events are needed
		setFireLabelUpdateNotifications(true);
	}

	@Override
	public void dispose() {
		try {
			for (Map.Entry<IChangeNotifier, INotifyChangedListener> next : forwards.entrySet()) {
				next.getKey().removeListener(next.getValue());
			}
			forwards.clear();
		} finally {
			super.dispose();
		}
	}

	@Override
	IItemLabelProvider adapt(AdapterFactory adapterFactory, EObject object) {
		IItemLabelProvider result = super.adapt(adapterFactory, object);

		if (result instanceof IChangeNotifier) {
			// Hook it up for forwarding
			IChangeNotifier notifier = (IChangeNotifier) result;
			if (forwards.putIfAbsent(notifier, getForwardingListener()) == null) {
				notifier.addListener(getForwardingListener());
			}
		}

		return result;
	}

	private INotifyChangedListener getForwardingListener() {
		if (forwardingListener == null) {
			forwardingListener = notification -> notifyChanged(notification);
		}
		return forwardingListener;
	}

	@Override
	public void notifyChanged(Notification notification)
	  {
	    if (isFireLabelUpdateNotifications())
	    {
	      if (!(notification instanceof IViewerNotification) || ((IViewerNotification)notification).isLabelUpdate())
	      {
	    	 // avoid ConcurrentModificationException 
	    	 List<ILabelProviderListener> localCopy = new ArrayList<>(labelProviderListeners);
	    	
	    	 for (ILabelProviderListener labelProviderListener : localCopy)
	        {
	          labelProviderListener.labelProviderChanged(new LabelProviderChangedEvent(this, notification.getNotifier()));
	        }
	      }
	    }
	  }

}
