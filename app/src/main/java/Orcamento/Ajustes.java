package Orcamento;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Firebase.ConfiguracaoFirebase;
import androidx.appcompat.app.AppCompatActivity;
import vostore.apporcamentoragonezi.R;


/**
 * Essa classe é utilizada como tela inicial. Possui uma animação e faz transição após 3 segundos para a MainActivity
 */
public class Ajustes extends AppCompatActivity {



    ImageView top;
    Animation fromlogo;
    LinearLayout ajustesART;
    EditText artArcondicionado,artEnvidracamento,artPedrasMarmore,artNovosRevestimentos,artEletrica,artHidraulica,artBox,artGesso,artDemolicaoParede,artMoveis,artDeslocamentoGas,artTaxa;




    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    Button btnVoltar,btnAtualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ajustes);

        //Fazendo cast e instanciando a animação
        //comecar = findViewById(R.id.idComecar);
        //Instanciando Firebase


       }


}