/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IMultiPageEditorPart;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.ui.IEditorPart;

/**
 * This allows to close editors depending to the current viewpoints.
 *
 * @since 2.1
 */
public class CloseEditorsForViewpointsCommand extends AbstractCommand {

	/**
	 * Store the allowed editors identifiers to keep opened.
	 */
	private Set<String> allowedEditorIds;

	/**
	 * A set of editors to close.
	 */
	private Set<Object> pageIdentifiersToClose;

	/**
	 * Constructor.
	 *
	 * @param viewpoints
	 *            The current viewpoints of the model.
	 */
	public CloseEditorsForViewpointsCommand(final Collection<MergedArchitectureViewpoint> viewpoints) {
		// Calculate the allowed editors identifiers to keep opened
		allowedEditorIds = new HashSet<>();
		for (final MergedArchitectureViewpoint viewpoint : viewpoints) {
			for (final RepresentationKind representationKind : viewpoint.getRepresentationKinds()) {
				if (representationKind instanceof PapyrusRepresentationKind) {
					if (representationKind instanceof PapyrusDiagram) {
						allowedEditorIds.add(((PapyrusRepresentationKind) representationKind).getImplementationID());
					} else if (representationKind instanceof PapyrusTable) {
						allowedEditorIds.add(((PapyrusRepresentationKind) representationKind).getId());
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.command.AbstractCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		final IEditorPart activeEditor = EditorHelper.getCurrentEditor();
		return activeEditor instanceof IMultiPageEditorPart;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	@Override
	public void execute() {
		// Get the current editor. It should be a multi page sash editor from Papyrus
		final IEditorPart activeEditor = EditorHelper.getCurrentEditor();
		if (activeEditor instanceof IMultiPageEditorPart) {
			// Get the page manager
			final IPageManager pageManager = ((IMultiPageEditorPart) activeEditor).getAdapter(IPageManager.class);
			if (null != pageManager) {

				// Create a set of editors to close (to avoid concurrent modification)
				pageIdentifiersToClose = new HashSet<>();
				for (Object object : pageManager.allPages()) {
					// If this is a diagram not defined in allowed editors identifiers, mark it as closed
					if (object instanceof Diagram && !allowedEditorIds.contains(((Diagram) object).getType())) {
						pageIdentifiersToClose.add(object);

						// If this is a table not defined in allowed editors identifiers, mark it as closed
					} else if (object instanceof Table && !allowedEditorIds.contains(((Table) object).getTableKindId())) {
						pageIdentifiersToClose.add(object);
					}
				}

				// Close the needed editors
				if (!pageIdentifiersToClose.isEmpty()) {
					pageIdentifiersToClose.stream().forEach(pageIdentifier -> pageManager.closePage(pageIdentifier));
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
	 */
	@Override
	public void undo() {
		if (null != pageIdentifiersToClose && !pageIdentifiersToClose.isEmpty()) {
			final IEditorPart activeEditor = EditorHelper.getCurrentEditor();
			if (activeEditor instanceof IMultiPageEditorPart) {
				// Get the page manager
				final IPageManager pageManager = ((IMultiPageEditorPart) activeEditor).getAdapter(IPageManager.class);
				if (null != pageManager) {
					pageIdentifiersToClose.stream().forEach(pageIdentifier -> pageManager.openPage(pageIdentifier));
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	@Override
	public void redo() {
		if (null != pageIdentifiersToClose && !pageIdentifiersToClose.isEmpty()) {
			final IEditorPart activeEditor = EditorHelper.getCurrentEditor();
			if (activeEditor instanceof IMultiPageEditorPart) {
				// Get the page manager
				final IPageManager pageManager = ((IMultiPageEditorPart) activeEditor).getAdapter(IPageManager.class);
				if (null != pageManager) {
					pageIdentifiersToClose.stream().forEach(pageIdentifier -> pageManager.closePage(pageIdentifier));
				}
			}
		}
	}

}
