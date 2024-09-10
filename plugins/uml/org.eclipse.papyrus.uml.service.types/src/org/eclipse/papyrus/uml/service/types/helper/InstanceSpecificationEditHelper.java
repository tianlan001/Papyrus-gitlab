/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.service.types.utils.RequestParameterConstants;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This helper is used to set the source and the target for a {@link Dependency}
 */
public class InstanceSpecificationEditHelper extends ElementEditHelper {

	public static final String INSTANCE_END = "InstanceEnd"; //$NON-NLS-1$


	/**
	 * This method provides the source type provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target role
	 */
	private InstanceSpecification getSourceOwnerType(ConfigureRequest req) {
		InstanceSpecification result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.SOURCE);
		if (paramObject instanceof InstanceSpecification) {
			result = (InstanceSpecification) paramObject;
		}

		return result;
	}

	/**
	 * This method provides the target type provided as {@link ConfigureRequest} parameter.
	 * 
	 * @return the target role
	 */
	private InstanceSpecification getTargetOwnerType(ConfigureRequest req) {
		InstanceSpecification result = null;
		Object paramObject = req.getParameter(CreateRelationshipRequest.TARGET);
		if (paramObject instanceof InstanceSpecification) {
			result = (InstanceSpecification) paramObject;
		}

		return result;
	}

	/**
	 * Test if the relationship creation is allowed.
	 * 
	 * @param source
	 *            the relationship source can be null
	 * @param target
	 *            the relationship target can be null
	 * @return true if the creation is allowed
	 */
	protected boolean canCreate(EObject source, EObject target) {

		if ((source != null) && !(source instanceof InstanceSpecification)) {
			return false;
		}

		if ((target != null) && !(target instanceof InstanceSpecification)) {
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest req) {

		EObject source = req.getSource();
		EObject target = req.getTarget();

		boolean noSourceOrTarget = (source == null || target == null);
		boolean noSourceAndTarget = (source == null && target == null);

		if (!noSourceAndTarget && !canCreate(source, target)) {
			// Abort creation.
			return UnexecutableCommand.INSTANCE;
		}

		if (noSourceOrTarget && !noSourceAndTarget) {
			// The request isn't complete yet. Return the identity command so
			// that the create relationship gesture is enabled.
			return IdentityCommand.INSTANCE;
		}

		// Propose a container if none is set in request.
		// EObject proposedContainer = EMFCoreUtil.getLeastCommonContainer(Arrays.asList(new EObject[]{ source, target }), UMLPackage.eINSTANCE.getPackage());
		View sourceView = (View) req.getParameter(RequestParameterConstants.EDGE_CREATE_REQUEST_SOURCE_VIEW);
		if (sourceView != null) {
			Diagram diagram = sourceView.getDiagram();
			EObject proposedContainer = EMFCoreUtil.getContainer(diagram.getElement(), UMLPackage.Literals.PACKAGE);
			req.setContainer(proposedContainer);
		}
		return new CreateRelationshipCommand(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getConfigureCommand(final ConfigureRequest req) {

		// // All Association configure are managed by HelperAdvice(s).
		// if ((getSourceOwnerType(req) == null) || (getTargetOwnerType(req) == null)) {
		// return UnexecutableCommand.INSTANCE;
		// }

		return super.getConfigureCommand(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getDestroyDependentsCommand(DestroyDependentsRequest req) {
		EObject elementToDestroy = req.getElementToDestroy();
		EObject container = elementToDestroy.eContainer();
		List<Slot> dependentSlots = new ArrayList<Slot>();
		if (elementToDestroy instanceof InstanceSpecification) {
			dependentSlots.addAll(collectDependentSlots((InstanceSpecification) elementToDestroy));
		}
		if (container != null && container instanceof InstanceSpecification) {
			dependentSlots.addAll(collectDependentSlots((InstanceSpecification) container));
		}
		return dependentSlots.isEmpty() ? super.getDestroyDependentsCommand(req) : req.getDestroyDependentsCommand(dependentSlots);
	}

	private List<Slot> collectDependentSlots(InstanceSpecification instanceSpecification) {
		if (instanceSpecification == null) {
			return Collections.emptyList();
		}
		List<InstanceSpecification> ends = getEnds(instanceSpecification);
		if (ends.size() != 2) {
			return Collections.emptyList();
		}
		InstanceSpecification source = ends.get(0);
		InstanceSpecification target = ends.get(1);
		EList<Classifier> classifiers = instanceSpecification.getClassifiers();
		if (classifiers == null || classifiers.isEmpty()) {
			return Collections.emptyList();
		}
		List<Slot> result = new ArrayList<Slot>();
		Iterator<Classifier> associations = classifiers.iterator();
		while (associations.hasNext()) {
			Classifier nextAssociation = associations.next();
			if (false == nextAssociation instanceof Association) {
				continue;
			}
			Iterator<Property> proIterator = ((Association) nextAssociation).getMemberEnds().iterator();
			while (proIterator.hasNext()) {
				Property nextAssociationEnd = proIterator.next();
				Slot sourceSlot = findAssociatedSlot(nextAssociationEnd, source);
				if (sourceSlot != null) {
					result.add(sourceSlot);
				}
				Slot targetSlot = findAssociatedSlot(nextAssociationEnd, target);
				if (targetSlot != null) {
					result.add(targetSlot);
				}
			}
		}
		return result;
	}

	private Slot findAssociatedSlot(Property associationEnd, InstanceSpecification instanceSpecification) {
		for (Slot nextSlot : instanceSpecification.getSlots()) {
			if (associationEnd == nextSlot.getDefiningFeature()) {
				return nextSlot;
			}
		}
		return null;
	}

	/**
	 *
	 * @param instance
	 *            link where instance end end are look for
	 * @return a list of two elements that are instance specfication : ends of this instance Link
	 *         if this is not an instance link : the size of the array list is 0
	 */
	private List<InstanceSpecification> getEnds(InstanceSpecification instance) {
		List<InstanceSpecification> array = new ArrayList<InstanceSpecification>();
		EAnnotation endtypes = instance.getEAnnotation(INSTANCE_END);
		if (endtypes != null) {
			assert (endtypes.getReferences().size() == 2);
			array.add((InstanceSpecification) endtypes.getReferences().get(0));
			array.add((InstanceSpecification) endtypes.getReferences().get(1));
		}
		return array;
	}

}
