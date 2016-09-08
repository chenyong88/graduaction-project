package co.cy.view;

import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class MultiViewResover implements ViewResolver {  
  
    private Map<String, ViewResolver> resolvers; 
    
    private String defaultView;
	
    @Override  
    public View resolveViewName(String viewName, Locale locale) throws Exception {  
        int n = viewName.lastIndexOf("."); 
        String suffix = "";  
        if (n == (-1)) { 
        	suffix = defaultView == null || "".equals(defaultView) ? "ftl" : defaultView;
        } else {  
            suffix = viewName.substring(n + 1);  
            viewName = viewName.substring(0, n);  
        }  
  
        ViewResolver resolver = resolvers.get(suffix);  
  
        if (resolver != null) {  
            return resolver.resolveViewName(viewName, locale);  
        } else {  
            return null;  
        }  
    }  
  
    public Map<String, ViewResolver> getResolvers() {  
        return resolvers;  
    }  
  
    public void setResolvers(Map<String, ViewResolver> resolvers) {  
        this.resolvers = resolvers;  
    }

	public String getDefaultView() {
		return defaultView;
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	} 
    
    
}  