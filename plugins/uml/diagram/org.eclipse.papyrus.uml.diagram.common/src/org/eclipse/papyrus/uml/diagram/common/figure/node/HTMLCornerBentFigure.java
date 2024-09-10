/*****************************************************************************
 * Copyright (c) 2010, 2023 CEA LIST.
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
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - implementation of strike though, font and font color
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 581921
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.text.BlockFlow;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.AnimatableScrollPane;
import org.eclipse.gmf.runtime.draw2d.ui.text.TextFlowEx;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.nebula.widgets.richtext.painter.ResourceHelper;
import org.eclipse.papyrus.infra.ui.preferences.RichtextPreferencePage;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.parser.HTMLCleaner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Corner bend figure able to display styled text formatted in html.
 */
public class HTMLCornerBentFigure extends CornerBentFigure implements ILabelFigure, IMultilineEditableFigure {

	/**
	 * Font size prefix into 'span' node.
	 */
	private static final String FONT_SIZE_SPAN = "font-size: "; //$NON-NLS-1$

	/**
	 * Font prefix into 'span' node.
	 */
	private static final String FONT_FAMILY = "font-family: "; //$NON-NLS-1$

	/**
	 * Font Color prefix into 'span' node.
	 */
	private static final String COLOR_RGB = "color: rgb("; //$NON-NLS-1$

	/**
	 * HTML renderer extension ID
	 *
	 * @since 2.0
	 */
	public static final String EXTENSION_ID = Activator.ID + ".htmlRendererContributor"; //$NON-NLS-1$

	/** indicates if the figure should use local coordinates or not */
	protected boolean useLocalCoordinates = false;

	/** background color for this figure */
	static final Color THIS_BACK = new Color(null, 248, 249, 214);

	/** font used by default by this figure */
	private static final FontData DEFAULT_FONT = new FontData("Arial", 8, SWT.NORMAL); //$NON-NLS-1$

	/** font used for sampleCode by this figure */
	private static final FontData CODE_SAMPLE_FONT = new FontData("Lucida Console", 8, SWT.NORMAL); //$NON-NLS-1$

	/** font used for quote by this figure */
	private static final FontData QUOTE_FONT = new FontData("Monotype Corsiva", 10, SWT.NORMAL); //$NON-NLS-1$

	/** key for the font style, corresponding to the type of font */
	private static final String FONT_NAME = "face"; //$NON-NLS-1$

	/** key for the font style, corresponding to the type of font */
	private static final String FONT_SIZE = "size"; //$NON-NLS-1$

	/** main flow page */
	protected FlowPage page;

	/** Content for html renderer content */
	protected AnimatableScrollPane contentPane;

	/**
	 * HTML renderer from extensions (if any)
	 *
	 * @since 2.0
	 */
	protected HTMLRenderer htmlRenderer;

	/**
	 * Preference boolean
	 */
	protected Boolean useHtmlRenderer = null;
	/**
	 * Body size to remember so layout changes are not fired infinitely
	 *
	 * @since 2.0
	 */
	protected int oldWidth = 0;
	/**
	 * @since 2.0
	 */
	protected int oldHeight = 0;

	/** text styles stack */
	private final List<List<Styles>> myStyles = new ArrayList<>();

	/** font used for the figure */
	private FontData currentFontData;

	private String text = ""; // Used as the default text (instead of null) //$NON-NLS-1$

	/**
	 * The color style stack of text.
	 */
	private Stack<Color> colors = new Stack<>();

	/**
	 * set of font datas used by this comment. It will be cleaned juste after
	 * the creation of the comment
	 */
	private Set<FontData> cachedFontDatas = new HashSet<>();

	/**
	 * Creates a new HTMLCornerBentFigure.
	 */
	public HTMLCornerBentFigure() {
		super();
		createContents();
	}

	/**
	 * return the label thath contains the icon.
	 *
	 * @return the label that contains the icon
	 */
	public Label getIconLabel() {
		return this.iconLabel;
	}

	/**
	 * Generates the basic contents for this figure
	 */
	protected void createContents() {
		if (!useHtmlRenderer()) {
			page = new FlowPage();
			page.setForegroundColor(getForegroundColor());
			this.add(page);
		} else {
			if (htmlRenderer == null) {
				IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

				for (IConfigurationElement e : config) {
					if (!"contributor".equals(e.getName())) { //$NON-NLS-1$
						continue;
					}
					try {
						Object instance = e.createExecutableExtension("contributor"); //$NON-NLS-1$
						if (instance instanceof HTMLRenderer) {
							htmlRenderer = (HTMLRenderer) instance;
							break;
						}
					} catch (Exception ex) {
						Activator.log.warn("Invalid html renderer contribution from: " + e.getContributor()); //$NON-NLS-1$
					}
				}
			}

			IFigure htmlContent = null;

			if (htmlRenderer != null) {
				htmlContent = htmlRenderer.getFigure();
			}

			if (htmlContent != null) {
				this.add(htmlContent);
				addHTMLLayoutListener();
			} else {
				page = new FlowPage();
				page.setForegroundColor(getForegroundColor());
				this.add(page);
			}
		}
	}

	private HTMLLayoutListener htmlLayoutListener;

	private class HTMLLayoutListener extends LayoutListener.Stub {
		@Override
		public void postLayout(IFigure container) {
			if (oldWidth != container.getClientArea().width || oldHeight != container.getClientArea().height) {
				oldWidth = container.getClientArea().width;
				oldHeight = container.getClientArea().height;
				paintHTML();
			}
		}
	}

	private void addHTMLLayoutListener() {
		if (htmlLayoutListener == null) {
			htmlLayoutListener = new HTMLLayoutListener();
			HTMLCornerBentFigure.this.addLayoutListener(htmlLayoutListener);
		}
	}

	private void paintHTML() {
		if (htmlRenderer != null) {
			try {
				if (this.isVisible()) {
					if (Display.getDefault() != null) {
						int width = getClientArea().width;
						int height = getClientArea().height;

						if (width > 0 && height > 0) { // width and height can be nil before figures are displayed
							htmlRenderer.paintHTML(text, width, height, 0, 0);
						}
					}
				}
			} catch (Exception e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
	 *
	 * @return <code>true</code> if this Figure uses local coordinates
	 */
	@Override
	protected boolean useLocalCoordinates() {
		return useLocalCoordinates;
	}

	/**
	 * Returns the current instance of this class
	 *
	 * @return the current instance of this class
	 */
	public HTMLCornerBentFigure getCornerBentFigure() {
		return this;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#getIcon()
	 *
	 * @return
	 */

	@Override
	public Image getIcon() {
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#getText()
	 *
	 * @return
	 */

	@Override
	public String getText() {
		return text;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#setIcon(org.eclipse.swt.graphics.Image)
	 *
	 * @param icon
	 */

	@Override
	public void setIcon(Image icon) {


	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#setText(java.lang.String)
	 *
	 * @param text
	 */

	@Override
	public void setText(String text) {
		if (equals(text, this.text)) {
			return;
		}

		this.text = text;

		if (!useHtmlRenderer() || htmlRenderer == null) {
			if (page != null) {
				// remove all children from page.
				page.removeAll();

				// init the first font data
				currentFontData = new FontData("Wingdings", 8, SWT.NORMAL); //$NON-NLS-1$

				// generates new ones
				generateBlockForText(text, page);
			}
		} else {
			paintHTML();
		}
	}

	private static boolean equals(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}

		if (s1 != null) {
			return s1.equals(s2);
		} else {
			return s2.equals(s1);
		}
	}

	/**
	 * Generates block list for the given text, and adds it to the root flow
	 * page
	 *
	 * @param text
	 *            the string to display
	 */
	protected void generateBlockForText(String text, FlowPage page) {
		// parse the HMTL text and transforms it into a tree. "Body" tags
		// enforce the character chain to be a valid xml chain
		NodeList nodeList = generateNodeList("<body>" + text + "</body>"); //$NON-NLS-1$ //$NON-NLS-2$
		myStyles.clear();
		// generate blocks from this list and adds it to the flow page children
		if (nodeList.getLength() > 0) {
			generateBlocksFromNodeList(nodeList, page, new Stack<Styles>());
		} else {
			// problem during parsing
			// return only one text flow with the content of the text
			TextFlowEx textFlow = new TextFlowEx(text);
			page.add(textFlow);
		}
	}

	/**
	 * Builds the structure and content of block flows for a given list of nodes
	 *
	 * @param nodeList
	 *            the list of nodes from which to generates the blockflows
	 */
	protected void generateBlocksFromNodeList(NodeList nodeList, BlockFlow parentFlow, Stack<Styles> parentStyles) {
		// for each element in the list, generates the corresponding blocks
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String nodeName = node.getNodeName();

			short nodeType = node.getNodeType();
			if (nodeType == Node.TEXT_NODE) {
				String text = HTMLCleaner.cleanHTMLTags(node.getNodeValue());
				if (text == null || text.trim().isEmpty()) {
					continue;
				}
				List<Styles> styles = Collections.list(parentStyles.elements());
				myStyles.add(styles);
				generateTextFromTextNode(text, parentFlow, styles);
			} else {
				try {
					switch (HTMLTags.valueOf(nodeName)) {
					case body: // main tag for the comment body
						// create a block for the body
						generateBlocksFromBodyNode(node, parentFlow, parentStyles);
						break;
					case h3:
						generateBlocksFromH3Node(node, parentFlow, parentStyles);
						break;
					case h4: // sub section heading
						generateBlocksFromH4Node(node, parentFlow, parentStyles);
						break;
					case h5: // sub sub section heading
						generateBlocksFromH5Node(node, parentFlow, parentStyles);
						break;
					case strong: // bold character
					case b:
						generateBlocksFromStrongNode(node, parentFlow, parentStyles);
						break;
					case em: // italic
						generateBlocksFromItalicNode(node, parentFlow, parentStyles);
						break;
					case s: // strike through
						generateBlocksFromStrikeThroughNode(node, parentFlow, parentStyles);
						break;
					case u: // underline
						generateBlocksFromUnderlineNode(node, parentFlow, parentStyles);
						break;
					case sub: // subscript
						break;
					case sup: // superscript
						break;
					case blockquote: // indent left or right
						break;
					case table: // table
						break;
					case p: // paragraph
						generateBlocksFromParagraphNode(node, parentFlow, parentStyles);
						break;
					case br:
						generateBlocksFromBRNode(node, parentFlow);
						break;
					case font:
						generateBlocksForFontNode(node, parentFlow, parentStyles);
						break;
					case span:
						generateBlocksForSpanNode(node, parentFlow, parentStyles);
						break;
					default:
						break;
					}
				} catch (IllegalArgumentException ex) {
					// Ignored. Unsupported HTML Tag.
				}
			}
		}
	}

	/**
	 * Generate Blocks for a Span Node.
	 *
	 * @param node
	 *            the node from which to generate below flows
	 * @param parentFlow
	 *            the parent block flow which will contain the created block
	 * @param styles
	 *            stack of styles to be applied
	 * @since 3.0
	 */
	protected void generateBlocksForSpanNode(final Node node, final BlockFlow parentFlow, final Stack<Styles> styles) {
		NodeList childrenNodes = node.getChildNodes();
		NamedNodeMap attributes = node.getAttributes();
		Node namedItem = attributes.getNamedItem("style"); //$NON-NLS-1$
		String nodeValue = namedItem.getNodeValue();

		if (nodeValue.startsWith(COLOR_RGB)) {
			colors.push(ResourceHelper.getColor(nodeValue));
			styles.push(Styles.color);
			generateBlocksFromNodeList(childrenNodes, parentFlow, styles);
			styles.pop();
			colors.pop();
		} else if (nodeValue.startsWith(FONT_FAMILY)) {
			String fontString = nodeValue.substring(FONT_FAMILY.length());
			fontString = fontString.substring(0, fontString.length() - 1);
			String fontName = fontString.split(",")[0]; //$NON-NLS-1$

			String oldFont = (Styles.font.getData().get(FONT_NAME) != null) ? (String) Styles.font.getData().get(FONT_NAME) : "Segoe UI"; //$NON-NLS-1$
			Styles.font.getData().put(FONT_NAME, fontName);
			styles.push(Styles.font);

			generateBlocksFromNodeList(childrenNodes, parentFlow, styles);

			Styles.font.getData().put(FONT_NAME, oldFont);
			styles.pop();
		} else if (nodeValue.startsWith(FONT_SIZE_SPAN)) {

			String fontSizeString = nodeValue.substring(FONT_SIZE_SPAN.length());
			fontSizeString = fontSizeString.substring(0, fontSizeString.length() - 1).replace("px", ""); //$NON-NLS-1$ //$NON-NLS-2$

			int fontSize = Integer.parseInt(fontSizeString);
			int oldSize = (Styles.font.getData().get(FONT_SIZE) != null) ? ((Integer) Styles.font.getData().get(FONT_SIZE)) : -1;

			Styles.font.getData().put(FONT_SIZE, (fontSize - 4) / 2);

			styles.push(Styles.font);

			generateBlocksFromNodeList(childrenNodes, parentFlow, styles);
			if (-1 != oldSize) {
				Styles.font.getData().put(FONT_SIZE, oldSize);
			} else {
				Styles.font.getData().remove(FONT_SIZE);
			}
			styles.pop();

		} else {
			generateBlocksFromNodeList(childrenNodes, parentFlow, styles);
		}
	}

	/**
	 * Generates code from a node representing an underlined text.
	 *
	 * @param node
	 *            the node from which to generate belowk flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromUnderlineNode(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		NodeList childrenNodes = node.getChildNodes();

		styles.push(Styles.underline);
		generateBlocksFromNodeList(childrenNodes, parentFlow, styles);
		styles.pop();
	}

	/**
	 * Overrides Figure.setFont() method
	 * setup font for all TextFlow childes of this figure
	 *
	 */
	@Override
	public void setFont(Font f) {
		if (page != null) {
			super.setFont(f);

			List<TextFlow> textFlowList = findTextFlowChildList(page);
			int i = 0;
			for (TextFlow nextTextFlow : textFlowList) {
				if (i == myStyles.size()) {
					// make code more robust
					break;
				}
				nextTextFlow.setFont(calculateCurrentFont(myStyles.get(i)));
				i++;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Overridden to refresh text after set of font and font color to be taken into account.
	 *
	 * @see org.eclipse.draw2d.Figure#setForegroundColor(org.eclipse.swt.graphics.Color)
	 */
	@Override
	public void setForegroundColor(final Color fg) {
		super.setForegroundColor(fg);

		// Refresh text
		if (!useHtmlRenderer() || htmlRenderer == null) {
			if (page != null) {
				page.removeAll();
				generateBlockForText(text, page);
			}
		}
	}

	/**
	 * @see HTMLCornerBentFigure.setFont() for using
	 *
	 * @param parent
	 * @return TextFlow childs list
	 */
	private List<TextFlow> findTextFlowChildList(IFigure parent) {
		List<TextFlow> result = new ArrayList<>();
		for (Object nextFigure : parent.getChildren()) {
			if (!(nextFigure instanceof TextFlow)) {
				result.addAll(findTextFlowChildList((IFigure) nextFigure));
				continue;
			}
			result.add((TextFlow) nextFigure);
		}
		return result;
	}

	/**
	 * Generates code from a node representing a text.
	 * IFigure default font value will setup if styles list is empty.
	 *
	 * @param node
	 *            the node from which to generate belowk flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateTextFromTextNode(String text, BlockFlow parentFlow, List<Styles> styles) {
		TextFlowEx textFlow = new TextFlowEx(text);
		textFlow.setTextUnderline(getUnderLineFromStyles(styles));
		textFlow.setTextStrikeThrough(getStrikeThroughFromStyles(styles));
		textFlow.setFont(calculateCurrentFont(styles));
		textFlow.setForegroundColor(calculateCurrentForeground(styles));

		parentFlow.add(textFlow);
	}

	/**
	 * Calculate Current Foreground color.
	 *
	 * @param styles
	 *            The list of styles.
	 * @return The current foreground color.
	 */
	private Color calculateCurrentForeground(final List<Styles> styles) {
		Color result = null;
		for (Styles style : styles) {
			switch (style) {
			case color:
				result = colors.firstElement();
				break;
			default:
				break;
			}
		}
		return null != result ? result : getForegroundColor();
	}

	/**
	 * Calculates current font as default figure font + styles
	 * styles has overrides default font
	 * It's main method for getting font value by this figure
	 *
	 * @return
	 */
	private Font calculateCurrentFont(List<Styles> styles) {
		return composeFontWithStyles(getDefaultFontData(), styles);
	}

	/**
	 * Extracts underLine value from styles
	 *
	 * @see generateTextFromTextNode() for using
	 * @return
	 */
	private boolean getUnderLineFromStyles(List<Styles> styles) {
		boolean result = false;
		for (Styles style : styles) {
			switch (style) {
			case underline:
				result = true;
				break;
			default:
				break;
			}
		}
		return result;
	}

	/**
	 * Extract strike through value from styles.
	 *
	 * @param styles
	 *            the list of styles
	 */
	private boolean getStrikeThroughFromStyles(final List<Styles> styles) {
		boolean result = false;
		for (Styles style : styles) {
			switch (style) {
			case strikeThrough:
				result = true;
				break;
			default:
				break;
			}
		}
		return result;
	}

	/**
	 * Calculates default fontData without styles
	 *
	 * @return
	 */
	private FontData getDefaultFontData() {
		boolean quote = false;
		boolean codeSample = false;
		if (codeSample) {
			return CODE_SAMPLE_FONT;
		}
		if (quote) {
			return QUOTE_FONT;
		}
		FontData fromDefaultFigure = getCurrentFigureFontData();
		if (fromDefaultFigure == null) {
			return DEFAULT_FONT;
		}
		return fromDefaultFigure;
	}

	/**
	 * Font -> FontData converter
	 *
	 * @return Current Figure FonData value
	 */
	private FontData getCurrentFigureFontData() {
		if (getFont() == null) {
			return null;
		}
		if (getFont().getFontData() == null || getFont().getFontData().length == 0) {
			return null;
		}
		return getFont().getFontData()[0];
	}

	/**
	 * Compose figure font with styles
	 *
	 * @param defaultFontData
	 * @return
	 */
	private Font composeFontWithStyles(FontData defaultFontData, List<Styles> styles) {
		if (styles == null || styles.isEmpty()) {
			return (Font) JFaceResources.getResources().get(FontDescriptor.createFrom(defaultFontData));
		}
		FontData defaultFontDataCopy = FontDescriptor.copy(defaultFontData);
		int defaultStyle = defaultFontDataCopy.getStyle();
		boolean quote = false;
		boolean codeSample = false;
		// calculate the font to apply
		for (Styles style : styles) {
			switch (style) {
			case italic:
				defaultStyle = defaultStyle | SWT.ITALIC;
				break;
			case strong:
				defaultStyle = defaultStyle | SWT.BOLD;
				break;
			case quote:
				quote = true;
				break;
			case code:
				codeSample = true;
				break;
			case font:
				if (Styles.font.getData().get(FONT_NAME) != null) {
					defaultFontDataCopy.setName((String) Styles.font.getData().get(FONT_NAME));
				}
				if (Styles.font.getData().get(FONT_SIZE) != null) {
					// font size = [1..7] in html, but does not correspond to system
					// size... 2 by default => 8 in real size.
					// so: real size = (html font size)+6
					defaultFontDataCopy.setHeight(((Integer) Styles.font.getData().get(FONT_SIZE)) * 2 + 4);
				}
				break;
			default:
				break;
			}
		}
		defaultFontDataCopy.setStyle(defaultStyle);
		if (codeSample) {
			defaultFontDataCopy = CODE_SAMPLE_FONT;
		} else if (quote) {
			defaultFontDataCopy = QUOTE_FONT;
		}
		return (Font) JFaceResources.getResources().get(FontDescriptor.createFrom(defaultFontDataCopy));
	}

	/**
	 * Generates code from a node representing a bolded text.
	 *
	 * @param node
	 *            the node from which to generate block flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromStrongNode(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		NodeList childrenNodes = node.getChildNodes();

		styles.push(Styles.strong);
		generateBlocksFromNodeList(childrenNodes, parentFlow, styles);
		styles.pop();
	}

	/**
	 * Generates code from a node representing an italic styled text.
	 *
	 * @param node
	 *            the node from which to generate belowk flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromItalicNode(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		NodeList childrenNodes = node.getChildNodes();

		styles.push(Styles.italic);
		generateBlocksFromNodeList(childrenNodes, parentFlow, styles);
		styles.pop();
	}

	/**
	 * Generate code from a node representing a strike through styled text.
	 *
	 * @param node
	 *            the node from which to generate below flows
	 * @param parentFlow
	 *            the parent block flow which will contain the created block
	 * @param styles
	 *            the stack of styles
	 * @since 3.0
	 */
	protected void generateBlocksFromStrikeThroughNode(final Node node, final BlockFlow parentFlow, final Stack<Styles> styles) {
		NodeList childrenNodes = node.getChildNodes();

		styles.push(Styles.strikeThrough);
		generateBlocksFromNodeList(childrenNodes, parentFlow, styles);
		styles.pop();
	}

	/**
	 * Generates code from a node with new Font.
	 *
	 * @param node
	 *            the node from which to generate belowk flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksForFontNode(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		// retrieves the font to apply
		Node fontNameNode = node.getAttributes().getNamedItem("face"); //$NON-NLS-1$
		Node fontSizeNode = node.getAttributes().getNamedItem("size"); //$NON-NLS-1$
		String oldFont = ""; //$NON-NLS-1$
		int oldSize = 8;

		if (fontNameNode != null) {
			String fontName = fontNameNode.getNodeValue();
			oldFont = (Styles.font.getData().get(FONT_NAME) != null) ? (String) Styles.font.getData().get(FONT_NAME) : "Arial"; //$NON-NLS-1$
			Styles.font.getData().put(FONT_NAME, fontName);
			styles.push(Styles.font);
		}
		if (fontSizeNode != null) {
			int fontSize = Integer.parseInt(fontSizeNode.getNodeValue());
			oldSize = (Styles.font.getData().get(FONT_SIZE) != null) ? ((Integer) Styles.font.getData().get(FONT_SIZE)) : 2;
			Styles.font.getData().put(FONT_SIZE, fontSize);
			styles.push(Styles.font);
		}

		NodeList childrenNodes = node.getChildNodes();

		generateBlocksFromNodeList(childrenNodes, parentFlow, styles);

		if (fontNameNode != null) {
			Styles.font.getData().put(FONT_NAME, oldFont);
			styles.pop();
		}
		if (fontSizeNode != null) {
			Styles.font.getData().put(FONT_SIZE, oldSize);
			styles.pop();
		}
	}

	/**
	 * Generates code from a node representing a H3 section (header section).
	 *
	 * @param node
	 *            the node from which to generate below flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromH3Node(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		BlockFlow blockFlow = new BlockFlow();
		NodeList childrenNodes = node.getChildNodes();

		styles.push(Styles.header3);
		generateBlocksFromNodeList(childrenNodes, blockFlow, styles);
		styles.pop();
		parentFlow.add(blockFlow);
	}

	/**
	 * Generates code from a node representing a H3 section (header section).
	 *
	 * @param node
	 *            the node from which to generate belowk flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromH4Node(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		BlockFlow blockFlow = new BlockFlow();
		NodeList childrenNodes = node.getChildNodes();

		styles.push(Styles.header4);
		generateBlocksFromNodeList(childrenNodes, blockFlow, styles);
		styles.pop();
		parentFlow.add(blockFlow);
	}

	/**
	 * Generates code from a node representing a H3 section (header section).
	 *
	 * @param node
	 *            the node from which to generate belowk flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromH5Node(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		BlockFlow blockFlow = new BlockFlow();
		NodeList childrenNodes = node.getChildNodes();

		styles.push(Styles.header5);
		generateBlocksFromNodeList(childrenNodes, blockFlow, styles);
		styles.pop();
		parentFlow.add(blockFlow);
	}

	/**
	 * Pretty prints the list of child nodes
	 *
	 * @param childNodes
	 */
	@SuppressWarnings("unused")
	private void debug(NodeList childNodes) {
		for (int i = 0; i < childNodes.getLength(); i++) {
			System.err.println("[" + i + "] " + childNodes.item(i).getNodeName()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Generates code from a node representing a body.
	 *
	 * @param node
	 *            the node from which to generate block flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromBodyNode(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		BlockFlow blockFlow = new BlockFlow();
		NodeList childrenNodes = node.getChildNodes();

		generateBlocksFromNodeList(childrenNodes, blockFlow, styles);

		parentFlow.add(blockFlow);
	}

	/**
	 * Generates code from a node representing a paragraph.
	 *
	 * @param node
	 *            the node from which to generate block flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromParagraphNode(Node node, BlockFlow parentFlow, Stack<Styles> styles) {
		BlockFlow blockFlow = new BlockFlow();

		// perhaps a style is associated to the paragraph (class="code sample"
		// for example)
		NamedNodeMap attributes = node.getAttributes();
		Node classNode = attributes.getNamedItem("class"); //$NON-NLS-1$
		boolean hasToPop = false;
		if (classNode != null) {
			String classNodeValue = classNode.getNodeValue();

			if ("codeSample".equals(classNodeValue)) { //$NON-NLS-1$
				hasToPop = true;
				styles.push(Styles.code);
			} else if ("quote".equals(classNodeValue)) { //$NON-NLS-1$
				styles.push(Styles.quote);
				hasToPop = true;
			}
		}

		NodeList childrenNodes = node.getChildNodes();

		generateBlocksFromNodeList(childrenNodes, blockFlow, styles);

		if (hasToPop) {
			styles.pop();
		}
		parentFlow.add(blockFlow);
	}

	/**
	 * Generates code from a node representing a carraige return.
	 *
	 * @param node
	 *            the node from which to generate block flows
	 * @param parentFlow
	 *            the parent block flow which will contain the block created
	 */
	protected void generateBlocksFromBRNode(Node node, BlockFlow parentFlow) {
		BlockFlow blockFlow = new BlockFlow();
		parentFlow.add(blockFlow);
	}

	/**
	 * Generates a list of nodes from the parse of an html text
	 *
	 * @param text
	 *            the text to parse
	 * @return the parsed text under the form of a list of nodes
	 */
	protected NodeList generateNodeList(String text) {
		return HTMLCommentParser.parse(text);
	}

	/**
	 * Valid HTML tags enumeration
	 *
	 * @since 3.0
	 */
	protected enum HTMLTags {

		body(""), // main tag for the comment body //$NON-NLS-1$
		h3(""), // section heading //$NON-NLS-1$
		h4(""), // sub section heading //$NON-NLS-1$
		h5(""), // sub sub section heading //$NON-NLS-1$
		strong(""), // bold character //$NON-NLS-1$
		b(""), // bold character //$NON-NLS-1$
		/**
		 * @since 3.0
		 */
		em(""), // italic //$NON-NLS-1$
		/**
		 * @since 3.0
		 */
		s(""), // strike through //$NON-NLS-1$
		u(""), // underline //$NON-NLS-1$
		sub(""), // subscript //$NON-NLS-1$
		sup(""), // superscript //$NON-NLS-1$
		blockquote(""), // indent left or right //$NON-NLS-1$
		table(""), // table //$NON-NLS-1$
		p(""), // paragraph //$NON-NLS-1$
		br(""), // new line //$NON-NLS-1$
		font(""), // specific font //$NON-NLS-1$
		/**
		 * @since 3.0
		 */
		span(""); //$NON-NLS-1$

		/** additional data for this enum */
		protected String data;

		HTMLTags(String data) {
			this.data = data;
		}

		/**
		 * Sets the data for this enum
		 *
		 * @param data
		 *            the data to set
		 */
		public void setData(String data) {
			this.data = data;
		}

		/**
		 * Returns the data associated to this enum
		 *
		 * @return the data associated to this enum
		 */
		public String getData() {
			return data;
		}

	}

	/**
	 * Styles to apply to the text
	 */
	protected enum Styles {
		strong, header3, header4, header5, underline, italic, code, subscript, supscript, quote, font(new HashMap<String, Object>()),
		/**
		 * @since 3.0
		 */
		strikeThrough,
		/**
		 * @since 3.0
		 */
		color;

		/** additional data */
		private Map<String, Object> data;

		Styles() {
			this.data = null;
		}

		Styles(Map<String, Object> data) {
			this.data = data;
		}

		/**
		 * sets the data associated to this enum
		 *
		 * @param data
		 *            the data to set
		 */
		public void setData(Map<String, Object> data) {
			this.data = data;
		}

		/**
		 * Returns the data for this enum
		 *
		 * @return the data for this enum
		 */
		public Map<String, Object> getData() {
			return data;
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure#getEditionLocation()
	 *
	 */
	@Override
	public Point getEditionLocation() {
		return getBounds().getTopLeft();
	}

	private boolean useHtmlRenderer() {
		if (useHtmlRenderer == null) {
			useHtmlRenderer = false;
			IPreferenceStore store = org.eclipse.papyrus.infra.ui.Activator.getDefault().getPreferenceStore();
			if (store != null) {
				useHtmlRenderer = store.getBoolean(RichtextPreferencePage.USE_HTML_RENDERER);
			}
		}

		return useHtmlRenderer;
	}
}
