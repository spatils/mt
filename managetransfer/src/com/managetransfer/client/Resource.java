/**
 * 
 */
package com.managetransfer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

 
public interface Resource extends ClientBundle {
	public static final Resource INSTANCE  = GWT.create(Resource.class);
	@Source("com/managetransfer/resources/img/arrow.png")
	ImageResource arrow();
	@Source("com/managetransfer/resources/img/leftarrow_g.png")
	ImageResource leftarrow();
	@Source("com/managetransfer/resources/img/rightarrow_g.png")
	ImageResource rightarrow();
	
}
