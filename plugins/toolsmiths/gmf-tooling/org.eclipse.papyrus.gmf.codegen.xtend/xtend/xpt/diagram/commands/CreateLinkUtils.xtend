/*****************************************************************************
 * Copyright (c) 2007-2012, 2021 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : 1.4 Merge papyrus extension templates into codegen.xtend
 * Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : L1.2 clean up providers
 *****************************************************************************/
package xpt.diagram.commands

import com.google.inject.Inject
import metamodel.MetaModel
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet
import xpt.Common
import xpt.diagram.Utils_qvto
import xpt.diagram.editpolicies.BaseItemSemanticEditPolicy

@com.google.inject.Singleton class CreateLinkUtils {
	@Inject extension Common;
	@Inject extension Utils_qvto;

	@Inject MetaModel xptMetaModel;
	@Inject BaseItemSemanticEditPolicy xptBaseItemSemanticEditPolicy;

	/**
	* Check whether link may be created.
	*/
	def canCreate(LinkModelFacet it, GenLink link) ''' 
		
			«generatedMemberComment()» 
			public boolean canExecute() {
				if (source == null && target == null) {
					return false;
				}
				if (source != null && «xptMetaModel.NotInstance(it.sourceType, 'source')») {
					return false;
				}
				if (target != null && «xptMetaModel.NotInstance(it.targetType, 'target')») {
					return false;
				}
				if (getSource() == null) {
					return true; // link creation is in progress; source is not defined yet
				}
				// target may be null here but it's possible to check constraint
				«canCreateElement(it, link)»
			}
	'''

	def dispatch canCreateElement(LinkModelFacet it, GenLink link) ''''''

	def dispatch canCreateElement(TypeLinkModelFacet it, GenLink link) ''' 
		«IF hasContainerOtherThanSource(it)»
			if (getContainer() == null) {
				return false;
			}
		«ENDIF»
			return «xptBaseItemSemanticEditPolicy.canCreateCall(it, link, 'getContainer()', 'getSource()', 'getTarget()')»;
	'''

	def dispatch canCreateElement(FeatureLinkModelFacet it, GenLink link) ''' 
		return «xptBaseItemSemanticEditPolicy.canCreateCall(it, link, 'getSource()', 'getTarget()')»;
	'''

	/**
	 *	Fields of command that creates link.
	 */
	def dispatch fields(LinkModelFacet it) '''
			«generatedMemberComment()» 
			protected final org.eclipse.emf.ecore.EObject source;
		
			«generatedMemberComment()» 
			protected final org.eclipse.emf.ecore.EObject target;
	'''

	def dispatch fields(TypeLinkModelFacet it) '''
			«generatedMemberComment()» 
			protected final org.eclipse.emf.ecore.EObject source;
		
			«generatedMemberComment()» 
			protected final org.eclipse.emf.ecore.EObject target;
		«IF hasContainerOtherThanSource(it)»
			
			«generatedMemberComment()» 
			protected «xptMetaModel.QualifiedClassName(it.containmentMetaFeature.genClass)» container;
		«ENDIF»
	'''

	/**
	 *	Field accessors of command that creates link.
	**/
	def accessors(LinkModelFacet it) ''' 
		«generatedMemberComment()» 
		protected «xptMetaModel.QualifiedClassName(it.sourceType)» getSource() {
			return «xptMetaModel.CastEObject(it.sourceType, 'source')»;
		}
		
		«generatedMemberComment()» 
		protected «xptMetaModel.QualifiedClassName(it.targetType)» getTarget() {
			return «xptMetaModel.CastEObject(it.targetType, 'target')»;
		}
		«containerAccessor(it)»
	'''

	def dispatch containerAccessor(LinkModelFacet it) ''''''

	def dispatch containerAccessor(TypeLinkModelFacet it) ''' 
		«IF hasContainerOtherThanSource(it)»
			
				«generatedMemberComment()» 
				public «xptMetaModel.QualifiedClassName(it.containmentMetaFeature.genClass)» getContainer() {
					return container;
				}
			
				«generatedMemberComment(
			'Default approach is to traverse ancestors of the source to find instance of container.\n' + 'Modify with appropriate logic.'
		)»
				protected «xptMetaModel.QualifiedClassName(it.containmentMetaFeature.genClass)» deduceContainer(org.eclipse.emf.ecore.EObject source, org.eclipse.emf.ecore.EObject target) {
					// Find container element for the new link.
					// Climb up by containment hierarchy starting from the source
					// and return the first element that is instance of the container class.
					for (org.eclipse.emf.ecore.EObject element = source; element != null; element = element.eContainer()) {
						if («xptMetaModel.IsInstance(containmentMetaFeature.genClass, 'element')») {
							return «xptMetaModel.CastEObject(it.containmentMetaFeature.genClass, 'element')»;
						}
					}
					return null;
				}
		«ENDIF»
		
	'''

	/**
	 * Part of the constructor that performs initialization.
 	*/
	def dispatch initAndDeduceContainerIfNeeded(LinkModelFacet it) ''' 
		this.source = source;
		this.target = target;
	'''

	def dispatch initAndDeduceContainerIfNeeded(TypeLinkModelFacet it) ''' 
		this.source = source;
		this.target = target;
		«IF hasContainerOtherThanSource(it)»
			container = deduceContainer(source, target);
		«ENDIF»
	'''

}
