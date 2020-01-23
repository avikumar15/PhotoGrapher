package com.example.photographer.adapters;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

    private List<Uri> pathList;
    private Context context;

    public ImageRecyclerViewAdapter(List<Uri> pathList, Context context) {
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
        Glide.with(context).load(pathList.get(position)).into(holder.galleryImageView);
        holder.galleryImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
                    String path = pathList.get(getAdapterPosition()).toString();
                    Bitmap bmp = BitmapFactory.decodeFile(path);
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
