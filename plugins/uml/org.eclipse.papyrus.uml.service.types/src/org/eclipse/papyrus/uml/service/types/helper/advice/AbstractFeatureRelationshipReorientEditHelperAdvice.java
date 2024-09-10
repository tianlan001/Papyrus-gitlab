/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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
 *   Mickaël ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 517679
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.Arrays;
import java.util.List;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.core.utils.ElementTypeRegistryUtils;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;

public abstract class AbstractFeatureRelationshipReorientEditHelperAdvice extends AbstractEditHelperAdvice {


	@Override
	protected ICommand getBeforeReorientReferenceRelationshipCommand(ReorientReferenceRelationshipRequest request) {

		Object value = request.getParameters().get(DefaultSemanticEditPolicy.GRAPHICAL_RECONNECTED_EDGE);

		if (value instanceof View) {

			try {
				IClientContext context = TypeContext.getContext((View) value);
				List<IElementType> elementTypes = ElementTypeRegistryUtils.getElementTypesBySemanticHint(((View) value).getType(), context.getId());

				for (IElementType iElementType : elementTypes) {

					List<ISpecializationType> subs = Arrays.asList(ElementTypeRegistry.getInstance().getSpecializationsOf(elementTypeIDToSpecialize()));

					boolean typeOf = ElementUtil.isTypeOf(iElementType, ElementTypeRegistry.getInstance().getType(elementTypeIDToSpecialize()));// Fix due to the miss of element in all context
					if (subs.contains(iElementType) || typeOf) {
						return getFeatureRelationshipReorientCommand(request);
					}
				}
			} catch (ServiceException e) {
				org.eclipse.papyrus.uml.service.types.Activator.log.error(e);
			}

		}

		return null;
	}

	/**
	 * @return
	 */
	protected abstract String elementTypeIDToSpecialize();

	protected abstract ICommand getFeatureRelationshipReorientCommand(ReorientReferenceRelationshipRequest request);


}
