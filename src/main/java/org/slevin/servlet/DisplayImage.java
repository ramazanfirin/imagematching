package org.slevin.servlet;


import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slevin.prime.faces.bean.LtuImageMB;
 
public class DisplayImage extends HttpServlet {
    private static final long serialVersionUID = 4593558495041379082L;
    
    @ManagedProperty(value="#{ltuImageMB}")
    private LtuImageMB ltuImageMB;
 
    @Override
    public void init(ServletConfig config) throws ServletException {
    	
//    	ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
//    	
//    	LtuImageMB sd= (LtuImageMB)ac.getBean("ltuImageMB");
//    	
    	System.out.println(ltuImageMB.getLastSearchFileName());
    };
    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Statement stmt = null;
        ResultSet rs;
        InputStream sImage;
        try {
 
         System.out.println("bitti");  
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public LtuImageMB getLtuImageMB() {
		return ltuImageMB;
	}

	public void setLtuImageMB(LtuImageMB ltuImageMB) {
		this.ltuImageMB = ltuImageMB;
	}
}