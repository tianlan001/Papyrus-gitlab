/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 429826
 *  Camille Letavernier (CEA LIST) - Bug 430118
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.drafter;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.AbstractReadOnlyHandler;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ReadOnlyAxis;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.uml2.uml.Profile;

import com.google.common.base.Optional;


/**
 * Discretion-based read-only handler for applied profiles.
 */
public class AppliedProfileReadOptionalyWriteHandler extends AbstractReadOnlyHandler {

	public AppliedProfileReadOptionalyWriteHandler(EditingDomain editingDomain) {
		super(editingDomain);
	}

	public Optional<Boolean> anyReadOnly(Set<ReadOnlyAxis> axes, URI[] uris) {
		if((getEditingDomain() != null) && axes.contains(ReadOnlyAxis.DISCRETION)) {
			Resource mainUmlResource = null;
			if(getEditingDomain().getResourceSet() instanceof ModelSet) {
				UmlModel umlModel = (UmlModel)((ModelSet)getEditingDomain().getResourceSet()).getModel(UmlModel.MODEL_ID);
				if(umlModel == null) {
					return Optional.absent();
				}
				mainUmlResource = umlModel.getResource();
			}

			for(URI uri : uris) {
				Resource resource = getEditingDomain().getResourceSet().getResource(uri.trimFragment(), false);
				if(isProfileResource(resource) && mainUmlResource != resource) {
					return Optional.absent();
//					return Optional.of(Boolean.TRUE);
				}
			}
		}

		return Optional.absent();
	}

	private boolean isProfileResource(Resource resource) {
		if(resource == null) {
			return false;
		}

		if(!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof Profile) {
			return true;
		}

		return false;
	}

	/**
	 * We want to be able to modify existing profiles.
	 * 
	 * @see org.eclipse.papyrus.infra.core.resource.IReadOnlyHandler2#makeWritable(java.util.Set, org.eclipse.emf.common.util.URI[])
	 *
	 * @param axes
	 * @param uris
	 * @return
	 */
	public Optional<Boolean> makeWritable(Set<ReadOnlyAxis> axes, URI[] uris) {
		return Optional.of(Boolean.TRUE); 
	}

}
