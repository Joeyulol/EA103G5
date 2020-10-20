package com.functionx.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.emp_authority.model.EmpAuthorityJNDIDAO;
import com.emp_authority.model.EmpAuthorityVO;

public class FunctionxJNDIDAO implements FunctionxDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/xduDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, functionxVO.getFuncname());
			pstmt.setString(2, functionxVO.getFuncifo());
			pstmt.setDate(3, functionxVO.getFunclmod());

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, functionxVO.getFuncname());
			pstmt.setString(2, functionxVO.getFuncifo());
			pstmt.setDate(3, functionxVO.getFunclmod());
			pstmt.setString(4, functionxVO.getFuncno());

			pstmt.executeUpdate();
			con.commit();
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
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, funcno);

			pstmt.executeUpdate();
			con.commit();
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
	public FunctionxVO findByPrimaryKey(String funcno) {

		FunctionxVO functionxVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
		} catch (SQLException e) {
			try {
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
		return list;
	}

	@Override
	public void insertWithFunc(FunctionxVO functionxVO, List<EmpAuthorityVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String funcno = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			
			String cols[] = {"funcno"};
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, functionxVO.getFuncname());
			pstmt.setString(2, functionxVO.getFuncifo());
			pstmt.setDate(3, functionxVO.getFunclmod());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				funcno = rs.getString(1);
				System.out.println("自增主鍵" + funcno);
			}
			
			EmpAuthorityJNDIDAO  authdao = new EmpAuthorityJNDIDAO();
			System.out.println("進入權力DAO");
			for(EmpAuthorityVO empAuthorityVO : list) {
				System.out.println("跑到");
				empAuthorityVO.setFuncno(funcno);
				authdao.insert2(empAuthorityVO, con);
			}
			con.commit();
			System.out.println("新增完成");
			
		} catch (SQLException e) {
			try {
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
}
