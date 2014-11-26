package com.scttsc.charge.util;

import org.apache.log4j.Logger;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

/**
 * @author xiaolong.hou
 * @version 0.0.1
 * @date 2014.8.29
 * @describe 文件操作, 主要针对日志和统计信息
 */

public class FileUtils {

	private final static Logger LOG = Logger.getLogger(FileUtils.class);

	/**
	 * 向文件中增量写文件
	 * 
	 * @param path
	 * @param value
	 */
	public static void writeToFileByLock(String path, String value) {
		RandomAccessFile rf = null;
		FileChannel fc = null;
		FileLock lock = null;
		ByteBuffer byteBuffer = null;
		try {
			rf = new RandomAccessFile(path, "rw");
			fc = rf.getChannel(); // 获得文件通道
			byteBuffer = ByteBuffer.wrap(StringUtil.append(value, "\n")
					.getBytes());
			while (true) {
				try {
					lock = fc.lock(fc.size() + 1, byteBuffer.limit(), false);// 获取阻塞锁
					break;
				} catch (OverlappingFileLockException e) {
					Thread.sleep(1 * 1000);
					LOG.info("file locked");
				}
			}
			if (null != lock) {
				fc.position(fc.size());
				fc.write(byteBuffer);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (lock != null && lock.isValid()) {
					lock.release();
				}
				if (fc != null && fc.isOpen()) {
					fc.close();
					fc = null;
				}
				if (rf != null) {
					rf.close();
					rf = null;
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
}