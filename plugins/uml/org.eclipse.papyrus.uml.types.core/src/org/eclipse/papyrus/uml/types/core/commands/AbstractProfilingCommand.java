/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

public abstract class AbstractProfilingCommand extends AbstractTransactionalCommand {

	/**
	 * Constructor.
	 *
	 * @param domain
	 * @param label
	 * @param affectedFiles
	 */
	public AbstractProfilingCommand(TransactionalEditingDomain domain, String label, List<Object> affectedFiles) {
		super(domain, label, affectedFiles);
	}

	protected static List<Object> getAffectedFiles(IEditCommandRequest request) {
		List<Object> result = new ArrayList<Object>();
		List<?> elements = request.getElementsToEdit();
		int size;
		if (elements != null && ((size = elements.size()) > 0)) {
			if (size == 1) {
				Resource resource = ((EObject) elements.get(0)).eResource();
				if (resource != null) {
					IFile file = WorkspaceSynchronizer.getFile(resource);
					if (file != null) {
						result.add(file);
					}
				}
			} else {
				Map<Resource, Object> resourcesToFileMap = new HashMap<Resource, Object>();
				for (int i = 0; i < size; ++i) {
					Resource resource = ((EObject) elements.get(i)).eResource();
					if (resource != null) {
						Object file = resourcesToFileMap.get(resource);
						// if it is in the Map, then it is in the List already as well
						if (file == null) {
							file = WorkspaceSynchronizer.getFile(resource);
							if (file != null) {
								resourcesToFileMap.put(resource, file);
								result.add(file);
							}
						}
					}
				}
			}
		}
		return result;
	}




}
