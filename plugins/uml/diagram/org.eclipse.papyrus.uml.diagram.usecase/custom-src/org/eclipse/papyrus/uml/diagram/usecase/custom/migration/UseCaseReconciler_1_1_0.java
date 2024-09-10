/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.custom.migration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.InsertFloatingLabelFromMapCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler;

/**
 * UseCase Diagram Reconciler from 1.0.0 to 1.1.0
 */
public class UseCaseReconciler_1_1_0 extends DiagramReconciler {

	private final static String ActorEditPartTN_VISUAL_ID = "2011";
	private final static String ActorFloatingLabelEditPartTN_VISUAL_ID = "6048";
	private final static String ActorInComponentEditPart_VISUAL_ID = "3018";
	private final static String ActorInComponentFloatingLabelEditPart_VISUAL_ID = "6050";
	private final static String ActorInPackageEditPart_VISUAL_ID = "3011";
	private final static String ActorInPackageFloatingLabelEditPart_VISUAL_ID = "6049";
	private final static String ComponentInPackageEditPart_VISUAL_ID = "3013";
	private final static String ComponentInPackageFloatingLabelEditPart_VISUAL_ID = "6051";
	private final static String SubjectClassifierEditPartTN_VISUAL_ID = "2015";
	private final static String SubjectClassifierFloatingLabelEditPartTN_VISUAL_ID = "6047";
	private final static String UseCaseEditPartTN_VISUAL_ID = "2013";
	private final static String UseCaseFloatingLabelEditPartTN_VISUAL_ID = "6038";
	private final static String UseCaseInComponentEditPart_VISUAL_ID = "3009";
	private final static String UseCaseInComponentFloatingLabelEditPart_VISUAL_ID = "6045";
	private final static String UseCaseInPackageEditPart_VISUAL_ID = "3012";
	private final static String UseCaseInPackageFloatingLabelEditPart_VISUAL_ID = "6046";

	/**
	 * Gets the reconcile command.
	 *
	 * @param diagram
	 *            the diagram
	 * @return the reconcile command
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler#getReconcileCommand(org.eclipse.gmf.runtime.notation.Diagram)
	 */
	@Override
	public ICommand getReconcileCommand(Diagram diagram) {
		return new InsertFloatingLabelFromMapCommand(diagram, getFloatingLabelMap());
	}

	/**
	 * Gets the floating label map to add.
	 *
	 * @return the floating label map
	 */
	private Map<String, String> getFloatingLabelMap() {
		Map<String, String> map = new HashMap<>();
		map.put(ActorEditPartTN_VISUAL_ID, ActorFloatingLabelEditPartTN_VISUAL_ID);
		map.put(ActorInComponentEditPart_VISUAL_ID, ActorInComponentFloatingLabelEditPart_VISUAL_ID);
		map.put(ActorInPackageEditPart_VISUAL_ID, ActorInPackageFloatingLabelEditPart_VISUAL_ID);
		map.put(ComponentInPackageEditPart_VISUAL_ID, ComponentInPackageFloatingLabelEditPart_VISUAL_ID);
		map.put(SubjectClassifierEditPartTN_VISUAL_ID, SubjectClassifierFloatingLabelEditPartTN_VISUAL_ID);
		map.put(UseCaseEditPartTN_VISUAL_ID, UseCaseFloatingLabelEditPartTN_VISUAL_ID);
		map.put(UseCaseInComponentEditPart_VISUAL_ID, UseCaseInComponentFloatingLabelEditPart_VISUAL_ID);
		map.put(UseCaseInPackageEditPart_VISUAL_ID, UseCaseInPackageFloatingLabelEditPart_VISUAL_ID);

		return map;
	}
}
