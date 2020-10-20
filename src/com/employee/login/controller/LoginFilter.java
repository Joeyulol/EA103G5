package com.employee.login.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp_authority.model.EmpAuthorityService;
import com.emp_authority.model.EmpAuthorityVO;
import com.employee.model.EmployeeService;
import com.employee.model.EmployeeVO;


public class LoginFilter implements Filter {
	
	private FilterConfig config;

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	public void destroy() {
		config = null;
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		Object empacc = session.getAttribute("empacc");
		//從帳號找員工編號
		EmployeeService empSvc = new EmployeeService();		
		EmployeeVO empVO =  empSvc.getEmpAcc((String) empacc);
		String empno = empVO.getEmpno();
	
		EmpAuthorityService  empAuth = new EmpAuthorityService();
		List<EmpAuthorityVO> empAuthList = empAuth.findByEmp(empno);
		for(EmpAuthorityVO e: empAuthList) {
			System.out.println(e.getFuncno());
		}
		
		
		if (empacc == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/back-end/login/login.jsp");
			return;
		}		
		chain.doFilter(request, response);
	}



}
