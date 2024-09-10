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

import org.eclipse.papyrus.infra.textedit.xtext.custom.PapyrusOutlinePage;
import org.eclipse.papyrus.infra.textedit.xtext.custom.PapyrusOutlineRefreshJob;
import org.eclipse.papyrus.infra.textedit.xtext.custom.PapyrusXTextDocumentProvider;
import org.eclipse.papyrus.uml.textedit.property.xtext.ui.UmlPropertyUiModule;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.ui.editor.model.IResourceForEditorInputFactory;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineRefreshJob;

/**
 * Custom Uml Property UI Module
 */
public class CustomUmlPropertyUiModule extends UmlPropertyUiModule {

	/**
	 * Constructor.
	 *
	 * @param plugin
	 */
	public CustomUmlPropertyUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	/**
	 *
	 * @see org.eclipse.xtext.ui.DefaultUiModule#bindIResourceForEditorInputFactory()
	 *
	 * @return
	 *         the {@link IResourceForEditorInputFactory} to use. We provide a custom one, to get a working completion proposal
	 */
	@Override
	public Class<? extends IResourceForEditorInputFactory> bindIResourceForEditorInputFactory() {
		return PapyrusResourceForEditorInputFactory.class;
	}

	/**
	 *
	 * @return
	 *         the document provider to use. We need to provide a custom one, to manage the save in the edited model
	 */
	public Class<? extends XtextDocumentProvider> bindXtextDocumentProvider() {
		return PapyrusXTextDocumentProvider.class;
	}

	/**
	 *
	 * @see org.eclipse.xtext.ui.DefaultUiModule#bindIContentOutlinePage()
	 *
	 * @return
	 */
	@Override
	public Class<? extends IContentOutlinePage> bindIContentOutlinePage() {
		return PapyrusOutlinePage.class;
	}

	public Class<? extends OutlineRefreshJob> bindOutlineRefreshJob() {
		return PapyrusOutlineRefreshJob.class;
	}
}
