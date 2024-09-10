/*****************************************************************************
 * Copyright (c) 2013, 2020 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.NattablecellPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.Problem;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 *
 * @author Vincent Lorenzo
 *
 */
public class ResolvedProblemHandler extends AbstractTableHandler {



	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final TransactionalEditingDomain domain = getTableEditingDomain();

		Problem problemToDestroy = getProblemToDestroy(event);
		DestroyElementRequest request = new DestroyElementRequest(domain, problemToDestroy, false);
		final Cell cell = (Cell) problemToDestroy.eContainer();
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(cell);
		CompositeCommand composite = new CompositeCommand("Destroy Problem Command"); //$NON-NLS-1$
		composite.add(provider.getEditCommand(request));

		// TODO : improve me and move me into an edit helper when we will have customization for the cell
		composite.add(new AbstractTransactionalCommand(domain, "Clean Table Model : remove empty Cell", null) { //$NON-NLS-1$

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				boolean isEmpty = true;
				if (cell.getProblems().size() == 0) {
					Collection<EStructuralFeature> featureToIgnore = new ArrayList<>();
					featureToIgnore.add(EcorePackage.eINSTANCE.getEModelElement_EAnnotations());
					featureToIgnore.add(NattablecellPackage.eINSTANCE.getCell_ColumnWrapper());
					featureToIgnore.add(NattablecellPackage.eINSTANCE.getCell_RowWrapper());
					Collection<EStructuralFeature> allFeatures = new ArrayList<>(cell.eClass().getEAllStructuralFeatures());
					allFeatures.removeAll(featureToIgnore);
					for (EStructuralFeature eStructuralFeature : allFeatures) {
						if (eStructuralFeature.isMany()) {
							if (!((Collection<?>) cell.eGet(eStructuralFeature)).isEmpty()) {
								isEmpty = false;
							}
						} else {
							if (cell.eGet(eStructuralFeature) != eStructuralFeature.getDefaultValue()) {
								isEmpty = false;
							}
						}

					}
				}
				if (isEmpty) {
					DestroyElementRequest request = new DestroyElementRequest(domain, cell, false);
					// final Cell cell = (Cell)problemToDestroy.eContainer();
					IElementEditService provider = ElementEditServiceUtils.getCommandProvider(cell.eContainer());
					provider.getEditCommand(request).execute(null, null);
				}
				return null;
			}
		});
		Command cmd = new GMFtoEMFCommandWrapper(composite);
		domain.getCommandStack().execute(cmd);
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTreeTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			calculatedValue = getProblemToDestroy(evaluationContext) != null;
		}
		return calculatedValue;
	}

	/**
	 *
	 * @param evaluationContextOrExecutionEvent
	 *            an evaluation context (coming from setEnable(Object evaluationContext) or an ExecutionEvent (coming from execute(ExecutionEvent e)
	 * @return
	 */
	private Problem getProblemToDestroy(Object evaluationContextOrExecutionEvent) {
		TableSelectionWrapper wrapper = getTableSelectionWrapper();
		if (isEnabled() && wrapper != null) {
			Collection<PositionCoordinate> selectionCells = wrapper.getSelectedCells();
			if (selectionCells.size() == 1) {
				final PositionCoordinate positionCoordinate = selectionCells.iterator().next();
				final INattableModelManager nattableManager = getCurrentNattableModelManager();
				final Object rowElement;
				final Object columnElement;
				if (!nattableManager.getTable().isInvertAxis()) {
					rowElement = nattableManager.getRowElement(positionCoordinate.getRowPosition());
					columnElement = nattableManager.getColumnElement(positionCoordinate.getColumnPosition());
				} else {
					columnElement = nattableManager.getRowElement(positionCoordinate.getRowPosition());
					rowElement = nattableManager.getColumnElement(positionCoordinate.getColumnPosition());
				}

				final Cell cell = nattableManager.getCell(columnElement, rowElement);
				if (cell != null) {
					final Collection<Problem> problems = cell.getProblems();
					if (problems.size() == 1) {
						return problems.iterator().next();
					}
				}
			}
		}
		return null;
	}

}
