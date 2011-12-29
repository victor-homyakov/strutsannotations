package by.shade.strutsannotations.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.RequestActionMapping;
import org.apache.struts.actions.EventDispatchAction;
import org.junit.Test;

import by.shade.strutsannotations.StrutsAction;

/**
 * @author homyakov
 * @version $Id$
 */
public class ActionConfigBuilderTest {

    /**
     * Test method for {@link ActionConfigBuilder#buildEventDispatchActionParameter()} .
     */
    @Test
    public void testActionParameter() {
        String parameter = new ActionConfigBuilder(Disp.class).parameter();
        assertNull("Action should have default null parameter", parameter);

        parameter = new EventDispatchActionConfigBuilder(Disp.class).parameter();
        assertEquals("Public methods only", "visible1,default=visible2,visible3", parameter);
    }

    @Test
    public void testActionPath() {
        assertDefaultActionPath("/disp", Disp.class);
        assertActionPath("disp", Disp.class);

        assertDefaultActionPath("/empty", EmptyAction.class);
        assertActionPath("/a1", EmptyAction.class);

        assertDefaultActionPath("/anotherEmpty", AnotherEmptyAction.class);
        assertActionPath("/anotherEmpty", AnotherEmptyAction.class);

        assertDefaultActionPath("/XXL", XXLAction.class);
        assertActionPath("/XXL", XXLAction.class);
    }

    private static void assertDefaultActionPath(String expected, Class<? extends Action> clazz) {
        ActionConfigBuilder builder = new ActionConfigBuilder(clazz);
        assertEquals("Default path", expected, builder.getDefaultActionPath());
    }

    private static void assertActionPath(String expected, Class<? extends Action> clazz) {
        IActionConfigBuilder builder = new ActionConfigBuilder(clazz);
        assertEquals("Path", expected, builder.actionConfig().getPath());
    }

    /**
     * Test action.
     */
    @StrutsAction(path = "disp", defaultMethod = "visible2")
    class Disp extends EventDispatchAction {
        public ActionForward visible1(final ActionMapping mapping, final ActionForm form,
                final HttpServletRequest request, final HttpServletResponse response) {
            return invisible(mapping, form, request, response);
        }

        public ActionForward visible2(final ActionMapping mapping, final ActionForm form,
                final HttpServletRequest request, final HttpServletResponse response) {
            return null;
        }

        public ActionRedirect visible3(final ActionMapping mapping, final ActionForm form,
                final HttpServletRequest request, final HttpServletResponse response) {
            return null;
        }

        private ActionForward invisible(final ActionMapping mapping, final ActionForm form,
                final HttpServletRequest request, final HttpServletResponse response) {
            return null;
        }

        public ActionForward otherParams(final String string, final ActionForm form,
                final HttpServletRequest request, final HttpServletResponse response) {
            return null;
        }

        public ActionForward otherParams2(final RequestActionMapping mapping,
                final ActionForm form, final HttpServletRequest request,
                final HttpServletResponse response) {
            return null;
        }

        public void otherResult(final ActionMapping mapping, final ActionForm form,
                final HttpServletRequest request, final HttpServletResponse response) {
            // empty
        }

    }

    /**
     * Test action.
     */
    @StrutsAction(path = "/a1")
    class EmptyAction extends Action {
    }

    /**
     * Test action with default config.
     */
    @StrutsAction
    class AnotherEmptyAction extends Action {
    }

    /**
     * Test action with default config.
     */
    @StrutsAction
    class XXLAction extends Action {
    }

}
