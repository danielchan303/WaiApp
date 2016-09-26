package tk.gengwai.waiapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Feed extends AppCompatActivity {

    int eatFoodCount = 0;

    private final class feedFood implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(null, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private class eatFood implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState(); // Declare the drag object
            ImageView dropTarget = (ImageView) v; // Declare the drop object
            TextView waiHungry = (TextView) findViewById(R.id.wai_hungry);
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    dropTarget.setImageResource(R.drawable.mouth);
                    break;
                case DragEvent.ACTION_DROP:
                    if (view.getId() == R.id.hot_pepper || view.getId() == R.id.grape) {
                        view.setVisibility(View.INVISIBLE); // set drag object invisible if it is favourite food
                        dropTarget.setImageResource(R.drawable.yummy_emoji);
                        eatFoodCount ++;
                        waiHungry.setTextSize(20);
                        if (eatFoodCount == 1) {
                            waiHungry.setText(R.string.correct_feed_first);
                        } else {
                            waiHungry.setText(R.string.correct_feed_second);
                            dropTarget.setImageResource(R.drawable.wai);
                        }
                    } else {
                        dropTarget.setImageResource(R.drawable.angry_emoji);
                        waiHungry.setText(R.string.wrong_feed);
                        waiHungry.setTextSize(30);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult() == false) {
                        waiHungry.setText("餵好d啦！");
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    ImageView hotPepper, grape, poo, wai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Set hotPepper Drag Action
        hotPepper = (ImageView) findViewById(R.id.hot_pepper);
        hotPepper.setOnTouchListener(new feedFood());

        //Set grape Drag Action
        grape = (ImageView) findViewById(R.id.grape);
        grape.setOnTouchListener(new feedFood());

        // Set poo Drag Action
        poo = (ImageView) findViewById(R.id.poo);
        poo.setOnTouchListener(new feedFood());

        // Set Drop Action
        wai = (ImageView) findViewById(R.id.wai);
        wai.setOnDragListener(new eatFood());
    }

}
