# Introduction #

There is a simple way in multi-module project to set all stuff up and running (including StrutsAction, StrutsForward annotations, StrutsAnnotationsPlugin and MoreDescriptiveRequestProcessor for all modules) without changing all struts-config.xml files.


# Details #

To use annotations in entire project, replace Struts servlet in web.xml with provided one:

```
    <servlet-class>by.shade.strutsannotations.ActionServlet</servlet-class>
    <!-- <servlet-class>org.apache.struts.action.ActionServlet</servlet-class> -->
```

Done. Now you can relax and refactor step-by-step each struts-config when you have free time.