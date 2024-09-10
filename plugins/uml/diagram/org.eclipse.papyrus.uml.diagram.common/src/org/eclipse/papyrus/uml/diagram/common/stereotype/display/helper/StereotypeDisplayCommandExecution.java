/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *   Christian W. Damus - bug 492482
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SetNodeVisibilityCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStyleValueCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.SetPersistentViewCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.UnsetPersistentViewCommand;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This Class regroups the Commands and their execution for the Stereotype Display
 *
 * @author Céline JANSSENS
 *
 */
public final class StereotypeDisplayCommandExecution {
	/**
	 * singleton instance
	 */
	private static StereotypeDisplayCommandExecution labelHelper;
	private StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	/** Singleton contructor */
	private StereotypeDisplayCommandExecution() {
	}



	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static StereotypeDisplayCommandExecution getInstance() {
		if (labelHelper == null) {
			labelHelper = new StereotypeDisplayCommandExecution();
		}
		return labelHelper;
	}

	/**
	 * Set the visibility of a view
	 *
	 * @param view
	 *            The view on which the visibility has to be set
	 * @param isVisible
	 *            True to make the Compartment visible
	 */
	public void setVisibility(final TransactionalEditingDomain domain, final View view, final boolean isVisible, final boolean inCommandStack) {
		SetNodeVisibilityCommand visibility = new SetNodeVisibilityCommand(domain, view, isVisible);

		if (!inCommandStack) {
			CommandUtil.executeCommand(visibility, domain);
		} else {
			CommandUtil.executeCommandInStack(visibility, domain);
		}
	}


	/**
	 * Set the visibility of a view
	 *
	 * @param view
	 *            The view on which the visibility has to be set
	 * @param isVisible
	 *            True to make the Compartment visible
	 */
	public void setPersistency(final TransactionalEditingDomain domain, final View view, boolean inCommandStack) {
		SetPersistentViewCommand persitence = new SetPersistentViewCommand(domain, view);

		if (!inCommandStack) {
			CommandUtil.executeCommand(persitence, domain);
		} else {
			CommandUtil.executeCommandInStack(persitence, domain);
		}

	}

	/**
	 * Set the visibility of a view
	 *
	 * @param view
	 *            The view on which the visibility has to be set
	 * @param isVisible
	 *            True to make the Compartment visible
	 */
	public void unsetPersistency(final TransactionalEditingDomain domain, final View view, boolean inCommandStack) {
		UnsetPersistentViewCommand persistence = new UnsetPersistentViewCommand(domain, view);
		if (!inCommandStack) {
			CommandUtil.executeCommand(persistence, domain);
		} else {
			CommandUtil.executeCommandInStack(persistence, domain);
		}

	}

	/**
	 * @param eContainer
	 */
	public void makeViewPersistant(final View view) {
		if (view != null) {
			if (view.eContainer() != null && view.eContainer() instanceof View) {

				// Make the Parent Persistent
				makeViewPersistant((View) view.eContainer());
				// Move the view from the Transient List to the Persistent Children list
				if (!(view instanceof Edge)) {
					((View) view.eContainer()).getPersistedChildren().add(view);
					((View) view.eContainer()).getTransientChildren().remove(view);
				}
			}
		}
	}

	/**
	 * @param eContainer
	 */
	public void removeViewPersistant(final View view) {
		if (view != null) {
			if ((view.eContainer() != null) && (view.eContainer() instanceof View)) {

				// Move the view from the Persistent List to the Transcient Children list
				if (!(view instanceof Edge)) {

					((View) view.eContainer()).getTransientChildren().add(view);
					((View) view.eContainer()).getPersistedChildren().remove(view);
				}
			}
		}
	}

	/**
	 * Set the depth Name of the Stereotype Label.
	 * It uses the NamedStyle to store the depth into a View.
	 *
	 * @param stereotype
	 *            The Stereotype of the Label that should be modified.
	 * @param nodeView
	 *            The view of the element that needs to be updated (i.e. The Class)
	 * @param depth
	 *            The Depth value as a string (Can be "none", "full" or a negative number )
	 */
	public void setDepth(final TransactionalEditingDomain domain, final Stereotype stereotype, final View nodeView, final String depth, final boolean inCommandStack) {
		View label = null;
		if (helper.isStereotypeLabel(nodeView)) {
			label = nodeView;
		} else {
			label = helper.getStereotypeLabel(nodeView, stereotype);
		}
		Command command = new CustomStyleValueCommand(label, depth, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(), StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH);

		if (inCommandStack) {
			CommandUtil.executeCommandInStack(command, domain);
		} else {
			CommandUtil.executeCommand(command, domain);
		}
	}

	/**
	 * This Method is called when the user ask explicitly to display a View.
	 * Then the node is first set as Persistence and the command is put in the command Stack
	 * before to set the Visibility as wanted.
	 *
	 * @param domain
	 *            The Transactional Domain
	 * @param view
	 *            The View to make visible
	 * @param visible
	 *            True if the View has to be visible, false if the Node should be hidden
	 *
	 */
	public void setUserVisibility(final TransactionalEditingDomain domain, final View view, final boolean visible) {
		if (view != null && domain != null) {
			final CompoundCommand compoundCommand = new CompoundCommand("Set User Visibility");

			final SetPersistentViewCommand persitence = new SetPersistentViewCommand(domain, view);
			compoundCommand.append(persitence);
			final SetNodeVisibilityCommand visibility = new SetNodeVisibilityCommand(domain, view, visible);
			compoundCommand.append(visibility);
			CommandUtil.executeCommandInStack(compoundCommand, domain);
		}
	}

	/**
	 * This Method is called when the user ask explicitly to display a View.
	 * The command is put in the command Stack before to set the Visibility as wanted.
	 *
	 * @param domain
	 *            The Transactional Domain
	 * @param view
	 *            The View to make visible
	 * @param visible
	 *            True if the View has to be visible, false if the Node should be hidden
	 *
	 */
	public void setUserVisibilityWithoutPersistence(final TransactionalEditingDomain domain, final View view, final boolean visible) {
		if (view != null && domain != null) {

			final SetNodeVisibilityCommand visibility = new SetNodeVisibilityCommand(domain, view, visible);
			CommandUtil.executeCommandInStack(visibility, domain);
		}
	}

	/**
	 * This Method is called when the user ask explicitly to display a View.
	 * Then the node is first set as Persistence and the command is put in the command Stack
	 * before to set the Visibility as wanted.
	 *
	 * @param domain
	 *            The Transactional Domain
	 * @param view
	 *            The View to make visible
	 * @param visible
	 *            True if the View has to be visible, false if the Node should be hidden
	 *
	 */
	public void setUserDepth(final TransactionalEditingDomain domain, final Stereotype stereotype, final View label, final String depth) {
		if (label != null && depth != null && !depth.isEmpty()) {

			setPersistency(domain, label, true);
			setDepth(domain, stereotype, label, depth, true);
		}
	}
}
