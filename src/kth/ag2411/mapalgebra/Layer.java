package kth.ag2411.mapalgebra;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import javax.swing.*;

public class Layer {
    public String name;
    public int nRows;
    public int nCols;
    public double[] origin = new double[2];
    public double resolution;
    public double[][] values;
    public double nullValue = -9999;

    public Layer(String layerName, String fileName) {
        this.name = layerName;
        try {
            File rFile = new File(fileName);
            FileReader fReader = new FileReader(rFile);
            BufferedReader bReader = new BufferedReader(fReader);

            String text = bReader.readLine();
            this.nCols = Integer.parseInt(text.substring(5).trim());

            text = bReader.readLine();
            this.nRows = Integer.parseInt(text.substring(5).trim());

            text = bReader.readLine();
            this.origin[0] = Double.parseDouble(text.substring(10).trim());
            text = bReader.readLine();
            this.origin[1] = Double.parseDouble(text.substring(10).trim());

            text = bReader.readLine();
            this.resolution = Double.parseDouble(text.substring(10).trim());

            text = bReader.readLine();
            this.nullValue = Double.parseDouble(text.substring(13).trim());

            int row = 0;
            this.values = new double[nRows][nCols];

            text = bReader.readLine();
            while (text != null) {
//                System.out.println("this is the " + count + "th line: " + text);
                String[] textArray = text.split(" ");
                double value;
                for (int col = 0; col < textArray.length; col++) {
                    value = Double.parseDouble(textArray[col]);
                    this.values[row][col] = value;
                }
                row++;
                text = bReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Layer(String outLayerName, int nCols, int nRows,
                 double[] origin, double resolution, double nullValue) {
        this.name = outLayerName;
        this.nCols = nCols;
        this.nRows = nRows;
        this.origin = origin;
        this.resolution = resolution;
        this.nullValue = nullValue;
        this.values = new double[nRows][nCols];
    }

    public void print() {
        System.out.println("============ Layer: " + name + " ============");
        System.out.println("nCols         " + nCols);
        System.out.println("nRows         " + nRows);
        System.out.println("xllcorner     " + origin[0]);
        System.out.println("yllcorner     " + origin[1]);
        System.out.println("cellsize      " + resolution);
        System.out.println("NODATA_value  " + nullValue);

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                System.out.print(values[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void save(String outputFileName) {
        try {
            File newFile = new File(outputFileName);
            FileWriter fWriter = new FileWriter(newFile);
            fWriter.write("nCols          " + this.nCols + "\n");
            fWriter.write("nRows          " + this.nRows + "\n");
            fWriter.write("xllcorner      " + this.origin[0] + "\n");
            fWriter.write("yllcorner      " + this.origin[1] + "\n");
            fWriter.write("cellsize       " + this.resolution + "\n");
            fWriter.write("NODATA_value   " + this.nullValue + "\n");
            for (int i = 0; i < this.nRows; i++) {
                for (int j = 0; j < this.nCols; j++) {
                    fWriter.write(Double.toString(this.values[i][j]) + " ");
                }
                fWriter.write("\n");
            }
            fWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public BufferedImage toImage() {
        int height = values.length;
        int width = values[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();

        double min = getMin();
        double max = getMax();

        int[] color = new int[3];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int greyValue = (int) (255 - (values[h][w] - min) / (max - min) * 255);

                color[0] = greyValue;
                color[1] = greyValue;
                color[2] = greyValue;
                raster.setPixel(w, h, color);
            }
        }
        return image;
    }


    public BufferedImage toImage(Double[] highlightV) {
        int width = values[0].length;
        int height = values.length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();

        double min = getMin();
        double max = getMax();

        HashMap<Double, int[]> colorMap = new HashMap<Double, int[]>();
        for (int i = 0; i < highlightV.length; i++) {
            int[] rgb = new int[3];
            rgb[0] = (int) (Math.random() * 255);
            rgb[1] = (int) (Math.random() * 255);
            rgb[2] = (int) (Math.random() * 255);
            colorMap.put(highlightV[i], rgb);
        }

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int[] color = new int[3];
                if (Arrays.asList(highlightV).contains(values[r][c])) {
                    color = colorMap.get(values[r][c]);
                } else {
                    int greyValue = (int) (255 - (values[r][c] - min) / (max - min) * 255);
                    color[0] = greyValue;
                    color[1] = greyValue;
                    color[2] = greyValue;
                }
                raster.setPixel(c, r, color);
            }
        }
        return image;
    }


    private double getMin() {
        double smallest = Double.POSITIVE_INFINITY;
        double currentValue;

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                currentValue = values[i][j];

                if (currentValue < smallest) {
                    smallest = currentValue;
                }
            }
        }
        return smallest;
    }

    private double getMax() {
        double biggest = Double.NEGATIVE_INFINITY;
        double currentValue;

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                currentValue = values[i][j];

                if (currentValue > biggest) {
                    biggest = currentValue;
                }
            }
        }
        return biggest;
    }

    // TODO: check nullValue not using if
    public Layer localSum(Layer inLayer, String outLayerName) {
        if (inLayer.nRows != nRows | inLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    inLayer.nRows, inLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }
        Layer outLayer = new Layer(outLayerName, this.nCols,
                this.nRows, this.origin, this.resolution, this.nullValue);
        outLayer.values = new double[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (inLayer.values[i][j] == nullValue || values[i][j] == nullValue) {
                    outLayer.values[i][j] = nullValue;
                }
                outLayer.values[i][j] = inLayer.values[i][j] + values[i][j];
            }
        }
        return outLayer;
    }
    public Layer focalVariety(int r, boolean isSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nCols,
                this.nRows, this.origin, this.resolution, this.nullValue);

        outLayer.values = new double[nRows][nCols];
        ArrayList<Integer[]> neighborhood;

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                 ArrayList<Double> valueList = new ArrayList<Double>();
                
                neighborhood = this.getNeighborhood(new int[]{i, j}, r, isSquare);
//               System.out.printf("===>>> Operating cell: (%d, %d) \n", i, j);
                for (Integer[] nb : neighborhood) {
                    int x = nb[0];
                    int y = nb[1];
//                    System.out.println("neighborhood: " + x + " " + y);
                    // the condition after && is to remove the duplicated value in each neighborhood.
                    if (values[x][y] != nullValue && !valueList.contains(values[x][y])) {
                    		valueList.add(values[x][y]);
                    }
                }
                outLayer.values[i][j] = valueList.size();
            }
        }
        return outLayer;
    }

    ////////////////// Local Operations(by Ion)

    public Layer localMean(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, this.nullValue);
        for(int i=0;i<nRows;i++) {
            for(int j=0;j<nCols;j++) {
                if(values[i][j]==this.nullValue || inLayer.values[i][j]==inLayer.nullValue) {//if either of the value is null
                    outLayer.values[i][j]=outLayer.nullValue;//set the cell nullvalue
                }
                else {
                    outLayer.values[i][j] = (values[i][j] + inLayer.values[i][j])/2;
                }
            }
        }
        return outLayer;
    }

    // local difference
    // here should be non-negtive????
    public Layer localDifference(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, this.nullValue);
        for(int i=0;i<nRows;i++) {
            for(int j=0;j<nCols;j++) {
                if(values[i][j]==this.nullValue || inLayer.values[i][j]==inLayer.nullValue) {//if either of the value is null
                    outLayer.values[i][j]=outLayer.nullValue;//set the cell nullvalue
                }
                else {
                    outLayer.values[i][j] = values[i][j] - inLayer.values[i][j];
                }
            }
        }
        return outLayer;
    }

    // local Product
    public Layer localProduct(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, this.nullValue);
        for(int i=0;i<nRows;i++) {
            for(int j=0;j<nCols;j++) {
                if(values[i][j]==this.nullValue || inLayer.values[i][j]==inLayer.nullValue) {//if either of the value is null
                    outLayer.values[i][j]=outLayer.nullValue;//set the cell nullvalue
                }
                else {
                    outLayer.values[i][j] = values[i][j] * inLayer.values[i][j];
                }
            }
        }
        return outLayer;
    }

    // local maximum
    public Layer localMax(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, this.nullValue);
        for(int i=0;i<nRows;i++) {
            for(int j=0;j<nCols;j++) {
                if(values[i][j]==this.nullValue || inLayer.values[i][j]==inLayer.nullValue) {//if either of the value is null
                    outLayer.values[i][j]=outLayer.nullValue;//set the cell nullvalue
                }
                else {
                    outLayer.values[i][j] = Math.max(values[i][j], inLayer.values[i][j]);
                }
            }
        }
        return outLayer;
    }

    // local minimum
    public Layer localMin(Layer inLayer, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, this.nRows, this.nCols, this.origin, this.resolution, this.nullValue);
        for(int i=0;i<nRows;i++) {
            for(int j=0;j<nCols;j++) {
                if(values[i][j]==this.nullValue || inLayer.values[i][j]==inLayer.nullValue) {//if either of the value is null
                    outLayer.values[i][j]=outLayer.nullValue;//set the cell nullvalue
                }
                else {
                    outLayer.values[i][j] = Math.min(values[i][j], inLayer.values[i][j]);
                }
            }
        }
        return outLayer;
    }

    /////////////////////////// Focal Operation part (by Shuo)
    public Layer focalPercentage(int r, boolean IsSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        int count;
        for(int i = 0;i< nRows*nCols;i++) {
            boolean flag = true;
            int[] neighborhood = returnNeighborhood(i,r,IsSquare);
            count = 0;
            for(int j = 0;j<neighborhood.length;j++) {
                int row = neighborhood[j]/nCols; //corvert j to (row,col)
                int col = neighborhood[j]%nCols;
                if(values[row][col]==nullValue) {
                    flag=false;
                    break;
                }
                if((values[row][col]) == values[i/nCols][i%nCols])
                    count = count + 1;
            }
            if(flag == false) {
                outLayer.values[i/nCols][i%nCols] = nullValue;
                break;
            }
            outLayer.values[i/nCols][i%nCols] = (double)count / (double)neighborhood.length;
        }
        return outLayer;
    }

    public Layer focalMean(int r, boolean IsSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        double sum;
        for(int i = 0;i< nRows*nCols;i++) {
            int[] neighborhood = returnNeighborhood(i,r,IsSquare);
            sum = 0;
            for(int j = 0;j<neighborhood.length;j++) {
                int row = neighborhood[j]/nCols; //corvert j to (row,col)
                int col = neighborhood[j]%nCols;
                if(values[row][col]==nullValue) {
                    sum = nullValue * (double)neighborhood.length;
                    break;
                }
                else {
                    sum = sum + values[row][col];
                }
            }
            outLayer.values[i/nCols][i%nCols] = sum / (double)neighborhood.length;
        }
        return outLayer;
    }

    public Layer focalStd(int r, boolean IsSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        double sum;
        double mean;
        double squaresum;
        boolean flag ;
        for(int i = 0;i< nRows*nCols;i++) {
            int[] neighborhood = returnNeighborhood(i,r,IsSquare);
            sum = 0;
            mean = 0.0;
            squaresum = 0.0;
            flag = true;
            for(int j = 0;j<neighborhood.length;j++) {
                int row = neighborhood[j]/nCols; //corvert j to (row,col)
                int col = neighborhood[j]%nCols;
                if(values[row][col]==nullValue) {
                    flag = false;
                    break;
                }
                sum = sum + values[row][col];
            }

            if(flag == false) {
                outLayer.values[i/nCols][i%nCols] = nullValue;
                break;
            }

            mean = sum / (double)neighborhood.length;
            for(int j = 0;j<neighborhood.length;j++) {
                int row = neighborhood[j]/nCols; //corvert j to (row,col)
                int col = neighborhood[j]%nCols;
                squaresum = squaresum + Math.pow((values[row][col]-mean), 2);
            }

            outLayer.values[i/nCols][i%nCols] = Math.sqrt(squaresum / ((double)neighborhood.length-1));
        }
        return outLayer;
    }

    public Layer focalDrainage(int r, boolean IsSquare, String outLayerName) {
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        for(int i = 0;i< nRows*nCols;i++) {
            int[] neighborhood = returnNeighborhood(i,r,IsSquare);
            double minneiValue = values[neighborhood[i]/nCols][neighborhood[i]%nCols];
            int index = 0;
            boolean flag =true;
            for(int j = 0;j<neighborhood.length;j++) {
                int row = neighborhood[j]/nCols; //corvert j to (row,col)
                int col = neighborhood[j]%nCols;
                if(values[row][col]==nullValue) {
                    flag = false;
                    break;
                }
                if (values[row][col]<minneiValue ) {
                    minneiValue = values[row][col];
                    index = j; //find the drainage point
                }
            }
            if(flag == false) {
                outLayer.values[i/nCols][i%nCols] = nullValue;
                break;
            }
            if (index>neighborhood.length/2)
                index = index-1;  // this indicates that the center point does not need a direction number
            outLayer.values[i/nCols][i%nCols] = index;
        }
        return outLayer;
    }

/////////////////////////// Zonal Operations(by Hui)

    /**
     * Algorithm reference: http://resources.esri.com/help/9.3/arcgisengine/java/gp_toolref/spatial_analyst_tools/how_zonal_statistics_works.htm
     */
    public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
        // if the two input layer's dimensions mismatch, throw an exception.
        if (zoneLayer.nRows != nRows | zoneLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    zoneLayer.nRows, zoneLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }

        Layer outLayer = new Layer(outLayerName, nCols, nRows, origin, resolution, nullValue);
        outLayer.values  = new double[nRows][nCols];
        // locations of cells with same value. {value : (r1, c1), (r2, c2)....}
        HashMap<Double, Double> mask = new HashMap<Double, Double>();

        // loop through every cell in inLayer, find the unique value as key in mask, and the minimum value of this.layer.values in that zone
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (!mask.containsKey(zoneLayer.values[r][c]) ||
                        ( values[r][c] > nullValue && values[r][c] < mask.get(zoneLayer.values[r][c]))) {
                    mask.put(zoneLayer.values[r][c], this.values[r][c]);
                }
            }
        }

        // assign the minimum value stored in mask to corresponding zone to outLayer.
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                double min = mask.get(zoneLayer.values[i][j]);
                if (min != nullValue) {
                    outLayer.values[i][j] = min;
                } else {
                    outLayer.values[i][j] = nullValue;
                }
            }
        }
        return outLayer;
    }

    public Layer zonalMaximum(Layer zoneLayer, String outLayerName) {
        // if the two input layer's dimensions mismatch, throw an exception.
        if (zoneLayer.nRows != nRows | zoneLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    zoneLayer.nRows, zoneLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }

        Layer outLayer = new Layer(outLayerName, nCols, nRows, origin, resolution, nullValue);
        outLayer.values  = new double[nRows][nCols];
        // {zone : this.values.min, ....}
        HashMap<Double, Double> mask = new HashMap<Double, Double>();

        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (!mask.containsKey(zoneLayer.values[r][c]) ||
                        ( values[r][c] > nullValue && values[r][c] > mask.get(zoneLayer.values[r][c]))) {
                    mask.put(zoneLayer.values[r][c], this.values[r][c]);
                }
            }
        }

        // assign the minimum value stored in mask to corresponding zone to outLayer.
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                double max = mask.get(zoneLayer.values[i][j]);
                if (max != nullValue) {
                    outLayer.values[i][j] = max;
                } else {
                    outLayer.values[i][j] = nullValue;
                }
            }
        }
        return outLayer;

    }

    public Layer zonalMean(Layer zoneLayer, String outLayerName) {
        // if the two input layer's dimensions mismatch, throw an exception.
        if (zoneLayer.nRows != nRows | zoneLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    zoneLayer.nRows, zoneLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }

        Layer outLayer = new Layer(outLayerName, nCols, nRows, origin, resolution, nullValue); // initialize the outLayer
        outLayer.values  = new double[nRows][nCols]; // define the size for outLayer.values to make it assignable later
        // locations of cells with same value. {value : (r1, c1), (r2, c2)....}
        // for storing the index(the unique value of zoneLayer) of each zone and the minimum value of this.layer.values in that zone
        HashMap<Double, Double> mask = new HashMap<Double, Double>();
        HashMap<Double, Integer> zoneValueNum = new HashMap<Double, Integer>();
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);

        // loop through every cell in inLayer, find the unique value as key in mask, and the minimum value of this.layer.values in that zone
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (values[r][c] > nullValue) {
                    if (!mask.containsKey(zoneLayer.values[r][c])) {
                        mask.put(zoneLayer.values[r][c], values[r][c]);
                        zoneValueNum.put(zoneLayer.values[r][c], 1);
                    } else {
                        mask.put(zoneLayer.values[r][c], mask.get(zoneLayer.values[r][c]) + values[r][c]);
                        zoneValueNum.put(zoneLayer.values[r][c], zoneValueNum.get(zoneLayer.values[r][c]) + 1);
                    }
                }
            }
        }

        // assign the minimum value stored in mask to corresponding zone to outLayer.
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                double mean = mask.get(zoneLayer.values[i][j]) / zoneValueNum.get(zoneLayer.values[i][j]);
                BigDecimal b   =   new   BigDecimal(mean);
                mean   =   b.setScale(4,   RoundingMode.HALF_EVEN).doubleValue();
                outLayer.values[i][j] = mean;
            }
        }
        return outLayer;
    }

    public Layer zonalSum(Layer zoneLayer, String outLayerName) {
        // if the two input layer's dimensions mismatch, throw an exception.
        if (zoneLayer.nRows != nRows | zoneLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    zoneLayer.nRows, zoneLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }

        Layer outLayer = new Layer(outLayerName, nCols, nRows, origin, resolution, nullValue); // initialize the outLayer
        outLayer.values  = new double[nRows][nCols]; // define the size for outLayer.values to make it assignable later
        // locations of cells with same value. {value : (r1, c1), (r2, c2)....}
        // for storing the index(the unique value of zoneLayer) of each zone and the minimum value of this.layer.values in that zone
        HashMap<Double, Double> mask = new HashMap<Double, Double>();


        // loop through every cell in inLayer, find the unique value as key in mask, and the minimum value of this.layer.values in that zone
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (values[r][c] > nullValue) {
                    if (!mask.containsKey(zoneLayer.values[r][c])) {
                        mask.put(zoneLayer.values[r][c], values[r][c]);
                    } else {
                        mask.put(zoneLayer.values[r][c], mask.get(zoneLayer.values[r][c]) + values[r][c]);

                    }
                }
            }
        }

        // assign the minimum value stored in mask to corresponding zone to outLayer.
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                double sum = mask.get(zoneLayer.values[i][j]);
                outLayer.values[i][j] = sum;
            }
        }
        return outLayer;
    }

    public Layer zonalVariety(Layer zoneLayer, String outLayerName) {
        // if the two input layer's dimensions mismatch, throw an exception.
        if (zoneLayer.nRows != nRows | zoneLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    zoneLayer.nRows, zoneLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }

        Layer outLayer = new Layer(outLayerName, nCols, nRows, origin, resolution, nullValue); // initialize the outLayer
        outLayer.values  = new double[nRows][nCols]; // define the size for outLayer.values to make it assignable later
        // locations of cells with same value. {value : (r1, c1), (r2, c2)....}
        // for storing the index(the unique value of zoneLayer) of each zone and the minimum value of this.layer.values in that zone
        HashMap<Double, ArrayList<Double>> mask = new HashMap<Double, ArrayList<Double>>();

        // loop through every cell in inLayer, find the unique value as key in mask, and the minimum value of this.layer.values in that zone
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (values[r][c] > nullValue) {
                    if (!mask.containsKey(zoneLayer.values[r][c])) {
                        ArrayList<Double> valueList = new ArrayList<Double>();
                        valueList.add(values[r][c]);
                        mask.put(zoneLayer.values[r][c], valueList);
                    } else {
                        mask.get(zoneLayer.values[r][c]).add(values[r][c]);
                    }

                }
            }
        }

        // assign the minimum value stored in mask to corresponding zone to outLayer.
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {
                double variety = mask.get(zoneLayer.values[i][j]).size();
                outLayer.values[i][j] = variety;
            }
        }
        return outLayer;
    }

    public Layer zonalMinority(Layer zoneLayer, String outLayerName) {
        // if the two input layer's dimensions mismatch, throw an exception.
        if (zoneLayer.nRows != nRows | zoneLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    zoneLayer.nRows, zoneLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }

        Layer outLayer = new Layer(outLayerName, nCols, nRows, origin, resolution, nullValue); // initialize the outLayer
        outLayer.values  = new double[nRows][nCols]; // define the size for outLayer.values to make it assignable later
        // this.layer.values frequency count for each zone : {zoneValue1 : {value1: 0, value2: 1, ...}, ....}
        HashMap<Double, HashMap<Double, Integer>> mask = new HashMap<Double, HashMap<Double, Integer>>();

        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (values[r][c] > nullValue) {
                    if (!mask.containsKey(zoneLayer.values[r][c])) {
                        HashMap<Double, Integer> valueFrequency = new HashMap<>();
                        valueFrequency.put(values[r][c], 1);
                        mask.put(zoneLayer.values[r][c], valueFrequency);
                    } else if (!mask.get(zoneLayer.values[r][c]).containsKey(values[r][c])) {
                        mask.get(zoneLayer.values[r][c]).put(values[r][c], 1);
                    } else {
                        mask.get(zoneLayer.values[r][c]).put(values[r][c], mask.get(zoneLayer.values[r][c]).get(values[r][c]) + 1);

                    }
                }
            }
        }

        // find the value of layer.values with lowest frequency.
        HashMap<Double, Double> minorityMap = new HashMap<Double, Double>();
        for (Double zone: mask.keySet()) { // for each zone
            Double minority = Double.POSITIVE_INFINITY;
            for (Double cell: mask.get(zone).keySet()) {
                if ((mask.get(zone).get(cell) == 1) && (cell < minority))  {
                    minority = cell;
                }
            }
            minorityMap.put(zone, minority);
        }

        // assign the minimum value stored in mask to corresponding zone to outLayer.
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {

                double minority = minorityMap.get(zoneLayer.values[i][j]);
                outLayer.values[i][j] = minority;
            }
        }
        return outLayer;
    }

    public Layer zonalMajority(Layer zoneLayer, String outLayerName) {
        // if the two input layer's dimensions mismatch, throw an exception.
        if (zoneLayer.nRows != nRows | zoneLayer.nCols != nCols) {
            System.out.printf("Dimension mismatch! inLayer: (%s, %s) this layer: (%s, %s) \n",
                    zoneLayer.nRows, zoneLayer.nCols, nRows, nCols);
            throw new IndexOutOfBoundsException();
        }

        Layer outLayer = new Layer(outLayerName, nCols, nRows, origin, resolution, nullValue); // initialize the outLayer
        outLayer.values  = new double[nRows][nCols]; // define the size for outLayer.values to make it assignable later
        // this.layer.values frequency count for each zone : {zoneValue1 : {value1: 0, value2: 1, ...}, ....}
        HashMap<Double, HashMap<Double, Integer>> mask = new HashMap<Double, HashMap<Double, Integer>>();

        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (values[r][c] > nullValue) {
                    if (!mask.containsKey(zoneLayer.values[r][c])) {
                        HashMap<Double, Integer> valueFrequency = new HashMap<>();
                        valueFrequency.put(values[r][c], 1);
                        mask.put(zoneLayer.values[r][c], valueFrequency);
                    } else if (!mask.get(zoneLayer.values[r][c]).containsKey(values[r][c])) {
                        mask.get(zoneLayer.values[r][c]).put(values[r][c], 1);
                    } else {
                        mask.get(zoneLayer.values[r][c]).put(values[r][c], mask.get(zoneLayer.values[r][c]).get(values[r][c]) + 1);

                    }
                }
            }
        }

        // find the value of layer.values with lowest frequency.
        HashMap<Double, Double> minorityMap = new HashMap<Double, Double>();
        for (Double zone: mask.keySet()) { // for each zone
            Double majority = Double.POSITIVE_INFINITY;
            Integer maxFreq = 1;
            for (Double cell: mask.get(zone).keySet()) {
                if ((mask.get(zone).get(cell) >= maxFreq)) {
                    maxFreq = mask.get(zone).get(cell); // current max frequency
                    if (cell < majority) {
                        majority = cell;
                    }
                }
            }
            minorityMap.put(zone, majority);
        }

        // assign the minimum value stored in mask to corresponding zone to outLayer.
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.nCols; j++) {

                double minority = minorityMap.get(zoneLayer.values[i][j]);
                outLayer.values[i][j] = minority;
            }
        }
        return outLayer;
    }

    private ArrayList<Integer[]> getNeighborhood(int[] center, int r, boolean isSquare) {
        int rStart = Math.max(center[0] - r, 0);
        int cStart = Math.max(center[1] - r, 0);
        int rEnd = Math.min(center[0] + r + 1, nRows);
        int cEnd = Math.min(center[1] + r + 1, nCols);
        // the neighborhood is store in Integer pair in ArrayList, e.g {{x1, y1}, {x2, y2}, {x3, y3}, ....}
        ArrayList<Integer[]> l = new ArrayList<Integer[]>();
        if (isSquare) {
            for (int x = rStart; x < rEnd; x++) {
                for (int y = cStart; y < cEnd; y++) {
                    l.add(new Integer[]{x, y});
                }
            }
        } else {
            int rectlDis;
            for (int x = rStart; x < rEnd; x++) {
                for (int y = cStart; y < cEnd; y++) {
                    // Rectilinear distance
                    rectlDis = Math.abs(x - center[0]) + Math.abs(y - center[1]);
                    if (rectlDis <= r) {
                        l.add(new Integer[]{x, y});
                    }
                }
            }
        }
        return l;
    }


	private int[] returnNeighborhood(int i, int r, boolean IsSquare) {
		int neighbor;
		ArrayList<Integer> l = new ArrayList<Integer>();
		Integer neighborObj;

		int row = i/nCols; //corvert i to (row,col)
		int col = i%nCols;
		int firstRow = Math.max(row-r,0);
		int lastRow =  Math.min(row+r,nRows-1);
		int firstCol = Math.max(col-r, 0);
		int lastCol = Math.min(col+r,nCols-1);


		for(int m = firstRow ; m <=lastRow;m++) {
			for(int n = firstCol; n <= lastCol; n++) {
				// square
				if (IsSquare) {
					neighbor = m * nCols + n; //convert back
					neighborObj = new Integer(neighbor);
					l.add(neighborObj);
				}
				// circle
				if(IsSquare ==false) {
					double distance = Math.sqrt((m-row)*(m-row)+(n-col)*(n-col));
					if (distance<=r) {
						neighbor = m * nCols + n; //convert back
						neighborObj = new Integer(neighbor);
						l.add(neighborObj);
					}
				}
			}
		}
		int[] neighborArray = new int[l.size()];
		for (int j=0; j < neighborArray.length; j++)
		{
			neighborArray[j] = l.get(j).intValue();
		}
		return neighborArray;

	}

    // TODO: show cell values, or legend
    public void visualize(int scale) {
        BufferedImage image = toImage();

        JFrame appFrame = new JFrame();
        Dimension dimension = new Dimension(image.getWidth() * scale, image.getHeight() * scale);
        appFrame.setPreferredSize(dimension);
        MapPanel map = new MapPanel(image, scale);
        appFrame.add(map);

        appFrame.pack();
        appFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Layer layer = new Layer("layer", "./src/data/input/outLocalSum.txt");
        Layer zoneLayer = new Layer("zoneLayer", "./src/data/input/outFocalVariaty.txt");

        BufferedImage img = layer.toImage();

        int scale = 10;

        JFrame appFrame = new JFrame();
        Dimension dimension = new Dimension(img.getWidth() * scale, img.getHeight() * scale);
        appFrame.setPreferredSize(dimension);

        MapPanel map = new MapPanel(img, scale);
        appFrame.add(map);

        appFrame.pack();
        appFrame.setVisible(true);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
