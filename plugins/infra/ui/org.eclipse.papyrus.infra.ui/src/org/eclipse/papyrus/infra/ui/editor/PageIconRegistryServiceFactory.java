/**
 *
 */
package org.eclipse.papyrus.infra.ui.editor;

import org.eclipse.papyrus.infra.core.services.IServiceFactory;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.editorsfactory.IPageIconsRegistry;
import org.eclipse.papyrus.infra.ui.editorsfactory.PageIconsRegistry;
import org.eclipse.papyrus.infra.ui.extension.diagrameditor.PluggableEditorFactoryReader;

/**
 * Service Factory to register {@link IPageIconsRegistry}.
 *
 * @author cedric dumoulin
 * @since 1.2
 *
 */
public class PageIconRegistryServiceFactory implements IServiceFactory {

	private PageIconsRegistry pageIconsRegistry;

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#init(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param servicesRegistry
	 * @throws ServiceException
	 */
	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#startService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void startService() throws ServiceException {
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.services.IService#disposeService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void disposeService() throws ServiceException {
		if (pageIconsRegistry != null) {
			pageIconsRegistry.dispose();
		}
	}

	/**
	 * Create and populate a {@link PageIconsRegistry}. Return it as the service
	 * instance.
	 *
	 * @return
	 */
	@Override
	public Object createServiceInstance() {
		if (pageIconsRegistry == null) {
			pageIconsRegistry = new PageIconsRegistry();
			PluggableEditorFactoryReader editorReader = new PluggableEditorFactoryReader(Activator.PLUGIN_ID);
			editorReader.populate(pageIconsRegistry);
		}
		return pageIconsRegistry;
	}

}
