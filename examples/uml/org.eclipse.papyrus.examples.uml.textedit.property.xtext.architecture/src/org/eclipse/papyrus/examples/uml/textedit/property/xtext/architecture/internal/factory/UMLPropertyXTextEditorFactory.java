/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.examples.uml.textedit.property.xtext.architecture.internal.factory;

import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.textedit.xtext.nested.editor.AbstractPapyrusXtextEditorFactory;
import org.eclipse.papyrus.uml.textedit.property.xtext.UmlPropertyRuntimeModule;
import org.eclipse.papyrus.uml.textedit.property.xtext.ui.contributions.PropertyXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.textedit.property.xtext.ui.internal.UmlPropertyActivator;
import org.eclipse.xtext.service.DefaultRuntimeModule;
import org.eclipse.xtext.ui.DefaultUiModule;

/**
 * The XText editor factory used to open the XTextEditor configured for UML Property
 */
public class UMLPropertyXTextEditorFactory extends AbstractPapyrusXtextEditorFactory {

	/**
	 * the implementationID value declared in the architecture file
	 */
	private static final String TEXT_DOCUMENT_TYPE = "UMLXTextPropertyEditor";//$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public UMLPropertyXTextEditorFactory() {
		super();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.textedit.xtext.AbstractPapyrusXtextEditorFactory#getRuntimeModule()
	 *
	 * @return
	 */
	@Override
	protected DefaultRuntimeModule getRuntimeModule() {
		return new UmlPropertyRuntimeModule();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.textedit.xtext.AbstractPapyrusXtextEditorFactory#getUiModule()
	 *
	 * @return
	 */
	@Override
	protected DefaultUiModule getUiModule() {
		return new CustomUmlPropertyUiModule(UmlPropertyActivator.getInstance());
	}

	/**
	 * @see org.eclipse.papyrus.infra.textedit.xtext.AbstractPapyrusXtextEditorFactory#getDirectEditorConfiguration()
	 *
	 * @return
	 */
	@Override
	protected ICustomDirectEditorConfiguration getDirectEditorConfiguration() {
		return new PropertyXtextDirectEditorConfiguration();
	}

	/**
	 * @see org.eclipse.papyrus.infra.textedit.xtext.nested.editor.AbstractPapyrusXtextEditorFactory#getFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getFileExtension() {
		return "umlproperty"; //$NON-NLS-1$
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.textedit.xtext.nested.editor.AbstractPapyrusXtextEditorFactory#getSupportedImplementationID()
	 *
	 * @return
	 */
	@Override
	protected String getSupportedImplementationID() {
		return TEXT_DOCUMENT_TYPE;
	}
}
