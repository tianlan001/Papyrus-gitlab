/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.DefaultEditPartProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure;

/**
 * EditPartProvider for papyrus generic edit part which are not associated to a diagram.
 * 
 * @author Mickaël ADAM
 * 
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeEditPartProvider extends DefaultEditPartProvider {

	/**
	 * The expected model id for this DefaultEditPartProvider.
	 */
	private static final String EXPECTED_MODEL_ID = "PapyrusEditPartProviderModelID";//$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public StereotypePropertyReferenceEdgeEditPartProvider() {
		super(new StereotypePropertyReferenceEdgeEditPartFactory(), new DiagramStructureExtension(), EXPECTED_MODEL_ID);
	}

	/**
	 * Empty {@link DiagramStructure} to permits to use {@link DefaultEditPartProvider} in all diagram.
	 * 
	 * @author Mickael ADAM
	 */
	private static final class DiagramStructureExtension extends DiagramStructure {
		/**
		 * {@inheritDoc
		 * 
		 * @see org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure#isSemanticLeafVisualID(java.lang.String)
		 */
		@Override
		public boolean isSemanticLeafVisualID(final String visualID) {
			return false;
		}

		/**
		 * {@inheritDoc
		 * 
		 * @see org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure#isCompartmentVisualID(java.lang.String)
		 */
		@Override
		public boolean isCompartmentVisualID(final String visualID) {
			return false;
		}

		/**
		 * {@inheritDoc
		 * 
		 * @see org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure#getVisualID(org.eclipse.gmf.runtime.notation.View)
		 */
		@Override
		public String getVisualID(final View view) {
			return view.getType();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure#getNodeVisualID(org.eclipse.gmf.runtime.notation.View, org.eclipse.emf.ecore.EObject)
		 */
		@Override
		public String getNodeVisualID(final View containerView, final EObject domainElement) {
			return null;
		}

		/**
		 * 
		 * @see org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure#getModelID(org.eclipse.gmf.runtime.notation.View)
		 */
		@Override
		public String getModelID(final View view) {
			return EXPECTED_MODEL_ID;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure#checkNodeVisualID(org.eclipse.gmf.runtime.notation.View, org.eclipse.emf.ecore.EObject, java.lang.String)
		 */
		@Override
		public boolean checkNodeVisualID(final View containerView, final EObject domainElement, final String candidate) {
			return false;
		}
	}


}
