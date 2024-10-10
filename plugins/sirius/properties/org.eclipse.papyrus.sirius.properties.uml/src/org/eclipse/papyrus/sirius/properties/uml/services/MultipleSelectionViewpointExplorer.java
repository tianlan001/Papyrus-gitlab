/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.sirius.properties.uml.Activator;
import org.eclipse.sirius.properties.Category;
import org.eclipse.sirius.properties.GroupDescription;
import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.viewpoint.description.Group;

/**
 * This class is used to load the VSM resource
 * "/org.eclipse.papyrus.sirius.properties.uml/description/umlProperties.odesign" in order
 * to retrieve Groups matching UML classes to display in Properties View. These Groups are
 * used to determine which features can be displayed among the list of common features between
 * these classes.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class MultipleSelectionViewpointExplorer {

	/**
	 * The name of the properties group memberEnd_uml_group.
	 */
	private static final String MEMBER_END_UML_GROUP_NAME = "memberEnd_uml_group"; //$NON-NLS-1$

	/**
	 * The name of the properties group metaclass_uml_group.
	 */
	private static final String METACLASS_UML_GROUP_NAME = "metaclass_uml_group"; //$NON-NLS-1$

	/**
	 * The prefix used in the domain class of each properties group.
	 */
	private static final String UML_DOMAIN_CLASS_PREFIX = "uml::"; //$NON-NLS-1$

	/**
	 * The suffix used for the property groups of the UML page.
	 */
	private static final String UML_GROUP_SUFFIX = "_uml_group"; //$NON-NLS-1$

	/**
	 * The relative path of the VSM.
	 */
	private static final String UML_PROPERTIES_ODESIGN_PATH = "/" + Activator.PLUGIN_ID + Activator.VSM_PATH; //$NON-NLS-1$

	/**
	 * The singleton instance.
	 */
	private static MultipleSelectionViewpointExplorer instance;

	/**
	 * The content of the VSM "umlProperties.odesign".
	 */
	private Group vsmContent;

	/**
	 * The constructor used to initialize the singleton.
	 */
	private MultipleSelectionViewpointExplorer() {
		AdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(adapterFactory, null);
		ResourceSet resourceSet = editingDomain.getResourceSet();

		URI uri = URI.createPlatformPluginURI(UML_PROPERTIES_ODESIGN_PATH, true);
		Resource resource = resourceSet.getResource(uri, true);
		this.vsmContent = (Group) resource.getContents().get(0);
	}

	/**
	 * Gets the singleton instance for this class.
	 * 
	 * @return the singleton instance
	 */
	public static MultipleSelectionViewpointExplorer getInstance() {
		if (instance == null) {
			instance = new MultipleSelectionViewpointExplorer();
		}
		return instance;
	}

	/**
	 * Used to identify which features should be displayed among the specified features list,
	 * according to the controls used in the VSM groups.
	 * 
	 * @param classifiers
	 *            the set of classifiers to which these features belong
	 * @param commonFeatures
	 *            the list of common features found for the classifiers
	 * @return the list of features that can be displayed
	 */
	public List<EStructuralFeature> keepDisplayableFeatures(Set<EClass> classifiers, List<EStructuralFeature> commonFeatures) {
		List<EStructuralFeature> featuresToKeep = new ArrayList<>();
		if (commonFeatures != null) {
			Set<String> featureNames = commonFeatures.stream().map(feature -> feature.getName()).collect(Collectors.toSet());
			if (classifiers != null) {
				List<GroupDescription> groupsMatchingClassifiers = getGroupsMatchingClassifiers(classifiers);
				if (!groupsMatchingClassifiers.isEmpty()) {
					for (GroupDescription group : groupsMatchingClassifiers) {
						Set<String> displayableFeatures = group.getControls().stream()
								.map(control -> control.getName())
								.filter(name -> featureNames.contains(name))
								.collect(Collectors.toSet());
						featureNames.retainAll(displayableFeatures);
					}
				} else {
					featureNames.clear();
				}
			}
			if (!featureNames.isEmpty()) {
				featuresToKeep = commonFeatures.stream()
						.filter(feature -> featureNames.contains(feature.getName()))
						.collect(Collectors.toList());
			}
		}
		return featuresToKeep;
	}

	/**
	 * Gets the list of UML groups that can be applied on each specified classifiers.
	 * 
	 * @param classifiers
	 *            the set of classifiers for which we are looking for groups that can be applied
	 * @return the list of matching groups found
	 */
	private List<GroupDescription> getGroupsMatchingClassifiers(Set<EClass> classifiers) {
		ViewExtensionDescription siriusRulesProperties = (ViewExtensionDescription) this.vsmContent.getExtensions().get(0);
		Category defaultCategory = siriusRulesProperties.getCategories().get(0);
		List<GroupDescription> allGroups = defaultCategory.getGroups();
		Set<String> typeNames = classifiers.stream().map(eClass -> eClass.getName()).collect(Collectors.toSet());
		List<GroupDescription> matchingGroups = allGroups.stream()
				.filter(group -> matchGroup(typeNames, group))
				.filter(group -> !isAnException(group))
				.collect(Collectors.toList());
		return matchingGroups;
	}

	/**
	 * Used to identify some exceptions that should not be included in the list of match groups.
	 * The "metaclass_uml_group" may match the Class UML element, and the "memberEnd_uml_group" should
	 * not be used in multiple selections.
	 * 
	 * @param group
	 *            the group that may be an exception
	 * @return <code>true</code> if the group is an exception;<code>false</code> otherwise.
	 */
	private boolean isAnException(GroupDescription group) {
		return METACLASS_UML_GROUP_NAME.equals(group.getName()) || MEMBER_END_UML_GROUP_NAME.equals(group.getName());
	}

	/**
	 * Used to identify if the specified group can be applied on one of the specified UML class names.
	 * The domain class and the name of the group are used to determines if it can be applied.
	 * 
	 * @param typeNames
	 *            the set of UML class names
	 * @param group
	 *            the group that may be used to be applied on one of the UML class
	 * @return <code>true</code> if the group can be applied on one UML class;<code>false</code> otherwise.
	 */
	private boolean matchGroup(Set<String> typeNames, GroupDescription group) {
		String domainClass = group.getDomainClass().substring(UML_DOMAIN_CLASS_PREFIX.length());
		return typeNames.contains(domainClass) && group.getName().endsWith(UML_GROUP_SUFFIX);
	}

}
