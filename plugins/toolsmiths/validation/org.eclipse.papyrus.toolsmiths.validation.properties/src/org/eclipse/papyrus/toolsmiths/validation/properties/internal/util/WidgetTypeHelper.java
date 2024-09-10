/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.util;

import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.environment.EnvironmentPackage;
import org.eclipse.papyrus.infra.properties.environment.Namespace;
import org.eclipse.papyrus.infra.properties.environment.WidgetType;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.Widget;
import org.eclipse.papyrus.infra.properties.ui.runtime.IConfigurationManager;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesUtil;
import org.eclipse.papyrus.infra.tools.util.Iterators2;

/**
 * Support for analysis of {@linkplain PropertyEditor#getWidgetType() widget types} in <em>Properties UI</em>
 * models.
 */
public class WidgetTypeHelper {

	/** The namespace of the standard widget types in the base environment model. */
	private static final String NAMESPACE = "org.eclipse.papyrus.infra.properties.ui.widgets"; //$NON-NLS-1$

	private final IConfigurationManager configManager = PropertiesRuntime.getConfigurationManager();

	private Optional<Namespace> coreWidgetNamespace;

	/**
	 * Not instantiable by clients.
	 */
	private WidgetTypeHelper() {
		super();
	}

	public static final WidgetTypeHelper getInstance(Widget widget) {
		return getInstance(EMFHelper.getResourceSet(widget));
	}

	public static final WidgetTypeHelper getInstance(Property property) {
		return getInstance(EMFHelper.getResourceSet(property));
	}

	private static final WidgetTypeHelper getInstance(ResourceSet resourceSet) {
		HelperAdapter adapter = (HelperAdapter) EcoreUtil.getExistingAdapter(resourceSet, HelperAdapter.class);
		if (adapter == null) {
			adapter = new HelperAdapter(new WidgetTypeHelper());
			resourceSet.eAdapters().add(adapter);
		}

		return adapter.getHelper();
	}

	public WidgetType getDefaultWidgetType(Property property) {
		return configManager.getDefaultEditorType(property);
	}

	public boolean isCoreWidgetType(WidgetType widgetType) {
		return widgetType != null && widgetType.getNamespace() == getCoreWidgetNamespace();
	}

	public Namespace getCoreWidgetNamespace() {
		if (coreWidgetNamespace == null) {
			Predicate<Notifier> shouldPrune = Predicate.<Notifier> not(EnvironmentPackage.Literals.ENVIRONMENT::isInstance)
					.and(EObject.class::isInstance);
			coreWidgetNamespace = Iterators2.stream(configManager.getResourceSet().getAllContents(), shouldPrune)
					.filter(Namespace.class::isInstance).map(Namespace.class::cast)
					.filter(ns -> PropertiesUtil.namespaceEquals(ns, NAMESPACE))
					.findFirst();
		}

		return coreWidgetNamespace.orElse(null);
	}

	//
	// Nested types
	//

	private static final class HelperAdapter extends AdapterImpl {
		private final WidgetTypeHelper helper;

		HelperAdapter(WidgetTypeHelper helper) {
			super();

			this.helper = helper;
		}

		WidgetTypeHelper getHelper() {
			return helper;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == WidgetTypeHelper.class || type == HelperAdapter.class;
		}

	}

}
