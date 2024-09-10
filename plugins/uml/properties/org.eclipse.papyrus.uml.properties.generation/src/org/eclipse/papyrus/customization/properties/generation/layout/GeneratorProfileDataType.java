/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.customization.properties.generation.layout;

import java.util.List;

import org.eclipse.papyrus.customization.properties.generation.Activator;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.customization.properties.generation.generators.ProfileGenerator;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextRoot;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.environment.CompositeWidgetType;
import org.eclipse.papyrus.infra.properties.environment.Environment;
import org.eclipse.papyrus.infra.properties.environment.LayoutType;
import org.eclipse.papyrus.infra.properties.environment.PropertyEditorType;
import org.eclipse.papyrus.infra.properties.environment.Type;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.papyrus.infra.properties.ui.Layout;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.UiFactory;
import org.eclipse.papyrus.infra.properties.ui.ValueAttribute;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Generator for Profile which contained DataTypes.
 */
public class GeneratorProfileDataType extends StandardLayoutGenerator {

	/**
	 * The managed generator.
	 */
	protected IGenerator generator;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator#sortEditors(java.util.List)
	 */
	@Override
	protected void sortEditors(final List<PropertyEditor> editors) {
		for (PropertyEditor editor : editors) {
			Category category = new TypeCategory(editor.getProperty());
			getByCategory(category).add(editor);

			if (editor.getWidgetType() == null) {
				Activator.log.warn(String.format("Editor for property %s doesn't have a WidgetType", editor.getProperty().getName())); //$NON-NLS-1$
				continue;
			}

			namespaces.add(editor.getWidgetType().getNamespace());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator#layoutCategorizedEditors(org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator.Category, java.util.List)
	 */
	@Override
	protected CompositeWidget layoutCategorizedEditors(final Category category, final List<PropertyEditor> editors) {
		if (((TypeCategory) category).isDatatype) {
			CompositeWidgetType compositeType = PropertiesRuntime.getConfigurationManager().getDefaultCompositeType();

			CompositeWidget container = UiFactory.eINSTANCE.createCompositeWidget();
			container.setWidgetType(compositeType);

			Layout layout = createLayout(category.getNumColumns());
			container.setLayout(layout);

			for (PropertyEditor editor : editors) {
				PropertyEditor viewEditor = UiFactory.eINSTANCE.createPropertyEditor();
				viewEditor.setWidgetType(getViewEditor());
				viewEditor.setProperty(editor.getProperty());

				ValueAttribute view = UiFactory.eINSTANCE.createValueAttribute();
				view.setName("view"); //$NON-NLS-1$
				view.setValue(getViewName(editor.getProperty()));
				viewEditor.getAttributes().add(view);

				container.getWidgets().add(viewEditor);
			}

			return container;
		}

		return super.layoutCategorizedEditors(category, editors);
	}

	/**
	 * Get the view name.
	 * 
	 * @param property
	 *            The context property to manage.
	 * @return The string corresponding to the property.
	 */
	protected String getViewName(final Property property) {
		if (!(generator instanceof ProfileGenerator)) {
			return ""; //$NON-NLS-1$
		}

		org.eclipse.uml2.uml.Property attribute = ((ProfileGenerator) generator).getAttribute(property);

		Package nearestPackage = attribute.getType().getNearestPackage();
		Package rootPackage = nearestPackage;
		while (rootPackage.getNestingPackage() != null) {
			rootPackage = rootPackage.getNestingPackage();
		}

		// TODO : We're assuming the rootPackage has the same name as the context...
		// This layout generator is really only compatible with ProfileGenerator
		return rootPackage.getName() + ":Single " + attribute.getType().getName(); //$NON-NLS-1$
	}

	/**
	 * This allows to find the context property.
	 * 
	 * @param property
	 *            The context property.
	 * @return the context.
	 */
	protected Context findContext(final Property property) {
		DataContextElement element = property.getContextElement();
		if (element instanceof DataContextRoot) {
			// TODO : Add a container reference to Context
			return (Context) element.eContainer();
		}

		return findContext(element);
	}

	/**
	 * Find the context from the data context element.
	 * 
	 * @param element
	 *            The data context element.
	 * @return The needed context.
	 */
	protected Context findContext(final DataContextElement element) {
		DataContextPackage dataContextPackage = element.getPackage();
		if (dataContextPackage instanceof DataContextRoot) {
			return (Context) dataContextPackage.eContainer();
		}
		return findContext(dataContextPackage);
	}

	/**
	 * Get the qualified name of the view.
	 * 
	 * @param view
	 *            The view.
	 * @return The qualified name of the view.
	 */
	protected String getQualifiedName(final View view) {
		return view.getContext().getName() + ":" + view.getName(); //$NON-NLS-1$
	}

	/**
	 * This allows to create the correct layout.
	 * 
	 * @param columns
	 *            the number of columns needed.
	 * @return The created layout.
	 */
	protected Layout createLayout(final Integer columns) {
		LayoutType propertiesLayoutType = PropertiesRuntime.getConfigurationManager().getDefaultLayoutType();

		Layout layout = UiFactory.eINSTANCE.createLayout();
		ValueAttribute numColumns = UiFactory.eINSTANCE.createValueAttribute();
		numColumns.setName("numColumns"); //$NON-NLS-1$
		numColumns.setValue(columns.toString());

		layout.getAttributes().add(numColumns);
		layout.setLayoutType(propertiesLayoutType);

		return layout;
	}

	/**
	 * Get the view editor.
	 * 
	 * @return The view editor.
	 */
	protected PropertyEditorType getViewEditor() {
		for (Environment environment : ConfigurationManager.getInstance().getPropertiesRoot().getEnvironments()) {
			for (PropertyEditorType widgetType : environment.getPropertyEditorTypes()) {
				if (widgetType.getNamespace() != null && "ppe".equals(widgetType.getNamespace().getName()) && widgetType.getWidgetClass().equals("DataTypeEditor")) {
					return widgetType;
				}
			}
		}

		return PropertiesRuntime.getConfigurationManager().getDefaultEditorType(Type.STRING, false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator#setGenerator(org.eclipse.papyrus.customization.properties.generation.generators.IGenerator)
	 */
	@Override
	public void setGenerator(IGenerator generator) {
		this.generator = generator;
	}

	/**
	 * The type category.
	 */
	protected class TypeCategory extends Category {

		/**
		 * Boolean to determinate if this is a data type.
		 */
		protected boolean isDatatype;

		/**
		 * Constructor.
		 *
		 * @param property
		 *            The context property to manage.
		 */
		public TypeCategory(final Property property) {
			super(property);
			org.eclipse.uml2.uml.Property attribute = resolveProperty(property);
			if (attribute != null && attribute.getType() != null) {
				isDatatype = attribute.getType().eClass() == UMLPackage.eINSTANCE.getDataType();
			}
		}

		/**
		 * Resolve the context property to UML property.
		 * 
		 * @param property
		 *            The context property.
		 * @return The UML property corresponding.
		 */
		protected org.eclipse.uml2.uml.Property resolveProperty(final Property property) {
			// TODO : We should not have to rely on the IGenerator to retrieve the property...
			if (generator instanceof ProfileGenerator) {
				ProfileGenerator profileGenerator = (ProfileGenerator) generator;
				return profileGenerator.getAttribute(property);
			}

			return null;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator.Category#getNumColumns()
		 */
		@Override
		public Integer getNumColumns() {
			if (isDatatype) {
				return 1;
			}
			return super.getNumColumns();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator.Category#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + (isDatatype ? 1231 : 1237);
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator.Category#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (!(obj instanceof TypeCategory)) {
				return false;
			}
			TypeCategory other = (TypeCategory) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (isDatatype != other.isDatatype) {
				return false;
			}
			return true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator.Category#getTypeIndex()
		 */
		@Override
		public Integer getTypeIndex() {
			if (isDatatype) {
				return orderedTypes.length + 1;
			}
			return super.getTypeIndex();
		}

		/**
		 * Return the outer type.
		 * 
		 * @return The outer type.
		 */
		private GeneratorProfileDataType getOuterType() {
			return GeneratorProfileDataType.this;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.properties.generation.layout.StandardLayoutGenerator#getName()
	 */
	@Override
	public String getName() {
		return "Generator for Profile with DataTypes"; //$NON-NLS-1$
	}

}
