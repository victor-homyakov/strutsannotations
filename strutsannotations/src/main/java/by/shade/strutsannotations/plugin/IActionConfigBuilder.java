package by.shade.strutsannotations.plugin;

import org.apache.struts.config.ActionConfig;

/**
 * Interface of ActionConfig builders.
 *
 * @author homyakov
 * @version $Id$
 */
public interface IActionConfigBuilder {

    /**
     * @return ActionConfig
     */
    ActionConfig actionConfig();

    /**
     * @return <code>parameter</code> for ActionConfig
     */
    String parameter();

    /**
     * @return <code>path</code> for ActionConfig
     */
    String path();

    /**
     * @return <code>type</code> for ActionConfig
     */
    String type();

}
