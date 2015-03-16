StrutsAction and StrutsForward annotations for Struts 1.2
  1. Supports multi-module configuration
  1. No need to change struts-config.xml (it will stay almost empty no matter how much actions and form beans you create)
  1. References to form-beans visible in Java code (Ctrl+Shift+G on form bean class in Eclipse will show you all actions which use this form bean)
  1. Sensible default values (convention over configuration)
  1. Straightforward migration from XML configuration (attributes from struts-config.xml are mapped on similar annotation fields)
  1. MoreDescriptiveRequestProcessor