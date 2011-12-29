package by.shade.strutsannotations.plugin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;

/**
 * Create ActionConfig for Action class and its annotation.
 *
 * @author homyakov
 * @version $Id$
 */
public class ActionConfigBuilder extends AbstractActionConfigBuilder<Action> implements
        IActionConfigBuilder {

    /** Commons logging instance. */
    private static final Log LOG = LogFactory.getLog(ActionConfigBuilder.class);

    /**
     * Retrieve annotation.
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

}
