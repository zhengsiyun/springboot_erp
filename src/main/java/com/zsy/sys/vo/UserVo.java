package com.zsy.sys.vo;

import com.zsy.sys.domain.User;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserVo extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer limit;
	private Integer[] ids;
}	
