package by.shade.strutsannotations.plugin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.struts.action.Action;
import org.apache.struts.action.DynaActionForm;
import org.junit.Test;

import by.shade.strutsannotations.StrutsAction;

/**
 * @author homyakov
 * @version $Id$
 */
public class FormBeanConfigBuilderTest {

    @Test
    public void testGetDefaultFBC() {
        final FormBeanConfigBuilder fbcb = new FormBeanConfigBuilder(DefaultFBConfig.class);
        assertFormClass(fbcb, null); // default
        assertFormName(fbcb, null); // default
    }

    @Test
    public void testGetNameFBC() {
        final FormBeanConfigBuilder fbcb = new FormBeanConfigBuilder(NameFBConfig.class);
        assertFormClass(fbcb, null); // default
        assertFormName(fbcb, "someForm1");
    }

    @Test
    public void testGetClassFBC() {
        final FormBeanConfigBuilder fbcb = new FormBeanConfigBuilder(ClassFBConfig.class);
        assertFormClass(fbcb, DynaActionForm.class);
        assertFormName(fbcb, "dynaActionForm"); // auto-generated decapitalized name
    }

    @Test
    public void testGetSpecialClassFBC() {
        final FormBeanConfigBuilder fbcb = new FormBeanConfigBuilder(SpecialClassFBConfig.class);
        assertFormClass(fbcb, XXActionForm.class);
        assertFormName(fbcb, "XXActionForm"); // auto-generated decapitalized name
    }

    @Test
    public void testGetNameAndClassFBC() {
        final FormBeanConfigBuilder fbcb = new FormBeanConfigBuilder(NameAndClassFBConfig.class);
        assertFormClass(fbcb, DynaActionForm.class);
        assertFormName(fbcb, "someForm2");
    }

    private static void assertFormClass(FormBeanConfigBuilder fbcb, Class<?> klass) {
        if (klass == null) {
            assertNull("Form class should be null", fbcb.getFormClass());
        } else {
            assertEquals("Form class should be " + klass.getSimpleName(), klass,
                    fbcb.getFormClass());
        }
    }

    private static void assertFormName(FormBeanConfigBuilder fbcb, String name) {
        assertEquals("Form name should be " + name, name, fbcb.getFormName());
    }

    /**
     * Default name and form class.
     */
    @StrutsAction(path = "/a1")
    class DefaultFBConfig extends Action { // NOPMD
    }

    /**
     * Default form class.
     */
    @StrutsAction(path = "/a2", name = "someForm1")
    class NameFBConfig extends Action { // NOPMD
    }

    /**
     * Auto-generated name.
     */
    @StrutsAction(path = "/a3", form = DynaActionForm.class)
    class ClassFBConfig extends Action { // NOPMD
    }

    /**
     * Auto-generated name - special case.
     */
    @StrutsAction(path = "/a4", form = XXActionForm.class)
    class SpecialClassFBConfig extends Action { // NOPMD
    }

    /**
     * Defined name and form class.
     */
    @StrutsAction(path = "/a5", form = DynaActionForm.class, name = "someForm2")
    class NameAndClassFBConfig extends Action { // NOPMD
    }

    /**
     * Introspector.decapitalize("XXActionForm") == "XXActionForm".
     */
    class XXActionForm extends DynaActionForm {
        private static final long serialVersionUID = -9217762729979429553L;
    }

}
