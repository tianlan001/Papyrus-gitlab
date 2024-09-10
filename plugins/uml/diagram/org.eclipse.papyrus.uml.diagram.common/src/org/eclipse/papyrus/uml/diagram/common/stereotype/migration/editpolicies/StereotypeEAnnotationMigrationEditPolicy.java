/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 493420
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration.editpolicies;

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.commands.RemoveEAnnotationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeDisplayDiagramReconciler_1_2_0;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeMigrationHelper;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 * This Edit Policy is in charge to migrate user preferences from old stereotype display structure
 * (which were using EAnnotation) to the new structure using NamedStyle and additional notation Views
 *
 * @author Céline JANSSENS
 *
 * @deprecated Replaced by {@link StereotypeDisplayDiagramReconciler_1_2_0}
 */
@Deprecated
public abstract class StereotypeEAnnotationMigrationEditPolicy extends AbstractEditPolicy {

	public static final Object STEREOTYPE_RECONCILER = "Stereotype Display Reconciler"; //$NON-NLS-1$

	protected StereotypeMigrationHelper migrationHelper = StereotypeMigrationHelper.getInstance();
	protected StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	protected View hostView = null;
	protected EAnnotation eAnnotation = null;
	protected IGraphicalEditPart editPart = null;



	/**
	 * Set the attributes and launch the migration.
	 *
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#activate()
	 *
	 */
	@Override
	public void activate() {

		// set editPart
		if (getHost() instanceof GraphicalEditPart) {
			this.editPart = (GraphicalEditPart) getHost();
		}

		if (getHost() instanceof ConnectionEditPart) {
			this.editPart = (ConnectionEditPart) getHost();
		}

		// set hostView
		if (editPart != null && editPart.getModel() instanceof View) {
			this.hostView = (View) editPart.getModel();
		}

		// set EAnnotation and start the migration process
		if (editPart != null && hostView != null) {
			this.eAnnotation = migrationHelper.getStereotypeEAnnotation(hostView);
			if (eAnnotation != null && !eAnnotation.getDetails().isEmpty()) {
				migrateStereotype();
			}
		}

	}

	/**
	 * Migrate all the Stereotype User preferences
	 *
	 */
	protected void migrateStereotype() {

		if (eAnnotation != null) {
			if (hasEAnnotationDetails(hostView)) {
				// Retrieve the migration Command and execute it.
				ICommand command = getStereotypeMigrationTransactionalCommand(hostView);
				CommandUtil.executeUnsafeCommand(command, editPart);

			}
			// Clean the details associated to each sub edit policies;
			cleanEAnnotationDetails(hostView);
		}
		// Clean the EAnnotation if Empty
		cleanEAnnotation();
	}


	/**
	 * Remove the EAnnotation when necessary
	 */
	private void cleanEAnnotation() {

		// If the EAnnotation is Empty Delete it
		if (eAnnotation != null && eAnnotation.getDetails().size() == 0) {
			RemoveEAnnotationCommand command = new RemoveEAnnotationCommand(editPart.getEditingDomain(), hostView, eAnnotation);
			CommandUtil.executeUnsafeCommand(command, editPart);
		}

		// If No Stereotype Structure is Found on the host but EAnnotation is present, delete the EAnnotation
		if (eAnnotation != null && !helper.hasStereotypeViews(hostView)) {
			RemoveEAnnotationCommand command = new RemoveEAnnotationCommand(editPart.getEditingDomain(), hostView, eAnnotation);
			CommandUtil.executeUnsafeCommand(command, editPart);
		}

		// Delete orphan Comment Node from OldStructure
		Object container = hostView.eContainer();
		if (container instanceof View) {
			View containerView = (View) container;
			Iterator<Object> sibilings = containerView.getChildren().iterator();
			while (sibilings.hasNext()) {
				Object sibiling = sibilings.next();
				if (migrationHelper.isOldComment(sibiling)) {
					if (migrationHelper.isOrphanComment((View) sibiling)) {
						DeleteCommand deleteComment = new DeleteCommand((View) sibiling);
						CommandUtil.executeUnsafeCommand(deleteComment, sibiling);
					}
				}
			}
		}

	}

	/**
	 * Clean EAnnotation Details in the model depending of what has been treated.
	 *
	 * @param hostView
	 *            The view on which the Stereotype has been applied
	 */
	public abstract void cleanEAnnotationDetails(View view);

	/**
	 * Define if the Edit Policy detail for the specific EANnotation Detail is not Empty.
	 *
	 * @param view
	 *            The view on which the Stereotype has been applied
	 */
	public abstract boolean hasEAnnotationDetails(View view);

	/**
	 * Get the command to update the Node visibility related to the EAnnotation.
	 *
	 * @param hostView
	 *            The view on which the Stereotype has been applied
	 */
	public Runnable getStereotypeMigrationCommand(View view) {
		return null;
	}

	/**
	 * Get the command to update the Node visibility related to the EAnnotation.
	 *
	 * @param hostView
	 *            The view on which the Stereotype has been applied
	 */
	public ICommand getStereotypeMigrationTransactionalCommand(final View view) {
		ICommand command = null;
		if (null != getStereotypeMigrationCommand(view)) {
			command = new AbstractTransactionalCommand(migrationHelper.getDomain(view), "Migration Stereotype", null) { //$NON-NLS-1$
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					getStereotypeMigrationCommand(view).run();
					return CommandResult.newOKCommandResult();
				}
			};
		}
		return command;
	}
}
