/*****************************************************************************
 * Copyright (c) 2018, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 568954
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.ui.architecture.messages.Messages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author melaasar
 *
 */
public class CustomAdapterFactoryContentProvider extends AdapterFactoryContentProvider {

	/**
	 * @param adapterFactory
	 */
	public CustomAdapterFactoryContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySourceProvider#getPropertySource(java.lang.Object)
	 *
	 * @param object
	 * @return
	 */
	@Override
	protected IPropertySource createPropertySource(Object object,
			IItemPropertySource itemPropertySource) {
		return new CustomPropertySource(object, itemPropertySource);
	}

	/**
	 * A custom property source
	 */
	private class CustomPropertySource extends PropertySource {
		/**
		 * Constructor.
		 *
		 * @param object
		 * @param itemPropertySource
		 */
		public CustomPropertySource(Object object, IItemPropertySource itemPropertySource) {
			super(object, itemPropertySource);
		}

		/*
		 * @see
		 * PropertySource#createPropertyDescriptor(org.eclipse.emf.edit .provider.IItemPropertyDescriptor)
		 */
		@Override
		protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor) {
			return new CustomPropertyDescriptor(object, itemPropertyDescriptor);
		}
	}

	private class CustomPropertyDescriptor extends PropertyDescriptor {

		/**
		 * @param object
		 * @param itemPropertyDescriptor
		 */
		public CustomPropertyDescriptor(Object object, IItemPropertyDescriptor itemPropertyDescriptor) {
			super(object, itemPropertyDescriptor);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * org.eclipse.ui.views.properties.IPropertyDescriptor#createPr opertyEditor(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public CellEditor createPropertyEditor(Composite composite) {
			final EObject eObject = (EObject) object;
			final EStructuralFeature feature = (EStructuralFeature) itemPropertyDescriptor.getFeature(eObject);
			if (feature.getName().endsWith("Class") || feature.getName().endsWith("class")) { //$NON-NLS-1$ //$NON-NLS-2$
				EClassifier etype = feature.getEType();
				if (etype instanceof EDataType && etype.getName().equals("EString")) { //$NON-NLS-1$
					CellEditor cellEditor = new DialogCellEditor(composite) {
						@Override
						protected Object openDialogBox(Control cellEditorWindow) {
							return selectTypeDialog(cellEditorWindow, eObject, feature);
						}
					};
					return cellEditor;
				}
			}
			return super.createPropertyEditor(composite);
		}
	}

	public String selectTypeDialog(Control cellEditorWindow, EObject eObject, EStructuralFeature feature) {
		URI uri = eObject.eResource().getURI();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFile(new Path(uri.toPlatformString(false)));
		IProject project = file.getProject();
		IJavaProject[] javaProject = new IJavaProject[] { JavaCore.create(project) };
		IJavaSearchScope searchScope = SearchEngine.createJavaSearchScope(javaProject);

		int scope = IJavaElementSearchConstants.CONSIDER_CLASSES;
		String filter = (String) eObject.eGet(feature);
		if (filter == null || filter.length() == 0) {
			filter = "**"; //$NON-NLS-1$
		}

		try {
			SelectionDialog dialog = JavaUI.createTypeDialog(cellEditorWindow.getShell(), PlatformUI.getWorkbench().getProgressService(), searchScope, scope, false, filter);
			dialog.setTitle(Messages.CustomAdapterFactoryContentProvider_0);
			if (dialog.open() == Window.OK) {
				IType type = (IType) dialog.getResult()[0];
				return type.getFullyQualifiedName('$');
			}
		} catch (JavaModelException e) {
			Activator.log.error(e);
		}
		return null;
	}
}
