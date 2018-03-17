package com.test.musicfinderpro.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.test.musicfinderpro.R;

public class SlideShowAdapter extends PagerAdapter {

    private Context context;
    LayoutInflater inflater;

    public int[] images = {
            R.drawable.edsheeran,
            R.drawable.camila,
            R.drawable.bruno,
            R.drawable.drake,
            R.drawable.kidink,
            R.drawable.avril,
            R.drawable.simpleplan1,
            R.drawable.justin,
            R.drawable.taylor
    };


    public SlideShowAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {

        return images.length;

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view==(LinearLayout)object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.slideshow_layout,container,false);

        ImageView img = view.findViewById(R.id.imageView_id);

        Glide.with(context).load(images[position]).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make( view ,"Image " + ( position + 1 ) , Snackbar.LENGTH_LONG ).show();
            }
        });

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout)object);

    }


}
