package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tokopedia.testproject.R;

import java.util.ArrayList;

public class SlidingImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> mImage;
    private int[] mIndex;
    private LayoutInflater inflter;

    public SlidingImageAdapter(Context context, ArrayList<Bitmap> image, int[] index) {
        mContext = context;
        mImage = image;
        inflter = (LayoutInflater.from(context));
        mIndex = index;
    }

    @Override
    public int getCount() {
        return mImage.size();
    }

    @Override
    public Object getItem(int position) {
        return (Object) mImage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.item_image_sliding_image, null);
        final ImageView image = view.findViewById(R.id.slicedimage);
        image.setImageBitmap(mImage.get(mIndex[position]));
        return view;
    }
}
