package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.tokopedia.testproject.R;

import java.util.ArrayList;
import java.util.Random;

public class SlidingImageGameActivity extends AppCompatActivity {
    public static final String X_IMAGE_URL = "x_image_url";
    public static final int GRID_NO = 4;
    private String imageUrl;
    ImageView[][] imageViews = new ImageView[4][4];
    private GridView grid;

    ArrayList<Bitmap> bitmapSliced = new ArrayList<>();
    SlidingImageAdapter imageAdapter;
    private static int[] imageIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    public static Intent getIntent(Context context, String imageUrl) {
        Intent intent = new Intent(context, SlidingImageGameActivity.class);
        intent.putExtra(X_IMAGE_URL, imageUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            imageIndex = savedInstanceState.getIntArray("imageIndex");
        } else {
            int start = 15;
            for (int irand = 0; irand < 2; irand++) {
                int random = randomMove(start);
                imageIndex[start] = imageIndex[random];
                imageIndex[random] = 15;
                start = random;
            }
        }
        imageUrl = getIntent().getStringExtra(X_IMAGE_URL);
        setContentView(R.layout.activity_sliding_image_game);
        grid = findViewById(R.id.gridLayout);

        Solution.sliceTo4x4(getApplicationContext(), new Solution.onSuccessLoadBitmap() {
            @Override
            public void onSliceSuccess(ArrayList<Bitmap> bitmapList) {
                bitmapSliced.clear();
                bitmapSliced.addAll(bitmapList);
            }

            @Override
            public void onSliceFailed(Throwable throwable) {
                Toast.makeText(SlidingImageGameActivity.this,
                        throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, imageUrl);

        imageAdapter = new SlidingImageAdapter(getApplicationContext(), bitmapSliced, imageIndex);
        grid.setAdapter(imageAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int nullindex = lookAround(position);
                if (nullindex == position) { //Cant move
                    Toast.makeText(getApplicationContext(), "Unavailable move!", Toast.LENGTH_SHORT).show();
                } else {
                    int tempindex = imageIndex[nullindex];
                    imageIndex[nullindex] = imageIndex[position];
                    imageIndex[position] = tempindex;
                }
                imageAdapter.notifyDataSetChanged();
                isSolved();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putIntArray("imageIndex", imageIndex);
    }

    private int lookAround(int index) {
        int row = index / 4;
        int col = index % 4;
        int[] moveable = new int[]{index, index, index, index}; //left, up, right, bottom
        if (col - 1 >= 0) { //left
            moveable[0] = (row * 4) + (col - 1);
            if (bitmapSliced.get(imageIndex[moveable[0]]) == null) {
                return moveable[0];
            }
        }
        if (row - 1 >= 0) { //up
            moveable[1] = ((row - 1) * 4) + col;
            if (bitmapSliced.get(imageIndex[moveable[1]]) == null) {
                return moveable[1];
            }
        }
        if (col + 1 <= 3) { //right
            moveable[2] = (row * 4) + (col + 1);
            if (bitmapSliced.get(imageIndex[moveable[2]]) == null) {
                return moveable[2];
            }
        }
        if (row + 1 <= 3) { //bottom
            moveable[3] = ((row + 1) * 4) + col;
            if (bitmapSliced.get(imageIndex[moveable[3]]) == null) {
                return moveable[3];
            }
        }
        return index;
    }

    private int randomMove(int index) {
        int row = index / 4;
        int col = index % 4;
        int[] moveable = new int[]{index, index, index, index};
        if (col - 1 >= 0) { //left
            moveable[0] = (row * 4) + (col - 1);
        }
        if (row - 1 >= 0) { //up
            moveable[1] = ((row - 1) * 4) + col;
        }
        if (col + 1 <= 3) { //right
            moveable[2] = (row * 4) + (col + 1);
        }
        if (row + 1 <= 3) { //bottom
            moveable[3] = ((row + 1) * 4) + col;
        }
        int randindex = index;
        while (randindex == index) {
            randindex = moveable[new Random().nextInt(3)];
        }
        return randindex;
    }

    private void isSolved() {
        boolean solved = true;
        for (int i = 0; i < (GRID_NO * GRID_NO); i++) {
            if (imageIndex[i] != i) {
                solved = false;
                break;
            }
        }
        if (solved) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                    .setTitle("Congratulation!")
                    .setMessage("You solved this puzzle.");
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
