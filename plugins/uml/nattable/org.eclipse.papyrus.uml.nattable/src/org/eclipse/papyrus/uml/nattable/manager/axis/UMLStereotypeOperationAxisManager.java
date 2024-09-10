/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.manager.axis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.manager.axis.IIdAxisManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.OperationIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.nattable.provider.UMLStereotypeRestrictedOperationContentProvider;
import org.eclipse.papyrus.uml.nattable.utils.StereotypeOperationUtils;
import org.eclipse.papyrus.uml.tools.util.StereotypeUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Axis Manager for operations of stereotypes
 *
 * @since 5.4
 */
public class UMLStereotypeOperationAxisManager extends UMLOperationAxisManager implements IIdAxisManager {

	/**
	 *
	 * @see org.eclipse.papyrus.uml.nattable.manager.axis.UMLFeatureAxisManager#isAllowedContents(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object object) {
		boolean result = false;
		if (object instanceof Operation) {
			final Operation operation = (Operation) object;
			Element owner = operation.getOwner();
			if (owner instanceof Stereotype) {
				while (owner.getOwner() instanceof Package && !result) {
					owner = owner.getOwner();
					result = owner instanceof Profile;
				}
				if (result) {
					result = EMFHelper.isReadOnly(operation);
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getComplementaryAddAxisCommand(TransactionalEditingDomain, java.util.Collection)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @return
	 */
	@Override
	public Command getComplementaryAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd) {
		final Set<Object> operationsToAdd = getOperationToAdd(objectToAdd);
		if (!operationsToAdd.isEmpty()) {
			return getAddAxisCommand(domain, operationsToAdd);
		}
		return null;
	}

	/**
	 * Get the properties to add.
	 *
	 * @param objectToAdd
	 *            The initial objects to add.
	 * @return The properties to add.
	 */
	protected Set<Object> getOperationToAdd(final Collection<Object> objectToAdd) {
		final Set<Stereotype> appliedStereotypes = new HashSet<>();
		for (final Object current : objectToAdd) {
			if (current instanceof Element) {
				appliedStereotypes.addAll(((Element) current).getAppliedStereotypes());
			}
		}
		final Set<Object> operationToAdd = new HashSet<>();

		for (final Stereotype stereotype : appliedStereotypes) {
			operationToAdd.addAll(StereotypeUtil.getAllStereotypeOperations(stereotype, true, true, true));

		}
		return operationToAdd;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getAddAxisCommand(TransactionalEditingDomain, java.util.Collection)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @return
	 */
	@Override
	public Command getAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd) {
		Collection<IAxis> toAdd = getAxisToAdd(objectToAdd);
		if (!toAdd.isEmpty()) {
			return new AddCommandWrapper(AddCommand.create(domain, getRepresentedContentProvider(), NattableaxisproviderPackage.eINSTANCE.getAxisProvider_Axis(), toAdd), objectToAdd);
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.EStructuralFeatureAxisManager#getAddAxisCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.util.Collection, int)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @param index
	 * @return
	 */
	@Override
	public Command getAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd, final int index) {
		Collection<IAxis> toAdd = getAxisToAdd(objectToAdd);
		if (!toAdd.isEmpty()) {
			return new AddCommandWrapper(AddCommand.create(domain, getRepresentedContentProvider(), NattableaxisproviderPackage.eINSTANCE.getAxisProvider_Axis(), toAdd, index), objectToAdd);
		}
		return null;
	}

	/**
	 * Get the axis to add from the objects to add.
	 *
	 * @param objectToAdd
	 *            The objects to add.
	 * @return The axis to add.
	 */
	@Override
	protected Collection<IAxis> getAxisToAdd(final Collection<Object> objectToAdd) {
		final Collection<IAxis> toAdd = new ArrayList<>();
		final List<String> allOperationQN = new ArrayList<>();
		for (Object object : objectToAdd) {
			if (isAllowedContents(object)) {
				allOperationQN.add(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX + ((NamedElement) object).getQualifiedName());
			}
		}
		allOperationQN.removeAll(getElements());
		if (!allOperationQN.isEmpty()) {
			for (String operationQN : allOperationQN) {
				final IdAxis newAxis = NattableaxisFactory.eINSTANCE.createOperationIdAxis();
				newAxis.setElement(operationQN);
				newAxis.setManager(this.representedAxisManager);
				toAdd.add(newAxis);
			}
		}
		return toAdd;
	}


	/**
	 * @see org.eclipse.papyrus.uml.nattable.manager.axis.UMLOperationAxisManager#createPossibleAxisContentProvider(boolean)
	 *
	 * @param isRestricted
	 * @return
	 */
	@Override
	public IRestrictedContentProvider createPossibleAxisContentProvider(boolean isRestricted) {
		return new UMLStereotypeRestrictedOperationContentProvider(this, isRestricted);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager#getDestroyAxisCommand(TransactionalEditingDomain, java.util.Collection)
	 *
	 * @param domain
	 * @param umlProperties
	 *            the UML Property for which we want destroy axis
	 * @return
	 */
	@Override
	public Command getDestroyAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> umlProperties) {
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(getRepresentedContentProvider());
		final CompositeCommand compositeCommand = new CompositeCommand("Destroy IAxis Command"); //$NON-NLS-1$
		final List<String> propIdToDestroy = new ArrayList<>();
		for (final Object current : umlProperties) {
			if (current instanceof Operation && ((Operation) current).eContainer() instanceof Stereotype) {
				propIdToDestroy.add(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX + ((NamedElement) current).getQualifiedName());
			} else if (current instanceof String) {
				propIdToDestroy.add((String) current);
			} else if (current instanceof IAxis) {
				propIdToDestroy.add(((IAxis) current).getElement().toString());
			}
		}

		for (final IAxis current : getRepresentedContentProvider().getAxis()) {
			if (current instanceof IdAxis) {
				String propId = AxisUtils.getPropertyId(current);
				if (propIdToDestroy.contains(propId)) {
					DestroyElementRequest request = new DestroyElementRequest(domain, current, false);
					compositeCommand.add(provider.getEditCommand(request));
				}
			}
		}

		if (!compositeCommand.isEmpty()) {
			return new RemoveCommandWrapper(new GMFtoEMFCommandWrapper(compositeCommand), umlProperties);
		}
		return null;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.IIdAxisManager#resolvedPath(java.lang.String)
	 *
	 * @param path
	 * @return
	 */
	@Override
	public Object resolvedPath(final String path) {
		if (path.startsWith(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX)) {
			return StereotypeOperationUtils.getRealStereotypeOperation(getTableContext(), path);
		}
		return null;
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.EStructuralFeatureAxisManager#getElementAxisName(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis)
	 */
	@Override
	public String getElementAxisName(final IAxis axis) {
		String returnedValue = null; // $NON-NLS-1$
		if (axis instanceof OperationIdAxis) {
			String elementId = ((OperationIdAxis) axis).getElement();
			final Object resolvedElement = resolvedPath(elementId);
			if (resolvedElement instanceof NamedElement) {
				returnedValue = UMLLabelInternationalization.getInstance().getLabel((NamedElement) resolvedElement);
			} else {
				elementId = elementId.replace(StereotypeOperationUtils.OPERATION_OF_STEREOTYPE_PREFIX, ""); //$NON-NLS-1$
				final String[] splitedElementId = elementId.split("::"); //$NON-NLS-1$
				returnedValue = splitedElementId[splitedElementId.length - 1];
			}

		}
		return null != returnedValue ? returnedValue : super.getElementAxisName(axis);
	}


}
