/**
 *
 */
package org.eclipse.papyrus.infra.ui.editorsfactory;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

/**
 * Concrete implementation of the {@link IPageIconsRegistry}. This
 * implementation allows to add and remove {@link IPageIconsRegistry}.
 *
 *
 * @author cedric dumoulin
 * @since 1.2
 */
public class PageIconsRegistry implements IPageIconsRegistryExtended {

	/** list of registered icon factories */
	protected List<IEditorIconFactory> pageIcons = new ArrayList<IEditorIconFactory>();

	/**
	 * Constructor.
	 *
	 * @param editorFactoryRegistry
	 * @param servicesRegistry
	 */
	public PageIconsRegistry() {

	}

	/**
	 * Walk each registered {@link IEditorFactory} to find the one handling the
	 * specified pageIdentifier. Call the corresponding method in the found
	 * pageIdentifier.
	 *
	 * TODO Throw an exception to report errors.
	 *
	 * @see org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory#createIPageModel(java.lang.Object)
	 */
	@Override
	public Image getEditorIcon(Object pageIdentifier) {

		for (IEditorIconFactory factory : getPageIcons()) {
			if (factory.isPageModelFactoryFor(pageIdentifier)) {
				{
					// return factory.getEditorIcon(pageIdentifier);
					return factory.getEditorIcon(pageIdentifier);
				}
			}
		}
		// no editor found !
		// TODO Throw an exception.
		// throw new EditorNotFoundException("No editor registered for '" +
		// pageIdentifier + "'.");
		return null;
	}

	/**
	 * @return the editorFactories
	 */
	protected List<IEditorIconFactory> getPageIcons() {
		return pageIcons;
	}

	/**
	 * Add the specified {@link IEditorFactory}
	 *
	 * @param editorIconFactory
	 */
	public void add(IEditorIconFactory editorIconFactory) {
		// This should never happen
		if (editorIconFactory == null) {
			throw new RuntimeException("Parameter should not be null."); //$NON-NLS-1$
		}

		pageIcons.add(editorIconFactory);
	}

	/**
	 * Remove the specified {@link IEditorFactory}
	 *
	 * @param editorIconFactory
	 */
	public void remove(IEditorIconFactory editorIconFactory) {
		pageIcons.remove(editorIconFactory);
	}

	/**
	 * Return the path to the icon ressource.
	 *
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistryExtended#getEditorURLIcon(java.lang.Object)
	 *
	 * @param model
	 * @return
	 */
	@Override
	public String getEditorURLIcon(Object model) {
		for (IEditorIconFactory factory : getPageIcons()) {
			if (factory.isPageModelFactoryFor(model)) {
				{
					if (factory instanceof IEditorIconFactoryExtended) {
						return ((IEditorIconFactoryExtended) factory).getURLMainIcon(model);
					}
				}
			}
		}
		return "";
	}

	@Override
	public void dispose() {
		for (IEditorIconFactory factory : pageIcons) {
			factory.dispose();
		}
	}
}
