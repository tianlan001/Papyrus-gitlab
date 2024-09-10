/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 /*****************************************************************************/
package org.eclipse.papyrus.infra.constraints.constraints;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.constraints.Activator;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.JavaQuery.FalseQuery;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;

/**
 * This constraint allows to define a Java Query (without parameters) without
 * defining it in an environment file.
 *
 * The constraint takes one parameter ("className"), which defines the
 * qualified name of the Java class used to implement the constraint.
 *
 * The Java class must implement the {@link JavaQuery} interface
 *
 * @author Camille Letavernier
 */
public class JavaQueryConstraint extends AbstractConstraint {

	/**
	 * The Java Class property
	 */
	public static final String QUERY_CLASS_NAME_PROPERTY = "className"; //$NON-NLS-1$

	private JavaQuery query = new FalseQuery();

	@Override
	protected void setDescriptor(SimpleConstraint descriptor) {
		String queryClassName = getValue(QUERY_CLASS_NAME_PROPERTY);
		query = ClassLoaderHelper.newInstance(queryClassName, JavaQuery.class, EcoreUtil.getURI(descriptor));
		if (query == null) {
			Activator.log.warn("Cannot load the JavaQuery for this constraint : " + descriptor.getName());
		}
	}

	@Override
	public boolean match(Object selection) {
		return query != null && query.match(selection);
	}

	@Override
	protected boolean equivalent(Constraint constraint) {
		if (constraint instanceof JavaQueryConstraint) {
			return ((JavaQueryConstraint) constraint).query.getClass().equals(query.getClass());
		}
		return false;
	}


}
