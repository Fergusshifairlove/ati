package ar.edu.itba.models.masks;

/**
 * Created by root on 9/16/17.
 */
public class LaplacianMask extends Mask {
    private static final int WHITE = 255;

    public LaplacianMask(int size) {
        super(size);
        mask[0][0] = mask[0][2] = mask[2][0] = mask[2][2] = 0;
        mask[0][1] = mask[1][0] = mask[1][2] = mask[2][1] = -1;
        mask[1][1] = 4;

    }

    public double[][] filter(double[][] image) {
        double[][] maskApplied;

        maskApplied = filterImage(image);
        int height = maskApplied.length;
        int width = maskApplied[0].length;
        double[][] crossByRow = new double[height][width];
        double[][] crossByCol = new double[height][width];

        for (int i = 0; i <height ; i++) {
            for (int j = 0; j <width ; j++) {
                crossByRow[i][j]=0;
                crossByCol[i][j]=0;

            }

        }

        //cross by zero per row
        for (int i = 0; i < height; i++) {
            double previous = maskApplied[i][0];
            for (int j = 1; j < width; j++) {
                double current = maskApplied[i][j];

                if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {
                    crossByRow[i][j] = WHITE;
                    }else{
                    crossByRow[i][j]=0;
                    }
                previous = current;
                }

            }


        //cross by zero per col
        for (int j = 0; j < width; j++) {
            double previous = maskApplied[0][j];
            for (int i = 1; i < height; i++) {
                double current = maskApplied[i][j];

                if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {
                        crossByCol[i][j] = WHITE;
                    } else{
                        crossByCol[i][j]=0;

                    }
                previous = current;
                }

            }


        //union
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (crossByRow[i][j] == WHITE || crossByCol[i][j] == WHITE) {
                    maskApplied[i][j] = WHITE;
                } else {
                    maskApplied[i][j] = 0;
                }
            }
        }





        return maskApplied;

    }
}