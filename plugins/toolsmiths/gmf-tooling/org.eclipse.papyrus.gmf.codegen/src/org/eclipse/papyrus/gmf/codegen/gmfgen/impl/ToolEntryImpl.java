/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import java.util.Collection;

import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ToolEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tool Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ToolEntryImpl#getGenNodes <em>Gen Nodes</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ToolEntryImpl#getGenLinks <em>Gen Links</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ToolEntryImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ToolEntryImpl extends AbstractToolEntryImpl implements ToolEntry {
	/**
	 * The cached value of the '{@link #getGenNodes() <em>Gen Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<GenNode> genNodes;

	/**
	 * The cached value of the '{@link #getGenLinks() <em>Gen Links</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<GenLink> genLinks;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ToolEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getToolEntry();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenNode> getGenNodes() {
		if (genNodes == null) {
			genNodes = new EObjectResolvingEList<GenNode>(GenNode.class, this, GMFGenPackage.TOOL_ENTRY__GEN_NODES);
		}
		return genNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenLink> getGenLinks() {
		if (genLinks == null) {
			genLinks = new EObjectResolvingEList<GenLink>(GenLink.class, this, GMFGenPackage.TOOL_ENTRY__GEN_LINKS);
		}
		return genLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenCommonBase> getElements() {
		EList<? extends GenCommonBase> picked;
		if (getGenNodes().isEmpty()) {
			picked = getGenLinks();
		} else {
			picked = getGenNodes();
		}
		return new EcoreEList.UnmodifiableEList<GenCommonBase>(this, GMFGenPackage.eINSTANCE.getToolEntry_Elements(), picked.size(), picked.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.TOOL_ENTRY__GEN_NODES:
				return getGenNodes();
			case GMFGenPackage.TOOL_ENTRY__GEN_LINKS:
				return getGenLinks();
			case GMFGenPackage.TOOL_ENTRY__ELEMENTS:
				return getElements();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GMFGenPackage.TOOL_ENTRY__GEN_NODES:
				getGenNodes().clear();
				getGenNodes().addAll((Collection<? extends GenNode>)newValue);
				return;
			case GMFGenPackage.TOOL_ENTRY__GEN_LINKS:
				getGenLinks().clear();
				getGenLinks().addAll((Collection<? extends GenLink>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case GMFGenPackage.TOOL_ENTRY__GEN_NODES:
				getGenNodes().clear();
				return;
			case GMFGenPackage.TOOL_ENTRY__GEN_LINKS:
				getGenLinks().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GMFGenPackage.TOOL_ENTRY__GEN_NODES:
				return genNodes != null && !genNodes.isEmpty();
			case GMFGenPackage.TOOL_ENTRY__GEN_LINKS:
				return genLinks != null && !genLinks.isEmpty();
			case GMFGenPackage.TOOL_ENTRY__ELEMENTS:
				return !getElements().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public String getCreateMethodName() {
		if (getCreateMethodNameGen() != null) {
			return getCreateMethodNameGen();
		}
		return CodeGenUtil.validJavaIdentifier("create" + CodeGenUtil.capName(String.valueOf(getTitle())) + getEntryID() + "CreationTool");
	}
} //ToolEntryImpl
