package by.shade.strutsannotations.plugin;

import org.apache.struts.action.Action;
import org.apache.struts.actions.EventDispatchAction;

import by.shade.strutsannotations.StrutsAction;

/**
 * Retrieve builder of ActionConfig for specific Action class.
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
    public static IActionConfigBuilder getActionConfigBuilder(final Class<? extends Action> clazz) {
        return getActionConfigBuilder(clazz, clazz.getAnnotation(StrutsAction.class));
    }

    /**
     * @param clazz
     *            class of Action
     * @param annotation
     *            desired StrutsAction annotation
     * @return ActionConfig builder for this class
     */
    @SuppressWarnings("unchecked")
    public static IActionConfigBuilder getActionConfigBuilder(final Class<? extends Action> clazz,
            final StrutsAction annotation) {
        IActionConfigBuilder result;
        if (EventDispatchAction.class.isAssignableFrom(clazz)) {
            result = new EventDispatchActionConfigBuilder(
                    (Class<? extends EventDispatchAction>) clazz, annotation);
        } else {
            result = new ActionConfigBuilder(clazz, annotation);
        }
        // TODO DispatchActionConfigBuilder
        return result;
    }

}
