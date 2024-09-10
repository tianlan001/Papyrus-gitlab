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
import java.util.LinkedList;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildLabelNode;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkConstraints;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkEnd;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode;
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet;
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet;
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeModelFacet;
import org.eclipse.papyrus.gmf.internal.codegen.util.Extras;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getGenOutgoingLinks <em>Gen Outgoing Links</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getGenIncomingLinks <em>Gen Incoming Links</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getDiagram <em>Diagram</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getModelFacet <em>Model Facet</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getLabels <em>Labels</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#isOutgoingCreationAllowed <em>Outgoing Creation Allowed</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#isIncomingCreationAllowed <em>Incoming Creation Allowed</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#isViewDirectionAlignedWithModel <em>View Direction Aligned With Model</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getCreationConstraints <em>Creation Constraints</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#isTargetReorientingAllowed <em>Target Reorienting Allowed</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#isSourceReorientingAllowed <em>Source Reorienting Allowed</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getCreateCommandClassName <em>Create Command Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getReorientCommandClassName <em>Reorient Command Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#isTreeBranch <em>Tree Branch</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkImpl#getTargets <em>Targets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenLinkImpl extends GenCommonBaseImpl implements GenLink {
	/**
	 * The cached value of the '{@link #getModelFacet() <em>Model Facet</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelFacet()
	 * @generated
	 * @ordered
	 */
	protected LinkModelFacet modelFacet;

	/**
	 * The cached value of the '{@link #getLabels() <em>Labels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabels()
	 * @generated
	 * @ordered
	 */
	protected EList<GenLinkLabel> labels;

	/**
	 * The default value of the '{@link #isOutgoingCreationAllowed() <em>Outgoing Creation Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOutgoingCreationAllowed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OUTGOING_CREATION_ALLOWED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isOutgoingCreationAllowed() <em>Outgoing Creation Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOutgoingCreationAllowed()
	 * @generated
	 * @ordered
	 */
	protected boolean outgoingCreationAllowed = OUTGOING_CREATION_ALLOWED_EDEFAULT;

	/**
	 * The default value of the '{@link #isIncomingCreationAllowed() <em>Incoming Creation Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncomingCreationAllowed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INCOMING_CREATION_ALLOWED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIncomingCreationAllowed() <em>Incoming Creation Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIncomingCreationAllowed()
	 * @generated
	 * @ordered
	 */
	protected boolean incomingCreationAllowed = INCOMING_CREATION_ALLOWED_EDEFAULT;

	/**
	 * The default value of the '{@link #isViewDirectionAlignedWithModel() <em>View Direction Aligned With Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isViewDirectionAlignedWithModel()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VIEW_DIRECTION_ALIGNED_WITH_MODEL_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isViewDirectionAlignedWithModel() <em>View Direction Aligned With Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isViewDirectionAlignedWithModel()
	 * @generated
	 * @ordered
	 */
	protected boolean viewDirectionAlignedWithModel = VIEW_DIRECTION_ALIGNED_WITH_MODEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCreationConstraints() <em>Creation Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationConstraints()
	 * @generated
	 * @ordered
	 */
	protected GenLinkConstraints creationConstraints;

	/**
	 * The default value of the '{@link #isTargetReorientingAllowed() <em>Target Reorienting Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTargetReorientingAllowed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TARGET_REORIENTING_ALLOWED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isTargetReorientingAllowed() <em>Target Reorienting Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTargetReorientingAllowed()
	 * @generated
	 * @ordered
	 */
	protected boolean targetReorientingAllowed = TARGET_REORIENTING_ALLOWED_EDEFAULT;

	/**
	 * The default value of the '{@link #isSourceReorientingAllowed() <em>Source Reorienting Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSourceReorientingAllowed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SOURCE_REORIENTING_ALLOWED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isSourceReorientingAllowed() <em>Source Reorienting Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSourceReorientingAllowed()
	 * @generated
	 * @ordered
	 */
	protected boolean sourceReorientingAllowed = SOURCE_REORIENTING_ALLOWED_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreateCommandClassName() <em>Create Command Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateCommandClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATE_COMMAND_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreateCommandClassName() <em>Create Command Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateCommandClassName()
	 * @generated
	 * @ordered
	 */
	protected String createCommandClassName = CREATE_COMMAND_CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getReorientCommandClassName() <em>Reorient Command Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReorientCommandClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String REORIENT_COMMAND_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReorientCommandClassName() <em>Reorient Command Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReorientCommandClassName()
	 * @generated
	 * @ordered
	 */
	protected String reorientCommandClassName = REORIENT_COMMAND_CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isTreeBranch() <em>Tree Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTreeBranch()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TREE_BRANCH_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isTreeBranch() <em>Tree Branch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTreeBranch()
	 * @generated
	 * @ordered
	 */
	protected boolean treeBranch = TREE_BRANCH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenLink();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenLink> getGenOutgoingLinks() {
		EList<GenLink> r = GenLinkEndOperations.getGenOutgoingLinks(this);
		return new EcoreEList.UnmodifiableEList<GenLink>(this, GMFGenPackage.eINSTANCE.getGenLinkEnd_GenOutgoingLinks(), r.size(), r.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenLink> getGenIncomingLinks() {
		EList<GenLink> r = GenLinkEndOperations.getGenIncomingLinks(this);
		return new EcoreEList.UnmodifiableEList<GenLink>(this, GMFGenPackage.eINSTANCE.getGenLinkEnd_GenIncomingLinks(), r.size(), r.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDiagram getDiagram() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_LINK__DIAGRAM) return null;
		return (GenDiagram)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkModelFacet getModelFacet() {
		return modelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModelFacet(LinkModelFacet newModelFacet, NotificationChain msgs) {
		LinkModelFacet oldModelFacet = modelFacet;
		modelFacet = newModelFacet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__MODEL_FACET, oldModelFacet, newModelFacet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModelFacet(LinkModelFacet newModelFacet) {
		if (newModelFacet != modelFacet) {
			NotificationChain msgs = null;
			if (modelFacet != null)
				msgs = ((InternalEObject)modelFacet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_LINK__MODEL_FACET, null, msgs);
			if (newModelFacet != null)
				msgs = ((InternalEObject)newModelFacet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_LINK__MODEL_FACET, null, msgs);
			msgs = basicSetModelFacet(newModelFacet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__MODEL_FACET, newModelFacet, newModelFacet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenLinkLabel> getLabels() {
		if (labels == null) {
			labels = new EObjectContainmentWithInverseEList<GenLinkLabel>(GenLinkLabel.class, this, GMFGenPackage.GEN_LINK__LABELS, GMFGenPackage.GEN_LINK_LABEL__LINK);
		}
		return labels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOutgoingCreationAllowed() {
		return outgoingCreationAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOutgoingCreationAllowed(boolean newOutgoingCreationAllowed) {
		boolean oldOutgoingCreationAllowed = outgoingCreationAllowed;
		outgoingCreationAllowed = newOutgoingCreationAllowed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__OUTGOING_CREATION_ALLOWED, oldOutgoingCreationAllowed, outgoingCreationAllowed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIncomingCreationAllowed() {
		return incomingCreationAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIncomingCreationAllowed(boolean newIncomingCreationAllowed) {
		boolean oldIncomingCreationAllowed = incomingCreationAllowed;
		incomingCreationAllowed = newIncomingCreationAllowed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__INCOMING_CREATION_ALLOWED, oldIncomingCreationAllowed, incomingCreationAllowed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isViewDirectionAlignedWithModel() {
		return viewDirectionAlignedWithModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setViewDirectionAlignedWithModel(boolean newViewDirectionAlignedWithModel) {
		boolean oldViewDirectionAlignedWithModel = viewDirectionAlignedWithModel;
		viewDirectionAlignedWithModel = newViewDirectionAlignedWithModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__VIEW_DIRECTION_ALIGNED_WITH_MODEL, oldViewDirectionAlignedWithModel, viewDirectionAlignedWithModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenLinkConstraints getCreationConstraints() {
		return creationConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreationConstraints(GenLinkConstraints newCreationConstraints, NotificationChain msgs) {
		GenLinkConstraints oldCreationConstraints = creationConstraints;
		creationConstraints = newCreationConstraints;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS, oldCreationConstraints, newCreationConstraints);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreationConstraints(GenLinkConstraints newCreationConstraints) {
		if (newCreationConstraints != creationConstraints) {
			NotificationChain msgs = null;
			if (creationConstraints != null)
				msgs = ((InternalEObject)creationConstraints).eInverseRemove(this, GMFGenPackage.GEN_LINK_CONSTRAINTS__LINK, GenLinkConstraints.class, msgs);
			if (newCreationConstraints != null)
				msgs = ((InternalEObject)newCreationConstraints).eInverseAdd(this, GMFGenPackage.GEN_LINK_CONSTRAINTS__LINK, GenLinkConstraints.class, msgs);
			msgs = basicSetCreationConstraints(newCreationConstraints, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS, newCreationConstraints, newCreationConstraints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTargetReorientingAllowed() {
		return targetReorientingAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetReorientingAllowed(boolean newTargetReorientingAllowed) {
		boolean oldTargetReorientingAllowed = targetReorientingAllowed;
		targetReorientingAllowed = newTargetReorientingAllowed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__TARGET_REORIENTING_ALLOWED, oldTargetReorientingAllowed, targetReorientingAllowed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSourceReorientingAllowed() {
		return sourceReorientingAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceReorientingAllowed(boolean newSourceReorientingAllowed) {
		boolean oldSourceReorientingAllowed = sourceReorientingAllowed;
		sourceReorientingAllowed = newSourceReorientingAllowed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__SOURCE_REORIENTING_ALLOWED, oldSourceReorientingAllowed, sourceReorientingAllowed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreateCommandClassNameGen() {
		return createCommandClassName;
	}

	public String getCreateCommandClassName() {
		return GenCommonBaseImpl.getValidClassName(getCreateCommandClassNameGen(), this, CREATE_COMMAND_SUFFIX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreateCommandClassName(String newCreateCommandClassName) {
		String oldCreateCommandClassName = createCommandClassName;
		createCommandClassName = newCreateCommandClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__CREATE_COMMAND_CLASS_NAME, oldCreateCommandClassName, createCommandClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReorientCommandClassNameGen() {
		return reorientCommandClassName;
	}

	public String getReorientCommandClassName() {
		return GenCommonBaseImpl.getValidClassName(getReorientCommandClassNameGen(), this, REORIENT_COMMAND_SUFFIX);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReorientCommandClassName(String newReorientCommandClassName) {
		String oldReorientCommandClassName = reorientCommandClassName;
		reorientCommandClassName = newReorientCommandClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__REORIENT_COMMAND_CLASS_NAME, oldReorientCommandClassName, reorientCommandClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTreeBranch() {
		return treeBranch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTreeBranch(boolean newTreeBranch) {
		boolean oldTreeBranch = treeBranch;
		treeBranch = newTreeBranch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK__TREE_BRANCH, oldTreeBranch, treeBranch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenLinkEnd> getSources() {
		final EReference feature = GMFGenPackage.eINSTANCE.getGenLink_Sources();
		if (getModelFacet() == null){
			return new EcoreEList.UnmodifiableEList<GenLinkEnd>(this, feature, 0, new Object[0]);
		}
		EList<GenLinkEnd> r = getCompatibleLinkEnds(getModelFacet().getSourceType());
		return new EcoreEList.UnmodifiableEList<GenLinkEnd>(this, feature, r.size(), r.toArray());
	}
	
	private EList<GenLinkEnd> getCompatibleLinkEnds(GenClass desiredType) {
		if (desiredType == null){
			return ECollections.emptyEList();
		}
		BasicEList<GenLinkEnd> result = new BasicEList<GenLinkEnd>();
		for (GenNode nextNode : getDiagram().getAllNodes()){
			if (nextNode instanceof GenChildLabelNode){
				continue;
			}
			if (canBeLinkEnd(desiredType, nextNode.getModelFacet())){
				result.add(nextNode);
			}
		}
		for (GenLink nextLink : getDiagram().getLinks()){
			if (nextLink.getModelFacet() instanceof TypeModelFacet && canBeLinkEnd(desiredType, (TypeModelFacet)nextLink.getModelFacet())){
				result.add(nextLink);
			}
		}
		return result;
	}
	
	private static boolean canBeLinkEnd(GenClass desiredEndType, TypeModelFacet actualModelFacet){
		if (desiredEndType == null || actualModelFacet == null){
			return false;
		}
		
		GenClass actualMetaclass = actualModelFacet.getMetaClass();
		return actualMetaclass != null && Extras.isSuperTypeOf(desiredEndType.getEcoreClass(), actualMetaclass.getEcoreClass());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenLinkEnd> getTargets() {
		EReference feature = GMFGenPackage.eINSTANCE.getGenLink_Targets();
		if (getModelFacet() == null){
			return new EcoreEList.UnmodifiableEList<GenLinkEnd>(this, feature, 0, new Object[0]);
		}
		EList<GenLinkEnd> r = getCompatibleLinkEnds(getModelFacet().getTargetType());
		return new EcoreEList.UnmodifiableEList<GenLinkEnd>(this, feature, r.size(), r.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenCommonBase> getAssistantSources() {
		if (getModelFacet() == null) {
			return ECollections.emptyEList();
		}
		return getParticipants(getModelFacet().getAssistantSourceTypes());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenCommonBase> getAssistantTargets() {
		if (getModelFacet() == null) {
			return ECollections.emptyEList();
		}
		return getParticipants(getModelFacet().getAssistantTargetTypes());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getCreateCommandQualifiedClassName() {
		return getDiagram().getEditCommandsPackageName() + '.' + getCreateCommandClassName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getReorientCommandQualifiedClassName() {
		return getDiagram().getEditCommandsPackageName() + '.' + getReorientCommandClassName();
	}

	/**
	 * Finds nodes that are based on the specified types.
	 */
	protected EList<GenCommonBase> getParticipants(EList<GenClass> participantTypes) {
		LinkedList<GenNode> participants = new LinkedList<GenNode>();
		for (GenNode node : getDiagram().getAllNodes()) {
			if (node.getModelFacet() != null) {
				GenClass nodeType = node.getModelFacet().getMetaClass();
				if (nodeType != null && nodeType.getEcoreClass() != null) {
					for (GenClass participantType : participantTypes) {
						if (participantType.getEcoreClass() != null && Extras.isSuperTypeOf(participantType.getEcoreClass(), nodeType.getEcoreClass())) {
							participants.add(node);
						}
					}
				}
			}
		}
		return new BasicEList.UnmodifiableEList<GenCommonBase>(participants.size(), participants.toArray());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_LINK__DIAGRAM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_LINK__DIAGRAM, msgs);
			case GMFGenPackage.GEN_LINK__LABELS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLabels()).basicAdd(otherEnd, msgs);
			case GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS:
				if (creationConstraints != null)
					msgs = ((InternalEObject)creationConstraints).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS, null, msgs);
				return basicSetCreationConstraints((GenLinkConstraints)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_LINK__DIAGRAM:
				return eBasicSetContainer(null, GMFGenPackage.GEN_LINK__DIAGRAM, msgs);
			case GMFGenPackage.GEN_LINK__MODEL_FACET:
				return basicSetModelFacet(null, msgs);
			case GMFGenPackage.GEN_LINK__LABELS:
				return ((InternalEList<?>)getLabels()).basicRemove(otherEnd, msgs);
			case GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS:
				return basicSetCreationConstraints(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case GMFGenPackage.GEN_LINK__DIAGRAM:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_DIAGRAM__LINKS, GenDiagram.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_LINK__GEN_OUTGOING_LINKS:
				return getGenOutgoingLinks();
			case GMFGenPackage.GEN_LINK__GEN_INCOMING_LINKS:
				return getGenIncomingLinks();
			case GMFGenPackage.GEN_LINK__DIAGRAM:
				return getDiagram();
			case GMFGenPackage.GEN_LINK__MODEL_FACET:
				return getModelFacet();
			case GMFGenPackage.GEN_LINK__LABELS:
				return getLabels();
			case GMFGenPackage.GEN_LINK__OUTGOING_CREATION_ALLOWED:
				return isOutgoingCreationAllowed();
			case GMFGenPackage.GEN_LINK__INCOMING_CREATION_ALLOWED:
				return isIncomingCreationAllowed();
			case GMFGenPackage.GEN_LINK__VIEW_DIRECTION_ALIGNED_WITH_MODEL:
				return isViewDirectionAlignedWithModel();
			case GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS:
				return getCreationConstraints();
			case GMFGenPackage.GEN_LINK__TARGET_REORIENTING_ALLOWED:
				return isTargetReorientingAllowed();
			case GMFGenPackage.GEN_LINK__SOURCE_REORIENTING_ALLOWED:
				return isSourceReorientingAllowed();
			case GMFGenPackage.GEN_LINK__CREATE_COMMAND_CLASS_NAME:
				return getCreateCommandClassName();
			case GMFGenPackage.GEN_LINK__REORIENT_COMMAND_CLASS_NAME:
				return getReorientCommandClassName();
			case GMFGenPackage.GEN_LINK__TREE_BRANCH:
				return isTreeBranch();
			case GMFGenPackage.GEN_LINK__SOURCES:
				return getSources();
			case GMFGenPackage.GEN_LINK__TARGETS:
				return getTargets();
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
			case GMFGenPackage.GEN_LINK__MODEL_FACET:
				setModelFacet((LinkModelFacet)newValue);
				return;
			case GMFGenPackage.GEN_LINK__LABELS:
				getLabels().clear();
				getLabels().addAll((Collection<? extends GenLinkLabel>)newValue);
				return;
			case GMFGenPackage.GEN_LINK__OUTGOING_CREATION_ALLOWED:
				setOutgoingCreationAllowed((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_LINK__INCOMING_CREATION_ALLOWED:
				setIncomingCreationAllowed((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_LINK__VIEW_DIRECTION_ALIGNED_WITH_MODEL:
				setViewDirectionAlignedWithModel((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS:
				setCreationConstraints((GenLinkConstraints)newValue);
				return;
			case GMFGenPackage.GEN_LINK__TARGET_REORIENTING_ALLOWED:
				setTargetReorientingAllowed((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_LINK__SOURCE_REORIENTING_ALLOWED:
				setSourceReorientingAllowed((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_LINK__CREATE_COMMAND_CLASS_NAME:
				setCreateCommandClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_LINK__REORIENT_COMMAND_CLASS_NAME:
				setReorientCommandClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_LINK__TREE_BRANCH:
				setTreeBranch((Boolean)newValue);
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
			case GMFGenPackage.GEN_LINK__MODEL_FACET:
				setModelFacet((LinkModelFacet)null);
				return;
			case GMFGenPackage.GEN_LINK__LABELS:
				getLabels().clear();
				return;
			case GMFGenPackage.GEN_LINK__OUTGOING_CREATION_ALLOWED:
				setOutgoingCreationAllowed(OUTGOING_CREATION_ALLOWED_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK__INCOMING_CREATION_ALLOWED:
				setIncomingCreationAllowed(INCOMING_CREATION_ALLOWED_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK__VIEW_DIRECTION_ALIGNED_WITH_MODEL:
				setViewDirectionAlignedWithModel(VIEW_DIRECTION_ALIGNED_WITH_MODEL_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS:
				setCreationConstraints((GenLinkConstraints)null);
				return;
			case GMFGenPackage.GEN_LINK__TARGET_REORIENTING_ALLOWED:
				setTargetReorientingAllowed(TARGET_REORIENTING_ALLOWED_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK__SOURCE_REORIENTING_ALLOWED:
				setSourceReorientingAllowed(SOURCE_REORIENTING_ALLOWED_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK__CREATE_COMMAND_CLASS_NAME:
				setCreateCommandClassName(CREATE_COMMAND_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK__REORIENT_COMMAND_CLASS_NAME:
				setReorientCommandClassName(REORIENT_COMMAND_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK__TREE_BRANCH:
				setTreeBranch(TREE_BRANCH_EDEFAULT);
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
			case GMFGenPackage.GEN_LINK__GEN_OUTGOING_LINKS:
				return !getGenOutgoingLinks().isEmpty();
			case GMFGenPackage.GEN_LINK__GEN_INCOMING_LINKS:
				return !getGenIncomingLinks().isEmpty();
			case GMFGenPackage.GEN_LINK__DIAGRAM:
				return getDiagram() != null;
			case GMFGenPackage.GEN_LINK__MODEL_FACET:
				return modelFacet != null;
			case GMFGenPackage.GEN_LINK__LABELS:
				return labels != null && !labels.isEmpty();
			case GMFGenPackage.GEN_LINK__OUTGOING_CREATION_ALLOWED:
				return outgoingCreationAllowed != OUTGOING_CREATION_ALLOWED_EDEFAULT;
			case GMFGenPackage.GEN_LINK__INCOMING_CREATION_ALLOWED:
				return incomingCreationAllowed != INCOMING_CREATION_ALLOWED_EDEFAULT;
			case GMFGenPackage.GEN_LINK__VIEW_DIRECTION_ALIGNED_WITH_MODEL:
				return viewDirectionAlignedWithModel != VIEW_DIRECTION_ALIGNED_WITH_MODEL_EDEFAULT;
			case GMFGenPackage.GEN_LINK__CREATION_CONSTRAINTS:
				return creationConstraints != null;
			case GMFGenPackage.GEN_LINK__TARGET_REORIENTING_ALLOWED:
				return targetReorientingAllowed != TARGET_REORIENTING_ALLOWED_EDEFAULT;
			case GMFGenPackage.GEN_LINK__SOURCE_REORIENTING_ALLOWED:
				return sourceReorientingAllowed != SOURCE_REORIENTING_ALLOWED_EDEFAULT;
			case GMFGenPackage.GEN_LINK__CREATE_COMMAND_CLASS_NAME:
				return CREATE_COMMAND_CLASS_NAME_EDEFAULT == null ? createCommandClassName != null : !CREATE_COMMAND_CLASS_NAME_EDEFAULT.equals(createCommandClassName);
			case GMFGenPackage.GEN_LINK__REORIENT_COMMAND_CLASS_NAME:
				return REORIENT_COMMAND_CLASS_NAME_EDEFAULT == null ? reorientCommandClassName != null : !REORIENT_COMMAND_CLASS_NAME_EDEFAULT.equals(reorientCommandClassName);
			case GMFGenPackage.GEN_LINK__TREE_BRANCH:
				return treeBranch != TREE_BRANCH_EDEFAULT;
			case GMFGenPackage.GEN_LINK__SOURCES:
				return !getSources().isEmpty();
			case GMFGenPackage.GEN_LINK__TARGETS:
				return !getTargets().isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == GenLinkEnd.class) {
			switch (derivedFeatureID) {
				case GMFGenPackage.GEN_LINK__GEN_OUTGOING_LINKS: return GMFGenPackage.GEN_LINK_END__GEN_OUTGOING_LINKS;
				case GMFGenPackage.GEN_LINK__GEN_INCOMING_LINKS: return GMFGenPackage.GEN_LINK_END__GEN_INCOMING_LINKS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == GenLinkEnd.class) {
			switch (baseFeatureID) {
				case GMFGenPackage.GEN_LINK_END__GEN_OUTGOING_LINKS: return GMFGenPackage.GEN_LINK__GEN_OUTGOING_LINKS;
				case GMFGenPackage.GEN_LINK_END__GEN_INCOMING_LINKS: return GMFGenPackage.GEN_LINK__GEN_INCOMING_LINKS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (outgoingCreationAllowed: ");
		result.append(outgoingCreationAllowed);
		result.append(", incomingCreationAllowed: ");
		result.append(incomingCreationAllowed);
		result.append(", viewDirectionAlignedWithModel: ");
		result.append(viewDirectionAlignedWithModel);
		result.append(", targetReorientingAllowed: ");
		result.append(targetReorientingAllowed);
		result.append(", sourceReorientingAllowed: ");
		result.append(sourceReorientingAllowed);
		result.append(", createCommandClassName: ");
		result.append(createCommandClassName);
		result.append(", reorientCommandClassName: ");
		result.append(reorientCommandClassName);
		result.append(", treeBranch: ");
		result.append(treeBranch);
		result.append(')');
		return result.toString();
	}

	public String getClassNamePrefix() {
		// should be consistent with ClassNamingStrategy
		LinkModelFacet aModelFacet = getModelFacet();
		if (aModelFacet instanceof TypeLinkModelFacet) {
			GenClass metaClass = ((TypeLinkModelFacet) aModelFacet).getMetaClass();
			String name = metaClass.getName();
			if (!isEmpty(name)) {
				return getValidClassName(name);
			}
		} else if (aModelFacet instanceof FeatureLinkModelFacet) {
			GenFeature metaFeature = ((FeatureLinkModelFacet) aModelFacet).getMetaFeature();
			String name = metaFeature.getCapName();
			if (!isEmpty(name)) {
				return getValidClassName(metaFeature.getGenClass().getName() + name);
			}
		}
		return CLASS_NAME_PREFIX;
	}
	
	public boolean isSansDomain() {
		return getModelFacet() == null;
	}
} //GenLinkImpl
