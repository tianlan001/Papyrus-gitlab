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
 *   Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.diagram.clazz.providers.ElementInitializers;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The Class PropertyCommandForAssociation use to create a property into an association (ownedEnd)
 */
public class PropertyCommandForAssociation extends CreateElementCommand {

	/**
	 * Constructor.
	 *
	 * @param request
	 */
	public PropertyCommandForAssociation(CreateElementRequest request) {
		super(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EObject doDefaultElementCreation() {
		Property newElement = UMLFactory.eINSTANCE.createProperty();
		Association owner = (Association) getElementToEdit();
		Object type = getRequest().getParameter("type");
		if (type != null && type instanceof Type) {
			newElement.setType((Type) type);
			newElement.setName(((Type) type).getName());
		}
		owner.getOwnedEnds().add(newElement);
		ElementInitializers.getInstance().init_Property_SignalAttributeLabel(newElement);
		if (type != null && type instanceof Type) {
			newElement.setName(((Type) type).getName());
			if (InternationalizationPreferencesUtils.getInternationalizationPreference((Type) type) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML((Type) type)) {
				UMLLabelInternationalization.getInstance().setLabel((Type) type, UMLLabelInternationalization.getInstance().getLabelWithoutUML((Type) type), null);
			}
		}
		return newElement;
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		Property newElement = (Property) doDefaultElementCreation();
		((CreateElementRequest) getRequest()).setNewElement(newElement);
		return CommandResult.newOKCommandResult(newElement);
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	protected EClass getEClassToEdit() {
		return UMLPackage.eINSTANCE.getAssociation();
	}
}
