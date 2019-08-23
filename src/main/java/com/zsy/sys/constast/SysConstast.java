package com.zsy.sys.constast;

public interface SysConstast {

	String USER_LOGIN_ERROR_MSG = "用户名或密码不正确";
	//查询是否可用
	Integer AVAILABLE_TRUE=1;
	Integer AVAILABLE_FALSE=0;
	
	//用户类型
	Integer USER_TYPE_SUPER=0;
	Integer USER_TYPE_NORMAL=1;
	
	//是否展开
	Integer SPREAD_TRUE=1;
	Integer SPREAD_FALSE=0;	
	
	/**
	 * 操作状态
	 */
	String ADD_SUCCESS="添加成功";
	String ADD_ERROR="添加失败";
	
	String UPDATE_SUCCESS="更新成功";
	String UPDATE_ERROR="更新失败";
	
	String DELETE_SUCCESS="删除成功";
	String DELETE_ERROR="删除失败";
	
	String RESET_SUCCESS="重置成功";
	String RESET_ERROR="重置失败";
	
	String DISPATCH_SUCCESS="分配成功";
	String DISPATCH_ERROR="分配失败";
	
	Integer CODE_SUCCESS=0; //操作成功
	Integer CODE_ERROR=-1;//失败
	
	
	//公用常量
	Integer CODE_ZERO = 0;
	Integer CODE_ONE = 1;
	Integer CODE_TWO = 2;
	Integer CODE_THREE = 3;
	
	//默认密码配置
	String USER_DEFAULT_PWD = "123456";
	
	//临时文件标记
	String FILE_UPLOAD_TEMP = "_temp";
	Object DEFAULT_CAR_IMG = "images/defaultcarimage.jpg";
	//汽车出租前后缀
	String CAR_ORDER_CZ = "CZ";
	String CAR_ORDER_JC = "JC";
	//汽车归还状态
	Integer RENT_BACK_TRUE = 1;
	Integer RENT_BACK_FALSE = 0;
	//汽车出租状态
	Integer RENT_CAR_TRUE = 1;
	Integer RENT_CAR_FALSE = 0;
	
	String PERMISSION_TYPE_MENU = "menu";
	String PERMISSION_TYPE_PERMISSION = "permission";
	
	/**
	 * 默认图片地址
	 */
	String DEFAULT_USER_IMG = "images/defaultuserimage.jpg";
	String DEFAULT_GOODS_IMG = "images/defaultgoodsimg.png";
	
}
