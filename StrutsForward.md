# Introduction #

Describes an ActionForward that is to be made available to an Action as a return value. An
ActionForward is referenced by a logical name and encapsulates a URI. Instead of deprecated
contextRelative="true" use module="/some\_module".


# Details #

Replacement for 

&lt;action&gt;

<forward ... />

&lt;/action&gt;

 elements of struts-config.xml.

Should be used on static final String fields.

Default values:
  * path: value of the annotated String
  * module: current module
  * redirect: false