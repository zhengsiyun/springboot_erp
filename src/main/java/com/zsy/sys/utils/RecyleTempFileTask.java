package com.zsy.sys.utils;

import java.io.File;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zsy.sys.constast.SysConstast;

@Component
@EnableScheduling  //开启定时任务
public class RecyleTempFileTask {
	
	/**
	 * 每天晚上12点执行
	 */
	@Scheduled(cron="0 0 0 * * ? ")
	public void recyleTempFile() {
		File file=new File(AppFileUtils.PATH);
		deleteFile(file);
	}
	
	/**
	 * 删除图片
	 * @param file
	 */
	public void deleteFile(File file) {
		if(null!=file) {
			File[] listFiles = file.listFiles();
			if(null!=listFiles&&listFiles.length>0) {
				for (File f : listFiles) {
					if(f.isFile()) {
						if(f.getName().endsWith(SysConstast.FILE_UPLOAD_TEMP)) {
							f.delete();
						}
					}else {
						//如果是文件夹，再递归删除
						deleteFile(f);
					}
				}
			}
		}
	}

}
