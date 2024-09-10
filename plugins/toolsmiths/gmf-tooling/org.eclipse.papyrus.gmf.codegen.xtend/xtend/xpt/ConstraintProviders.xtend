/******************************************************************************
 * Copyright (c) 2014, 2020 Borland Software Corporation, CEA LIST, Artal and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Svyatoslav Kovalsky (Montages) - initial API and implementation
 *	Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *	Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up
 *****************************************************************************/
 package xpt

import com.google.inject.Inject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContainer

@com.google.inject.Singleton class ConstraintProviders {

	@Inject extension Common
	@Inject extension GenAuditRoot_qvto
	@Inject extension GenEditorGenerator_qvto

	def extensions(GenEditorGenerator it)'''
		«IF hasAudits(it)»«extensions(audits)»«ENDIF»
	'''

	def extensions(GenAuditRoot it)'''
		«extensions_emfv_constraintProviders»
		«extensions_emfv_constraintBindings»
		«extensions_emfv_uiContexts»
	'''

	//
	// Extension point: org.eclipse.emf.validation.constraintProviders
	//
	def extensions_emfv_constraintProviders(GenAuditRoot it)'''
		«tripleSpace(1)»<extension point="org.eclipse.emf.validation.constraintProviders">
		«tripleSpace(2)»«xmlGeneratedTag»
		«FOR c : categories»«declareCategory(c)»«ENDFOR»
		«tripleSpace(2)»<constraintProvider cache="true">
			«FOR p : getAllTargetedModelPackages(it)»
			«tripleSpace(3)»<package namespaceUri="«p.getEcorePackage.nsURI»"/>
			«ENDFOR»
		«tripleSpace(1)»«FOR c : categories»«defineCategory(c)»«ENDFOR»
		«tripleSpace(2)»</constraintProvider>
		«tripleSpace(1)»</extension>
	'''

	//
	// Extension point: org.eclipse.emf.validation.constraintBindings
	//
	def extensions_emfv_constraintBindings(GenAuditRoot it)'''
		«tripleSpace(1)»<extension point="org.eclipse.emf.validation.constraintBindings">
		«tripleSpace(2)»«xmlGeneratedTag»
		«FOR ctx : clientContexts»
			«tripleSpace(2)»<clientContext default="false" id="«getQualifiedIdentity(ctx)»">
			«tripleSpace(3)»<selector class="«ctx.getQualifiedClassName()»"/>
			«tripleSpace(2)»</clientContext>
			«tripleSpace(2)»<binding context="«getQualifiedIdentity(ctx)»">
			«FOR rule : rules.filter(r | r.target !== null && r.target.contextSelector == ctx)»
					«tripleSpace(6)»<constraint ref="«editorGen.plugin.ID».«escapeXML(rule.id)»"/>
			«ENDFOR»
			«tripleSpace(5)»</binding>

		«ENDFOR»
		«tripleSpace(1)»</extension>
	'''

	//
	// Extension point: org.eclipse.emf.validation.ui.UIRegisteredClientContext
	//
	def extensions_emfv_uiContexts(GenAuditRoot it)'''
		«IF editorGen.diagram.liveValidationUIFeedback»
		«tripleSpace(1)»<extension point="org.eclipse.emf.validation.ui.UIRegisteredClientContext">
		«tripleSpace(2)»«xmlGeneratedTag»
		«FOR ctx : clientContexts»
			«tripleSpace(3)»<clientContext id="«getQualifiedIdentity(ctx)»"/>
		«ENDFOR»
		«tripleSpace(2)»</extension>
		«ENDIF»
	'''

	def declareCategory(GenAuditContainer it)'''
		«tripleSpace(4)»<category id="«pathMap»" mandatory="false" name="«IF name !== null »«escapeXML(name)»«ELSE»«pathMap»«ENDIF»">
		«tripleSpace(3)»<![CDATA[«IF description !== null »«description»«ELSE»«''»«ENDIF»]]>
		«tripleSpace(2)»</category>
	'''

	def defineCategory(GenAuditContainer it)'''
		«tripleSpace(4)»<constraints categories="«pathMap»">
		«FOR audit : audits.filter(a | a.target !== null && a.target.getTargetClass() !== null )»
		«tripleSpace(6)»<constraint id="«escapeXML(audit.id)»"
		«tripleSpace(4)»«IF audit.requiresConstraintAdapter»lang="Java" class="«audit.getConstraintAdapterQualifiedClassName()»"«ELSE»lang="OCL"«ENDIF»
			«IF audit.name !== null »
				«tripleSpace(4)»name="«escapeXML(audit.name)»"
			«ELSE»
				«tripleSpace(4)»name="«escapeXML(audit.id)»"
			«ENDIF»
		«tripleSpace(4)»mode="«IF audit.useInLiveMode»Live«ELSE»Batch«ENDIF»"
		«tripleSpace(4)»severity="«audit.severity»" statusCode="200">
			«IF audit.description !== null »
			«tripleSpace(4)»«IF !audit.requiresConstraintAdapter»<![CDATA[«IF audit.rule !== null »«audit.rule.body»«ELSE»«''»«ENDIF»]]>«ENDIF»
			«tripleSpace(4)»<description><![CDATA[«(audit.description)»]]></description>
			«ELSE»
			«tripleSpace(4)»<description><![CDATA[«('')»]]></description>
			«ENDIF»
			«IF audit.message !== null »
				«tripleSpace(4)»<message><![CDATA[«audit.message»]]></message>
				«ELSE»
				«IF audit.name !== null »
				«tripleSpace(4)»<message><![CDATA[«audit.name» audit violated]]></message>
				«ELSE»
				«tripleSpace(4)»<message><![CDATA[«audit.id» audit violated]]></message>
				«ENDIF»
			«ENDIF»
		«tripleSpace(4)»<target class="«audit.target.getTargetClassModelQualifiedName()»"/>
		«tripleSpace(3)»</constraint>
		«ENDFOR»
		«tripleSpace(6)»</constraints>
	'''

	protected def pathMap(GenAuditContainer it) '''«FOR p : path SEPARATOR '/' »«escapeXML(p.id)»«ENDFOR»'''
}