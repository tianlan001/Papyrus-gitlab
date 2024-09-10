/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.custom.helper.MultiAssociationHelper;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.AssociationFigure;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Property;

/**
 * this a abstract editpart use to add listeners
 */
public abstract class AbstractAssociationBranchEditPart extends ConnectionEditPart {

	/**
	 *
	 */
	private static final String ASSOCIATION_END_LISTENERS_TARGET = "AssociationEndListenersTarget"; //$NON-NLS-1$

	public AbstractAssociationBranchEditPart(View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void activate() {
		super.activate();
		addAssociationEndListeners();
	}

	/**
	 *
	 * add listener
	 */
	protected void addAssociationEndListeners() {
		if (resolveSemanticElement() instanceof Association) {
			Property targetEnd = MultiAssociationHelper.getPropertyToListen(((Edge) getModel()), (Association) resolveSemanticElement());
			if (targetEnd != null) {
				addListenerFilter(ASSOCIATION_END_LISTENERS_TARGET, this, targetEnd);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deactivate() {
		removeAssociationEndListeners();
		super.deactivate();
	}

	/**
	 * @generated
	 */
	@Override
	public abstract AssociationFigure getPrimaryShape();

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);
		// set the good ends for the association figure
		if (((View) getModel()).isSetElement()) {
			refreshVisuals();
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	protected void refreshVisuals() {
		if (resolveSemanticElement() != null) {
			if (resolveSemanticElement() instanceof Association) {
				Property target = MultiAssociationHelper.getPropertyToListen(((Edge) getModel()), (Association) resolveSemanticElement());
				if (target != null && target.getOwner() != null) {
					int sourceType = 0;
					int targetType = 0;
					// aggregation?
					if (target.getAggregation() == AggregationKind.SHARED_LITERAL) {
						targetType += AssociationFigure.aggregation;
					}
					// composite?
					if (target.getAggregation() == AggregationKind.COMPOSITE_LITERAL) {
						targetType += AssociationFigure.composition;
					}
					// navigable?
					if (target.isNavigable()) {
						targetType += AssociationFigure.navigable;
					}
					getPrimaryShape().setEnd(sourceType, targetType);
				}
			}
		}
		super.refreshVisuals();
	}

	/**
	 * used to remove listeners at the end
	 */
	protected void removeAssociationEndListeners() {
		removeListenerFilter(ASSOCIATION_END_LISTENERS_TARGET);
	}
}
