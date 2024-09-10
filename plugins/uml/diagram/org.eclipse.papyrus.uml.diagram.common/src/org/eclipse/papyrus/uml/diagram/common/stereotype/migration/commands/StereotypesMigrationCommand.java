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

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.commands.RemoveEAnnotationCommand;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeEAnnotationLabelMigration;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeEAnnotationNestedMigration;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeEAnnotationPropertiesMigration;
import org.eclipse.papyrus.uml.diagram.common.stereotype.migration.StereotypeMigrationHelper;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 * The command used to migration stereotype representation in the diagram
 */
public class StereotypesMigrationCommand extends AbstractTransactionalCommand {

	/**
	 * the migrated diagram
	 */
	private Diagram diagram;

	protected StereotypeMigrationHelper migrationHelper = StereotypeMigrationHelper.getInstance();

	protected StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();

	/**
	 * Constructor.
	 *
	 * @param label
	 * @param diagram
	 */
	public StereotypesMigrationCommand(final TransactionalEditingDomain domain, final Diagram diagram) {
		super(domain, "Stereotypes Migration", null);
		this.diagram = diagram;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor progressMonitor, final IAdaptable info) {
		TreeIterator<EObject> allContentIterator = diagram.eAllContents();
		List<View> viewsToClean = new ArrayList<View>();
		List<View> viewsWithCommentToDelete = new ArrayList<View>();
		while (allContentIterator.hasNext()) {
			EObject eObject = allContentIterator.next();
			if (eObject instanceof View) {
				final View view = (View) eObject;

				if (StereotypeMigrationHelper.getInstance().hasStereotypeEAnnotation(view)) {
					if (null != view.getElement()) {
						String appliedStereotypesPropertiesLocalization = migrationHelper.getAppliedStereotypesPropertiesLocalization(view);

						StereotypeEAnnotationLabelMigration labelMigration = new StereotypeEAnnotationLabelMigration(view);
						labelMigration.activate();

						if (view instanceof BasicCompartment) {
							StereotypeEAnnotationNestedMigration nestedMigration = new StereotypeEAnnotationNestedMigration(view);
							nestedMigration.activate();
						} else {
							StereotypeEAnnotationPropertiesMigration propertiesMigration = new StereotypeEAnnotationPropertiesMigration(view);
							propertiesMigration.activate();
						}

						// Clean the Ennotation after
						viewsToClean.add(view);
						if (StereotypeDisplayConstant.STEREOTYPE_COMMENT_LOCATION.equals(appliedStereotypesPropertiesLocalization)) {
							// Delete the comment after
							viewsWithCommentToDelete.add(view);
						}
					} else if (view.getType().equals("AppliedStereotypesComment")) { //$NON-NLS-1$
						StereotypeEAnnotationNestedMigration nestedMigration = new StereotypeEAnnotationNestedMigration(view);
						nestedMigration.activate();
					}
				}
			}
		}

		// Delete all the old comment
		for (View view : viewsWithCommentToDelete) {
			deleteOldComment(view);
		}

		// Clean all Ennotation node of each view with an applied stereotype
		for (View view : viewsToClean) {
			cleanEAnnotation(view);
		}

		return CommandResult.newOKCommandResult();
	}

	/**
	 * @param view
	 */
	private void deleteOldComment(final View view) {
		// Delete Comment from the Old Structure
		View oldComment = migrationHelper.getOldStereotypeComment(view);
		if (oldComment != null) {
			DeleteCommand deleteComment = new DeleteCommand(oldComment);
			CommandUtil.executeUnsafeCommand(deleteComment, view);
		}
		Edge oldLink = migrationHelper.getOldStereotypeLinkComment(view);
		if (oldLink != null) {
			DeleteCommand deleteLink = new DeleteCommand(oldLink);
			CommandUtil.executeUnsafeCommand(deleteLink, view);
		}
	}

	/**
	 * Remove the EAnnotation when necessary.
	 */
	private void cleanEAnnotation(final View view) {
		final TransactionalEditingDomain resolveEditingDomain = CommandUtil.resolveEditingDomain(view);
		EAnnotation eAnnotation = migrationHelper.getStereotypeEAnnotation(view);

		// If the EAnnotation is Empty Delete it
		if ((null != eAnnotation) && (eAnnotation.getDetails().isEmpty())) {
			final RemoveEAnnotationCommand command = new RemoveEAnnotationCommand(resolveEditingDomain, view, eAnnotation);
			CommandUtil.executeUnsafeCommand(command, view);
		}

		// If No Stereotype Structure is Found on the host but EAnnotation is present, delete the EAnnotation
		if ((null != eAnnotation) && (!helper.hasStereotypeViews(view))) {
			final RemoveEAnnotationCommand command = new RemoveEAnnotationCommand(resolveEditingDomain, view, eAnnotation);
			CommandUtil.executeUnsafeCommand(command, view);
		}

		// Delete orphan Comment Node from OldStructure
		final Object container = view.eContainer();
		if (container instanceof View) {
			final View containerView = (View) container;
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
}
