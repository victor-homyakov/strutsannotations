package by.shade.strutsannotations.plugin;

import org.apache.struts.action.Action;
import org.apache.struts.actions.EventDispatchAction;

/**
 * Retrieve builder of ActionConfig for specific Action.
 *
 * @author homyakov
 * @version $Id$
 */
public final class ActionConfigBuilderFactory {

    /**
     * Utility class.
     */
    private ActionConfigBuilderFactory() {
        // empty
    }

    /**
     * @param clazz
     *            class of Action
     * @return ActionConfig builder for this class
     */
    @SuppressWarnings("unchecked")
    public static IActionConfigBuilder getActionConfigBuilder(final Class<? extends Action> clazz) {
        IActionConfigBuilder result;
        if (EventDispatchAction.class.isAssignableFrom(clazz)) {
            result = new EventDispatchActionConfigBuilder(
                    (Class<? extends EventDispatchAction>) clazz);
        } else {
            result = new ActionConfigBuilder(clazz);
        }
        // TODO DispatchActionConfigBuilder
        return result;
    }
}
