package com.functionx.model;

import java.util.List;

public class FunctionxService {
	
	private FunctionxDAO_interface dao;
	
//	public FunctionxService() {
//		dao = new FunctionxJDBCDAO();
//	}
	
	public FunctionxService() {
		dao = new FunctionxJNDIDAO();
	}
	
	//新增功能
	public FunctionxVO addFunc(String funcname, String funcifo, java.sql.Date funclmod) {
		
		FunctionxVO functionxVO = new FunctionxVO();
		functionxVO.setFuncname(funcname);
		functionxVO.setFuncifo(funcifo);
		functionxVO.setFunclmod(funclmod);
		dao.insert(functionxVO);
		
		return functionxVO;		
	}
	
	//更新功能資料
	
	public FunctionxVO updateFunc(String funcno, String funcname, String funcifo, java.sql.Date funclmod) {
		
		FunctionxVO functionxVO = new FunctionxVO();
		functionxVO.setFuncno(funcno);
		functionxVO.setFuncname(funcname);
		functionxVO.setFuncifo(funcifo);
		functionxVO.setFunclmod(funclmod);
		dao.update(functionxVO);
		
		return functionxVO;
	}
	
	//刪除
	public void deleteFunc(String funcno) {
		dao.delete(funcno);
	}
	
	//查詢單一
	public FunctionxVO getOneFunc(String funcno) {
		return dao.findByPrimaryKey(funcno);
	}
	
	//查詢全部
	public List<FunctionxVO> getAll(){
		return dao.getAll();
	}
}
