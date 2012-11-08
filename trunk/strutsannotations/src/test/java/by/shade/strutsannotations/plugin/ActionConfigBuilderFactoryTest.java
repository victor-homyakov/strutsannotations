package by.shade.strutsannotations.plugin;

import static org.junit.Assert.assertTrue;

import org.apache.struts.action.Action;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.actions.EventDispatchAction;
import org.junit.Test;

/**
 * @author homyakov
 * @version $Id$
 */
public class ActionConfigBuilderFactoryTest {

    @Test
    public void testGetActionConfigBuilder() {
        assertBuilderFor(ExtAction.class, ActionConfigBuilder.class);
        assertBuilderFor(ExtExtAction.class, ActionConfigBuilder.class);
        assertBuilderFor(ExtDispatchAction.class, ActionConfigBuilder.class);
        assertBuilderFor(ExtEventDispatchAction.class, EventDispatchActionConfigBuilder.class);
        assertBuilderFor(ExtExtEventDispatchAction.class, EventDispatchActionConfigBuilder.class);
    }

    private static void assertBuilderFor(Class<? extends Action> actionClass, Class<?> builderClass) {
        IActionConfigBuilder builder = ActionConfigBuilderFactory
                .getActionConfigBuilder(actionClass);
        assertTrue(builderClass.isInstance(builder));
    }

    class ExtAction extends Action {
        // empty
    }

    class ExtExtAction extends ExtAction {
        // empty
    }

    class ExtDispatchAction extends DispatchAction {
        // empty
    }

    class ExtEventDispatchAction extends EventDispatchAction {
        // empty
    }

    class ExtExtEventDispatchAction extends ExtEventDispatchAction {
        // empty
    }

}
