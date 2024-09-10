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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * Instances of this base class are provided via "org.eclipse.papyrus.infra.gmfdiag.common.tableReconciler" extension point and provide migration of
 * the table instances from "previous" version to the "current" Papyrus version. The version is stored as a {@link StringValueStyle} with name {@link TableVersioningUtils#COMPATIBILITY_VERSION}.
 * <p/>
 * Every {@link TableReconciler} subclass provides statical descriptor of its capabilities (that is, the supported table type, outdated "previous" version and the expected "current" version). For some possible advanced cases it also gets a chance to
 * decline the reconciliation for some particular instance based on runtime data, via {@link TableReconciler#canReconcileFrom(Table, String)}.
 * <p/>
 * If the suitable reconcilers are found, all of them are asked to update table instance before the first table opening. Planned modifications are provided as an {@link ICommand} instance that allows user to roll-back them all at once.
 * <p/>
 * @since 3.0
 */
public abstract class TableReconciler implements IExecutableExtension {

	/**
	 * Extension point attribute passed to {@link IConfigurationElement#createExecutableExtension(String)}, so value should be FQN of reconciler to
	 * instantiate.
	 * <p/>
	 * Note that the value of this class will be used to search the instance to un-register, so it should be unique among all the reconcilers for given table type. It is however safe to define same reconciler class for different table types.
	 * <p/>
	 * Value of this constant is "reconcilerClass"
	 */
	public static final String ATTR_RECONCILER_CLASS = "reconcilerClass"; //$NON-NLS-1$

	/**
	 * Mandatory extension attribute to define applicable outdated table version this reconciler can handle.
	 * Reconciler's that wants to update tables created before the Papyrus 1.0.0 release may be registered to "undefined" source version.
	 * <p/>
	 * Value of this constant is "source"
	 */
	public static final String ATTR_SOURCE_VERSION = "source"; //$NON-NLS-1$

	/**
	 * Mandatory extension attribute to define applicable "current" table version this reconciler will update the instance to.
	 * <p/>
	 * This value should pass the {@link TableVersioningUtils#isCurrentPapyrusVersion(String)} check for reconciler to be considered.
	 * <p/>
	 * Value of this constant is "target"
	 */
	public static final String ATTR_TARGET_VERSION = "target"; //$NON-NLS-1$

	private String mySourceVersion;

	private String myTargetVersion;

	/**
	 * The value of this to be used when searching
	 */
	private String myClassFqn;

	/**
	 * Instance of reconciler gets a chance to deny reconciliation based on some custom run-time properties.
	 * The <code>false</code> value returned from this method means that the table is unsuitable for this reconciler, and does not assume any error.
	 * <p>
	 * Subclass can override tgis method, default implementation always return true, because all the dfeault filtering is done based on static extension declarations.
	 *
	 * @param table
	 *            the runtime instance to reconcile
	 * @param currentTableVersion
	 * @return <code>false</code> if this reconciler wants to be ignored for some particular table instance
	 */
	public boolean canReconcileFrom(Table table, String currentTableVersion) {
		return true;
	}

	/**
	 * Creates command that will fix the given table and update it to the statically known "current" version.
	 * It is considered an error to return the command with {@link ICommand#canExecute()} of <code>false</code>, and editor implementation will log an
	 * erroneous reconcile attempt.
	 * <p/>
	 * If the reconciler wants to ignore the instance it can either return <code>false</code> from {@link TableReconciler#canReconcileFrom(Table, String)} or return <code>null</code> from this method
	 *
	 * @param table
	 * @return the command or <code>null</code> if nothing to do with this instance
	 */
	public abstract ICommand getReconcileCommand(Table table);

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		mySourceVersion = config.getAttribute(ATTR_SOURCE_VERSION);
		myTargetVersion = config.getAttribute(ATTR_TARGET_VERSION);
		myClassFqn = config.getAttribute(propertyName);
	}

	public String getSourceVersion() {
		return mySourceVersion;
	}

	public String getTargetVersion() {
		return myTargetVersion;
	}

	public String getClassFqn() {
		return myClassFqn;
	}

	protected static boolean safeEquals(String s1, String s2) {
		if (s1 == s2) {
			return true;
		}
		return s1 != null && s1.equals(s2);
	}

	/**
	 * For debug purpose only
	 */
	@Override
	public String toString() {
		return "TableReconciler:" + myClassFqn + "[" + mySourceVersion + " -> " + myTargetVersion + "]";
	}
}
