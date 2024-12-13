/*****************************************************************************
 * Copyright (c) 2023, 2024 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.junit.Assert;

/**
 * Semantic Checker for edge creation
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public abstract class AbstractSemanticEdgeCreationChecker implements ISemanticRepresentationElementCreationChecker {

	/**
	 * By default, when executing a creation tool, only one semantic element is created.
	 */
	private static final int DEFAULT_CREATED_ELEMENTS_NUMBER = 1;

	/**
	 * The semantic owner of the created element
	 */
	protected final EObject semanticOwner;


	private final EReference containmentFeature;

	/**
	 * The number of children in the owning feature before the creation
	 */
	protected final int nbChildren;

	/**
	 * The expected number of additional created elements in the {@code containmentFeature}.
	 */
	protected int expectedCreatedElements = DEFAULT_CREATED_ELEMENTS_NUMBER;

	/**
	 * The expected number of associated semantic elements.
	 */
	protected int expectedAssociatedElements = DEFAULT_CREATED_ELEMENTS_NUMBER;

	/**
	 *
	 * Constructor.
	 *
	 * @param expectedOwner
	 *            the semantic owner
	 * @param containmentFeature
	 *            the containment feature of the created element
	 */
	public AbstractSemanticEdgeCreationChecker(final EObject expectedOwner, EReference containmentFeature) {
		this.semanticOwner = expectedOwner;
		this.containmentFeature = containmentFeature;
		this.nbChildren = getContainmentFeatureValue().size();
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.AbstractSemanticEdgeCreationChecker#validateRepresentationElement(org.eclipse.sirius.viewpoint.DRepresentationElement)
	 *
	 * @param createdElementRepresentation
	 */
	@Override
	public void validateRepresentationElement(DRepresentationElement createdElementRepresentation) {
		final List<EObject> semanticElements = createdElementRepresentation.getSemanticElements();
		Assert.assertEquals("Number associated element is wrong", getExpectedAssociatedElements(), semanticElements.size()); //$NON-NLS-1$

		final EObject element = semanticElements.get(0);
		validateSemanticElementInstance(element);
		validateSemanticOwner(element);
	}

	/**
	 * This method checks the expected owner of the created element
	 *
	 * @param semanticElement
	 *            the owner of the created element
	 */
	protected void validateSemanticOwner(final EObject semanticElement) {
		Assert.assertTrue("Owner does not contain the created element.", getContainmentFeatureValue().contains(semanticElement)); //$NON-NLS-1$
		Assert.assertEquals("Owner contains the number of elements.", nbChildren + getNumberOfExpectedCreatedElement(), getContainmentFeatureValue().size()); //$NON-NLS-1$
	}

	/**
	 * this method validate the type of the created element
	 *
	 * @param createdElement
	 *            the created element
	 *
	 */
	protected abstract void validateSemanticElementInstance(final EObject createdElement);

	/**
	 * Get the expected number of associated semantic elements.
	 *
	 * @return the expected number of associated semantic elements.
	 */
	public int getExpectedAssociatedElements() {
		return expectedAssociatedElements;
	}

	/**
	 * Set the expected number of associated semantic elements.
	 *
	 * @param value
	 *            the expected number of associated semantic elements.
	 */
	public void setExpectedAssociatedElements(int value) {
		expectedAssociatedElements = value;
	}

	/**
	 * Get the expected number of additional created elements in the checked {@code containmentFeature}.
	 *
	 * @return the expected number of additional created elements in the checked {@code containmentFeature}.
	 */
	public int getNumberOfExpectedCreatedElement() {
		return expectedCreatedElements;
	}

	/**
	 * Set the expected number of additional created elements in the checked {@code containmentFeature}.
	 *
	 * @param value
	 *            the expected number of additional created elements in the checked {@code containmentFeature}.
	 */
	public void setExpectedCreatedElements(int value) {
		expectedCreatedElements = value;
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementChecker#validateAfterUndo()
	 *
	 */
	@Override
	public void validateAfterUndo() {
		Assert.assertEquals("Owner must contain the same number of elements as initially", nbChildren, getContainmentFeatureValue().size()); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementChecker#validateAfterRedo()
	 *
	 */
	@Override
	public void validateAfterRedo() {
		Assert.assertEquals("Owner contains unexpected number of element after the redo.", nbChildren + getNumberOfExpectedCreatedElement(), getContainmentFeatureValue().size()); //$NON-NLS-1$
	}

	/**
	 *
	 * @return
	 *         the value of the feature that should contain the created element
	 */
	protected final Collection<?> getContainmentFeatureValue() {
		final EReference owningFeature = getContainmentFeature();
		if (!owningFeature.isMany()) {
			throw new UnsupportedOperationException("The case where the owning feature is not multi-valued is not yet implemented"); //$NON-NLS-1$
		}
		return ((Collection<?>) semanticOwner.eGet(getContainmentFeature()));
	}

	/**
	 *
	 * @return
	 *         the containment feature of the created element
	 */
	protected EReference getContainmentFeature() {
		return containmentFeature;
	}
}
