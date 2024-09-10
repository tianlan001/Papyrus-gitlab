/*****************************************************************************
 * Copyright (c) 2009, 2014 CEA LIST and others.
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
 *  Yann TANGUY (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 440263
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 530155
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils;

import java.util.Collection;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NameElementNamingStrategyPreferenceInitializer;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NamedElementIndexNamingStrategyEnum;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.util.UMLSwitch;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

/**
 * Utility class for <code>org.eclipse.uml2.uml.NamedElement</code><BR>
 */
public class NamedElementUtil {

	public static final String COPY_OF = "CopyOf";

	public static final String QUALIFIED_NAME_SEPARATOR = "::";

	private static final String PUBLIC_STRING = "+";

	private static final String PROTECTED_STRING = "#";

	private static final String PRIVATE_STRING = "-";

	private static final String PACKAGE_STRING = "~";

	private static final UMLSwitch<Boolean> IS_AUTONAMED = getIsAutoNamedSwitch();

	/**
	 * A helper method to calculate the max depth of an element
	 *
	 * @param the
	 *            named element
	 * @return the maximum depth found in qualified name
	 */
	public static int getQualifiedNameMaxDepth(NamedElement namedElement) {
		int d = 0;
		String s = namedElement.getQualifiedName();
		if (s == null) {
			return 0;
		}
		int n = 0;
		while ((n = s.indexOf(QUALIFIED_NAME_SEPARATOR, n)) != -1) {
			n += 2;
			d++;
		}
		return d;
	}

	/**
	 * generate a default name for the eobject in parameter the format is :
	 * "eclassName"+"max(elementOfTheSameName in the container)" + 1
	 *
	 * @param newElement
	 * @return
	 */
	public static String getDefaultNameWithIncrement(EObject newElement) {
		if (newElement.eContainer() != null) {
			return getDefaultNameWithIncrement(newElement, newElement.eContainer().eContents());
		}
		return null;
	}

	/**
	 * generate a default name for the eobject in parameter the format is :
	 * "eclassName"+"max(elementOfTheSameName in the container)" + 1 the method checks already
	 * existing element in contents parameter
	 *
	 * @param newElement
	 */
	public static String getDefaultNameWithIncrement(EObject newElement, Collection<?> contents) {
		return getDefaultNameWithIncrement("", newElement, contents);
	}

	public static String getDefaultCopyNameWithIncrement(NamedElement namedElement, Collection<?> contents) {
		// A NamedElement with empty string for a name is logically unnamed, so any copy of it should also be unnamed
		String rootName = Strings.emptyToNull(namedElement.getName());
		if (rootName != null) {
			for (Object o : contents) {
				if (o instanceof EObject) {
					String name = EMFCoreUtil.getName((EObject) o);
					if (rootName.equals(name)) {
						String newName = NLS.bind(COPY_OF + "_{0}_", rootName);
						return NamedElementUtil.getDefaultNameWithIncrementFromBase(newName, contents);
					}
				}
			}
		}
		return rootName;
	}

	public static String getDefaultNameWithIncrement(String prefix, EObject newElement, Collection<?> contents) {
		if (prefix == null) {
			prefix = "";
		}
		return getDefaultNameWithIncrementFromBase(prefix + newElement.eClass().getName(), contents, newElement, "");
	}

	public static String getDefaultNameWithIncrementFromBase(String base, Collection<?> contents) {
		return getDefaultNameWithIncrementFromBase(base, contents, null, ""); //$NON-NLS-1$
	}

	public static String getDefaultNameWithIncrementFromBase(String base, Collection<?> contents, EObject elementToRename, String separator) {
		return (elementToRename != null) ? //
				getDefaultNameSwitch(base, contents, separator).doSwitch(elementToRename).orNull()
				: //
				computeDefaultNameWithIncrementFromBase(base, contents, elementToRename, separator);
	}

	private static UMLSwitch<Optional<String>> getDefaultNameSwitch(final String base, final Collection<?> contents, final String separator) {
		return new UMLSwitch<Optional<String>>() {

			@Override
			public Optional<String> defaultCase(EObject object) {
				return Optional.fromNullable(computeDefaultNameWithIncrementFromBase(base, contents, object, separator));
			}

			@Override
			public Optional<String> casePseudostate(Pseudostate object) {
				String base = object.getKind().getLiteral();
				base = base.substring(0, 1).toUpperCase() + base.substring(1);

				return Optional.fromNullable(computeDefaultNameWithIncrementFromBase(base, contents, object, separator));
			}

			@Override
			public Optional<String> caseRelationship(Relationship object) {
				return Optional.absent();
			}

			@Override
			public Optional<String> caseAssociation(Association object) {
				return Optional.absent();
			}

			@Override
			public Optional<String> caseAssociationClass(AssociationClass object) {
				return defaultCase(object);
			}

			@Override
			public Optional<String> caseActivityEdge(ActivityEdge object) {
				return Optional.absent();
			}

			@Override
			public Optional<String> caseTransition(Transition object) {
				return Optional.absent();
			}

			@Override
			public Optional<String> caseGeneralOrdering(GeneralOrdering object) {
				return Optional.absent();
			}
		};
	}

	private static UMLSwitch<Boolean> getIsAutoNamedSwitch() {
		return new UMLSwitch<Boolean>() {

			@Override
			public Boolean defaultCase(EObject object) {
				return Boolean.TRUE;
			}

			@Override
			public Boolean caseRelationship(Relationship object) {
				return Boolean.FALSE;
			}

			@Override
			public Boolean caseAssociation(Association object) {
				return Boolean.FALSE;
			}

			@Override
			public Boolean caseAssociationClass(AssociationClass object) {
				return Boolean.TRUE;
			}

			@Override
			public Boolean caseActivityEdge(ActivityEdge object) {
				return Boolean.FALSE;
			}

			@Override
			public Boolean caseTransition(Transition object) {
				return Boolean.FALSE;
			}

			@Override
			public Boolean caseGeneralOrdering(GeneralOrdering object) {
				return Boolean.FALSE;
			}
		};
	}

	public static boolean isAutoNamed(EObject element) {
		return IS_AUTONAMED.doSwitch(element);
	}

	static String computeDefaultNameWithIncrementFromBase(String base, Collection<?> contents, EObject elementToRename, String separator) {
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		final String preferenceValue = store.getString(NameElementNamingStrategyPreferenceInitializer.NAMED_ELEMENT_INDEX_INITIALIZATION);
		return computeDefaultNameWithIncrementFromBase(base, contents, elementToRename, separator, preferenceValue);
	}

	/**
	 * @param base
	 * @param contents
	 * @param elementToRename
	 * @param separator
	 * @param preferenceValue
	 * @return
	 */
	public static String computeDefaultNameWithIncrementFromBase(String base, Collection<?> contents, EObject elementToRename, String separator, final String preferenceValue) {
		if (null != preferenceValue) {
			if (elementToRename instanceof NamedElement && null != ((NamedElement) elementToRename).getName() && false == ((NamedElement) elementToRename).getName().isEmpty()) {
				if (null != elementToRename.eContainer()) {// not really equivalent to the contents.contains(elementToRename), but it works most of the time
					return ((NamedElement) elementToRename).getName();
				}
			}
			if (NamedElementIndexNamingStrategyEnum.NO_INDEX_INITIALIZATION.getName().equals(preferenceValue)) {
				return base;
			}
			if (NamedElementIndexNamingStrategyEnum.QUICK_INDEX_INITIALIZATION.getName().equals(preferenceValue)) {
				final StringBuilder builder = new StringBuilder(base);
				builder.append(separator);
				builder.append(contents.size());
				//builder.append(1);// most of the time, the element to initialized is already in a list, so the +1 is not required
				return builder.toString();
			}
			// in other case, we continue;
		}
		if (elementToRename != null) {
			// Is this even an element that we should auto-name?
			if (!isAutoNamed(elementToRename)) {
				return null;
			}

			// Do not change the name if it's already present in the contents collection and already has a name
			if (contents.contains(elementToRename)) {
				if (elementToRename instanceof ENamedElement) {
					ENamedElement eNamedElement = (ENamedElement) elementToRename;
					if (eNamedElement.getName() != null) {
						return eNamedElement.getName();
					}
				}
				// UML specific
				if (elementToRename instanceof NamedElement) {
					NamedElement namedElement = (NamedElement) elementToRename;
					if (namedElement.getName() != null) {
						return namedElement.getName();
					}
				}
			}
		}

		int nextNumber = 1;

		// specific value for properties. default name is Attribute.
		// Note: That could be set in a specific advice rather than the default initializer.
		// Note2: The name of operations / properties could be set by default with a first lower case letter.
		if ("property".equalsIgnoreCase(base)) {
			base = "Attribute";
		}

		for (Object o : contents) {
			if (o instanceof EObject && o != elementToRename) {
				String name = EMFCoreUtil.getName((EObject) o);
				if (name != null && name.startsWith(base)) {
					String end = name.substring(base.length());
					int nextNumberTmp = 1;

					try {
						nextNumberTmp = Integer.parseInt(end) + 1;
					} catch (NumberFormatException ex) {
					}

					if (nextNumberTmp > nextNumber) {
						nextNumber = nextNumberTmp;
					}
				}
			}
		}

		return base + separator + nextNumber;
	}

	/**
	 * Give the visibility of the {@link NamedElement} as a string, as defined in the UML2 standard.
	 *
	 * @return A String representing the visibility of the {@link NamedElement}. Possible values:
	 *         <ul>
	 *         <li>public: <code>"+"</code>
	 *         <li>private: <code>"-"</code>
	 *         <li>protected: <code>"#"</code>
	 *         <li>package: <code>"~"</code>
	 *         </ul>
	 */
	public static String getVisibilityAsSign(NamedElement element) {
		String vKindValue = "";

		switch (element.getVisibility().getValue()) {
		case org.eclipse.uml2.uml.VisibilityKind.PUBLIC:
			vKindValue = PUBLIC_STRING;
			break;
		case org.eclipse.uml2.uml.VisibilityKind.PRIVATE:
			vKindValue = PRIVATE_STRING;
			break;
		case org.eclipse.uml2.uml.VisibilityKind.PACKAGE:
			vKindValue = PACKAGE_STRING;
			break;
		case org.eclipse.uml2.uml.VisibilityKind.PROTECTED:
			vKindValue = PROTECTED_STRING;
			break;
		}
		return vKindValue;
	}

	/**
	 * Returns the name of an element, given its qualified name
	 *
	 * @param qualifiedName
	 *            the qualified name of the element
	 * @return the name of the element. It shall never be <code>null</code>.
	 */
	public static String getNameFromQualifiedName(String qualifiedName) {
		String name = qualifiedName.substring(qualifiedName.lastIndexOf(NamedElement.SEPARATOR) + NamedElement.SEPARATOR.length());
		return (name != null) ? name : "";
	}

	/**
	 *
	 * @param childQualifiedName
	 *            the qualifiedName of an element
	 * @return
	 * 		the qualified name of its parent
	 */
	public static String getParentQualifiedName(final String childQualifiedName) {
		final String childName = getNameFromQualifiedName(childQualifiedName);
		final String parentQualifiedName = childQualifiedName.substring(0, childQualifiedName.length() - (NamedElement.SEPARATOR.length() + childName.length()));
		return (parentQualifiedName != null) ? parentQualifiedName : "";
	}


	public static String getName(NamedElement element) {
		if (element.getName() != null) {
			return element.getName();
		} else {
			return (NamedElementUtil.getDefaultNameWithIncrement(element));
		}
	}
}
