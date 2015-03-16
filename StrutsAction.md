# Introduction #

Describes an ActionMapping object that is to be used to process a request for a specific
module-relative URI. Generates `parameter` for `EventDispatchAction` automatically.


# Details #

Replacement for <form-bean ... /> and <action ...>

Unknown end tag for &lt;/action&gt;

 elements of struts-config.xml.

Default values:
  * path: "/" + uncapitalized name of the action class without the last "Action"
> > (e.g. "/getCardsCount" for GetCardsCountAction)
  * name: uncapitalized canonical name of the form bean class ("actionForm" for ActionForm.class)
  * parameter: list of dispatch methods for `EventDispatchAction`, empty otherwise
  * scope: "session"

To use annotations in entire project, replace Struts servlet in web.xml (changes in
struts-config.xml are not required):

```
  <servlet-class>by.shade.strutsannotations.ActionServlet</servlet-class>
  <!-- <servlet-class>org.apache.struts.action.ActionServlet</servlet-class> -->
```

To use annotations in one module, insert in struts-config.xml for this module:

```
<struts-config>
  <plug-in className="by.shade.strutsannotations.plugin.StrutsAnnotationsPlugin" />
</struts-config>
```