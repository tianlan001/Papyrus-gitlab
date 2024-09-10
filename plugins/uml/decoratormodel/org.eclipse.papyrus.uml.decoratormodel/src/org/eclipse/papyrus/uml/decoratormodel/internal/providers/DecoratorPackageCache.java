/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.providers;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.decoratormodel.profileExternalization.ApplyProfiles;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;

/**
 * A cache of decorator-package relationships that lets us continue to infer the relationship between a decorator package and the user-model package
 * to which it applies profiles <em>after</em> the decorator model has been unloaded. This is important for correct reasoning on EMF {@link Notification}s
 * broadcast in a post-transaction-commit batch after a decorator model was unloaded.
 */
public class DecoratorPackageCache extends AdapterImpl {
	private static final Map<Package, Package> DECORATOR_TO_USER_PACKAGE_CACHE = new MapMaker().weakKeys().weakValues().concurrencyLevel(4).makeMap();

	private final Resource resource;

	public DecoratorPackageCache(Resource resource) {
		super();

		this.resource = resource;
	}

	public void install() {
		resource.eAdapters().add(this);
	}

	public static Package getUserModelPackage(Package decoratorPackage) {
		return DECORATOR_TO_USER_PACKAGE_CACHE.get(decoratorPackage);
	}

	public static void aboutToUnload(Resource resource) {
		DecoratorPackageCache cache = getCache(resource);
		if (cache != null) {
			cache.populate();
		}
	}

	static void cache(Package decorator, Package userModel) {
		DECORATOR_TO_USER_PACKAGE_CACHE.put(decorator, userModel);
	}

	static DecoratorPackageCache getCache(Resource resource) {
		return Iterables.getFirst(Iterables.filter(resource.eAdapters(), DecoratorPackageCache.class), null);
	}

	private static void cache(ApplyProfiles applyProfiles) {
		Dependency dependency = applyProfiles.getBase_Dependency();
		if (dependency != null) {
			Package decorator = getPackage(dependency.getSuppliers());
			Package userModel = getPackage(dependency.getClients());
			if ((decorator != null) && (userModel != null)) {
				cache(decorator, userModel);
			}
		}
	}

	static Package getPackage(Collection<?> elements) {
		return (Package) EcoreUtil.getObjectByType(elements, UMLPackage.Literals.PACKAGE);
	}

	@Override
	public void notifyChanged(Notification msg) {
		if (msg.getFeatureID(Resource.class) == Resource.RESOURCE__CONTENTS) {
			switch (msg.getEventType()) {
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				if (!resource.isLoaded()) {
					// This is the clearing of the contents during unload
					Object old = msg.getOldValue();
					Collection<?> removed = (old instanceof Collection<?>) ? (Collection<?>) old : Collections.singletonList(old);
					for (ApplyProfiles next : Iterables.filter(removed, ApplyProfiles.class)) {
						cache(next);
					}
				}
			}
		}
	}

	void populate() {
		for (ApplyProfiles next : Iterables.filter(resource.getContents(), ApplyProfiles.class)) {
			cache(next);
		}
	}
}
