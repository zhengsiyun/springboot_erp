package com.zsy.sys.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.zsy.sys.domain.LogLogin;
import com.zsy.sys.domain.Permission;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class LogLoginVo extends LogLogin{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer limit;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endDate;
}
