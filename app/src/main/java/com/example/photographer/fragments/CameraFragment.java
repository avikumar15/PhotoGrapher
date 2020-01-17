package com.example.photographer.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.photographer.R;
import com.example.photographer.activities.GraphActivity;
import com.example.photographer.adapters.ImageRecyclerViewAdapter;
import com.example.photographer.apiservice.ApiService;
import com.example.photographer.apiservice.RetrofitInstance;
import com.example.photographer.model.MathpixRequest;
import com.example.photographer.model.MathpixResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {
    //private final String LATEX_TEST = "\\frac{-b\\pm\\sqrt{b^2-4ac}}{2a}";

    private TextureView textureView;
    CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    private String cameraId;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;

    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int GALLERY_IMAGE_FETCH = 211;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private FloatingActionButton capture;
    private Button galleryButton;
    private RecyclerView galleryImagesRV;
    private ImageRecyclerViewAdapter adapter;
    private List<String> pathList;


    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        capture = view.findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        galleryButton = view.findViewById(R.id.openGallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_IMAGE_FETCH);
            }
        });
        galleryImagesRV = view.findViewById(R.id.galleryImagesRV);
        pathList = getAllImagePaths();
        adapter = new ImageRecyclerViewAdapter(pathList, getActivity());
        galleryImagesRV.setHasFixedSize(true);
        galleryImagesRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        galleryImagesRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        textureView = view.findViewById(R.id.cameraView);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);
        return view;
    }

    private void galleryImageFetched(Uri imgURI) {
        try {
            InputStream imgStream = getActivity().getContentResolver().openInputStream(imgURI);
            Bitmap bmp = BitmapFactory.decodeStream(imgStream);
            String encodedImage = encodeImage(bmp);
            sendRequest(encodedImage);
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(), "Error selecting image", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private List<String> getAllImagePaths() {
        List<String> paths = new ArrayList<>();
        String[] projection = new String[] {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String BUCKET_GROUP_BY = "1) GROUP BY 1,(1";
        String BUCKET_ORDER_BY = MediaStore.Images.Media.DATE_TAKEN + " DESC";
        Cursor cur = getActivity().getContentResolver().query(images,
                projection,
                BUCKET_GROUP_BY,
                null,
                BUCKET_ORDER_BY
        );

        if (cur != null && cur.moveToFirst()) {
            String path;
            int pathColumn = cur.getColumnIndex(MediaStore.MediaColumns.DATA);
            do {
                path = cur.getString(pathColumn);
                paths.add(path);
            } while (cur.moveToNext());
            cur.close();
        }
        return paths;
    }

    private void captureImage() {
        Bitmap bmp = textureView.getBitmap();
        String encodedImage = encodeImage(bmp);
        sendRequest(encodedImage);
    }

    private String encodeImage(Bitmap bmp) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] byteImage = outputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteImage, Base64.DEFAULT);
        return encodedImage;
    }

    private void sendRequest(String encodedImage) {
        //Toast.makeText(getActivity(), "Sending request", Toast.LENGTH_LONG).show();
        MathpixRequest req = new MathpixRequest();
        req.setSrc(encodedImage);
        ApiService service = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        Call<MathpixResponse> call = service.getLatex(getString(R.string.app_id),
                getString(R.string.app_key), req);
        call.enqueue(new Callback<MathpixResponse>() {
            @Override
            public void onResponse(Call<MathpixResponse> call, Response<MathpixResponse> response) {
                String latex = response.body().getLatex_normal();
                if(latex != null) {
                    Intent intent = new Intent(getActivity(), GraphActivity.class);
                    intent.putExtra("latex", latex);
                    startActivity(intent);
                }
                Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MathpixResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (cameraDevice == null)
                        return;
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(getActivity(), "Changed", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if (cameraDevice == null)
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            //Check realtime permission if run higher API 23
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "You can't use camera without permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();
        if (textureView.isAvailable())
            openCamera();
        else
            textureView.setSurfaceTextureListener(textureListener);
    }

    @Override
    public void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cameraCaptureSessions.close();
        cameraDevice.close();
        cameraDevice = null;
    }


    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_IMAGE_FETCH && resultCode == RESULT_OK) {
            galleryImageFetched(data.getData());
        }
    }
}
