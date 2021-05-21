package com.example.cupid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cupid.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditImages extends AppCompatActivity {

    ImageButton edit_img_float_btn;
    List<String> imagesEncodedList;
    String imageEncoded;
    ImageView image1, image2, image3, image4, image5, image6;

    ArrayList<String> img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_images);
        edit_img_float_btn = findViewById(R.id.edit_img_float_btn);
        image1 = findViewById(R.id.image_one);
        image2 = findViewById(R.id.image_two);
        image3 = findViewById(R.id.image_three);
        image4 = findViewById(R.id.image_four);
        image5 = findViewById(R.id.image_five);
        image6 = findViewById(R.id.image_six);


        final int[] size = {0};
        img_url = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");
        String url = "http://api.betterdate.info/endpoints/gallery.php?userId=" + userId;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        loadImages();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                size[0] = response.length();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);


        edit_img_float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (size[0] >= 6) {
                    Toast.makeText(getApplicationContext(), "You have uploaded maximum images", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                }


            }
        });

    }

    private void loadImages() {
        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userid", "");

        String url = "http://api.betterdate.info/endpoints/gallery.php?userId=" + userId;
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String img_name = "http://api.betterdate.info/gallery/" + object.getString("galleryImg");
                        // Toast.makeText(getApplicationContext(),String.valueOf(response.length()) ,Toast.LENGTH_LONG).show();
                        img_url.add(img_name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                int sizes = img_url.size();
                Log.d("*******", String.valueOf(sizes));

                if (sizes == 1) {
                    Picasso.get().load(img_url.get(0)).placeholder(R.drawable.download).into(image1);
                }
                if (sizes == 2) {
                    Picasso.get().load(img_url.get(0)).placeholder(R.drawable.download).into(image1);
                    Picasso.get().load(img_url.get(1)).placeholder(R.drawable.download).into(image2);
                }
                if (sizes == 3) {
                    Picasso.get().load(img_url.get(0)).placeholder(R.drawable.download).into(image1);
                    Picasso.get().load(img_url.get(1)).placeholder(R.drawable.download).into(image2);
                    Picasso.get().load(img_url.get(2)).placeholder(R.drawable.download).into(image3);
                }
                if (sizes == 4)
                    Picasso.get().load(img_url.get(0)).placeholder(R.drawable.download).into(image1);
                Picasso.get().load(img_url.get(1)).placeholder(R.drawable.download).into(image2);
                Picasso.get().load(img_url.get(2)).placeholder(R.drawable.download).into(image3);
               // Picasso.get().load(img_url.get(3)).placeholder(R.drawable.download).into(image4);

                if (sizes == 5) {
                    Picasso.get().load(img_url.get(0)).placeholder(R.drawable.download).into(image1);
                    Picasso.get().load(img_url.get(1)).placeholder(R.drawable.download).into(image2);
                    Picasso.get().load(img_url.get(2)).placeholder(R.drawable.download).into(image3);
                    Picasso.get().load(img_url.get(3)).placeholder(R.drawable.download).into(image4);
                    Picasso.get().load(img_url.get(4)).placeholder(R.drawable.download).into(image5);
                }
                if (sizes == 6) {
                    Picasso.get().load(img_url.get(0)).placeholder(R.drawable.download).into(image6);
                    Picasso.get().load(img_url.get(1)).placeholder(R.drawable.download).into(image1);
                    Picasso.get().load(img_url.get(2)).placeholder(R.drawable.download).into(image2);
                    Picasso.get().load(img_url.get(3)).placeholder(R.drawable.download).into(image3);
                    Picasso.get().load(img_url.get(4)).placeholder(R.drawable.download).into(image4);
                    Picasso.get().load(img_url.get(5)).placeholder(R.drawable.download).into(image5);
                }
                // restart();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);

                    uploadsingleImage(bitmap);
                    // restart();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        ArrayList<Bitmap> bitmapArrayList = new ArrayList<Bitmap>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                            testing_image.setImageURI(mArrayUri.get(0));
                            // Toast.makeText(getApplicationContext(), String.valueOf(mArrayUri.get(0)), Toast.LENGTH_SHORT).show();
                            bitmapArrayList.add(bitmap);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        //  Toast.makeText(getApplicationContext(),String.valueOf(imagesEncodedList.size()), Toast.LENGTH_LONG).show();
                        uploadimages(bitmapArrayList);
                        // restart();

                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void restart() {
//        Intent i = new Intent(EditImages.this, EditImages.class);
//        startActivity(i);
//        finish();
//    }

    private void uploadsingleImage(Bitmap bitmap) {
        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        String userID = sharedPreferences.getString("userid", "");

        String url = "http://api.betterdate.info/endpoints/gallery.php";

        ProgressDialog dialog = ProgressDialog.show(EditImages.this, "",
                "Uploading Image. Please wait...", true);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            // Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_LONG).show();
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                             dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      //  loadImages();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userID);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("galleryImg", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }

        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadimages(List<Bitmap> bitmaps) {
        String url = "http://api.betterdate.info/endpoints/gallery.php";
        SharedPreferences sharedPreferences = getSharedPreferences("Questoins", MODE_PRIVATE);
        String userID = sharedPreferences.getString("userid", "");


        for (int i = 0; i < bitmaps.size(); i++) {
            ProgressDialog dialog = ProgressDialog.show(EditImages.this, "",
                    "Uploading Image. Please wait...", true);
            Bitmap temp_bitmap = bitmaps.get(i);
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                dialog.dismiss();
                                // Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_LONG).show();
                                JSONObject obj = new JSONObject(new String(response.data));

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                           // loadImages();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "error recieved", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("userId", userID);
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("galleryImg", new DataPart(imagename + ".png", getFileDataFromDrawable(temp_bitmap)));
                    return params;
                }

            };
            Volley.newRequestQueue(this).add(volleyMultipartRequest);

        }


//        int socketTimeout = 500000;//30 seconds - change to what you want
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        multipartRequest.setRetryPolicy(policy);
//        AppController.getInstance().addToRequestQueue(multipartRequest);
    }


}