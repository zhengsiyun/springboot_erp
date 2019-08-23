package com.zsy.bus.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.zsy.bus.domain.Customer;
import com.zsy.bus.domain.Provider;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class ProviderVo extends Provider{
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
