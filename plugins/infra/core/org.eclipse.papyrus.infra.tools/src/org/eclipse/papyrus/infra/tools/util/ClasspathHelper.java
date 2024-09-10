/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.util;

import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.papyrus.infra.tools.Activator;

/**
 * Optional support for a JDT workspace, to find development-time classes such as may be referenced for commands
 * in <em>Architecture Models</em>.
 */
public class ClasspathHelper {

	public static final ClasspathHelper INSTANCE;

	static {
		ClasspathHelper instance;

		try {
			instance = new JDTHelper();
		} catch (Exception e) {
			// JDT is not available
			instance = new ClasspathHelper();
		}

		INSTANCE = instance;
	}

	/**
	 * Find a class of the given {@code name} that is referenced by a model in the given {@code context} resource,
	 * with an optional {@code constraint}.
	 *
	 * @param name
	 *            the class name to find (using the canonical <tt>$</tt> separator for nested types
	 * @param context
	 *            the contextual model resource URI
	 * @param constraint
	 *            a class to which the result must conform (as a supertype), or {@code null} if no constraint is needed
	 * @return the class, or {@code null} if the class doesn't exist or is invalid (such as not conforming to the {@code constraint}).
	 *         The result may be a Java {@link Class} or a JDT {@link IType}, depending whether JDT is available
	 */
	public Object findClass(String name, URI context, Class<?> constraint) {
		return constraint == null
				? ClassLoaderHelper.loadClass(name, context)
				: ClassLoaderHelper.loadClass(name, constraint, context);
	}

	//
	// Nested types
	//

	/**
	 * The JDT helper instance looks for classes in the Java workspace (and typically in the current PDE target platform).
	 */
	private static final class JDTHelper extends ClasspathHelper {

		@Override
		public Object findClass(String name, URI context, Class<?> constraint) {
			Object result = null;

			IJavaProject project = getJavaProject(context);
			if (project == null) {
				if (context == null) {
					// No specific source context? Try to search the entire workspace, then
					result = searchType(name, constraint);
				}

				if (result == null) {
					// Assume that this search is not in a Java project context. So, it's a
					// run-time environment and we need to search the classpath.
					result = super.findClass(name, context, constraint);
				}
			} else {
				result = findType(project, name, constraint);
			}

			if (result == null) {
				// Search the workspace index
				result = searchType(name, constraint);
			}

			return result;
		}

		private IJavaProject getJavaProject(URI context) {
			IJavaProject result = null;

			if (context != null && (context.isPlatformResource() || context.isPlatformPlugin())) {
				String projectName = context.segment(1);
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if (project.isAccessible()) {
					result = JavaCore.create(project);
				}
			}

			return result;
		}

		IType findType(IJavaProject project, String name, Class<?> constraint) {
			IType result;

			try {
				result = project.findType(jdtQualifiedName(name));

				if (result != null && constraint != null && !conformsTo(result, constraint)) {
					result = null;
				}
			} catch (JavaModelException e) {
				// Maybe we already found a result? If not, assume no result exists
				Activator.log.error("Failed to find Java type on the project classpath.", e); //$NON-NLS-1$
				result = null;
			}

			return result;
		}

		private static String jdtQualifiedName(String qualifiedName) {
			// JDT doesn't use the JVM syntax for nested classes but the Java source syntax
			return qualifiedName.replace('$', '.');
		}

		private IType searchType(String name, Class<?> constraint) {
			SearchEngine search = new SearchEngine();
			SearchPattern pattern = SearchPattern.createPattern(jdtQualifiedName(name),
					IJavaSearchConstants.TYPE, IJavaSearchConstants.DECLARATIONS,
					SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE);

			IType[] result = { null };
			IProgressMonitor monitor = new NullProgressMonitor();

			SearchRequestor requestor = new SearchRequestor() {

				@Override
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					if (match.getElement() instanceof IType) {
						IType foundType = (IType) match.getElement();

						if (constraint == null || conformsTo(foundType, constraint)) {
							result[0] = foundType;
							monitor.setCanceled(true);
						}
					}
				}
			};

			try {
				search.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() },
						SearchEngine.createWorkspaceScope(), requestor, monitor);
			} catch (CoreException e) {
				// Maybe we already found a result? If not, assume no result exists
				Activator.log.error("Failed to find Java type on the workspace classpath.", e); //$NON-NLS-1$
			} catch (OperationCanceledException e) {
				// It's okay: I cancelled this, myself
			}

			return result[0];
		}

		private boolean conformsTo(IType type, Class<?> supertype) {
			boolean result = false;

			try {
				ITypeHierarchy hierarchy = type.newSupertypeHierarchy(new NullProgressMonitor());
				result = Stream.of(hierarchy.getAllSupertypes(type)).map(t -> t.getFullyQualifiedName('$'))
						.anyMatch(supertype.getCanonicalName()::equals);
			} catch (JavaModelException e) {
				Activator.log.error("Failed to find Java type on the workspace classpath.", e); //$NON-NLS-1$
			}

			return result;
		}

	}

}

