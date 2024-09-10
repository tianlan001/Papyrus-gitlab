/*****************************************************************************
 *  Copyright (c) 2018 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Tessier  (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.query.java.metamodel.v0_2_0.javaquery.internal;

import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetSet;
import org.eclipse.papyrus.emf.facet.query.java.metamodel.v0_2_0.javaquery.JavaQuery;
import org.eclipse.papyrus.emf.facet.query.java.metamodel.v0_2_0.javaquery.JavaQueryPackage;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.pde.core.project.IBundleProjectDescription;

/**
 * This class is used to validate the existence of a Java query in the custom model
 */
public class JavaQueryValidator extends EObjectValidator {


	public static JavaQueryValidator eInstance= new JavaQueryValidator();

	@Override
	protected EPackage getEPackage() {
		return JavaQueryPackage.eINSTANCE;
	}
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
		case JavaQueryPackage.JAVA_QUERY:
			return validateJavaQuery((JavaQuery)value, diagnostics, context);
		default:
			return super.validate(classifierID, value, diagnostics, context);
		}
	}

	/**
	 * 
	 * @param javaquery
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	public boolean validateJavaQuery(JavaQuery javaquery, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean valid = true;
		if (diagnostics != null) {
			if (javaquery.getImplementationClassName() != null && !("".equals(javaquery.getImplementationClassName().trim()))) {
				String classNameToFind=javaquery.getImplementationClassName();
				//1. the class could only on workspace
				//so look for it
				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
				for (IProject project : projects) {
					try {
						if (project.getNature(IBundleProjectDescription.PLUGIN_NATURE) != null) {
							IJavaProject javaProject= JavaCore.create(project);
							IType foundSource=javaProject.findType(classNameToFind);
							if (foundSource!=null) {
								return true;
							}
						}
					} catch (JavaModelException e) {
						e.printStackTrace();
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}


				//2. maybe it is in the platform plugin?
				Class<?> loadedClass = ClassLoaderHelper.loadClass(classNameToFind);
				if (loadedClass == null) {
					valid = false;
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
							JavaQueryValidator.DIAGNOSTIC_SOURCE,
							1, "The java query '"
									+ javaquery.getImplementationClassName() + "' references a class that does not exist " , //$NON-NLS-1$
									new Object[] { javaquery }));

				}

			}

		}
		return valid;
	}
	/**
	 * @param facet
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	private boolean validatefacetSet_validate(FacetSet facetSet, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean valid = true;
		if (diagnostics != null) {
			if (facetSet.getNsPrefix() == null||facetSet.getNsPrefix().equals("")) {
				return true;
			}
		}
		return valid;
	}

}
