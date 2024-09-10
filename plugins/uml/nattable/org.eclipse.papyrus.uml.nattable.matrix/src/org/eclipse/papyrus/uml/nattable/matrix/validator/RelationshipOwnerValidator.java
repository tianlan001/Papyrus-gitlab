/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   CEA LIST - bug 532639  [Table][Matrix]System shall enable to specify the creation location of relationships created using a matrix editor.
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.validator;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.ui.emf.dialog.NestedEditingDialogContext;
import org.eclipse.papyrus.uml.nattable.matrix.Activator;
import org.eclipse.papyrus.uml.nattable.matrix.messages.Messages;

/**
 * @author Vincent LORENZO
 *
 *         This class provides a validate method to check the used owner during a relationship's creation. We assume the checked object is compliant with the strategy defined by the user.
 *         If not, the returned message in the returned status will be invalid!!!
 *
 */
public class RelationshipOwnerValidator implements IValidator {


	/**
	 * the cell editor configuration of the current matrix
	 */
	private final GenericRelationshipMatrixCellEditorConfiguration conf;

	/**
	 * Constructor.
	 *
	 */
	public RelationshipOwnerValidator(final IMatrixTableWidgetManager matrixManager) {
		this.conf = (GenericRelationshipMatrixCellEditorConfiguration) matrixManager.getTable().getOwnedCellEditorConfigurations();
	}

	/**
	 * 
	 * @param message
	 *            the message to set in the error IStatus
	 * @return
	 * 		the created IStatus error
	 */
	private final IStatus createErrorStatus(final String message) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, message);
	}

	/**
	 * @see org.eclipse.core.databinding.validation.IValidator#validate(java.lang.Object)
	 *
	 * @param value
	 *            a value to validate. We assume the value to check is consistent with the owner strategy mode defined in the {@link GenericRelationshipMatrixCellEditorConfiguration}.
	 *            If not the returned message will be probably invalid!!!!
	 * @return
	 */
	@Override
	public IStatus validate(final Object value) {
		EObject realEObject = null;
		if (value instanceof EObjectWrapper) {
			realEObject = ((EObjectWrapper) value).getElement();
		} else if (value instanceof EObjectTreeItemAxis) {
			realEObject = ((EObjectTreeItemAxis) value).getElement();
		} else if (value instanceof EObject) {
			realEObject = (EObject) value;
		}

		if (null == realEObject) {
			return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_ElementCantBeResolvedAsEObject, value));
		}

		final IStatus relationshipDefinition = hasADefinedRelationship();
		if (false == relationshipDefinition.isOK()) {
			return relationshipDefinition;
		}

		boolean hasAValidFeature = hasFeatureAcceptingRelationShip(realEObject);
		// We assume the value to check is consistent with the owner strategy mode defined in the {@link GenericRelationshipMatrixCellEditorConfiguration}.
		// If not the returned message will be probably invalid!!!!
		if (false == hasAValidFeature) {
			final ILabelProvider labelProvider = getLabelProvider(realEObject);
			final String realObjectLabel = null != labelProvider ? labelProvider.getText(realEObject) : realEObject.toString();

			switch (conf.getRelationshipOwnerStrategy()) {
			case COLUMN_AS_OWNER:
				return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_ColumnCantbeUsedAsOwner, realObjectLabel));
			case COLUMN_OWNER:
				return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_ColumnOwnerCantbeUsedAsOwner, realObjectLabel));
			case DEFAULT:
				// we hope it never appears!
				return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_ItIsAPapyrusBug, realObjectLabel));
			case OTHER:
				return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_TheChosenElementCantBeUsedAsOwner, realObjectLabel));
			case ROW_AS_OWNER:
				return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_RowCantbeUsedAsOwner, realObjectLabel));
			case ROW_OWNER:
				return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_RowOwnerCantbeUsedAsOwner, realObjectLabel));
			case TABLE_CONTEXT:
				return createErrorStatus(NLS.bind(Messages.RelationshipOwnerValidator_TableContextCantbeUsedAsOwner, realObjectLabel));
			default:
				break;
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * 
	 * @return
	 * 		a IStatus indicating if the relationship is defined and valid or not
	 */
	private final IStatus hasADefinedRelationship() {
		final ElementTypeConfiguration etc = this.conf.getEditedElement();
		if (null == etc) {
			
			return createErrorStatus(Messages.RelationshipOwnerValidator_NoRelationshipDefined);
		}
		
		final EClass eclass = getBaseMetamodelEClass(etc);
		if (null != eclass) {
		if (eclass.isAbstract()) {
			return createErrorStatus(Messages.RelationshipOwnerValidator_ChosenRelationshipIsAbstract); // very unlucky case, probably impossible
		}
		}
		return Status.OK_STATUS;
	}


	/**
	 * 
	 * @param wantedOwner
	 *            the wanted owner for the created relationship
	 * @return
	 * 		<code>true</code> if the wanted owner has a feature accepting the created relationship
	 */
	private final boolean hasFeatureAcceptingRelationShip(final EObject wantedOwner) {
		Assert.isNotNull(wantedOwner);
		final ElementTypeConfiguration etc = this.conf.getEditedElement();
		final EClass eclass = getBaseMetamodelEClass(etc);
		if (null != eclass) {
			final EObject dummyRelationship = eclass.getEPackage().getEFactoryInstance().create(eclass);
			for (EStructuralFeature current : wantedOwner.eClass().getEAllContainments()) {
				if (current.getEType().isInstance(dummyRelationship)) {
					return true;
				}
			}
		}
		return false;
	}
	

	// TODO : move these 3 methods in a util class. Such methods already exists in UMLRelationshipHelper
	/**
	 * 
	 * @param elementTypeConfiguration
	 *            an element type configuration
	 * @return
	 * 		the base EClass for this element type configuration(when it is unique) and <code>null</code> otherwise
	 * @since 1.0.100
	 * 
	 */
	private EClass getBaseMetamodelEClass(final ElementTypeConfiguration elementTypeConfiguration) {
		return getBaseMetamodelTypeConfiguration(elementTypeConfiguration) != null ? getBaseMetamodelTypeConfiguration(elementTypeConfiguration).getEClass() : null;
	}

	/**
	 * 
	 * @param elementTypeConfiguration
	 *            an element type configuration
	 * @return
	 * 		the base metamodel type configuration (when it is unique) and <code>null</code> otherwise
	 * @since 1.0.100
	 */
	private MetamodelTypeConfiguration getBaseMetamodelTypeConfiguration(final ElementTypeConfiguration elementTypeConfiguration) {
		if (elementTypeConfiguration instanceof MetamodelTypeConfiguration) {
			return (MetamodelTypeConfiguration) elementTypeConfiguration;
		} else if (elementTypeConfiguration instanceof SpecializationTypeConfiguration) {
			return getBaseMetamodelTypeConfiguration((SpecializationTypeConfiguration) elementTypeConfiguration);
		}
		return null;
	}

	/**
	 * 
	 * @param elementTypeConfiguration
	 *            an element type configuration
	 * @return
	 * 		the base metamodel type configuration (when it is unique) and <code>null</code> otherwise
	 * @since 1.0.100
	 */
	private MetamodelTypeConfiguration getBaseMetamodelTypeConfiguration(final SpecializationTypeConfiguration elementTypeConfiguration) {
		return 1 == elementTypeConfiguration.getSpecializedTypes().size() ? getBaseMetamodelTypeConfiguration(elementTypeConfiguration.getSpecializedTypes().get(0)) : null;
	}




	/**
	 * 
	 * @param eobject
	 *            an eobject
	 * @return
	 * 		the label provider to use
	 */
	private final ILabelProvider getLabelProvider(final EObject eobject) {
		LabelProviderService lpSvc = null;
		try {
			lpSvc = (eobject.eResource() != null)
					? ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, eobject)
					: ServiceUtilsForResourceSet.getInstance().getService(LabelProviderService.class, NestedEditingDialogContext.getInstance().getResourceSet());
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		if (null != lpSvc) {
			return lpSvc.getLabelProvider();
		}
		return null;
	}

}
