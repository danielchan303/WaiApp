package tk.gengwai.waiapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class LoveActivity extends AppCompatActivity {

    private Context context;
    private Camera camera;
    private SurfaceView preview;
    private TextView instruction2View;
    private ImageView responseImg;
    private CameraSource.PictureCallback pictureCallback;

    private static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            c.setDisplayOrientation(90);
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private void releaseCamera(){
        if (camera != null){
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    class CameraPreview implements SurfaceHolder.Callback {
        private Camera mCamera;

        // Constructor
        public CameraPreview(Camera c) {
            mCamera = c;
        }

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d("camera", "Error settting camera preview: " + e.getMessage());
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i("LoveActivity.java", "Surface destroyed");
            // empty. Take care of releasing the Camera preview in your activity.
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.
            if (holder.getSurface() == null) {
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
                // ignore: tried to stop a non-existent preview
            }

            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();

            } catch (Exception e) {
                Log.d("camera", "Error starting camera preview: " + e.getMessage());
            }
        }
    }

    class PictureCallback implements Camera.PictureCallback {
        private BarcodeDetector barcodeDetector;

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String barCodeContent;

            // Display the captured photo and put image into Frame
            Bitmap myBitmap = new BitmapFactory().decodeByteArray(data, 0, data.length);
            Frame capturePic = new Frame.Builder().setBitmap(myBitmap).build();

            // The barcodeDetector
            if (barcodeDetector == null) {
                Log.i("LoveActivity.java", "detector is null");
                barcodeDetector =
                        new BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.QR_CODE).build();
            }

            // Obtain the Result
            SparseArray<Barcode> barcode = barcodeDetector.detect(capturePic);

            if(barcode.size() > 0) // Find Barcode
            {
                barCodeContent = barcode.valueAt(0).rawValue;
                if (barCodeContent.equals("烈焰紅唇")) {
                    instruction2View.setText("威威好有被愛的感覺");
                    camera.stopPreview();
                    preview.setVisibility(View.GONE);
                    responseImg.setVisibility(View.VISIBLE);
                    return;
                } else {
                    instruction2View.setText(barCodeContent + R.string.wrong_barcode_response);
                }
            }
            else {
                instruction2View.setText(R.string.no_barcode_response);
            }
            camera.startPreview();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        context = getApplicationContext();
        camera = getCameraInstance();
        preview = (SurfaceView) findViewById(R.id.camera_preview);
        instruction2View = (TextView) findViewById(R.id.instruction2_View);
        responseImg = (ImageView) findViewById(R.id.responseImg);
        final Camera.PictureCallback pictureCallback = new PictureCallback();

        preview.getHolder().addCallback(new CameraPreview(camera));
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null, null, pictureCallback);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }

}
