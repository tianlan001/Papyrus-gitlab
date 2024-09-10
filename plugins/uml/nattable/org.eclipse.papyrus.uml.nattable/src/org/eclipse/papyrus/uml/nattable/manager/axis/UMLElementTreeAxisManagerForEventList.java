/*****************************************************************************
 * Copyright (c) 2014, 2021 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 514620
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.manager.axis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.SpecializationType;
import org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectTreeAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.UMLPackage;

/**
 *
 * @author Vincent Lorenzo
 *         UML manager for Element in a Tree Table
 */
public class UMLElementTreeAxisManagerForEventList extends EObjectTreeAxisManagerForEventList {


	public UMLElementTreeAxisManagerForEventList() {
		super();
	}


	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectTreeAxisManagerForEventList#isAllowedContents(java.lang.Object, Object, TreeFillingConfiguration, int)
	 *
	 * @param objectToTest
	 * @param depth
	 * @return
	 */
	@Override
	public boolean isAllowedContents(Object objectToTest, Object semanticParent, TreeFillingConfiguration conf, int depth) {
		return objectToTest instanceof Element && super.isAllowedContents(objectToTest, semanticParent, conf, depth);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#canCreateAxisElement(java.lang.String)
	 *
	 * @param elementId
	 * @return
	 */
	@Override
	public boolean canCreateAxisElement(String elementId) {
		if (elementId == null) {
			return false;
		}

		final IElementType types = ElementTypeRegistry.getInstance().getType(elementId);
		if (types == null || (types instanceof SpecializationType && ((SpecializationType) types).getMetamodelType() == null)) {
			return false;
		}

		final EClass eClass = types.getEClass();
		if (eClass != null) {
			if (eClass.getEPackage() == UMLPackage.eINSTANCE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageSetNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageSetNotification(Notification notification) {
		if (isStereotypeApplicationNotification(notification)) {
			manageStereotypeApplication(notification);
			return;
		}
		super.manageSetNotification(notification);
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractTreeAxisManagerForEventList#manageUnsetNotification(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	protected void manageUnsetNotification(Notification notification) {
		if (isStereotypeApplicationNotification(notification)) {
			manageStereotypeApplication(notification);
			return;
		}
		super.manageUnsetNotification(notification);
	}




	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectTreeAxisManagerForEventList#ignoreEvent(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 * @return
	 */
	@Override
	protected boolean ignoreEvent(Notification notification) {
		int event = notification.getEventType();
		Object notifier = notification.getNotifier();
		switch (event) {
		case Notification.ADD:
		case Notification.REMOVE:
			// we ignore the addition and the remove of stereotype application to the resource
			return notifier instanceof Resource && isStereotypeApplicationNotification(notification);

		default:
			return super.ignoreEvent(notification);
		}
	}

	/**
	 * This method allows to add/remove elements from the table, according to values returned by {@link #getFilteredValueAsCollection(TreeFillingConfiguration, Object, int)},
	 * when the attribute base_metaclass_name is modified in a stereotype application
	 *
	 * @param notification
	 *            a notification which concern the feature base_ of a stereotype application
	 */
	protected void manageStereotypeApplication(final Notification notification) {
		Object feature = notification.getFeature();
		if (!(feature instanceof EReference)) {
			return;
		}
		EReference ref = (EReference) feature;
		if (ref.isContainment() || ref.isMany()) {
			return;
		}

		Object notifier = notification.getNotifier();
		if (!(notifier instanceof EObject)) {
			return;
		}




		Object newValue = notification.getNewValue();
		Object oldValue = notification.getOldValue();
		Element baseElement = null;
		if (oldValue instanceof Element) {
			baseElement = (Element) oldValue;
		} else if (newValue instanceof Element) {
			baseElement = (Element) newValue;
		}
		if (baseElement == null) {
			return;
		}

		// we assume that the stereotyped element is always displayed as child of its semantic parent
		Element owner = baseElement.getOwner();
		if (this.managedElements.containsKey(owner)) {
			Set<ITreeItemAxis> parentRepresentation = this.managedElements.get(owner);

			for (ITreeItemAxis curr : parentRepresentation) {
				manageStereotypeApplication(curr, baseElement);
			}
		}

		// in all cases we need to upate root elements
		if (FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(getTable(), 0)) {
			manageStereotypeApplication(null, baseElement);
		}
	}

	/**
	 *
	 * @param semanticParent
	 *            the {@link ITreeItemAxis} representing the semantic parent of the baseElement concerned by the stereotype application
	 * @param baseElement
	 */
	protected void manageStereotypeApplication(final ITreeItemAxis semanticParent, final Element baseElement) {
		Object context;
		List<TreeFillingConfiguration> fillingConf;
		int nextDepth = 0;
		if (semanticParent != null) {
			context = semanticParent.getElement();
			nextDepth = getSemanticDepth(semanticParent) + 1;
		} else {
			context = getTableContext();
		}
		fillingConf = FillingConfigurationUtils.getTreeFillingConfigurationForDepth(getTable(), representedAxisManager, nextDepth);

		for (TreeFillingConfiguration curr : fillingConf) {
			Collection<?> values = getFilteredValueAsCollection(curr, context, 0);
			ITreeItemAxis confRep = null;
			if (this.managedElements.containsKey(curr)) {
				Iterator<ITreeItemAxis> iter = this.managedElements.get(curr).iterator();
				while (iter.hasNext() && confRep == null) {
					final ITreeItemAxis axis = iter.next();
					if (axis.getParent() == semanticParent) {
						confRep = axis;
					}
				}
			}
			if (values.isEmpty()) {
				if (confRep != null) {
					ITreeItemAxis toDestroy = getITreeItemAxisRepresentingObject(confRep.getChildren(), baseElement);
					if (null != toDestroy) {
						removeObject(toDestroy);
					}
				}
			} else {
				if (values.contains(baseElement)) {
					// we create the conf rep only for depth==0 (<=> semanticParent==null) or when the semantic parent has already been expanded
					if (confRep == null && (nextDepth == 0 || alreadyExpanded.contains(semanticParent))) {
						confRep = addObject(null, curr);
					}

					if (nextDepth == 0 || alreadyExpanded.contains(confRep)) {
						ITreeItemAxis stereotypedElementRep = getITreeItemAxisRepresentingObject(confRep.getChildren(), baseElement);
						if (stereotypedElementRep == null) {
							ITreeItemAxis newAxis = addObject(confRep, baseElement);
							if (!managedElements.containsKey(baseElement) && (alreadyExpanded.contains(confRep) || confRep.getParent() == null)) {// already expanded or root!
								fillChildrenForSemanticElement(newAxis);
							}
						}
					}
				} else {
					ITreeItemAxis toDestroy = getITreeItemAxisRepresentingObject(confRep.getChildren(), baseElement);
					if (toDestroy != null) {
						removeObject(toDestroy);
					}
				}
			}
		}
	}

	/**
	 *
	 * @param notification
	 *            a notification
	 * @return
	 *         <code>true</code> if the notification concerns a stereotype application
	 */
	protected boolean isStereotypeApplicationNotification(final Notification notification) {
		Object notifier = notification.getNotifier();
		if (notifier instanceof EObject) {
			Object feature = notification.getFeature();
			if (feature instanceof EReference && !((EReference) feature).isContainment() && ((EReference) feature).getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
				return true;
			}
		}
		return false;
	}
}
