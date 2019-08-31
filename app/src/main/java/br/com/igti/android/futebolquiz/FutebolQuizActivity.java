package br.com.igti.android.futebolquiz;

import android.animation.Animator;
import android.app.Activity;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;


public class FutebolQuizActivity extends Activity {

    private static final String TAG = "FutebolQuizActivity";
    private static final String PREF_PRIMEIRA_VEZ = "primeiraVez";
    private static final String KEY_INDICE = "indice";
    private MediaPlayer player;

    private Button mBotaoVerdade;
    private Button mBotaoFalso;
    //private TextView mConteudoCard;
    private CardView mCardView;

    private int mIndiceAtual = 0;

    private Pergunta[] mPerguntas = new Pergunta[]{
            new Pergunta(R.string.cardview_conteudo_joinville, true),
            new Pergunta(R.string.cardview_conteudo_cruzeiro, false),
            new Pergunta(R.string.cardview_conteudo_gremio, false),
            new Pergunta(R.string.cardview_conteudo_curitia, true),
            new Pergunta(R.string.cardview_conteudo_framengu, true)
    };

    private View.OnClickListener mBotaoVerdadeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checaResposta(true);
            mIndiceAtual = (mIndiceAtual + 1) % mPerguntas.length;
            atualizaQuestao(false);
            revelaCard();
        }
    };

    private View.OnClickListener mBotaoFalsoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checaResposta(false);
            mIndiceAtual = (mIndiceAtual + 1) % mPerguntas.length;
            atualizaQuestao(false);
            revelaCard();
        }
    };

    private void atualizaQuestao(boolean primeiraQuestao) {
        int questao = mPerguntas[mIndiceAtual].getQuestao();
        //mConteudoCard.setText(questao);
        //aqui devemos criar novo fragmento
        if (primeiraQuestao) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_content, createFragment(questao))
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_content, createFragment(questao))
                    .commit();
        }

    }

    private void revelaCard() {
        //criando um reveal circular
        Animator animator = ViewAnimationUtils.createCircularReveal(
                mCardView,
                0,
                0,
                0,
                (float) Math.hypot(mCardView.getWidth(), mCardView.getHeight()));

        // interpolador ease-in/ease-out.
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    private void checaResposta(boolean botaoPressionado) {
        boolean resposta = mPerguntas[mIndiceAtual].isQuestaoVerdadeira();

        int recursoRespostaId = 0;

        if (botaoPressionado == resposta) {
            recursoRespostaId = R.string.toast_acertou;
            player = MediaPlayer.create(this, R.raw.cashregister);
            player.start();

        } else {
            recursoRespostaId = R.string.toast_errou;
            player = MediaPlayer.create(this, R.raw.buzzer);
            player.start();
        }

        Toast.makeText(this, recursoRespostaId, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState");
        savedInstanceState.putInt(KEY_INDICE, mIndiceAtual);
    }

   /* private Fragment createFragment() {
        String textName = (String) getIntent().getSerializableExtra(TextViewFragment.ARG_TEXT_NAME);
        return TextViewFragment.newInstance(textName);
    }*/

    private Fragment createFragment(int questao) {
        return TextViewFragment.newInstance(questao);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futebol_quiz);

        if (savedInstanceState != null) {
            mIndiceAtual = savedInstanceState.getInt(KEY_INDICE, 0);
        }

        mBotaoVerdade = (Button) findViewById(R.id.botaoVerdade);
        mBotaoVerdade.setOnClickListener(mBotaoVerdadeListener);
        mBotaoFalso = (Button) findViewById(R.id.botaoFalso);
        mBotaoFalso.setOnClickListener(mBotaoFalsoListener);

        atualizaQuestao(true);

        mCardView = (CardView) findViewById(R.id.cardview);

        Log.d(TAG, "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_futebol_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        boolean primeiraVez = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(PREF_PRIMEIRA_VEZ, true);

        if (primeiraVez) {
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                    .edit()
                    .putBoolean(PREF_PRIMEIRA_VEZ, false)
                    .commit();
            Toast.makeText(this, "Bem-vindo ao FutebolQuiz!", Toast.LENGTH_SHORT).show();
        }
    }
}
