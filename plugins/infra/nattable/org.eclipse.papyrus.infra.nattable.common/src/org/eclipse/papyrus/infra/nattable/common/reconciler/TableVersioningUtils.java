/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.reconciler;

import java.util.AbstractMap;
import java.util.StringTokenizer;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * This class provide several convenience methods to tag a table with a version number 
 * or retrieve this version number.
 * @since 3.0
 */
public class TableVersioningUtils {

	/**
	 * Returns the "current" table version. Tables with this version don't require the reconciliation until the Papyrus version updates in such a
	 * way that some tables needs reconciliation.
	 * <p/>
	 * The current value returned by this method is "1.3.0".
	 * <p/>
	 * The value itself, howewer, should NOT be used outside of this package to avoid weird dependency issues. Instead, external code should 
	 * use {@link TableVersioningUtils#stampCurrentVersion(Table)} and
	 * {@link TableVersioningUtils#createStampCurrentVersionCommand(Table)}.
	 * <p/>
	 * This method is intentinally NOT a constant but indeed the method. This method is intentionally private and should NOT be made public.
	 */
	private static String CURRENT_TABLE_VERSION() {
		return "1.3.0"; //$NON-NLS-1$
	}

	/**
	 * Directly marks the given table as either created with "current" Papyrus version or already reconciled to the "current" Papyrus version.
	 * <p/>
	 * It is guaranteed that {@link TableVersioningUtils#isOfCurrentPapyrusVersion(Table)} returns true immediately after the call to this method.
	 *
	 * @param table
	 *            table to stamp as "current"
	 */
	public static void stampCurrentVersion(Table table) {
		setCompatibilityVersion(table, CURRENT_TABLE_VERSION());
	}

	/**
	 * Returns the command that will mark the given table as either created with "current" Papyrus version or already reconciled to the "current"
	 * Papyrus version.
	 * <p/>
	 * It is guaranteed that {@link TableVersioningUtils#isOfCurrentPapyrusVersion(Table)} will returns true immediately after the execution of the command.
	 *
	 * @param table
	 * @return the command that is guaranteed to be not null and executable
	 */
	public static ICommand createStampCurrentVersionCommand(Table table) {
		EAnnotation annot = findOrCreateCompatibilityAnnotation(table);
		if (annot.eContainer() == null) {
			annot.getDetails().put(COMPATIBILITY_VERSION, CURRENT_TABLE_VERSION());
			return new SetValueCommand(new SetRequest(table, EcorePackage.eINSTANCE.getEModelElement_EAnnotations(), annot));
		} else {
			return new SetValueCommand(new SetRequest(annot, EcorePackage.eINSTANCE.getEAnnotation_Details(), new AbstractMap.SimpleEntry<String, String>(COMPATIBILITY_VERSION, CURRENT_TABLE_VERSION())));
		}
	}

	/**
	 * The name of the {@link EAnnotation} that defines actual table version.
	 */
	public static final String VERSION_ANNOTATION = "http://www.eclipse.org/papyrus/infra/nattable/version";//$NON-NLS-1$

	/**
	 * The name of the {@link EAnnotation} that defines actual table version.
	 * <p/>
	 * The value for this constant is "version", it is intentionally the same as been used for SysML tables versioning.
	 */
	public static final String COMPATIBILITY_VERSION = "version";//$NON-NLS-1$

	/**
	 * The version constant for the tables that does not have a {@link TableVersioningUtils#COMPATIBILITY_VERSION} annotation.
	 * It may be assumed that these tables had been created on or before Papyrus 1.0.
	 */
	public static final String UNDEFINED_VERSION = "undefined";//$NON-NLS-1$

	private static final String DELIM_VERSION = ".";//$NON-NLS-1$

	/**
	 * Get the table compatibility version.
	 *
	 * @param view
	 *            the table
	 * @return the compatibility version or {@link TableVersioningUtils#UNDEFINED_VERSION} if none stored. Never returns <code>null</code>.
	 */
	public static String getCompatibilityVersion(Table table) {
		EAnnotation annot = findOrCreateCompatibilityAnnotation(table);
		return annot.eContainer() == null ? UNDEFINED_VERSION : annot.getDetails().get(COMPATIBILITY_VERSION);
	}

	/**
	 * Set the table compatibility version.
	 *
	 * @param table
	 *            the table
	 * @param version
	 *            the compatibility version
	 */
	public static void setCompatibilityVersion(Table table, String version) {
		EAnnotation annot = findOrCreateCompatibilityAnnotation(table);
		annot.getDetails().put(COMPATIBILITY_VERSION, version);
		if (annot.eContainer() == null) {
			table.getEAnnotations().add(annot);
		}
	}

	/**
	 * Finds the existing annotation with {@link TableVersioningUtils#VERSION_ANNOTATION} name or creates a new one if none existing found.
	 * If a new annotation is created, it's not attached to the table
	 * 
	 * @param table
	 * @return the existing or a new annotation with {@link TableVersioningUtils#VERSION_ANNOTATION} name.
	 */
	private static EAnnotation findOrCreateCompatibilityAnnotation(Table table) {
		EAnnotation annot = table.getEAnnotation(VERSION_ANNOTATION);
		if (annot == null) {
			annot = EcoreFactory.eINSTANCE.createEAnnotation();
			annot.setSource(VERSION_ANNOTATION);
			annot.getDetails().put(COMPATIBILITY_VERSION, CURRENT_TABLE_VERSION());
		}
		return annot;
	}

	/**
	 * Checks whether the table is of "current", last released type.
	 */
	public static boolean isOfCurrentPapyrusVersion(Table table) {
		return isCurrentPapyrusVersion(getCompatibilityVersion(table));
	}

	/**
	 * Checks whether the given string represent the current papyrus version without telling explicitly what the current version is.
	 *
	 * @param version
	 *            version to check
	 * @return
	 */
	public static boolean isCurrentPapyrusVersion(String version) {
		return CURRENT_TABLE_VERSION().equals(version);
	}

	/**
	 * Compare to version number.
	 * The test is done only on the first 2 segments of a version.
	 * The two String should have the same number of segments (i.e: 0.9.2 and 1.1.0).
	 * 
	 * @param referenceVersion
	 *            Version that is the reference for the test
	 * @param testedVersion
	 *            the version that is compare to the reference.
	 * @return true if the tested Version is before the reference Version .
	 *         false by default.
	 */
	public static boolean isBeforeVersion(String referenceVersion, String testedVersion) {
		boolean before = false;

		StringTokenizer targetVersionTokenizer = new StringTokenizer(referenceVersion, DELIM_VERSION);
		StringTokenizer sourceVersionTokenizer = new StringTokenizer(testedVersion, DELIM_VERSION);
		try {
			if (targetVersionTokenizer.countTokens() == sourceVersionTokenizer.countTokens()) {// Check if the format is the same for the 2 Strings
				int targetMainVersion = Integer.parseInt(targetVersionTokenizer.nextToken());// get the first number
				int sourceMainVersion = Integer.parseInt(sourceVersionTokenizer.nextToken());
				if (targetMainVersion == sourceMainVersion) {// if main versions are the same check the intermediate version
					int targetIntermediateVersion = Integer.parseInt(targetVersionTokenizer.nextToken());// get the second number
					int sourceIntermediateVersion = Integer.parseInt(sourceVersionTokenizer.nextToken());
					before = (targetIntermediateVersion > sourceIntermediateVersion);


				} else {
					before = (targetMainVersion > sourceMainVersion);
				}
			}

		} catch (NumberFormatException e) {
			Activator.log.error(e);
		}

		return before;
	}


}
