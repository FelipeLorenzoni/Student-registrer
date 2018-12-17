package br.com.felorenzoni.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.felorenzoni.agenda.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO (Context context){
        super(context,"Agenda",null,1);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE Alunos(" +
                "id INTEGER PRIMARY KEY " +
                ", nome TEXT NOT NULL" +
                ", endereco TEXT " +
                ", telefone TEXT" +
                ", site TEXT" +
                ", nota REAL) ";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova){
        String sql = "DROP TABLE IF EXISTS Alunos;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Aluno aluno){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues novoaluno = new ContentValues();

        novoaluno.put("nome",aluno.getNome());
        novoaluno.put("endereco",aluno.getEndereco());
        novoaluno.put("site",aluno.getSite());
        novoaluno.put("telefone",aluno.getTelefone());
        novoaluno.put("nota",aluno.getNota());

        db.insert("Alunos",null,novoaluno);

    }

}
