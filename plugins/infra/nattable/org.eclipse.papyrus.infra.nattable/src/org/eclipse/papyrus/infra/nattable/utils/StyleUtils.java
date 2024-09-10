/*****************************************************************************
 * Copyright (c) 2014, 2017, 2019 CEA LIST and others.
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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 459220, 515737, 527496
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 550520
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NamedStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.Style;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StyledElement;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 * @author Vincent Lorenzo
 *
 */
public class StyleUtils {

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         a list with the hidden depth in the table. The returned values in never <code>null</code>;
	 */
	public static final List<Integer> getHiddenDepths(final Table table) {
		IntListValueStyle style = getHiddenDepthsValueStyle(table);
		if (style == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(style.getIntListValue());
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         a list with the hidden depth in the table. The returned values in never <code>null</code>;
	 */
	public static final List<Integer> getHiddenDepths(final INattableModelManager manager) {
		return getHiddenDepths(manager.getTable());
	}

	/**
	 *
	 * @param manager
	 *            the table
	 * @return
	 *         the style referencing the hidden category
	 */

	public static final IntListValueStyle getHiddenDepthsValueStyle(final Table table) {
		return (IntListValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getIntListValueStyle(), NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH);
	}

	/**
	 *
	 * @param manager
	 *            the table manager
	 * @return
	 *         the style referencing the hidden category
	 */
	public static final IntListValueStyle getHiddenDepthsValueStyle(final INattableModelManager manager) {
		return getHiddenDepthsValueStyle(manager.getTable());
	}

	/**
	 *
	 * @param manager
	 *            a table manager
	 * @param depth
	 *            a depth
	 * @return
	 *         <code>true</code> if the category must be hidden
	 */
	public static final boolean isHiddenDepth(final INattableModelManager manager, final int depth) {
		return isHiddenDepth(manager.getTable(), depth);
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @param depth
	 *            a depth
	 * @return
	 *         <code>true</code> if the category must be hidden
	 */
	public static final boolean isHiddenDepth(final Table table, final int depth) {
		List<Integer> hidden = getHiddenDepths(table);
		if (hidden.contains(depth)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param manager
	 *            the table manager
	 * @return
	 *         <code>true</code> if at least one filter is register on the currents columns
	 */
	public static final boolean hasAppliedFilter(final INattableModelManager manager) {
		for (Object current : manager.getColumnElementsList()) {
			if (current instanceof StyledElement) {
				StyledElement element = (StyledElement) current;
				NamedStyle style = element.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.FILTER_SYSTEM_ID);
				if (style != null) {
					return true;
				}
				style = element.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.FILTER_FORCED_BY_USER_ID);
				if (style != null) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * Get the value of a boolean named style in a given styled element.
	 *
	 * @param styledElement
	 *            The given styled element
	 * @param namedStyleString
	 *            The named style string
	 * @param defaultValue
	 *            The default value if the boolean value style does not exist
	 * @return The boolean value of the named style
	 * @since 4.0
	 */
	public static boolean getBooleanNamedStyleValue(final StyledElement styledElement, final String namedStyleString, final boolean defaultValue) {

		if (null != styledElement && null != namedStyleString) {
			// Get the relevant boolean named style
			final BooleanValueStyle booleanNamedStyle = (BooleanValueStyle) styledElement.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), namedStyleString);

			if (null != booleanNamedStyle) {
				return booleanNamedStyle.isBooleanValue();
			}
		}

		return defaultValue;
	}

	/**
	 * Get the value of a boolean named style in a given table.
	 *
	 * @param table
	 *            The given papyrus table
	 * @param namedStyleString
	 *            The named style string
	 * @return The boolean value of the named style
	 * @since 3.0
	 */
	public static boolean getBooleanNamedStyleValue(final Table table, final String namedStyleString) {
		boolean resultValue = false;

		if (null != table && null != namedStyleString) {
			// Get the relevant boolean named style from the table
			BooleanValueStyle booleanNamedStyle = (BooleanValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), namedStyleString);

			if (null == booleanNamedStyle) {
				final TableConfiguration config = table.getTableConfiguration();
				booleanNamedStyle = (BooleanValueStyle) config.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), namedStyleString);
			}

			resultValue = null != booleanNamedStyle && booleanNamedStyle.isBooleanValue();
		}

		return resultValue;
	}

	/**
	 * Set the value of a boolean named style in a given styled element.
	 *
	 * @param editingDomain
	 *            The editing domain
	 * @param styledElement
	 *            The given styled element
	 * @param namedStyleString
	 *            The named style string
	 * @param value
	 *            The boolean value to be set
	 * @since 4.0
	 */
	public static void setBooleanNamedStyle(final TransactionalEditingDomain editingDomain, final StyledElement styledElement, final String namedStyleString, final boolean value) {
		if (null != editingDomain && null != styledElement && null != namedStyleString) {
			// Get the relevant boolean named style from the style element
			BooleanValueStyle booleanNamedStyle = (BooleanValueStyle) styledElement.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), namedStyleString);

			if (null != booleanNamedStyle) {
				IElementEditService editService = ElementEditServiceUtils.getCommandProvider(booleanNamedStyle);
				SetRequest request = new SetRequest(editingDomain, booleanNamedStyle, NattablestylePackage.eINSTANCE.getBooleanValueStyle_BooleanValue(), value);

				if (editService.canEdit(request)) {
					Command command = GMFtoEMFCommandWrapper.wrap(editService.getEditCommand(request));
					editingDomain.getCommandStack().execute(command);
				}
			}
		}
	}

	/**
	 * Initialize the given named style if it is not created.
	 * If the named style has been already created, do nothing.
	 *
	 * @param editingDomain
	 *            The editing domain
	 * @param styledElement
	 *            The given styled element
	 * @param namedStyleString
	 *            The named style string
	 * @param defaultValue
	 *            The default value to be set
	 * @since 4.0
	 */
	public static void initBooleanNamedStyle(final TransactionalEditingDomain editingDomain, final StyledElement styledElement, final String namedStyleString, final boolean defaultValue) {
		if (null != editingDomain && null != styledElement && null != namedStyleString) {
			// Get the relevant named style
			BooleanValueStyle namedStyle = (BooleanValueStyle) styledElement.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), namedStyleString);

			// If it does not exist, initialize it, otherwise do nothing
			if (null == namedStyle) {
				namedStyle = NattablestyleFactory.eINSTANCE.createBooleanValueStyle();
				namedStyle.setName(namedStyleString);
				namedStyle.setBooleanValue(defaultValue);

				IElementEditService editService = ElementEditServiceUtils.getCommandProvider(styledElement);
				SetRequest request = new SetRequest(editingDomain, styledElement, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), namedStyle);
				if (editService.canEdit(request)) {
					Command command = GMFtoEMFCommandWrapper.wrap(editService.getEditCommand(request));
					editingDomain.getCommandStack().execute(command);
				}
			}
		}
	}

	/**
	 * Delete the given named style if it is already created.
	 * If the named style does not exist, do nothing.
	 *
	 * @param editingDomain
	 *            The editing domain
	 * @param styledElement
	 *            The given styled element to be deleted
	 * @param namedStyleString
	 *            The named style string
	 * @since 4.0
	 */
	public static void deleteBooleanNamedStyle(final TransactionalEditingDomain editingDomain, final StyledElement styledElement, final String namedStyleString) {
		if (null != editingDomain && null != styledElement && null != namedStyleString) {
			// Get the relevant named style
			BooleanValueStyle namedStyle = (BooleanValueStyle) styledElement.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), namedStyleString);

			// If it does exist, remove it, otherwise do nothing
			if (null != namedStyle) {

				IElementEditService editService = ElementEditServiceUtils.getCommandProvider(styledElement);
				DestroyElementRequest request = new DestroyElementRequest(editingDomain, namedStyle, false);
				if (editService.canEdit(request)) {
					Command command = GMFtoEMFCommandWrapper.wrap(editService.getEditCommand(request));
					editingDomain.getCommandStack().execute(command);
				}
			}
		}
	}

	/**
	 * @since 6.4
	 * @param dominantStyledElement
	 *            the styled element providing style to use in case of duplication with recessiveStyledElement
	 * @param recessiveStyledElement
	 *            the styled element providing style to erase in case of duplication with dominantStyledElement
	 *
	 * @return
	 *         the combination of the 2 styles
	 */
	public static final Collection<Style> getCombinedStyles(final StyledElement dominantStyledElement, final StyledElement recessiveStyledElement) {
		final EcoreUtil.Copier copier = new Copier();
		final Map<Object, Style> styles = new HashMap<>();
		for (final Style current : recessiveStyledElement.getStyles()) {
			if (current instanceof NamedStyle) {
				styles.put(((NamedStyle) current).getName(), (Style) copier.copy(current));
			} else {
				styles.put(current.eClass(), (Style) copier.copy(current));
			}
		}

		for (final Style current : dominantStyledElement.getStyles()) {
			if (current instanceof NamedStyle) {
				styles.put(((NamedStyle) current).getName(), (Style) copier.copy(current));
			} else {
				styles.put(current.eClass(), (Style) copier.copy(current));
			}
		}
		copier.clear();
		return styles.values();
	}
}
