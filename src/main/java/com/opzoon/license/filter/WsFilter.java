package com.opzoon.license.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.opzoon.license.exception.ExceptionCode;

public class WsFilter implements Filter {
	
	private static Logger log = Logger.getLogger(WsFilter.class);

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("<=license=>WsFilter");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		if(!req.getRequestURI().contains("loginUser.do") && req.getSession().getAttribute("loginUserId") == null) {
			resp.setContentType("application/json;charset=utf-8");
			PrintWriter out = resp.getWriter();
			JsonObject result = new JsonObject();
			result.addProperty("errorCode", ExceptionCode.SessionOverdueException.getErrorCode());
			out.write(result.toString());
			out.close();
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
}
