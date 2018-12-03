package com.example.gonzalo.examen3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class HomeAct extends AppCompatActivity {

    ViewFlipper _viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int images[] = {R.drawable.calakmul, R.drawable.aguablanca, R.drawable.parquenacionalnevadodetoluca,
                        R.drawable.xilitla, R.drawable.cascadaspetrificadas, R.drawable.cabopulmo, R.drawable.selva_lacandona,
                        R.drawable.mariposa_monarca};

        _viewFlipper = findViewById(R.id.viewFlipper);

        for (int i = 0; i<images.length; i++) {
            flipperImages(images[i]);
        }
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        _viewFlipper.addView(imageView);
        _viewFlipper.setFlipInterval(5000);  //5 segunditos
        _viewFlipper.setAutoStart(true);

        //Animación shida
        _viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        _viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }
}
