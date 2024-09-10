/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 487496
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.nattable.manager.axis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.Window;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.axis.ITreeItemAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.nattable.tree.ITreeItemAxisHelper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

//import org.eclipse.nebula.widgets.nattable.ui.NatEventData;

/**
 *
 * @author Vincent Lorenzo
 *         Class used to managed hierarchical axis
 */
public class EObjectTreeAxisManagerForEventList extends AbstractTreeAxisManagerForEventList implements IAxisManagerForEventList, ITreeItemAxisManagerForEventList {


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getAddAxisCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.util.Collection)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @return
	 */
	@Override
	public Command getAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd) {
		final Collection<IAxis> toAdd = getAxisToAdd(objectToAdd);
		if (!toAdd.isEmpty()) {
			return new AddCommandWrapper(AddCommand.create(domain, getRepresentedContentProvider(), NattableaxisproviderPackage.eINSTANCE.getAxisProvider_Axis(), toAdd), objectToAdd);
		}
		return null;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#getAddAxisCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.util.Collection, int)
	 *
	 * @param domain
	 * @param objectToAdd
	 * @param index
	 * @return
	 */
	@Override
	public Command getAddAxisCommand(final TransactionalEditingDomain domain, final Collection<Object> objectToAdd, final int index) {
		final Collection<IAxis> toAdd = getAxisToAdd(objectToAdd);
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
	protected Collection<IAxis> getAxisToAdd(final Collection<Object> objectToAdd) {
		final Collection<IAxis> toAdd = new ArrayList<IAxis>();
		for (final Object object : objectToAdd) {
			if (isAllowedContents(object, null, null, 0) && !isAlreadyManaged(object)) {
				final EObjectAxis horizontalAxis = NattableaxisFactory.eINSTANCE.createEObjectTreeItemAxis();
				horizontalAxis.setElement((EObject) object);
				horizontalAxis.setManager(this.representedAxisManager);
				toAdd.add(horizontalAxis);
			}
		}
		return toAdd;
	}


	/**
	 * @param objectToTest
	 * @param depth
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.ITreeItemAxisManagerForEventList#isAllowedContents(java.lang.Object, Object, TreeFillingConfiguration, int)
	 *
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object objectToTest, Object semanticParent, TreeFillingConfiguration conf, int depth) {
		boolean result = false;
		if (objectToTest instanceof EObject) {
			if (null != conf && null != conf.getFilterRule() && conf.getFilterRule() instanceof IBooleanEObjectExpression) {
				return ((IBooleanEObjectExpression) conf.getFilterRule()).evaluate((EObject) objectToTest).booleanValue();
			}
			result = true;
		}
		return result;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#isAllowedContents(java.lang.Object)
	 *
	 * @param object
	 * @return
	 * @deprecated
	 */
	@Override
	public boolean isAllowedContents(Object object) {
		return object instanceof EObject;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#createITreeItemAxis(org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis, java.lang.Object)
	 *
	 * @param parentAxis
	 * @param objectToAdd
	 * @return
	 */
	protected ITreeItemAxis createITreeItemAxis(ITreeItemAxis parentAxis, Object objectToAdd) {
		return ITreeItemAxisHelper.createITreeItemAxis(getTableEditingDomain(), parentAxis, objectToAdd, this.representedAxisManager);
	}


	/**
	 * 
	 * @param notification
	 *            a notification
	 * @return
	 * 		<code>true</code> if the notification must be ignored
	 */
	protected boolean ignoreEvent(final Notification notification) {
		boolean res = super.ignoreEvent(notification);
		if (res) {
			return res;
		}
		Object notifier = notification.getNotifier();
		Object feature = notification.getFeature();
		if (feature == null) {
			return true;
		}
		if (notifier instanceof PageRef || notifier instanceof Window) {
			return true;
		}


		// I am not sure of the end of this method
		// List<EStructuralFeature> listenFeature = new ArrayList<EStructuralFeature>();
		// List<TreeFillingConfiguration> confs = FillingConfigurationUtils.getTreeFillingConfiguration(getTable(), representedAxisManager);
		// boolean derivedFeature = false;
		// for (TreeFillingConfiguration conf : confs) {
		// IAxis axis = conf.getAxisUsedAsAxisProvider();
		// if (axis instanceof EStructuralFeatureAxis) {
		// EStructuralFeature f = (EStructuralFeature) axis.getElement();
		// derivedFeature = derivedFeature || f.isDerived();
		// if (derivedFeature) {
		// return false;
		// }
		// }
		// }
		// if (listenFeature.contains(feature)) {
		// return false;
		// }
		//
		// if (confs.size() == listenFeature.size()) {
		// return false;
		// }
		// return true;
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#canDestroyAxisElement(java.lang.Integer)
	 */
	@Override
	public boolean canDestroyAxisElement(final Integer axisIndex) {
		final Object current = getElements().get(axisIndex);
		if (current instanceof EObjectTreeItemAxis && !(((EObjectTreeItemAxis) current).getElement() instanceof TreeFillingConfiguration)) {
			return ((EObjectTreeItemAxis) current).getElement() != null // can be null for empty axis
                          	&& !EMFHelper.isReadOnly(((EObjectTreeItemAxis) current).getElement());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#getDestroyAxisElementCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Integer)
	 */
	@Override
	public Command getDestroyAxisElementCommand(final TransactionalEditingDomain domain, final Integer axisPosition) {
		final Object current = getElements().get(axisPosition);
		if (current instanceof EObjectTreeItemAxis) {
			final EObject element = ((EObjectTreeItemAxis) current).getElement();
			final DestroyElementRequest request = new DestroyElementRequest(getContextEditingDomain(), element, false);
			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(element);
			return new RemoveCommandWrapper(new GMFtoEMFCommandWrapper(provider.getEditCommand(request)), Collections.singleton((Object) ((EObjectTreeItemAxis) current).getElement()));
		}
		return null;
	}
}
