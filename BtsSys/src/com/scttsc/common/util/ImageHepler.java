package com.scttsc.common.util;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
   
/**   
 * ͼƬ�����࣬���ͼƬ�Ľ�ȡ   
 *    
 * @author inc062977   
 *    
 */   
public class ImageHepler {    
	private static Logger log4j = Logger.getLogger(ImageHepler.class);
    /**   
     * ʵ��ͼ��ĵȱ����   
     * @param source   
     * @param targetW   
     * @param targetH   
     * @return   
     */   
    private static BufferedImage resize(BufferedImage source, int targetW,    
            int targetH) {    
        // targetW��targetH�ֱ��ʾĿ�곤�Ϳ�    
        int type = source.getType();    
        BufferedImage target = null;    
        double sx = (double) targetW / source.getWidth();    
        double sy = (double) targetH / source.getHeight();    
        // ������ʵ����targetW��targetH��Χ��ʵ�ֵȱ���š������Ҫ�ȱ����    
        // �������if else���ע�ͼ���    
        if (sx < sy) {    
            sx = sy;    
            targetW = (int) (sx * source.getWidth());    
        } else {    
            sy = sx;    
            targetH = (int) (sy * source.getHeight());    
        }    
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade    
            ColorModel cm = source.getColorModel();    
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW,    
                    targetH);    
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();    
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);    
        } else   
            target = new BufferedImage(targetW, targetH, type);    
        Graphics2D g = target.createGraphics();    
        // smoother than exlax:    
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,    
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);    
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));    
        g.dispose();    
        return target;    
    }    
   
    /**   
     * ʵ��ͼ��ĵȱ���ź���ź�Ľ�ȡ   
     * @param inFilePath Ҫ��ȡ�ļ���·��   
     * @param outFilePath ��ȡ������·��   
     * @param width Ҫ��ȡ���   
     * @param hight Ҫ��ȡ�ĸ߶�   
     * @param proportion   
     * @throws Exception   
     */   
        
    public static void saveImageAsJpg(String inFilePath, String outFilePath,    
            int width, int hight)throws Exception {    
         File file = new File(inFilePath);    
         InputStream in = new FileInputStream(file);    
         File saveFile = new File(outFilePath);    
   
        BufferedImage srcImage = ImageIO.read(in);    
        if (width > 0 || hight > 0) {    
            // ԭͼ�Ĵ�С    
            int sw = srcImage.getWidth();    
            int sh = srcImage.getHeight();    
            // ���ԭͼ��Ĵ�СС��Ҫ��ŵ�ͼ���С��ֱ�ӽ�Ҫ��ŵ�ͼ���ƹ�ȥ    
            if (sw > width && sh > hight) {    
                srcImage = resize(srcImage, width, hight);    
            } else {    
                String fileName = saveFile.getName();    
                String formatName = fileName.substring(fileName    
                        .lastIndexOf('.') + 1);    
                ImageIO.write(srcImage, formatName, saveFile);    
                return;    
            }    
        }    
        // ��ź��ͼ��Ŀ�͸�    
        int w = srcImage.getWidth();    
        int h = srcImage.getHeight();    
        // �����ź��ͼ���Ҫ���ͼ����һ��Ͷ���ŵ�ͼ��ĸ߶Ƚ��н�ȡ    
        if (w == width) {    
            // ����X�����    
            int x = 0;    
            int y = h / 2 - hight / 2;    
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);    
        }    
        // �����������ź��ͼ��ĸ߶Ⱥ�Ҫ���ͼ��߶�һ��Ͷ���ź��ͼ��Ŀ�Ƚ��н�ȡ    
        else if (h == hight) {    
            // ����X�����    
            int x = w / 2 - width / 2;    
            int y = 0;    
            saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);    
        }    
        in.close();    
    }    
    /**   
     * ʵ����ź�Ľ�ͼ   
     * @param image ��ź��ͼ��   
     * @param subImageBounds Ҫ��ȡ����ͼ�ķ�Χ   
     * @param subImageFile Ҫ������ļ�   
     * @throws IOException   
     */   
    private static void saveSubImage(BufferedImage image,    
            Rectangle subImageBounds, File subImageFile) throws IOException {    
        if (subImageBounds.x < 0 || subImageBounds.y < 0   
                || subImageBounds.width - subImageBounds.x > image.getWidth()    
                || subImageBounds.height - subImageBounds.y > image.getHeight()) {    
            log4j.debug("Bad   subimage   bounds");    
            return;    
        }    
        BufferedImage subImage = image.getSubimage(subImageBounds.x,subImageBounds.y, subImageBounds.width, subImageBounds.height);    
        String fileName = subImageFile.getName();    
        String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);    
        ImageIO.write(subImage, formatName, subImageFile);    
    }    
   
}    