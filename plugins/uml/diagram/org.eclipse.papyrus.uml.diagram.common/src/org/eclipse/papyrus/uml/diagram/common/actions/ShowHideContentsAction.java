/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST.
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
 *  Benoit maggi (CEA LIST) benoit.maggi@cea.fr -#501701 Showing nested port on Port
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - bug 527181, ports on part layout
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IResizableCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.Messages;
import org.eclipse.papyrus.uml.diagram.common.commands.ShowHideElementsRequest;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AffixedNodeAlignmentEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideClassifierContentsEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.util.CompartmentUtils;
import org.eclipse.papyrus.uml.diagram.common.util.Util;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 *
 * This class provides an action to show/hide the owned members and the
 * inherited members for a Classifier
 *
 */
public class ShowHideContentsAction extends AbstractShowHideAction implements IActionDelegate, IWorkbenchWindowActionDelegate {

	/** INCREMENT for the location of the elements to show(ports and properties) */
	private static int INCREMENT = 10;

	/**
	 * Constructor.
	 */
	public ShowHideContentsAction() {
		this(Messages.ShowHideContentsAction_Title, Messages.ShowHideContentsAction_Message, ShowHideClassifierContentsEditPolicy.SHOW_HIDE_CLASSIFIER_CONTENTS_POLICY);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param title
	 *            title for the dialog
	 * @param message
	 *            message for the dialog
	 * @param editPolicyKey
	 *            EditPolicy used for this action
	 */
	public ShowHideContentsAction(String title, String message, String editPolicyKey) {
		super(title, message, editPolicyKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof StructuredSelection) {
			if (((StructuredSelection) selection).size() > 1) {
				action.setEnabled(false);
				return;
			}
		}
		super.selectionChanged(action, selection);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initAction() {
		super.initAction();
		for (IGraphicalEditPart current : this.selectedElements) {
			// the selected elements which aren't Classifier and InstanceSpecification are ignored
			EObject element = ((View) current.getModel()).getElement();
			if (element instanceof Classifier || element instanceof InstanceSpecification) {
				this.representations.add(new RootEditPartRepresentation(current, element));
			}
			if (element instanceof Port) {
				Port port = (Port) element;
				Type type = port.getType();
				if (type instanceof Classifier || type instanceof InstanceSpecification) {
					RootEditPartRepresentation e = new RootEditPartRepresentation(current, element);
					this.representations.add(e);
				}
			}
		}
		this.setContentProvider(new ContentProvider());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Object> getInput() {
		List<Object> list = new ArrayList<>();
		list.addAll(representations);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command getActionCommand() {

		ShowHideElementsRequest req = null;
		CompoundCommand completeCmd = new CompoundCommand("Show/Hide Inherited Elements Command"); //$NON-NLS-1$

		/*
		 * the command to hide elements we need the corresponding editpart
		 */
		for (EditPartRepresentation current : this.viewsToDestroy) {
			EditPart ep = current.getRepresentedEditPart(); // should not be null, because EP to destroy really exists
			if (ep != null) {
				req = new ShowHideElementsRequest(ep);
				EditPart parent = ep.getParent();
				if (parent instanceof CompartmentEditPart) {
					parent = parent.getParent();
				}
				Command cmd = parent.getCommand(req);
				if (cmd != null && cmd.canExecute()) {
					completeCmd.add(cmd);
				}
			} else {
				Activator.log.error("the edit part for this representation " + current + " should not be null", null);//$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		// the command to show element
		Point propertyLocation = new Point();
		Point portLocation = new Point(-10, -2 * INCREMENT + 1);
		for (EditPartRepresentation rep : this.viewsToCreate) {
			if (!(rep instanceof OptionalEditPartRepresentation)) {
				continue;
			}
			OptionalEditPartRepresentation optRep = (OptionalEditPartRepresentation) rep;
			EditPart ep = optRep.getParentRepresentation().getParentRepresentation().getRepresentedEditPart();
			View compartmentView = optRep.getParentRepresentation().getRepresentedEditPart().getNotationView();
			if (compartmentView != null) {
				req = new ShowHideElementsRequest(compartmentView, optRep.getSemanticElement());
				if (isXYLayout(compartmentView, ep)) {
					propertyLocation.x += INCREMENT;
					propertyLocation.y += INCREMENT;
					req.setLocation(new Point(propertyLocation));

				} else if (isAffixedChildNode(ep, optRep.getSemanticElement())) {

					portLocation.y += INCREMENT;
					Point initialPosition = getInitialPortLocation(ep, optRep.getSemanticElement());
					if (initialPosition != null) {
						// use initial-position instead of increment-based one
						req.setLocation(initialPosition);
					} else {
						req.setLocation(new Point(portLocation));
					}
				}
				Command cmd = ep.getCommand(req);
				if (cmd != null && cmd.canExecute()) {
					completeCmd.add(cmd);
				}
			}
		}
		return completeCmd;
	}

	/**
	 * Return the initial position of a port on a part. Should be override by subclasses (e.g. composite diagram)
	 *
	 * @param partEditPart
	 *            edit part of the part within a composite (for which we want to display a port)
	 * @param port
	 *            the semantic UML2 port which we want to display
	 * @return the initial location of the port or null (if none could be determined)
	 * @since 3.1
	 */
	public Point getInitialPortLocation(EditPart partEditPart, EObject port) {
		return null;
	}

	/**
	 * Test if the child is represented by an affixed child node
	 * TODO This method will not work if we have an UML element E1 which inherits from
	 * another element E2 and if E2 is represented by an affixed child node and
	 * not E1!
	 *
	 * @param ep
	 *            the parent EditPart
	 * @param child
	 *            the child to show
	 * @return
	 *         <ul>
	 *         <li><code>true</code> if child is represented by an affixed child node</li>
	 *         <li><code>false</code> if not</li>
	 *         </ul>
	 */
	protected boolean isAffixedChildNode(EditPart ep, EObject child) {
		TransactionalEditingDomain domain = ((IGraphicalEditPart) ep).getEditingDomain();
		ViewDescriptor viewDescriptor = new ViewDescriptor(new EObjectAdapter(child), Node.class, null, ViewUtil.APPEND, false, ((IGraphicalEditPart) ep).getDiagramPreferencesHint());
		CreateCommand cmd = new CreateCommand(domain, viewDescriptor, (View) ep.getModel());
		if (cmd.canExecute()) {
			/*
			 * the EditPart can own the child -> it's maybe an affixed child
			 * node
			 */
			return true;
		}
		return false;
	}

	/**
	 * Test if the layout for this compartment is a XYLayout
	 *
	 * @param compartment
	 *            the compartment to test
	 * @param ep
	 *            the editpart owning this compartment
	 * @return
	 *         <ul>
	 *         <li><code>true</code> if the layout for this compartment is a XYLayout</li>
	 *         <li><code>false</code> if not</li>
	 *         </ul>
	 */
	protected boolean isXYLayout(View compartment, EditPart ep) {
		List<?> children = ep.getChildren();
		for (Object current : children) {
			if (current instanceof EditPart) {
				if (((EditPart) current).getModel() == compartment) {
					EditPolicy editpolicy = ((EditPart) current).getEditPolicy(EditPolicy.LAYOUT_ROLE);
					if (editpolicy != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Return the EditParts owned by this EditPart. When the owned EditPart is a
	 * CompartmentEditPart, we return its containing EditPart
	 *
	 *
	 * @param ep
	 *            an editpart
	 * @return The EditParts owned by this EditPart. When the owned EditPart is
	 *         a CompartmentEditPart, we return its containing EditPart
	 */
	protected List<EditPart> getChildrenEditPart(EditPart ep) {
		List<EditPart> children = new ArrayList<>();
		List<?> tmp = ep.getChildren();
		for (Object current : tmp) {
			// we don't want the compartment used for the name
			if (current instanceof ITextAwareEditPart) {
				continue;
				// if it's a compartment, we want it's children
			} else if (current instanceof CompartmentEditPart) {
				children.addAll(((CompartmentEditPart) current).getChildren());
				// it's an affixed child node?!
			} else {
				children.add((EditPart) current);
			}
		}

		return children;
	}

	/**
	 * Return the compartment which allows the creation of the EObject
	 *
	 * @param editPart
	 *            an editpart
	 * @param child
	 *            an object to create
	 * @return the compartment which allows the creation of this EObject or <code>null</code>
	 */
	protected View getCompartmentForCreation(EditPart editPart, EObject child) {
		if (isAffixedChildNode(editPart, child)) {
			return (View) editPart.getModel();
		}
		TransactionalEditingDomain domain = ((IGraphicalEditPart) editPart).getEditingDomain();
		ViewDescriptor viewDescriptor = new ViewDescriptor(new EObjectAdapter(child), Node.class, null, ViewUtil.APPEND, false, ((IGraphicalEditPart) editPart).getDiagramPreferencesHint());

		List<View> visibleCompartments = ((IGraphicalEditPart) editPart).getNotationView().getVisibleChildren();
		for (View currentComp : visibleCompartments) {
			CreateCommand cmd = new CreateCommand(domain, viewDescriptor, currentComp);
			if (cmd.canExecute()) {
				return currentComp;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void buildShowHideElementsList(Object[] results) {
		super.buildShowHideElementsList(results);

		List<Object> result = new ArrayList<>();

		// we remove the EditPartRepresentation from the result
		for (int i = 0; i < results.length; i++) {
			if (results[i] instanceof RootEditPartRepresentation || results[i] instanceof CompartmentEditPartRepresentation) {
				continue;
			} else {
				result.add(results[i]);
			}
		}

		// we are looking for the objects to show
		for (Object element : result) {
			if (initialSelection.contains(element)) {
				// we do nothing
				continue;
			} else if (element instanceof EditPartRepresentation) {
				viewsToCreate.add((EditPartRepresentation) element);
			}
		}

		// we are looking for the view to destroy
		for (Object current : this.initialSelection) {
			if (!result.contains(current) && current instanceof EditPartRepresentation) {
				viewsToDestroy.add((EditPartRepresentation) current);
			}
		}
	}

	/**
	 * Return the editpart representation owning the element
	 *
	 * @param element
	 *            the element which we are looking for
	 * @return the editpart representation owning the element
	 */
	protected EditPartRepresentation findEditPartRepresentation(Object element) {
		for (EditPartRepresentation current : representations) {
			if (current.getPossibleElement().contains(element)) {
				return current;
			} else {
				EList<?> views = current.getRepresentedEditPart().getNotationView().getChildren();
				if (views.contains(element)) {
					return current;
				}
			}
		}
		return null;
	}

	public class CustomComparator implements Comparator<Object> {

		/** this list contains the name of all the classes which want sort */
		private List<String> classesList;

		/**
		 * Constructor.
		 *
		 * @param members
		 *            the elements to sort
		 */
		public CustomComparator(List<NamedElement> elements) {
			buildList(elements);
		}

		/**
		 * Fill {@link #classesList} with the class name of each element to sort
		 *
		 * @param elements
		 *            the elements to sort
		 */
		public void buildList(List<NamedElement> elements) {
			this.classesList = new ArrayList<String>();
			for (NamedElement namedElement : elements) {
				this.classesList.add(new String(namedElement.getClass().getSimpleName()));
			}
			Collections.sort(classesList);
		}

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Object o1, Object o2) {

			String name1 = o1.getClass().getSimpleName();
			String name2 = o2.getClass().getSimpleName();
			int index1 = classesList.indexOf(name1);
			int index2 = classesList.indexOf(name2);
			int classIndex = classesList.indexOf("ClassImpl"); //$NON-NLS-1$
			if (index1 == index2 && index1 == classIndex) {
				boolean metaclassO1 = Util.isMetaclass((Type) o1);
				boolean metaclassO2 = Util.isMetaclass((Type) o2);
				if (metaclassO1 && !metaclassO2) {
					return 1;
				} else if (!metaclassO1 && metaclassO2) {
					return -1;
				}
				return 0;
			}
			if (index1 == -1) {
				Activator.log.debug("The class " + name1 + " is unknown by " + this.getClass());//$NON-NLS-1$ //$NON-NLS-2$
				return -1;
			} else if (index1 == index2) {
				return 0;
			} else if (index1 > index2) {
				return 1;
			} else if (index1 < index2) {
				return -1;
			}
			return 0;
		}

	}

	/**
	 * Content provider for the {@link CheckedTreeSelectionDialog}
	 */
	public class ContentProvider implements ITreeContentProvider {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispose() {
			// nothing here
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// nothing here
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				return ((List<?>) inputElement).toArray();
			}
			return new Object[0];
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof EditPartRepresentation) {
				return ((EditPartRepresentation) parentElement).getPossibleElement().toArray();
			}
			return new Object[0];
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getParent(Object element) {
			if (element instanceof EditPartRepresentation) {
				EditPartRepresentation editPartRepresentation = (EditPartRepresentation) element;
				return editPartRepresentation.getParentRepresentation();
			}

			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasChildren(Object element) {
			if (getChildren(element) != null && getChildren(element).length > 0) {
				return true;
			}
			return false;
		}

	}

	/**
	 * Class that represents the root edit part taht contains the compartments
	 */
	protected class RootEditPartRepresentation extends EditPartRepresentation {

		/**
		 * Creates a new {@link RootEditPartRepresentation}
		 *
		 * @param representedEditPart
		 *            the edit part managed by this representation
		 * @param element
		 *            the classifier managed by the represented edit part
		 */
		public RootEditPartRepresentation(IGraphicalEditPart representedEditPart, EObject element) {
			super(representedEditPart, element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void initRepresentation() {
			super.initRepresentation();

			IGraphicalEditPart representedEditPart = getRepresentedEditPart();
			List<IResizableCompartmentEditPart> compartmentEditParts = CompartmentUtils.getAllCompartmentsEditPart(representedEditPart, false);
			for (IResizableCompartmentEditPart currentEditPart : compartmentEditParts) {
				CompartmentEditPartRepresentation representation = new CompartmentEditPartRepresentation(currentEditPart, getSemanticElement(), this);
				elementsToSelect.add(representation);
			}

			// check if the element has a affixed child edit policy => will be treated as a compartment
			EditPolicy policy = representedEditPart.getEditPolicy(AffixedNodeAlignmentEditPolicy.AFFIXED_CHILD_ALIGNMENT_ROLE);
			if (policy != null) {
				EditPartRepresentation representation;
				// there can be some affixed children, create a pseudo compartment edit part representation
				if (getSemanticElement() instanceof Port) {
					representation = new PortAffixedChildrenEditPartRepresentation(getSemanticElement(), this);
				} else {
					representation = new AffixedChildrenEditPartRepresentation(getSemanticElement(), this);
				}
				elementsToSelect.add(representation);
			}
		}
	}

	/**
	 * Class that represents the compartment edit parts.
	 */
	protected class CompartmentEditPartRepresentation extends EditPartRepresentation {

		/**
		 * Creates a new {@link CompartmentEditPartRepresentation}
		 *
		 * @param compartmentEditPart
		 *            the compartment edit part managed by this representation
		 * @param element
		 *            the classifier managed by the represented edit part
		 * @param parentRepresentation
		 *            parent presentation of this parent
		 */
		public CompartmentEditPartRepresentation(IResizableCompartmentEditPart compartmentEditPart, EObject element, EditPartRepresentation parentRepresentation) {
			super(compartmentEditPart, element, parentRepresentation);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getLabel() {
			if (getRepresentedEditPart() instanceof IResizableCompartmentEditPart) {
				return ((IResizableCompartmentEditPart) getRepresentedEditPart()).getCompartmentName();
			}
			return super.getLabel();
		}

		/**
		 * {@inheritDoc}
		 */
		protected List<Element> collectMembers() {
			if (getSemanticElement() instanceof Classifier) {
				return new ArrayList<Element>(((Classifier) getSemanticElement()).getMembers());
			}
			if (getSemanticElement() instanceof InstanceSpecification) {
				return new ArrayList<Element>(((InstanceSpecification) getSemanticElement()).getSlots());
			}
			return Collections.emptyList();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void initRepresentation() {
			// call super first
			super.initRepresentation();

			for (Element nextMember : collectMembers()) {
				if (false == canContain(nextMember)) {
					continue;
				}
				// create the leaf representation
				OptionalEditPartRepresentation editPartRepresentation = new OptionalEditPartRepresentation(null, nextMember, this);
				this.elementsToSelect.add(editPartRepresentation);
				// build the initial selection
				EList<?> childrenView = getRepresentedEditPart().getNotationView().getVisibleChildren();
				for (Object object : childrenView) {
					if (object instanceof View) {
						if (((View) object).getElement() == nextMember) {
							this.initialSelection.add(editPartRepresentation);

							// set the edit part in the optional edit part representation
							for (Object o : getRepresentedEditPart().getChildren()) {
								if (o instanceof IGraphicalEditPart) {
									if (((View) object).equals(((IGraphicalEditPart) o).getNotationView())) {
										editPartRepresentation.setRepresentedEditPart((IGraphicalEditPart) o);
									}
								}
							}
							break;
						}
					}
				}
			}

		}

		/**
		 * REturns <code>true</code> if the associated compartment edit part can contain the given element
		 *
		 * @param element
		 *            the named element to show/hide
		 * @return <code>true</code> if the compartment can display the element
		 */
		protected boolean canContain(Element element) {
			TransactionalEditingDomain domain = getRepresentedEditPart().getEditingDomain();
			ViewDescriptor viewDescriptor = new ViewDescriptor(new EObjectAdapter(element), Node.class, null, ViewUtil.APPEND, false, getRepresentedEditPart().getDiagramPreferencesHint());

			CreateCommand cmd = new CreateCommand(domain, viewDescriptor, getRepresentedEditPart().getNotationView());
			return cmd.canExecute();
		}
	}

	/**
	 * Specific edit part representation for edit part that can display affixed children. It extends {@link CompartmentEditPartRepresentation}, as
	 * this is displayed at the same level.
	 */
	protected class AffixedChildrenEditPartRepresentation extends CompartmentEditPartRepresentation {

		/**
		 * Constructor.
		 *
		 * @param element
		 *            uml element linked to this representation
		 * @param parentRepresentation
		 *            the main edit part against which show/hide content action is performed
		 */
		public AffixedChildrenEditPartRepresentation(EObject element, EditPartRepresentation parentRepresentation) {
			super(null, element, parentRepresentation);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected boolean canContain(Element element) {
			TransactionalEditingDomain domain = getParentRepresentation().getRepresentedEditPart().getEditingDomain();
			ViewDescriptor viewDescriptor = new ViewDescriptor(new EObjectAdapter(element), Node.class, null, ViewUtil.APPEND, false, getParentRepresentation().getRepresentedEditPart().getDiagramPreferencesHint());
			CreateCommand cmd = new CreateCommand(domain, viewDescriptor, getParentRepresentation().getRepresentedEditPart().getNotationView());
			return (cmd.canExecute());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public IGraphicalEditPart getRepresentedEditPart() {
			return getParentRepresentation().getRepresentedEditPart();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getLabel() {
			return "Border Items";
		}

		@Override
		protected void initRepresentation() {
			this.initialSelection = new ArrayList<EditPartRepresentation>();
			this.elementsToSelect = new ArrayList<EditPartRepresentation>();

			for (Element nextMember : collectMembers()) {
				if (false == canContain(nextMember)) {
					continue;
				}
				// create the leaf representation
				OptionalEditPartRepresentation editPartRepresentation = new OptionalEditPartRepresentation(null, nextMember, this);
				this.elementsToSelect.add(editPartRepresentation);
				// build the initial selection
				EList<?> childrenView = getParentRepresentation().getRepresentedEditPart().getNotationView().getVisibleChildren();
				for (Object object : childrenView) {
					if (object instanceof View) {
						if (((View) object).getElement() == nextMember) {
							this.initialSelection.add(editPartRepresentation);

							// set the edit part in the optional edit part representation
							for (Object o : getParentRepresentation().getRepresentedEditPart().getChildren()) {
								if (o instanceof IGraphicalEditPart) {
									if (((View) object).equals(((IGraphicalEditPart) o).getNotationView())) {
										editPartRepresentation.setRepresentedEditPart((IGraphicalEditPart) o);
										break;
									}
								}
							}
							break;
						}
					}
				}
			}
		}

	}

	/**
	 * Specific edit part representation for edit part that can display InnerPort affixed children. It extends {@link AffixedChildrenEditPartRepresentation}
	 */
	protected class PortAffixedChildrenEditPartRepresentation extends AffixedChildrenEditPartRepresentation {

		/**
		 * Constructor.
		 *
		 * @param element
		 * @param parentRepresentation
		 */
		public PortAffixedChildrenEditPartRepresentation(EObject element, EditPartRepresentation parentRepresentation) {
			super(element, parentRepresentation);
		}


		/**
		 * @see org.eclipse.papyrus.uml.diagram.common.actions.ShowHideContentsAction.CompartmentEditPartRepresentation#collectMembers()
		 *
		 * @return
		 */
		@Override
		protected List<Element> collectMembers() {
			List<Element> res = new ArrayList<>();
			EObject semanticElement = getSemanticElement();
			if (semanticElement instanceof Port) {
				Port port = (Port) semanticElement;
				Type type = port.getType();
				if (type != null && type instanceof Classifier) {
					EList<Property> allAttributes = ((Classifier) type).getAllAttributes();
					for (Element element : allAttributes) {
						if (element instanceof Port) {
							res.add(element);
						}
					}
					return res;
				}
			}
			return Collections.emptyList();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected boolean canContain(Element element) {
			TransactionalEditingDomain domain = getParentRepresentation().getRepresentedEditPart().getEditingDomain();
			ViewDescriptor viewDescriptor = new ViewDescriptor(new EObjectAdapter(element), Node.class, "Port_Shape", ViewUtil.APPEND, false, getParentRepresentation().getRepresentedEditPart().getDiagramPreferencesHint());
			CreateCommand cmd = new CreateCommand(domain, viewDescriptor, getParentRepresentation().getRepresentedEditPart().getNotationView());
			return cmd.canExecute();
		}

	}

	/**
	 * Class that manages an element that can not be displayed currently
	 */
	protected class OptionalEditPartRepresentation extends EditPartRepresentation {

		/**
		 * Creates a new {@link OptionalEditPartRepresentation}
		 *
		 * @param representedEditPart
		 *            the edit part managed by this representation, which can be <code>null</code> in this implementation
		 * @param element
		 *            the semantic element managed by the represented edit part
		 * @param parentRepresentation
		 *            parent representation for this edit part (should be a compartment edit part representation)
		 */
		public OptionalEditPartRepresentation(IGraphicalEditPart representedEditPart, Element element, CompartmentEditPartRepresentation parentRepresentation) {
			super(representedEditPart, element, parentRepresentation);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public CompartmentEditPartRepresentation getParentRepresentation() {
			return (CompartmentEditPartRepresentation) super.getParentRepresentation();
		}

	}
}
