package net.profeinformatica.eprofe.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import net.profeinformatica.eprofe.MenuPrincipal;

public class eProfDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eProf.db";

    public eProfDbHelper(Context context) {
        super(MenuPrincipal.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(eProfContract.getSQL_CREATE_AcumulativosTable());
        db.execSQL(eProfContract.getSQL_CREATE_AlumnosTable());
        db.execSQL(eProfContract.getSQL_CREATE_AsignaturasSeccionesTable());
        db.execSQL(eProfContract.getSQL_CREATE_AsignaturasTable());
        db.execSQL(eProfContract.getSQL_CREATE_CentroDocenteTable());
        db.execSQL(eProfContract.getSQL_CREATE_CentrosTable());
        db.execSQL(eProfContract.getSQL_CREATE_CentrosModalidadesTable());
        db.execSQL(eProfContract.getSQL_CREATE_DetallesAsistenciasTable());
        db.execSQL(eProfContract.getSQL_CREATE_DocentesTable());
        db.execSQL(eProfContract.getSQL_CREATE_EncabezadoAsistenciasTable());
        db.execSQL(eProfContract.getSQL_CREATE_ImagesTable());
        db.execSQL(eProfContract.getSQL_CREATE_MatriculasTable());
        db.execSQL(eProfContract.getSQL_CREATE_ModalidadesTable());
        db.execSQL(eProfContract.getSQL_CREATE_NotaAcumulativosTable());
        db.execSQL(eProfContract.getSQL_CREATE_PeriodosTable());
        db.execSQL(eProfContract.getSQL_CREATE_SeccionsTable());
        db.execSQL(eProfContract.getSQL_CREATE_TipoAcumulativos());




    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(eProfContract.getSQL_DELETE_AcumulativosTable());
        db.execSQL(eProfContract.getSQL_DELETE_AlumnosTable());
        db.execSQL(eProfContract.getSQL_DELETE_AsignaturasSeccionesTable());
        db.execSQL(eProfContract.getSQL_DELETE_AsignaturasTable());
        db.execSQL(eProfContract.getSQL_DELETE_CentroDocenteTable());
        db.execSQL(eProfContract.getSQL_DELETE_CentrosTable());
        db.execSQL(eProfContract.getSQL_DELETE_CentrosModalidadesTable());
        db.execSQL(eProfContract.getSQL_DELETE_DetallesAsistenciasTable());
        db.execSQL(eProfContract.getSQL_DELETE_DocentesTable());
        db.execSQL(eProfContract.getSQL_DELETE_EncabezadoAsistenciasTable());
        db.execSQL(eProfContract.getSQL_DELETE_ImagesTable());
        db.execSQL(eProfContract.getSQL_DELETE_MatriculasTable());
        db.execSQL(eProfContract.getSQL_DELETE_ModalidadesTable());
        db.execSQL(eProfContract.getSQL_DELETE_NotaAcumulativosTable());
        db.execSQL(eProfContract.getSQL_DELETE_PeriodosTable());
        db.execSQL(eProfContract.getSQL_DELETE_SeccionsTable());
        db.execSQL(eProfContract.getSQL_DELETE_TipoAcumulativos());
        onCreate(db);


    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
