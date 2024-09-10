/*****************************************************************************
 * Copyright (c) 2012, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 461629
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.configuration.handler;

import static org.eclipse.papyrus.infra.gmfdiag.css.configuration.helper.DiagramTypeHelper.getDiagramType;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.GradientData;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.appearance.helper.VisualInformationPapyrusConstants;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.css.configuration.Activator;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.BaseCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.provider.CustomStyle;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.AttributeSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.CSSFactory;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ClassSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.CssSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.CssTok;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ElementSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.charset;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.css_declaration;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.css_property;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ruleset;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.selector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.simple_selector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.stylesheet;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForHandlers;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;


public abstract class AbstractStyleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		ISelection selection;
		try {
			selection = ServiceUtilsForHandlers.getInstance().getNestedActiveIEditorPart(event).getSite().getSelectionProvider().getSelection();
			if (selection.isEmpty()) {
				return null;
			}
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			return null;
		}

		if (!(selection instanceof IStructuredSelection)) {
			return null;
		}

		IStructuredSelection sSelection = (IStructuredSelection) selection;
		Object element = sSelection.getFirstElement();

		View view = NotationHelper.findView(element);
		if (view == null) {
			Activator.log.warn("Cannot create a Style from the selected element ; the element is not a View");
			return null;
		}

		Shell parentShell = ((Event) event.getTrigger()).widget.getDisplay().getActiveShell();

		if (view.getElement() == null || view instanceof Diagram) {
			MessageDialog.open(MessageDialog.WARNING, parentShell, "Style error", "The selected element's style cannot be exported", SWT.NONE);
			return null;
		}

		Map<css_declaration, Boolean> declarations = handleStyles(view);
		Map<AttributeSelector, Boolean> conditions = handleSemantic(view);

		String selectorName = view.getElement().eClass().getName();

		AbstractStyleDialog dialog = createStyleDialog(parentShell, declarations, conditions, selectorName, view);

		if (dialog.open() != Window.OK) {
			return null;
		}

		ruleset ruleset = getRuleset(dialog);
		selector baseSelector = CSSFactory.eINSTANCE.createselector();
		simple_selector simple_selector = createSimpleElementSelector(selectorName, !dialog.useSelectorName());

		baseSelector.getSimpleselectors().add(simple_selector);

		if (dialog.getDiagramRestriction()) {
			String diagramType = getDiagramType(view.getDiagram());
			selector compositeSelector = CSSFactory.eINSTANCE.createselector();
			compositeSelector.setSelector(baseSelector);


			simple_selector diagramSelector = createSimpleElementSelector(diagramType, false);

			compositeSelector.getSimpleselectors().add(diagramSelector);

			ruleset.getSelectors().add(compositeSelector);
		} else {
			ruleset.getSelectors().add(baseSelector);
		}

		if (dialog.getCSSClass() != null) {
			String cssClass = dialog.getCSSClass();
			ClassSelector classCondition = CSSFactory.eINSTANCE.createClassSelector();
			classCondition.setName(cssClass);
			simple_selector.getSubSelectors().add(classCondition);
		}

		for (CssSelector condition : conditions.keySet()) {
			if (conditions.get(condition)) {
				simple_selector.getSubSelectors().add(condition);
			}
		}

		for (css_declaration declaration : declarations.keySet()) {
			if (declarations.get(declaration)) {
				ruleset.getDeclarations().add(declaration);
			}
		}

		stylesheet xtextStylesheet = getStyleSheet(dialog, view);

		if (xtextStylesheet == null) {
			return null;
		}

		Resource resource = xtextStylesheet.eResource();

		if (!xtextStylesheet.getRuleset().contains(ruleset)) {
			xtextStylesheet.getRuleset().add(ruleset);
		}

		try {
			resource.save(new HashMap<>());
			BaseCSSEngine.INSTANCE.reset();
			DiagramHelper.forceRefresh();
		} catch (IOException ex) {
			Activator.log.error(ex);
			MessageDialog.open(MessageDialog.ERROR, parentShell, "Style error", "An unexpected error occured while trying to save the Stylesheet", SWT.NONE);
		} catch (Exception ex) {
			Activator.log.error(ex);
			MessageDialog.open(MessageDialog.ERROR, parentShell, "Style error", "An unexpected error occured while trying to save the Stylesheet", SWT.NONE);
		}

		return null;
	}

	protected simple_selector createSimpleElementSelector(String elementName, boolean universal) {
		simple_selector simple_selector = CSSFactory.eINSTANCE.createsimple_selector();

		if (universal) {
			simple_selector.setUniversal(CSSFactory.eINSTANCE.createUniversalSelector());
		} else {
			ElementSelector elementSelector = CSSFactory.eINSTANCE.createElementSelector();
			elementSelector.setName(elementName);
			simple_selector.setElement(elementSelector);
		}

		return simple_selector;
	}

	protected abstract AbstractStyleDialog createStyleDialog(Shell shell, Map<css_declaration, Boolean> declarations, Map<AttributeSelector, Boolean> conditions, String selectorName, View context);

	protected abstract ruleset getRuleset(AbstractStyleDialog dialog);

	protected abstract stylesheet getStyleSheet(AbstractStyleDialog dialog, View contextView);

	protected Map<css_declaration, Boolean> handleStyles(View view) {
		Map<css_declaration, Boolean> declarations = new LinkedHashMap<>();

		for (Object styleObject : view.getStyles()) {
			Style style = (Style) styleObject;
			declarations.putAll(handleStyle(style));
		}

		if (view instanceof Style) {
			declarations.putAll(handleStyle((Style) view));
		}

		if (view instanceof CustomStyle) {
			declarations.putAll(handleCustomStyle((CustomStyle) view, view));
		}

		return declarations;
	}

	protected Map<AttributeSelector, Boolean> handleSemantic(View view) {
		Map<AttributeSelector, Boolean> result = new LinkedHashMap<>();

		EObject semanticElement = view.getElement();

		for (EStructuralFeature feature : semanticElement.eClass().getEAllStructuralFeatures()) {
			if (isBoolean(feature) || isInteger(feature) || feature.getEType() instanceof EEnum) {
				AttributeSelector attributeCondition = CSSFactory.eINSTANCE.createAttributeSelector();

				attributeCondition.setName(feature.getName());
				attributeCondition.setOp("=");
				attributeCondition.setValue(semanticElement.eGet(feature).toString());

				boolean check = semanticElement.eGet(feature) != feature.getDefaultValue();

				result.put(attributeCondition, check);
			}
		}

		return result;
	}

	protected boolean isBoolean(EStructuralFeature feature) {
		if (feature.getEType() == EcorePackage.eINSTANCE.getEBoolean() || feature.getEType() == EcorePackage.eINSTANCE.getEBooleanObject()) {
			return true;
		}

		if (feature.getEType() instanceof EDataType) {
			EDataType datatype = (EDataType) feature.getEType();
			return datatype.getName().equals("Boolean");
		}

		return false;
	}

	protected boolean isString(EStructuralFeature feature) {
		return feature.getEType() == EcorePackage.eINSTANCE.getEString();
	}

	protected boolean isInteger(EStructuralFeature feature) {
		if (feature.getEType() == EcorePackage.eINSTANCE.getEInt() || feature.getEType() == EcorePackage.eINSTANCE.getEIntegerObject()) {
			return true;
		}

		if (feature.getEType() instanceof EDataType) {
			EDataType datatype = (EDataType) feature.getEType();
			return datatype.getName().equals("Integer");
		}

		return false;
	}

	protected Map<css_declaration, Boolean> handleStyle(Style style) {
		if (style instanceof NamedStyle) {
			return Collections.emptyMap();
		}

		Map<css_declaration, Boolean> declarations = new LinkedHashMap<>();

		for (EStructuralFeature feature : style.eClass().getEAllStructuralFeatures()) {
			if (NotationPackage.eINSTANCE.getStyle().isSuperTypeOf(feature.getEContainingClass())) {
				Object currentValue = style.eGet(feature);
				Object defaultValue = feature.getDefaultValue();
				boolean check = currentValue == null ? currentValue != defaultValue : !currentValue.equals(defaultValue);

				css_declaration declaration = handleStyleFeature(style, feature);
				if (!declaration.getValueTokens().isEmpty()) { // If expression is null, the type of this property is not supported
					declarations.put(declaration, check);
				}
			}
		}

		return declarations;
	}

	// FIXME: Use a helper to determine whether the custom styles are computed or forced
	protected Map<css_declaration, Boolean> handleCustomStyle(CustomStyle customStyle, View view) {
		Map<css_declaration, Boolean> declarations = new LinkedHashMap<>();

		GMFToCSSConverter converter = GMFToCSSConverter.instance;

		handleCustomStyle(view, NamedStyleProperties.ELEMENT_ICON, VisualInformationPapyrusConstants.DISPLAY_NAMELABELICON, declarations, converter.convert(customStyle.showElementIcon()));
		handleCustomStyle(view, NamedStyleProperties.SHADOW, VisualInformationPapyrusConstants.SHADOWFIGURE, declarations, converter.convert(customStyle.showShadow()));
		handleCustomStyle(view, NamedStyleProperties.QUALIFIED_NAME_DEPTH, VisualInformationPapyrusConstants.QUALIFIED_NAME, declarations, converter.convert(customStyle.getQualifiedNameDepth()));

		return declarations;
	}

	protected void handleCustomStyle(View view, String cssProperty, String eAnnotationName, Map<css_declaration, Boolean> result, List<CssTok> expression) {
		css_declaration cssDeclaration = CSSFactory.eINSTANCE.createcss_declaration();

		setProperty(cssDeclaration, cssProperty);

		setValueTokens(cssDeclaration, expression);

		boolean check = view.getEAnnotation(eAnnotationName) != null;
		result.put(cssDeclaration, check);
	}

	protected css_declaration handleStyleFeature(Style style, EStructuralFeature feature) {
		css_declaration declaration = CSSFactory.eINSTANCE.createcss_declaration();

		setProperty(declaration, feature.getName());

		GMFToCSSConverter converter = GMFToCSSConverter.instance;

		if (isString(feature)) {
			setValueTokens(declaration, converter.convert((String) style.eGet(feature)));
		}

		if (isInteger(feature)) {
			if (feature.getName().endsWith("Color")) {
				Color color = FigureUtilities.integerToColor((Integer) style.eGet(feature));
				setValueTokens(declaration, converter.convert(color));
				color.dispose();
			} else {
				setValueTokens(declaration, converter.convert((Integer) style.eGet(feature)));
			}
		}

		if (feature.getEType() == NotationPackage.eINSTANCE.getGradientData()) {
			setValueTokens(declaration, converter.convert((GradientData) style.eGet(feature)));
		}

		if (feature.getEType() instanceof EEnum) {
			setValueTokens(declaration, converter.convert((Enumerator) style.eGet(feature)));
		}

		if (isBoolean(feature)) {
			setValueTokens(declaration, converter.convert((Boolean) style.eGet(feature)));
		}

		return declaration;
	}

	protected final void setProperty(css_declaration declaration, String property) {
		css_property cssProperty = CSSFactory.eINSTANCE.createcss_property();
		cssProperty.setName(property);
		declaration.setProperty(cssProperty);
	}

	protected final void setValueTokens(css_declaration declaration, List<CssTok> tokens) {
		declaration.getValueTokens().clear();
		declaration.getValueTokens().addAll(tokens);
	}

	protected final void setCharset(stylesheet stylesheet, String charset) {
		charset cssCharset = CSSFactory.eINSTANCE.createcharset();
		cssCharset.setCharset(charset);
	}

}
