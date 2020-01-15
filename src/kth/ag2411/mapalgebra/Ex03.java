package kth.ag2411.mapalgebra;

/**
 * Created by hui on 2019/11/15.
 */
public class Ex03 {
    public static void main(String[] args) {
   	
        String operation = args[0];
        Layer inLayer1 = new Layer("raster1", args[1]);
        Layer outLayer;

        if (operation.equals("localSum")) { //operation, layer1Path, savePath, scale, layer2Path
        	if (args.length == 5 ) {
                Layer inLayer2 = new Layer("raster2", args[4]);
                outLayer = inLayer1.localSum(inLayer2, "outFocalSum");
                outLayer.save(args[2]);
                outLayer.visualize(Integer.parseInt(args[3]));
        	} else {
        		System.out.println("Arguments required: \noperation(localSum or focalVariety or zonalMinimum), "
        				+ "\nlayer2Path, savePath, scale, layer2Path");
        	}

            
        } else if (operation.equals("focalVariety")) { // focalVariety ./src/data/vegetation.txt ./src/data/outZonalMinimum.txt 10 1 true
        	if (args.length == 6 ) {  
	            outLayer = inLayer1.focalVariety(Integer.parseInt(args[4]),
	                    Boolean.parseBoolean(args[5]), "outFocalVariety");
	            outLayer.save(args[2]);
	            outLayer.visualize(Integer.parseInt(args[3]));
        	} else {
        		System.out.println("Arguments required: \noperation(localSum or focalVariety or zonalMinimum), "
        				+ "\nlayer2Path, savePath, scale, radius, isSquare");
        	}
            
        } else if (operation.equals("zonalMinimum")) { // zonalMinimum ./src/data/elevation.txt ./src/data/outZonalMinimum.txt 10  ./src/data/vegetation.txt outZ
        	if (args.length == 6 ) { 
	        	Layer inLayer2 = new Layer("raster2", args[4]);
	            outLayer = inLayer1.zonalMinimum(inLayer2, args[5]);
	            outLayer.save(args[2]);
	            outLayer.visualize(Integer.parseInt(args[3]));
        	} else {
        		System.out.println("Arguments required: \noperation(localSum or focalVariety or zonalMinimum), "
        				+ "\nlayer2Path, savePath, scale, layer2Path, outLayerName");
        	}
            
        } else {
            System.out.println(operation + "is not currently available.");
        }
    }
}
