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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.types.core.notification.TypesEventsChain;
import org.eclipse.papyrus.infra.types.core.notification.events.AbstractTypesEvent;


public class TypesEventContentProvider implements ITreeContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {
		

	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 *
	 * @param viewer
	 * @param oldInput
	 * @param newInput
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		

	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		List<TypesEventsChain> result = new ArrayList<TypesEventsChain>();
		if (inputElement instanceof List<?>) {
			List<?> inputSet = (List<?>) inputElement;
			for (Object element : inputSet) {
				if (element instanceof TypesEventsChain) {
					result.add((TypesEventsChain) element);
				}
			}
		}

		Collections.sort(result, new Comparator<TypesEventsChain>() {

			@Override
			public int compare(TypesEventsChain o1, TypesEventsChain o2) {
				if (o1.getTimestamp() > o2.getTimestamp()) {
					return -1;
				}
				return 1;
			}
		});


		return result.toArray();
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(Object parentElement) {


		List<Object> result = new ArrayList<Object>();
		if (parentElement instanceof TypesEventsChain) {
			TypesEventsChain typesEventsChain = (TypesEventsChain) parentElement;

			List<AbstractTypesEvent> adviceRequestConfigurationEvents = typesEventsChain.getAdviceRequestConfigurationEvents();
			if (!adviceRequestConfigurationEvents.isEmpty()) {
				result.add(new AbstractMap.SimpleEntry<String, Object>("AdviceRequestConfigurationEvents", adviceRequestConfigurationEvents));
			}
			AbstractTypesEvent editHelperRequestConfigurationEvent = typesEventsChain.getEditHelperRequestConfigurationEvent();
			if (editHelperRequestConfigurationEvent != null) {
				result.add(editHelperRequestConfigurationEvent);
			}
			List<AbstractTypesEvent> adviceApprovalEvents = typesEventsChain.getAdviceApprovalEvents();
			if (!adviceApprovalEvents.isEmpty()) {
				result.add(new AbstractMap.SimpleEntry<String, Object>("AdviceApprovalEvents", adviceApprovalEvents));
			}
			AbstractTypesEvent editHelperApprovalEvent = typesEventsChain.getEditHelperApprovalEvent();
			if (editHelperApprovalEvent != null) {
				result.add(editHelperApprovalEvent);
			}
			List<AbstractTypesEvent> beforeAdvicesCommandsEvents = typesEventsChain.getBeforeAdvicesCommandsEvents();
			if (!beforeAdvicesCommandsEvents.isEmpty()) {
				result.add(new AbstractMap.SimpleEntry<String, Object>("BeforeAdvicesCommandsEvents", beforeAdvicesCommandsEvents));
			}
			AbstractTypesEvent editHelperCommandEvent = typesEventsChain.getEditHelperCommandEvent();
			if (editHelperCommandEvent != null) {
				result.add(editHelperCommandEvent);
			}
			List<AbstractTypesEvent> afterAdvicesCommandsEvents = typesEventsChain.getAfterAdvicesCommandsEvents();
			if (!afterAdvicesCommandsEvents.isEmpty()) {
				result.add(new AbstractMap.SimpleEntry<String, Object>("AfterAdvicesCommandsEvents", afterAdvicesCommandsEvents));
			}
		} else if (parentElement instanceof Map.Entry) {
			Object value = ((Map.Entry<?, ?>) parentElement).getValue();
			if (value instanceof Collection) {
				result.addAll((Collection<?>) value);
			}
		}
		return result.toArray();
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length == 0 ? false : true;
	}

}
