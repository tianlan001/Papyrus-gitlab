/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.hyperlink.helper;

import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.swt.widgets.Shell;

/**
 * this class is an abstract helper used to serialize and deserialize a HyperLink Object
 *
 */
public abstract class AbstractHyperLinkHelper {

	/**
	 * the id of the tab
	 */
	private String tabid;

	/**
	 * Getter for this{@link #tabid}
	 *
	 * @return
	 * 		this{@link #tabid}
	 */
	public final String getTabId() {
		return this.tabid;
	}

	/**
	 * Setter for this{@link #tabid}
	 *
	 * @param tabId
	 *            the id of the tab
	 */
	public final void setTabId(final String tabId) {
		this.tabid = tabId;
	}

	/**
	 *
	 * @return as string of the kind of hyperlink to display
	 */
	public abstract String getNameofManagedHyperLink();

	/**
	 * this method is called in order to create an HyperLinkObject and add into
	 * a given HyperLinkObject list
	 *
	 * @param list
	 *            a list of hyperlink Object
	 * @param aModel
	 *            TODO
	 */
	// TODO remove this method
	public abstract void executeNewMousePressed(Shell parentShell, List<HyperLinkObject> list, EObject aModel);


	/**
	 * this method is called in order to edit an HyperLinkObject and add into a
	 * given HyperLinkObject list
	 *
	 * @param list
	 *            a list of hyperlink Object
	 * @param amodel
	 *            the root model
	 * @param HyperLinkObject
	 *            the HyperLinkObject to edit
	 */
	public void executeEditMousePressed(Shell parentShell, List<HyperLinkObject> list, HyperLinkObject hyperLinkObject, EObject amodel) {
		hyperLinkObject.executeEditMousePressed(parentShell, list, amodel);
	}

	/**
	 * from a list of hyperlinks, it return a list of hyperlink with the same
	 * kind. for example return a list of diagramhyperlink
	 *
	 * @param HyperLinkObjects
	 *            the list of HyperLinkObjects
	 * @return a list of hyperlink object with the same kind
	 */
	public abstract List<HyperLinkObject> getFilteredObject(List<HyperLinkObject> hyperLinkObjects);

	/**
	 *
	 * @param eAnnotation
	 *            that represents a hyperlink object
	 * @return the hyperlink object from the eannotation
	 */
	public abstract HyperLinkObject getHyperLinkObject(EAnnotation eAnnotation);

	/**
	 * get a command to serailize a hyperlink object
	 *
	 * @param domain
	 *            the editing domain
	 * @param object
	 *            the EModelElement to which the hyperlink as attached
	 * @param HyperLinkObject
	 *            the HyperLinkObject to serailize
	 * @return the command in charge of the serialization
	 */
	public abstract RecordingCommand getAddHyperLinkCommand(TransactionalEditingDomain domain, EModelElement object, HyperLinkObject HyperLinkObject);
}
