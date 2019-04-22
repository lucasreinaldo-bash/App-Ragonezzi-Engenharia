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
    LinearLayout linearART,linearRevestimento,linearHidraulica,linearDemolicao,linearPintura;
    Button btnArt,btnHidraulica,btnPintura,btnRevestimento,btnDemolicao;
    EditText artArcondicionado,artEnvidracamento,artPedrasMarmore,artNovosRevestimentos,artEletrica,artHidraulica,artBox,artGesso,artDemolicaoParede,artMoveis,artDeslocamentoGas,artTaxa;
    EditText demolicaoRasgar4x2,demolicaoRasgar4x4,demolicaoRemoverAlvenaria,demolicaoRemoverGesso,demolicaoRemoverHidraulica,demolicaoRemoverPia,demolicaoRemoverPiso,demolicaoRemoverRevestimentoParede,demolicaoRemoverTanque,demolicaoRemoverVao,demolicaoRemoverVasoSanitario;
    EditText hidraulicaChuveiro,hidraulicaCriacaoAgua,hidraulicaCriacaoEsgoto,hidraulicaInstalarVasoSanitario,hidraulicaRalo10cm,hidraulicaRalo15cm,hidraulicaRaloLinear,hidraulicaRegistroAcabamento,hidraulicaTorneiraEletrica,hidraulicaTorneiraMonocomando,hidraulicaTorneiraSimples,hidraulicaValvulaSifao;
    EditText pinturaApartamento,pinturaEfeitoDecorativo,pinturaJanela,pinturaPorta,pinturaReparoGesso;
    EditText revestimentoImpermeabilizante,revestimentoPastilhaVidro,revestimentoCriarAlvenaria,revestimentoCriarContraPiso,revestimentoPorcelanatoMaior,revestimentoPorcelanatomMenor,revestimentoRevestimento3D;
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


        //Cast dos Lineares
        linearART = findViewById(R.id.linear_ajustes_art);
        linearDemolicao = findViewById(R.id.linear_ajustes_demolicao);
        linearHidraulica = findViewById(R.id.linear_ajustes_hidraulica);
        linearRevestimento = findViewById(R.id.linear_ajustes_revestimento);
        linearPintura = findViewById(R.id.linear_ajustes_pintura);
        //Cast dos botoes
        btnArt = findViewById(R.id.btnART);
        btnHidraulica = findViewById(R.id.btnHidraulica);
        btnPintura = findViewById(R.id.btnPintura);
        btnDemolicao = findViewById(R.id.btnDemolicao);
        btnRevestimento = findViewById(R.id.btnRevestimento);
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


        //Demolicao

        demolicaoRasgar4x2 = findViewById(R.id.AjustesDemolicaoRasgarCaixinha4x2);
        demolicaoRasgar4x4 = findViewById(R.id.AjustesDemolicaoRasgarCaixinha4x4);
        demolicaoRemoverGesso = findViewById(R.id.AjustesDemolicaoRemoverGesso);
        demolicaoRemoverHidraulica = findViewById(R.id.AjustesDemolicaoRemoverHidraulica);
        demolicaoRemoverAlvenaria = findViewById(R.id.AjustesDemolicaoRemoverAlvenaria);
        demolicaoRemoverPia = findViewById(R.id.AjustesDemolicaoRemoverPia);
        demolicaoRemoverPiso = findViewById(R.id.AjustesDemolicaoRemoverPiso);
        demolicaoRemoverRevestimentoParede = findViewById(R.id.AjustesDemolicaoRemoverRevestimentoParede);
        demolicaoRemoverTanque = findViewById(R.id.AjustesDemolicaoRemoverTanque);
        demolicaoRemoverVao = findViewById(R.id.AjustesDemolicaoRemoverVao);
        demolicaoRemoverVasoSanitario = findViewById(R.id.AjustesDemolicaoRemoverVasoSanitario);

        //Hidraulica

        hidraulicaChuveiro = findViewById(R.id.AjustesHidraulicaChuveiro);
        hidraulicaCriacaoAgua = findViewById(R.id.AjustesHidraulicaAgua);
        hidraulicaCriacaoEsgoto = findViewById(R.id.AjustesHidraulicaEsgoto);
        hidraulicaInstalarVasoSanitario = findViewById(R.id.AjustesHidraulicaInstalarVasoSanitario);
        hidraulicaRalo10cm = findViewById(R.id.AjustesHidraulicaRalo10cm);
        hidraulicaRalo15cm = findViewById(R.id.AjustesHidraulicaRalo15cm);
        hidraulicaRaloLinear = findViewById(R.id.AjustesHidraulicaRaloLinear);
        hidraulicaRegistroAcabamento = findViewById(R.id.AjustesHidraulicaRegistroAcabamento);
        hidraulicaTorneiraEletrica = findViewById(R.id.AjustesHidraulicaTorneiraEletrica);
        hidraulicaTorneiraMonocomando = findViewById(R.id.AjustesHidraulicaTorneiraMonocomando);
        hidraulicaTorneiraSimples = findViewById(R.id.AjustesHidraulicaTorneiraSimples);
        hidraulicaValvulaSifao = findViewById(R.id.AjustesHidraulicaValvulaSifao);

        //Pintura
        pinturaApartamento = findViewById(R.id.AjustesPinturaApartamento);
        pinturaEfeitoDecorativo = findViewById(R.id.AjustesPinturaEfeitoDecorativo);
        pinturaJanela = findViewById(R.id.AjustesPinturaJanela);
        pinturaPorta = findViewById(R.id.AjustesPinturaPorta);
        pinturaReparoGesso = findViewById(R.id.AjustesPinturaReparoGesso);

//        Revestimento

        revestimentoImpermeabilizante = findViewById(R.id.AjustesRevestimentoImpermeabilizante);
        revestimentoCriarAlvenaria = findViewById(R.id.AjustesRevestimentoAlvenaria);
        revestimentoCriarContraPiso = findViewById(R.id.AjustesRevestimentoCriarContraPiso);
        revestimentoPastilhaVidro = findViewById(R.id.AjustesRevestimentoPastilhaVidro);
        revestimentoPorcelanatoMaior = findViewById(R.id.AjustesRevestimentoPorcelanatoMaior);
        revestimentoPorcelanatomMenor = findViewById(R.id.AjustesRevestimentoPorcelanatoMenor);
        revestimentoRevestimento3D = findViewById(R.id.AjustesRevestimentoRevestimento3D);
        btnArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearART();
            }
        });
        btnRevestimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearRevestimento();
            }
        });
        btnHidraulica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearHidraulica();
            }
        });
        btnPintura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearPintura();
            }
        });
        btnDemolicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearDemolicao();
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarValores();
            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ajustes.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    //Metodos

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

                    //art

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

//                    demolicao

                    demolicaoRasgar4x2.setText(dataSnapshot.child("demolicao").child("RasgarCaixinha4x2").getValue().toString());
                    demolicaoRasgar4x4.setText(dataSnapshot.child("demolicao").child("RasgarCaixinha4x4").getValue().toString());
                    demolicaoRemoverAlvenaria.setText(dataSnapshot.child("demolicao").child("RemoverAlvenaria").getValue().toString());
                    demolicaoRemoverGesso.setText(dataSnapshot.child("demolicao").child("RemoverGesso").getValue().toString());
                    demolicaoRemoverHidraulica.setText(dataSnapshot.child("demolicao").child("RemoverHidraulica").getValue().toString());
                    demolicaoRemoverPia.setText(dataSnapshot.child("demolicao").child("RemoverPia").getValue().toString());
                    demolicaoRemoverPiso.setText(dataSnapshot.child("demolicao").child("RemoverPiso").getValue().toString());
                    demolicaoRemoverRevestimentoParede.setText(dataSnapshot.child("demolicao").child("RemoverRevestimentoParede").getValue().toString());
                    demolicaoRemoverTanque.setText(dataSnapshot.child("demolicao").child("RemoverTanque").getValue().toString());
                    demolicaoRemoverVao.setText(dataSnapshot.child("demolicao").child("RemoverVao").getValue().toString());
                    demolicaoRemoverVasoSanitario.setText(dataSnapshot.child("demolicao").child("RemoverVasoSanitario").getValue().toString());

                    //Hidraulica
                    hidraulicaChuveiro.setText(dataSnapshot.child("hidraulica").child("Chuveiro").getValue().toString());
                    hidraulicaCriacaoAgua.setText(dataSnapshot.child("hidraulica").child("CriacaoAgua").getValue().toString());
                    hidraulicaCriacaoEsgoto.setText(dataSnapshot.child("hidraulica").child("CriacaoEsgoto").getValue().toString());
                    hidraulicaInstalarVasoSanitario.setText(dataSnapshot.child("hidraulica").child("InstalarVasoSanitario").getValue().toString());
                    hidraulicaRalo10cm.setText(dataSnapshot.child("hidraulica").child("Ralo10cm").getValue().toString());
                    hidraulicaRalo15cm.setText(dataSnapshot.child("hidraulica").child("Ralo15cm").getValue().toString());
                    hidraulicaRaloLinear.setText(dataSnapshot.child("hidraulica").child("RaloLinear").getValue().toString());
                    hidraulicaRegistroAcabamento.setText(dataSnapshot.child("hidraulica").child("RegistroAcabamento").getValue().toString());
                    hidraulicaTorneiraEletrica.setText(dataSnapshot.child("hidraulica").child("TorneiraEletrica").getValue().toString());
                    hidraulicaTorneiraMonocomando.setText(dataSnapshot.child("hidraulica").child("TorneiraMonocomando").getValue().toString());
                    hidraulicaTorneiraSimples.setText(dataSnapshot.child("hidraulica").child("TorneiraSimples").getValue().toString());
                    hidraulicaValvulaSifao.setText(dataSnapshot.child("hidraulica").child("ValvulaSifao").getValue().toString());

                    //pintura
                    pinturaApartamento.setText(dataSnapshot.child("pintura").child("PinturaApartamento").getValue().toString());
                    pinturaEfeitoDecorativo.setText(dataSnapshot.child("pintura").child("PinturaEfeitoDecorativo").getValue().toString());
                    pinturaJanela.setText(dataSnapshot.child("pintura").child("PinturaJanela").getValue().toString());
                    pinturaPorta.setText(dataSnapshot.child("pintura").child("PinturaPorta").getValue().toString());
                    pinturaReparoGesso.setText(dataSnapshot.child("pintura").child("PinturaReparoGesso").getValue().toString());

                    //Revestimento
                    revestimentoImpermeabilizante.setText(dataSnapshot.child("revestimento").child("AplicarImpermeabilizante").getValue().toString());
                    revestimentoCriarAlvenaria.setText(dataSnapshot.child("revestimento").child("CriarAlvenaria").getValue().toString());
                    revestimentoCriarContraPiso.setText(dataSnapshot.child("revestimento").child("CriarContraPiso").getValue().toString());
                    revestimentoPastilhaVidro.setText(dataSnapshot.child("revestimento").child("PastilhaVidro").getValue().toString());
                    revestimentoPorcelanatoMaior.setText(dataSnapshot.child("revestimento").child("PorcelanatoMaior").getValue().toString());
                    revestimentoPorcelanatomMenor.setText(dataSnapshot.child("revestimento").child("PorcelanatoMenor").getValue().toString());
                    revestimentoRevestimento3D.setText(dataSnapshot.child("revestimento").child("Revestimento3D").getValue().toString());


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
           DatabaseReference databaseReferenceDemolicao = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("demolicao");
           DatabaseReference databaseReferencePintura = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("pintura");
           DatabaseReference databaseReferenceHidraulica = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("hidraulica");
           DatabaseReference databaseReferenceRevestimento = ConfiguracaoFirebase.getFirebase().child("ValorServico").child("revestimento");

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

           //Demolicao
           databaseReferenceDemolicao.child("RasgarCaixinha4x2").setValue(demolicaoRasgar4x2.getText().toString());
           databaseReferenceDemolicao.child("RasgarCaixinha4x4").setValue(demolicaoRasgar4x4.getText().toString());
           databaseReferenceDemolicao.child("RemoverAlvenaria").setValue(demolicaoRemoverAlvenaria.getText().toString());
           databaseReferenceDemolicao.child("RemoverGesso").setValue(demolicaoRemoverGesso.getText().toString());
           databaseReferenceDemolicao.child("RemoverHidraulica").setValue(demolicaoRemoverHidraulica.getText().toString());
           databaseReferenceDemolicao.child("RemoverPia").setValue(demolicaoRemoverPia.getText().toString());
           databaseReferenceDemolicao.child("RemoverPiso").setValue(demolicaoRemoverPiso.getText().toString());
           databaseReferenceDemolicao.child("RemoverRevestimentoParede").setValue(demolicaoRemoverRevestimentoParede.getText().toString());
           databaseReferenceDemolicao.child("RemoverTanque").setValue(demolicaoRemoverTanque.getText().toString());
           databaseReferenceDemolicao.child("RemoverVao").setValue(demolicaoRemoverVao.getText().toString());
           databaseReferenceDemolicao.child("RemoverVasoSanitario").setValue(demolicaoRemoverVasoSanitario.getText().toString());

           //Hidraulica

           databaseReferenceHidraulica.child("Chuveiro").setValue(hidraulicaChuveiro.getText().toString());
           databaseReferenceHidraulica.child("CriacaoAgua").setValue(hidraulicaCriacaoAgua.getText().toString());
           databaseReferenceHidraulica.child("CriacaoEsgoto").setValue(hidraulicaCriacaoEsgoto.getText().toString());
           databaseReferenceHidraulica.child("InstalarVasoSanitario").setValue(hidraulicaInstalarVasoSanitario.getText().toString());
           databaseReferenceHidraulica.child("Ralo10cm").setValue(hidraulicaRalo10cm.getText().toString());
           databaseReferenceHidraulica.child("Ralo15cm").setValue(hidraulicaRalo15cm.getText().toString());
           databaseReferenceHidraulica.child("RaloLinear").setValue(hidraulicaRaloLinear.getText().toString());
           databaseReferenceHidraulica.child("RegistroAcabamento").setValue(hidraulicaRegistroAcabamento.getText().toString());
           databaseReferenceHidraulica.child("TorneiraEletrica").setValue(hidraulicaTorneiraEletrica.getText().toString());
           databaseReferenceHidraulica.child("TorneiraMonocomando").setValue(hidraulicaTorneiraMonocomando.getText().toString());
           databaseReferenceHidraulica.child("TorneiraSimples").setValue(hidraulicaTorneiraSimples.getText().toString());
           databaseReferenceHidraulica.child("ValvulaSifao").setValue(hidraulicaValvulaSifao.getText().toString());


           //Pintura
           databaseReferencePintura.child("PinturaApartamento").setValue(pinturaApartamento.getText().toString());
           databaseReferencePintura.child("PinturaEfeitoDecorativo").setValue(pinturaEfeitoDecorativo.getText().toString());
           databaseReferencePintura.child("PinturaJanela").setValue(pinturaJanela.getText().toString());
           databaseReferencePintura.child("PinturaPorta").setValue(pinturaPorta.getText().toString());
           databaseReferencePintura.child("PinturaReparoGesso").setValue(pinturaReparoGesso.getText().toString());


           //Revestimento

           databaseReferenceRevestimento.child("AplicarImpermeabilizante").setValue(revestimentoImpermeabilizante.getText().toString());
           databaseReferenceRevestimento.child("CriarAlvenaria").setValue(revestimentoCriarAlvenaria.getText().toString());
           databaseReferenceRevestimento.child("CriarContraPiso").setValue(revestimentoCriarContraPiso.getText().toString());
           databaseReferenceRevestimento.child("PastilhaVidro").setValue(revestimentoPastilhaVidro.getText().toString());
           databaseReferenceRevestimento.child("PorcelanatoMaior").setValue(revestimentoPorcelanatoMaior.getText().toString());
           databaseReferenceRevestimento.child("PorcelanatoMenor").setValue(revestimentoPorcelanatomMenor.getText().toString());
           databaseReferenceRevestimento.child("Revestimento3D").setValue(revestimentoRevestimento3D.getText().toString());


       }
    public void linearHidraulica() {
        if (linearHidraulica.getVisibility() == View.VISIBLE)
            linearHidraulica.setVisibility(View.GONE);
        else {
            linearHidraulica.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearART.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
        }
    }

    public void linearRevestimento() {
        if (linearRevestimento.getVisibility() == View.VISIBLE)
            linearRevestimento.setVisibility(View.GONE);
        else {
            linearRevestimento.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearART.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
        }
    }

    public void linearART() {
        if (linearART.getVisibility() == View.VISIBLE)
            linearART.setVisibility(View.GONE);
        else {
            linearART.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);

        }
    }

    public void linearDemolicao() {
        if (linearDemolicao.getVisibility() == View.VISIBLE)
            linearDemolicao.setVisibility(View.GONE);
        else {
            linearDemolicao.setVisibility(View.VISIBLE);
            linearART.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);
            linearPintura.setVisibility(View.GONE);
        }
    }

    public void linearPintura() {
        if (linearPintura.getVisibility() == View.VISIBLE)
            linearPintura.setVisibility(View.GONE);
        else {
            linearPintura.setVisibility(View.VISIBLE);
            linearDemolicao.setVisibility(View.GONE);
            linearART.setVisibility(View.GONE);
            linearRevestimento.setVisibility(View.GONE);
            linearHidraulica.setVisibility(View.GONE);
        }
    }
}