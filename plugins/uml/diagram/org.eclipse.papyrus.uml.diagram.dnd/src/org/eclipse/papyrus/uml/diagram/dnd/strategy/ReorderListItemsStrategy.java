/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation - Bug 450844
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.dnd.strategy;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.AbstractDropStrategy;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLCompartmentEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * Drop strategy to reorder items in a list compartment (e.g. Attributes in a Class)
 *
 * @since 1.3
 */
public class ReorderListItemsStrategy extends AbstractDropStrategy {

	private IFigure dropFeedback;

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel() {
		return "Reorder list items";
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getDescription()
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return "Reorders items in a list compartment";
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getImage()
	 *
	 * @return
	 */
	@Override
	public Image getImage() {
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getID()
	 *
	 * @return
	 */
	@Override
	public String getID() {
		return "org.eclipse.papyrus.uml.diagram.dnd.strategy.ReorderListItemsStrategy";
	}

	// Drop can happen either on the compartment, or on a child of the compartment (Sibling of the moved element)
	// The actual target is always the compartment
	private ListCompartmentEditPart getTargetEP(EditPart targetEditPart) {
		if (targetEditPart instanceof ListCompartmentEditPart) {
			return (ListCompartmentEditPart) targetEditPart;
		} else if (targetEditPart.getParent() instanceof ListCompartmentEditPart) {
			return (ListCompartmentEditPart) targetEditPart.getParent();
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getCommand(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 *
	 * @param request
	 * @param targetEditPart
	 * @return
	 */
	@Override
	public Command getCommand(Request request, EditPart targetEditPart) {
		// We only support diagram-to-diagram drop
		if (false == request instanceof ChangeBoundsRequest) {
			return null;
		}
		ListCompartmentEditPart listPart = getTargetEP(targetEditPart);
		if (listPart == null) {
			return null;
		}

		ChangeBoundsRequest dropRequest = (ChangeBoundsRequest) request;

		List<?> objects = dropRequest.getEditParts();

		List<UMLCompartmentEditPart> editParts = objects.stream() //
				.filter(IPrimaryEditPart.class::isInstance) //
				.flatMap(selectByType(UMLCompartmentEditPart.class)) //
				.filter(p -> p.getParent() == listPart)
				.collect(Collectors.toList());

		if (editParts.isEmpty()) {
			return null;
		}

		// Some edit parts are not owned by the same parent (e.g. attributes are selected in different classes)
		if (editParts.stream().anyMatch(p -> p.getParent() != listPart)) {
			return null;
		}

		Resource eResource = NotationHelper.findView(listPart).eResource();
		IFile file = ResourceUtils.getFile(eResource);
		List<IFile> modifiedResources = file == null ? Collections.emptyList() : Collections.singletonList(file);

		TransactionalEditingDomain editingDomain = (TransactionalEditingDomain) EMFHelper.resolveEditingDomain(listPart);

		return new ICommandProxy(new AbstractTransactionalCommand(editingDomain, "Reorder items", modifiedResources) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				EditPart dropBefore = getDropBefore(dropRequest, listPart);

				EditPart primary = listPart;
				while (primary != null && false == primary instanceof IPrimaryEditPart) {
					primary = primary.getParent();
				}

				if (primary == null) {
					return CommandResult.newCancelledCommandResult();
				}

				if (DiagramEditPartsUtil.isCanonical(primary)) {
					return reorderSemantic(listPart, dropBefore);
				} else {
					return reorderGraphical(listPart, dropBefore);
				}
			}

			private CommandResult reorderGraphical(ListCompartmentEditPart listPart, EditPart dropBefore) {
				EList<View> viewChildren = listPart.getNotationView().getPersistedChildren();

				View dropBeforeView = NotationHelper.findView(dropBefore);

				// Note: Edit parts are dropped in the order they were selected; not in the order they visually appear in the list
				// This may lead to surprising reordering during drop. To avoid this, it may be useful to sort the list of selected parts

				for (UMLCompartmentEditPart /* EditPart */ selected : editParts) {
					if (selected == dropBefore) {
						continue;
					}

					View view = selected.getNotationView();
					if (view == null) {
						continue;
					}

					int targetIndex = dropBeforeView == null ? viewChildren.size() /* End of the list */ : viewChildren.indexOf(dropBeforeView);
					int currentIndex = viewChildren.indexOf(view);

					if (targetIndex > currentIndex) {
						targetIndex--;
					}

					viewChildren.move(targetIndex, view);
				}

				return CommandResult.newOKCommandResult();
			}

			private CommandResult reorderSemantic(ListCompartmentEditPart listPart, EditPart dropAfter) {
				// The diagram is synchronized, but we don't have detailed access to the strategy used
				// to populate the compartment (It seems that the Papyrus Canonical Mode synchronizes the
				// entire semantic node, e.g. the class, rather than each individual compartment).
				// Additionally, we may not always be able to manipulate semantic children as a single coherent
				// list, where ordering would be possible.

				Runnable showInfo = () -> {
					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Reorder not supported",
							"Reordering elements from the diagram is not supported when the diagram is synchronized with the model.\n"
									+ "You can either disable the \"Sync with model\" mode in the Appearance tab of the properties view, or reorder the semantic model directly (e.g. using the Model Explorer).");
				};

				if (Display.getCurrent() == null) {
					Display.getDefault().syncExec(showInfo);
				} else {
					showInfo.run();
				}

				return CommandResult.newCancelledCommandResult();
			}

		});
	}

	private int getDropIndex(ChangeBoundsRequest dropRequest, ListCompartmentEditPart listPart) {
		EditPart dropAfter = getDropBefore(dropRequest, listPart);
		return dropAfter == null ? listPart.getChildren().size() : listPart.getChildren().indexOf(dropAfter);
	}

	/**
	 * Find the edit part after which the selected elements should be dropped. If null, elements
	 * should be dropped at the end of the list
	 */
	private EditPart getDropBefore(ChangeBoundsRequest dropRequest, ListCompartmentEditPart listPart) {
		int target = dropRequest.getLocation().y;
		List<? extends EditPart> children = listPart.getChildren();

		EditPart dropBefore = null;
		// Find the first element below the drop target
		// Null if all of them are above, i.e. we should drop at the bottom/end of the list
		for (EditPart ep : children) {
			if (ep instanceof GraphicalEditPart) {
				IFigure figure = ((GraphicalEditPart) ep).getFigure();
				if (figure.getBounds().y + figure.getBounds().height / 2 > target) {
					dropBefore = ep;
					break;
				}
			}
		}

		return dropBefore;
	}

	private static <T> Function<Object, Stream<? extends T>> selectByType(Class<T> type) {
		return (o -> type.isInstance(o) ? Stream.of(type.cast(o)) : Stream.empty());
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#getPriority()
	 *
	 * @return
	 * @deprecated
	 */
	@Deprecated
	@Override
	public int getPriority() {
		return 0;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#showTargetFeedback(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 *
	 * @param request
	 * @param targetEditPart
	 * @return
	 */
	@Override
	public boolean showTargetFeedback(Request request, EditPart targetEditPart) {
		if (getCommand(request, targetEditPart) != null) {
			ListCompartmentEditPart targetEP = getTargetEP(targetEditPart);
			ChangeBoundsRequest dropRequest = (ChangeBoundsRequest) request;
			EditPart ep = targetEP.getChildren().get(0);
			IFigure sibling = ((GraphicalEditPart) ep).getFigure();
			IFigure figure = sibling.getParent();

			if (dropFeedback == null) {
				RectangleFigure rectangle = new RectangleFigure();
				rectangle.setBounds(new Rectangle(0, 0, 100, 1));
				int border = figure.getBorder().getInsets(figure).getWidth();
				rectangle.getBounds().setWidth(figure.getBounds().width() - border);
				// rectangle.setLineWidth(2);
				rectangle.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
				rectangle.setBackgroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
				rectangle.setVisible(true);
				rectangle.setFill(true);
				rectangle.setLocation(new Point(30, 300));

				dropFeedback = rectangle;
				figure.add(dropFeedback);// , SubCompartmentLayoutManager.SKIP_LAYOUT);
			}

			// Update position
			int index = getDropIndex(dropRequest, getTargetEP(targetEditPart));
			figure.add(dropFeedback, index);
			return true;
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy#eraseTargetFeedback(org.eclipse.gef.Request, org.eclipse.gef.EditPart)
	 *
	 * @param request
	 * @param targetEditPart
	 * @return
	 */
	@Override
	public boolean eraseTargetFeedback(Request request, EditPart targetEditPart) {
		if (dropFeedback != null && request instanceof ChangeBoundsRequest) {
			if (dropFeedback.getParent() != null) {
				dropFeedback.getParent().getChildren().remove(dropFeedback);
			}
			dropFeedback = null;
			return true;
		}
		return false;
	}

}
