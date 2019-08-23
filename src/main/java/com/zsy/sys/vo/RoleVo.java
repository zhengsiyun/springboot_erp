package com.zsy.sys.vo;

import java.util.List;

import com.zsy.sys.domain.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleVo extends Role{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer limit;
	private List<Integer> ids;
}
