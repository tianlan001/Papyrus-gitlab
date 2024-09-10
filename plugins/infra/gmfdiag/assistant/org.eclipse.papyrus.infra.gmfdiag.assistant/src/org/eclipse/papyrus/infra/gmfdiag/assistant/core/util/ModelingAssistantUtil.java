/*****************************************************************************
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.assistant.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.util.ProxyElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticElementAdapter;
import org.eclipse.papyrus.infra.services.edit.utils.ElementTypeUtils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.ibm.icu.text.Collator;

/**
 * Various utilities to support the implementation of modeling assistants.
 */
public class ModelingAssistantUtil {

	private static final Pattern VISUAL_ID_PATTERN = Pattern.compile("\\d{4,}"); //$NON-NLS-1$

	private ModelingAssistantUtil() {
		super();
	}

	/**
	 * Best-effort calculation of the element types matching the given {@code input}.
	 *
	 * @param provider
	 *            the modeling assistant provider in which context we are calculating element types. Must not be {@code null}
	 * @param input
	 *            some manifestation of an object being edited, usually either a diagram edit-part, a semantic model element, or an {@link IEditHelperContext}
	 *
	 * @return the elements, or an empty array if none can be determined (never {@code null})
	 */
	public static IElementType[] getElementTypes(ModelingAssistantProvider provider, Object input) {
		IElementType[] result;

		if (input instanceof IElementType) {
			result = new IElementType[] { (IElementType) input };
		} else if (input instanceof IEditHelperContext) {
			IEditHelperContext context = (IEditHelperContext) input;
			IElementType type = context.getElementType();
			if (type != null) {
				result = new IElementType[] { type };
			} else if (context.getClientContext() == null) {
				result = ElementTypeRegistry.getInstance().getAllTypesMatching(context.getEObject(), provider.getClientContext());
				if (provider.getClientContext() != ElementTypeUtils.getDefaultClientContext()) {
					IElementType[] more = ElementTypeRegistry.getInstance().getAllTypesMatching(context.getEObject(), ElementTypeUtils.getDefaultClientContext());
					result = (more.length == 0) ? result : (result.length == 0) ? more : ObjectArrays.concat(more, result, IElementType.class);
				}
			} else {
				result = ElementTypeRegistry.getInstance().getAllTypesMatching(context.getEObject(), context.getClientContext());
			}
		} else {
			EObject object = AdapterUtils.adapt(input, EObject.class, null);
			if (object != null) {
				List<IElementType> core = Arrays.asList(ElementTypeRegistry.getInstance().getAllTypesMatching(object, provider.getClientContext()));
				List<IElementType> resultList = Lists.newArrayListWithExpectedSize(core.size());

				if (provider.getClientContext() != ElementTypeUtils.getDefaultClientContext()) {
					// First add the client-context-specific types
					List<IElementType> more = Arrays.asList(ElementTypeRegistry.getInstance().getAllTypesMatching(object, ElementTypeUtils.getDefaultClientContext()));
					resultList.addAll(more);
				}

				// Then the core types
				resultList.addAll(core);

				if (!resultList.isEmpty()) {
					// Interleave hinted types, if necessary
					View view = AdapterUtils.adapt(input, View.class, null);
					if (view != null) {
						for (ListIterator<IElementType> iter = resultList.listIterator(); iter.hasNext();) {
							IElementType next = iter.next();
							IElementType hintedType = findHintedSpecializationType(next, view.getType(), object);
							if (hintedType != null) {
								iter.previous(); // Back up to insert
								iter.add(hintedType);
								iter.next(); // Step back over the base type again
							}
						}
					}
				}

				result = Iterables.toArray(resultList, IElementType.class);
			} else {
				result = new IElementType[0];
			}
		}

		return result;
	}

	static IElementType findHintedSpecializationType(IElementType type, String hint, EObject semanticElement) {
		IElementType result = null;

		for (IHintedType next : Iterables.filter(Arrays.asList(ElementTypeRegistry.getInstance().getSpecializationsOf(type.getId())), IHintedType.class)) {
			if (Objects.equal(next.getSemanticHint(), hint) && matches(next, semanticElement)) {
				result = next;
				break;
			}
		}

		return result;
	}

	static boolean matches(IElementType elementType, EObject element) {
		boolean result;

		if (elementType instanceof ISpecializationType) {
			ISpecializationType specializationType = (ISpecializationType) elementType;
			result = (specializationType.getMatcher() != null) ? specializationType.getMatcher().matches(element)
					: (specializationType.getMetamodelType() != null) ? matches(specializationType.getMetamodelType(), element)
							: false;
		} else {
			// Just a metamodel type
			result = (elementType.getEClass() != null) && elementType.getEClass().isInstance(element);
		}

		return result;
	}

	/**
	 * Collects all of the concrete subtypes of a given {@code supertype} that are referenced by a modeling assistant {@code provider}.
	 *
	 * @param supertype
	 *            a possible abstract (or not) element type
	 * @param provider
	 *            a modeling assistant provider from which to collect subtypes
	 * @param types
	 *            the accumulator of the resulting subtypes
	 */
	public static void collectAllConcreteSubtypes(IElementType supertype, ModelingAssistantProvider provider, Collection<? super IElementType> types) {
		for (IElementType next : provider.getElementTypes()) {
			if ((next.getEClass() != null) && !next.getEClass().isAbstract() && isSubtype(next, supertype)) {
				types.add(next);
			}
		}
	}

	/**
	 * Queries whether a postulated {@code subtype} is a subtype of its presumptive {@code supertype}.
	 *
	 * @param subtype
	 *            an element type
	 * @param supertype
	 *            another element type
	 * @return whether the {@code subtype} is, in fact, the same as or a subtype of the {@code supertype}
	 */
	public static boolean isSubtype(IElementType subtype, IElementType supertype) {
		return (subtype == supertype) || Arrays.asList(subtype.getAllSuperTypes()).contains(supertype);
	}

	/**
	 * Obtains the hinted specializations of an element type in the context of the given {@code provider}.
	 *
	 * @param supertype
	 *            the element type for which to find hinted specializations
	 * @param provider
	 *            the provider context
	 * @param host
	 *            the host context in which the {@code provider} is asked for modeling assistants
	 *
	 * @return the hinted specializations
	 */
	public static List<IHintedType> getHintedTypes(IElementType supertype, ModelingAssistantProvider provider, IAdaptable host) {
		List<IHintedType> result = getHintedTypes(supertype, provider);

		if (result.isEmpty() && (supertype instanceof ISpecializationType)) {
			// Generate some ephemeral types
			IElementType surrogate = ((ISpecializationType) supertype).getSpecializedTypes()[0];
			result = getHintedTypes(surrogate, provider);
			final boolean needQualifiers = result.size() > 1;

			for (ListIterator<IHintedType> iter = result.listIterator(); iter.hasNext();) {
				IHintedType next = iter.next();
				if ((next != surrogate) && !Strings.isNullOrEmpty(next.getSemanticHint())) {
					iter.set(ProxyElementType.create(supertype, next, needQualifiers));
				}
			}
		}

		return result;
	}

	private static List<IHintedType> getHintedTypes(IElementType supertype, ModelingAssistantProvider provider) {
		List<IHintedType> result = Lists.newArrayList();
		List<IElementType> banned = provider.getExcludedElementTypes();

		for (ISpecializationType next : ElementTypeRegistry.getInstance().getSpecializationTypes(provider.getClientContext())) {
			if ((next instanceof IHintedType) && next.isSpecializationOf(supertype) && !isSpecializationOfAny(next, banned)) {
				result.add((IHintedType) next);
			}
		}

		return result;
	}

	/**
	 * Queries whether an {@code elementType} is a specialization of any of a set of other {@code types}.
	 *
	 * @param elementType
	 *            an element type
	 * @param types
	 *            a set of possible supertypes of the {@code elementType}
	 * @return whether the {@code elementType} specializes any of the {@code types}
	 */
	public static boolean isSpecializationOfAny(ISpecializationType elementType, Iterable<? extends IElementType> types) {
		boolean result = false;

		Set<IElementType> allSupers = Sets.newHashSet(elementType.getAllSuperTypes());
		allSupers.add(elementType);

		for (Iterator<? extends IElementType> iter = types.iterator(); !result && iter.hasNext();) {
			result = allSupers.contains(iter.next());
		}

		return result;
	}

	public static Predicate<? super IElementType> notSpecializationOfAny(final Iterable<? extends IElementType> types) {
		return new Predicate<Object>() {
			@Override
			public boolean apply(Object input) {
				return !(input instanceof ISpecializationType) || !isSpecializationOfAny((ISpecializationType) input, types);
			}
		};
	}

	public static IElementType resolveSemanticType(IElementType type) {
		return (type instanceof IProxyElementType) ? ((IProxyElementType) type).resolveSemanticType() : type;
	}

	public static IHintedType resolveVisualType(IElementType type) {
		return (type instanceof IProxyElementType) ? ((IProxyElementType) type).resolveVisualType() : (type instanceof IHintedType) ? (IHintedType) type : null;
	}

	/**
	 * Determines if the passed hint is a visualID
	 * There is now no reliable way for testing if a semantic hint represents a visual id
	 * 
	 * @param hint
	 * @return
	 * @deprecated This function should not be relied on. Other ways of determining
	 *             if an element type represents a visual element should be used
	 */
	@Deprecated
	public static boolean isVisualID(String hint) {
		hint = Strings.nullToEmpty(hint);
		return VISUAL_ID_PATTERN.matcher(hint).matches() ||
				hint.contains("Diagram") ||
				hint.contains("Shape") ||
				hint.contains("Edge") ||
				hint.contains("Compartment") ||
				hint.contains("Label");
	}

	/**
	 * Filters a set of connection types for only those that we think we could actually create in the current diagram context and
	 * sorts them alphabetically.
	 *
	 * @param provider
	 *            the contextual assistant provider
	 * @param elementTypes
	 *            connection element types matched in the assistant model
	 * @param connectionEnd
	 *            the context of the element creation, being one of the edit parts at its ends
	 *
	 * @return the (possibly) reduced set of connection types that we think we could create, sorted
	 */
	public static EList<IElementType> filterConnectionTypes(ModelingAssistantProvider provider, Set<IElementType> elementTypes, IAdaptable connectionEnd) {
		Iterable<IElementType> result = Iterables.filter(elementTypes, notSpecializationOfAny(provider.getExcludedElementTypes()));

		final View endView = AdapterUtils.adapt(connectionEnd, View.class, null);
		if (endView != null) {
			result = Iterables.filter(result, new Predicate<IElementType>() {
				@Override
				public boolean apply(IElementType input) {
					IAdaptable semanticAdapter = new SemanticElementAdapter(input);
					String semanticHint = (input instanceof IHintedType) ? ((IHintedType) input).getSemanticHint() : ""; //$NON-NLS-1$
					return ViewService.getInstance().provides(Edge.class, semanticAdapter, (View) endView.eContainer(), semanticHint, -1, true, null);
				}
			});
		}

		IElementType[] array = Iterables.toArray(result, IElementType.class);
		Arrays.sort(array, alphabetical());
		return new BasicEList.UnmodifiableEList<>(array.length, array);
	}

	public static Function<IElementType, String> primaryNameFunction() {
		return new Function<IElementType, String>() {
			@Override
			public String apply(IElementType input) {
				return (input instanceof IProxyElementType) ? apply(((IProxyElementType) input).resolveSemanticType()) : input.getDisplayName();
			}
		};
	}

	public static Function<IElementType, Integer> visualIDFunction() {
		return new Function<IElementType, Integer>() {
			@Override
			public Integer apply(IElementType input) {
				int result = 0;

				if (input instanceof IHintedType) {
					String hint = ((IHintedType) input).getSemanticHint();
					if (!Strings.isNullOrEmpty(hint) && CharMatcher.digit().matchesAllOf(hint)) {
						result = Integer.parseInt(hint);
					}
				}

				return result;
			}
		};
	}

	public static Ordering<IElementType> alphabetical() {
		final Collator collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
		return Ordering.from(collator).onResultOf(primaryNameFunction()).compound(Ordering.natural().onResultOf(visualIDFunction()));
	}
}
