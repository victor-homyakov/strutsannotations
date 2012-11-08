package by.shade.strutsannotations.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        Class<EmptyClassAction> c = EmptyClassAction.class;
        StrutsActions sa = c.getAnnotation(StrutsActions.class);
        assertTrue(sa.value().length == 1);
        assertAction("/emptyClass", "", c, sa.value()[0]);
    }

    @Test
    public void testTwoSubAnnotations() {
        Class<TestClassAction> c = TestClassAction.class;
        StrutsActions sa = c.getAnnotation(StrutsActions.class);
        assertTrue(sa.value().length == 2);
        assertAction("/path1", "method1", c, sa.value()[0]);
        assertAction("/path2", "method2", c, sa.value()[1]);
    }

    private static void assertAction(String expectedPath, String expectedDefMethod,
            Class<? extends Action> clazz, StrutsAction annotation) {
        IActionConfigBuilder builder = new ActionConfigBuilder(clazz, annotation);
        // assertEquals(expectedPath, annotation.path());
        assertEquals(expectedPath, builder.actionConfig().getPath());
        assertEquals(expectedDefMethod, annotation.defaultMethod());
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
