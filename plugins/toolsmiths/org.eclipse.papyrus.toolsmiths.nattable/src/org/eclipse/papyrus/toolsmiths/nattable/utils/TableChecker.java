/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.toolsmiths.nattable.Activator;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;

/**
 *
 * This class allows to check if a table is conform to that we expect to create a new table configuration
 *
 */
public class TableChecker {

	/**
	 * This field groups only the approved AxisManagerId
	 *
	 * Here, the list of the existing axis manager (the 7th of February 2018), with our opinion about their usages.
	 * We recommand to use the UML Generic Tree Table, even to create flat table or Generic Matrix Table for Matrix
	 *
	 * <ul>
	 * <li>Approved</li>
	 * <ul>
	 * <li>axisId : org.eclipse.papyrus.infra.emf.nattable.operation.axis.manager - class org.eclipse.papyrus.infra.emf.nattable.manager.axis.EOperationAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.infra.emf.nattable.axis.column.eobject.matrix.manager - class org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectColumnMatrixAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.uml.nattable.stereotype.property.axis.manager - class org.eclipse.papyrus.uml.nattable.manager.axis.UMLStereotypePropertyAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.uml.nattable.feature.axis.manager - class org.eclipse.papyrus.uml.nattable.manager.axis.UMLFeatureAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.infra.uml.nattable.tree.axis.manager - class org.eclipse.papyrus.uml.nattable.manager.axis.UMLElementTreeAxisManagerForEventList</li>
	 * <li>axisId : org.eclipse.papyrus.uml.nattable.operation.axis.manager - class org.eclipse.papyrus.uml.nattable.manager.axis.UMLOperationAxisManager</li>
	 * </ul>
	 * <li>Neutral (no real idea)</li>
	 * <ul>
	 * <li>axisId : org.eclipse.papyrus.infra.emf.nattable.user.axis.manager - class org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.infra.emf.nattable.feature.axis.manager - class org.eclipse.papyrus.infra.emf.nattable.manager.axis.EStructuralFeatureAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.infra.emf.nattable.tree.axis.manager - class org.eclipse.papyrus.infra.emf.nattable.manager.axis.EObjectTreeAxisManagerForEventList</li>
	 * </ul>
	 * <li>Not recommended</li>
	 * <ul>
	 * <li>axisId : org.eclipse.papyrus.infra.nattable.pagelist.contents.axis.manager - class org.eclipse.papyrus.infra.nattable.views.config.manager.axis.EditorContextSynchronizerAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.infra.nattable.page.view.feature.axis.manager - class org.eclipse.papyrus.infra.nattable.views.config.manager.axis.EditorFeatureAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.uml.nattable.element.axis.manager - class org.eclipse.papyrus.uml.nattable.manager.axis.UMLElementAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.infra.uml.nattable.class.tree.axis.manager - class org.eclipse.papyrus.uml.nattable.clazz.config.manager.axis.UMLClassTreeAxisManagerForEventList</li>
	 * <li>axisId : org.eclipse.papyrus.uml.nattable.stereotype.display.axis.manager - class org.eclipse.papyrus.uml.nattable.stereotype.display.manager.axis.NotationTreeTableAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.uml.nattable.stereotype.display.properties.axis.manager - class org.eclipse.papyrus.uml.nattable.stereotype.display.manager.axis.StereotypeDisplayPropertiesAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.sysml14.nattable.requirement.axis.manager - class org.eclipse.papyrus.sysml14.nattable.common.manager.axis.RequirementAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.sysml14.nattable.requirement.tree.axis.manager - class org.eclipse.papyrus.sysml14.nattable.common.manager.axis.RequirementTreeAxisManager</li>
	 * <li>axisId : org.eclipse.papyrus.sysml14.nattable.allocate.axis.manager - class org.eclipse.papyrus.sysml14.nattable.common.manager.axis.AllocateAxisManager</li>
	 * </ul>
	 * </ul>
	 *
	 */
	private static final List<String> ALLOWED_AXIS_ID;
	static {
		ALLOWED_AXIS_ID = new ArrayList<>();
		ALLOWED_AXIS_ID.add("org.eclipse.papyrus.infra.emf.nattable.operation.axis.manager");//$NON-NLS-1$
		ALLOWED_AXIS_ID.add("org.eclipse.papyrus.infra.emf.nattable.axis.column.eobject.matrix.manager"); //$NON-NLS-1$
		ALLOWED_AXIS_ID.add("org.eclipse.papyrus.uml.nattable.stereotype.property.axis.manager");//$NON-NLS-1$
		ALLOWED_AXIS_ID.add("org.eclipse.papyrus.uml.nattable.feature.axis.manager");//$NON-NLS-1$
		ALLOWED_AXIS_ID.add("org.eclipse.papyrus.infra.uml.nattable.tree.axis.manager");//$NON-NLS-1$
		ALLOWED_AXIS_ID.add("org.eclipse.papyrus.uml.nattable.operation.axis.manager");//$NON-NLS-1$
	}

	/**
	 * The newline char
	 */
	private static final String NEWLINE = "\n"; //$NON-NLS-1$

	/**
	 * The value of the field TableConfiguration#getType of the UML Generic Tree Table
	 */
	private static final String UML_GENERIC_TREE_TABLE_TYPE = "PapyrusUMLGenericTreeTable"; //$NON-NLS-1$

	/**
	 * The value of the field TableConfiguration#getType of the UML Generic Matrix of Relationship
	 */
	private static final String UML_GENERIC_MATRIX = "UMLGenericMatrixOfRelationships"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	private TableChecker() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param table
	 *            a table
	 * @return
	 *         an IStatus indicating if the table can be used without doubt to create a TableConfiguration
	 */
	public static final IStatus checkTable(final Table table) {
		final StringBuilder message = new StringBuilder();
		if (table.isInvertAxis()) {
			message.append(Messages.TableChecker_Warning_TableIsInverted);
		}

		final TableConfiguration tConfig = table.getTableConfiguration();

		final StringBuilder forbiddenRowAxis = new StringBuilder();

		final StringBuilder forbiddenColumnAxis = new StringBuilder();

		// check row axis manager
		for (final AxisManagerRepresentation currentAxisManager : tConfig.getRowHeaderAxisConfiguration().getAxisManagers()) {
			if (false == ALLOWED_AXIS_ID.contains(currentAxisManager.getAxisManagerId())) {
				forbiddenRowAxis.append(currentAxisManager.getAxisManagerId());
				forbiddenRowAxis.append(NEWLINE);
			}
		}

		for (final AxisManagerRepresentation currentAxisManager : tConfig.getColumnHeaderAxisConfiguration().getAxisManagers()) {
			if (false == ALLOWED_AXIS_ID.contains(currentAxisManager.getAxisManagerId())) {
				forbiddenColumnAxis.append(currentAxisManager.getAxisManagerId());
				forbiddenColumnAxis.append(NEWLINE);
			}
		}


		if (forbiddenRowAxis.length() > 0 || forbiddenColumnAxis.length() > 0) {
			message.append(NLS.bind(Messages.TableChecker_InformWhichKindOfTableToUseToCreateNewOne, UML_GENERIC_TREE_TABLE_TYPE, UML_GENERIC_MATRIX));
		}

		if (forbiddenRowAxis.length() > 0) {
			message.append(NLS.bind(Messages.TableChecker_NotRecommendedRowAxis, forbiddenRowAxis.toString()));
		}

		if (forbiddenColumnAxis.length() > 0) {
			message.append(NLS.bind(Messages.TableChecker_NotRecommendedColumnAxis, forbiddenColumnAxis.toString()));
		}


		if (message.length() == 0) {
			return Status.OK_STATUS;
		}


		return new Status(IStatus.WARNING, Activator.PLUGIN_ID, message.toString());
	}
}
