package com.scttsc.common.util;

import java.io.*;
import java.net.URL;

public class FileUtil
{
  public static File[] list(String paramString)
  {
    File localFile = new File(paramString);
    File[] arrayOfFile = localFile.listFiles();
    return arrayOfFile;
  }

  public static boolean delete(String paramString)
    throws FileNotFoundException
  {
    File localFile = new File(paramString);
    if (!(localFile.exists()))
      throw new FileNotFoundException(paramString);
    return localFile.delete();
  }

  public static boolean createDir(String paramString)
    throws IOException
  {
    File localFile = new File(paramString);
    if ((localFile.exists()) && (localFile.isDirectory()))
      throw new IOException("同名的目录已经存在");
    return localFile.mkdir();
  }

    public static final int BUFFER_SIZE = 16 * 1024;
    	public static final String GLOBAL_CHARSET = "utf-8";

	public static byte[] readByte(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte[] r = new byte[fis.available()];
			fis.read(r);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readByte(File f) {
		try {
			FileInputStream fis = new FileInputStream(f);
			byte[] r = readByte(fis);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readByte(InputStream is) {
		try {
			byte[] r = new byte[is.available()];
			is.read(r);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean writeByte(String fileName, byte[] b) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(fileName));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean writeByte(File f, byte[] b) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(f));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String readText(File f) {
		return readText(f, GLOBAL_CHARSET);
	}

	public static String readText(File f, String encoding) {
		try {
			InputStream is = new FileInputStream(f);
			String str = readText(is, encoding);
			is.close();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readText(InputStream is, String encoding) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					encoding));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readText(String fileName) {
		return readText(fileName, GLOBAL_CHARSET);
	}

	public static String readText(String fileName, String encoding) {
		try {
			InputStream is = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					encoding));
			StringBuffer sb = new StringBuffer();

			int c = br.read();
			if ((!(encoding.equalsIgnoreCase("utf-8"))) || (c != 65279))
				sb.append((char) c);
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			br.close();
			is.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readURLText(String urlPath) {
		return readURLText(urlPath, GLOBAL_CHARSET);
	}

	public static String readURLText(String urlPath, String encoding) {
		try {
			URL url = new URL(urlPath);
			BufferedReader in = new BufferedReader(new InputStreamReader(url
					.openStream(), encoding));

			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}
			in.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean mkdir(String path) {
		File dir = new File(path);
		if (!(dir.exists())) {
			dir.mkdirs();
		}
		return true;
	}

	/**
	 * 将源文件上传文件至目标路径
	 */
	public static boolean uploadFile(File srcFile, String destPath) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destPath);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				out.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getFileExt(String fileName) {
		if (fileName != null) {
			String fileExt = "";
			fileName = fileName.toLowerCase();
			int index = fileName.lastIndexOf(".");
			fileExt = fileName.substring(index + 1, fileName.length());
			return fileExt;
		} else {
			return "";
		}
	}

	/**
	 * 判断目录是否存在，如果不存在则建立
	 *
	 * @param fileRealPath
	 * @return
	 */
	public static boolean checkFolder(String fileRealPath) {
		boolean tt = false;
		try {

			File f = new File(fileRealPath);
			if (f.exists()) {
				tt = true;
			} else {
				tt = f.mkdirs();
			}
		} catch (Exception ex) {
			System.out.println("[checkFolder] error:" + ex.getMessage());
		}
		return tt;
	}

    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
   public static  boolean  exists(String   fileName)   {
        try   {
            FileReader   f   =   new   FileReader(new   File(fileName));
            return   true;
        }
        catch   (FileNotFoundException   e)   {
            return   false;
        }

    }
}