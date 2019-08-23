package com.zsy.sys.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {
	/**
	 * 把简单集合转换成有层级关系的集合
	 * @param nodes 
	 * @param i
	 * @return
	 */
	public static List<TreeNode> builder(List<TreeNode> nodes, int i) {
		List<TreeNode> treeNodes = new ArrayList<>();
		for (TreeNode n1 : nodes) {
			if (n1.getPid()==i) {
					treeNodes.add(n1);
			}
			for (TreeNode n2 : nodes) {
				if (n2.getPid()==n1.getId()) {
					n1.getChildren().add(n2);
				}
			}
		}
		return treeNodes;
	}
	
}
