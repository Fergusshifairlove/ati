package ar.edu.itba.services.impl;

import ar.edu.itba.models.Pixel;
import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.services.ImageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.*;

public class ImageServiceImpl implements ImageService{

    private ImageMatrix matrix;


    public void loadImage(File img) throws IOException {
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
        this.matrix = ImageMatrix.readImage(image);
    }

    public Pixel selectPixel(int x, int y) {
        return this.matrix.getPixelColor(x, y);
    }

    @Override
    public void modifyPixel(Pixel pixel) {
        matrix.setPixel(pixel);
    }

    @Override
    public BufferedImage getImage() {
        return matrix.getImage();
    }

    private static BufferedImage bufferedFromRaw(File file) throws IOException{
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

    private static BufferedImage bufferedFromPPM(File file) throws IOException{
        int [] vals = parsePImage(file);
        for (int i = 0; i < vals.length; i++) {
            System.out.println(vals[i]);
        }
        int width = vals[1];
        int height = vals[2];
        int imageType = BufferedImage.TYPE_INT_RGB;
        // GREY BufferedImage.TYPE_BYTE_GRAY == 10
        // RGB BufferedImage.TYPE_INT_RGB == 1
        return copyImage(width, height, imageType, file, vals[4]);
    }

    private static BufferedImage bufferedFromPGM(File file) throws IOException{
        int [] vals = parsePImage(file);
        for (int i = 0; i < vals.length; i++) {
            System.out.println(vals[i]);
        }
        int width = vals[1];
        int height = vals[2];
        int imageType = BufferedImage.TYPE_BYTE_GRAY;
        // GREY BufferedImage.TYPE_BYTE_GRAY == 10
        // RGB BufferedImage.TYPE_INT_RGB == 1
        System.out.println(width);
        System.out.println(height);
        return copyImage(width, height, imageType, file, vals[4]);
    }
    private static BufferedImage copyImage(int width, int height, int imageType, File file, int src) throws IOException{
        byte[] pixels = IOUtils.toByteArray(new FileInputStream(file));
        System.out.println(pixels.length - src);
        BufferedImage image = new BufferedImage(width, height, imageType);
        DataBufferByte buffer = (DataBufferByte) image.getRaster().getDataBuffer();
        byte[] imgData = buffer.getData();
        System.out.println(imgData.length);
        System.arraycopy(pixels, src, imgData, 0, imgData.length);

        return image;
    }

    private static int[] parsePImage(File file) throws IOException{
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
}
