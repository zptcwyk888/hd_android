package net.huadong.pd.android.override;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by xu on 2015/10/29.
 */
public class OpenScroListView extends ListView {
        public OpenScroListView(Context context) {
            super(context);
        }
        public OpenScroListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        public OpenScroListView(Context context, AttributeSet attrs,
                                int defStyle) {
            super(context, attrs, defStyle);
        }
        @Override
        /**
         * 重写该方法，达到使ListView适应ScrollView的效果
         */
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }
}
