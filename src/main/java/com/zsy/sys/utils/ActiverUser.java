package com.zsy.sys.utils;

import java.util.List;

import com.zsy.sys.domain.User;

import lombok.Data;
@Data
public class ActiverUser {
	private User user;
	private List<String> roles;
	private List<String> permissions;
}
