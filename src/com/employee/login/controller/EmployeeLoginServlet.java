package com.employee.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp_authority.model.EmpAuthorityService;
import com.emp_authority.model.EmpAuthorityVO;
import com.employee.model.EmployeeService;
import com.employee.model.EmployeeVO;

public class EmployeeLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");

	
		String empacc = req.getParameter("empacc");
		String emppwd = req.getParameter("emppwd");
		
		EmployeeService empSvc = new EmployeeService();
		EmployeeVO employeeVO = empSvc.logIn(empacc, emppwd);
		//還要再做狀態，停用時無法登入
		
		if(employeeVO == null) { //還要再做錯誤提醒
			req.setAttribute("empacc", empacc);
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login/login.jsp");
			failureView.forward(req, res);
		}
		
		String empname=employeeVO.getEmpname();
		String empno = employeeVO.getEmpno();
		System.out.println(empno);
		System.out.println(empname);
		
		if (!allowUser(empacc, emppwd, employeeVO)) { // 【帳號 , 密碼無效時】
			RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login/login.jsp");
			failureView.forward(req, res);
			return;
		} else { // 【帳號 , 密碼有效時, 才做以下工作】
			HttpSession session = req.getSession();
			
			EmpAuthorityService empAuthSvc = new EmpAuthorityService();
			List<EmpAuthorityVO> empAuthorityVO= empAuthSvc.findByEmp(empno);
			System.out.println(empAuthorityVO);		
			session.setAttribute("empacc", empacc); // *工作1: 才在session內做已經登入過的標識
			session.setAttribute("empname", empname);
			session.setAttribute("empauth", empAuthorityVO);
			
			
			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
					res.sendRedirect(location);
					return;
				}
			} catch (Exception ignored) {
			}

			res.sendRedirect(req.getContextPath() + "/front-end/back-endHomePage.jsp"); // *工作3:
																							// (-->如無來源網頁:則重導至login_success.jsp)
		}

	}

	protected boolean allowUser(String empacc, String emppwd,EmployeeVO employeeVO) {
	
		if (employeeVO == null) {
			return false;
		}
		
		String getempacc = employeeVO.getEmpacc();
		String getemppwd = employeeVO.getEmppwd();
		if (getempacc.equals(empacc) && getemppwd.equals(emppwd))
			return true;
		else
			return false;
	}
	


}
