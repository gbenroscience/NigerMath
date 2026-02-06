/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author GBEMIRO JIBOYE <gbenroscience@gmail.com>
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.InputMismatchException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 *
 * @author MRSJIBOYE
 */
public class ImageUtilities {

    public static BufferedImage loadImage(String ref) {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(new File(ref));
        } catch (Exception e) {

        }
        return bimg;
    }

    /**
     *
     * @param url
     * @return a BufferedImage object from the Jar file.
     */
    public static BufferedImage loadImage(URL url) {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimg;
    }

    public void loadAndDisplayImage(JFrame frame, String imgPath) {
        // Load the img
        BufferedImage loadImg = ImageUtilities.loadImage(imgPath);
        frame.setBounds(0, 0, loadImg.getWidth(), loadImg.getHeight());
        frame.setVisible(true);
        // Get the surfaces Graphics object
        Graphics2D g = (Graphics2D) frame.getRootPane().getGraphics();
        // Now draw the image
        g.drawImage(loadImg, null, 0, 0);

    }

    /**
     *
     * @return the BufferedImage object created.
     */
    public static BufferedImage createImage() {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 100, 100);

        for (int i = 1; i < 49; i++) {
            g.setColor(new Color(5 * i, 5 * i, 4 + 1 * 2 + i * 3));
            g.fillRect(2 * i, 2 * i, 3 * i, 3 * 1);
        }
        return img;
    }

    /**
     * Saves a BufferedImage to the given file, pathname must not have any
     * periods "." in it except for the one before the format, i.e.
     * C:/images/fooimage.png
     *
     * @param img The image to save
     * @param ref The path to save the image to.
     */
    public static void saveImage(BufferedImage img, String ref) {
        //For no specified format, use png
        if(!ref.contains(".")){
            ref = ref.concat(".png");
        }
        try {
            String format = "";
            String lowercaseRef = ref.toLowerCase();
            if(lowercaseRef.endsWith("jpg") || lowercaseRef.endsWith("jpeg")){
                format = "jpg";
            }else if(lowercaseRef.endsWith("png")){
                format = "png";
            }else if(lowercaseRef.endsWith("tiff") || lowercaseRef.endsWith("bmp") || lowercaseRef.endsWith("gif")){
                format = lowercaseRef.substring(lowercaseRef.lastIndexOf(".")+1);
            }else{
                throw new InputMismatchException("We currently do not support the image format specified in "+ref);
            }
            ImageIO.write(img, format, new File(ref));
        } catch (InputMismatchException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param bufferedImage The BufferedImage object.
     * @return the bytes of the image as a byte array.
     */
    public static byte[] getImageBytes(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[]{};
        }
    }
    
 

    /**
     * 
     * @param bufferedImage The BufferedImage object.
     * @param format The image format e.g. jpg or png etc.
     * @return the bytes of the image as a byte array.
     */
    public static byte[] getImageBytes(BufferedImage bufferedImage, String format) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, format, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[]{};
        }
    }


    /**
     *
     * @param imageBytes The bytes of the BufferedImage object.
     * @return the image from this byte array or null if any exception occurs.
     */
    public static BufferedImage getImageFromBytes(byte[] imageBytes) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage b = ImageIO.read(bais);
            bais.close();

            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param is The image stream.
     * @return the image from this byte array or null if any exception occurs.
     */
    public static BufferedImage getImageFromStream(InputStream is) {
        try {
            BufferedImage b = ImageIO.read(is);
            is.close();
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Making an Image Translucent Sometimes it is necessary to make an image,
     * or part of an image somewhat translucent, or see through. If you have any
     * experience with Photoshop, Gimp or other Imaging programs, this means
     * modifying an Images alpha, or applying an Alpha layer on top of the
     * Image. I will show you how to do the latter. The Graphics2D object, which
     * is a part of every image, and top level container (such as a JFrame),
     * lets you manipulate how it draws through multiple methods. Mainly,
     * setComposite(Composite comp), setStroke(Stroke stroke) and setPaint(Paint
     * paint), all of these affect how the Graphics2D draws the image. For this
     * part of the tutorial, we will mainly be concerned with
     * setComposite(Composite comp).
     *
     * Composites let us modify things like the transparency of an image, which
     * is what we are interested in. Most of the time we only want the
     * AlphaComposite on the Image, not on the entire Container. So we'll add
     * another method to our ImageUtil class that will load an Image and set a
     * certain amount of alpha on the image only, leaving the container alone.
     * Here is the code:
     *
     * @param url
     * @param transparency
     * @return
     */
    public static BufferedImage loadTranslucentImage(String url, float transparency) {
        // Load the image
        BufferedImage loaded = loadImage(url);
        // Create the image using the
        BufferedImage aimg = new BufferedImage(loaded.getWidth(), loaded.getHeight(), BufferedImage.TRANSLUCENT);
        // Get the images graphics
        Graphics2D g = aimg.createGraphics();
        // Set the Graphics composite to Alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
        // Draw the LOADED img into the prepared reciver image
        g.drawImage(loaded, null, 0, 0);
        // let go of all system resources in this Graphics
        g.dispose();
        // Return the image
        return aimg;
    }

    /**
     *
     * @param img The image
     * @return the image flipped about an horizontal axis
     */
    public static BufferedImage horizontalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }

    /**
     *
     * @param table A javax.swing.JTable object
     * @return a BufferedImage of the JTable
     */
    public static BufferedImage createSwingTableImage(JTable table) {
        //get the table header component
        JTableHeader tableHeaderComp = table.getTableHeader();

        //Calculate the total width and height of thr table iwth the header
        int totalWidth = tableHeaderComp.getWidth() + table.getWidth();
        int totalHeight = tableHeaderComp.getHeight() + table.getHeight();

        //create a BufferedImage object of total width and height
        BufferedImage tableImage = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);

        //get the graphics object from the image
        Graphics2D g2D = (Graphics2D) tableImage.getGraphics();

        //paint the table header on image graphics
        tableHeaderComp.paint(g2D);

        //now translate the origin to (0, height of table header)
        g2D.translate(0, tableHeaderComp.getHeight());

        //now paint the table on the image graphics
        table.paint(g2D);

        //return the table image
        return tableImage;
    }

    /**
     *
     *
     * Note: For compound objects, the image may not be draws completely.. e.g
     * objects like JTables, that have other objects (headers) attached to them.
     *
     * @param obj The swing object whose image is needed.
     * @return a BufferedImage object of the JComponent.
     */
    public static BufferedImage createSwingObjectImage(JComponent obj) {

        //Calculate the total width and height of the swing object.
        int totalWidth = obj.getWidth();
        int totalHeight = obj.getHeight();

        //create a BufferedImage object of total width and height
        BufferedImage objImage = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);

        //get the graphics object from the image
        Graphics2D g2D = (Graphics2D) objImage.getGraphics();

        //paint the Swing object on image graphics
        obj.paint(g2D);

        //return the image
        return objImage;
    }

    /**
     *
     * @param img The image
     * @return the image flipped about a vertical axis
     */
    public static BufferedImage verticalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return dimg;
    }

    /**
     *
     * @param img The image
     * @param angle The angle of rotation.
     * @return the image rotated through the angle about the image center.
     */
    public static BufferedImage rotate(BufferedImage img, double angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawImage(img, null, 0, 0);
        return dimg;
    }

    /**
     *
     * @param img The image
     * @param cols The number of columns
     * @param rowsThe number of rows
     * @return An array of images that the original image has been split to
     * along <code>rows</code> and <code>cols</code>
     */
    public static BufferedImage[] splitImage(BufferedImage img, int cols, int rows) {
        int w = img.getWidth() / cols;
        int h = img.getHeight() / rows;
        int num = 0;
        BufferedImage imgs[] = new BufferedImage[w * h];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                imgs[num] = new BufferedImage(w, h, img.getType());
                // Tell the graphics to draw only one block of the image
                Graphics2D g = imgs[num].createGraphics();
                g.drawImage(img, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);
                g.dispose();
                num++;
            }
        }
        return imgs;
    }

    /**
     *
     * @param src The source image.
     * @param w The new width to resize it to.
     * @param h The new height to resize it to.
     * @param io The ImageObserver
     * @return the resized version of the image.
     */
    public static BufferedImage resizeImage(BufferedImage src, int w, int h, ImageObserver io) {
        return resizeImage(src, new Dimension(w, h), io);
    }

    /**
     *
     * @param src The source image.
     * @param newSize The new size to resize it to.
     * @param io The {@link ImageObserver}
     * @return the resized version of the image.
     */
    public static BufferedImage resizeImage(BufferedImage src, Dimension newSize, ImageObserver io) {

        int srcW = src.getWidth();
        int srcH = src.getHeight();
        if (srcW != newSize.width || srcH != newSize.height) {
            int dstW = newSize.width;
            int dstH = newSize.height;

            BufferedImage tmp = new BufferedImage(dstW, srcH, BufferedImage.TYPE_INT_BGR);
            Graphics g = tmp.getGraphics();
            Color c = Color.darkGray;
            g.setColor(c);
            int delta = (srcW << 16) / dstW;
            int pos = delta / 2;

            for (int x = 0; x < dstW; x++) {
                g.setClip(x, 0, 1, srcH);
                g.drawImage(src, x - (pos >> 16), 0, io);
                pos += delta;
            }

            BufferedImage dst = new BufferedImage(dstW, dstH, BufferedImage.TYPE_INT_BGR);
            g = dst.getGraphics();

            delta = (srcH << 16) / dstH;
            pos = delta / 2;

            for (int y = 0; y < dstH; y++) {
                g.setClip(0, y, dstW, 1);
                g.drawImage(tmp, 0, y - (pos >> 16), io);
                pos += delta;
            }

            return dst;
        }
        return src;
    }

    /**
     *
     * @param img The image to scale
     * @param width The new width
     * @param height The new height
     * @param background The background color
     * @return
     */
    public static BufferedImage resizeImage(BufferedImage img, int width, int height,
            Color background) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        if (imgWidth * height < imgHeight * width) {
            width = imgWidth * height / imgHeight;
        } else {
            height = imgHeight * width / imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            if (background != null) {
                g.setBackground(background);
            }
            g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        return newImage;
    }

    /**
     *
     * @param x The x coordinate to start copying from.
     * @param y The y coordinate to start copying from.
     * @param width The distance along the horizontal for which the copy is to
     * be done.
     * @param height The distance along the vertical for which the copy is to be
     * done.
     * @return a BufferedImage object which is an independent clone of the area
     * specified in the one passed as a parameter to this method.
     */
    public static BufferedImage copyArea(BufferedImage img, int x, int y, int width, int height) {
        if (width < img.getWidth() && height < img.getHeight()) {
            BufferedImage subImg = img.getSubimage(x, y, width, height);
            Raster raster = subImg.getData();
            BufferedImage areaCopied = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            areaCopied.setData(raster);
            return areaCopied;
        }//end if
        throw new IllegalArgumentException("Specified width or height cannot be greater"
                + " than that of the substrate image!");
    }//end method

    /**
     *
     * @param x The x coordinate to start copying from.
     * @param y The y coordinate to start copying from.
     * @param width The distance along the horizontal for which the copy is to
     * be done.
     * @param height The distance along the vertical for which the copy is to be
     * done.
     * @return a BufferedImage object which is an independent clone of the area
     * specified in the one passed as a parameter to this method.
     */
    public static BufferedImage clone(BufferedImage img) {
        Raster raster = img.getData();
        BufferedImage cloned = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        cloned.setData(raster);
        return cloned;
    }//end method

    /**
     *
     * @param image The Image object
     * @return a Buffered Image object.
     */
    public static BufferedImage toBufferedImage(Image image) {
        // This code ensures that all the pixels in the image are loaded.
        image = new ImageIcon(image).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }

    public static BufferedImage toBufferedImage(ImageIcon icon , int imageType) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                imageType);
        Graphics g = bi.createGraphics();
        
// paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();

        return bi;
    }

    /**
     * Scales this image at constant aspect ratio.
     *
     * @param map The bitmap to scale
     * @param newDimension The width of the image or its height
     * @param newDimensionIsWidth Set this parameter to true if newDimension
     * refers to the new width of the Bitmap, else set it to false if it refers
     * to the new height.
     *
     */
    public static BufferedImage scaleImage(BufferedImage map, int newDimension, boolean newDimensionIsWidth) {

        float oldWidth = map.getWidth();
        float oldHeight = map.getHeight();

        float newWidth = 1;
        float newHeight = 1;

        float aspectRatio = oldWidth / oldHeight;
        float scaleWidth;
        float scaleHeight;

        if (newDimensionIsWidth) {
            newWidth = newDimension;
            newHeight = newWidth / aspectRatio;
        } else {
            newHeight = newDimension;
            newWidth = newHeight * aspectRatio;
        }

        scaleWidth = newWidth / oldWidth;
        scaleHeight = newHeight / oldHeight;

        BufferedImage resizedImage = new BufferedImage((int) scaleWidth, (int) scaleHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        try {
            g.setComposite(AlphaComposite.Src);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(map, 0, 0, (int) scaleWidth, (int) scaleHeight, null);

        } catch (Exception e) {
        } finally {
            g.dispose();
        }

        return resizedImage;
    }

}//end class
