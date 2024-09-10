/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.filters.provider.FilteredElementItemProvider;
import org.eclipse.papyrus.infra.filters.provider.FiltersItemProviderAdapterFactory;

/**
 * @since 3.1
 */
public class FilteredElementUtil {
	public static void collectNewFilters(Collection<Object> newChildDescriptors, Object context) {

		// IChildCreationExtender is not supported when inheriting from another Ecore Metamodel
		// We need to delegate to the FilteredElementItemProvider
		// However, this will cause duplicates for known (non-extended) new child descriptors, such as CompoundFilter and Equals
		// So the second step is to filter to merge only non-duplicates new childs

		// Delegate to FilteredElementItemProvider to get the extended new child
		AdapterFactory delegateAF = new FiltersItemProviderAdapterFactory();
		FilteredElementItemProvider delegate = new FilteredElementItemProvider(delegateAF);
		Collection<?> delegateNewChild = delegate.getNewChildDescriptors(context, EMFHelper.resolveEditingDomain(context), null);

		// Identify EClass for each known new child descriptor
		Set<EClass> knownTypes = newChildDescriptors.stream()
				.filter(CommandParameter.class::isInstance)
				.map(CommandParameter.class::cast)
				.map(CommandParameter::getEValue)
				.filter(v -> v != null)
				.map(EObject::eClass)
				.collect(Collectors.toSet());

		// Filter known new child descriptors to avoid duplicates, based on the EClass
		delegateNewChild.stream()
				.filter(CommandParameter.class::isInstance)
				.map(CommandParameter.class::cast)
				.filter(cp -> !knownTypes.contains(cp.getEValue().eClass()))
				.forEach(newChildDescriptors::add);

		delegate.dispose();

	}
}
