package by.shade.strutsannotations.plugin;

import java.beans.Introspector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;

import by.shade.strutsannotations.StrutsAction;

/**
 * Builds FormBeanConfig from annotated Action class.
 *
 * @author homyakov
 * @version $Id$
 */
public class FormBeanConfigBuilder {

    /** Default value for StrutsAction.form(). */
    private static final Class<? extends ActionForm> NO_FORM = ActionForm.class;

    /** Commons logging instance. */
    private static final Log LOG = LogFactory.getLog(FormBeanConfigBuilder.class);

    private final String formName;

    private final Class<? extends ActionForm> formClass;

    public FormBeanConfigBuilder(final Class<? extends Action> clazz) {
        this(clazz.getAnnotation(StrutsAction.class));
    }

    /**
     * @param annotation
     *            annotation of an action, with form bean configuration (<code>name</code> and <code>form</code>)
     */
    public FormBeanConfigBuilder(final StrutsAction annotation) {
        Class<? extends ActionForm> form = annotation.form();
        String name = StringUtils.isBlank(annotation.name()) ? null : annotation.name();

        formClass = NO_FORM.equals(form) ? null : form;

        // create default name if no name specified
        formName = formClass == null ? name
                : name == null ? uncapitalize(formClass.getSimpleName()) : name;
    }

    /**
     * Builds configuration of form bean. Field formName should not be empty or null. Field
     * formClass should not be null.
     *
     * @return FormBeanConfig
     */
    public FormBeanConfig build() {
        final FormBeanConfig formBeanConfig = new FormBeanConfig();
        formBeanConfig.setName(formName);
        formBeanConfig.setType(formClass.getCanonicalName());
        if (LOG.isDebugEnabled()) {
            LOG.debug(formBeanConfig);
        }
        return formBeanConfig;
    }

    public Class<? extends ActionForm> getFormClass() {
        return formClass;
    }

    public String getFormName() {
        return formName;
    }

    /**
     * Updates ActionConfig. Sets the name of form bean, if it is not empty
     *
     * @param actionConfig
     *            configuration of action
     */
    public void updateActionConfig(final ActionConfig actionConfig) {
        if (formName != null) {
            actionConfig.setName(formName);
        }
    }

    /**
     * Updates ModuleConfig. Adds the configuration of form bean, if it is not already present
     * (many actions may refer to single form bean) and can be created.
     *
     * @param moduleConfig
     *            configuration of current module
     */
    public void updateModuleConfig(final ModuleConfig moduleConfig) {
        if (formName != null && formClass != null
                && moduleConfig.findFormBeanConfig(formName) == null) {
            moduleConfig.addFormBeanConfig(build());
        }
    }

    // TODO unify with AbstractActionConfigBuilder
    private static String uncapitalize(final String name) {
        // return StringUtils.uncapitalize(name);
        return Introspector.decapitalize(name);
    }

}
