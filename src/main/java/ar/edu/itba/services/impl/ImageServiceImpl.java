package ar.edu.itba.services.impl;

import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.services.ImageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class ImageServiceImpl implements ImageService{


    public ImageMatrix loadImage(File img) throws IOException {
        System.out.println("reading file");
        String extension = FilenameUtils.getExtension(img.getName());
        BufferedImage image;
        switch (extension) {
            case "raw":
            case "RAW":
                image = bufferedFromRaw(img);
                break;
            case "PPM":
            case "ppm":
                image = bufferedFromPPM(img);
                break;
            case "PGM":
            case "pgm":
                image = bufferedFromPGM(img);
                break;
            default:
                image = ImageIO.read(img);
        }
        if (image == null) {
            throw new IOException();
        }
        return ImageMatrix.readImage(image);
    }

    private BufferedImage bufferedFromRaw(File file) throws IOException{
        //search for data txt file
        String dataFilename = FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getName()) + ".data";
        if (!FilenameUtils.directoryContains(file.getParent(),dataFilename)) {
            //prompt width, height and pixel length?
            throw new FileNotFoundException("No existe el archivo "+ dataFilename +" de metadata de la imagen RAW en el directorio " + file.getParent());
        }

        File dataFile = new File(dataFilename);
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        int width = Integer.parseInt(reader.readLine());
        int height = Integer.parseInt(reader.readLine());
        int imageType = Integer.parseInt(reader.readLine());
        // GREY BufferedImage.TYPE_BYTE_GRAY == 10
        // RGB BufferedImage.TYPE_INT_RGB == 1
        return copyImage(width, height, imageType, file, 0);

    }

    private BufferedImage bufferedFromPPM(File file) throws IOException{
        int [] vals = parsePImage(file);
        int width = vals[1];
        int height = vals[2];
        int imageType = BufferedImage.TYPE_INT_RGB;
        // GREY BufferedImage.TYPE_BYTE_GRAY == 10
        // RGB BufferedImage.TYPE_INT_RGB == 1
        return copyImage(width, height, imageType, file, vals[4]);
    }

    private BufferedImage bufferedFromPGM(File file) throws IOException{
        int [] vals = parsePImage(file);
        int width = vals[1];
        int height = vals[2];
        int imageType = BufferedImage.TYPE_BYTE_GRAY;
        // GREY BufferedImage.TYPE_BYTE_GRAY == 10
        // RGB BufferedImage.TYPE_INT_RGB == 1
        return copyImage(width, height, imageType, file, vals[4]);
    }
    private BufferedImage copyImage(int width, int height, int imageType, File file, int src) throws IOException{
        byte[] pixels = IOUtils.toByteArray(new FileInputStream(file));
        BufferedImage image = new BufferedImage(width, height, imageType);
        DataBufferByte buffer = (DataBufferByte) image.getRaster().getDataBuffer();
        byte[] imgData = buffer.getData();
        System.arraycopy(pixels, src, imgData, 0, imgData.length);

        return image;
    }

    private BufferedImage copyRGBImage(int width, int height, int imageType, File file, int src) throws IOException {
        byte[] pixels = IOUtils.toByteArray(new FileInputStream(file));
        int[] rgbPixels = new int[(pixels.length -src)/3];


        for (int i = src; i < rgbPixels.length ; i+=3) {
            int color = pixels[i];
            color = (color << 8) + pixels[i+1];
            color = (color << 8) + pixels[i+2];
            rgbPixels[i/3] = color;
        }
        System.out.println(pixels.length - src);
        BufferedImage image = new BufferedImage(width, height, imageType);
        DataBufferInt buffer = (DataBufferInt) image.getRaster().getDataBuffer();
        int[] imgData = buffer.getData();
        System.out.println(imgData.length);
        System.arraycopy(rgbPixels, src, imgData, 0, imgData.length);

        return image;
    }

    private int[] parsePImage(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        boolean lineDone;
        String[] words;
        String line;
        int [] vals = new int[5];
        int k = 0;
        do {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            vals[4] += line.toCharArray().length + 1;
            System.out.println("LINE: " + line);
            words = line.split(" ");
            lineDone = false;
            System.out.println(words.length);
            for (String word: words) {
                System.out.println("WORD: " + word);
                switch (word.charAt(0)) {
                    case '#':
                        lineDone = true;
                        break;
                    case 'P':
                        vals[k] = Integer.parseInt(word.charAt(1) + "");
                        k++;
                        break;
                    default:
                        vals[k] = Integer.parseInt(word);
                        k++;
                }
                if (lineDone)
                    break;
                if (k >= 4)
                    return vals;
            }
        } while (line != null);
        throw new RuntimeException();
    }

    public BufferedImage deepCopy(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        //NOT CROPPED
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
