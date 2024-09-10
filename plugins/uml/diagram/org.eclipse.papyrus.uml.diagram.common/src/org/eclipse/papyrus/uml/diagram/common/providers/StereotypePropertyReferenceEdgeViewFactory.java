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

import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.EDGE_LABEL_ANNOTATION_KEY;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.FEATURE_TO_SET_ANNOTATION_KEY;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT;
import static org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice.STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.core.view.factories.ViewFactory;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ReferenceEdgeNameEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.StereotypePropertyReferenceEdgeRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.UpdaterLinkDescriptor;
import org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper;
import org.eclipse.papyrus.uml.diagram.common.stereotype.IStereotypePropertyReferenceEdgeAdvice;

/**
 * {@link ViewFactory} for stereotype property reference edge.
 * 
 * @author Mickael ADAM
 * 
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeViewFactory implements ViewFactory {


	/**
	 * Constructor.
	 */
	public StereotypePropertyReferenceEdgeViewFactory() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.core.view.factories.ViewFactory#createView(org.eclipse.core.runtime.IAdaptable, org.eclipse.gmf.runtime.notation.View, java.lang.String, int, boolean, org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint)
	 */
	@Override
	public View createView(final IAdaptable semanticAdapter, final View containerView, final String semanticHint, final int index, final boolean persisted, final PreferencesHint preferencesHint) {
		Edge view = null;
		if (semanticHint.equals(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT)) {
			view = createStereotypePropertyReferenceEdge(semanticAdapter, containerView, index, persisted, preferencesHint);
		}
		return view;
	}

	/**
	 * Create the stereotype property reference edge.
	 */
	@SuppressWarnings("unchecked")
	public Edge createStereotypePropertyReferenceEdge(final IAdaptable semanticAdapter, final View containerView, final int index, final boolean persisted, final PreferencesHint preferencesHint) {
		IEditHelperAdvice[] advices = ElementTypeRegistry.getInstance().getEditHelperAdvice(semanticAdapter.getAdapter(IElementType.class));
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<RelativeBendpoint>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);

		// Reference link is not attached to a semantic element
		edge.setElement(null);

		Node referenceLink_NameLabel = createLabel(edge, ReferenceEdgeNameEditPart.VISUAL_ID);
		referenceLink_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location referenceLink_NameLabel_Location = (Location) referenceLink_NameLabel.getLayoutConstraint();
		referenceLink_NameLabel_Location.setX(0);
		referenceLink_NameLabel_Location.setY(20);

		StereotypePropertyReferenceEdgeRepresentation linkDescriptor = getStereotypePropertyReferenceEdgeRepresentation(advices, semanticAdapter);

		// set annotation
		EAnnotation eAnnotation = createAnnotation(linkDescriptor);
		if (null != eAnnotation) {
			edge.getEAnnotations().add(eAnnotation);
		}

		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, getStereotypePropertyReferenceEdgeId(linkDescriptor));
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, getStereotypePropertyReferenceEdgeId(linkDescriptor));

		return edge;
	}

	/**
	 * Create the annotation for the edge.
	 * 
	 * @param edgeDescriptor
	 *            The edge descriptor
	 * @return the created annotation.
	 */
	protected EAnnotation createAnnotation(final StereotypePropertyReferenceEdgeRepresentation edgeDescriptor) {
		EAnnotation eAnnotation = null;
		if (null != edgeDescriptor) {
			eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(STEREOTYPE_PROPERTY_REFERENCE_EDGE_HINT);
			eAnnotation.getDetails().put(FEATURE_TO_SET_ANNOTATION_KEY, edgeDescriptor.getFeatureToSet());
			eAnnotation.getDetails().put(STEREOTYPE_QUALIFIED_NAME_ANNOTATION_KEY, edgeDescriptor.getStereotypeQualifiedName());
			eAnnotation.getDetails().put(EDGE_LABEL_ANNOTATION_KEY, edgeDescriptor.getEdgeLabel());
		}
		return eAnnotation;
	}

	/**
	 * Get the stereotype property reference edge representation.
	 * 
	 * @param advices
	 *            The list of advices where to look
	 * @param semanticAdapter
	 *            The semantic adapter to look at if not
	 */
	protected StereotypePropertyReferenceEdgeRepresentation getStereotypePropertyReferenceEdgeRepresentation(final IEditHelperAdvice[] advices, final IAdaptable semanticAdapter) {
		StereotypePropertyReferenceEdgeRepresentation linkDescriptor = getStereotypePropertyReferenceEdgeRepresentation(advices);

		// If no advice to get information open dialog
		if (null == linkDescriptor) {
			UpdaterLinkDescriptor UpdaterLinkDescriptor = semanticAdapter.getAdapter(UpdaterLinkDescriptor.class);
			EObject modelElement = UpdaterLinkDescriptor.getModelElement();
			if (modelElement instanceof StereotypePropertyReferenceEdgeRepresentation) {
				linkDescriptor = (StereotypePropertyReferenceEdgeRepresentation) modelElement;
			}
		}
		return linkDescriptor;
	}

	/**
	 * Create A {@link DecorationNode} label.
	 * 
	 * @param owner
	 *            The owner of the label.
	 * @param hint
	 *            The hint of the label.
	 * @return the created label.
	 */
	protected Node createLabel(final View owner, final String hint) {
		DecorationNode rv = NotationFactory.eINSTANCE.createDecorationNode();
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		return rv;
	}

	/**
	 * Get the {@link StereotypePropertyReferenceEdgeRepresentation} from an array of of {@link IEditHelperAdvice}.
	 */
	protected StereotypePropertyReferenceEdgeRepresentation getStereotypePropertyReferenceEdgeRepresentation(final IEditHelperAdvice[] advices) {
		StereotypePropertyReferenceEdgeRepresentation descriptor = null;
		for (IEditHelperAdvice advice : advices) {
			if (advice instanceof IStereotypePropertyReferenceEdgeAdvice) {
				String stereotypeQualifyName = ((IStereotypePropertyReferenceEdgeAdvice) advice).getStereotypeQualifiedName();
				String featureToSet = ((IStereotypePropertyReferenceEdgeAdvice) advice).getFeatureToSet();
				String linkLabel = ((IStereotypePropertyReferenceEdgeAdvice) advice).getEdgeLabel();
				if (null != stereotypeQualifyName && null != featureToSet) {
					descriptor = new StereotypePropertyReferenceEdgeRepresentation(null, null, stereotypeQualifyName, featureToSet, linkLabel);
				}
			}
		}
		return descriptor;
	}

	/**
	 * Get stereotype property reference link Id from {@link StereotypePropertyReferenceEdgeRepresentation}.
	 */
	protected String getStereotypePropertyReferenceEdgeId(final StereotypePropertyReferenceEdgeRepresentation linkDescriptor) {
		return linkDescriptor.getStereotypeQualifiedName() + "::" + linkDescriptor.getFeatureToSet();//$NON-NLS-1$
	}
}
