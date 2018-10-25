/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paringer.medisafe.view;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.paringer.medisafe.R;
import com.paringer.medisafe.view.svg.SvgDecoder;
import com.paringer.medisafe.view.svg.SvgDrawableTranscoder;
import com.paringer.medisafe.view.svg.SvgSoftwareLayerSetter;

import java.io.InputStream;

@BindingMethods({
        @BindingMethod(type = android.widget.ImageView.class, attribute = "android:tint", method = "setImageTintList"),
        @BindingMethod(type = android.widget.ImageView.class, attribute = "android:tintMode", method = "setImageTintMode"),
})
public class ImageViewBindingAdapter {

    @BindingAdapter("android:uri")
    public static void setImageUri(ImageView view, String imageUri) {
        if (view == null || imageUri == null) return;
        if (imageUri == null) {
            view.setImageURI(null);
        } else {
//            view.setImageURI(Uri.parse(imageUri));

            Glide.with(view.getContext()).load(imageUri).dontAnimate().placeholder(android.R.drawable.ic_dialog_dialer).into(view);
            GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(view.getContext())
                .using(Glide.buildStreamModelLoader(Uri.class, view.getContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(android.R.drawable.ic_dialog_dialer)
                .error(android.R.drawable.ic_dialog_dialer)
//                .animate(android.R.anim.fade_in)
                .dontAnimate()
                .listener(new SvgSoftwareLayerSetter<Uri>());
//            Uri uri = Uri.parse("http://upload.wikimedia.org/wikipedia/commons/e/e8/Svg_example3.svg");
            Uri uri = Uri.parse(imageUri);
//            if(uri == null) return;
            requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(view);
        }
    }
    @BindingAdapter("android:uri")
    public static void setImageUri(ImageView view, Uri imageUri) {
        view.setImageURI(imageUri);
    }
    @BindingAdapter("android:uri")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }
}