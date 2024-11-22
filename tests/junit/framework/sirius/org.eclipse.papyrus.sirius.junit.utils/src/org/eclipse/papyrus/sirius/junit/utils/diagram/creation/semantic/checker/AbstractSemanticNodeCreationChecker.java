/*****************************************************************************
 * Copyright (c) 2022, 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *  Obeo - Improvement of the checker
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.junit.Assert;

/**
 * Semantic Checker for element creation
 */
public abstract class AbstractSemanticNodeCreationChecker implements ISemanticRepresentationElementCreationChecker {

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
	protected int expectedCreatedElements;

	/**
	 * The expected number of associated semantic elements.
	 */
	protected int expectedAssociatedElements;

	/**
	 * 
	 * Constructor.
	 *
	 * @param expectedOwner
	 *            the semantic owner
	 * @param containmentFeature
	 *            the containment feature of the created element
	 */
	public AbstractSemanticNodeCreationChecker(final EObject expectedOwner, EReference containmentFeature) {
		this.semanticOwner = expectedOwner;
		this.containmentFeature = containmentFeature;
		this.nbChildren = getContainmentFeatureValue().size();

		this.expectedCreatedElements = DEFAULT_CREATED_ELEMENTS_NUMBER;
		this.expectedAssociatedElements = DEFAULT_CREATED_ELEMENTS_NUMBER;
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementChecker#validateRepresentationElement(org.eclipse.sirius.viewpoint.DRepresentationElement)
	 *
	 * @param createdElementRepresentation
	 */

	@Override
	public void validateRepresentationElement(DRepresentationElement createdElementRepresentation) {
		final List<EObject> semanticElements = createdElementRepresentation.getSemanticElements();
		Assert.assertEquals("The created element representation must have 1 associated semantic element", getExpectedAssociatedElements(), semanticElements.size()); //$NON-NLS-1$

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
		Assert.assertTrue("The semantic owner doesn't contains the created element.", getContainmentFeatureValue().contains(semanticElement)); //$NON-NLS-1$
		Assert.assertTrue("The created element is not owned by the expected feature.", getContainmentFeatureValue().contains(semanticElement)); //$NON-NLS-1$
		Assert.assertEquals("The owner contains more than one additional element after the creation.", this.nbChildren + this.getNumberOfExpectedCreatedElement(), getContainmentFeatureValue().size()); //$NON-NLS-1$
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
		return this.expectedAssociatedElements;
	}

	/**
	 * Set the expected number of associated semantic elements.
	 * 
	 * @param expectedAssociatedElements
	 *            the expected number of associated semantic elements.
	 */
	public void setExpectedAssociatedElements(int expectedAssociatedElements) {
		this.expectedAssociatedElements = expectedAssociatedElements;
	}

	/**
	 * Get the expected number of additional created elements in the checked {@code containmentFeature}.
	 * 
	 * @return the expected number of additional created elements in the checked {@code containmentFeature}.
	 */
	public int getNumberOfExpectedCreatedElement() {
		return this.expectedCreatedElements;
	}

	/**
	 * Set the expected number of additional created elements in the checked {@code containmentFeature}.
	 * 
	 * @param expectedElements
	 *            the expected number of additional created elements in the checked {@code containmentFeature}.
	 */
	public void setExpectedCreatedElements(int expectedElements) {
		this.expectedCreatedElements = expectedElements;
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementChecker#validateAfterUndo()
	 *
	 */
	public void validateAfterUndo() {
		Assert.assertEquals("The semantic owner must contains the same number of elements as initially", this.nbChildren, getContainmentFeatureValue().size()); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementChecker#validateAfterRedo()
	 *
	 */
	public void validateAfterRedo() {
		Assert.assertEquals("The owner contains more than one additional element after the redo.", this.nbChildren + this.getNumberOfExpectedCreatedElement(), getContainmentFeatureValue().size()); //$NON-NLS-1$
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
		return ((Collection<?>) this.semanticOwner.eGet(getContainmentFeature()));
	}

	/**
	 * 
	 * @return
	 *         the containment feature of the created element
	 */
	protected EReference getContainmentFeature() {
		return this.containmentFeature;
	}
}
