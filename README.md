StrutsAction and StrutsForward annotations for Struts 1.2

- Supports multi-module configuration
- No need to change struts-config.xml (it will stay almost empty no matter how much actions and form beans you create)
- References to form-beans visible in Java code (Ctrl+Shift+G on form bean class in Eclipse will show you all actions which use this form bean)
- Sensible default values (convention over configuration)
- Straightforward migration from XML configuration (attributes from struts-config.xml are mapped on similar annotation fields)
- MoreDescriptiveRequestProcessor
