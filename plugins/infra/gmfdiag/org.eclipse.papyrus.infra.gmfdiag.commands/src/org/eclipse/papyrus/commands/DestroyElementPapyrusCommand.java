/*****************************************************************************
 * Copyright (c) 2010 Atos Origin
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * This destroy command uses the first generic cross referencer founded instead
 * of using only the GMF one. This is useful because elements that don't have a
 * corresponding GMF type (like MOS in the sequence diagram) don't have the GMF
 * cross referencer registered after a reload.
 *
 * @author mvelten
 *
 */
public class DestroyElementPapyrusCommand extends DestroyElementCommand {

	public DestroyElementPapyrusCommand(DestroyElementRequest request) {
		super(request);

	}

	private List<Object> affectedFiles;

	// Compute the affected files as late as possible, as this is an expensive operation (CrossReference)
	@Override
	public List<Object> getAffectedFiles() {
		if (affectedFiles == null) {
			affectedFiles = new ArrayList<Object>(super.getAffectedFiles());
			affectedFiles.addAll(fileOfIncomingReferences(((DestroyElementRequest) getRequest()).getElementToDestroy()));
		}
		return affectedFiles;
	}

	/**
	 * Compute list of affected files affected by the tear donw methods
	 *
	 * @param destructee
	 * @return
	 */
	protected List fileOfIncomingReferences(EObject destructee) {
		if (destructee != null) {
			Collection<Setting> usages = EMFHelper.getUsages(destructee);
			List<Object> result = new ArrayList<>();
			Set<Resource> resources = new HashSet<>();
			for (Setting setting : usages) {
				if (setting.getEStructuralFeature() instanceof EReference) {
					EReference eRef = (EReference) setting.getEStructuralFeature();
					if (eRef.isChangeable() && (eRef.isDerived() == false) && (eRef.isContainment() == false) && (eRef.isContainer() == false)) {
						Resource resource = setting.getEObject().eResource();
						if (resource != null) {
							resources.add(resource);
						}
					}
				}
			}
			for (Resource resource : resources) {
				IFile file = WorkspaceSynchronizer.getFile(resource);
				if (file != null) {
					result.add(file);
				}
			}


			return result;
		}
		return Collections.emptyList();
	}

	@Override
	protected void tearDownIncomingReferences(EObject destructee) {
		Collection<Setting> usages = EMFHelper.getUsages(destructee);

		for (Setting setting : usages) {
			if (setting.getEStructuralFeature() instanceof EReference) {
				EReference eRef = (EReference) setting.getEStructuralFeature();
				if (eRef.isChangeable() && (eRef.isDerived() == false) && (eRef.isContainment() == false) && (eRef.isContainer() == false)) {
					EcoreUtil.remove(setting.getEObject(), eRef, destructee);
				}
			}
		}
	}

}
