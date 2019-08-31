package br.com.igti.android.futebolquiz;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

public class TextViewFragment extends Fragment {

    public static final String ARG_TEXT_NAME = "text_name";
    public static final String ARG_TEXT_ID = "text_id";
    private int text;

    // uma implementação burra do Callback que não fará nada
    // apenas para quando o fragmento não estiver anexado na atividade
    /*private static TextChangeCallback sDummyCallbacks = new TextChangeCallback() {
        @Override
        public void onTextChangeCallback(String text) {

        }
    };

    // o callback do fragmento que notifica é notificado sobre o clique na
    private TextChangeCallback textChangeCallback = sDummyCallbacks;*/

    /*public static TextViewFragment newInstance(String textName) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEXT_NAME, textName);
        TextViewFragment fragment = new TextViewFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    public static TextViewFragment newInstance(int textId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TEXT_ID, textId);
        TextViewFragment fragment = new TextViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments().containsKey(ARG_TEXT_NAME)) {
            String textName = (String) getArguments().getSerializable(ARG_TEXT_NAME);
            text = "Teste";
        }*/
        if (getArguments().containsKey(ARG_TEXT_ID)) {
            text = (int) getArguments().getSerializable(ARG_TEXT_ID);
        }
        //text = R.string.cardview_conteudo_cruzeiro;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.text_view_fragment, container, false);
        if (text > 0) {
            ((TextView) rootView.findViewById(R.id.cardviewConteudo)).setText(text);
        }
        return rootView;
    }
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Quando o fragmento é anexado na atividade
        // garantimos que a atividade implementa
        // os Callbacks para os cliques
        if (!(activity instanceof TextChangeCallback)) {
            throw new IllegalStateException("A atividade tem que implementar os Callbacks");
        }
        textChangeCallback = (TextChangeCallback) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Se o fragmento é desanexado
        // resetamos os callbacks
        textChangeCallback = sDummyCallbacks;
    }


    public interface TextChangeCallback {

        public void onTextChangeCallback(String text);
    }*/
}
