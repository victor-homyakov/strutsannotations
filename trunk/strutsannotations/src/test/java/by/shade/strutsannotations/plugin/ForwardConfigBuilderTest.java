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

    private static final String FORWARD1_NAME = "FORWARD1";
    private static final String FORWARD1_VALUE = "forward1";

    private static final String FORWARD2_NAME = "FORWARD2";
    private static final String FORWARD2_VALUE = "forward2";

    private static final String FORWARD3_NAME = "FORWARD3";
    private static final String FORWARD3_VALUE = "/forward3.jsp";

    @Test
    public void testScanner() {
        Collection<Field> fields = StrutsAnnotationsScanner.findFields(NewAction.class);
        assertEquals(3, fields.size());
        boolean found1 = false, found2 = false, found3 = false;
        for (Field field : fields) {
            found1 = found1 || FORWARD1_NAME.equals(field.getName());
            found2 = found2 || FORWARD2_NAME.equals(field.getName());
            found3 = found3 || FORWARD3_NAME.equals(field.getName());
        }
        assertTrue(found1);
        assertTrue(found2);
        assertTrue(found3);
    }

    @Test
    public void testForward() throws Exception {
        assertForward(FORWARD1_NAME, FORWARD1_VALUE, "/forward1.jsp", "/module");
        assertForward(FORWARD2_NAME, FORWARD2_VALUE, "/forward2.jsp", null);
        assertForward(FORWARD3_NAME, FORWARD3_VALUE, FORWARD3_VALUE, null);
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
        private static final String FORWARD1 = FORWARD1_VALUE;
        @StrutsForward(path = "/forward2.jsp")
        private static final String FORWARD2 = FORWARD2_VALUE;
        @StrutsForward
        private static final String FORWARD3 = FORWARD3_VALUE;
    }

}
