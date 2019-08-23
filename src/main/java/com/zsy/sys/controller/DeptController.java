package com.zsy.sys.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.sys.constast.SysConstast;
import com.zsy.sys.domain.Dept;
import com.zsy.sys.service.IDeptService;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.utils.TreeNode;
import com.zsy.sys.vo.DeptVo;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-10
 */
@RestController
@RequestMapping("dept")
public class DeptController {
	@Autowired
	private IDeptService deptService;
	
	/**
	 * 加载部门左边树的json
	 * @param deptVo
	 * @return
	 */
	@RequestMapping("loadDeptManagerLeftTreeJson")
	public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo) {
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("available", SysConstast.AVAILABLE_TRUE);
		List<Dept> list = deptService.list(queryWrapper);
		List<TreeNode> nodes = new ArrayList<>();
		for (Dept d : list) {
			Boolean spread=d.getOpen()==SysConstast.SPREAD_TRUE?true:false;
			nodes.add(new TreeNode(d.getId(), d.getPid(), d.getTitle(), spread));
		}
		return new DataGridView(nodes);
	}
	
	/**
	 * 加载部门管理右边的数据表格
	 */
	@RequestMapping("loadAllDept")
	public DataGridView loadAllDept(DeptVo deptVo) {
		IPage<Dept> page=new Page<>(deptVo.getPage(),deptVo.getLimit());
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		//判断传入的参数是否为null或者""
		if (StringUtils.isNoneBlank(deptVo.getTitle())) {
			queryWrapper.like("title", deptVo.getTitle());
		}
		if (StringUtils.isNoneBlank(deptVo.getAddress())) {
			queryWrapper.like("address", deptVo.getAddress());
		}
		if (StringUtils.isNoneBlank(deptVo.getRemark())) {
			queryWrapper.like("remark", deptVo.getRemark());
		}
		if (deptVo.getId()!=null) {
			queryWrapper.eq("id", deptVo.getId()).or().eq("pid", deptVo.getId());
		}
		deptService.page(page,queryWrapper);
		return new DataGridView(page.getTotal(),page.getRecords());
	}
	
	/**
	 * 查询最大的排序码
	 */
	@RequestMapping("loadDeptMaxOrderNumber")
	public Map<String, Object> loadDeptMaxOrderNumber(){
		Integer maxOrderNum = this.deptService.queryDeptMaxOrderNumber();
		Map<String, Object> map = new HashMap<>();
		map.put("value", maxOrderNum+1);
		return map;
		
	}
	
	/**
	 *  添加部门
	 * @param deptVo
	 * @return
	 */
	@RequestMapping("addDept")
	public ResultObj addDept(DeptVo deptVo) {
		try {
			deptVo.setCreatetime(new Date());
			this.deptService.save(deptVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	
	/**
	 *  修改部门
	 * @param deptVo
	 * @return
	 */
	@RequestMapping("updateDept")
	public ResultObj updateDept(DeptVo deptVo) {
		try {
			this.deptService.updateById(deptVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 根据部门id查询当前id下面有没有字节点
	 * @param id
	 * @return
	 */
	@RequestMapping("checkDeptHasChildren")
	public ResultObj checkDeptHasChildren(Integer id) {
		QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("pid", id);
		int count = deptService.count(queryWrapper );
		if (count>0) {
			return ResultObj.STATUS_TRUE;
		}else {
			return ResultObj.STATUS_FALSE;
		}
	}
	
	@RequestMapping("deleteDept")
	public ResultObj deleteDept(Integer id) {
		try {
			deptService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
		
	}
}

