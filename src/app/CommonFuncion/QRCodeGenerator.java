package app.CommonFuncion;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.nio.file.Paths;

public class QRCodeGenerator {

    public static void generatorQRCode(String data, String path) throws Exception{
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);

        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
    }

    public static void main(String[] args) throws Exception{
        String data = "101";
        String path = "D:\\Code\\Java\\QRCode.jpg";
        generatorQRCode(data, path);
    }
}
