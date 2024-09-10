/*****************************************************************************
 * Copyright (c) 2011, 2017, 2019 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 454891
 *  Christian W. Damus - bugs 485220, 515459
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550568
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.GMFObservableList;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.GMFObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.gmfdiag.properties.Activator;
import org.eclipse.papyrus.infra.gmfdiag.properties.databinding.DiagramLabelObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.properties.databinding.GradientObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.properties.provider.ModelContentProvider;
import org.eclipse.papyrus.infra.gmfdiag.properties.util.LegacyOwnerObservable;
import org.eclipse.papyrus.infra.gmfdiag.style.StylePackage;
import org.eclipse.papyrus.infra.internationalization.utils.utils.InternationalizationConstants;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.infra.widgets.providers.EmptyContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A ModelElement to represent an element of the GMF Notation metamodel
 *
 * @author Camille Letavernier
 *
 */
public class GMFModelElement extends EMFModelElement {

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 *            The source element (from the Notation metamodel)
	 */
	public GMFModelElement(EObject source) {
		super(source);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 *            The source element (from the Notation metamodel)
	 * @param domain
	 *            The editing domain on which the commands will be executed.
	 *            May be null
	 */
	public GMFModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	@Override
	protected boolean isFeatureEditable(String propertyPath) {
		boolean result = true;
		if (InternationalizationConstants.LABEL_PROPERTY_PATH.equals(propertyPath)) {
			result = true;
		} else if (propertyPath.endsWith("owner")) { //$NON-NLS-1$
			result = true;
		} else {
			result = super.isFeatureEditable(propertyPath);
		}
		return result;
	}

	@Override
	protected IObservable doGetObservable(String propertyPath) {
		if (InternationalizationConstants.LABEL_PROPERTY_PATH.equals(propertyPath)) {
			Diagram diagram = (Diagram) source;
			return new DiagramLabelObservableValue(diagram, getDomain());
		} else if (propertyPath.endsWith("owner")) {
			Diagram diagram = (Diagram) source;
			Style style = diagram.getStyle(StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE);
			if (style != null) {
				return new GMFObservableValue(style, StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE__OWNER, domain);
			}
			return new LegacyOwnerObservable(diagram, StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE__OWNER, domain);
		} else if (propertyPath.endsWith("prototype")) {
			Diagram diagram = (Diagram) source;
			Style style = diagram.getStyle(StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE);
			if (style != null) {
				return new GMFObservableValue(style, StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE__DIAGRAM_KIND_ID, domain);
			}
			return new LegacyOwnerObservable(diagram, StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE__DIAGRAM_KIND_ID, domain);
		}

		FeaturePath featurePath = getFeaturePath(propertyPath);
		EStructuralFeature feature = getFeature(propertyPath);

		if (feature == null) {
			return null;
		}

		if (feature.getEType() == NotationPackage.eINSTANCE.getGradientData()) {
			return new GradientObservableValue(source, feature, domain);
		}

		if (feature.getUpperBound() != 1) {
			IObservableList list = domain == null ? EMFProperties.list(featurePath).observe(source) : new GMFObservableList(EMFProperties.list(featurePath).observe(source), domain, getSource(featurePath), feature);
			return list;
		}

		IObservableValue value = domain == null ? EMFProperties.value(featurePath).observe(source) : new GMFObservableValue(getSource(featurePath), feature, domain);
		return value;
	}

	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		if (propertyPath.endsWith("prototype")) {
			return new ILabelProvider() {

				@Override
				public void addListener(ILabelProviderListener listener) {
				}

				@Override
				public void removeListener(ILabelProviderListener listener) {
				}

				@Override
				public void dispose() {
				}

				@Override
				public boolean isLabelProperty(Object element, String property) {
					return false;
				}

				@Override
				public Image getImage(Object element) {
					PolicyChecker checker = PolicyChecker.getFor(source);
					ViewPrototype prototype = DiagramUtils.getPrototype((Diagram) source, checker, false);

					// If this is not an unavailable view prototype and is not in current viewpoints, display the grayed icon if possible
					if (!ViewPrototype.UNAVAILABLE_VIEW.equals(prototype) && !checker.isInViewpoint(prototype.getRepresentationKind())) {
						// If the grayed icon is not set, use the unavailable view prototype icon
						return null == prototype.getGrayedIconURI() || prototype.getGrayedIconURI().isEmpty() ? ViewPrototype.UNAVAILABLE_VIEW.getIcon() : prototype.getGrayedIcon();
					}

					return prototype.getIcon();
				}

				@Override
				public String getText(Object element) {
					ViewPrototype proto = DiagramUtils.getPrototype((Diagram) source, false);
					return proto.getQualifiedName();
				}
			};
		}

		ILabelProvider result = null;
		try {
			// If the object is deleted, then there is no label-provider service for it
			ResourceSet rset = EMFHelper.getResourceSet(source);
			if (rset != null) {
				result = ServiceUtilsForResourceSet.getInstance().getService(LabelProviderService.class, rset).getLabelProvider();
			}
		} catch (ServiceException ex) {
			Activator.log.error(ex);
		}

		if (result == null) {
			result = new LabelProvider();
		}

		return result;
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 */
	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		if ("element".equals(propertyPath)) {
			if (source instanceof Diagram) {
				Diagram diagram = (Diagram) source;
				return new ModelContentProvider(diagram, getRoot(diagram.getElement())) {

					@Override
					protected boolean isValid(EObject selection, Diagram diagram, ViewPrototype prototype) {
						return PolicyChecker.getFor(diagram).canHaveNewView(selection, DiagramUtils.getOwner(diagram), prototype);
					}
				};
			} else {
				return EmptyContentProvider.instance;
			}
		} else if ("owner".equals(propertyPath)) {
			Diagram diagram = (Diagram) source;
			return new ModelContentProvider(diagram, getRoot(diagram.getElement())) {

				@Override
				protected boolean isValid(EObject selection, Diagram diagram, ViewPrototype prototype) {
					return (PolicyChecker.getFor(diagram).getOwningRuleFor(prototype, selection) != null);
				}
			};
		}
		return super.getContentProvider(propertyPath);
	}

	/**
	 * Gets the root EObject from the given one
	 *
	 * @param obj
	 *            An object
	 * @return The root object which is an ancestor of the given one
	 */
	private EObject getRoot(EObject obj) {
		EObject current = obj;

		// Check if parameter is not null
		if (obj != null) {
			EObject parent = obj.eContainer();
			while (parent != null) {
				current = parent;
				parent = parent.eContainer();
			}
		}
		return current;
	}
}
