/******************************************************************************
 * Copyright (c) 2015, 2020 Montages A.G., CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Svyatoslav Kovalsky (Montages) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.1 Remove reference to xpand/qvto
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 generate less dead or duplicate code
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.net.URL;
import java.util.List;

import org.eclipse.papyrus.gmf.common.UnexpectedBehaviourException;
import org.eclipse.papyrus.gmf.internal.common.codegen.JavaClassEmitter;

import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class CodegenEmittersWithXtend2 extends CodegenEmitters {

	private final Injector myInjector;

	private IExtensionTemplatesProvider myExtensionTemplateProvider = null;

	public CodegenEmittersWithXtend2(boolean useBaseTemplatesOnly, String templateDirectory) {
		if (templateDirectory != null && !templateDirectory.isEmpty()) {
			myExtensionTemplateProvider = new ExtensionTemplatesProviderImpl(templateDirectory, !useBaseTemplatesOnly);
		}
		myInjector = Guice.createInjector(new GMFGeneratorModule(myExtensionTemplateProvider));
	}

	// -----------------------------------------------------------------------------------------

	@Override
	public JavaClassEmitter getPropertySheetLabelProviderEmitter() throws UnexpectedBehaviourException {
		return getXtendEmitter("xpt::propsheet::LabelProvider", "LabelProvider"); //$NON-NLS-1$
	}

	@Override
	public JavaClassEmitter getPropertySectionEmitter() throws UnexpectedBehaviourException {
		return getXtendEmitter("xpt::propsheet::PropertySection", "PropertySection"); //$NON-NLS-1$
	}

	@Override
	public JavaClassEmitter getModelAccessFacilityEmitter() {
		return getXtendEmitter("metamodel::Facility", "Main");
	}

	private JavaClassEmitter getXtendEmitter(String templateFqn, String mainMethod) {
		String classFqn = templateFqn.replace("::", ".");
		Class<?> clazz = null;
		try {
			clazz = Class.forName(classFqn);
		} catch (ClassNotFoundException e) {
			if (myExtensionTemplateProvider != null) {
				List<Class<?>> customClasses = myExtensionTemplateProvider.getCustomTemplateClasses();
				for (Class<?> _class : customClasses) {
					String name = _class.getName();
					if (name.equals(classFqn)) {
						clazz = _class;
						break;
					}
				}
			}
			if (clazz == null) {
				throw new IllegalStateException("Can't load: " + classFqn, e);
			}
		}
		return new Xtend2ClassEmitter(myInjector, clazz, mainMethod);
	}

	public void disposeEmitters() {
		if (myExtensionTemplateProvider != null) {
			myExtensionTemplateProvider.dispose();
		}
	}

	@Override
	protected JavaClassEmitter createJavaClassEmitter(String templateName, String mainMethod) {
		return getXtendEmitter(templateName, mainMethod);
	}

	/**
	 * @see org.eclipse.papyrus.gmf.codegen.util.CodegenEmitters#getJMergeControlFile()
	 *
	 * @return
	 */
	@Override
	public URL getJMergeControlFile() {
		// @generated NOT is ignored if control file is undefined
		return CodegenXtendPlugin.getInstance().getBundle().getEntry("/templates/emf-merge.xml"); //$NON-NLS-1$
	}
}
