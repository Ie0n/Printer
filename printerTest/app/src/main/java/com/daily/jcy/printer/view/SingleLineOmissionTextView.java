package com.daily.jcy.printer.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import static android.text.TextUtils.TruncateAt.END;

/**
 * Created by Administrator on 2017/12/22.
 */

public class SingleLineOmissionTextView extends TextView {
    public SingleLineOmissionTextView(Context context) {
        this(context,null);
    }

    public SingleLineOmissionTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SingleLineOmissionTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SingleLineOmissionTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setMaxLines(1);
        setEllipsize(END);
        setSingleLine(true);

    }





}
