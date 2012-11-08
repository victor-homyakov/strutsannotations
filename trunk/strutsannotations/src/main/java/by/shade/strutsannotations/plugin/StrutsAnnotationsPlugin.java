package by.shade.strutsannotations.plugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.servlet.UnavailableException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;

import by.shade.strutsannotations.StrutsAction;
import by.shade.strutsannotations.StrutsActions;
import by.shade.strutsannotations.scanner.StrutsAnnotationsScanner;

/**
 * Struts annotations processing plugin.
 *
 * To use StrutsAction and StrutsForward annotations, insert in struts-config.xml:
 *
 * <pre>
 * &lt;struts-config>
 *   &lt;plug-in className="by.shade.strutsannotations.plugin.StrutsAnnotationsPlugin" />
 * &lt;/struts-config>
 * </pre>
 *
 * and replace with annotations content of struts-config.xml:
 *
 * <pre>
 *   &lt;action path="/addMember" type="test.stuts.StrutsDemoAction"
 *       name="strutsDemoForm" input="/addMemberForm.do" scope="request"
 *       parameter="a=b" validate="true">
 *     &lt;forward name="success" path="/showMembers.do" redirect="true" />
 *     &lt;forward name="failure" path="/addMember.do" />
 *   &lt;/action>
 * </pre>
 *
 * @author homyakov
 * @version $Id$
 */
public class StrutsAnnotationsPlugin implements PlugIn {

    /** Commons logging instance. */
    private static final Log LOG = LogFactory.getLog(StrutsAnnotationsPlugin.class);

    private static Collection<Class<?>> actionClasses;

    /**
     * Receive notification that the specified module is being started up.
     *
     * @param servlet
     *            ActionServlet that is managing all the modules in this web application
     * @param moduleConfig
     *            ModuleConfig for the module with which this plug-in is associated
     * @throws UnavailableException
     *             if this plugin cannot be successfully initialized
     */
    @SuppressWarnings("unchecked")
    public void init(final ActionServlet servlet, final ModuleConfig moduleConfig)
            throws UnavailableException {
        LOG.info("Processing annotated action classes for module '" + moduleConfig.getPrefix()
                + "'");
        try {
            if (actionClasses == null) {
                actionClasses = StrutsAnnotationsScanner.findActions(new File(servlet
                        .getServletContext().getRealPath("WEB-INF/classes")));
                LOG.info(actionClasses.size() + " annotated action classes found");
            }
            for (Class<?> clazz : actionClasses) {
                updateActionConfig((Class<? extends Action>) clazz, moduleConfig);
            }
        } catch (ClassNotFoundException e) {
            // This should never happen, because it would mean that there are
            // class files under WEB-INF/classes that can't be loaded through
            // the application class loader (breaks Servlet specification)
            LOG.error("Error searching annotated classes. ", e);
            throw new UnavailableException(e.getMessage()); // NOPMD
        } catch (IllegalAccessException e) {
            LOG.error("Error updating struts configuration. ", e);
            throw new UnavailableException(e.getMessage()); // NOPMD
        }
    }

    /**
     * Receive notification that our owning module is being shut down.
     */
    public void destroy() {
        // Nothing to destroy
    }

    /**
     * Update struts actions configuration.
     *
     * @param clazz
     * @param moduleConfig
     * @throws IllegalAccessException
     *             if the field annotated with StrutsForward is inaccessible
     */
    private static void updateActionConfig(final Class<? extends Action> clazz,
            final ModuleConfig moduleConfig) throws IllegalAccessException {
        for (StrutsAction actionMapping : getAnnotations(clazz)) {
            if (actionMapping == null || !actionMapping.module().equals(moduleConfig.getPrefix())) {
                // action is not annotated or from different module
                continue;
            }
            final ActionConfig actionConfig = ActionConfigBuilderFactory.getActionConfigBuilder(
                    clazz, actionMapping).actionConfig();
            updateFormBeanConfig(actionMapping, actionConfig, moduleConfig);
            for (Field field : StrutsAnnotationsScanner.findFields(clazz)) {
                updateForwardConfig(field, actionConfig);
            }
            moduleConfig.addActionConfig(actionConfig);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Added action mapping " + actionConfig);
            }
        }
    }

    /**
     * @param actionMapping
     *            action annotation
     * @param actionConfig
     *            configuration information of an action element from a Struts module configuration
     *            file
     * @param moduleConfig
     *            module configuration
     */
    private static void updateFormBeanConfig(final StrutsAction actionMapping,
            final ActionConfig actionConfig, final ModuleConfig moduleConfig) {
        FormBeanConfigBuilder formBeanConfigBuilder = new FormBeanConfigBuilder(actionMapping);
        formBeanConfigBuilder.updateActionConfig(actionConfig);
        formBeanConfigBuilder.updateModuleConfig(moduleConfig);
    }

    /**
     * @param field
     *            annotated field (should be static final String)
     * @param actionConfig
     *            configuration information of an action element from a Struts module configuration
     *            file
     * @throws IllegalAccessException
     *             if the field annotated with StrutsForward is inaccessible
     */
    private static void updateForwardConfig(final Field field, final ActionConfig actionConfig)
            throws IllegalAccessException {
        actionConfig.addForwardConfig(new ForwardConfigBuilder(field).build());
    }

    /**
     * @param clazz
     * @return array of StrutsAction annotations
     */
    private static StrutsAction[] getAnnotations(Class<?> clazz) {
        return clazz.isAnnotationPresent(StrutsAction.class) ? new StrutsAction[] { clazz
                .getAnnotation(StrutsAction.class) } : clazz.getAnnotation(StrutsActions.class)
                .value();
    }

}
