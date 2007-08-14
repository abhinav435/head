/**

 * MifosImageTag.java    version: 1.0

 

 * Copyright (c) 2005-2006 Grameen Foundation USA

 * 1029 Vermont Avenue, NW, Suite 400, Washington DC 20005

 * All rights reserved.

 

 * Apache License 
 * Copyright (c) 2005-2006 Grameen Foundation USA 
 * 

 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 *

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the 

 * License. 
 * 
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an explanation of the license 

 * and how it is applied. 

 *

 */

package org.mifos.framework.struts.tags;


import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * Custom tag for input fields of type "img". The tag 
 * selects properties file according to the attribute mentioned in the tag.  
 */

public class MifosImageTag extends TagSupport
{
		
		private static final long serialVersionUID = 1L;
		protected String moduleName = null;
		protected String id = null;
		private String path=null;
		
		public MifosImageTag () {
			super();
			// TODO Auto-generated constructor stub
		}
		
		/** Used to set the id given in the JSP file */
	
		 @Override
		public void setId(String id) 
		 {
		           this.id = id;
		 }
		 
		@Override
		public String getId() 
		{
		        return (this.id);
	    }

		/** Used to set the moduleName given in the JSP file */
		
		public void setModuleName(String name) 
	    {
	    	 this.moduleName = name;
	    }
		
		public String getModuleName() 
		{
		        return (this.moduleName);
	    }

		/** Used to get the corresponding properties fileName using moduleName  */
		
	    public String getPropertiesFileName()
	    {
	    	String moduleName=getModuleName();
	    	if(moduleName.contains("."))
	    		moduleName=moduleName.substring(moduleName.indexOf(".")+1,moduleName.length());
	    	return moduleName+"Images";
	    }
	    
	    /**
		 * Function to render the tag
		 * 
		 * @throws JspException
		 */	   
	    @Override
		public int doStartTag() throws JspException 
		{
	    	JspWriter out = pageContext.getOut();
	    			
	    	ResourceBundle resource = ResourceBundle.getBundle("org.mifos.application."+getModuleName()+".util.resources."+getPropertiesFileName());
			path = resource.getString(getId());
				try {
					out.println(render());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			return(SKIP_BODY);
			
		}
	    
	    public String render(){
			ResourceBundle resource = ResourceBundle.getBundle("org.mifos.application."+getModuleName()+".util.resources."+getPropertiesFileName());
			path = resource.getString(getId());
			XmlBuilder html = new XmlBuilder();
			html.startTag("html");
			html.startTag("body");
			html.singleTag("img", "src",path);
			html.endTag("body");
			html.endTag("html");
			return html.toString();
		}
}	






