package br.com.felorenzoni.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.felorenzoni.agenda.dao.AlunoDAO;
import br.com.felorenzoni.agenda.modelo.Aluno;


public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        this.helper = new FormularioHelper(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.PegaAluno();

                Toast.makeText(this, "Aluno " + aluno.getNome() + " salvo com Sucesso!"
                        , Toast.LENGTH_SHORT).show();

                AlunoDAO dao = new AlunoDAO(this);
                dao.insere(aluno);
                dao.close();

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
