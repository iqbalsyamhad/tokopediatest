package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.content.Context;
import android.graphics.Bitmap;

import com.tokopedia.testproject.R;
import com.tokopedia.testproject.UtilKt;

import java.util.ArrayList;

public class Solution {

    public interface onSuccessLoadBitmap {
        void onSliceSuccess(ArrayList<Bitmap> bitmapList);

        void onSliceFailed(Throwable throwable);
    }

    private static ArrayList<Bitmap> bitmapList = new ArrayList<>();

    public static void sliceTo4x4(final Context context, final onSuccessLoadBitmap onSuccessLoadBitmap, final String image) {
        // TODO, download the image, crop, then sliced to 15 Bitmap (4x4 Bitmap). ignore the last Bitmap
        // below is stub, replace with your implementation!

        bitmapList.clear();
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample11));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample12));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample13));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample14));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample21));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample22));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample23));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample24));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample31));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample32));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample33));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample34));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample41));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample42));
        bitmapList.add(UtilKt.toBitmap(context, R.drawable.sample43));
        bitmapList.add(null);

        onSuccessLoadBitmap.onSliceSuccess(bitmapList);
    }
}
