/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 491478
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.internal.ui.advice;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.creation.EditionDialog;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;
import org.eclipse.swt.widgets.Display;

/**
 * advice for the {@link SetValuesAdviceConfiguration}
 */
public class RuntimeValuesAdviceEditHelperAdvice extends AbstractEditHelperAdvice {

	/** list of views to display */
	protected Set<View> viewsToDisplay;


	/**
	 * Default Constructor
	 */
	public RuntimeValuesAdviceEditHelperAdvice(RuntimeValuesAdviceConfiguration configuration) {
		viewsToDisplay = new HashSet<View>();
		for (ViewToDisplay display : configuration.getViewsToDisplay()) {
			View view = display.getView();
			if (view != null) {
				viewsToDisplay.add(view);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		return super.approveRequest(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeCreateCommand(CreateElementRequest request) {
		return super.getBeforeCreateCommand(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterCreateCommand(CreateElementRequest request) {
		return super.getAfterCreateCommand(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		return super.getBeforeConfigureCommand(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterConfigureCommand(final ConfigureRequest request) {
		final EObject elementToConfigure = request.getElementToConfigure();
		if (elementToConfigure == null) {
			return null;
		}

		final boolean dialogCancellable = ElementTypeUtils.dialogCancellable(request);

		return new AbstractTransactionalCommand(request.getEditingDomain(), "Editing " + EMFCoreUtil.getName(elementToConfigure), Collections.singletonList(WorkspaceSynchronizer.getFile((elementToConfigure.eResource())))) {
			/**
			 * {@inheritDoc}
			 */
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				int dialogResult = Window.OK;
				Set<View> viewsToDisplay = getViewsToDisplay();
				if (!viewsToDisplay.isEmpty()) {
					EditionDialog dialog = new EditionDialog(Display.getCurrent().getActiveShell(), dialogCancellable) {

					};
					dialog.setTitle("Edit " + EMFCoreUtil.getName(elementToConfigure));
					dialog.setViews(viewsToDisplay);
					dialog.setInput(elementToConfigure);

					dialogResult = dialog.open();
				}

				return (dialogResult == Window.OK)
						? CommandResult.newOKCommandResult(elementToConfigure)
						: CommandResult.newCancelledCommandResult();
			}
		};

	}


	/**
	 * @return the viewsToDisplay
	 */
	public Set<View> getViewsToDisplay() {
		return viewsToDisplay;
	}

}
