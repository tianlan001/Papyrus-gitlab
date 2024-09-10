/*****************************************************************************
 * Copyright (c) 2014, 2019 CEA LIST.
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
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 549705
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.advices.settype;

import java.util.Arrays;
import java.util.Optional;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElementFactory;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.uml.types.core.Activator;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;

/**
 * advice for the {@link ApplyStereotypeAdviceConfiguration}
 */
public class SetTypeAdviceEditHelperAdvice extends AbstractEditHelperAdvice {

	protected SetTypeAdviceConfiguration configuration;

	public SetTypeAdviceEditHelperAdvice(SetTypeAdviceConfiguration configuration) {
		this.configuration = configuration;
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
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		return super.getBeforeConfigureCommand(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		if (configuration == null) {
			return null;
		}
		ICommand resultCommand = null;
		// retrieve eobject
		final EObject elementToConfigure = request.getElementToConfigure();
		if (!(elementToConfigure instanceof Element)) {
			return null;
		}

		final TransactionalEditingDomain editingDomain = request.getEditingDomain();
		if (editingDomain == null) {
			return null;
		}
		// retrieve edit service to get features from configure command
		IElementEditService service = ElementEditServiceUtils.getCommandProvider(elementToConfigure);
		if (service == null) {
			Activator.log.error("Impossible to get edit service from element: " + elementToConfigure, null); //$NON-NLS-1$
			return null;
		}

		resultCommand = new AbstractTransactionalCommand(editingDomain, "Editing type", Arrays.asList((WorkspaceSynchronizer.getFile(elementToConfigure.eResource())))) { //$NON-NLS-1$

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				Object elemObject = null;

				final Context context = ConfigurationManager.getInstance().getContext("UML"); //$NON-NLS-1$
				if (null != context) {
					Optional<DataContextRoot> umlDataContextFilter = context.getDataContexts().stream().filter(dc -> "UML".equals(dc.getName())).findFirst(); //$NON-NLS-1$
					DataContextRoot umlDataContext = umlDataContextFilter.isPresent() ? umlDataContextFilter.get() : null;

					if (null != umlDataContext) {
						final String factoryName = umlDataContext.getModelElementFactory().getFactoryClass();
						final ModelElementFactory modelElementFactory = ClassLoaderHelper.newInstance(factoryName, ModelElementFactory.class, EcoreUtil.getURI(context));

						final ModelElement umlModelElement = modelElementFactory.createFromSource(elementToConfigure, umlDataContext);
						final ReferenceValueFactory factory = umlModelElement.getValueFactory("type"); //$NON-NLS-1$
						elemObject = factory.edit(Display.getDefault().getFocusControl(), elementToConfigure);
					}
				}
				return CommandResult.newOKCommandResult(elemObject);
			}
		};

		return resultCommand;
	}

	/**
	 * @return
	 */
	protected EObject getDefaultTypeContainer(ConfigureRequest request) {
		if (request.getElementToConfigure() instanceof Element) {
			return ((Element) request.getElementToConfigure()).getNearestPackage();
		}
		return request.getElementToConfigure().eContainer();
	}


}
