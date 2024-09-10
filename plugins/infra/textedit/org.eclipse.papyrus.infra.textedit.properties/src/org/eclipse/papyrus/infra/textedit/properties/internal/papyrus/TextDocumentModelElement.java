/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.properties.internal.papyrus;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.swt.graphics.Image;

/**
 * A specific model element used to manipulate customized feature of TextDocument
 */
public class TextDocumentModelElement extends EMFModelElement {

	/**
	 * The kindId property name
	 */
	private static final String KIND_ID_PROPERTY = "kindId"; //$NON-NLS-1$

	/**
	 * The custom kindId used in the ctx file
	 */
	private static final String CUSTOM_KIND_ID_PROPERTY = "customKindId"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param source
	 * @param domain
	 */
	public TextDocumentModelElement(TextDocument source, EditingDomain domain) {
		super(source, domain);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getSource()
	 *
	 * @return
	 */
	@Override
	public TextDocument getSource() {
		return (TextDocument) super.getSource();
	}

	/**
	 * Constructor.
	 *
	 * @param source
	 */
	public TextDocumentModelElement(EObject source) {
		super(source);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#doGetObservable(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	protected IObservable doGetObservable(String propertyPath) {
		if (CUSTOM_KIND_ID_PROPERTY.equals(propertyPath)) {
			return super.doGetObservable(KIND_ID_PROPERTY);
		}
		return super.doGetObservable(propertyPath);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		if (CUSTOM_KIND_ID_PROPERTY.equals(propertyPath)) {
			return new TextDocumentKindIdLabelProvider(getSource());
		}
		return super.getLabelProvider(propertyPath);
	}

	/**
	 * This label provider is used to display the TextDocument kindId
	 */
	private class TextDocumentKindIdLabelProvider implements ILabelProvider {

		/**
		 * the current TextDocument
		 */
		private TextDocument txtDocument;

		public TextDocumentKindIdLabelProvider(final TextDocument txtDocument) {
			this.txtDocument = txtDocument;
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 *
		 * @param listener
		 */
		@Override
		public void addListener(ILabelProviderListener listener) {
			// nothing to do
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 *
		 */
		@Override
		public void dispose() {
			this.txtDocument = null;
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
		 *
		 * @param element
		 * @param property
		 * @return
		 */
		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		/**
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 *
		 * @param listener
		 */
		@Override
		public void removeListener(ILabelProviderListener listener) {
			// nothing to do
		}

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 *
		 * @param element
		 * @return
		 */
		@Override
		public Image getImage(Object element) {
			// to get the same behavior than for GMF Diagram

			final PolicyChecker checker = PolicyChecker.getFor(this.txtDocument);
			final ViewPrototype prototype = ViewPrototype.get(this.txtDocument);
			// If this is not an unavailable view prototype and is not in current viewpoints, display the grayed icon if possible
			if (!ViewPrototype.UNAVAILABLE_VIEW.equals(prototype) && !checker.isInViewpoint(prototype.getRepresentationKind())) {
				// If the grayed icon is not set, use the unavailable view prototype icon
				return null == prototype.getGrayedIconURI() || prototype.getGrayedIconURI().isEmpty() ? ViewPrototype.UNAVAILABLE_VIEW.getIcon() : prototype.getGrayedIcon();
			}
			return prototype.getIcon();
		}

		/**
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 *
		 * @param element
		 * @return
		 */
		@Override
		public String getText(Object element) {
			// to get the same behavior than for GMF Diagram
			ViewPrototype prototype = ViewPrototype.get(this.txtDocument);
			if (prototype != null) {
				return prototype.getQualifiedName();
			}
			return ""; //$NON-NLS-1$
		}
	}

}
