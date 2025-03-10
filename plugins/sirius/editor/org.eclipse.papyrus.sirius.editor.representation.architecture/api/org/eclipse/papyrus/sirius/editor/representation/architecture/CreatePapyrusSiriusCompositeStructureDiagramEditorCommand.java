/*****************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.editor.representation.architecture;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.utils.EditorNameInitializer;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.sirius.editor.internal.viewpoint.SiriusDiagramViewPrototype;
import org.eclipse.papyrus.sirius.editor.representation.ICreateSiriusDiagramEditorCommand;
import org.eclipse.papyrus.sirius.editor.representation.SiriusDiagramPrototype;
import org.eclipse.papyrus.sirius.editor.representation.architecture.internal.messages.Messages;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.DiagramPackage;
import org.eclipse.sirius.viewpoint.ViewpointPackage;

/**
 * This class allows to create new Sirius Diagram instance and open the papyrus editor for it
 */
public class CreatePapyrusSiriusCompositeStructureDiagramEditorCommand extends AbstractCreateSiriusDiagramEditorCommand implements ICreateSiriusDiagramEditorCommand {

	/**
	 * Prompts the user the future diagram's name
	 *
	 * @return The name, or <code>null</code> if the user cancelled the creation
	 */
	private String askName(final ViewPrototype prototype, final EObject semanticContext) {
		final String defaultName = getDefaultName(prototype, semanticContext);
		return askDiagramName(Messages.CreatePapyrusSiriusDiagramEditorCommand_CreateSiriusDiagramDialogTitle, defaultName);
	}

	/**
	 *
	 * @param prototype
	 *            the ViewPrototype
	 * @param semanticContext
	 *            the semantic context for the created DSemanticDiagram
	 * @return
	 *         the default name to use
	 */
	private String getDefaultName(final ViewPrototype prototype, final EObject semanticContext) {
		final StringBuilder nameBuilder = new StringBuilder(prototype.getLabel().replaceAll(" ", "")); //$NON-NLS-1$ //$NON-NLS-2$
		final String nameWithIncrement = EditorNameInitializer.getNameWithIncrement(DiagramPackage.eINSTANCE.getDDiagram(), ViewpointPackage.eINSTANCE.getDRepresentationDescriptor_Name(), nameBuilder.toString(),
				semanticContext);
		return nameWithIncrement;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.sirius.editor.internal.ICreateSiriusDiagramEditorCommand.ICreateDSemanticDiagramEditorCommand#execute(org.eclipse.papyrus.sirius.editor.internal.viewpoint.PapyrusDSemanticDiagramViewPrototype,
	 *      org.eclipse.emf.ecore.EObject, java.lang.String)
	 */
	@Override
	public DSemanticDiagram execute(final ViewPrototype prototype, final String name, final EObject semanticContext, final boolean openAfterCreation) {
		return execute(prototype, name, semanticContext, semanticContext, openAfterCreation);
	}

	/**
	 * @see org.eclipse.papyrus.sirius.editor.representation.ICreateSiriusDiagramEditorCommand.ICreateDSemanticDiagramEditorCommand#execute(org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype, java.lang.String,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean)
	 */
	@Override
	public DSemanticDiagram execute(final ViewPrototype prototype, final String name, final EObject semanticContext, final EObject graphicalContext, boolean openAfterCreation) {
		if (prototype instanceof SiriusDiagramViewPrototype) {
			final PapyrusRepresentationKind representation = prototype.getRepresentationKind();
			Assert.isTrue(representation instanceof SiriusDiagramPrototype, "The representation associated to the SiriusDiagramViewPrototype must be an instanceof SiriusDiagramPrototype."); //$NON-NLS-1$
			SiriusDiagramPrototype siriusDiagramPrototype = (SiriusDiagramPrototype) representation;

			final String diagramName = (name == null || name.isEmpty()) ? askName(prototype, semanticContext) : name;
			if (null == diagramName) {
				return null; // the creation is cancelled
			}

			return super.execute(siriusDiagramPrototype, diagramName, semanticContext, graphicalContext, openAfterCreation, siriusDiagramPrototype.getImplementationID());
		}
		return null;
	};

}
