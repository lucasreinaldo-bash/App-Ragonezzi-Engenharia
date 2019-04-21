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

        //Instanciando Firebase
        database = FirebaseDatabase.getInstance();
        recuperarValorAtual();


        btnAtualizar = findViewById(R.id.btn_atualizar);
        btnVoltar = findViewById(R.id.btn_voltar);

        //Cast EditText Art
        artArcondicionado = findViewById(R.id.AjustesArcondicionado);
        artEnvidracamento = findViewById(R.id.AjustesEnvidracamento);
        artPedrasMarmore = findViewById(R.id.AjustesPedraMarmore);
        artNovosRevestimentos = findViewById(R.id.AjustesRevestimentos);
        artEletrica = findViewById(R.id.AjustesEletrica);
        artHidraulica = findViewById(R.id.AjustesHidraulica);
        artBox = findViewById(R.id.AjustesBox);
        artGesso = findViewById(R.id.AjustesGesso);
        artDemolicaoParede = findViewById(R.id.AjustesDemolicaoNestrutural);
        artMoveis = findViewById(R.id.AjustesMoveisPlanejados);
        artDeslocamentoGas = findViewById(R.id.AjustesDeslocamentoGas);
        artTaxa = findViewById(R.id.AjustesCrea);

       }

       public void recuperarValorAtual(){

        DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase().child("ValorServico");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("art").child("ArtTaxa").getValue() == null){
                    Toast.makeText(Ajustes.this, "Sem Internet!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String art = dataSnapshot.child("art").child("ArtArcondicionado").getValue().toString();
                    Toast.makeText(Ajustes.this, ""+art, Toast.LENGTH_SHORT).show();
                    artArcondicionado.setText(art.toString());
                    artEnvidracamento.setText(dataSnapshot.child("art").child("ArtEnvidracamento").getValue().toString());
                    artPedrasMarmore.setText(dataSnapshot.child("art").child("ArtPedrasMarmore").getValue().toString());
                    artNovosRevestimentos.setText(dataSnapshot.child("art").child("ArtNovosRevestimentos").getValue().toString());
                    artEletrica.setText(dataSnapshot.child("art").child("ArtEletrica").getValue().toString());
                    artHidraulica.setText(dataSnapshot.child("art").child("ArtHidraulica").getValue().toString());
                    artBox.setText(dataSnapshot.child("art").child("ArtBox").getValue().toString());
                    artGesso.setText(dataSnapshot.child("art").child("ArtGesso").getValue().toString());
                    artDemolicaoParede.setText(dataSnapshot.child("art").child("ArtDemolicaoParedeNaoEstrutural").getValue().toString());
                    artMoveis.setText(dataSnapshot.child("art").child("ArtMoveisPlanejados").getValue().toString());
                    artDeslocamentoGas.setText(dataSnapshot.child("art").child("ArtDeslocamentoPontoGas").getValue().toString());
                    artTaxa.setText(dataSnapshot.child("art").child("ArtTaxa").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       }
       public void atualizarValores(){

           //Instanciando as bases de dados de acordo com a categoria do servico

           DatabaseReference databaseReferenceArt = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("art");
           DatabaseReference databaseReferenceDemolicao = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("art");
           DatabaseReference databaseReferencePintura = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("art");
           DatabaseReference databaseReferenceHidraulica = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("art");
           DatabaseReference databaseReferenceRevestimento = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("art");

           //Art
           databaseReferenceArt.child("ArtArcondicionado").setValue(artArcondicionado.getText().toString());
           databaseReferenceArt.child("ArtEnvidracamento").setValue(artEnvidracamento.getText().toString());
           databaseReferenceArt.child("ArtPedrasMarmore").setValue(artPedrasMarmore.getText().toString());
           databaseReferenceArt.child("ArtNovosRevestimentos").setValue(artNovosRevestimentos.getText().toString());
           databaseReferenceArt.child("ArtEletrica").setValue(artEletrica.getText().toString());
           databaseReferenceArt.child("ArtHidraulica").setValue(artHidraulica.getText().toString());
           databaseReferenceArt.child("ArtBox").setValue(artBox.getText().toString());
           databaseReferenceArt.child("ArtGesso").setValue(artGesso.getText().toString());
           databaseReferenceArt.child("ArtDemolicaoParedeNaoEstrutural").setValue(artDemolicaoParede.getText().toString());
           databaseReferenceArt.child("ArtMoveisPlanejados").setValue(artMoveis.getText().toString());
           databaseReferenceArt.child("ArtDeslocamentoPontoGas").setValue(artDeslocamentoGas.getText().toString());
           databaseReferenceArt.child("ArtTaxa").setValue(artTaxa.getText().toString());


    }

}