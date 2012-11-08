package by.shade.strutsannotations.plugin;

import static org.junit.Assert.assertEquals;

import org.apache.struts.action.Action;
import org.junit.Test;

import by.shade.strutsannotations.StrutsAction;
import by.shade.strutsannotations.StrutsActions;

/**
 * @author serafimovich
 * @version $Id$
 */
public class StrutsActionsTest {

    @Test
    public void testDefaultAnnotation() {
        Class<EmptyClassAction> action = EmptyClassAction.class;
        StrutsActions sa = action.getAnnotation(StrutsActions.class);
        assertEquals(1, sa.value().length);
        assertAction("/emptyClass", "", action, sa.value()[0]);
    }

    @Test
    public void testTwoSubAnnotations() {
        Class<TestClassAction> action = TestClassAction.class;
        StrutsActions sa = action.getAnnotation(StrutsActions.class);
        assertEquals(2, sa.value().length);
        assertAction("/path1", "method1", action, sa.value()[0]);
        assertAction("/path2", "method2", action, sa.value()[1]);
    }

    private static void assertAction(String expectedPath, String expectedDefMethod,
            Class<? extends Action> clazz, StrutsAction annotation) {
        IActionConfigBuilder builder = new ActionConfigBuilder(clazz, annotation);
        // assertEquals(expectedPath, annotation.path());
        assertEquals("Path", expectedPath, builder.actionConfig().getPath());
        assertEquals("Default method", expectedDefMethod, annotation.defaultMethod());
        // ActionConfigBuilder builder = new ActionConfigBuilder(clazz, annotation);
        // assertEquals("Default path", expected, builder.getDefaultActionPath());
    }

    @StrutsActions
    class EmptyClassAction extends Action {
        // empty
    }

    @StrutsActions({ @StrutsAction(path = "/path1", defaultMethod = "method1"),
            @StrutsAction(path = "/path2", defaultMethod = "method2") })
    class TestClassAction extends Action {
        // empty
    }

}
