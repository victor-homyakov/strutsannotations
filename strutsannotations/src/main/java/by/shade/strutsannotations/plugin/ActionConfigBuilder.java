package by.shade.strutsannotations.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;

import by.shade.strutsannotations.StrutsAction;

/**
 * Create ActionConfig for annotated Action class.
 *
 * @author homyakov
 * @version $Id$
 */
public class ActionConfigBuilder extends AbstractActionConfigBuilder<Action> {

    /** Commons logging instance. */
    private static final Log LOG = LogFactory.getLog(ActionConfigBuilder.class);

    /**
     * Create configuration builder for class with single StrutsAction annotation. Retrieve
     * StrutsAction annotation from class.
     *
     * @param clazz
     *            class of Action
     */
    public ActionConfigBuilder(final Class<? extends Action> clazz) {
        super(clazz);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Annotated Action: " + clazz);
        }
    }

    /**
     * Create configuration builder for class with StrutsActions annotation (multiple StrutsAction
     * annotations).
     *
     * @param clazz
     *            class of Action
     * @param annotation
     *            desired StrutsAction annotation
     */
    public ActionConfigBuilder(final Class<? extends Action> clazz, final StrutsAction annotation) {
        super(clazz, annotation);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Annotated Action: " + clazz);
        }
    }

}
