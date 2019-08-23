package com.zsy.sys.vo;

import com.zsy.sys.domain.Dept;
import com.zsy.sys.domain.Permission;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class DeptVo extends Dept{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer limit;
}
