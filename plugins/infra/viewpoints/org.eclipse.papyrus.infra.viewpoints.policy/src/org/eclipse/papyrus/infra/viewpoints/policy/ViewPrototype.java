/*****************************************************************************
 * Copyright (c) 2013, 2017, 2019, 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Christian W. Damus - bugs 474467, 493375, 527580
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550568
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 560219
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.architecture.representation.ModelRule;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.swt.graphics.Image;


/**
 * Represents a prototype of view in Papyrus
 *
 * @author Laurent Wouters
 */
public abstract class ViewPrototype {
	/**
	 * Singleton for unavailable views
	 */
	public static final ViewPrototype UNAVAILABLE_VIEW = new UnavailableViewPrototype("View", "View"); //$NON-NLS-1$ //$NON-NLS-2$
	/**
	 * ID of the extension point for Papyrus diagrams
	 */
	protected static final String EXTENSION_ID = "org.eclipse.papyrus.infra.viewpoints.policy.viewType"; //$NON-NLS-1$

	/**
	 * The collection of helpers
	 */
	private static final Collection<IViewTypeHelper> HELPERS = getCommandHelpers();

	/**
	 * Retrieves the helpers from the extensions
	 *
	 * @return The helpers
	 */
	private static Collection<IViewTypeHelper> getCommandHelpers() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		List<IConfigurationElement> elements = new ArrayList<>(Arrays.asList(registry.getConfigurationElementsFor(EXTENSION_ID)));

		// Only the view helper elements
		for (Iterator<IConfigurationElement> iter = elements.iterator(); iter.hasNext();) {
			if (!"helper".equals(iter.next().getName())) { //$NON-NLS-1$
				iter.remove();
			}
		}

		// Sort by priority
		Collections.sort(elements, helperConfigComparator());

		Collection<IViewTypeHelper> result = new ArrayList<>();
		for (IConfigurationElement element : elements) {
			try {
				IViewTypeHelper instance = (IViewTypeHelper) element.createExecutableExtension("class"); //$NON-NLS-1$
				if (instance != null) {
					result.add(instance);
				}
			} catch (CoreException e) {
				Activator.log.log(e.getStatus());
			}
		}

		return result;
	}

	/**
	 * Determines whether the given object is a supported view object
	 *
	 * @param object
	 *            The object to inspect
	 * @return <code>true</code> if the object is a supported view
	 */
	public static boolean isViewObject(EObject object) {
		for (IViewTypeHelper helper : HELPERS) {
			if (helper.isSupported(object)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the prototype for the given representationKind element
	 *
	 * @param config
	 *            The representationKind element
	 * @return The prototype
	 */
	public static ViewPrototype get(PapyrusRepresentationKind representationKind) {
		for (IViewTypeHelper helper : HELPERS) {
			if (helper.isSupported(representationKind.eClass())) {
				ViewPrototype proto = helper.getPrototypeFor(representationKind);
				if (proto != null) {
					return proto;
				}
			}
		}
		return UNAVAILABLE_VIEW;
	}

	/**
	 * Gets the prototype for the the given object representing a view
	 *
	 * @param view
	 *            The view for which a prototype is expected
	 * @return The view's prototype, never {@code null} but possibly {@link #isUnavailable() unavailable}
	 */
	public static ViewPrototype get(EObject view) {
		for (IViewTypeHelper helper : HELPERS) {
			if (helper.isSupported(view)) {
				ViewPrototype proto = helper.getPrototypeOf(view);
				if (proto != null) {
					return proto;
				}
			}
		}
		return UNAVAILABLE_VIEW;
	}

	/**
	 * Gets the prototype for the given implementation identifier
	 *
	 * @param implem
	 *            The implementation ID
	 * @param owner
	 *            The owner
	 * @param root
	 *            The root element
	 * @return The prototype
	 */
	public static ViewPrototype get(PolicyChecker policy, String implem, EObject owner, EObject root) {
		PapyrusRepresentationKind view = policy.getRepresentationKindFrom(implem, owner, root);
		if (view == null) {
			// The given implementation has been inhibited by the current viewpoint representationKind
			return UNAVAILABLE_VIEW;
		}
		return get(view);
	}


	/**
	 * The representation kind of this view
	 */
	protected final PapyrusRepresentationKind representationKind;

	/**
	 * Constructor.
	 *
	 * @param representationKind
	 *            The representation kind element
	 */
	protected ViewPrototype(PapyrusRepresentationKind representationKind) {
		this.representationKind = representationKind;
	}

	/**
	 * Gets the representation kind for this view prototype
	 *
	 * @return The representation kind
	 */
	public PapyrusRepresentationKind getRepresentationKind() {
		return representationKind;
	}

	/**
	 * Queries whether the prototype is unavailable, effectively a <em>Null Object</em>.
	 *
	 * @return whether the prototype is a non-view prototype
	 *
	 * @since 1.2
	 */
	public boolean isUnavailable() {
		return false;
	}

	/**
	 * Gets the ID of the implementation of this prototype
	 *
	 * @return The implementation ID
	 */
	public String getImplementation() {
		return representationKind.getImplementationID();
	}

	/**
	 * Gets the label
	 *
	 * @return The label
	 */
	public String getLabel() {
		return representationKind.getName();
	}

	/**
	 * Gets the full label that is qualified with its possible root elements
	 *
	 * @return The full label
	 */
	public String getFullLabel() {
		StringBuilder builder = new StringBuilder(getLabel());
		builder.append(" for "); //$NON-NLS-1$
		boolean first = true;
		for (ModelRule rule : representationKind.getModelRules()) {
			if (rule.getStereotypes() != null && rule.getStereotypes().size() > 0) {
				for (EClass stereotype : rule.getStereotypes()) {
					if (!first) {
						builder.append(", "); //$NON-NLS-1$
						first = false;
					}
					builder.append(stereotype.getName());
				}
			} else if (rule.getElement() != null) {
				if (!first) {
					builder.append(", "); //$NON-NLS-1$
					first = false;
				}
				builder.append(rule.getElement().getName());
			} else {
				continue;
			}
		}
		return builder.toString();
	}

	/**
	 * Gets the qualified name of this prototype
	 *
	 * @return The qualified name
	 */
	public String getQualifiedName() {
		ArchitectureDescriptionLanguage lang = (ArchitectureDescriptionLanguage) representationKind.eContainer();
		return lang.getName() + " :: " + getLabel(); //$NON-NLS-1$
	}

	/**
	 * Gets the URI of this prototype's icon
	 *
	 * @return The icon's URI
	 */
	public String getIconURI() {
		return representationKind.getIcon();
	}

	/**
	 * Gets the URI of this prototype's grayed icon.
	 *
	 * @return The grayed icon's URI.
	 * @since 3.1
	 */
	public String getGrayedIconURI() {
		return representationKind.getGrayedIcon();
	}

	/**
	 * Gets the image descriptor of this prototype's icon
	 *
	 * @return The icon's descriptor
	 */
	public ImageDescriptor getIconDescriptor() {
		URL url = null;
		try {
			url = new URL(getIconURI());
		} catch (MalformedURLException e) {
			Activator.log.error("Cannot load icon at URI " + getIconURI(), e); //$NON-NLS-1$
		}
		return ImageDescriptor.createFromURL(url);
	}

	/**
	 * Gets the image descriptor of this prototype's grayed icon.
	 *
	 * @return The grayed icon's descriptor.
	 * @since 3.1
	 */
	public ImageDescriptor getGrayedIconDescriptor() {
		URL url = null;
		try {
			url = new URL(getGrayedIconURI());
		} catch (MalformedURLException e) {
			Activator.log.error("Cannot load grayed icon at URI " + getIconURI(), e); //$NON-NLS-1$
		}
		return ImageDescriptor.createFromURL(url);
	}

	/**
	 * Gets the image of this prototype's icon
	 *
	 * @return The icon's image
	 */
	public Image getIcon() {
		return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(getIconDescriptor());
	}

	/**
	 * Gets the image of this prototype's grayed icon.
	 *
	 * @return The grayed icon's image.
	 * @since 3.1
	 */
	public Image getGrayedIcon() {
		return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(getGrayedIconDescriptor());
	}

	/**
	 * Determines whether the instances of this prototype can change owners
	 *
	 * @return <code>true</code> if the owner is reassignable
	 */
	public abstract boolean isOwnerReassignable();

	/**
	 * Create a new view from this prototype with the given owner
	 *
	 * @param owner
	 *            The new view's owner
	 * @return <code>true</code> if the instantiation succeeded
	 */
	public abstract boolean instantiateOn(EObject owner);

	/**
	 * Create a new view from this prototype with the given owner and name
	 *
	 * @param owner
	 *            The new view's owner
	 * @param name
	 *            The new view's name
	 * @return <code>true</code> if the instantiation succeeded
	 */
	public abstract boolean instantiateOn(EObject owner, String name);

	/**
	 * Create a new view from this prototype with the given owner and name, and open it if required
	 *
	 * @param owner
	 * @param name
	 * @param openCreatedView
	 * @return
	 *         <code>true</code> if the diagram creation succeed, <code>false</code> otherwise
	 * @since 3.2
	 */
	public boolean instantiateOn(final EObject owner, final String name, final boolean openCreatedView) {
		return instantiateOn(owner, name);
	}

	/**
	 * Gets the command for moving the given view to a new owner (target)
	 *
	 * @param view
	 *            The view to be moved
	 * @param target
	 *            The target owner
	 * @return The command, or <code>null</code> if the change is not allowed
	 */
	public abstract Command getCommandChangeOwner(EObject view, EObject target);

	/**
	 * Gets the command for changing the root element of the given view to a new element (target)
	 *
	 * @param view
	 *            The view to change root
	 * @param target
	 *            The target element
	 * @return The command, or <code>null</code> if the change is not allowed
	 */
	public abstract Command getCommandChangeRoot(EObject view, EObject target);

	/**
	 * Gets the object owning the given view
	 *
	 * @param view
	 *            The view
	 * @return The view's owner
	 */
	public abstract EObject getOwnerOf(EObject view);

	/**
	 * Gets the object which is the root element of the given view
	 *
	 * @param view
	 *            The view
	 * @return The views's root element
	 */
	public abstract EObject getRootOf(EObject view);

	/**
	 * Represents a comparator of prototypes
	 *
	 * @author Laurent Wouters
	 */
	public static class Comp implements Comparator<ViewPrototype> {
		private static final Map<Class<? extends PapyrusRepresentationKind>, Integer> priorities = new HashMap<>();
		{
			priorities.put(PapyrusDiagram.class, 1);
			priorities.put(PapyrusTable.class, 2);
		}

		private static Integer getPriority(ViewPrototype proto) {
			for (Map.Entry<Class<? extends PapyrusRepresentationKind>, Integer> entry : priorities.entrySet()) {
				if (entry.getKey().isAssignableFrom(proto.representationKind.getClass())) {
					return entry.getValue();
				}
			}
			return 0;
		}

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(ViewPrototype proto1, ViewPrototype proto2) {
			Integer p1 = getPriority(proto1);
			Integer p2 = getPriority(proto2);
			if (p1.equals(p2)) {
				return (proto1.getLabel().compareTo(proto2.getLabel()));
			} else {
				return p1.compareTo(p2);
			}
		}
	}

	/**
	 * Gets the number of views of this type owned by a given object
	 *
	 * @param element
	 *            The object to count views for
	 * @param prototype
	 *            The prototype of view to counts
	 * @return The number of owned views
	 */
	public int getOwnedViewCount(EObject element) {
		int count = 0;
		Iterator<EObject> roots = NotationUtils.getNotationRoots(element);
		if (roots == null) {
			return count;
		}
		while (roots.hasNext()) {
			EObject view = roots.next();
			ViewPrototype proto = ViewPrototype.get(view);
			if (this == proto) {
				EObject owner = proto.getOwnerOf(view);
				if (owner == element) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Gets the number of views of this type on a given object
	 *
	 * @param element
	 *            The object to count views on
	 * @param prototype
	 *            The prototype of view to counts
	 * @return The number of views on the object
	 */
	public int getViewCountOn(EObject element) {
		int count = 0;
		Iterator<EObject> roots = NotationUtils.getNotationRoots(element);
		if (roots == null) {
			return count;
		}
		while (roots.hasNext()) {
			EObject view = roots.next();
			ViewPrototype proto = ViewPrototype.get(view);
			if (this == proto) {
				EObject root = proto.getRootOf(view);
				if (root == element) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Comparator to order view-type helper configurations from highest to
	 * lowest priority.
	 *
	 * @return the descending priority comparator
	 */
	private static Comparator<IConfigurationElement> helperConfigComparator() {
		return new Comparator<IConfigurationElement>() {
			private Map<IConfigurationElement, Integer> priorities = new IdentityHashMap<>();

			@Override
			public int compare(IConfigurationElement o1, IConfigurationElement o2) {
				// Sort from highest priority to lowest
				return getPriority(o2) - getPriority(o1);
			}

			int getPriority(IConfigurationElement element) {
				Integer result = priorities.get(element);

				if (result == null) {
					result = 0;
					String priorityString = element.getAttribute("priority"); //$NON-NLS-1$
					if (priorityString != null) {
						try {
							result = Integer.parseInt(priorityString);
						} catch (Exception e) {
							result = 0;
						}
					}
					priorities.put(element, result);
				}

				return result;
			}
		};
	}
}
