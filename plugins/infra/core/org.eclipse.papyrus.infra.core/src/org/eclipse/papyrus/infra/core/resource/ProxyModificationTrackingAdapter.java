/*****************************************************************************
 * Copyright (c) 2013, 2014 Atos, CEA, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mathieu Velten (Atos) mathieu.velten@atos.net - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 432753
 *  Christian W. Damus (CEA) - bug 437052
 *  Calin Glitia (Esterel Technologies) calin.glitia@esterel-technologies.com - bug 480209
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 549816
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

/**
 * This adapter handles "modified" flag of resources for tricky cases :
 *
 * - when there is a change in an URI all the resources containing a proxy
 * to the modified resource should be marked as modified
 *
 * - when adding/removing objects from resources they should be marked as modified,
 * and all the resources containing a proxy too
 *
 * - when doing control/uncontrol operations the resource of the parent object
 * should be marked as modified
 *
 * @author mvelten
 *
 */
public class ProxyModificationTrackingAdapter extends EContentAdapter {

	/**
	 * Queries whether a {@code resource} tracked by me is either known to be or assumed to be needing to be saved.
	 * Generally this is true for resources that have been modified since the last save and that are saveable
	 * (not read-only and correctly and completely loaded in the first place).
	 *
	 * @param resource
	 *            a resource that I track
	 *
	 * @return whether the {@code resource} should be saved
	 */
	public boolean shouldSave(Resource resource) {
		return !resource.isTrackingModification() || resource.isModified();
	}

	@Override
	protected void setTarget(Resource target) {
		basicSetTarget(target);
	}

	@Override
	protected void unsetTarget(Resource target) {
		basicUnsetTarget(target);
	}

	@Override
	protected void setTarget(EObject target) {
	}

	@Override
	protected void unsetTarget(EObject target) {
	}

	@Override
	public void notifyChanged(Notification n) {
		Object notifier = n.getNotifier();

		if (notifier instanceof Resource.Internal) {
			Resource.Internal r = (Resource.Internal) notifier;

			if (n.getEventType() == Notification.SET && n.getFeatureID(Resource.class) == Resource.RESOURCE__URI) {
				r.setModified(true);
				setOtherResourcesAsModified(r);

			} else {
				List<?> objects;

				switch (n.getEventType()) {
				case Notification.ADD_MANY:
					objects = (List<?>) n.getNewValue();
					break;
				case Notification.REMOVE_MANY:
					objects = (List<?>) n.getOldValue();
					break;
				case Notification.ADD:
					objects = Collections.singletonList(n.getNewValue());
					break;
				case Notification.REMOVE:
					objects = Collections.singletonList(n.getOldValue());
					break;
				default:
					objects = Collections.emptyList();
					break;
				}

				if (r.isLoaded() && !r.isLoading()) {
					if (!objects.isEmpty()) {
						r.setModified(true);
						setOtherResourcesAsModified(r);
					}
				}
			}
		}

		super.notifyChanged(n);
	}

	protected void setReferencingResourcesAsModified(EObject eObj) {
		Collection<Setting> references = org.eclipse.papyrus.infra.core.utils.EMFHelper.getUsages(eObj);
		for (Setting setting : references) {
			EStructuralFeature f = setting.getEStructuralFeature();
			if (setting.getEObject() != null && !f.isDerived() && !f.isTransient()) {
				Resource.Internal refResource = (org.eclipse.emf.ecore.resource.Resource.Internal) setting.getEObject().eResource();
				if (refResource != null && !refResource.isLoading()) {
					refResource.setModified(true);
				}
			}
		}
	}

	/**
	 * This allows to mark other resources of resource set as modified if needed.
	 *
	 * @param resource
	 *            The current resource.
	 * @since 3.1
	 */
	protected void setOtherResourcesAsModified(final Resource resource) {
		// Get the resource set to loop through resources
		final ResourceSet resourceSet = resource.getResourceSet();
		EditingDomain domain = null;

		// Get the editing domain to determinate if a resource is read only
		if (resourceSet instanceof ModelSet) {
			domain = ((ModelSet) resourceSet).getTransactionalEditingDomain();
		} else if (resourceSet instanceof IEditingDomainProvider) {
			domain = ((IEditingDomainProvider) resourceSet).getEditingDomain();
		} else if (resourceSet != null) {
			IEditingDomainProvider editingDomainProvider = (IEditingDomainProvider) EcoreUtil.getExistingAdapter(resourceSet, IEditingDomainProvider.class);
			if (editingDomainProvider != null) {
				domain = editingDomainProvider.getEditingDomain();
			}
		}

		// Loop through resources to determinate if this is needed to mark it as modified
		for (final Resource subResource : resourceSet.getResources()) {
			if (subResource instanceof Resource.Internal && subResource.isLoaded() && !((Resource.Internal) subResource).isLoading()) {
				if (null == domain || !domain.isReadOnly(subResource)) {
					subResource.setModified(true);
				}
			}
		}
	}
}
