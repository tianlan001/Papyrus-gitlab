/**
 * *******************************************************************************
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *     Fabien Giquel (Mia-Software) - initial API and implementation
 * *******************************************************************************
 *
 */
package org.eclipse.papyrus.views.properties.model.xwt.xwtxml.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Attribute;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.CDATA;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Comment;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.DocumentTypeDeclaration;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Element;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Namespace;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.ProcessingInstruction;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Root;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Text;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.XwtxmlFactory;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.XwtxmlPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class XwtxmlFactoryImpl extends EFactoryImpl implements XwtxmlFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static XwtxmlFactory init() {
		try {
			XwtxmlFactory theXwtxmlFactory = (XwtxmlFactory) EPackage.Registry.INSTANCE.getEFactory(XwtxmlPackage.eNS_URI);
			if (theXwtxmlFactory != null) {
				return theXwtxmlFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new XwtxmlFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public XwtxmlFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case XwtxmlPackage.ATTRIBUTE:
			return createAttribute();
		case XwtxmlPackage.TEXT:
			return createText();
		case XwtxmlPackage.ELEMENT:
			return createElement();
		case XwtxmlPackage.ROOT:
			return createRoot();
		case XwtxmlPackage.NAMESPACE:
			return createNamespace();
		case XwtxmlPackage.COMMENT:
			return createComment();
		case XwtxmlPackage.DOCUMENT_TYPE_DECLARATION:
			return createDocumentTypeDeclaration();
		case XwtxmlPackage.CDATA:
			return createCDATA();
		case XwtxmlPackage.PROCESSING_INSTRUCTION:
			return createProcessingInstruction();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Text createText() {
		TextImpl text = new TextImpl();
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Element createElement() {
		ElementImpl element = new ElementImpl();
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Root createRoot() {
		RootImpl root = new RootImpl();
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Namespace createNamespace() {
		NamespaceImpl namespace = new NamespaceImpl();
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DocumentTypeDeclaration createDocumentTypeDeclaration() {
		DocumentTypeDeclarationImpl documentTypeDeclaration = new DocumentTypeDeclarationImpl();
		return documentTypeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CDATA createCDATA() {
		CDATAImpl cdata = new CDATAImpl();
		return cdata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ProcessingInstruction createProcessingInstruction() {
		ProcessingInstructionImpl processingInstruction = new ProcessingInstructionImpl();
		return processingInstruction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public XwtxmlPackage getXwtxmlPackage() {
		return (XwtxmlPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static XwtxmlPackage getPackage() {
		return XwtxmlPackage.eINSTANCE;
	}

} // XwtxmlFactoryImpl
