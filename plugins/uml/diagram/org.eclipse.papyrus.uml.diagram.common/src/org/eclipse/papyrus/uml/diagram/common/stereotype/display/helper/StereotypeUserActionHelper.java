/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Chokri Mraidha (CEA LIST) Chokri.Mraidha@cea.fr - Initial API and implementation
 *  Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - modification
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.AddAppliedQNStereotypeToDisplayCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.AddAppliedStereotypePropertiesToDisplayCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.AddAppliedStereotypeToDisplayCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.RemoveAppliedStereotypePropertiesToDisplayCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.RemoveAppliedStereotypeToDisplayCommand;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The Class AppliedStereotypeHelper.
 * Helper used in the User interaction to display Stereotypes from the Property View.
 * Note: this class is to be used instead of <i>AppliedStereotypeHelper</i> which is deprecated)
 */
public class StereotypeUserActionHelper {



	/**
	 * add new applied stereotypes to display with the qualified name.
	 *
	 * @param domain
	 *            the transactionnal edit domain
	 * @param view
	 *            the emodel element that is the display of the uml element
	 * @param appliedStereotypeList
	 *            the list of stereotype with qualified name to display
	 *
	 * @return the command to display it
	 */
	public static RecordingCommand getAddAppliedStereotypeToDisplayWithQNCommand(TransactionalEditingDomain domain, View view, Stereotype stereotype, String depth) {
		return new AddAppliedQNStereotypeToDisplayCommand(domain, view, stereotype, depth);
	}

	/**
	 * Gets the adds the applied stereotype command.
	 *
	 * @param domain
	 *            the domain
	 * @param view
	 *            the view
	 * @param appliedStereotypeListToAdd
	 *            the applied stereotype list to add
	 * @param presentationKind
	 *            the presentation kind
	 *
	 * @return the adds the applied stereotype command
	 */
	public static RecordingCommand getAddAppliedStereotypeCommand(TransactionalEditingDomain domain, View view, String stereotypeName) {
		return new AddAppliedStereotypeToDisplayCommand(domain, view, stereotypeName);
	}

	/**
	 * Gets the adds the applied stereotype propertiescommand.
	 *
	 * @param domain
	 *            the domain
	 * @param view
	 *            the view
	 * @param appliedStereotypeListToAdd
	 *            the applied stereotype properties list to add
	 *
	 * @return the adds the applied stereotype command
	 */
	public static RecordingCommand getAddAppliedStereotypePropertiesCommand(TransactionalEditingDomain domain, View view, Stereotype stereotype, Property property, String location) {
		return new AddAppliedStereotypePropertiesToDisplayCommand(domain, view, property, stereotype, location);
	}

	/**
	 * Gets the removes the applied stereotype command.
	 *
	 * @param domain
	 *            the domain
	 * @param view
	 *            the view
	 * @param appliedStereotypeListToRemove
	 *            the applied stereotype list to remove
	 * @param presentationKind
	 *            the presentation kind
	 *
	 * @return the removes the applied stereotype command
	 */
	public static RecordingCommand getRemoveAppliedStereotypeCommand(TransactionalEditingDomain domain, View view, Stereotype stereotype) {
		return new RemoveAppliedStereotypeToDisplayCommand(domain, view, stereotype);
	}

	/**
	 * Gets the remove applied stereotype properties command.
	 *
	 * @param domain
	 *            the domain
	 * @param view
	 *            the view
	 * @param appliedStereotypeListToRemove
	 *            the applied stereotype list to remove
	 *
	 * @return the removes the applied stereotype properties command
	 */
	public static RecordingCommand getRemoveAppliedStereotypePropertiesCommand(TransactionalEditingDomain domain, View view, Property property, Stereotype stereotype, String location) {
		return new RemoveAppliedStereotypePropertiesToDisplayCommand(domain, view, property, stereotype, location);
	}



}
