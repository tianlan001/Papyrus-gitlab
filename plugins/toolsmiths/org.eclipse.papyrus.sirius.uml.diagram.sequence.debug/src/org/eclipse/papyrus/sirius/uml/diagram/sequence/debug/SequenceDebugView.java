/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.debug;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.sirius.api.debug.AbstractDebugView;
import org.eclipse.sirius.api.debug.TabularReport;
import org.eclipse.sirius.diagram.sequence.SequenceDDiagram;
import org.eclipse.sirius.diagram.sequence.business.internal.VerticalPositionFunction;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.SequenceDiagramEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DDiagramEditPart;

/**
 * The Sequence Debug View implementation. It allows to display the elements ordering constraints.
 */
@SuppressWarnings({ "restriction", "nls" })
public class SequenceDebugView extends AbstractDebugView {

	private static final String LINE_SEPARATOR = "line.separator";
	/**
	 * The global ID for the Eclipse View.
	 */
	public static final String ID = "org.eclipse.papyrus.sirius.diagram.sequence.debug.DebugView";

	/**
	 * Creates the actions available to users.
	 */
	@Override
	protected void createActionButtons() {
		this.addShowOrderingsAction();
		this.addShowEAnnotationAction();

	}

	/**
	 * Prints a report of the graphical orderings on a sequence diagram. For elements which have a valid
	 * graphical position, it is also reported.
	 */
	private void addShowOrderingsAction() {
		this.addAction("Sequence Diagram - Orderings", () -> {
			if (getSelection() instanceof SequenceDiagramEditPart sequenceDiagramEditPart
					&& sequenceDiagramEditPart.resolveSemanticElement() instanceof SequenceDDiagram sequenceDiagram) {
				VerticalPositionFunction verticalPositionFunction = new VerticalPositionFunction(sequenceDiagram);
				TabularReport report = new TabularReport("Element", "Y");
				sequenceDiagram.getGraphicalOrdering().getEventEnds().forEach(eventEnd -> {
					List<String> line = new ArrayList<>();
					EObject semanticEnd = eventEnd.getSemanticEnd();
					line.add(getEndText(semanticEnd));
					Integer yValue = verticalPositionFunction.apply(eventEnd);
					line.add(yValue.intValue() != -1 ? yValue.toString() : "n/a");
					report.addLine(line);
				});
				this.setText(report.print());
			}
		});
	}

	/**
	 * Prints a report of the EAnnotations used by the ordering
	 */
	private void addShowEAnnotationAction() {
		this.addAction("Sequence Diagram - EAnnotations", () -> {

			if (getSelection() instanceof IGraphicalEditPart graphicalEditPart
					&& graphicalEditPart.resolveSemanticElement() instanceof SequenceDDiagram sequenceDDiagram
					&& sequenceDDiagram.getTarget() instanceof EModelElement eModelElement) {

				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("The following table displays the EMF annotations dedicated to the graphical ordering. They are serialized within the root Interaction.");
				stringBuilder.append(System.getProperty(LINE_SEPARATOR));
				stringBuilder.append("----------------------------");
				stringBuilder.append(System.getProperty(LINE_SEPARATOR));
				TabularReport report = new TabularReport("EAnnotation source", "EAnnotation referenced element");
				EAnnotation orderingAnnotation = eModelElement.getEAnnotation(SequenceDiagramOrderServices.ORDERING_ANNOTATION_SOURCE);

				if (orderingAnnotation != null) {
					AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(this.getAdapterFactory());
					orderingAnnotation.getContents().forEach(orderingElement -> addOrderingEndToReport(orderingElement, report, adapterFactoryLabelProvider));
				}
				stringBuilder.append(report.print());
				this.setText(stringBuilder.toString());
			} else {
				this.setText("Select the sequence diagram first");
			}
		});
	}

	private void addOrderingEndToReport(EObject orderingElement, TabularReport report, AdapterFactoryLabelProvider adapterFactoryLabelProvider) {
		if (orderingElement instanceof EAnnotation annotation) {
			EList<EObject> references = annotation.getReferences();
			if (!references.isEmpty()) {
				String referenceText = adapterFactoryLabelProvider.getText(references.get(0));
				report.addLine(List.of(annotation.getSource(), referenceText));
			}
		}
	}

	private String getEndText(EObject eObject) {
		StringBuilder stringBuilder = new StringBuilder();
		if (eObject instanceof EAnnotation annotation) {
			AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(this.getAdapterFactory());
			String sourceKind = retrieveSourceKind(annotation);
			if (!sourceKind.isEmpty()) {
				stringBuilder.append(sourceKind);
			} else {
				stringBuilder.append("Unkown end kind");
			}
			stringBuilder.append(" - ");
			EList<EObject> references = annotation.getReferences();
			if (!references.isEmpty()) {
				EObject semanticTarget = annotation.getReferences().get(0);
				stringBuilder.append(adapterFactoryLabelProvider.getText(semanticTarget));
			}
		}
		return stringBuilder.toString();
	}

	private String retrieveSourceKind(EAnnotation annotation) {
		String source = annotation.getSource();
		return source.substring(source.lastIndexOf('.') + 1);
	}

	@Override
	protected String getTextFor(Object selectedElement) {
		if (selectedElement instanceof DDiagramEditPart) {
			return "Run one of the available actions to display the related information.";
		} else {
			return "Select the diagram blank and run one of the available actions to display the related information.";
		}
	}
}
