package com.functionx.model;

import java.sql.*;
import java.util.*;


public class FunctionxJDBCDAO implements  FunctionxDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "XDU";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO functionx (funcno, funcname, funcifo, funclmod) VALUES (('FUN' || LPAD(SEQ_EMPLOYEE.NEXTVAL, 4, 0)), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT funcno, funcname, funcifo, to_char(funclmod,'yyyy-mm-dd') funclmod FROM functionx order by funcno";
	private static final String GET_ONE_STMT = "SELECT funcno, funcname, funcifo, to_char(funclmod,'yyyy-mm-dd') funclmod FROM functionx where funcno = ?";
	private static final String DELETE = "DELETE FROM functionx where funcno = ?";
	private static final String UPDATE = "UPDATE functionx set funcname=?, funcifo=?, funclmod=? where funcno=? ";
	
	@Override
	public void insert(FunctionxVO functionxVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, functionxVO.getFuncname());
			pstmt.setString(2, functionxVO.getFuncifo());
			pstmt.setDate(3, functionxVO.getFunclmod());
		
			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
		

	@Override
	public void update(FunctionxVO functionxVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, functionxVO.getFuncname());
			pstmt.setString(2, functionxVO.getFuncifo());
			pstmt.setDate(3, functionxVO.getFunclmod());
			pstmt.setString(4, functionxVO.getFuncno());
		
			pstmt.executeUpdate();
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	

	@Override
	public void delete(String funcno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, funcno);
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}  finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}


	@Override
	public FunctionxVO findByPrimaryKey(String funcno) {

		FunctionxVO functionxVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, funcno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				functionxVO = new FunctionxVO();
				functionxVO.setFuncno(rs.getString("funcno"));
				functionxVO.setFuncname(rs.getString("funcname"));
				functionxVO.setFuncifo(rs.getString("funcifo"));
				functionxVO.setFunclmod(rs.getDate("funclmod"));
			}
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return functionxVO;
	}

	@Override
	public List<FunctionxVO> getAll() {
		
		List<FunctionxVO> list = new ArrayList<FunctionxVO>();
		FunctionxVO functionxVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				functionxVO = new FunctionxVO();
				functionxVO.setFuncno(rs.getString("funcno"));
				functionxVO.setFuncname(rs.getString("funcname"));
				functionxVO.setFuncifo(rs.getString("funcifo"));
				functionxVO.setFunclmod(rs.getDate("funclmod"));
				list.add(functionxVO);
			}			
			con.commit();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			try {
				System.out.println("123");
				con.rollback();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			}
			throw new RuntimeException("A database error occured. " + e.getMessage());
		}  finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
public static void main(String[] args) {
		
	FunctionxJDBCDAO dao = new FunctionxJDBCDAO();
		
		//新增
		FunctionxVO functionxVO1 = new FunctionxVO();
		functionxVO1.setFuncname("講座管理");
		functionxVO1.setFuncifo("管理講座場次及座位");
		functionxVO1.setFunclmod(java.sql.Date.valueOf("2020-09-25"));
		
		dao.insert(functionxVO1);
		System.out.println("新增成功");
//		
//		//修改
//		FunctionxVO functionxVO2 = new FunctionxVO();
//		functionxVO2.setFuncname("講座管理");
//		functionxVO2.setFuncifo("管理講座場次及座位");
//		functionxVO2.setFunclmod(java.sql.Date.valueOf("2020-09-10"));
//		functionxVO2.setFuncno("FUN0031");		
//		dao.update(functionxVO2);
//		System.out.println("修改成功");
//		
//		//刪除
//		dao.delete("FUN0031");
//		System.out.println("刪除成功");
//		
		//查詢單一
//		FunctionxVO functionxVO3 = dao.findByPrimaryKey("FUN0002");
//		System.out.print(functionxVO3.getFuncno()+ ",");
//		System.out.print(functionxVO3.getFuncname()+ ",");
//		System.out.print(functionxVO3.getFuncifo()+ ",");
//		System.out.print(functionxVO3.getFunclmod());
	
		
		//查詢全部
//		List<FunctionxVO> list = dao.getAll();
//		for (FunctionxVO VO : list) {
//			System.out.print(VO.getFuncno()+ ",");
//			System.out.print(VO.getFuncname()+ ",");
//			System.out.print(VO.getFuncifo()+ ",");
//			System.out.print(VO.getFunclmod());
//
//			System.out.println();
//		}
	}
	
}
