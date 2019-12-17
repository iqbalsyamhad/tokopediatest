package com.tokopedia.testproject.problems.news.view.slider;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tokopedia.testproject.R;
import com.tokopedia.testproject.problems.news.model.Headline;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SliderAdapter extends PagerAdapter {


    private List<Headline> headlines;
    private LayoutInflater inflater;
    private Context context;


    public SliderAdapter(Context context, List<Headline> headlines) {
        this.context = context;
        this.headlines = headlines;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return headlines.size();
    }

    @NotNull
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slider_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.banner);
        final TextView textView = imageLayout.findViewById(R.id.tvSliderTitle);
        Glide.with(imageLayout).load(headlines.get(position).getUrlToImage()).into(imageView);
        textView.setText(headlines.get(position).getTitle());
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
