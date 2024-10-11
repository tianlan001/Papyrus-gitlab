/******************************************************************************
 * Copyright (c) 2021, 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Artal Technologies - Initial API and implementation
 *  Obeo - Integration nouveau plugin sequence
 *****************************************************************************/

package org.eclipse.papyrus.sirius.editor.representation.architecture;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.utils.EditorNameInitializer;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.sirius.editor.internal.viewpoint.SiriusDiagramViewPrototype;
import org.eclipse.papyrus.sirius.editor.representation.ICreateSiriusDiagramEditorCommand;
import org.eclipse.papyrus.sirius.editor.representation.SiriusDiagramPrototype;
import org.eclipse.papyrus.sirius.editor.representation.architecture.internal.messages.Messages;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.DiagramPackage;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * This class allows to create new Sirius Diagram instance and open the papyrus editor for it
 * TODO : VL : This can must be moved in another plugin. Here we can't get UML dependencies
 * TODO : VL Remove UML dependencies from this plugin
 */
public class CreatePapyrusSiriusSequenceDiagramEditorCommand extends AbstractCreateSiriusDiagramEditorCommand implements ICreateSiriusDiagramEditorCommand {

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
	 *
	 * @param prototype
	 * @param name
	 * @param semanticContext
	 * @param openAfterCreation
	 * @return
	 */
	@Override
	public DSemanticDiagram execute(final ViewPrototype prototype, final String name, final EObject semanticContext, final boolean openAfterCreation) {
		return execute(prototype, name, semanticContext, semanticContext, openAfterCreation);
	}

	/**
	 * @see org.eclipse.papyrus.sirius.editor.representation.ICreateSiriusDiagramEditorCommand.ICreateDSemanticDiagramEditorCommand#execute(org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype, java.lang.String,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean)
	 *
	 * @param prototype
	 * @param name
	 * @param semanticContext
	 * @param graphicalContext
	 * @param openAfterCreation
	 * @return
	 */
	@Override
	public DSemanticDiagram execute(final ViewPrototype prototype, final String name, EObject semanticContext, final EObject graphicalContext, boolean openAfterCreation) {
		if (prototype instanceof SiriusDiagramViewPrototype) {
			final PapyrusRepresentationKind representation = prototype.getRepresentationKind();
			Assert.isTrue(representation instanceof SiriusDiagramPrototype, "The representation associated to the PapyrusDSemanticDiagramViewPrototype must be an instanceof SiriusDiagramPrototype."); //$NON-NLS-1$
			SiriusDiagramPrototype docProto = (SiriusDiagramPrototype) representation;

			final String diagramName = (name == null || name.isEmpty()) ? askName(prototype, semanticContext) : name;
			if (null == diagramName) {
				return null; // the creation is cancelled
			}
			
			if (!(semanticContext instanceof Interaction)) {
				Interaction interaction = UMLFactory.eINSTANCE.createInteraction();
				ElementDefaultNameProvider elementDefaultNameProvider = new ElementDefaultNameProvider();
				interaction.setName(elementDefaultNameProvider.getDefaultName(interaction, semanticContext));
				TransactionalEditingDomain ted = this.getEditingDomain(semanticContext);
				if (semanticContext instanceof Package) {
					ted.getCommandStack().execute(new RecordingCommand(ted) {

						@Override
						protected void doExecute() {

							((Package) semanticContext).getPackagedElements().add(interaction);
						}
					});
				} else if (semanticContext instanceof BehavioredClassifier) {
					ted.getCommandStack().execute(new RecordingCommand(ted) {

						@Override
						protected void doExecute() {
							((BehavioredClassifier) semanticContext).getOwnedBehaviors().add(interaction);
							((BehavioredClassifier) semanticContext).setClassifierBehavior(interaction);
						}
					});
				}
					return super.execute(docProto, diagramName, interaction, interaction, openAfterCreation, docProto.getImplementationID());
			
			}
			return super.execute(docProto, diagramName, graphicalContext, semanticContext, openAfterCreation, docProto.getImplementationID());
		}
		return null;
	};

}
