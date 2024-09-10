/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Arnaud Cuccuru (CEA LIST) - Initial API and implementation
 *  Vincent Lorenzo   (CEA LIST)
 *  Benoit Maggi (CEA LIST) - Bug 431629 Patch to avoid loop on imported packages
 *  Benoit Maggi (CEA LIST) - Bug 518317 Autocompletion for type of properties 
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * 
 * This class allows to find named element from this name or its (partially) qualified name
 *
 */
public class NameResolutionHelper implements INameResolutionHelper {

	/**
	 * the scope used to look for the elements
	 */
	protected Namespace scope;

	/**
	 * the filter
	 */
	protected EClass filter;

	/**
	 * This map links the name and the named element
	 */
	protected Map<String, List<NamedElement>> allNames;

	/**
	 * the empty string
	 */
	private static final String EMPTY_STRING = ""; // $NON-NLS-N1

	/**
	 * 
	 * Constructor.
	 *
	 * @param element
	 *            an element of the model
	 * @param filter
	 *            the filter
	 */
	public NameResolutionHelper(Element element, EClass filter) {
		Namespace space = null;
		if (element instanceof Namespace) {
			space = (Namespace) element;

		} else if (element instanceof NamedElement) {
			space = ((NamedElement) element).getNamespace();
		} else {
			space = element.getNearestPackage();
		}
		init(space, filter);
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param element
	 *            an element of the model
	 * @param filter
	 *            the filter
	 */
	public NameResolutionHelper(Namespace scope, EClass filter) {
		init(scope, filter);
	}

	/**
	 * A cached getter to all names structure
	 * @return all the names
	 * @since 3.3
	 */
	public Map<String, List<NamedElement>> getAllNames() {
		if (allNames == null) {
			allNames = new HashMap<>();
			computeAllNames();
		} 
		return allNames;
	}
	
	
	/**
	 * 
	 * @param scope
	 * @param filter
	 */
	protected void init(Namespace scope, EClass filter) {
		Assert.isNotNull(scope);
		if (filter == null) {
			filter = UMLPackage.eINSTANCE.getNamedElement();
		}
		this.scope = scope;
		this.filter = filter;
	}

	/**
	 * 
	 * @param name
	 *            a name
	 * @return
	 * 		all named element matching this name
	 * 
	 * @deprecated you should use {@link #getElementsByName(String)}
	 */
	@Deprecated
	public List<NamedElement> getNamedElements(String name) {
		List<NamedElement> returnedValues = new ArrayList<>();
		List<Object> obj = getElementsByName(name);
		for (Object current : obj) {
			if (current instanceof NamedElement) {
				returnedValues.add((NamedElement) current);
			}
		}
		return returnedValues;
	}

	/**
	 * Compute all name with all authorized namespace computation in UML
	 */
	protected void computeAllNames() {

		// compute names directly available in the scope
		computeNames(EMPTY_STRING, scope, true);

		// compute names related to enclosing namepaces of scope
		Namespace enclosingNamespace = scope.getNamespace();
		String prefix = EMPTY_STRING;
		while (enclosingNamespace != null) {
			prefix = EMPTY_STRING;
			computeNames(prefix, enclosingNamespace, false);
			enclosingNamespace = enclosingNamespace.getNamespace();
		}

		// Compute names related to the root context model
		Namespace model = scope.getModel();
		if (model == null) {
			model = scope;
		}

		if (filter.isSuperTypeOf(model.eClass())) {
			List<NamedElement> l = this.allNames.get(model.getName());
			if (l == null) { // i.e. no names have already been resolved in enclosed namespaces
				l = new ArrayList<>();
				l.add(model);
				this.allNames.put(model.getName(), l);
			}
		}
		StringBuilder builder = new StringBuilder(model.getName());
		builder.append(NamedElementUtil.QUALIFIED_NAME_SEPARATOR);
		computeNames(builder.toString(), model, false);

		// Build names corresponding to other available UML resources in the workspace
		List<Resource> resources = new ArrayList<>(scope.eResource().getResourceSet().getResources());// we duplicate the resource to avoid concurrent modification
		for (Resource resource : resources) {
			if (resource != scope.eResource() && resource instanceof UMLResource) {
				UMLResource umlResource = (UMLResource) resource;
				Model root = null;
				for (Iterator<EObject> i = umlResource.getAllContents(); i.hasNext() && root == null;) {
					EObject next = i.next();
					if (next instanceof Model) {
						root = (Model) next;
					}
				}
				if (root != null) {

					if (filter.isSuperTypeOf(root.eClass())) {
						List<NamedElement> l = this.allNames.get(root.getName());
						if (l == null) { // i.e. no names have already been resolved in enclosed namespaces
							l = new ArrayList<>();
							l.add(root);
							this.allNames.put(root.getName(), l);
						}
					}
					StringBuilder builder2 = new StringBuilder(root.getName());
					builder2.append(NamedElementUtil.QUALIFIED_NAME_SEPARATOR);
					computeNames(builder2.toString(), root, false);
				}
			}
		}
	}

	protected void computeNames(String prefix, Namespace scope, boolean ignoreAlreadyFoundNames) {
		computeNames(prefix, scope, ignoreAlreadyFoundNames, new HashSet<Namespace>());
	}

	/**
	 *
	 * @param prefix
	 * @param scope
	 * @param ignoreAlreadyFoundNames
	 * @param alreadyComputedNamespace
	 *            list of already visited Namespace to avoid loop on imported packages
	 */
	protected void computeNames(String prefix, Namespace scope, boolean ignoreAlreadyFoundNames, Set<Namespace> alreadyComputedNamespace) {
		alreadyComputedNamespace.add(scope);
		Set<String> preExistingKeys;
		if (ignoreAlreadyFoundNames) {
			preExistingKeys = new HashSet<>();
		} else {
			preExistingKeys = this.allNames.keySet();
		}
		// iterates members of the scope
		for (NamedElement member : scope.getMembers()) {
			List<String> memberNames = scope.getNamesOfMember(member);
			// iterates other names given to the current member in the context of this scope
			for (String memberName : memberNames) {
				// Checks if the name must be considered or not
				if (!preExistingKeys.contains(prefix + memberName) || !this.allNames.get(prefix + memberName).contains(member)) {
					// the second part of the previous if is to be able to found model::Class1 in this kind of model with scope==Property and filter : UML::Class
					// model
					// Class1
					// Property1
					// Class1
					// Class2


					if (filter.isSuperTypeOf(member.eClass())) {
						List<NamedElement> l = this.getNamedElements(prefix + memberName);
						l.add(member);
						this.allNames.put(prefix + memberName, l);
					}
				}
				if (member instanceof Namespace && !alreadyComputedNamespace.contains(member)) { // avoid loop on imported packages
					// Recursive call on the current member
					StringBuilder newPrefix = new StringBuilder(prefix);
					newPrefix.append(memberName);
					newPrefix.append(NamedElementUtil.QUALIFIED_NAME_SEPARATOR);
					computeNames(newPrefix.toString(), (Namespace) member, false, alreadyComputedNamespace);
				}
			}
		}
	}

	/**
	 * This allows to get the shortest qualified name for a named element in parameter.
	 * 
	 * @param namedElement
	 *            The named element.
	 * @return
	 * 		the shortest qualified to use for the element
	 */
	@Deprecated
	public List<String> getShortestQualifiedNames(NamedElement namedElement) {
		return getShortestQualifiedNames(namedElement, true);
	}

	/**
	 * This allows to get the shortest qualified name for a named element in parameter.
	 * 
	 * @param namedElement
	 *            The named element.
	 * @param manageDuplicate
	 *            Boolean to determinate if the duplicated shortest qualified names must be remove from the returned list.
	 * @return
	 * 		the shortest qualified to use for the element
	 */
	public List<String> getShortestQualifiedNames(NamedElement namedElement, final boolean manageDusplicate) {
		return NameResolutionUtils.getShortestQualifiedNames(namedElement, this.scope, manageDusplicate);
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getMatchingElements(java.lang.String)
	 *
	 * @param aString
	 * @return
	 */
	public List<Object> getMatchingElements(String aString) {
		if (this.allNames == null) {
			this.allNames = new HashMap<>();
			this.computeAllNames();
		}

		Collection<Object> elements = new HashSet<>();

		for (Entry<String, List<NamedElement>> current : this.allNames.entrySet()) {
			if (aString == null || aString.isEmpty() || current.getKey().startsWith(aString)) {
				elements.addAll(current.getValue());
			}
		}
		// to avoid to found the same element several time
		return new ArrayList<>(elements);
	}

	/**
	 * Apply the predicate to get matching elements from all Name Elements
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getMatchingElements(java.util.function.Predicate)
	 *
	 * @param p
	 * @return
	 * @since 3.3
	 */
	public List<?> getMatchingElements(Predicate predicate) {
		// Since Predicate isn't generic we need to handle the exception in filter
		Set<?> collect = (Set<?>) this.getAllNames().values().stream().flatMap(List::stream)
				.filter(namedElement -> {try {return predicate.test(namedElement);} catch (Exception e){return false;}}).collect(Collectors.toSet());
		return new ArrayList<>(collect);
	}	
	
	
	/**
	 * This method will return all named element that can be found in the given qualified name
	 * (should be named getNamedElementByQualifiedName()
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getElementsByName(java.lang.String)
	 *
	 * @param aString
	 * @return
	 */
	public List<Object> getElementsByName(String aString) {
		if (this.allNames == null) {
			this.allNames = new HashMap<>();
			this.computeAllNames();
		}
		List<NamedElement> namedElements = this.allNames.get(aString);
		List<Object> returnedValues = null;
		if (namedElements != null && namedElements.size() > 0) {
			returnedValues = new ArrayList<>(namedElements);
		}
		return returnedValues != null ? returnedValues : Collections.emptyList();
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getShortestQualifiedNames(java.lang.Object)
	 * @deprecated since 1.2.0
	 */
	@Deprecated
	public List<String> getShortestQualifiedNames(final Object element) {
		return getShortestQualifiedNames(element, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper#getShortestQualifiedNames(java.lang.Object, boolean)
	 */
	public List<String> getShortestQualifiedNames(final Object element, final boolean manageDuplicate) {
		if (element instanceof NamedElement) {
			return getShortestQualifiedNames((NamedElement) element, manageDuplicate);
		}
		return null;
	}



}
