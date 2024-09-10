/*****************************************************************************
 * Copyright (c) 2007, 2010, 2013, 2021 Borland Software Corporation, Montages, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexander Shatalin/Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - API extracted to GMF-T runtime, migrated to Xtend2
 * Artem Tikhomirov (Borland) - [257119] Create views directly, not through ViewFactories
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Thibault Landre (Atos Origin) - initial API and implementation
 * Vincent Lorenzo (CEA-LIST) Add a line to initialize the display of the compartments to true
 * Vincent Lorenzo (CEA-LIST) - Add lines to initialize the display of the labels - Bug 335987 [General][Enhancement] Show/Hide Connectors Labels and External Nodes Labels
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.providers

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import xpt.Common
import xpt.editor.VisualIDRegistry
import xpt.diagram.editparts.EditPartFactory

@com.google.inject.Singleton class EditPartProvider {
	@Inject extension Common;

	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject EditPartFactory xptEditPartFactory;

	def className(GenDiagram it) '''«it.editPartProviderClassName»'''

	def packageName(GenDiagram it) '''«it.providersPackageName»'''

	def qualifiedClassName(GenDiagram it) '''«packageName(it)».«className(it)»'''

	def fullPath(GenDiagram it) '''«qualifiedClassName(it)»'''

	def EditPartProvider(GenDiagram it) '''
		«copyright(editorGen)»
		package «packageName(it)»;

		«generatedClassComment»
		public class «className(it)» «extendsList(it)» {

			«constructor(it)»
		}
	'''

	def extendsList(GenDiagram it) '''extends org.eclipse.papyrus.infra.gmfdiag.common.providers.DefaultEditPartProvider'''

	def constructor(GenDiagram it) '''
		«generatedMemberComment»
		public «className(it)»() {
			super(new «xptEditPartFactory.qualifiedClassName(it)»(), «xptVisualIDRegistry.runtimeTypedInstanceCall(it)», «xptEditPartFactory.getEditPartQualifiedClassName(it)».MODEL_ID);
		}
	'''
}
