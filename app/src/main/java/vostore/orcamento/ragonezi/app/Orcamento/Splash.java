package vostore.orcamento.ragonezi.app.Orcamento;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;



import androidx.appcompat.app.AppCompatActivity;
import vostore.apporcamentoragonezi.R;


/**
 * Essa classe é utilizada como tela inicial. Possui uma animação e faz transição após 3 segundos para a MainActivity
 */
public class Splash extends AppCompatActivity implements Runnable {



    ImageView top;
    Animation fromlogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //Fazendo cast e instanciando a animação
        //comecar = findViewById(R.id.idComecar);

        //Determinando o tempo de 3 segundos para entrar na próxima activity
        fromlogo = AnimationUtils.loadAnimation(this, R.anim.fromlogo);
        top = findViewById(R.id.logosplash);
        top.setAnimation(fromlogo);
        Handler handler = new Handler();
        handler.postDelayed(this, 3000);
    }

    //Usando intent no método run
    public void run(){
        startActivity(new Intent(this, login.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}