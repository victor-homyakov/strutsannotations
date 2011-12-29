package by.shade.strutsannotations;

import javax.servlet.ServletException;

import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;

import by.shade.strutsannotations.plugin.StrutsAnnotationsPlugin;

/**
 * Struts ActionServlet with implicit annotations processing. Plugin StrutsAnnotationsPlugin is
 * called for every module configuration.
 *
 * @author homyakov
 * @version $Id$
 */
public class ActionServlet extends org.apache.struts.action.ActionServlet {

    private static final long serialVersionUID = -4876526000436621638L;

    private static final String PLUGIN_CLASS_NAME = StrutsAnnotationsPlugin.class
            .getCanonicalName();
    private static final PlugInConfig PLUGIN_CONFIG = createPlugInConfig();

    // private static final String DEFAULT_PROCESSOR_CLASS_NAME =
    // "org.apache.struts.action.RequestProcessor";
    private static final String DEFAULT_PROCESSOR_CLASS_NAME = org.apache.struts.action.RequestProcessor.class
            .getCanonicalName();
    private static final String PROCESSOR_CLASS_NAME = MoreDescriptiveRequestProcessor.class
            .getCanonicalName();

    @Override
    protected void initModulePlugIns(final ModuleConfig moduleConfig) throws ServletException {
        if (log.isDebugEnabled()) {
            log.debug("Init plugins for module '" + moduleConfig.getPrefix() + "'");
        }
        // moduleConfig.addPlugInConfig(createPlugInConfig());
        moduleConfig.addPlugInConfig(PLUGIN_CONFIG);
        super.initModulePlugIns(moduleConfig);
        updateController(moduleConfig);
    }

    private static PlugInConfig createPlugInConfig() {
        PlugInConfig plugInConfig = new PlugInConfig();
        plugInConfig.setClassName(PLUGIN_CLASS_NAME);
        return plugInConfig;
    }

    private static void updateController(final ModuleConfig moduleConfig) {
        if (DEFAULT_PROCESSOR_CLASS_NAME.equals(moduleConfig.getControllerConfig()
                .getProcessorClass())) {
            if (log.isDebugEnabled()) {
                log.debug("Replace default request processor for module '"
                        + moduleConfig.getPrefix() + "' with " + PROCESSOR_CLASS_NAME);
            }
            moduleConfig.getControllerConfig().setProcessorClass(PROCESSOR_CLASS_NAME);
        }
    }

}
