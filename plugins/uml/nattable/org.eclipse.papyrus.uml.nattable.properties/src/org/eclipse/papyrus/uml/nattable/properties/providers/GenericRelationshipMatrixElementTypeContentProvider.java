/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.properties.providers;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.cell.IGenericMatrixRelationshipCellManager;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * The content provider used by Generic Matrix to choose relationship element type
 */
public final class GenericRelationshipMatrixElementTypeContentProvider implements IStaticContentProvider, IGraphicalContentProvider, IHierarchicContentProvider, ITreeContentProvider, IContentProvider {

	/**
	 * The architecture context which allows to get all available element types
	 */
	private MergedArchitectureContext architextureContext;

	/**
	 * The list of the managed relationship identified by their EClass
	 */
	private Collection<EClass> managedRelationships;

	/**
	 * The list of the identifiers of the ElementTypeSetConfiguration to ignore
	 */
	private Collection<String> elementTypeSetConfigurationToIgnore;

	/**
	 * the map with the available elementtypeconfiguration and their allowed children.
	 */
	private Map<ElementTypeSetConfiguration, Collection<ElementTypeConfiguration>> typeConfigurationAndTheirChildren;

	/**
	 * The comparator used to sort Element typess
	 */
	private Comparator<ElementTypeConfiguration> comparator = new ElementTypeConfigurationComparator();

	/**
	 * the uri as string of the UML metamodel
	 */
	private static final String UML_METAMODEL_URI = "http://www.eclipse.org/uml2/5.0.0/UML";//$NON-NLS-1$

	/**
	 * the eobject used as context to create the architecture context to use
	 */
	private EObject contextForAF;

	/**
	 * Constructor.
	 *
	 * @param context
	 */
	public GenericRelationshipMatrixElementTypeContentProvider(final EObject context) {
		this.contextForAF = context;
		this.architextureContext = createArchitectureContext();
		initFields();
	}

	/**
	 * 
	 * return
	 * the created MergedArchitectureContext or <code>null</code> when it can't be created
	 */
	private MergedArchitectureContext createArchitectureContext() {
		if (null != this.contextForAF && null != this.contextForAF.eResource() && null != this.contextForAF.eResource().getResourceSet()) {
			return new ArchitectureDescriptionUtils((ModelSet) this.contextForAF.eResource().getResourceSet()).getArchitectureContext();
		}
		return null;
	}

	/**
	 * This method allows to init the field of this class
	 */
	private void initFields() {
		this.managedRelationships = initManagedRelationships();
		this.elementTypeSetConfigurationToIgnore = initElementTypeSetConfigurationsToIgnore();
		this.typeConfigurationAndTheirChildren = initMapContents();
	}

	/**
	 * @return
	 */
	private Map<ElementTypeSetConfiguration, Collection<ElementTypeConfiguration>> initMapContents() {
		Map<ElementTypeSetConfiguration, Collection<ElementTypeConfiguration>> mapByMetamodel = new HashMap<ElementTypeSetConfiguration, Collection<ElementTypeConfiguration>>();
		if (null == this.architextureContext) {
			return mapByMetamodel;
		}

		// we build the set of the supported configuration according to the context and the existing Matrix Cell Managers
		for (final ElementTypeSetConfiguration typeSet : this.architextureContext.getElementTypes()) {
			if (!UML_METAMODEL_URI.equals(typeSet.getMetamodelNsURI())) {
				continue;
			}

			if (this.elementTypeSetConfigurationToIgnore.contains(typeSet.getIdentifier())) {
				continue;
			}
			final TreeSet<ElementTypeConfiguration> children = new TreeSet<ElementTypeConfiguration>(this.comparator);
			for (final ElementTypeConfiguration configuration : typeSet.getElementTypeConfigurations()) {
				if (isValidValue(configuration)) {
					children.add(configuration);
				}
			}
			if (children.size() > 0) {
				mapByMetamodel.put(typeSet, children);
			}
		}
		return mapByMetamodel;
	}


	/**
	 * 
	 * @return
	 * 		a collection with the managed relationship identified by their EClass
	 */
	private Collection<EClass> initManagedRelationships() {
		final Set<EClass> managedRelationships = new HashSet<EClass>();
		for (final IGenericMatrixRelationshipCellManager current : CellManagerFactory.INSTANCE.getRegisteredGenericMatrixRelationshipCellManager()) {
			managedRelationships.add(current.getManagedRelationship());
		}
		return managedRelationships;
	}

	/**
	 * @return
	 * 		a collection with the identifier of the ElementTypeSetConfigurations to ignore
	 */
	private Collection<String> initElementTypeSetConfigurationsToIgnore() {
		final Set<String> ignoredStypeSets = new HashSet<String>();
		ignoredStypeSets.add("org.eclipse.papyrus.umldi.service.types.UMLDIElementTypeSet"); //$NON-NLS-1$
		ignoredStypeSets.add("org.eclipse.papyrus.sysml14di.elementTypeSet.class.extension"); //$NON-NLS-1$
		return ignoredStypeSets;
	}

	/**
	 * 
	 * @param configuration
	 *            an element type configuration
	 * @return
	 * 		<code>true</code> if the element type is managed by a CellManager and <code>false</code> otherwise
	 */
	private boolean isManagedElementTypeConfiguration(final ElementTypeConfiguration configuration) {
		final IElementType elementType;

		if (configuration instanceof MetamodelTypeConfiguration) {
			elementType = ElementTypeRegistry.getInstance().getType(((MetamodelTypeConfiguration) configuration).getIdentifier());
		} else if (configuration instanceof SpecializationTypeConfiguration) {
			elementType = ElementTypeRegistry.getInstance().getType(((SpecializationTypeConfiguration) configuration).getIdentifier());
		} else {
			elementType = null;
		}
		if (null == elementType) {
			return false;
		}


		final String name = configuration.getName() == null ? "" : configuration.getName();
		if (name.split(ProviderUtils.ELEMENT_TYPE_DISPLAY_NAME_SEPARATOR).length == 3) {
			return false;
		}

		if (elementType.getEClass() == null) {
			return false;
		}

		if (configuration instanceof MetamodelTypeConfiguration) {
			return this.managedRelationships.contains(((MetamodelTypeConfiguration) configuration).getEClass());
		}
		if (configuration instanceof SpecializationTypeConfiguration) {
			final SpecializationTypeConfiguration a = (SpecializationTypeConfiguration) configuration;
			if (a.getSpecializedTypes().size() == 1) {
				return isManagedElementTypeConfiguration(a.getSpecializedTypes().get(0));
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		return getElements();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 */
	@Override
	public Object[] getElements() {
		MergedArchitectureContext af = createArchitectureContext();
		if (null != this.architextureContext && !this.architextureContext.equals(af)) {// we check to be able to update our content provider if the architecture context changed, keeping visible the matrix tab
			this.architextureContext = af;
			initFields();
		}
		if (null != this.typeConfigurationAndTheirChildren) {
			return this.typeConfigurationAndTheirChildren.keySet().toArray();
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		if (this.typeConfigurationAndTheirChildren.containsKey(parentElement)) {
			return this.typeConfigurationAndTheirChildren.get(parentElement).toArray();
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(final Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(final Object element) {
		if (this.typeConfigurationAndTheirChildren.containsKey(element)) {
			return this.typeConfigurationAndTheirChildren.get(element).size() > 0;
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean isValidValue(Object element) {
		if (element instanceof ElementTypeConfiguration) {
			return isManagedElementTypeConfiguration((ElementTypeConfiguration) element);
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createBefore(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createBefore(Composite parent) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createAfter(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createAfter(Composite parent) {
		// nothing to do
	}

	/**
	 * This comparator is used to sort the provider {@link ElementTypeConfiguration}
	 */
	private class ElementTypeConfigurationComparator implements Comparator<ElementTypeConfiguration> {

		/**
		 * the label provider used to compare the {@link ElementTypeConfiguration}
		 */
		private ILabelProvider provider = new GenericRelationshipMatrixElementTypeLabelProvider();

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 *
		 * @param arg0
		 * @param arg1
		 * @return
		 */
		@Override
		public int compare(ElementTypeConfiguration arg0, ElementTypeConfiguration arg1) {
			return provider.getText(arg0).compareToIgnoreCase(provider.getText(arg1));
		}

	}
}
