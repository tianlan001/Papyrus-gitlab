/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation (Bug 455311)
 *   Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotypes Display
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper;

import java.util.Iterator;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.command.UnsetPersistentViewCommand;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This Helper contains all the Methods required in the using of Stereotype Display thanks
 * to the Notation View structure
 * 
 * @author Celine JANSSENS
 *
 */
public class StereotypeDisplayUtil {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	/**
	 * singleton instance
	 */
	private static StereotypeDisplayUtil labelHelper;

	/** Singleton constructor */
	private StereotypeDisplayUtil() {
	}

	/**
	 * Returns the singleton instance of this class
	 *
	 * @return the singleton instance.
	 */
	public static StereotypeDisplayUtil getInstance() {
		if (labelHelper == null) {
			labelHelper = new StereotypeDisplayUtil();
		}
		return labelHelper;
	}



	/**
	 * Return Stereotype application according to the Stereotype applied on the main Owner of the view
	 * 
	 * @param view
	 *            The view of which the Stereotype Application is Required (StereotypeCompartment, StereotypeProperty,...)
	 * @return The Direct AppliedStereotype when possible or the Applied Stereotype of the Base Element when the view is in the Comment Shape.
	 */
	public EObject getStereotypeApplication(final View view, Stereotype stereotype) {
		EObject appliedStereotypes = null;
		Element containerSemanticElement = getContainerSemanticElement(view);
		if (containerSemanticElement != null) {
			appliedStereotypes = containerSemanticElement.getStereotypeApplication(stereotype);
		} else if (isInStereotypeComment((Node) view)) {
			Element commentSemanticElement = getCommentSemanticElement(getTopContainer(view));
			if (commentSemanticElement != null) {
				appliedStereotypes = commentSemanticElement.getStereotypeApplication(stereotype);
			}
		}
		return appliedStereotypes;
	}

	/**
	 * Get the Semantic Element of the Comment View.
	 * The Comment View is not attached to its semantic element through the method {@link View#getElement()}.
	 * To retrieve the semantic element, it has been added as a namedStyle with the name {@link StereotypeDisplayConstant#STEREOTYPE_COMMENT_RELATION_NAME}
	 * 
	 * @param view
	 *            The Stereotype Comment View (of type "StereotypeComment")
	 * @return The Base Element of the Comment
	 */
	public Element getCommentSemanticElement(View view) {
		Element elementSemantic = null;
		if (isStereotypeComment(view)) {
			// Retrieve the Base Element of the Comment as the Semantic element
			EObject baseElement = NotationUtils.getEObjectValue(view, StereotypeDisplayConstant.STEREOTYPE_COMMENT_RELATION_NAME, null);
			if (baseElement instanceof Element) {
				elementSemantic = (Element) baseElement;
			}
		}
		return elementSemantic;
	}

	/**
	 * Retrieve the Container View of the Object on which the Stereotype is applied.
	 * By default the container is the direct parent, but in case of Property, the top Container is the grand Parent.
	 * 
	 * @param view
	 * @return the Container that support the Stereotype Application
	 */
	public View getTopContainer(final View view) {
		View topView = null;
		if (view != null) {
			EObject container = view.eContainer();
			if (isStereotypeCompartment(container) || isStereotypeBrace(container) || isStereotypeLabel(container)) {
				container = container.eContainer();
			}
			if (container instanceof View) {
				topView = (View) container;
			}
		}
		return topView;
	}

	/**
	 * Return the Semantic Element of the Container.
	 * 
	 * @param view
	 *            (i.e: LabelStereotype, CompartmentStereotype)
	 * @return The semantic Element of the container (Class , Operation, ...)
	 */
	public Element getContainerSemanticElement(final View view) {
		Element elementUML = null;
		EObject parentElement = getTopContainer(view).getElement();
		if (parentElement instanceof Element) {
			elementUML = (Element) parentElement;
		}

		return elementUML;
	}


	/**
	 * Get the Full label to display into the header
	 * 
	 * @param model
	 *            The corresponding View
	 * @return The label with Stereotype Name to display (i.e: "Blocks::Block, Allocation, SysML::Requirements::Requirement" )
	 */
	public String getStereotypeTextToDisplay(View model) {
		StringBuilder finalText = new StringBuilder();
		boolean displayStereotypes = NotationUtils.getBooleanValue(model, NamedElementEditPart.DISPLAY_STEREOTYPES, true);
		if (displayStereotypes) {
			StringBuilder textToDisplay = new StringBuilder();
			Iterator<?> childrenIterator = model.getChildren().iterator();
			// For all children, check if it's a StereotypeLabel and add the Name
			while (childrenIterator.hasNext()) {
				Object object = childrenIterator.next();
				if (isStereotypeLabel(object)) {
					if (((View) object).isVisible()) {
						if (!EMPTY_STRING.equals(textToDisplay.toString()) && (textToDisplay != null)) {
							textToDisplay.append(StereotypeDisplayConstant.STEREOTYPE_LABEL_SEPARATOR);
						}
						textToDisplay.append(getStereotypeName((DecorationNode) object));
					}
				}
			}
			// Then add the ornament around the text.
			if ((textToDisplay.toString() != null) && (!EMPTY_STRING.equals(textToDisplay.toString()))) {
				finalText.append(StereotypeDisplayConstant.QUOTE_LEFT).append(textToDisplay).append(StereotypeDisplayConstant.QUOTE_RIGHT);
			}
		}
		// Return the text or null if empty
		return finalText.toString();
	}


	/**
	 * Get the entire String to display between braces.
	 * 
	 * @param notationView
	 *            The View of the Element on which the Stereotype has been applied.
	 * @param inComment
	 *            if this is the Property in the comment
	 * 
	 * @return The entire string to be displayed in braces
	 */
	public String getStereotypePropertiesInBrace(View notationView) {
		String textToDisplay = EMPTY_STRING;
		Iterator<?> iter = notationView.getChildren().iterator();
		Object object;
		// For each Stereotype Compartment, retrieve the property text to be added in Braces
		while (iter.hasNext()) {
			object = iter.next();
			if (isStereotypeBrace(object)) {
				BasicCompartment compartment = (BasicCompartment) object;
				textToDisplay = addStereotypeCompartmentProperties(textToDisplay, compartment);
			}
		}
		return textToDisplay;
	}

	/**
	 * Get the Stereotype Name to the text to display.
	 * 
	 * @param label
	 *            The StereotypeLabel of the new stereotype to added
	 * 
	 * @return The String with the new StereotypeName and
	 */
	public String getStereotypeName(DecorationNode label) {
		// Retrieve Name and Depth from CSS or NamedStyle
		String name = getName(label);
		String depth = getDepth(label);

		// Compute name according to the depth
		String nameWithDepth = getStereotypeNameWithDepth(name, depth);
		return nameWithDepth;
	}


	/**
	 * Retrieve the Stereotype Name with its appropriate depth
	 * 
	 * @param text
	 *            The full name of the Stereotype (i.e: "SysML::Blocks::Block")
	 * @param depth
	 *            The Depth value (i.e: "-1" )
	 * @return The stereotype Name with the proper Depth (i.e: "Blocks::Block")
	 */
	public String getStereotypeNameWithDepth(String qualifiedName, String depth) {
		if (depth != null && !EMPTY_STRING.equals(depth)) {
			if (StereotypeDisplayConstant.DEPTH_MIN.equals(depth)) {
				return getMinimumDepthName(qualifiedName);
			} else if (StereotypeDisplayConstant.DEPTH_MAX.equals(depth)) {
				return qualifiedName;
			} else if (StereotypeDisplayConstant.DEPTH_AUTO.equals(depth)) {
				// TODO to be computed
				return qualifiedName;
			} else {
				// In case the depth retrieve is a number
				try {
					int depthRetrieve = Integer.parseInt(depth);
					// Case the number is coherent
					if (depthRetrieve > -getMaxDepth(qualifiedName) && depthRetrieve < 0) {
						// Nominal Case (i.e : Depth = -1 => Compute the name accordingly)
						return computeDepthName(depthRetrieve, qualifiedName);
					} else if (depthRetrieve <= -getMaxDepth(qualifiedName)) {
						// Depth is too Small (i.e : -24 ) => Return the last segment
						return getMinimumDepthName(qualifiedName);
					} else if (depthRetrieve == 0) {
						// Depth is null => return the full name
						return qualifiedName;
					} else {
						// in all other cases return the full name
						return qualifiedName;
					}
				} catch (NumberFormatException e) {
					Activator.log.error("Wrong Depth value. Impossible to parse depth : " + depth, e); //$NON-NLS-1$
				}
			}
		}
		return qualifiedName;
	}

	/**
	 * If the retrieve Depth is an appropriate number , compute the Name accordingly.
	 * 
	 * @param depthRetrieve
	 *            The depth retrieved (should be negative)
	 * @param qualifiedName
	 *            The Full Qualified Name
	 * @return the Name computed with the appropriate Depth
	 */
	protected String computeDepthName(int depthRetrieve, String qualifiedName) {
		StringBuffer name = new StringBuffer(qualifiedName);
		try {
			for (int i = 0; i > depthRetrieve; i--) {
				int index = name.indexOf(StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH_SEPARATOR);
				name = name.delete(0, index + StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH_SEPARATOR.length());
			}
		} catch (IndexOutOfBoundsException e) {
			Activator.log.error(e.getMessage(), e);
		}
		return name.toString();
	}

	/**
	 * Get the last segment of the full qualifiedName.
	 * 
	 * @param qualifiedName
	 *            The full path name
	 * @return The Last segment of the path.
	 */
	public String getMinimumDepthName(String qualifiedName) {
		String segment = EMPTY_STRING;
		StringBuffer name = new StringBuffer(qualifiedName);
		int index = name.lastIndexOf(StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH_SEPARATOR);
		segment = name.substring(index + StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH_SEPARATOR.length());
		return segment;
	}

	/**
	 * Get the number of deepness level. (Ex: SysML::Blocks::Block ==> Returns 3 )
	 * 
	 * @param qualifiedName
	 *            The full path name
	 * @return Number of deepness level of the full path
	 */
	protected int getMaxDepth(String qualifiedName) {
		StringTokenizer tok = new StringTokenizer(qualifiedName, StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH_SEPARATOR);
		return tok.countTokens();
	}

	/**
	 * Return the associated DecorationNode of a node from it's qualified name.
	 * 
	 * @param node
	 *            Container Node of the StereotypeLabel
	 * @param qualifiedName
	 *            Qualified Name of the Stereotype associated to this Label
	 * @return associated StereotypeLabel
	 */
	public DecorationNode getStereotypeLabel(View node, String qualifiedName) {
		if ((qualifiedName != null) && (qualifiedName != EMPTY_STRING)) {
			Object obj;
			Iterator<?> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				obj = iter.next();
				if (obj instanceof DecorationNode) {
					DecorationNode label = (DecorationNode) obj;
					if (StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE.equals(label.getType())) {
						if (qualifiedName.equals(getName(label))) {
							return label;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Return the associated Property view of a node from the property name.
	 * 
	 * @param node
	 *            Container Node of the StereotypeLabel
	 * @param qualifiedName
	 *            Qualified Name of the Stereotype associated to this Label
	 * @return associated StereotypeLabel
	 */
	public DecorationNode getStereotypeProperty(View node, Stereotype stereotype, Property property) {
		DecorationNode propertyView = null;
		if ((stereotype != null) && (property != null)) {
			View compartment = getStereotypeCompartment(node, stereotype);
			Object obj;
			if (compartment != null) {
				Iterator<?> iter = compartment.getChildren().iterator();
				while (null == propertyView && iter.hasNext()) {
					obj = iter.next();
					if (isStereotypeProperty(obj) && ((DecorationNode) obj).getElement().equals(property)) {
						propertyView = (DecorationNode) obj;
					}
				}
			}
		}
		return propertyView;
	}

	/**
	 * Return the associated Property view of a node from the property name.
	 * 
	 * @param node
	 *            Container Node of the StereotypeLabel
	 * @param qualifiedName
	 *            Qualified Name of the Stereotype associated to this Label
	 * @return associated StereotypeLabel
	 */
	public DecorationNode getStereotypePropertyInBrace(View node, Stereotype stereotype, Property property) {
		DecorationNode propertyView = null;
		if ((null != stereotype) && (null != property)) {
			View compartment = getStereotypeBraceCompartment(node, stereotype);
			Object obj;
			if (null != compartment) {
				Iterator<?> iter = compartment.getChildren().iterator();
				while (null == propertyView && iter.hasNext()) {
					obj = iter.next();
					if (isStereotypeBraceProperty(obj) && ((DecorationNode) obj).getElement().equals(property)) {
						propertyView = (DecorationNode) obj;
					}
				}
			}
		}
		return propertyView;
	}

	/**
	 * Return the associated Property view of a node from the property name.
	 * 
	 * @param node
	 *            Container Node of the StereotypeLabel
	 * @param qualifiedName
	 *            Qualified Name of the Stereotype associated to this Label
	 * @return associated StereotypeLabel
	 */
	public DecorationNode getStereotypePropertyInComment(View mainView, Stereotype stereotype, Property property) {
		View node = getStereotypeComment(mainView);
		DecorationNode propertyView = null;
		if ((null != stereotype) && (null != property)) {
			View compartment = getStereotypeCompartment(node, stereotype);
			Object obj;
			if (null != compartment) {
				Iterator<?> iter = compartment.getChildren().iterator();
				while (null == propertyView && iter.hasNext()) {
					obj = iter.next();
					if (isStereotypeProperty(obj) && ((DecorationNode) obj).getElement().equals(property)) {
						propertyView = (DecorationNode) obj;
					}
				}
			}
		}
		return propertyView;
	}

	/**
	 * Return the associated Property view of a node from the property name.
	 * 
	 * @param node
	 *            Node on which the Stereotype Label is retrieved
	 * @param stereotype
	 *            Stereotype Application of the Label to be retrieved.
	 * @param property
	 *            Property of the stereotype
	 * @return associated StereotypeLabel
	 */
	public DecorationNode getStereotypePropertyInCompartment(View node, Stereotype stereotype, Property property) {
		DecorationNode propertyView = null;
		if ((stereotype != null) && (property != null)) {
			View compartment = getStereotypeCompartment(node, stereotype);
			Object obj;
			if (compartment != null) {
				Iterator<?> iter = compartment.getChildren().iterator();
				while (null == propertyView && iter.hasNext()) {
					obj = iter.next();
					if (isStereotypeProperty(obj) && null != ((DecorationNode) obj).getElement() && ((DecorationNode) obj).getElement().equals(property)) {
						propertyView = (DecorationNode) obj;
					}
				}
			}
		}
		return propertyView;
	}

	/**
	 * Return the associated DecorationNode of a node from it's stereotypeAppplication.
	 * 
	 * @param node
	 *            Node on which the Stereotype Label is retrieved
	 * @param stereotypeApplication
	 *            Stereotype Application of the Label to be retrieved.
	 * @return associated StereotypeLabel
	 */
	public DecorationNode getStereotypeLabel(View node, Stereotype stereotype) {
		if ((stereotype != null) && (node != null)) {
			Object obj;
			Iterator<?> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				obj = iter.next();
				if (obj instanceof DecorationNode) {
					DecorationNode label = (DecorationNode) obj;
					if (isStereotypeLabel(label)) {
						if (stereotype.equals(label.getElement())) {
							return label;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Return the associated DecorationNode of a node from it's stereotypeAppplication.
	 * 
	 * @param node
	 *            Node on which the Stereotype Label is retrieved
	 * @param stereotypeApplication
	 *            Stereotype Application of the Label to be retrieved.
	 * @return associated StereotypeLabel or null if none
	 */
	public BasicCompartment getStereotypeCompartment(View node, Stereotype stereotype) {

		if ((stereotype != null) && (node != null)) {
			Object obj;
			Iterator<?> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				obj = iter.next();
				if (isStereotypeCompartment(obj)) {
					BasicCompartment compartment = (BasicCompartment) obj;
					if (compartment.getElement() != null) {
						if (compartment.getElement().equals(stereotype)) {
							return compartment;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * return the comment nodes that represent stereotype properties
	 *
	 * @return may be null if nothing is founded
	 */
	public Node getStereotypeComment(View view) {
		Node node = null;
		if (view != null && view.getSourceEdges() != null) {
			// look for all links with the id AppliedStereotypesCommentLinkEditPart.ID
			Iterator<Edge> edgeIterator = view.getSourceEdges().iterator();
			Edge appliedStereotypeLink = null;
			while (null == appliedStereotypeLink && edgeIterator.hasNext()) {
				Edge edge = edgeIterator.next();
				if (edge.getType().equals(StereotypeDisplayConstant.STEREOTYPE_COMMENT_LINK_TYPE)) {
					appliedStereotypeLink = edge;
					node = (Node) appliedStereotypeLink.getTarget();
				}
			}
		}
		return node;
	}

	/**
	 * Define if the Object is a StereotypeLabel
	 * 
	 * @param element
	 *            The object on which the test is done
	 * 
	 * @return True if the Object is of type "StereotypeLabel"
	 */
	public boolean isStereotypeLabel(Object element) {
		boolean stereotypeLabel = Boolean.FALSE;
		if (element instanceof DecorationNode) {
			stereotypeLabel = StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE.equals(((DecorationNode) element).getType());
		}
		return stereotypeLabel;
	}

	/**
	 * Defines if the Object is a StereotypeCompartment
	 * 
	 * @param element
	 *            The object on which the test is done
	 * @return True if Object is a StereotypeCompartment
	 */
	public boolean isStereotypeCompartment(Object element) {
		boolean stereotypeCompartment = Boolean.FALSE;
		if (element instanceof BasicCompartment) {
			stereotypeCompartment = StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_TYPE.equals(((DecorationNode) element).getType());
		}
		return stereotypeCompartment;
	}

	/**
	 * Defines if the Object is a StereotypeProperty
	 * 
	 * @param element
	 *            The object on which the test is done
	 * @return true if Object is a StereotypeProperty Label
	 */
	public boolean isStereotypeProperty(Object element) {
		boolean stereotypeProperty = Boolean.FALSE;
		if (element instanceof DecorationNode) {
			stereotypeProperty = StereotypeDisplayConstant.STEREOTYPE_PROPERTY_TYPE.equals(((DecorationNode) element).getType());

		}
		return stereotypeProperty;
	}


	/**
	 * Define if an object is a Stereotype Brace View
	 * 
	 * @param element
	 *            The Object to test
	 * @return true if it's a Stereotype Brace View
	 */
	public boolean isStereotypeBrace(Object element) {
		boolean stereotypeBraceCmpt = Boolean.FALSE;
		if (element instanceof BasicCompartment) {
			stereotypeBraceCmpt = StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE.equals(((BasicCompartment) element).getType());

		}
		return stereotypeBraceCmpt;
	}


	/**
	 * Define if an object is a Stereotype Brace Property Node. A Stereotype Property into Brace
	 * 
	 * @param element
	 *            The Object to test
	 * @return true if it's a Stereotype Brace Property Node
	 */
	public boolean isStereotypeBraceProperty(Object element) {
		boolean stereotypeProperty = Boolean.FALSE;
		if (element instanceof DecorationNode) {
			stereotypeProperty = StereotypeDisplayConstant.STEREOTYPE_PROPERTY_BRACE_TYPE.equals(((DecorationNode) element).getType());
		}
		return stereotypeProperty;
	}



	/**
	 * Defines if the Object is a StereotypeComment
	 * 
	 * @param element
	 *            The object on which the test is done
	 * 
	 * @return true if Object is a StereotypeProperty Label
	 */
	public boolean isStereotypeComment(Object element) {
		boolean stereotypeComment = Boolean.FALSE;
		if (element instanceof Shape) {
			stereotypeComment = StereotypeDisplayConstant.STEREOTYPE_COMMENT_TYPE.equals(((Shape) element).getType());
		}
		return stereotypeComment;
	}


	/**
	 * Get the name of the Stereotype Label (it should be the Qualified Name of the related Stereotype )
	 * 
	 * @param label
	 *            The DecorationNode of type StereotypeLabel of which the name is retrieved
	 * @return The name of the Label
	 */
	public String getName(DecorationNode label) {
		String name = EMPTY_STRING;
		if (isStereotypeLabel(label)) {
			String defaultName = EMPTY_STRING;
			if (label != null && label.getElement() instanceof Stereotype) {

				if (LabelInternationalizationPreferencesUtils.getInternationalizationPreference(label.getElement())) {
					name = UMLLabelInternationalization.getInstance().getQualifiedLabel(((Stereotype) label.getElement()));
				} else {
					defaultName = ((Stereotype) label.getElement()).getQualifiedName();

					// Retrieve Name from CSS or NamedStyle from the Notation model.
					name = NotationUtils.getStringValue(label, StereotypeDisplayConstant.STEREOTYPE_LABEL_NAME, defaultName);
				}
			}
		}
		return name;
	}

	/**
	 * Get the name of the Stereotype Compartment that contains Stereotype Properties (The name is the Qualified Name of the related Stereotype )
	 * 
	 * @param compartment
	 *            The BasicCompartment of the Applied Stereotype
	 * @return The name of the Compartment containing the Stereotype properties
	 */
	public String getName(BasicCompartment compartment) {
		String name = EMPTY_STRING;
		if (isStereotypeCompartment(compartment) || isStereotypeBrace(compartment)) {
			String defaultName = EMPTY_STRING;
			if (compartment != null && compartment.getElement() instanceof Stereotype) {
				if (LabelInternationalizationPreferencesUtils.getInternationalizationPreference(compartment.getElement())) {
					name = UMLLabelInternationalization.getInstance().getQualifiedLabel(((Stereotype) compartment.getElement()));
				} else {
					defaultName = ((Stereotype) compartment.getElement()).getQualifiedName();

					// Retrieve Name from CSS or NamedStyle from the Notation model.
					name = NotationUtils.getStringValue(compartment, StereotypeDisplayConstant.STEREOTYPE_COMPARTMENT_NAME, defaultName);
				}
			}
		}
		return name;
	}

	/**
	 * Retrieve the Depth NamedStyle Value of a StereotypeLabel DecorationNode.
	 * The evaluation of the retrieved content is done into method {@link #computeDepthName(int, String) }.
	 * 
	 * @param label
	 *            StereotypeLabel Node
	 * @return the Depth as a String
	 */
	public String getDepth(DecorationNode label) {
		String depth = EMPTY_STRING;
		if (label != null && StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE.equals(label.getType())) {
			// Retrieve Depth from CSS or NamedStyle from the Notation model.
			depth = NotationUtils.getStringValue(label, StereotypeDisplayConstant.STEREOTYPE_LABEL_DEPTH, StereotypeDisplayConstant.DEFAULT_DEPTH_VALUE);
		}
		return depth;
	}


	/**
	 * Defines if Label is present into the node
	 * 
	 * @param node
	 *            View of the node for which the test is done
	 * @param stereotypeApplication
	 *            The stereotype Application of the Label on which the test is done.
	 * @return True is the Node contains already a Label for the stereotype Application
	 */
	public boolean isLabelExist(View node, Stereotype stereotype) {
		boolean exist = false;
		Iterator<?> iter = node.getChildren().iterator();
		while (!exist && iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof DecorationNode) {
				DecorationNode stereo = (DecorationNode) obj;
				if (StereotypeDisplayConstant.STEREOTYPE_LABEL_TYPE.equals(stereo.getType())) {
					exist = stereo.getElement().equals(stereotype);
				}
			}
		}
		return exist;
	}

	/**
	 * Defines if the corresponding compartment is present into the node
	 * 
	 * @param node
	 *            View of the node for which the test is done
	 * @param stereotypeApplication
	 *            The stereotype Application of the Label on which the test is done.
	 * @return True is the Node contains already a Label for the stereotype Application
	 */
	public boolean isCompartmentExist(View node, Stereotype stereotype) {
		boolean exist = false;
		Iterator<?> iter = node.getChildren().iterator();
		while (!exist && iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof BasicCompartment) {
				BasicCompartment compartment = (BasicCompartment) obj;
				if (isStereotypeCompartment(compartment)) {
					exist = compartment.getElement().equals(stereotype);
				}
			}
		}
		return exist;
	}

	/**
	 * Defines if the corresponding property is present into the node
	 * 
	 * @param node
	 *            The Compartment of the
	 * @param stereotypeApplication
	 * @return
	 */
	public boolean isPropertyExist(View compartment, Property propertyUML) {
		boolean exist = false;
		Iterator<?> iter = compartment.getChildren().iterator();
		while (!exist && iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof DecorationNode) {
				DecorationNode property = (DecorationNode) obj;
				if (isStereotypeProperty(property)) {
					exist = property.getElement().equals(propertyUML);
				}
			}
		}
		return exist;
	}


	/**
	 * Retrieves the property Text to display into the StereotypeCompartment
	 * 
	 * @param view
	 *            The StereotypeProperty (DecorationNode)
	 * @param property
	 *            The UML Property required to retrieve the value
	 * @return The property text to be displayed with the property name and its value (i.e.: "allocatedFrom=[]")
	 */
	public String getStereotypePropertyToDisplay(View view, Property property) {
		String propertyAndValue = EMPTY_STRING;
		if (view instanceof DecorationNode && property != null) {
			DecorationNode node = ((DecorationNode) view);
			if (isDisplayed(node)) {
				// maybe this is an inherited properties so the applied stereotype is not the owner of the property but the stereotype referenced by the container of stereotype properties
				if (node.eContainer() instanceof BasicCompartment) {
					if (isStereotypeProperty(node) || isStereotypeBraceProperty(node)) {
						BasicCompartment stereotypeCompartment = (BasicCompartment) node.eContainer();
						EObject stereotypeEObject = stereotypeCompartment.getElement();
						if (stereotypeEObject instanceof Stereotype) {
							final Stereotype stereotype = (Stereotype) stereotypeEObject;
							Element umlElement = getContainerSemanticElement(view);
							if (umlElement == null) {
								umlElement = getCommentSemanticElement(getTopContainer(view));
							}
							if (umlElement != null) {
								propertyAndValue = StereotypeUtil.displayPropertyValue(stereotype, property, umlElement, StereotypeDisplayConstant.STEREOTYPE_PROPERTIES_SEPARATOR);
							}
						}
					}
				}
			}
		}
		return propertyAndValue;
	}

	/**
	 * Defines if the Property should be displayed in the location
	 * 
	 * @param node
	 *            The StereotypeProperty node to display
	 * @param location
	 *            The Location on which the test is done
	 * @return True if the property node should be displayed at the location.
	 */
	public boolean isDisplayed(Node node) {
		return node.isVisible() && ((Node) node.eContainer()).isVisible();

	}


	/**
	 * Tests if the node is contained into a Stereotype Comment.
	 * 
	 * @param node
	 *            The Node tested
	 * @return True if the Node is contained into a Stereotype Comment
	 *         False by default
	 */
	public boolean isInStereotypeComment(Node node) {
		boolean isComment = false;
		View container = getTopContainer(node);
		isComment = isStereotypeComment(container);
		return isComment;
	}


	/**
	 * Adds properties and value to the existing text.
	 * 
	 * @param textToDisplay
	 *            Initial Text (ie: "")
	 * @param compartment
	 *            Compartment containing the Properties to be added (i.e: Allocation property Compartment )
	 * @param inComment
	 * 
	 * @return the String with the new Properties and their values concatenated. (i.e: "allocatedFrom=[], allocatedTo[]")
	 */
	protected String addStereotypeCompartmentProperties(String textToDisplay, BasicCompartment compartment) {
		StringBuilder newTextToDisplay = new StringBuilder(textToDisplay);
		StringBuilder propertiesText = new StringBuilder(EMPTY_STRING);
		EList<?> properties = compartment.getChildren();
		// For the compartment, concatenate all the properties Text (property name and values) that should be displayed in Braces
		for (Object property : properties) {
			if (isStereotypeBraceProperty(property)) {
				DecorationNode propertyNode = (DecorationNode) property;
				Property propertyElement = (Property) propertyNode.getElement();
				// get the properties and values text (i.e: "allocatedFrom=[]")
				if (isDisplayed(propertyNode)) {
					String propAndValueText = getStereotypePropertyToDisplay(propertyNode, propertyElement);
					// add it to the String (i.e: "allocatedTo=[], allocatedFrom=[]")
					propertiesText = addTextWithSeparator(propertiesText, propAndValueText, StereotypeDisplayConstant.STEREOTYPE_LABEL_SEPARATOR);
				}
			}
		}

		// And concatenate it to the existing text.(i.e: "isEncapsulated=false, allocatedTo=[], allocatedFrom=[]" )
		newTextToDisplay = addTextWithSeparator(newTextToDisplay, propertiesText.toString(), StereotypeDisplayConstant.STEREOTYPE_LABEL_SEPARATOR);

		String finalText = newTextToDisplay.toString();
		return finalText;
	}


	/**
	 * Concatenates Text to another text with a separator.
	 * 
	 * @param textToAdd
	 *            The fragment to add
	 * @param separator
	 *            The separator String (i.e: ", " or "; " or " ")
	 * 
	 * @return The new text with separator and no empty space
	 */
	protected StringBuilder addTextWithSeparator(final StringBuilder initialText, final String textToAdd, final String separator) {
		StringBuilder result = new StringBuilder(initialText);
		// if result and text to add is not empty, then add a separator
		if ((!EMPTY_STRING.equals(textToAdd) && (textToAdd != null))) {
			if ((result != null) && (!result.toString().equals(EMPTY_STRING))) {
				result.append(separator);
			}
			result.append(textToAdd);
		}
		return result;
	}



	/**
	 * Defines if the Comment has Property to display in brace
	 * 
	 * @param comment
	 *            the Comment Node
	 * @return true if the Brace text is not empty.
	 */
	protected boolean hasBraceLabel(Node comment) {
		boolean hasBrace = false;
		String braceTextInComment = getStereotypePropertiesInBrace(comment);
		if (braceTextInComment != null) {
			hasBrace = !braceTextInComment.isEmpty();
		}
		return hasBrace;
	}

	/**
	 * Defines if the Node has at least one Stereotype Compartment as Child
	 * 
	 * @param view
	 *            The node on which we do the test
	 * @return true if at least one Stereotype Compartment
	 */
	public boolean hasStereotypeCompartment(View view) {
		Object obj;
		Iterator<?> iter = view.getChildren().iterator();
		boolean compartmentExist = false;
		while (iter.hasNext() && !compartmentExist) {
			obj = iter.next();
			compartmentExist = isStereotypeCompartment(obj);
		}
		return compartmentExist;
	}


	/**
	 * Defines if the Node has at least one Stereotype View as Child
	 * 
	 * @param view
	 *            The node on which we do the test
	 * @return true if at least one Stereotype Compartment, Brace or Label
	 */
	public boolean hasStereotypeViews(View view) {
		Iterator<?> iter = view.getChildren().iterator();
		boolean compartmentExist = false;
		while (iter.hasNext() && !compartmentExist) {
			Object obj = iter.next();
			compartmentExist = isStereotypeCompartment(obj)
					|| isStereotypeBrace(obj)
					|| isStereotypeLabel(obj);

		}
		return compartmentExist;
	}


	/**
	 * Defines if a Stereotype Comment Node has at least a brace or a compartment
	 * 
	 * @param comment
	 *            The Stereotype Comment Node on which the test is done
	 * @return true if the comment has no compartment and no braceLabel, false by default
	 */
	public boolean isCommentEmpty(Node comment) {
		boolean empty = false;
		empty = (!hasStereotypeCompartment(comment)) && (!hasBraceLabel(comment));
		return empty;
	}

	/**
	 * Define if a Node is empty or not. If at least one property should be displayed, the node is not empty for this location.
	 * 
	 * @param compartment
	 *            The main StereotypeCompartment that contains that property Nodes (not the Comment StereotypeCompartment)
	 * @param location
	 *            Location for which the test is done.
	 * @return true by default, false if at least one property node is displayed.
	 */
	public boolean isEmpty(Node compartment) {
		boolean empty = true;
		if (compartment != null) {
			Iterator<?> childrenIterator = compartment.getChildren().iterator();
			while (empty && childrenIterator.hasNext()) {
				Object property = childrenIterator.next();
				if (isStereotypeProperty(property) || isStereotypeBraceProperty(property)) {
					if (isDisplayed((Node) property)) {
						empty &= false;
					}
				}
			}
		}
		return empty;
	}

	/**
	 * @deprecated in 2.1.0 (call element.isStereotypeApplied(stereotype) directly)
	 * @param stereotype
	 * @param hostSemanticElement
	 * @return
	 */
	@Deprecated
	public boolean isAppliedStereotype(Stereotype stereotype, Element hostSemanticElement) {
		return hostSemanticElement.isStereotypeApplied(stereotype);
	}

	/**
	 * Defines if an object is a Stereotype View
	 * 
	 * @param element
	 *            the Object on which the test is done
	 * @return true if object is one of the following:
	 *         <ul>
	 *         <li>Stereotype Compartment</li>
	 *         <li>Stereotype Brace</li>
	 *         <li>Stereotype Label</li>
	 *         <li>Stereotype Property</li>
	 *         </ul>
	 */
	public boolean isStereotypeView(Object element) {
		boolean stereotypeView = (isStereotypeCompartment(element) || isStereotypeBrace(element) || isStereotypeLabel(element) || isStereotypeBraceProperty(element) || isStereotypeProperty(element));
		return stereotypeView;
	}


	/**
	 * Define if the Brace Compartment Exist already
	 * 
	 * @param node
	 *            Node container of the Brace
	 * @param stereotype
	 *            Related Brace Stereotype
	 * @return true is at least one Brace Compartment for the specified stereotype exist
	 */
	public boolean isBraceCompartmentExist(View node, Stereotype stereotype) {
		boolean exist = false;
		Iterator<?> iter = node.getChildren().iterator();
		while (!exist && iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof BasicCompartment) {
				BasicCompartment compartment = (BasicCompartment) obj;
				if (isStereotypeBrace(compartment)) {
					exist = compartment.getElement().equals(stereotype);
				}
			}
		}
		return exist;
	}

	/**
	 * Define if the Brace Property Exist already
	 * 
	 * @param compartment
	 *            The Brace compartment
	 * @param propertyUML
	 *            Related Brace Property
	 * @return true is at least one Brace Property for the specified stereotype exist
	 */
	public boolean isBracePropertyExist(Node compartment, Property propertyUML) {
		boolean exist = false;
		Iterator<?> iter = compartment.getChildren().iterator();
		while (!exist && iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof DecorationNode) {
				DecorationNode property = (DecorationNode) obj;
				if (isStereotypeBraceProperty(property)) {
					exist = property.getElement().equals(propertyUML);
				}
			}
		}
		return exist;
	}


	/**
	 * Get the Brace Compartment of a node
	 * 
	 * @param node
	 *            View of the node of which the compartment is looking for.
	 * @param stereotype
	 *            Stereotype of the Brace Compartment we are searching for.
	 * @return The Basic Compartment if any or null if not.
	 */
	public BasicCompartment getStereotypeBraceCompartment(View node, Stereotype stereotype) {
		if ((stereotype != null) && (node != null)) {
			Object obj;
			Iterator<?> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				obj = iter.next();
				if (isStereotypeBrace(obj)) {
					BasicCompartment compartment = (BasicCompartment) obj;
					if (compartment.getElement() != null) {
						if (compartment.getElement().equals(stereotype)) {
							return compartment;
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * Define if a Stereotype Compartment has visibleproperties.
	 * 
	 * @param stereotypeCompartment
	 *            The Compartment To Test
	 * @return true if at least one property is displayed
	 */
	public boolean hasVisibleProperties(View stereotypeCompartment) {
		boolean visibleProperties = false;
		if (stereotypeCompartment != null) {
			Iterator<?> iter = stereotypeCompartment.getChildren().iterator();
			while (iter.hasNext() && !visibleProperties) {
				Object child = iter.next();
				if (isStereotypeProperty(child)) {
					visibleProperties |= (((DecorationNode) child).isVisible());
				}
			}
		}
		return visibleProperties;
	}


	/**
	 * @param domain
	 * @param view
	 * @return
	 */
	public void unsetPersistency(final TransactionalEditingDomain domain, final View view) {
		try {
			domain.runExclusive(new Runnable() {


				@Override
				public void run() {
					Display.getCurrent().asyncExec(new Runnable() {


						@Override
						public void run() {

							// use to avoid to put it in the command stack
							UnsetPersistentViewCommand command = new UnsetPersistentViewCommand(domain, view);
							try {
								GMFUnsafe.write(domain, command);
							} catch (Exception e) {
								Activator.log.error(e);
							}
						}
					});
				}
			});

		} catch (Exception e) {
			Activator.log.error(e);
		}
	}

	/**
	 * Define if the element is a Stereotype Comment Link
	 * 
	 * @param element
	 *            the object to test
	 * @return true if it is a Stereotype Comment Link
	 */
	public boolean isStereotypeCommentLink(Object element) {
		boolean stereotypeCommentLink = Boolean.FALSE;
		if (element instanceof Edge) {
			stereotypeCommentLink = StereotypeDisplayConstant.STEREOTYPE_COMMENT_LINK_TYPE.equals(((Edge) element).getType());
		}
		return stereotypeCommentLink;
	}

	/**
	 * Get the semantic element from a view.
	 * In case of a view into a Stereotype Comment, return the semantic element of Base Element
	 * 
	 * @param view
	 *            The view of which the Semantic element is retrieved
	 * @return the semantic element
	 */
	public Element getSemanticElement(View view) {
		if (isStereotypeComment(view)) {
			return getCommentSemanticElement(view);
		} else {
			if (view.getElement() instanceof Element) {
				return (Element) view.getElement();
			}
		}
		return null;
	}

	/**
	 * Concatenate Label and Property in Brace for the floating Stereotype Label.
	 * 
	 * @param view
	 *            The Main view of the object on which the stereotype is applied
	 * @return String with Stereotype Label and Properties in Braces
	 */
	public String getStereotypeTextForFloatingLabel(View view) {
		StringBuilder text = new StringBuilder();

		String label = getStereotypeTextToDisplay(view);
		String brace = getStereotypePropertiesInBrace(view);

		if (null != label && !label.isEmpty()) {
			text.append(label);
		}

		if (null != brace && !brace.isEmpty()) {
			text.append(StereotypeDisplayConstant.STEREOTYPE_PROPERTY_SEPARATOR).append(StereotypeDisplayConstant.BRACE_LEFT + brace + StereotypeDisplayConstant.BRACE_RIGHT);
		}
		return text.toString();
	}

	/**
	 * Check if the Comment view has visible compartment.
	 * 
	 * @param comment
	 * @return
	 */
	public boolean hasVisibleCompartment(View comment) {
		boolean empty = true;
		if (isStereotypeComment(comment)) {
			Iterator<?> childrenIterator = comment.getChildren().iterator();
			while (empty && childrenIterator.hasNext()) {
				Object child = childrenIterator.next();
				if (isStereotypeCompartment(child)) {
					empty = !((View) child).isVisible();
				}
			}
		}
		return !empty;
	}

}
