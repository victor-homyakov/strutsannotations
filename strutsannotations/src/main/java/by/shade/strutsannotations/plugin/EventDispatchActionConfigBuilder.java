package by.shade.strutsannotations.plugin;

import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.actions.EventDispatchAction;

import by.shade.strutsannotations.scanner.methods.EventDispatchMethodFilter;
import by.shade.strutsannotations.scanner.methods.MethodScanner;

/**
 * Builds ActionConfig for annotated EventDispatchAction.
 *
 * @author homyakov
 * @version $Id$
 */
public class EventDispatchActionConfigBuilder extends
        AbstractActionConfigBuilder<EventDispatchAction> implements IActionConfigBuilder {

    /** Commons logging instance. */
    private static final Log LOG = LogFactory.getLog(EventDispatchActionConfigBuilder.class);

    /**
     * Retrieves annotation.
     *
     * @param clazz
     *            class of EventDispatchAction
     */
    public EventDispatchActionConfigBuilder(final Class<? extends EventDispatchAction> clazz) {
        super(clazz);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Annotated EventDispatchAction: " + clazz);
        }
    }

    /**
     * Returns <code>parameter</code>, if defined in annotation. If <code>parameter</code> is not defined in annotation,
     * returns list of all possible methods. Default method is taken from annotation's
     * <code>defaultMethod</code>.
     *
     * @return <code>parameter</code> for EventDispatchAction
     */
    @Override
    public String parameter() {
        // final StrutsAction annotation = getAnnotation();
        final String param = annotation.parameter();
        if (!StringUtils.isBlank(param)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Explicit EventDispatchAction parameter: " + param);
            }
            return param; // NOPMD
        }

        final Collection<Method> methods = new MethodScanner(clazz)
                .list(new EventDispatchMethodFilter());
        // String defaultMethod = annotation == null ? null : annotation.defaultMethod();
        final String defaultMethod = annotation.defaultMethod();
        // Identify the dispatch methods
        final StringBuilder parameter = new StringBuilder();

        for (Method method : methods) {
            if (parameter.length() > 0) {
                parameter.append(',');
            }
            // if (!StringUtils.isBlank(defaultMethod) && defaultMethod.equals(method.getName())) {
            if (method.getName().equals(defaultMethod)) {
                parameter.append("default=");
            }
            parameter.append(method.getName());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Generated EventDispatchAction parameter: " + parameter);
        }
        return parameter.toString();
    }

}
