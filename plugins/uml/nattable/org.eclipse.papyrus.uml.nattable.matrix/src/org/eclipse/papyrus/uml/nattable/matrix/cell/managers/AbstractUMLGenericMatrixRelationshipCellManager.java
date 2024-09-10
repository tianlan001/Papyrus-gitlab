/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) - thanhliem.phan@all4tec.net - Bug 515806
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Bug 532639
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.cell.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager;
import org.eclipse.papyrus.infra.nattable.manager.cell.IGenericMatrixRelationshipCellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.ui.emf.dialog.NestedEditingDialogContext;
import org.eclipse.papyrus.uml.nattable.matrix.Activator;
import org.eclipse.papyrus.uml.nattable.matrix.messages.Messages;
import org.eclipse.papyrus.uml.nattable.matrix.validator.RelationshipOwnerValidator;
import org.eclipse.papyrus.uml.tools.helper.UMLRelationshipHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Element;

/**
 * Abstract class used to manage Cell Editor for Papyrus UML Relationship Matrixes.
 */
public abstract class AbstractUMLGenericMatrixRelationshipCellManager extends AbstractCellManager implements IGenericMatrixRelationshipCellManager {

	/**
	 * the EClass supported by this CellManager
	 */
	private EClass managedElement;

	/**
	 * The UML relationship helper
	 */
	protected UMLRelationshipHelper helper;

	/**
	 * the default filter to use to filter cell contents
	 */
	private IBooleanEObjectExpression defaultFilter = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

	/**
	 *
	 * Constructor.
	 *
	 * @param managedElement
	 *            the EClass supported by this cell manager
	 */
	public AbstractUMLGenericMatrixRelationshipCellManager(final EClass managedElement) {
		this.managedElement = managedElement;
		this.helper = getOrCreateUMLRelationshipHelper();
	}

	/**
	 *
	 * @return
	 * 		the relationship helper to use
	 */
	protected UMLRelationshipHelper getOrCreateUMLRelationshipHelper() {
		if (null == this.helper) {
			this.helper = new UMLRelationshipHelper();
		}
		return this.helper;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#handles(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param manager
	 * @return
	 */
	@Override
	public boolean handles(final Object columnElement, final Object rowElement, final INattableModelManager manager) {
		boolean handle = manager instanceof IMatrixTableWidgetManager && isMatchingEditedElement(manager.getTable()) && manager.getTable().getContext() instanceof Element;
		if (handle) {
			handle = null != getElementTypeToCreate(getCellEditorConfiguration((IMatrixTableWidgetManager) manager));
		}
		if (handle) {
			final Object realColumn = AxisUtils.getRepresentedElement(columnElement);
			final Object realRow = AxisUtils.getRepresentedElement(rowElement);
			handle = realColumn instanceof Element && realRow instanceof Element;
		}
		return handle;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#isCellEditable(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param manager
	 * @return
	 * 		<code>true</code> if the cell displayed in the table must be editable for the user. In case of too many ends or too many links to display, the cell won't be editable
	 */
	@Override
	public final boolean isCellEditable(final Object columnElement, final Object rowElement, final INattableModelManager manager) {
		final Element realColumn = (Element) AxisUtils.getRepresentedElement(columnElement); // we already know that it is UML Element due to the handles method
		final Element realRow = (Element) AxisUtils.getRepresentedElement(rowElement);
		boolean isEditable = isCellEditableIgnoringCurrentValue(columnElement, rowElement, manager);
		if (isEditable) {
			final List<Element> currentValues = getMatchingRelationships(realColumn, realRow, manager);
			isEditable = currentValues.size() < 2;
			if (currentValues.size() == 1 && canHaveMoreThan2Ends()) {
				isEditable = getNumberOfEnds(currentValues.get(0)) == 2;
			}
		}

		// we don't check if the source and target element have the required applied stereotype and other stuff, we delegate to the Service type
		if (isEditable) {
			// we check if the set value command is executable with the owner calculated by Papyrus
			Command cmd = getSetValueCommand(TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable()), columnElement, rowElement, Boolean.TRUE, manager, true);
			isEditable = null == cmd ? false : cmd.canExecute();
		}
		return isEditable;
	}

	/**
	 *
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param manager
	 *            the table manager
	 * @return
	 * 		<code>true</code> if the cell must be editable according to the arguments;
	 */
	protected final boolean isCellEditableIgnoringCurrentValue(final Object columnElement, final Object rowElement, final INattableModelManager manager) {
		final Element realColumn = (Element) AxisUtils.getRepresentedElement(columnElement); // we already know that it is UML Element due to the handles method
		final Element realRow = (Element) AxisUtils.getRepresentedElement(rowElement);
		final IMatrixTableWidgetManager matrixManager = (IMatrixTableWidgetManager) manager;
		final GenericRelationshipMatrixCellEditorConfiguration conf = getCellEditorConfiguration(matrixManager);

		boolean isEditable = false;
		switch (conf.getDirection()) {
		case NONE:
			if (isDirectedRelationship()) {
				isEditable = false;
			} else {
				isEditable = (matchingSourceFeatureType(realRow) && matchingTargetFeatureType(realColumn))
						&& /* or || ?? */ matchingSourceFeatureType(realColumn) && matchingTargetFeatureType(realRow);
			}
			break;
		case FROM_ROW_TO_COLUMN:
			isEditable = matchingSourceFeatureType(realRow) && matchingTargetFeatureType(realColumn);
			break;
		case FROM_COLUMN_TO_ROW:
			isEditable = matchingSourceFeatureType(realColumn) && matchingTargetFeatureType(realRow);
			break;
		default:
			break;
		}
		return isEditable;
	}

	/**
	 *
	 * @param axisObject
	 * @return
	 * 		<code>true</code> if the object can be used as source
	 */
	protected boolean matchingSourceFeatureType(Object element) {
		return getRelationshipSourceFeature().getEType().isInstance(element);
	}

	/**
	 *
	 * @param axisObject
	 * @return
	 * 		<code>true</code> if the object can be used as target
	 */
	protected boolean matchingTargetFeatureType(final Element element) {
		return getRelationshipTargetFeature().getEType().isInstance(element);
	}

	/**
	 * Returns the best owner for the created relationship, according to the value of {@link GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerStrategy()}
	 */
	private final Element getBestOwner(final IMatrixTableWidgetManager tableManager, final ElementTypeConfiguration elementTypeConfiguration, final Element source, final Element target, final EObject context) {
		final GenericRelationshipMatrixCellEditorConfiguration conf = getCellEditorConfiguration(tableManager);
		if (null != conf) {
			switch (conf.getRelationshipOwnerStrategy()) {
			case ROW_AS_OWNER:
				return source;
			case ROW_OWNER:
				return source.getOwner();
			case COLUMN_AS_OWNER:
				return target;
			case COLUMN_OWNER:
				return target.getOwner();
			case TABLE_CONTEXT:
				return (Element) context; // brutal cast, because, we don't yet support other usecase than Element
			case OTHER:
				final IWrapper wrapper = conf.getRelationshipOwner();
				if (null != wrapper && wrapper.getElement() instanceof Element) {
					return (Element) wrapper.getElement();
				} else {
					return null;
				}
			case DEFAULT:// default case
			default:
				// default case

			}
		}
		return this.helper.getBestOwner(elementTypeConfiguration, source, target, (Element) context);

	}

	/**
	 *
	 * @param elementTypeConfiguration
	 *            the element type configuration declared in the table cell editor configuration
	 * @param source
	 *            the source of the relationship to create
	 * @param target
	 *            the target of the relationship to create
	 * @param context
	 *            the context of the table
	 * @return
	 * 		the element which should own the relationship after its creation
	 * 
	 */
	protected Element getBestOwner(final ElementTypeConfiguration elementTypeConfiguration, final Element source, final Element target, final EObject context) {
		return this.helper.getBestOwner(elementTypeConfiguration, source, target, (Element) context);
	}

	/**
	 *
	 * @param configuration
	 *            the cell editor configuration declared in the edited table
	 * @return
	 * 		the element type to use as described in the table configuration
	 */
	protected IElementType getElementTypeToCreate(final GenericRelationshipMatrixCellEditorConfiguration configuration) {
		final ElementTypeConfiguration editedElement = configuration.getEditedElement();
		if (null != editedElement) {
			return ElementTypeRegistry.getInstance().getType(editedElement.getIdentifier());
		}
		// editedElement.getIconEntry().getBundleId()
		// IElementType a = null;
		// a.getIconURL()
		return null;
	}

	/**
	 *
	 * @param configuration
	 *            the cell editor configuration declared in the edited table
	 * @return
	 * 		the matcher to use, or <code>null</code> if not defined
	 *
	 */
	protected IElementMatcher getElementTypeMatcher(final GenericRelationshipMatrixCellEditorConfiguration configuration) {
		final IElementType elementType = getElementTypeToCreate(configuration);
		return elementType instanceof ISpecializationType ? ((ISpecializationType) elementType).getMatcher() : null;
	}

	/**
	 *
	 * @return
	 * 		<code>true</code> if the managed EClass relationship can have more than 2 ends, and <code>false</code> if not OR if the relationship is not managed
	 */
	protected boolean canHaveMoreThan2Ends() {
		return this.helper.canHaveMoreThan2Ends(this.managedElement);
	}

	/**
	 *
	 * @return
	 * 		<code>true</code> if the managed EClass relationship is a directed relationship, and <code>false</code> if not OR if the relationship is not managed
	 */
	protected boolean isDirectedRelationship() {
		return this.helper.isDirectedRelationship(this.managedElement);
	}


	/**
	 *
	 * @param manager
	 *            the current edited table
	 * @return
	 * 		<code>true</code> if the current class manages the elementType configuration declared in the table
	 */
	protected final boolean isMatchingEditedElement(final Table table) {
		final ElementTypeConfiguration elTypeConf = getEditedElementKind(table);
		return handles(elTypeConf);
	}

	/**
	 *
	 * @param elTypeConf
	 *            an element type
	 * @return
	 * 		<code>true</code> if the current class manages this elementTypes
	 */
	protected final boolean handles(final ElementTypeConfiguration elTypeConf) {
		if (elTypeConf instanceof MetamodelTypeConfiguration) {
			return ((MetamodelTypeConfiguration) elTypeConf).getEClass() == this.managedElement;
		}
		if (elTypeConf instanceof SpecializationTypeConfiguration) {
			if (((SpecializationTypeConfiguration) elTypeConf).getSpecializedTypes().size() == 1) {
				return handles(((SpecializationTypeConfiguration) elTypeConf).getSpecializedTypes().get(0));
			}
		}
		return false;
	}

	/**
	 *
	 * @param manager
	 *            the matrix widget manager
	 *
	 * @return
	 * 		<code>true</code> if the table owns a {@link GenericRelationshipMatrixCellEditorConfiguration} and <code>false</code> otherwise
	 */
	protected final boolean hasGenericRelationshipMatrixCellEditorConfiguration(final IMatrixTableWidgetManager manager) {
		return getCellEditorConfiguration(manager) != null;
	}

	/**
	 *
	 * @param manager
	 *            the matrix widget manager
	 * @return
	 * 		the cell editor configuration registered in the table or <code>null</code> when it is not defined
	 */
	protected final GenericRelationshipMatrixCellEditorConfiguration getCellEditorConfiguration(final IMatrixTableWidgetManager manager) {
		final ICellEditorConfiguration conf = manager.getTable().getOwnedCellEditorConfigurations();
		if (conf instanceof GenericRelationshipMatrixCellEditorConfiguration) {
			return (GenericRelationshipMatrixCellEditorConfiguration) conf;
		}
		return null;
	}

	/**
	 *
	 * @param manager
	 *            the current edited table
	 * @return
	 * 		the elementType to manage according to the table configuration or <code>null</code> when it is not defined
	 */
	protected ElementTypeConfiguration getEditedElementKind(final Table table) {
		final ICellEditorConfiguration conf = table.getOwnedCellEditorConfigurations();
		if (conf instanceof GenericRelationshipMatrixCellEditorConfiguration) {
			return ((GenericRelationshipMatrixCellEditorConfiguration) conf).getEditedElement();
		}
		return null;
	}

	/**
	 *
	 * @param relationship
	 *            a relationship
	 * @return
	 * 		the feature used for the sources of the relationship
	 */
	protected EStructuralFeature getRelationshipSourceFeature() {
		return this.helper.getRelationshipSourceFeature(this.managedElement);
	}

	/**
	 *
	 * @param relationship
	 *            a relationship
	 * @return
	 * 		the feature used for the sources of the relationship
	 */
	protected EStructuralFeature getRelationshipTargetFeature() {
		return this.helper.getRelationshipTargetFeature(this.managedElement);
	}

	/**
	 *
	 * @param relationship
	 *            a relationship
	 * @return
	 * 		the sources of the relationship
	 */
	protected Collection<? extends Element> getSources(final Element relationship) {
		return this.helper.getSources(relationship);
	}

	/**
	 *
	 * @param relationship
	 *            a relationship
	 * @return
	 * 		the targets of the relationship
	 */
	protected Collection<? extends Element> getTargets(final Element relationship) {
		return this.helper.getTargets(relationship);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#doGetValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param tableManager
	 * @return
	 */
	@Override
	protected Object doGetValue(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		if (isCellEditableIgnoringCurrentValue(columnElement, rowElement, tableManager)) {
			// we already know that column and rows are UML Elemenr due to the handles methods
			return getMatchingRelationships((Element) AxisUtils.getRepresentedElement(columnElement), (Element) AxisUtils.getRepresentedElement(rowElement), tableManager);
		}
		return CellHelper.getUnsupportedCellContentsText();
	}

	/**
	 *
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		the relationship to display according to the these parameter and taking account the {@link GenericRelationshipMatrixCellEditorConfiguration} of the table
	 */
	protected List<Element> getMatchingRelationships(final Element columnElement, final Element rowElement, final INattableModelManager tableManager) {
		final Object realColumn = AxisUtils.getRepresentedElement(columnElement);
		final Object realRow = AxisUtils.getRepresentedElement(rowElement);
		final IMatrixTableWidgetManager matrixManager = (IMatrixTableWidgetManager) tableManager;
		List<Object> combined = new ArrayList<Object>();
		combined.add(realColumn);
		combined.add(realRow);
		Map<EObject, Collection<Setting>> refs = EcoreUtil.CrossReferencer.find(combined);
		final List<Element> matchingRelationships = new ArrayList<Element>();
		final GenericRelationshipMatrixCellEditorConfiguration conf = getCellEditorConfiguration(matrixManager);
		final MatrixRelationShipDirection dir = conf.getDirection();
		IBooleanEObjectExpression cellContentFilter = conf.getCellContentsFilter();
		if (null == cellContentFilter) {
			cellContentFilter = this.defaultFilter;
		}
		for (final Element current : refs.keySet().stream().filter(Element.class::isInstance).map(Element.class::cast).collect(Collectors.toList())) {
			final IElementMatcher matcher = getElementTypeMatcher(conf);
			boolean isValid = matcher != null ? matcher.matches(current) : true;
			if (isValid) {
				isValid = current.eClass() == getManagedRelationship() && cellContentFilter.evaluate(current);
			}
			if (isValid) {
				switch (dir) {
				case NONE:
					break;
				case FROM_ROW_TO_COLUMN:
					isValid = getSources(current).contains(realRow) && getTargets(current).contains(realColumn);
					break;
				case FROM_COLUMN_TO_ROW:
					isValid = getSources(current).contains(realColumn) && getTargets(current).contains(realRow);
					break;
				default:
					break;

				}
			}
			if (isValid) {
				matchingRelationships.add(current);
			}
		}
		return matchingRelationships;
	}

	/**
	 *
	 * @param relationship
	 *            a relationship
	 * @return
	 * 		the number of ends for the given relationship
	 */
	protected int getNumberOfEnds(final Element relationship) {
		return this.helper.getNumberOfEnds(relationship);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#getSetValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param tableManager
	 * @return
	 * 		the setValueCommand, using the relationship owner defined by the value of the {@link GenericRelationshipMatrixCellEditorConfiguration#MatrixRelationShipOwnerStrategy}
	 */
	@Override
	public Command getSetValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager) {
		final Element realColumn = (Element) AxisUtils.getRepresentedElement(columnElement);
		final Element realRow = (Element) AxisUtils.getRepresentedElement(rowElement);

		final GenericRelationshipMatrixCellEditorConfiguration conf = getCellEditorConfiguration((IMatrixTableWidgetManager) tableManager);
		final MatrixRelationShipDirection dir = conf.getDirection();
		final EObject owner;
		final EObject tableContext = tableManager.getTable().getContext();

		switch (dir) {
		case FROM_ROW_TO_COLUMN:
			owner = getBestOwner((IMatrixTableWidgetManager) tableManager, conf.getEditedElement(), realRow, realColumn, tableContext);
			break;
		case FROM_COLUMN_TO_ROW:
			owner = getBestOwner((IMatrixTableWidgetManager) tableManager, conf.getEditedElement(), realColumn, realRow, tableContext);
			break;
		default:
			owner = null;
		}

		return getSetValueCommand(domain, columnElement, rowElement, newValue, tableManager, owner);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#getSetValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param tableManager
	 * @param usePapyrusDefaultOwner
	 *            if <code>true</code> we return the set value command using the owner calculated by Papyrus, if <code>false</code>, we use the owner strategy defined by the user, reading
	 *            {@link GenericRelationshipMatrixCellEditorConfiguration#MatrixRelationShipOwnerStrategy}
	 * @return
	 * 		the command to use to set the cell value, using the calculated owner, according to the last argument ({@link usePapyrusDefaultOwner}) value
	 */
	@SuppressWarnings("unused")
	private final Command getSetValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager, final boolean usePapyrusDefaultOwner) {
		final Element realColumn = (Element) AxisUtils.getRepresentedElement(columnElement);
		final Element realRow = (Element) AxisUtils.getRepresentedElement(rowElement);

		final GenericRelationshipMatrixCellEditorConfiguration conf = getCellEditorConfiguration((IMatrixTableWidgetManager) tableManager);
		final MatrixRelationShipDirection dir = conf.getDirection();
		final EObject owner;
		final EObject tableContext = tableManager.getTable().getContext();
		switch (dir) {
		case FROM_ROW_TO_COLUMN:
			owner = usePapyrusDefaultOwner ?
			// owner defined by Papyrus code
					getBestOwner(conf.getEditedElement(), realRow, realColumn, tableContext) :
					// owner defined by {@link GenericRelationshipMatrixCellEditorConfiguration#MatrixRelationShipOwnerStrategy}
					getBestOwner((IMatrixTableWidgetManager) tableManager, conf.getEditedElement(), realRow, realColumn, tableContext);
			break;
		case FROM_COLUMN_TO_ROW:
			owner = usePapyrusDefaultOwner ?
			// owner defined by Papyrus code
					getBestOwner(conf.getEditedElement(), realColumn, realRow, tableContext) :
					// owner defined by {@link GenericRelationshipMatrixCellEditorConfiguration#MatrixRelationShipOwnerStrategy}
					getBestOwner((IMatrixTableWidgetManager) tableManager, conf.getEditedElement(), realColumn, realRow, tableContext);
			break;
		default:
			owner = null;
		}

		return getSetValueCommand(domain, columnElement, rowElement, newValue, tableManager, owner);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.AbstractCellManager#getSetValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param tableManager
	 * @param owner
	 *            the owner of the created relationship
	 * @return
	 * 		the command to use to set the cell value, using the owner defined as parameter's method in case of relationship's creation
	 */
	private final Command getSetValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager, final EObject owner) {
		final Element realColumn = (Element) AxisUtils.getRepresentedElement(columnElement);
		final Element realRow = (Element) AxisUtils.getRepresentedElement(rowElement);

		if (Boolean.FALSE.equals(newValue)) {
			final List<Element> r = getMatchingRelationships(realColumn, realRow, tableManager);
			if (r.size() == 1) {// according to the isCellEditable there are only 1 relationship
				final DestroyElementRequest request = new DestroyElementRequest(r.get(0), false);
				final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(r.get(0));
				final ICommand cmd = provider.getEditCommand(request);
				if (null != cmd && cmd.canExecute()) {
					return new GMFtoEMFCommandWrapper(cmd);
				}
			}
		}
		if (Boolean.TRUE.equals(newValue)) {
			final IMatrixTableWidgetManager matrixManager = (IMatrixTableWidgetManager) tableManager;
			final GenericRelationshipMatrixCellEditorConfiguration conf = getCellEditorConfiguration(matrixManager);
			final MatrixRelationShipDirection dir = conf.getDirection();
			final IElementType elementType = getElementTypeToCreate(conf);
			final CreateRelationshipRequest request;
			switch (dir) {
			case FROM_ROW_TO_COLUMN:
				request = new CreateRelationshipRequest(owner, realRow, realColumn, elementType);
				break;
			case FROM_COLUMN_TO_ROW:
				request = new CreateRelationshipRequest(owner, realColumn, realRow, elementType);
				break;
			default:
				request = null;
			}

			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(owner);
			final ICommand cmd = provider.getEditCommand(request);

			final RelationshipOwnerValidator validator = new RelationshipOwnerValidator(matrixManager);
			final IStatus validationStatus = validator.validate(owner);

			// we decided to trust the validation status, instead of the returned command which could be customized and create the relationship in a more good owner than chosen one!
			if (false == validationStatus.isOK()) {
				final ILabelProvider labelProvider = getLabelProvider(realRow);
				final String rowLabel = null != labelProvider ? labelProvider.getText(realRow) : realRow.toString();
				final String columnLabel = null != labelProvider ? labelProvider.getText(realColumn) : realColumn.toString();
				final String ownerLabel = null != labelProvider ? labelProvider.getText(owner) : owner.toString();

				final String str = NLS.bind(Messages.AbstractUMLGenericMatrixRelationshipCellManager_RelationshipCanBeCreated,
						new String[] { elementType.getDisplayName(), rowLabel, columnLabel, ownerLabel, validationStatus.getMessage() });

				return new GMFtoEMFCommandWrapper(new OpenMessageDialogCommand(domain, Messages.AbstractUMLGenericMatrixRelationshipCellManager_CreateRelationshipMessageDialogTitle, str, MessageDialog.ERROR));
			}
			if (null != cmd && cmd.canExecute()) {
				return new GMFtoEMFCommandWrapper(cmd);
			}
		}
		return null;
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

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.IGenericMatrixRelationshipCellManager#getManagedRelationship()
	 *
	 * @return
	 */
	@Override
	public EClass getManagedRelationship() {
		return this.managedElement;
	}

	/**
	 * 
	 * @author Vincent LORENZO
	 * 
	 *         This class allows to open a message dialog and to return a command result consistent with the user answer or the MessageDialog kind
	 *         TODO move me in an util plugin
	 */
	private static final class OpenMessageDialogCommand extends AbstractTransactionalCommand {

		/**
		 * The title of the dialog
		 */
		private final String dialogTitle;

		/**
		 * the message of the dialog
		 */
		private final String message;

		/**
		 * the type of the dialog
		 */
		private final int dialogType;


		/**
		 * 
		 * Constructor.
		 *
		 * @param domain
		 *            the editing domain
		 * @param dialogTitle
		 *            the dialog title
		 * @param message
		 *            the message to display
		 * @param dialogType
		 *            the style of dialog:
		 *            <ul>
		 *            <li>MessageDialog.ERROR: the command result will return a {@link IStatus#ERROR}</li>
		 *            <li>MessageDialog.INFORMATION:the command result will return a {@link IStatus#OK}</li>
		 *            <li>MessageDialog.QUESTION: the command result will return a {@link IStatus#OK}, with a boolean value (<code>true</code> for Yes button, and <code>false</code> for No button)</li></li>
		 *            <li>MessageDialog.WARNING: the command result will return a {@link IStatus#OK}</li>
		 *            <li>MessageDialog.CONFIRM: the command result will return a {@link IStatus#CANCEL} for cancel button and {@link IStatus#OK} for OK button</li>
		 *            </ul>
		 * 
		 */
		public OpenMessageDialogCommand(final TransactionalEditingDomain domain, final String dialogTitle, final String message, final int dialogType) {
			super(domain, null, null);
			this.message = message;
			this.dialogTitle = dialogTitle;
			this.dialogType = dialogType;
			switch (this.dialogType) {
			case MessageDialog.ERROR:
			case MessageDialog.INFORMATION:
			case MessageDialog.QUESTION:
			case MessageDialog.WARNING:
			case MessageDialog.CONFIRM:
				break;// all is ok
			default:
				Assert.isLegal(false, NLS.bind("The value {0} is not supported as iconType by this dialog", this.dialogType)); //$NON-NLS-1$
				break;
			}
		}


		/**
		 * 
		 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
		 *
		 * @param monitor
		 * @param info
		 * @return
		 * @throws ExecutionException
		 */
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			final Shell shell = Display.getDefault().getActiveShell();

			switch (this.dialogType) {
			case MessageDialog.NONE:
				// not supported
				break;

			case MessageDialog.ERROR:
				MessageDialog.openError(shell, dialogTitle, message);
				return CommandResult.newErrorCommandResult(message); // only ok button
			case MessageDialog.INFORMATION:
				MessageDialog.openInformation(shell, dialogTitle, message);// only ok button
				return CommandResult.newOKCommandResult();
			case MessageDialog.QUESTION:
				boolean res = MessageDialog.openQuestion(shell, dialogTitle, message);// return true/false for yes/no button
				return CommandResult.newOKCommandResult(res);

			case MessageDialog.WARNING:
				MessageDialog.openWarning(shell, dialogTitle, message);// only OK button
				return CommandResult.newOKCommandResult();

			case MessageDialog.CONFIRM:
				res = MessageDialog.openConfirm(shell, dialogTitle, message);// ok and cancel button
				if (res) {
					return CommandResult.newOKCommandResult();
				}
				return CommandResult.newCancelledCommandResult();

			default:
				// nothing to do
				// not possible with the current implementation
				break;
			}
			// not possible with the current implementation
			return new CommandResult(Status.OK_STATUS);
		};

	}
}
