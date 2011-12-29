package by.shade.strutsannotations.plugin;

import org.apache.struts.config.ActionConfig;

/**
 * Interface for builders of ActionConfig.
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
     * @return parameter for ActionConfig
     */
    String parameter();

    /**
     * @return path for ActionConfig
     */
    String path();

    /**
     * @return type for ActionConfig
     */
    String type();

}
