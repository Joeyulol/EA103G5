package com.employee.controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.emp_authority.model.EmpAuthorityService;
import com.emp_authority.model.EmpAuthorityVO;
import com.employee.model.*;

public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//--------------------------新增員工------------------------------------
		if ("insert".equals(action)) {

			List<String> errMsgs = new LinkedList<String>();
			req.setAttribute("errMsgs", errMsgs);

			try {
				String empacc = req.getParameter("empacc");
				String empaccReg = "^[a-zA-Z0-9]{4,10}$";
				if (empacc == null || empacc.trim().length() == 0) {
					req.setAttribute("accerr", "員工帳號:請勿為空白");
//					errMsgs.add("員工帳號:請勿為空白");
				} else if (!empacc.trim().matches(empaccReg)) {
					req.setAttribute("accerr2", "員工帳號:請輸入英文數字，且長度必須在4以上");
//					errMsgs.add("員工帳號:請輸入英文數字，且長度必須在4以上");
				}

				String emppwd = req.getParameter("emppwd");
				String empname = req.getParameter("empname");
				String enameReg = "^[(\u4e00-\u9fa5)]{2,10}$";
				if (empname == null || empname.trim().length() == 0) {
					req.setAttribute("nameerr", "員工姓名:請勿為空白");
//					errMsgs.add("員工姓名:請勿為空白");
				} else if (!empname.trim().matches(enameReg)) {
					req.setAttribute("nameerr2", "員工姓名:請輸入中文，且長度必須在2到10之間");
//					errMsgs.add("員工姓名:請輸入中文，且長度必須在2到10之間");
				}

				Integer empsalary;
				try {
					empsalary = new Integer(req.getParameter("empsalary").trim());
					if (empsalary < 0) {
						req.setAttribute("salaryerr", "員工薪水:請勿為負數");
					}
				} catch (NumberFormatException ne) {
					empsalary = 0;
					req.setAttribute("empsalary", 0);
					req.setAttribute("salaryerr2", "薪水請填數字!");
					errMsgs.add("薪水請填數字!");
				}
				java.sql.Date hiredate = null;

				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
				} catch (IllegalArgumentException ie) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					req.setAttribute("errhiredate", "請輸入日期!");
//					errMsgs.add("請輸入日期!");
				}
				String empemail = req.getParameter("empemail");
				if (empemail == null || empemail.trim().length() == 0) {
					req.setAttribute("errempemail", "員工email:請勿為空白");
				}
				
				String[] functionx = req.getParameterValues("functionx");

				EmployeeVO employeeVO = new EmployeeVO();
				EmpAuthorityService newEmpAuthority = new EmpAuthorityService();
				EmpAuthorityVO empAuthorityVO = new EmpAuthorityVO();
				
				
				employeeVO.setEmpacc(empacc);
				employeeVO.setEmppwd(emppwd);
				employeeVO.setEmpname(empname);
				employeeVO.setEmpsalary(empsalary);
				employeeVO.setHiredate(hiredate);
				employeeVO.setEmpemail(empemail);
				
				
				if (!errMsgs.isEmpty()) {
					req.setAttribute("EmployeeVO", employeeVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/employee/newadd_emp.jsp");
					failureView.forward(req, res);
					return;
				}
//1.random 密碼 >123
//2.email寄出 ran密碼>123
//3.123轉碼成456存在DB
				EmployeeService newEmp = new EmployeeService();
				
				EmployeeVO VO =  newEmp.addEmp(empacc, emppwd, empname, empsalary, hiredate, empemail);
				
				String empno = VO.getEmpno();
				
				if (functionx == null) {
					newEmpAuthority.deleteEmpAuth(empno);
				} else {
					for (int i = 0; i < functionx.length; i++) {
						newEmpAuthority.addEmpAuth(empno, functionx[i]);						
					}
				}
								
								
				RequestDispatcher succesView = req.getRequestDispatcher("/back-end/employee/newallemp.jsp");
				succesView.forward(req, res);
			} catch (Exception e) {
				 e.printStackTrace();
				errMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/employee/newadd_emp.jsp");
				failureView.forward(req, res);
			}

		}

		if ("forupdate".equals(action)) {
			List<String> errMsgs = new LinkedList<String>();
			req.setAttribute("errMsgs", errMsgs);
			try {
				String empno = new String(req.getParameter("empno"));

				EmployeeService empSvc = new EmployeeService();
				EmployeeVO employeeVO = empSvc.getEmp(empno);
				
				EmpAuthorityService empAuthSvc = new EmpAuthorityService();
				List<EmpAuthorityVO> empAuthorityVO = empAuthSvc.findByEmp(empno);
				
				req.setAttribute("employeeVO", employeeVO);	
				req.setAttribute("empAuthorityVO", empAuthorityVO);									
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/employee/newupdate_emp.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errMsgs.add("無法取得要修改的資料: " + e.getMessage());
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/employee/allemplist.jsp");
				successView.forward(req, res);
			}
		}
//-------------------------------------------權限-----------------------------------------------		

		if ("authority_update".equals(action)) {
			List<String> errMsgs = new LinkedList<String>();
			req.setAttribute("errMsgs", errMsgs);
			try {
				String empno = req.getParameter("empno");
				String[] functionx = req.getParameterValues("functionx");
				
				EmpAuthorityService newEmpAuthority = new EmpAuthorityService();
				EmpAuthorityVO empAuthorityVO = new EmpAuthorityVO();
				

				if (functionx == null) {
					newEmpAuthority.deleteEmpAuth(empno);
				} else {
					newEmpAuthority.deleteEmpAuth(empno);
					for (int i = 0; i < functionx.length; i++) {
						newEmpAuthority.addEmpAuth(empno, functionx[i]);
						empAuthorityVO.setEmpno(empno);
						empAuthorityVO.setFuncno(functionx[i]);
					}
				}
				req.setAttribute("empAuthorityVO", empAuthorityVO);
				RequestDispatcher successView = req
						.getRequestDispatcher("/back-end/emp_authority/empauthorityshow.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errMsgs.add("資料修改失敗: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/employee/select_emp.jsp");
				failureView.forward(req, res);
			}

		}

//-------------------------------------------------------------------------------------------	

		if ("empupdate".equals(action)) {
			List<String> errMsgs = new LinkedList<String>();
			req.setAttribute("errMsgs", errMsgs);

			try {
				String empno = req.getParameter("empno");

				String empacc = req.getParameter("empacc");
				String empaccReg = "^[a-zA-Z0-9]{4,10}$";
				if (empacc == null || empacc.trim().length() == 0) {
					errMsgs.add("員工帳號:請勿為空白");
				} else if (!empacc.trim().matches(empaccReg)) {
//					req.setAttribute("accerr2", "員工帳號:請輸入英文數字，且長度必須在4以上");
					errMsgs.add("員工帳號:請輸入英文數字，且長度必須在4以上");
				}
				String emppwd = req.getParameter("emppwd");
				if (emppwd == null || emppwd.trim().length() == 0) {
					errMsgs.add("密碼請勿為空白");
				}

				String empname = req.getParameter("empname");
				String enameReg = "^[(\u4e00-\u9fa5)]{2,10}$";
				if (empname == null || (empname.trim()).length() == 0) {
//					req.setAttribute("nameerr", "員工姓名:請勿為空白");
					errMsgs.add("員工姓名:請勿為空白");
				} else if (!empname.trim().matches(enameReg)) {
//					req.setAttribute("nameerr2", "員工姓名:請輸入中文，且長度必須在2到10之間");
					errMsgs.add("員工姓名:請輸入中文，且長度必須在2到10之間");
				}

				Integer empsalary = null;
				try {
					empsalary = new Integer(req.getParameter("empsalary").trim());
					if (empsalary < 0) {
//						req.setAttribute("salaryerr", "員工薪水:請勿為負數");
						errMsgs.add("員工薪水:請勿為負數");
					}
				} catch (NumberFormatException ne) {
					empsalary = 0;
//					req.setAttribute("salaryerr2", "薪水請填數字!");
					errMsgs.add("薪水請填數字!");
				}

				java.sql.Date hiredate = null;
				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());

				} catch (IllegalArgumentException ie) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					errMsgs.add("請輸入日期!");
				}

				String empemail = req.getParameter("empemail");
				if (empemail == null || (empemail.trim()).length() == 0) {
					errMsgs.add("請填寫email!");
				}

				Integer empdelete = new Integer(req.getParameter("empdelete").trim());
				
				//權限修改
				String[] functionx = req.getParameterValues("functionx");				
				EmpAuthorityService newEmpAuthority = new EmpAuthorityService();
				EmpAuthorityVO empAuthorityVO = new EmpAuthorityVO();
				if (functionx == null) {
					newEmpAuthority.deleteEmpAuth(empno);
				} else {
					newEmpAuthority.deleteEmpAuth(empno);
					for (int i = 0; i < functionx.length; i++) {
						newEmpAuthority.addEmpAuth(empno, functionx[i]);
//						empAuthorityVO.setEmpno(empno);
//						empAuthorityVO.setFuncno(functionx[i]);
					}
				}

				EmployeeVO employeeVO = new EmployeeVO();
				employeeVO.setEmpno(empno);
				employeeVO.setEmpacc(empacc);
				employeeVO.setEmppwd(emppwd);
				employeeVO.setEmpname(empname);
				employeeVO.setEmpsalary(empsalary);
				employeeVO.setHiredate(hiredate);
				employeeVO.setEmpemail(empemail);
				employeeVO.setEmpdelete(empdelete);

				if (!errMsgs.isEmpty()) {
					req.setAttribute("employeeVO", employeeVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/employee/update_emp.jsp");
					failureView.forward(req, res);
					return;
				}

				EmployeeService newEmp = new EmployeeService();
				employeeVO = newEmp.updateEmp(empno, empacc, emppwd, empname, empsalary, hiredate, empemail, empdelete);
				req.setAttribute("employeeVO", employeeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/employee/newallemp.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errMsgs.add("資料修改失敗: " + e.getMessage());
//				e.getStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/employee/newupdate_emp.jsp");
				failureView.forward(req, res);
			}

		}
//		要設計可以直接修改狀態
//		if("empdelete".equals(action)) {
//			Integer empdelete = null ;
//			try {
//				empdelete = new Integer(req.getParameter("empdelete"));
//			} catch (Exception e){
//				
//			}
//		}

		if ("getone_show".equals(action)) {
			List<String> errMsgs = new LinkedList<String>();
			req.setAttribute("errMsgs", errMsgs);
			// 未填寫資料錯誤處理
			try {
				String empno = req.getParameter("empno");
				if (empno == null || (empno.trim()).length() == 0) {
					errMsgs.add("請輸入員工編號");
				}
				if (!errMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/employee/select_emp.jsp");
					failureView.forward(req, res);
					return;
				}

				EmployeeService empSvc = new EmployeeService();
				EmployeeVO employeeVO = empSvc.getEmp(empno);
				// 查無資料錯誤處理
				if (employeeVO == null) {
					errMsgs.add("查無此員工");
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/employee/select_emp.jsp");
					failureView.forward(req, res);
					return;
				}

				req.setAttribute("employeeVO", employeeVO);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/employee/empone.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errMsgs.add("無法取得資料:" + e.getMessage());
			}
		}

	}

}
