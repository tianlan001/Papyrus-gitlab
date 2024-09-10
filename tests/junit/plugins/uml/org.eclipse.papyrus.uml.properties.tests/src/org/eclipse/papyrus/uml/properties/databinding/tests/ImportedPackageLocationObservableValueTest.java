/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.databinding.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.properties.modelelement.ImportedPackageModelElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.junit.Assert;

/**
 * This allows to test the imported package location observable value.
 */
@SuppressWarnings({ "nls" })
@PluginResource("model/ImportedPackageLocationObservableValue.di")
public class ImportedPackageLocationObservableValueTest extends AbstractUMLObservableValueTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		final PackageImport packageImport = (PackageImport) model.getPackageImports().get(0);
		Assert.assertNotNull("The model must contains package import", packageImport);

		final Package pckg = packageImport.getImportedPackage();
		Assert.assertNotNull("The imported package must not be null", pckg);

		return pckg;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new ImportedPackageModelElement((EObject) source, domain);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return "location";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return "pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml";
	}

	/**
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#canModify()
	 *
	 * @return
	 */
	@Override
	protected boolean canModify() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableValueTest#expectedValueType()
	 */
	@Override
	protected Object expectedValueType() {
		return String.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		return null;
	}

}
