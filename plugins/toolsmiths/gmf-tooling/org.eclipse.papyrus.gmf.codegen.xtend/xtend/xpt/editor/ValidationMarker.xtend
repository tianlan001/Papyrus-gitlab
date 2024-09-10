/*******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #372479, #386838
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
package xpt.editor

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.xtend.annotations.MetaDef
import xpt.Common

@com.google.inject.Singleton class ValidationMarker {
	@Inject extension Common;

	@MetaDef def className(GenDiagram it) '''ValidationMarker'''

	def packageName(GenDiagram it) '''«it.editorGen.editor.packageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def ValidationMarker(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» {

			«generatedMemberComment»
			private static final String KEY = "validation_marker"; //$NON-NLS-1$

			«generatedMemberComment»
			public static final «className(it)»[] EMPTY_ARRAY = new «className(it)»[0];

			«generatedMemberComment»
			private final String location;

			«generatedMemberComment»
			private final String message;

			«generatedMemberComment»
			private final int statusSeverity;

			«generatedMemberComment»
			public «className(it)»(String location, String message, int statusSeverity) {
				this.location = location;
				this.message = message;
				this.statusSeverity = statusSeverity;
			}

			«generatedMemberComment»
			public String getLocation() {
				return location;
			}

			«generatedMemberComment»
			public String getMessage() {
				return message;
			}

			«generatedMemberComment»
			public int getStatusSeverity() {
				return statusSeverity;	
			}

			«generatedMemberComment»
			private static java.util.Map getMarkers(org.eclipse.gef.EditPartViewer viewer) {
				java.util.Map markers = (java.util.Map) viewer.getProperty(KEY);
				if (markers == null) {
					markers = new java.util.HashMap();
					viewer.setProperty(KEY, markers);
				}
				return markers;
			}

			«generatedMemberComment»
			private static java.util.Set getMarkers(org.eclipse.gef.EditPartViewer viewer, String viewId, boolean create) {
				java.util.Set markers = (java.util.Set) getMarkers(viewer).get(viewId);
				if (markers == null) {
					if (!create) {
						return java.util.Collections.EMPTY_SET;
					}
					markers = new java.util.HashSet();
					getMarkers(viewer).put(viewId, markers);
				}
				return markers;
			}

			«generatedMemberComment»
			public static «className(it)»[] getMarkers(org.eclipse.gef.EditPartViewer viewer, String viewId) {
				java.util.Set markers = getMarkers(viewer, viewId, false);
				if (markers.isEmpty()) {
					return EMPTY_ARRAY;
				}
				return («className(it)»[]) markers.toArray(new «className(it)»[markers.size()]);
			}

			«generatedMemberComment»
			public static void removeAllMarkers(org.eclipse.gef.EditPartViewer viewer) {
				getMarkers(viewer).clear();
			}

			«generatedMemberComment»
			public void add(org.eclipse.gef.EditPartViewer viewer, String viewId) {
				getMarkers(viewer, viewId, true).add(this);
			}
		}
	'''

}
