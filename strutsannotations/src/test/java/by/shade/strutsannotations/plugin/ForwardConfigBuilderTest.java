package by.shade.strutsannotations.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.config.ForwardConfig;
import org.junit.Test;

import by.shade.strutsannotations.StrutsForward;
import by.shade.strutsannotations.scanner.StrutsAnnotationsScanner;

/**
 * @author homyakov
 * @version $Id$
 */
public class ForwardConfigBuilderTest {

    @Test
    public void testScanner() {
        Collection<Field> fields = StrutsAnnotationsScanner.findFields(NewAction.class);
        assertEquals(3, fields.size());
        boolean f1 = false, f2 = false, f3 = false;
        for (Field field : fields) {
            f1 = f1 || "FORWARD1".equals(field.getName());
            f2 = f2 || "FORWARD2".equals(field.getName());
            f3 = f3 || "FORWARD3".equals(field.getName());
        }
        assertTrue(f1);
        assertTrue(f2);
        assertTrue(f3);
    }

    @Test
    public void testForward() throws Exception {
        assertForward("FORWARD1", "forward1", "/forward1.jsp", "/module");
        assertForward("FORWARD2", "forward2", "/forward2.jsp", null);
        assertForward("FORWARD3", "/forward3.jsp", "/forward3.jsp", null);
    }

    private static void assertForward(String fieldName, String name, String path, String module)
            throws Exception {
        Field field = NewAction.class.getDeclaredField(fieldName);
        ForwardConfig config = new ForwardConfigBuilder(field).build();
        assertEquals("Name", name, config.getName());
        assertEquals("Path", path, config.getPath());
        assertEquals("Module", module, config.getModule());
    }

    class NewAction extends Action {
        @StrutsForward(module = "/module", path = "/forward1.jsp")
        private static final String FORWARD1 = "forward1";
        @StrutsForward(path = "/forward2.jsp")
        private static final String FORWARD2 = "forward2";
        @StrutsForward
        private static final String FORWARD3 = "/forward3.jsp";
    }

}
