/*****************************************************************************
 * Copyright (c) 2015, 2021 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Florian Noyrit - Initial API and implementation
 * Svyatoslav Kovalsky (Montages) - initial API and implementation
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 *****************************************************************************/
package xpt

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment
import xpt.diagram.editpolicies.CompartmentItemSemanticEditPolicy
import xpt.diagram.editpolicies.DiagramItemSemanticEditPolicy
import xpt.diagram.editpolicies.LinkItemSemanticEditPolicy
import xpt.diagram.editpolicies.NodeItemSemanticEditPolicy
import xpt.diagram.commands.CreateNodeCommand
import xpt.diagram.commands.CreateLinkCommand

@com.google.inject.Singleton class QualifiedClassNameProvider {

	@Inject CompartmentItemSemanticEditPolicy compItemSemantic;
	@Inject DiagramItemSemanticEditPolicy diagramItemSemantic;
	@Inject LinkItemSemanticEditPolicy linkItemSemantic;
	@Inject NodeItemSemanticEditPolicy nodeItemSemantic;

	@Inject CreateLinkCommand linkCommand
	@Inject CreateNodeCommand nodeCommand

	def dispatch getItemSemanticEditPolicyQualifiedClassName(GenCommonBase it) ''''''
	def dispatch getItemSemanticEditPolicyQualifiedClassName(GenDiagram it)'''org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCompartmentSemanticEditPolicy'''
	def dispatch getItemSemanticEditPolicyQualifiedClassName(GenCompartment it) '''org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCompartmentSemanticEditPolicy'''
	def dispatch getItemSemanticEditPolicyQualifiedClassName(GenLink it)  '''org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy'''
	def dispatch getItemSemanticEditPolicyQualifiedClassName(GenNode it) '''org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy'''

	def dispatch CharSequence getItemSemanticEditPolicyClassName(GenCommonBase it) ''''''
	def dispatch CharSequence getItemSemanticEditPolicyClassName(GenDiagram it) '''«diagramItemSemantic.className(it)»'''
	def dispatch CharSequence getItemSemanticEditPolicyClassName(GenLink it) '''«linkItemSemantic.className(it)»'''
	def dispatch CharSequence getItemSemanticEditPolicyClassName(GenNode it) '''«nodeItemSemantic.className(it)»'''
	def dispatch CharSequence getItemSemanticEditPolicyClassName(GenCompartment it) '''«compItemSemantic.className(it)»'''

	def dispatch getCreateCommandQualifiedClassName(GenCommonBase it) ''''''
	def dispatch getCreateCommandQualifiedClassName(GenNode it) '''«nodeCommand.qualifiedClassName(it)»'''
	def dispatch getCreateCommandQualifiedClassName(GenLink it) '''«linkCommand.qualifiedClassName(it)»'''
}