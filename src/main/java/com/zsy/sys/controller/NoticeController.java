package com.zsy.sys.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.sys.domain.Notice;
import com.zsy.sys.domain.User;
import com.zsy.sys.service.INoticeService;
import com.zsy.sys.utils.DataGridView;
import com.zsy.sys.utils.ResultObj;
import com.zsy.sys.utils.WebUtils;
import com.zsy.sys.vo.NoticeVo;

import cn.hutool.core.util.ReUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zsy
 * @since 2019-08-13
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private INoticeService noticeService;
	
	/**
	 * 加载公告列表
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping("loadAllNotice")
	public DataGridView loadAllNotice(NoticeVo noticeVo) {
		IPage<Notice> page = new Page<Notice>(noticeVo.getPage(),noticeVo.getLimit());
		QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(noticeVo.getTitle())) {
			queryWrapper.like("title", noticeVo.getTitle());
		}
		if (null!=noticeVo.getStartDate()) {
			queryWrapper.ge("createtime", noticeVo.getStartDate());
		}
		if (null!=noticeVo.getEndDate()) {
			queryWrapper.le("createtime", noticeVo.getEndDate());
		}
		this.noticeService.page(page ,queryWrapper);
		return new DataGridView(page.getTotal(),page.getRecords());
	}
	
	/**
	 * 添加公告
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping("addNotice")
	public ResultObj addNotice(NoticeVo noticeVo) {
		try {
			//添加
			noticeVo.setCreatetime(new Date());
			User user = (User) WebUtils.getHttpSession().getAttribute("user");
			noticeVo.setOpername(user.getName());
			this.noticeService.save(noticeVo);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping("updateNotice")
	public ResultObj updateNotice(NoticeVo noticeVo) {
		try {
			this.noticeService.updateById(noticeVo);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 删除公告
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteNotice")
	public ResultObj deleteNotice(Integer id) {
		try {
			this.noticeService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 批量删除
	 * @param noticeVo
	 * @return
	 */
	@RequestMapping("batchDeleteNotice")
	public ResultObj batchDeleteNotice(NoticeVo noticeVo) {
		try {
			Integer[] idsC = noticeVo.getIds();
			Collection<Serializable> ids = new ArrayList<>();
			for (Integer id : idsC) {
				ids.add(id);
			}
			this.noticeService.removeByIds(ids);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	
	@RequestMapping("loadNoticeById")
	public Notice loadNoticeById(Integer id) {
		Notice notice = this.noticeService.getById(id);
		return notice;
	}
}
