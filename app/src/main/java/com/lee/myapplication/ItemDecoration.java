package com.lee.myapplication;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        Resources resources = parent.getContext().getResources();
        outRect.left = resources.getDimensionPixelOffset(R.dimen.dimen_12px);
        outRect.right = resources.getDimensionPixelOffset(R.dimen.dimen_12px);

    }
}
