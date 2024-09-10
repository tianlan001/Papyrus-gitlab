/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  
 *    Radek Dvorak (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - refactoring
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemLabelProvider;



public class LabelProvider implements SubstitutionLabelProvider {
	public static final SubstitutionLabelProvider INSTANCE = new LabelProvider();

	private final AdapterFactory ecoreLabelProviderFactory = new EcoreItemProviderAdapterFactory();
	
	private LabelProvider() {
		super();
	}

	public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
		return eStructuralFeature.getName();
	}

	public String getObjectLabel(EObject eObject) {
		if(eObject == null) {
			return String.valueOf(eObject);
		}
		if(eObject instanceof EStructuralFeature) {
			return getFeatureLabel((EStructuralFeature)eObject);
		}
		else if(eObject instanceof ENamedElement) {
			return qualifiedName((ENamedElement)eObject, new StringBuffer()).toString();
		}
		
		String displayName = toDisplayName(eObject);		
		return (displayName != null) ? displayName : EcoreUtil.getIdentification(eObject);
	}

	public String getValueLabel(EDataType eDataType, Object value) {
		return EcoreUtil.convertToString(eDataType, value);
	}

	private static StringBuffer qualifiedName(ENamedElement namedElement, StringBuffer buf) {
		if(buf == null) {
			buf = new StringBuffer();
		}
		EObject container = namedElement.eContainer();
		if(container instanceof ENamedElement) {
			ENamedElement owner = (ENamedElement)container;
			qualifiedName(owner, buf);
			buf.append("::"); //$NON-NLS-1$			
		} 
		
		buf.append(namedElement.getName());
		return buf;
	}
	
	private String toDisplayName(EObject eObject) {
		IItemLabelProvider labelAdapter = (IItemLabelProvider)EcoreUtil.getRegisteredAdapter(EcorePackage.eINSTANCE.getEAnnotation(), IItemLabelProvider.class);
		if(labelAdapter == null) {
			labelAdapter = (IItemLabelProvider)ecoreLabelProviderFactory.adapt(eObject, IItemLabelProvider.class);
		}
		String label = null;
		try {
			label = labelAdapter.getText(eObject);
		} catch(RuntimeException e) {
			// Ensure fault isolation of the item provider
			label = EcoreUtil.getIdentification(eObject);
		}
		return label;
	}
	
	// FIXME no real need for static LabelProvider instance, can replace uses with AbstractProvider#getLabelProvider call
	public static final String getTextLabel(Object obj) {
		return (obj instanceof EObject) ? INSTANCE.getObjectLabel((EObject)obj) : String.valueOf(obj);
	}
}
