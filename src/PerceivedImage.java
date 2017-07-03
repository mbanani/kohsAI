/**
 * 	A wrapper class for the perceived image.
 *	For now, image will just be a numerical array
 *
 *	@author  	Mohamed El Banani
 *	@date	 	25 July 2015
 *	@version 	1.0
 *	@since 		1.0
 */

public class PerceivedImage {

    public int[][] 	p;
    public int		gazeX;
    public int 		gazeY;
    //band aid fix for now (perceived object, puzzleGaze)
    public String	perceivedObject;
    public Location	puzzleLoc;
    /**
     *	Constructor Class
     *	@param currentTable The array representation of the table
     */
    public PerceivedImage(int[][] currentTable, AttentionModule attention) {
	if(attention == null) {
	    gazeX = 0;
	    gazeY = 0;
	    System.out.println("Warning in PerceivedImage(): attention is ignored.");
	} else {
	    gazeX = attention.getX();
	    gazeY = attention.getY();
	}
	p = currentTable;
	perceivedObject = "t";
    }

}
