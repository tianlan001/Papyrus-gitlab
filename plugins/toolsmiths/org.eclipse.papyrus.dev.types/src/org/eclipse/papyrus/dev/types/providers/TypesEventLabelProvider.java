/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.dev.types.providers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.papyrus.infra.types.core.notification.TypesEventsChain;
import org.eclipse.papyrus.infra.types.core.notification.events.TypesEventKind;
import org.eclipse.papyrus.infra.types.core.notification.events.ITypesEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;


public class TypesEventLabelProvider extends org.eclipse.jface.viewers.LabelProvider implements IColorProvider {

	@Override
	public Image getImage(Object element) {

		return null;
	}

	@Override
	public String getText(Object element) {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd - hh:mm:ss:SSS");
		if (element instanceof ITypesEvent) {
			calendar.setTimeInMillis(((ITypesEvent) element).getTimestamp());
			result += "[" + formater.format(calendar.getTime()) + "] " + ((ITypesEvent) element).getEventName();
		} else if (element instanceof TypesEventsChain) {
			calendar.setTimeInMillis(((TypesEventsChain) element).getTimestamp());
			result += "[" + formater.format(calendar.getTime()) + "] " + ((TypesEventsChain) element).getName();
		} else if (element instanceof Map.Entry) {
			result += ((Map.Entry<?, ?>) element).getKey().toString();
		} else {
			result += element.toString();
		}
		return result;
	}

	Color yellow = new Color(Display.getCurrent(), new RGB(255, 200, 0));
	Color green = new Color(Display.getCurrent(), new RGB(0, 128, 0));
	Color red = new Color(Display.getCurrent(), new RGB(255, 0, 0));
	Color black = new Color(Display.getCurrent(), new RGB(0, 0, 0));
	Color blue = new Color(Display.getCurrent(), new RGB(0, 0, 255));


	/**
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Color getForeground(Object element) {
		if (element instanceof ITypesEvent) {
			if (((ITypesEvent) element).getEventType().equals(TypesEventKind.Disapproved)) {
				return red;
			} else if (((ITypesEvent) element).getEventType().equals(TypesEventKind.Unexecutable)) {
				return red;
			} else if (((ITypesEvent) element).getEventType().equals(TypesEventKind.Executable)) {
				return green;
			} else if (((ITypesEvent) element).getEventType().equals(TypesEventKind.Approved)) {
				return green;
			} else if (((ITypesEvent) element).getEventType().equals(TypesEventKind.Identity)) {
				return yellow;
			} else if (((ITypesEvent) element).getEventType().equals(TypesEventKind.RequestConfiguration)) {
				return blue;
			}
		} else if (element instanceof Map.Entry) {
			Object value = ((Map.Entry<?, ?>) element).getValue();
			if (value instanceof Collection) {
				return checkWorst((Collection<?>) value, true);
			}
		} else if (element instanceof TypesEventsChain) {
			return checkWorst(((TypesEventsChain) element).getAllEvents(), false);
		}
		return black;
	}

	/**
	 * @param value
	 */
	private Color checkWorst(Collection<?> value, boolean considerRequestConfiguration) {
		Color result = green;
		for (Object object : value) {
			if (((ITypesEvent) object).getEventType().equals(TypesEventKind.Disapproved)) {
				return red;
			} else if (((ITypesEvent) object).getEventType().equals(TypesEventKind.Unexecutable)) {
				return red;
			} else if (((ITypesEvent) object).getEventType().equals(TypesEventKind.Identity)) {
				if (result == green) {
					result = yellow;
				}
			} else if (((ITypesEvent) object).getEventType().equals(TypesEventKind.RequestConfiguration)) {
				if (considerRequestConfiguration) {
					if (result != yellow && result != red) {
						result = blue;
					}
				}
			}
		}
		return result;
	}

	/**
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Color getBackground(Object element) {
		
		return null;
	}

}
