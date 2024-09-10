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

package org.eclipse.papyrus.infra.editor.welcome.internal;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IComponentModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ICloseablePart;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.ui.extension.diagrameditor.EditorDescriptor;
import org.eclipse.papyrus.infra.ui.extension.diagrameditor.IPluggableEditorFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * Papyrus Editor editor factory for the welcome page.
 */
public class WelcomePageFactory implements IPluggableEditorFactory {

	private ServicesRegistry services;

	private ImageDescriptor icon;

	public WelcomePageFactory() {
		super();
	}

	@Override
	public void init(ServicesRegistry serviceRegistry, EditorDescriptor editorDescriptor) {
		this.services = serviceRegistry;
		this.icon = editorDescriptor.getIcon();
	}

	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		return new WelcomePageModel(pageIdentifier);
	}

	@Override
	public boolean isPageModelFactoryFor(Object pageIdentifier) {
		return WelcomePageService.isModel(pageIdentifier);
	}

	//
	// Nested types
	//

	private class WelcomePageModel implements IComponentModel, IAdaptable {

		private Object model;
		private Image iconImage;

		private WelcomePage welcome;

		WelcomePageModel(Object model) {
			super();

			this.model = model;
		}

		@Override
		public <T> T getAdapter(Class<T> adapter) {
			T result = null;

			if ((adapter == ICloseablePart.class) || (adapter == WelcomePage.class)) {
				result = adapter.cast(welcome); // Cast is null-safe
			}

			if (result == null) {
				result = Platform.getAdapterManager().getAdapter(this, adapter);
			}

			return result;
		}

		@Override
		public String getTabTitle() {
			return "Welcome";
		}

		@Override
		public Image getTabIcon() {
			if ((iconImage == null) && (icon != null)) {
				iconImage = icon.createImage(Display.getCurrent());
			}

			return iconImage;
		}

		@Override
		public Object getRawModel() {
			return model;
		}

		@Override
		public void dispose() {
			if (iconImage != null) {
				iconImage.dispose();
				iconImage = null;
			}
		}

		@Override
		public Composite createPartControl(Composite parent) {
			Composite result = null;

			try {
				IWelcomePageService service = services.getService(IWelcomePageService.class);
				welcome = new WelcomePage(service, model);
				result = welcome.createControl(parent);
			} catch (ServiceException e) {
				throw new IllegalStateException("Welcome-page service not available", e);
			}

			return result;
		}

	}
}
