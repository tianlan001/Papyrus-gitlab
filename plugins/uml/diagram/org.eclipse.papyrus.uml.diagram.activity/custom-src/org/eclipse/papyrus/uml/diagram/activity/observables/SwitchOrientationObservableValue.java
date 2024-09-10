/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.observables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ForkNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.JoinNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.part.CustomMessages;

/**
 * The observable value for the switch observable action.
 *
 * @since 3.2
 */
public class SwitchOrientationObservableValue extends AbstractObservableValue<Boolean> {

	/**
	 * The transactional editing domain.
	 */
	private TransactionalEditingDomain editingDomain;

	/**
	 * The activity nodes edit parts to modify.
	 */
	private List<EditPart> activityNodesEP;

	/**
	 * Constructor.
	 *
	 * @param editingDomain
	 *            The transactional editing domain.
	 * @param activityNodesEP
	 *            The activity nodes edit parts to modify.
	 */
	public SwitchOrientationObservableValue(final TransactionalEditingDomain editingDomain, final Collection<EditPart> activityNodesEP) {
		this.activityNodesEP = new ArrayList<>(activityNodesEP.size());
		this.activityNodesEP.addAll(activityNodesEP);
		this.editingDomain = editingDomain;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	@Override
	public Object getValueType() {
		return Boolean.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 */
	@Override
	protected Boolean doGetValue() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Boolean value) {
		CompositeCommand globalCommand = new CompositeCommand(CustomMessages.ForkJoinSegmentSwitchOrientation_actionLabel);
		for (Object part : this.activityNodesEP) {
			Object view = null;
			Figure figure = null;
			if (part instanceof JoinNodeEditPart) {
				view = ((JoinNodeEditPart) part).getModel();
				figure = ((JoinNodeEditPart) part).getPrimaryShape();
			} else if (part instanceof ForkNodeEditPart) {
				view = ((ForkNodeEditPart) part).getModel();
				figure = ((ForkNodeEditPart) part).getPrimaryShape();
			}
			// append a command for selected part only if correct configuration
			if (view instanceof View && figure != null) {
				SwitchSegmentOrientation switchCom = new SwitchSegmentOrientation(editingDomain, figure, (View) view);
				globalCommand.add(switchCom);
			}
		}
		// execute the command for all parts
		if (!globalCommand.isEmpty() && globalCommand.canExecute()) {
			editingDomain.getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(new ICommandProxy(globalCommand)));
		}
	}

	/**
	 * This class is a command which switches the orientation of a figure. The figure's width and height are switched, rotating the figure on its center. (center location kept)
	 */
	private class SwitchSegmentOrientation extends AbstractTransactionalCommand {

		/** The figure to switch */
		private Figure selectedFigure = null;

		/** The model view for the figure to switch */
		private View selectedView = null;

		/**
		 * Construct a switch orientation command.
		 *
		 * @param domain
		 *            The transactional editing domain.
		 * @param figure
		 *            The figure to rotate.
		 * @param view
		 *            The view which is the model of the figure.
		 */
		SwitchSegmentOrientation(final TransactionalEditingDomain domain, final Figure figure, final View view) {
			super(domain, CustomMessages.ForkJoinSegmentSwitchOrientation_actionLabel, null);
			selectedFigure = figure;
			selectedView = view;
		}

		/**
		 * Execute the command, rotating the figure.
		 *
		 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 *
		 * @param monitor
		 *            The progress monitor.
		 * @param info
		 *            The adapter for information.
		 * @return The result of the command.
		 * @throws ExecutionException
		 */
		@Override
		protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
			if (selectedFigure == null || selectedView == null) {
				return CommandResult.newCancelledCommandResult();
			}
			Dimension newSize = selectedFigure.getSize().getTransposed();
			Point newLocation = selectedFigure.getLocation().getCopy();
			newLocation.translate((newSize.height - newSize.width) / 2, (newSize.width - newSize.height) / 2);
			ViewUtil.setStructuralFeatureValue(selectedView, NotationPackage.eINSTANCE.getLocation_X(), newLocation.x);
			ViewUtil.setStructuralFeatureValue(selectedView, NotationPackage.eINSTANCE.getLocation_Y(), newLocation.y);
			ViewUtil.setStructuralFeatureValue(selectedView, NotationPackage.eINSTANCE.getSize_Width(), newSize.width);
			ViewUtil.setStructuralFeatureValue(selectedView, NotationPackage.eINSTANCE.getSize_Height(), newSize.height);
			return CommandResult.newOKCommandResult();
		}

		/**
		 * @see org.eclipse.emf.workspace.AbstractEMFOperation#canUndo()
		 * @return true if command can undo.
		 */
		@Override
		public boolean canUndo() {
			return selectedFigure != null && selectedView != null;
		}

		/**
		 * @see org.eclipse.emf.workspace.AbstractEMFOperation#canRedo()
		 * @return true if command can redo.
		 */
		@Override
		public boolean canRedo() {
			return selectedFigure != null && selectedView != null;
		}

		/**
		 * Undo the switch (by switching again).
		 *
		 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doUndo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 *
		 * @param monitor
		 *            The progress monitor.
		 * @param info
		 *            The adapter for information.
		 * @return The result of the command.
		 * @throws ExecutionException
		 */
		@Override
		protected IStatus doUndo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
			return doExecute(monitor, info);
		}

		/**
		 * Redo the switch (by switching again).
		 *
		 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doRedo(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 *
		 * @param monitor
		 *            The progress monitor.
		 * @param info
		 *            The adapter for information.
		 * @return The result of the command.
		 * @throws ExecutionException
		 */
		@Override
		protected IStatus doRedo(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
			return doExecute(monitor, info);
		}
	}



}
