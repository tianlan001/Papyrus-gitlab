/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - bug 531645 - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.internal.infra.nattable.model.nattable.nattableaxisconfiguration.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.provider.BooleanExpressionsItemProviderAdapterFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.provider.NattableEditPlugin;

public class ExtendedBooleanEObjectExpressionChildCreationExtender implements IChildCreationExtender {

	/**
	 * A dummy expression used to be able to get all existing boolean eobjects expressions. The generated code doesn't allows to get the children extender of a referenced metamodel
	 */
	private IBooleanEObjectExpression dummyExpression = BooleanExpressionsFactory.eINSTANCE.createAndExpression();

	/**
	 * the boolean expression factory. It doesn't provide all expressions (I don't know why), but the expressions extending the initial framework, and it is that we want!!!
	 */
	private BooleanExpressionsItemProviderAdapterFactory expressionsExtendedChildrenFactory = new BooleanExpressionsItemProviderAdapterFactory();

	/**
	 * @see org.eclipse.papyrus.internal.infra.nattable.model.nattable.nattableaxisconfiguration.provider.NattableaxisconfigurationItemProviderAdapterFactory#getNewChildDescriptors(java.lang.Object, org.eclipse.emf.edit.domain.EditingDomain)
	 *
	 * @param object
	 * @param editingDomain
	 * @return
	 */
	@Override
	public Collection<?> getNewChildDescriptors(final Object object, final EditingDomain editingDomain) {
		Collection<CommandParameter> result = new ArrayList<CommandParameter>();
		if (object instanceof TreeFillingConfiguration || object instanceof GenericRelationshipMatrixCellEditorConfiguration) {
			final EStructuralFeature editedFeature = object instanceof TreeFillingConfiguration ? NattableaxisconfigurationPackage.eINSTANCE.getTreeFillingConfiguration_FilterRule()
					: NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter();
			// for an unknown reason, we get the contribution twice, so I manage it only the first time, identified by its EClass
			final List<EClass> alreadyManaged = new ArrayList<EClass>();
			for (final Object curr : this.expressionsExtendedChildrenFactory.getNewChildDescriptors(this.dummyExpression, editingDomain)) {
				if (curr instanceof CommandParameter) {
					final EObject value = ((CommandParameter) curr).getEValue();
					if (false == alreadyManaged.contains(value.eClass())) {
						final CommandParameter cp = new CommandParameter(null, editedFeature, value);
						alreadyManaged.add(value.eClass());
						result.add(cp);
					}
				}
			}
		}
		return result;
	}

	/**
	 * @see org.eclipse.papyrus.internal.infra.nattable.model.nattable.nattableaxisconfiguration.provider.NattableaxisconfigurationItemProviderAdapterFactory#getResourceLocator()
	 *
	 * @return
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return NattableEditPlugin.INSTANCE;
	}

}
