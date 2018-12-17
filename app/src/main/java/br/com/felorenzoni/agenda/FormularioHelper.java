package br.com.felorenzoni.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.felorenzoni.agenda.modelo.Aluno;

public class FormularioHelper {

    private EditText campoNome;
    private EditText campoEndereco;
    private EditText campoTelefone;
    private EditText campoSite;
    private RatingBar campoNota;

    public FormularioHelper (FormularioActivity activity){

        this.campoNome = activity.findViewById(R.id.formulario_nome);
        this.campoEndereco = activity.findViewById(R.id.formulario_endereco);
        this.campoTelefone = activity.findViewById(R.id.formulario_telefone);
        this.campoSite = activity.findViewById(R.id.formulario_site);
        this.campoNota = activity.findViewById(R.id.formulario_ratingbar);

    }

    public Aluno PegaAluno(){

        Aluno aluno = new Aluno();

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota((double) campoNota.getProgress());

        return aluno;
    }
}
