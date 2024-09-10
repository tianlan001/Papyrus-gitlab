/*****************************************************************************
 * Copyright (c) 2011, 2015, 2022 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 * 	 Patrik Nandorf (Ericsson AB) - bug 458042
 *   Christian W. Damus - bug 459701
 *   Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Bug 580838
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import java.util.Arrays;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This abstract helper is used to set the source and the target for a {@link DirectedRelationship}
 */
public abstract class DirectedRelationshipEditHelper extends ElementEditHelper {

	/**
	 * Subclasses should implement this method providing the EReference to be used as source.
	 * 
	 * @return the source EReference
	 */
	protected abstract EReference getSourceReference();

	/**
	 * Subclasses should implement this method providing the EReference to be used as target.
	 * 
	 * @return the target EReference
	 */
	protected abstract EReference getTargetReference();

	/**
	 * Test if the relationship creation is allowed.
	 * 
	 * @param source
	 *            the relationship source can be null
	 * @param target
	 *            the relationship target can be null
	 * @return true if the creation is allowed
	 */
	protected abstract boolean canCreate(EObject source, EObject target);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest req) {
		EObject source = req.getSource();
		EObject target = req.getTarget();
		boolean noSourceOrTarget = (source == null || target == null);
		boolean noSourceAndTarget = (source == null && target == null);
		if (!noSourceAndTarget && !canCreate(source, target)) {
			// Abort creation.
			return UnexecutableCommand.INSTANCE;
		}
		if (noSourceOrTarget && !noSourceAndTarget) {
			// The request isn't complete yet. Return the identity command so
			// that the create relationship gesture is enabled.
			return IdentityCommand.INSTANCE;
		}

		// If the container is null or containmentFeature doesn't fit with container, try to find one that make sense
		if (req.getContainer() == null || (!req.getContainer().eClass().getEAllReferences().contains(req.getContainmentFeature())) || req.getContainmentFeature() == null) {
			// Propose a container.
			EObject proposedContainer = EMFCoreUtil.getLeastCommonContainer(Arrays.asList(new EObject[] { source, target }), UMLPackage.eINSTANCE.getPackage());

			// If no common container is found try source nearest package
			if (proposedContainer == null) {
				EObject sourcePackage = EMFCoreUtil.getContainer(source, UMLPackage.eINSTANCE.getPackage());
				if (sourcePackage != null && !isReadOnly(sourcePackage)) {
					proposedContainer = sourcePackage;
				}
			}

			// If no common container is found try target nearest package
			if (proposedContainer == null) {
				EObject targetPackage = EMFCoreUtil.getContainer(target, UMLPackage.eINSTANCE.getPackage());
				if (targetPackage != null && !isReadOnly(targetPackage)) {
					proposedContainer = targetPackage;
				}
			}

			if (proposedContainer == null) {
				return UnexecutableCommand.INSTANCE;
			}
			req.setContainer(proposedContainer);
		}
		return new CreateRelationshipCommand(req);
	}

	protected boolean isReadOnly(EObject eObject) {
		EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
		boolean isReadOnly = (eObject.eResource() != null) && (editingDomain.isReadOnly(eObject.eResource()));
		return isReadOnly;
	}

	/**
	 * This method provides the object to be use as source.
	 * 
	 * @return the source value (EList or EObject)
	 */
	protected Object getSourceObject(ConfigureRequest req) {
		Object result = null;
		if (getSourceReference().getUpperBound() != 1) {
			EList<EObject> objects = new BasicEList<EObject>();
			objects.add((EObject) req.getParameter(CreateRelationshipRequest.SOURCE));
			result = objects;
		} else {
			result = req.getParameter(CreateRelationshipRequest.SOURCE);
		}
		return result;
	}

	/**
	 * This method provides the object to be used as target.
	 * 
	 * @return the target value (EList or EObject)
	 */
	protected Object getTargetObject(ConfigureRequest req) {
		Object result = null;
		if (getTargetReference().getUpperBound() != 1) {
			EList<EObject> objects = new BasicEList<EObject>();
			objects.add((EObject) req.getParameter(CreateRelationshipRequest.TARGET));
			result = objects;
		} else {
			result = req.getParameter(CreateRelationshipRequest.TARGET);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getConfigureCommand(final ConfigureRequest req) {
		return CompositeCommand.compose(getConfigureSourcesAndTargetsCommand(req), super.getConfigureCommand(req));
	}

	/**
	 * Creates the primitive component of the overall configure command that configures sources and targets
	 * of the new directed relationship.
	 * 
	 * @param req
	 *            the configure request
	 * @return a command to configure the new relationship's sources and targets
	 */
	protected ICommand getConfigureSourcesAndTargetsCommand(final ConfigureRequest req) {
		return new ConfigureElementCommand(req) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				DirectedRelationship element = (DirectedRelationship) req.getElementToConfigure();
				if (req.getParameter(CreateRelationshipRequest.SOURCE) != null) {
					element.eSet(getSourceReference(), getSourceObject(req));
				}
				if (req.getParameter(CreateRelationshipRequest.TARGET) != null) {
					element.eSet(getTargetReference(), getTargetObject(req));
				}
				return CommandResult.newOKCommandResult(element);
			}
		};
	}
}
