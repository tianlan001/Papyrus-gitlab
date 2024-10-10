/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.uml.diagram.common.services;

import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.domain.services.directedit.ElementDirectEditInputValueProvider;
import org.eclipse.papyrus.uml.domain.services.directedit.ElementDirectEditValueConsumer;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDomainBasedEdgeSourceLabelProvider;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDomainBasedEdgeTargetLabelProvider;
import org.eclipse.papyrus.uml.domain.services.labels.ElementLabelProvider;
import org.eclipse.papyrus.uml.domain.services.labels.INamedElementNameProvider;
import org.eclipse.papyrus.uml.domain.services.labels.KeywordLabelProvider;
import org.eclipse.papyrus.uml.domain.services.labels.StereotypeLabelPrefixProvider;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.papyrus.uml.domain.services.status.CheckStatus;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.uml2.uml.Element;

/**
 * Services used to display label.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class UMLLabelServices {


	/**
	 * The ID of the layer used to show/hide the applied Stereotype
	 */
	private static final String APPLIED_STEREOTYPE_LAYER_ID = "AppliedStereotypeLayer"; //$NON-NLS-1$

	/**
	 * The ID of the layer used to show/hide qualified name
	 */
	private static final String QUALIFIED_NAMED_LAYER_ID = "QualifiedNameLayer"; //$NON-NLS-1$

	/**
	 * Renders the label for the provided {@code semanticObject}.
	 * <p>
	 * This method may render labels containing line breaks. See {@link #renderLabelInline(EObject, DDiagram)} to render labels inline.
	 * The provided {@code diagram} is used to retrieve diagram-specific label rendering configuration (e.g. activated layers).
	 * </p>
	 *
	 * @param semanticObject
	 *            a semantic element
	 * @param diagram
	 *            the diagram where the element is displayed
	 * @return a label
	 * 
	 * @see #renderLabelInline(EObject, DDiagram)
	 */
	public String renderLabel(EObject semanticObject, DDiagram diagram) {
		ElementLabelProvider elementLabelProvider = getElementLabelProvider(diagram, false);
		return elementLabelProvider.getLabel(semanticObject);
	}

	/**
	 * Renders the inline label for the provided {@code semanticObject}.
	 * <p>
	 * This method ensures that there is no line break in the returned label. It is
	 * typically used to display elements in a <i>list</i> compartment.
	 * The provided {@code diagram} is used to retrieve diagram-specific label rendering configuration (e.g. activated layers).
	 * </p>
	 * 
	 * @param semanticObject
	 *            the semantic element to compute the label from
	 * @param diagram
	 *            the diagram where the element is displayed
	 * @return the label
	 * 
	 * @see #renderLabel(EObject, DDiagram)
	 */
	public String renderLabelInline(EObject semanticObject, DDiagram diagram) {
		ElementLabelProvider elementLabelProvider = getElementLabelProvider(diagram, true);
		return elementLabelProvider.getLabel(semanticObject);
	}

	/**
	 * Returns the {@link ElementLabelProvider} matching the provided {@code diagram} and {@code inline} flag.
	 * <p>
	 * The provided {@code diagram} is used to retrieve diagram-specific label rendering configuration (e.g. activated layers).
	 * When {@code inline = true} the returned {@link ElementLabelProvider} won't use line breaks to compute labels.
	 * </p>
	 * 
	 * @param diagram
	 *            the diagram to create an {@link ElementLabelProvider} for
	 * @param inline
	 *            a flag indicating whether the {@link ElementLabelProvider} should produce inline labels
	 * @return the created {@link ElementLabelProvider}
	 */
	private ElementLabelProvider getElementLabelProvider(DDiagram diagram, boolean inline) {
		boolean showingQualifiedName = isShowingQualifiedName(diagram);
		boolean showingAppliedStereotype = isShowingAppliedStereotype(diagram);
		Function<EObject, String> stereotypeProvider = (element) -> ""; //$NON-NLS-1$
		INamedElementNameProvider elementNameProvider = new LabelElementNameProvider();
		if (showingAppliedStereotype) {
			stereotypeProvider = new StereotypeLabelPrefixProvider();
		}
		if (showingQualifiedName) {
			elementNameProvider = new NamedElementQualifiedNameProvider();
		}
		ElementLabelProvider.Builder elementLabelProviderBuilder = ElementLabelProvider.builder()
				.withKeywordLabelProvider(new KeywordLabelProvider())
				.withNameProvider(elementNameProvider)
				.withPrefixLabelProvider(stereotypeProvider);
		if (inline) {
			elementLabelProviderBuilder.withKeywordSeparator(UMLCharacters.SPACE)
					.withPrefixSeparator(UMLCharacters.SPACE);
		}
		return elementLabelProviderBuilder.build();
	}

	/**
	 * Returns true if the Layer "Display Qualified Name" is activated.
	 * 
	 * @param diagram
	 *            the current diagram
	 * @return
	 *         <code>true</code> if we need to use the Qualified Name of elements in the diagram.
	 */
	private boolean isShowingQualifiedName(final DDiagram diagram) {
		return diagram.getActivatedLayers().stream()
				.filter(layer -> QUALIFIED_NAMED_LAYER_ID.equals(layer.getName()))
				.findFirst()
				.isPresent();
	}

	/**
	 * Returns true if the Layer "Display Applied Stereotypes" is activated.
	 * 
	 * @param diagram
	 *            the current diagram
	 * @return
	 *         <code>true</code> if we need to display the stereotype of elements in the diagram.
	 */
	private boolean isShowingAppliedStereotype(final DDiagram diagram) {
		return diagram.getActivatedLayers().stream()
				.filter(layer -> APPLIED_STEREOTYPE_LAYER_ID.equals(layer.getName()))
				.findFirst()
				.isPresent();
	}

	/**
	 * Render the label of the source of an Edge representing a given semantic {@link EObject}.
	 * 
	 * @param semanticObject
	 *            the semantic {@link EObject} represented by the edge
	 * @param semanticSource
	 *            the semantic source of the edge
	 * @return the label of the source of an Edge representing a given semantic {@link EObject}.
	 */
	public String renderEdgeSourceLabel(EObject semanticObject, EObject semanticSource) {
		return ElementDomainBasedEdgeSourceLabelProvider.buildDefault().getLabel(semanticObject, semanticSource);
	}

	/**
	 * Render the label of the target of an Edge representing a given semantic {@link EObject}.
	 * 
	 * @param semanticObject
	 *            the semantic {@link EObject} represented by the edge
	 * @param semanticSource
	 *            the semantic target of the edge
	 * @return the label of the target of an Edge representing a given semantic {@link EObject}.
	 */
	public String renderEdgeTargetLabel(EObject semanticObject, EObject semanticTarget) {
		return ElementDomainBasedEdgeTargetLabelProvider.buildDefault().getLabel(semanticObject, semanticTarget);
	}



	/**
	 * A service to retrieve the label to display when the direct edit is activated.
	 * 
	 * @param element
	 *            the current element.
	 * @return the label to display.
	 */
	public String getDirectEditInputValue(Element element) {
		ElementDirectEditInputValueProvider provider = new ElementDirectEditInputValueProvider();
		return provider.getDirectEditInputValue(element);
	}

	/**
	 * A service to handle the new set value during the direct edit.
	 * 
	 * @param element
	 *            the edited element.
	 * @param input
	 *            the new direct edit value.
	 */
	public void consumeNewLabel(Element element, String input) {
		ElementDirectEditValueConsumer consumer = new ElementDirectEditValueConsumer();
		CheckStatus status = consumer.consumeNewLabel(element, input);
		if (!status.isValid()) {
			Activator.log.warn(status.getMessage());
		}
	}

}
