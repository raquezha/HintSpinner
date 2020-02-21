/*
 * Copyright 2018 JaiselRahman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jaiselrahman.hintspinner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

@SuppressWarnings("ALL")
public class HintSpinnerAdapter<T> extends ArrayAdapter<T> {
    private static final String TAG = HintSpinnerAdapter.class.getSimpleName();
    private List<T> objects;
    private String hint;
    private Context context;
    private @LayoutRes Integer dropDownRes;
    private @LayoutRes Integer res;

    private HintSpinnerAdapter() {
        super(null, 0);
    }

    public HintSpinnerAdapter(Context context, T[] objects) {
        this(context, Arrays.asList(objects));
    }

    public HintSpinnerAdapter(Context context, List<T> objects) {
        this(context, objects, null);
    }

    public HintSpinnerAdapter(Context context, T[] objects, @StringRes int hint) {
        this(context, Arrays.asList(objects), hint);
    }

    public HintSpinnerAdapter(Context context, T[] objects, String hint) {
        this(context, Arrays.asList(objects), hint);
    }

    public HintSpinnerAdapter(Context context, List<T> objects, @StringRes int hint) {
        this(context, objects, context.getResources().getString(hint));
    }

    public HintSpinnerAdapter(
            Context context,
            @LayoutRes int dropDownRes,
            @LayoutRes int res,
            List<T> objects,String hint
    ) {
        super(context, dropDownRes, objects);
        this.context = context;
        this.objects = objects;
        this.hint = hint;
        this.dropDownRes = dropDownRes;
        this.res = res;
    }

    public HintSpinnerAdapter(Context context, List<T> objects, String hint) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.objects = objects;
        this.hint = hint;
        this.dropDownRes = android.R.layout.simple_list_item_1;
    }

    public boolean hasHint() {
        return hint != null;
    }

    @Override
    @CallSuper
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            if(dropDownRes == null)
                convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            else
                convertView = LayoutInflater.from(context)
                        .inflate(dropDownRes, parent, false);
        }

        textView = (TextView) convertView;

        if (hint != null && position == 0) {
            textView.setText(hint);
            textView.setTextColor(Color.parseColor("#adadad"));
            textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
        } else {
            textView.setText(getLabelFor(objects.get(position - 1)));
            textView.setTextColor(ContextCompat.getColor(context, android.R.color.primary_text_light));
        }
        return convertView;
    }

    @NonNull
    @Override
    @CallSuper
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView;

        if (convertView == null) {
            if(res == null)
                convertView = LayoutInflater.from(context)
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
            else
                convertView = LayoutInflater.from(context)
                        .inflate(res, parent, false);
        }

        textView = (TextView) convertView;

        if (hint != null && position == 0) {
            textView.setText(hint);
            textView.setTextColor(Color.parseColor("#adadad"));
            textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
        } else {
            textView.setText(getLabelFor(objects.get(position - 1)));
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (hint != null)
            return position != 0 && super.isEnabled(position);

        return super.isEnabled(position);
    }

    public String getLabelFor(T object) {
        return object.toString();
    }

    @Override
    public int getCount() {
        if (hint != null) {
            return super.getCount() + 1;
        } else {
            return super.getCount();
        }
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}