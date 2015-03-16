# Introduction #

Some examples.


# Details #

## Without annotations ##

**web.xml**
```
  <servlet>
    <servlet-name>action</servlet-name>
    <servlet-class>org.apache.struts.action.ActionServlet</servlet-class>
    <init-param>
      <param-name>config</param-name>
      <param-value>/WEB-INF/struts-config.xml</param-value>
    </init-param>
    <!-- modules -->
    <init-param>
      <param-name>config/route_chiefrouteractions</param-name>
      <param-value>/WEB-INF/route_chiefrouteractions/struts-config.xml</param-value>
    </init-param>
    <!-- other modules -->
    <load-on-startup>2</load-on-startup>
  </servlet>
```

**struts-config.xml**
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts-config PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 1.2//EN"
                               "http://struts.apache.org/dtds/struts-config_1_2.dtd">
<struts-config>
  <form-beans>
    <form-bean name="processForm" type="web.route.chiefrouteractions.action.ChiefRouterActionsForm" />
  </form-beans>
  <action-mappings>
    <action path="/process" name="processForm" parameter="default=entry,selectCard"
      type="web.route.chiefrouteractions.action.ChiefRouterActionsAction">
      <forward name="cards" path="/chiefrouteractions.jsp" />
      <forward name="selectedCard" path="/selectedCard.jsp" />
    </action>
  </action-mappings>
  <controller pagePattern="$P" />
</struts-config>
```

## With annotations ##

**web.xml**
```
  <servlet>
    <servlet-name>action</servlet-name>
    <servlet-class>by.shade.struts.action.ActionServlet</servlet-class>
    <init-param>
      <param-name>config</param-name>
      <param-value>/WEB-INF/struts-config.xml</param-value>
    </init-param>
    <!-- modules -->
    <init-param>
      <param-name>config/route_chiefrouteractions</param-name>
      <param-value>/WEB-INF/route_chiefrouteractions/struts-config.xml</param-value>
    </init-param>
    <!-- other modules -->
    <load-on-startup>2</load-on-startup>
  </servlet>
```

**struts-config.xml**
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts-config PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 1.2//EN"
                               "http://struts.apache.org/dtds/struts-config_1_2.dtd">
<struts-config>
  <controller pagePattern="$P" />
</struts-config>
```

**`web.route.chiefrouteractions.action.ChiefRouterActionsForm`**
```
package web.route.chiefrouteractions.action;

// imports

// was <form-bean name="processForm" type="web.route.chiefrouteractions.action.ChiefRouterActionsForm" />
// and <action path="/process" name="processForm" parameter="default=entry,selectCard"
//     type="web.route.chiefrouteractions.action.ChiefRouterActionsAction">
@StrutsAction(module = "/route_chiefrouteractions", path="/process", defaultMethod = "entry",
  name="processForm", form=ChiefRouterActionsForm.class)
public class ChiefRouterActionsAction extends EventDispatchAction {

    // was <forward name="cards" path="/chiefrouteractions.jsp" />
    @StrutsForward
    private static final String CARDS = "/chiefrouteractions.jsp";

    // was <forward name="selectedCard" path="/selectedCard.jsp" />
    @StrutsForward(path = "/cardsList.jsp")
    private static final String SELECTED_CARD = "/selectedCard.jsp";

    public ActionForward entry(...) {
        ...
        return mapping.findForward(CARDS);
    }

    public ActionForward selectCard(...) {
        ...
        return mapping.findForward(SELECTED_CARD);
    }

}
```