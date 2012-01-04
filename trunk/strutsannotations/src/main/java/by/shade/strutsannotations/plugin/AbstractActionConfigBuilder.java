package by.shade.strutsannotations.plugin;

import java.beans.Introspector;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ActionConfig;

import by.shade.strutsannotations.StrutsAction;

/**
 * @author homyakov
 * @version $Id$
 * @param <T>
 */
public abstract class AbstractActionConfigBuilder<T> implements IActionConfigBuilder {

    /**
     * Class of an Action.
     */
    protected final Class<? extends T> clazz;

    /**
     * Annotation.
     */
    protected final StrutsAction annotation;

    /**
     * @param clazz
     *            class of Action
     */
    public AbstractActionConfigBuilder(final Class<? extends T> clazz) {
        this(clazz, clazz.getAnnotation(StrutsAction.class));
    }

    /**
     * @param clazz
     *            class of Action
     * @param annotation
     *            annotation
     */
    public AbstractActionConfigBuilder(final Class<? extends T> clazz, final StrutsAction annotation) {
        this.clazz = clazz;
        this.annotation = annotation;
    }

    /**
     * @return ActionConfig
     */
    public ActionConfig actionConfig() {
        // Struts throws ClassCastException if ActionConfig class is used
        final ActionConfig actionConfig = new ActionMapping();

        actionConfig.setPath(path());
        actionConfig.setType(type());
        // input is optional with default value an empty string
        if (!StringUtils.isBlank(annotation.input())) {
            actionConfig.setInput(annotation.input());
        }
        actionConfig.setParameter(parameter());
        actionConfig.setScope(annotation.scope());
        actionConfig.setValidate(annotation.validate());
        actionConfig.setCancellable(annotation.cancellable());

        return actionConfig;
    }

    /**
     * @return <code>parameter</code> for ActionConfig, if defined in annotation, or null
     */
    public String parameter() {
        final String param = annotation.parameter();
        return StringUtils.isBlank(param) ? null : param;
    }

    /**
     * @return <code>path</code> for ActionConfig
     */
    public String path() {
        String path = annotation.path();
        return StringUtils.isBlank(path) ? getDefaultActionPath() : path;
    }

    /**
     * @return <code>type</code> for ActionConfig (canonical name of Action class)
     */
    public String type() {
        return clazz.getCanonicalName();
    }

    protected String getDefaultActionPath() {
        String name = clazz.getSimpleName();
        return "/"
                + uncapitalize(name.endsWith("Action") ? name.substring(0, name.length() - 6)
                        : name);
    }

    // TODO unify with FormBeanConfigBuilder
    private static String uncapitalize(final String name) {
        // return StringUtils.uncapitalize(name);
        return Introspector.decapitalize(name);
    }

}
