package ar.edu.itba.models;

import ar.edu.itba.constants.Direction;
import ar.edu.itba.models.masks.GaussianMask;
import ar.edu.itba.models.masks.Mask;

import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Created by Luis on 29/10/2017.
 */
public class ActiveContours {
    private static int LIN = -1, LOUT = 1, OUT = 3, IN = -3;
    private static Direction[] DIRECTIONS = {Direction.HORIZONTAL, Direction.VERTICAL};
    private Set<Position> lIn, lOut;
    private Map<Position, Integer> phi;
    private Map<Position, Double> gaussianPhi;
    private Pixel backgroundAvg, objectAvg;
    private Mask mask;

    public ActiveContours(ImageMatrix image, int startX, int startY, int endX, int endY) {
        lIn = new HashSet<>();
        lOut = new HashSet<>();

        phi = new HashMap<>();
        gaussianPhi = new HashMap<>();

        System.out.println("start x: " + startX + " start y: " + startY);
        System.out.println("end x: " + endX + " end y: " + endY);

        int objCount = 0, avgCount = 0;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (i > startX && j > startY && i < endX && j < endY) {
                    phi.put(new Position(i, j), IN);
                    if (objectAvg == null)
                        objectAvg = image.getPixelColor(i,j);
                    else
                        objectAvg = this.objectAvg.binaryOperation(image.getPixelColor(i,j), (x,y) -> x + y);
                    objCount++;
                    continue;
                }

                if (((i == startX || i == endX) && j >= startY && j <= endY) ||
                        ((j == startY || j == endY) && i >= startX && i <= endX)) {
                    lIn.add(new Position(i, j));
                    phi.put(new Position(i, j), LIN);
                    continue;
                }

                if (((i == startX - 1 || i == endX + 1) && j >= startY && j <= endY) ||
                        ((j == startY - 1 || j == endY + 1) && i >= startX && i <= endX)) {
                    lOut.add(new Position(i, j));
                    phi.put(new Position(i, j), LOUT);
                    continue;
                }
                phi.put(new Position(i, j), OUT);

                if (backgroundAvg == null)
                    backgroundAvg = image.getPixelColor(i,j);
                else
                    backgroundAvg = this.backgroundAvg.binaryOperation(image.getPixelColor(i,j), (x,y) -> x + y);
                avgCount++;
            }
        }

        int countObj = objCount, countAvg = avgCount;
        mask = new GaussianMask(5, 1);
        objectAvg = objectAvg.singleOperation(x -> x/countObj);
        backgroundAvg = backgroundAvg.singleOperation(x -> x/countAvg);

    }

    public Set<Position> getlIn() {
        return lIn;
    }

    public Set<Position> getlOut() {
        return lOut;
    }

    private double speed(Pixel pixel) {
        double num = backgroundAvg.binaryOperation(pixel, (x, y) -> x - y).norm();
        double dem = objectAvg.binaryOperation(pixel, (x, y) -> x - y).norm();

        return Math.log(num / dem);
    }

    private Pixel average(Integer phiValue, ImageMatrix image) {
        Pixel p, acum = null;
        int count = 0;
        for (Position position : getPhiPixels(phiValue)) {
            count++;
            p = image.getPixelColor(position);
            if (acum == null) {
                acum = p;
            }else {
                acum = acum.binaryOperation(p, (x, y) -> x + y);
            }
        }
        int total = count;
        if (acum != null) {
            acum = acum.singleOperation(x -> x / total);
        }
        return acum;
    }

    public ImageMatrix findObject(ImageMatrix frame, int times, Pixel lin, Pixel lout) {
        computeCycle(frame, times, this::speed);
        computeCycle(frame, times, this::calculateGaussianPhi);
        return paintImage(frame, lin, lout);
    }

    private void computeCycle(ImageMatrix frame, int times, ToDoubleFunction<Pixel> speedFunction) {
        Pixel pixel;
        boolean inDone = false, outDone = false;
        int count = 0;
        Set<Position> toRemove = new HashSet<>(), toAdd = new HashSet<>();

        while (!(inDone && outDone) && count < times) {
            count++;
            System.out.println("TIMES: " + count + " LIN: " + lIn.size() + " LOUT: " + lOut.size());
            outDone = true;

            //switch in
            for (Position p : lOut) {
                pixel = frame.getPixelColor(p);
                if (speedFunction.applyAsDouble(pixel) > 0) {
                    outDone = false;
                    toRemove.add(p);
                    lIn.add(p);
                    phi.put(p, LIN);

                    //switch neighbours
                    for (Position n : p.getNeighbours(DIRECTIONS)) {
                        if (phi.getOrDefault(n, -5) == OUT) {
                            phi.put(n, LOUT);
                            toAdd.add(n);
                        }
                    }
                }
            }

            lOut.addAll(toAdd);
            lOut.removeAll(toRemove);

            toAdd.clear();
            toRemove.clear();

            //check lin
            for (Position p : lIn) {
                if (!isLin(p)) {
                    toRemove.add(p);
                    phi.put(p, IN);
                }
            }

            lIn.removeAll(toRemove);
            toRemove.clear();

            inDone = true;
            //switch out
            for (Position p : lIn) {
                pixel = frame.getPixelColor(p);
                if (speedFunction.applyAsDouble(pixel) < 0) {
                    inDone = false;
                    toRemove.remove(p);
                    lOut.add(p);
                    phi.put(p, LOUT);

                    //switch neighbours
                    for (Position n : p.getNeighbours(DIRECTIONS)) {
                        if (phi.getOrDefault(n, -5) == IN) {
                            phi.put(n, LIN);
                            toAdd.add(n);
                        }
                    }
                }
            }

            lIn.addAll(toAdd);
            lIn.removeAll(toRemove);

            toAdd.clear();
            toRemove.clear();

            //check lout
            for (Position p : lOut) {
                if (!isLout(p)) {
                    toRemove.remove(p);
                    phi.put(p, OUT);
                }
            }

            lOut.removeAll(toRemove);
            toRemove.clear();
        }
    }


    private boolean isLin(Position p) {
        boolean touchesIn = false, touchesLout = false;
        for (Position neighbour : p.getNeighbours(DIRECTIONS)) {
            if (phi.getOrDefault(neighbour, 0) == LOUT)
                touchesLout = true;
        }
        return touchesLout;
    }

    private boolean isLout(Position p) {
        boolean touchesOut = false, touchesLin = false;
        for (Position neighbour : p.getNeighbours(DIRECTIONS)) {

            if (phi.getOrDefault(neighbour, 0) == LIN)
                touchesLin = true;
        }
        return touchesLin;
    }

    private Iterable<Position> getPhiPixels(Integer phiValue) {
        return phi.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), phiValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private double calculateGaussianPhi(Pixel pixel) {
        Position pos = pixel.getPosition();
        double[] phiMatrix = new double[25];

        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                phiMatrix[(i + 2) * 5 + j + 2] = phi.getOrDefault(new Position(pos.getX() + i, pos.getY() + j), 0);
            }
        }

        return -1 * mask.applyFilter(phiMatrix);
    }

    private ImageMatrix paintImage(ImageMatrix image, Pixel lin, Pixel lout) {
        for (Position p: lIn) {
            image.setPixel(lin.changePosition(p));
        }
        for (Position p: lOut) {
            image.setPixel(lout.changePosition(p));
        }
        return image;
    }

}
