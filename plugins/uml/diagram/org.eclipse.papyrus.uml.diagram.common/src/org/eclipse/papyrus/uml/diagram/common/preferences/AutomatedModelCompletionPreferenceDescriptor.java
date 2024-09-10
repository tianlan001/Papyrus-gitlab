/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.common.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Element;

/**
 * 
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 * 
 * @since 3.0
 *
 */
public class AutomatedModelCompletionPreferenceDescriptor {

	/**
	 * element is the node on which the updater is applied
	 */
	private Class<? extends Element> element;

	/**
	 * listOfAccelerator
	 */
	private List<String> listOfAccelerator;

	/**
	 * preferenceConstant of this accelerator
	 */
	private String preferenceConstant;

	/**
	 * Constructor.
	 *
	 */
	public AutomatedModelCompletionPreferenceDescriptor(String preferenceConstant) {
		this.element = Element.class;
		this.listOfAccelerator = new ArrayList<>();
		this.preferenceConstant = preferenceConstant;
	}

	/**
	 * Constructor.
	 *
	 */
	public AutomatedModelCompletionPreferenceDescriptor(Class<? extends Element> node, List<String> listOfUpdater, String preferenceConstant) {
		this.element = node;
		this.listOfAccelerator = listOfUpdater;
		this.preferenceConstant = preferenceConstant;
	}

	/**
	 * this method add updater to the listOfUpdater
	 * 
	 * @param updater
	 * @return true if the list change
	 */
	boolean addAccelerator(String updater) {
		return listOfAccelerator.add(updater);
	}

	/**
	 * this method remove updater to the listOfUpdater
	 * 
	 * @param updater
	 * @return true if the list contain the specified element
	 */
	boolean removeAccelerator(String updater) {
		return listOfAccelerator.remove(updater);
	}

	/**
	 * @return the element
	 */
	public Class<? extends Element> getElement() {
		return element;
	}

	/**
	 * @param element
	 *            the element to set
	 */
	public void setElement(Class<? extends Element> element) {
		this.element = element;
	}

	/**
	 * @return the listOfAccelerator
	 */
	public List<String> getListOfAccelerator() {
		return listOfAccelerator;
	}

	/**
	 * @param listOfAccelerator
	 *            the listOfAccelerator to set
	 */
	public void setListOfAccelerator(List<String> listOfAccelerator) {
		this.listOfAccelerator = listOfAccelerator;
	}

	/**
	 * @return the preferenceConstant
	 */
	public String getPreferenceConstant() {
		return preferenceConstant;
	}

	/**
	 * @param preferenceConstant
	 *            the preferenceConstant to set
	 */
	public void setPreferenceConstant(String preferenceConstant) {
		this.preferenceConstant = preferenceConstant;
	}

}
