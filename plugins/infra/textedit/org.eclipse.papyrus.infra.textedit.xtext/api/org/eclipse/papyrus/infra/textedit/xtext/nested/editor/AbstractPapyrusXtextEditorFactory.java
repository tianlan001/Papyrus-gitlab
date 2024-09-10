/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.infra.textedit.xtext.nested.editor;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.AbstractPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.ui.provider.TextDocumentLabelProvider;
import org.eclipse.papyrus.infra.textedit.xtext.Activator;
import org.eclipse.papyrus.infra.ui.extension.diagrameditor.AbstractEditorFactory;
import org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor.ActionBarContributorRegistry;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.eclipse.xtext.service.DefaultRuntimeModule;
import org.eclipse.xtext.ui.DefaultUiModule;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;


/**
 * Abstract Factory for the TextDocument
 */
public abstract class AbstractPapyrusXtextEditorFactory extends AbstractEditorFactory {

	/**
	 *
	 * Constructor.
	 *
	 * @param editorClass
	 *            the editor class
	 */
	public AbstractPapyrusXtextEditorFactory(Class<? extends PapyrusXTextEditor> editorClass) {
		// we don't use the type for the TextDocument model
		super(editorClass, "");//$NON-NLS-1$
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param editorClass
	 *            the editor class
	 * @param editorType
	 *            the type of editor
	 */
	public AbstractPapyrusXtextEditorFactory() {
		this(PapyrusXTextEditor.class);
	}

	/**
	 * Create the IPageModel that is used by the SashWindows to manage the editor.
	 *
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IEditorFactory#createIPageModel(java.lang.Object)
	 *
	 * @param pageIdentifier
	 *            The model pushed in the sashmodel by the creation command
	 * @return A model implementing the IPageModel
	 */
	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		ServicesRegistry services = getServiceRegistry();
		ILabelProvider labels = ServiceUtils.getInstance().tryService(services, LabelProviderService.class)
				.map(lps -> lps.getLabelProvider(pageIdentifier))
				.orElseGet(TextDocumentLabelProvider::new);
		return new TextDocumentViewEditorModel(pageIdentifier, services, labels);

	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IEditorFactory#isPageModelFactoryFor(java.lang.Object)
	 *
	 * @param pageIdentifier
	 * @return
	 */
	@Override
	public boolean isPageModelFactoryFor(Object pageIdentifier) {
		if (pageIdentifier instanceof TextDocument) {
			final TextDocument txtDocument = (TextDocument) pageIdentifier;
			return getSupportedImplementationID().equals(txtDocument.getType());
		}
		return false;
	}

	/**
	 * IEditorModel used internally by the SashContainer. This model know how to handle IEditor creation.
	 *
	 */
	private class TextDocumentViewEditorModel extends AbstractPageModel implements IEditorModel {


		/**
		 * The servicesRegistry provided at creation.
		 */
		private ServicesRegistry servicesRegistry;

		/**
		 * The created editor.
		 */
		private IEditorPart editor;

		/**
		 * The raw model stored in the SashProvider.
		 */
		private TextDocument rawModel;

		/**
		 *
		 * Constructor.
		 */
		public TextDocumentViewEditorModel(Object pageIdentifier, ServicesRegistry servicesRegistry, ILabelProvider labels) {
			super(labels);

			this.rawModel = (TextDocument) pageIdentifier;
			this.servicesRegistry = servicesRegistry;
		}

		/**
		 * Create the IEditor for the diagram.
		 *
		 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel#createIEditorPart()
		 * @return
		 * @throws PartInitException
		 *
		 */
		@Override
		public IEditorPart createIEditorPart() throws PartInitException {
			final Class<?> clazz = getDiagramClass();
			final Injector injector = createInjector();

			try {
				final Object result = injector.getInstance(clazz);
				if (result instanceof PapyrusXTextEditor) {
					PapyrusXTextEditor pte = (PapyrusXTextEditor) result;
					pte.configureXTextEditor(this.servicesRegistry, rawModel, getDirectEditorConfiguration(), getFileExtension());
					this.editor = (IEditorPart) result;
				}
				return this.editor;

			} catch (Exception e) {
				// Lets propagate. This is an implementation problem that should be solved by
				// programmer.
				throw new PartInitException(NLS.bind("Can't create the editor {0}", getDiagramClass().getName()), e); //$NON-NLS-1$
			}

		}

		/**
		 * Get the action bar requested by the Editor.
		 *
		 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel#getActionBarContributor()
		 * @return
		 *
		 */
		@Override
		public EditorActionBarContributor getActionBarContributor() {

			String actionBarId = AbstractPapyrusXtextEditorFactory.this.editorDescriptor.getActionBarContributorId();

			// Do nothing if no EditorActionBarContributor is specify.
			if (actionBarId == null || actionBarId.length() == 0) {
				return null;
			}

			// Get ServiceRegistry
			ActionBarContributorRegistry registry;
			try {
				registry = this.servicesRegistry.getService(ActionBarContributorRegistry.class);
			} catch (ServiceException e) {
				// Service not found
				return null;
			}

			try {
				return registry.getActionBarContributor(actionBarId);
			} catch (BackboneException e) {
				Activator.log.error(e);
				return null;
			}
		}

		/**
		 * Get the underlying RawModel. Return the Document.
		 *
		 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel#getRawModel()
		 * @return
		 *
		 */
		@Override
		public Object getRawModel() {
			return this.rawModel;
		}
	}

	/**
	 *
	 * @return
	 *         the created injector
	 */
	private Injector createInjector() {
		final Module runtimeModule = getRuntimeModule();
		final Module sharedStateModule = getSharedStateModule();
		final Module uiModule = getUiModule();
		final Module mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule);
		return Guice.createInjector(mergedModule);

	}

	/**
	 *
	 * @return
	 *         the runtime module to use
	 */
	protected abstract DefaultRuntimeModule getRuntimeModule();

	/**
	 *
	 * @return
	 *         the UI module to use
	 */
	protected abstract DefaultUiModule getUiModule();

	/**
	 * the shared state module
	 */
	protected Module getSharedStateModule() {
		return new SharedStateModule();
	}

	/**
	 *
	 * @return
	 *         the directe editor configuration
	 */
	protected abstract ICustomDirectEditorConfiguration getDirectEditorConfiguration();

	/**
	 *
	 * @return
	 *         the file extension declared in the XText grammar
	 */
	protected abstract String getFileExtension();

	/**
	 *
	 * @return
	 *         the implementationID declared in the architecture file for the supported editor
	 */
	protected abstract String getSupportedImplementationID();
}

