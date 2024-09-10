/*****************************************************************************
 * Copyright (c) 2013, 2016 Atos, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Arthur Daussy (Atos) arthur.daussy@atos.net - Initial API and implementation
 *  Christian W. Damus - bugs 399859, 497865
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - bug 460435
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.controlmode.profile.commands;

import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.commands.AbstractControlCommand;
import org.eclipse.papyrus.uml.controlmode.profile.helpers.ProfileApplicationHelper;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.uml2.uml.Element;

import com.google.common.collect.Sets;

/**
 * Command to move stereotype application to new resource when controling or uncontroling elements
 *
 * @author adaussy
 *
 */
public final class MoveStereotypeApplicationToControlResource extends AbstractControlCommand {

	/** The Constant UNKNOWN_TARGET_RESOURCE_ERROR. */
	private static final String UNKNOWN_TARGET_RESOURCE_ERROR = "Target resource is unknown";

	/** The Constant COMMAND_LABEL. */
	private static final String COMMAND_LABEL = "Move stereotype application";

	/**
	 * Instantiates a new move stereotype application to control resource.
	 *
	 * @param affectedFiles
	 *            the affected files
	 * @param request
	 *            the request
	 */
	@SuppressWarnings("rawtypes")
	public MoveStereotypeApplicationToControlResource(List affectedFiles, ControlModeRequest request) {
		super(COMMAND_LABEL, affectedFiles, request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		// Build a set with target element and its related contents
		Element targetElement = (Element) getRequest().getTargetObject();
		Set<Element> targetElementsSet = Sets.newHashSet(targetElement);

		// Retrieve related contents
		TreeIterator<Object> contents = EcoreUtil.getAllProperContents(targetElement, true);
		while (contents.hasNext()) {
			EObject currentElement = (EObject) contents.next();
			if (currentElement instanceof Element) {
				targetElementsSet.add((Element) currentElement);
			}
		}

		// Retrieve related resources by control command
		Resource sourceResource = getRequest().getSourceResource(UmlModel.UML_FILE_EXTENSION);
		Resource targetResource = getRequest().getTargetResource(UmlModel.UML_FILE_EXTENSION);

		if (targetResource == null) {
			return createNewControlCommandError(UNKNOWN_TARGET_RESOURCE_ERROR);
		}

		// Relocate all target elements
		for (Element currentElement : targetElementsSet) {
			ProfileApplicationHelper.relocateStereotypeApplications(currentElement, sourceResource, targetResource);
		}

		return CommandResult.newOKCommandResult();

	}
}
