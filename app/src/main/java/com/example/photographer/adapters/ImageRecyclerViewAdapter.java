package com.example.photographer.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photographer.R;
import com.example.photographer.activities.GraphActivity;
import com.example.photographer.apiservice.ApiService;
import com.example.photographer.apiservice.RetrofitInstance;
import com.example.photographer.model.MathpixRequest;
import com.example.photographer.model.MathpixResponse;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muskan Hussain on 14-01-2020
 */
public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder> {

    private List<String> pathList;
    private Context context;

    public ImageRecyclerViewAdapter(List<String> pathList, Context context) {
        this.pathList = pathList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageRecyclerViewAdapter.ViewHolder holder, int position) {
        Bitmap bmp = getThumbnail(context.getContentResolver(), pathList.get(position));
        holder.galleryImageView.setImageBitmap(bmp);
        holder.galleryImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public Bitmap getThumbnail(ContentResolver cr, String path){

        Cursor ca = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.MediaColumns._ID }, MediaStore.MediaColumns.DATA + "=?", new String[] {path}, null);
        if (ca != null && ca.moveToFirst()) {
            int id = ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
            ca.close();
            return MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null );
        }

        ca.close();
        return null;

    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView galleryImageView;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            galleryImageView = itemView.findViewById(R.id.galleryImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String path = pathList.get(getAdapterPosition());
                    Bitmap bmp = BitmapFactory.decodeFile(path);
                    //Toast.makeText(context, "Sending request for image path " + path, Toast.LENGTH_LONG).show();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] byteImage = outputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(byteImage, Base64.DEFAULT);
                    MathpixRequest req = new MathpixRequest();
                    req.setSrc(encodedImage);
                    ApiService service = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
                    Call<MathpixResponse> call = service.getLatex(context.getString(R.string.app_id),
                            context.getString(R.string.app_key), req);
                    call.enqueue(new Callback<MathpixResponse>() {
                        @Override
                        public void onResponse(Call<MathpixResponse> call, Response<MathpixResponse> response) {
                            String latex = response.body().getLatex_normal();
                            if(latex != null) {
                                Intent intent = new Intent(context, GraphActivity.class);
                                intent.putExtra("latex", latex);
                                context.startActivity(intent);
                            }
                            Toast.makeText(context, response.body().getError(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<MathpixResponse> call, Throwable t) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }
}
