/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.rules.container;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IMetamodelType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.types.Activator;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType;
import org.eclipse.papyrus.infra.types.rulebased.core.AbstractRule;

@SuppressWarnings("restriction")
public class InvariantContainerRule extends AbstractRule<InvariantContainerRuleConfiguration> {

	protected boolean approveCreationRequest(ConfiguredHintedSpecializationElementType typeToCreate, CreateElementRequest request) {
		EObject newContainer = request.getContainer();
		return canContain(newContainer);
	}

	protected boolean approveSetRequest(ConfiguredHintedSpecializationElementType typeToMove, SetRequest request) {
		EObject newContainer = request.getElementToEdit();
		return canContain(newContainer);
	}

	protected boolean approveMoveRequest(ConfiguredHintedSpecializationElementType typeToMove, MoveRequest request) {
		EObject newContainer = request.getTargetContainer();
		return canContain(newContainer);
	}

	protected boolean canContain(EObject container) {
		if (container == null) {
			return false;
		}
		boolean isValid = false;
		try {
			IElementType newContainerElementType = ElementTypeRegistry.getInstance().getElementType(container, TypeContext.getContext(container));

			for (HierarchyPermission permission : invariantRuleConfiguration.getPermissions()) { // for each permission, get the matcher and matches directly
				boolean isPermitted = permission.isPermitted();
				String permissionContainerTypeID = permission.getContainerType();
				boolean isStrict = permission.isStrict();
				IElementType permissionContainerType = ElementTypeRegistry.getInstance().getType(permissionContainerTypeID);

				// check is the permission type is a specialization type or a metamodel type, to enhance performances
				// if this is a metamodel type and strict, a simple equals can match or not if it is permitted or not
				// if this is a metamodel type and not strict, we only compare the list of supertypes of the eObject type with the permission Type
				if (permissionContainerType instanceof IMetamodelType) {
					if (isStrict) {
						isValid = permissionContainerType.equals(newContainerElementType) ? isPermitted : !isPermitted;
					} else {
						// this is not strict
						// so any super metamodeltype of permission type that matches newContainerElementType is OK
						// the type itself can also match...
						if (newContainerElementType.equals(permissionContainerType)) {
							isValid = isPermitted;
						} else {
							isValid = Arrays.asList(newContainerElementType.getAllSuperTypes()).contains(permissionContainerType) ? isPermitted : !isPermitted;
						}

					}
				} else if (permissionContainerType instanceof ISpecializationType) {
					IElementMatcher matcher = ((ISpecializationType) permissionContainerType).getMatcher();
					if (matcher != null) {
						boolean matchesSpecialization = matcher.matches(container) && newContainerElementType.equals(((ISpecializationType) permissionContainerType).getMetamodelType()); // the eObject matches the specializations type that is permitted.
						if (matchesSpecialization) {
							// test the isStrict now... It will be false if it matches one of the sub-specialization type
							if (isStrict) {
								ISpecializationType[] subtypes = ElementTypeRegistry.getInstance().getSpecializationsOf(permissionContainerTypeID);
								if (subtypes != null && subtypes.length > 0) {
									for (ISpecializationType subType : subtypes) {
										isValid = subType.getMatcher().matches(container) ? isPermitted : !isPermitted; // the isStrict is not verified (one of the subtypes is matched whereas it should not)
									}
								} else { // there are no further sub specialization types.
									isValid = isPermitted;
								}
							} else {
								// not strict
								isValid = isPermitted;
							}
						} else { // eObject type does not match the permission type
							isValid = !isPermitted;

						}
					}
				}
			}

		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		return isValid;
	}


	@Override
	public boolean matches(EObject eObject) {
		EObject container = eObject.eContainer();

		return canContain(container);
	}


}
