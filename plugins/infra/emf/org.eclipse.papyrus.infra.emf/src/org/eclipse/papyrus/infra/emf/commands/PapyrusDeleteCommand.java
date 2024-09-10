/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.commands;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.google.common.collect.Maps;

/**
 * A specialized deletion command that relies on the cross-reference adapter for efficient determination of
 * the cross-references that need to be cleared when deleting an object (the superclass does an on-demand
 * walk over the resource set).
 */
public class PapyrusDeleteCommand extends DeleteCommand {

	/**
	 * Initializes me with the object(s) to delete.
	 *
	 * @param domain
	 *            the contextual editing domain
	 * @param collection
	 *            the object(s) to delete
	 */
	public PapyrusDeleteCommand(EditingDomain domain, Collection<?> collection) {
		super(domain, collection);
	}

	@Override
	protected Map<EObject, Collection<Setting>> findReferences(Collection<EObject> eObjects) {
		Map<EObject, Collection<Setting>> result;

		ECrossReferenceAdapter xrefs = ECrossReferenceAdapter.getCrossReferenceAdapter(domain.getResourceSet());
		if (xrefs == null) {
			result = super.findReferences(eObjects);
		} else {
			result = Maps.newLinkedHashMap();
			for (EObject next : eObjects) {
				result.put(next, xrefs.getInverseReferences(next, true));
			}
		}

		return result;
	}
}
