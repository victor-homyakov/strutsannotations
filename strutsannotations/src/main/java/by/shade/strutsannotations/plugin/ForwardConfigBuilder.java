package by.shade.strutsannotations.plugin;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.config.ForwardConfig;

import by.shade.strutsannotations.StrutsForward;

/**
 * Builds ForwardConfig from annotated field.
 *
 * @author homyakov
 * @version $Id$
 */
public class ForwardConfigBuilder {

    /** Commons logging instance. */
    private static final Log LOG = LogFactory.getLog(ForwardConfigBuilder.class);

    private final StrutsForward annotation;
    private final String name;
    private final String path;

    /**
     * Prepares creation of ForwardConfig: retrieves annotation, sets field access permission.
     *
     * @param field
     *            annotated field (should be static final String)
     * @throws IllegalAccessException
     *             if the field annotated with StrutsForward is inaccessible
     * @throws IllegalArgumentException
     *             should never happen
     */
    public ForwardConfigBuilder(final Field field) throws IllegalArgumentException,
            IllegalAccessException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Annotated forward: " + field);
        }
        this.annotation = field.getAnnotation(StrutsForward.class);

        // Read of private field may cause IllegalAccessException
        // if the JVM is started with Security Manager
        field.setAccessible(true);

        name = (String) field.get(null);
        path = StringUtils.isBlank(annotation.path()) ? name : annotation.path();
    }

    /**
     * @return ForwardConfig
     * @throws IllegalAccessException
     *             if the field annotated with StrutsForward is inaccessible
     */
    public ForwardConfig build() throws IllegalAccessException {
        // Use ActionForward to avoid
        // ClassCastException: org.apache.struts.config.ForwardConfig
        // cannot be cast to org.apache.struts.action.ActionForward
        ForwardConfig result = new ActionForward(name, path, annotation.redirect(),
                StringUtils.isBlank(annotation.module()) ? null : annotation.module());
        if (LOG.isDebugEnabled()) {
            LOG.debug(result);
        }
        return result;
    }

}
