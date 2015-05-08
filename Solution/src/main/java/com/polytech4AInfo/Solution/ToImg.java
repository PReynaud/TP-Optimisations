package com.polytech4AInfo.Solution;
import com.polytech4AInfo.Shape.PlacedShape;
import com.polytech4AInfo.Shape.Sheet;

import java.io.IOException;

        import javax.imageio.ImageIO;
        import java.awt.*;
        import java.awt.geom.Rectangle2D;
        import java.awt.image.BufferedImage;
        import java.io.File;
import java.util.ArrayList;

public class ToImg {
    /**
     * Font size for legend.
     */
    public static int mFontSize = 30;
    /**
     * Draw border on images or not.
     */
    public static boolean DRAW_BORDER = true;
    /**
     * In grey of in colors.
     */
    public static boolean ALL_IN_GRAY = true;
    /**
     * Pattern Legend or Not.
     */
    public static boolean PATTERN_LEGEND = true;
    /**
     * Background color.
     */
    public static Color BACKGROUND = Color.WHITE;
    /**
     * Greyscale minimum.
     */
    public static int GREY_MIN = 20;
    /**
     * Greyscale maximum.
     */
    public static int GREY_MAX = 200;
    /**
     * Saturation for colored mod.
     */
    public static float SATURATION = 0.9f;
    /**
     * Brightness for colored mod.
     */
    public static float BRIGHTNESS = 0.7f;

    /**
     * Void Constructor.
     */
    public ToImg() {
    }

    /**
     * Get a grey color.
     *
     * @param pId N� Image in Pattern.
     * @param pNb Number of Images in a Pattern.
     * @return Color
     */
    public static Color getGrayColor(int pId, int pNb) {
        int gray_val;

        if (pNb == 1) gray_val = 125;
        else {
            gray_val = GREY_MIN + (((GREY_MAX - GREY_MIN) * pId) / (pNb - 1));
        }
        return new Color(gray_val, gray_val, gray_val, 250);
    }

    /**
     * Get a color.
     *
     * @param pId N� Image in Pattern.
     * @param pNb Number of Images in a Pattern.
     * @return Color
     */
    public static Color getColoredColor(int pId, int pNb) {
        float color_val;

        if (pNb == 1) color_val = 0;
        else {
            color_val = (float) pId / (pNb - 1);
        }
        return Color.getHSBColor(color_val, SATURATION, BRIGHTNESS);
    }

    /**
     * Get a color or a gray
     *
     * @param pId N� Image in Pattern.
     * @param pNb Number of Images in a Pattern.
     * @return Color color in grey or not.
     */
    public static Color getColor(int pId, int pNb) {
        if (ALL_IN_GRAY) {
            return getGrayColor(pId, pNb);
        } else {
            return getColoredColor(pId, pNb);
        }
    }

    /**
     * Text color in function of the background.
     *
     * @param pBackgroundColor Color in RGB
     * @return Color Color of the background (Black or White)
     */
    public static Color getTextColor(Color pBackgroundColor) {
        int red = pBackgroundColor.getRed();
        int green = pBackgroundColor.getGreen();
        int blue = pBackgroundColor.getBlue();

        if ((red * 299 + green * 587 + blue * 114) / 1000 < 125) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }


    /**
     * Draw a rectangle on the buffered image img
     *
     * @param img    Image onto print Rectangle.
     * @param x      horizontal origin position.
     * @param y      vertical origin position.
     * @param width  horizontal size.
     * @param height vertical size
     * @param label  label of Rectangle.
     * @param color  background color of rectangle.
     */
    public static void drawRectangle(BufferedImage img, int x, int y, int width, int height, String label, Color color) {

        Graphics2D graph = img.createGraphics();
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graph.setColor(color);

        graph.fill(new Rectangle(x, y, width, height));

        if (DRAW_BORDER) {
            graph.setPaint(Color.red);
            int border_size = 2;
            for (int i = 0; i < border_size; i++) {
                // top
                graph.drawLine(x, y + i, x + width - 1, y + i);
                // bottom
                graph.drawLine(x, y + (height - 1) - i, x + (width - 1), y + (height - 1) - i);
                // right
                graph.drawLine(x + (width - 1) - i, y, x + (width - 1) - i, y + (height - 1));
                // left
                graph.drawLine(x + i, y, x + i, y + (height - 1));
            }

        }

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, mFontSize);
        graph.setFont(font);

        Rectangle2D textDimensions = graph.getFontMetrics().getStringBounds(label, graph);
        int stringLen = (int) textDimensions.getWidth();
        int start_x = width / 2 - stringLen / 2;

        graph.setPaint(getTextColor(color));
        graph.drawString(label, x + start_x, y + (height / 2) + 30);

        graph.dispose();
    }

    public void savePattern(String contextId, Sheet sheet, String fileName){
        double coeff = (float)(800.0 / sheet.getLength());
        BufferedImage img = new BufferedImage(
                (int)(sheet.getLength() * coeff),
                (int)(sheet.getBreadth() * coeff),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(BACKGROUND);
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        int i = 0;
        for (PlacedShape placedBox : sheet.getContent()) {
            if (placedBox.isLying()) {
                drawRectangle(img, (int)(placedBox.getPositionx() * coeff), (int)(placedBox.getPositiony() * coeff),
                        (int)(placedBox.getLength() * coeff), (int)(placedBox.getBreadth() * coeff), String.valueOf(i),
                        getColor(i, sheet.getContent().size()));
            } else {
                drawRectangle(img, (int)(placedBox.getPositionx() * coeff), (int)(placedBox.getPositiony() * coeff),
                        (int) (placedBox.getBreadth() * coeff), (int)(placedBox.getLength() * coeff), String.valueOf(i),
                        getColor(i, sheet.getContent().size()));
            }
            i++;
        }
        String filePath = ProgramMain.PATH_TO_IMAGES + contextId + fileName;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
                file.createNewFile();
            }
            ImageIO.write(img, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save Method.
     *
     * @param contextId Name of the file.
     * @param solution  Final Solution.
     */
    public void save(String contextId, Solution solution) {
        deleteDirectory(ProgramMain.PATH_TO_IMAGES + contextId);
        Pattern[] patterns = solution.getPatterns();
        for (int i=0; i<patterns.length; i++){
            savePattern(contextId,patterns[i].savePatternInFile(),"Solution" + i + ".png");
        }
    }

    /**
     * Delete all files in the indicated directory
     * @param emplacement
     */
    static public void deleteDirectory(String emplacement) {
        File path = new File(emplacement);
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(path+"\\"+files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
    }
}
