package net.profeinformatica.eprofe.dataBase;

import android.provider.BaseColumns;

public final class eProfContract {
    private eProfContract(){

    }




    public static String getSQL_CREATE_AcumulativosTable() {
        return SQL_CREATE_AcumulativosTable;
    }

    public static String getSQL_DELETE_AcumulativosTable() {
        return SQL_DELETE_AcumulativosTable;
    }




    public static String getSQL_CREATE_AlumnosTable() {
        return SQL_CREATE_AlumnosTable;
    }

    public static String getSQL_DELETE_AlumnosTable() {
        return SQL_DELETE_AlumnosTable;
    }




    public static String getSQL_CREATE_AsignaturasTable() {
        return SQL_CREATE_AsignaturasTable;
    }

    public static String getSQL_DELETE_AsignaturasTable() {
        return SQL_DELETE_AsignaturasTable;
    }



    public static String getSQL_CREATE_AsignaturasSeccionesTable() {
        return SQL_CREATE_AsignaturasSeccionesTable;
    }

    public static String getSQL_DELETE_AsignaturasSeccionesTable() {
        return SQL_DELETE_AsignaturasSeccionesTable;
    }



    public static String getSQL_CREATE_CentroDocenteTable() {
        return SQL_CREATE_CentroDocenteTable;
    }

    public static String getSQL_DELETE_CentroDocenteTable() {
        return SQL_DELETE_CentroDocenteTable;
    }




    public static String getSQL_CREATE_CentrosTable() {
        return SQL_CREATE_CentrosTable;
    }

    public static String getSQL_DELETE_CentrosTable() {
        return SQL_DELETE_CentrosTable;
    }



    public static String getSQL_CREATE_CentrosModalidadesTable() {
        return SQL_CREATE_CentrosModalidadesTable;
    }

    public static String getSQL_DELETE_CentrosModalidadesTable() {
        return SQL_DELETE_CentrosModalidadesTable;
    }




    public static String getSQL_CREATE_DetallesAsistenciasTable() {
        return SQL_CREATE_DetallesAsistenciasTable;
    }

    public static String getSQL_DELETE_DetallesAsistenciasTable() {
        return SQL_DELETE_DetallesAsistenciasTable;
    }




    public static String getSQL_CREATE_DocentesTable() {
        return SQL_CREATE_DocentesTable;
    }

    public static String getSQL_DELETE_DocentesTable() {
        return SQL_DELETE_DocentesTable;
    }




    public static String getSQL_CREATE_EncabezadoAsistenciasTable() {
        return SQL_CREATE_EncabezadoAsistenciasTable;
    }

    public static String getSQL_DELETE_EncabezadoAsistenciasTable() {
        return SQL_DELETE_EncabezadoAsistenciasTable;
    }




    public static String getSQL_CREATE_ImagesTable() {
        return SQL_CREATE_ImagesTable;
    }

    public static String getSQL_DELETE_ImagesTable() {
        return SQL_DELETE_ImagesTable;
    }





    public static String getSQL_CREATE_MatriculasTable() {
        return SQL_CREATE_MatriculasTable;
    }

    public static String getSQL_DELETE_MatriculasTable() {
        return SQL_DELETE_MatriculasTable;
    }




    public static String getSQL_CREATE_ModalidadesTable() {
        return SQL_CREATE_ModalidadesTable;
    }

    public static String getSQL_DELETE_ModalidadesTable() {
        return SQL_DELETE_ModalidadesTable;
    }




    public static String getSQL_CREATE_NotaAcumulativosTable() {
        return SQL_CREATE_NotaAcumulativosTable;
    }

    public static String getSQL_DELETE_NotaAcumulativosTable() {
        return SQL_DELETE_NotaAcumulativosTable;
    }




    public static String getSQL_CREATE_PeriodosTable() {
        return SQL_CREATE_PeriodosTable;
    }

    public static String getSQL_DELETE_PeriodosTable() {
        return SQL_DELETE_PeriodosTable;
    }



    public static String getSQL_CREATE_SeccionsTable() {
        return SQL_CREATE_SeccionsTable;
    }

    public static String getSQL_DELETE_SeccionsTable() {
        return SQL_DELETE_SeccionsTable;
    }



    public static String getSQL_CREATE_TipoAcumulativos() {
        return SQL_CREATE_TipoAcumulativos;
    }

    public static String getSQL_DELETE_TipoAcumulativos() {
        return SQL_DELETE_TipoAcumulativos;
    }



    public static class AcumulativosTable implements BaseColumns{
        public static final String TABLE_NAME="acumulativos";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_SECCIONID="seccion_id";
        public static final String COLUMN_NAME_DESCRIPCION="descripcion";
        public static final String COLUMN_NAME_TIPOACUMULATICOID="tipo_acumulativo_id";
        public static final String COLUMN_NAME_FEHA="fecha";
        public static final String COLUMN_NAME_PARCIAL="parcial";
        public static final String COLUMN_NAME_VALOR="valor";
        public static final String COLUMN_NAME_ASIGNAURAID="asignatura_id";
        public static final String COLUMN_NAME_MOVILID="movil_id";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";
        public static final String COLUMN_NAME_SINCRONIZACIONSERVER="sincronizar_servidor";
    }

    private static final String SQL_CREATE_AcumulativosTable =
            "CREATE TABLE " + AcumulativosTable.TABLE_NAME + " (" +
                    AcumulativosTable.COLUMN_NAME_MOVILID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AcumulativosTable.COLUMN_NAME_ID + " INTEGER," +
                    AcumulativosTable.COLUMN_NAME_SECCIONID + " INTEGER," +
                    AcumulativosTable.COLUMN_NAME_DESCRIPCION + " TEXT," +
                    AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID + " INTEGER," +
                    AcumulativosTable.COLUMN_NAME_FEHA + " TEXT," +
                    AcumulativosTable.COLUMN_NAME_PARCIAL + " TEXT," +
                    AcumulativosTable.COLUMN_NAME_VALOR + " REAL," +
                    AcumulativosTable.COLUMN_NAME_ASIGNAURAID + " INTEGER,"+
                    AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER + " INTEGER,"+
                    AcumulativosTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    AcumulativosTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_AcumulativosTable =
            "DROP TABLE IF EXISTS " + AcumulativosTable.TABLE_NAME;






    public static class AlumnosTable implements BaseColumns{
        public static final String TABLE_NAME="alumnos";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_RNE="rne";
        public static final String COLUMN_NAME_USERNAME="username";
        public static final String COLUMN_NAME_NOMBRE="nombre";
        public static final String COLUMN_NAME_APELLIDO="apellido";
        public static final String COLUMN_NAME_GENERO="genero";
        public static final String COLUMN_NAME_TELEFONO="telefono";
        public static final String COLUMN_NAME_EMAIL="email";
        public static final String COLUMN_NAME_PASSWORD="password";
        public static final String COLUMN_NAME_SINCRONIZACIONSERVER="sincronizar_servidor";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";
        public static final String COLUMN_NAME_REMEMBERTOKEN="remember_token";

    }

    private static final String SQL_CREATE_AlumnosTable =
            "CREATE TABLE " + AlumnosTable.TABLE_NAME + " (" +
                    AlumnosTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    AlumnosTable.COLUMN_NAME_RNE + " TEXT," +
                    AlumnosTable.COLUMN_NAME_USERNAME + " TEXT," +
                    AlumnosTable.COLUMN_NAME_NOMBRE + " TEXT," +
                    AlumnosTable.COLUMN_NAME_APELLIDO + " TEXT," +
                    AlumnosTable.COLUMN_NAME_GENERO + " INTEGER," +
                    AlumnosTable.COLUMN_NAME_TELEFONO + " TEXT," +
                    AlumnosTable.COLUMN_NAME_EMAIL + " TEXT," +
                    AlumnosTable.COLUMN_NAME_PASSWORD + " TEXT,"+
                    AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER + " INTEGER," +
                    AlumnosTable.COLUMN_NAME_REMEMBERTOKEN + " TEXT," +
                    AlumnosTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    AlumnosTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_AlumnosTable =
            "DROP TABLE IF EXISTS " + AlumnosTable.TABLE_NAME;






    public static class AsignaturasTable implements BaseColumns{
        public static final String TABLE_NAME="asignaturas";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_ALIAS="alias";
        public static final String COLUMN_NAME_NOMBRE="nombre";
        public static final String COLUMN_NAME_TIPO="tipo";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";

    }

    private static final String SQL_CREATE_AsignaturasTable =
            "CREATE TABLE " + AsignaturasTable.TABLE_NAME + " (" +
                    AsignaturasTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    AsignaturasTable.COLUMN_NAME_ALIAS + " TEXT," +
                    AsignaturasTable.COLUMN_NAME_NOMBRE + " TEXT," +
                    AsignaturasTable.COLUMN_NAME_TIPO + " TEXT," +
                    AsignaturasTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    AsignaturasTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_AsignaturasTable =
            "DROP TABLE IF EXISTS " + AsignaturasTable.TABLE_NAME;



    public static class AsignaturasSeccionesTable implements BaseColumns{
        public static final String TABLE_NAME="asignaturas_secciones";
        public static final String COLUMN_NAME_ASIGNATURAID="asignatura_id";
        public static final String COLUMN_NAME_SECCIONID="seccion_id";
        public static final String COLUMN_NAME_DOCENTEID="docente_id";

    }

    private static final String SQL_CREATE_AsignaturasSeccionesTable =
            "CREATE TABLE " + AsignaturasSeccionesTable.TABLE_NAME + " (" +
                    AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID + " INTEGER," +
                    AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID + " INTEGER," +
                    AsignaturasSeccionesTable.COLUMN_NAME_DOCENTEID + " INTEGER)";

    private static final String SQL_DELETE_AsignaturasSeccionesTable =
            "DROP TABLE IF EXISTS " + AsignaturasSeccionesTable.TABLE_NAME;





    public static class CentroDocenteTable implements BaseColumns{
        public static final String TABLE_NAME="centro_docente";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_DOCENTEID="docente_id";
        public static final String COLUMN_NAME_CENTROID="centro_id";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";

    }

    private static final String SQL_CREATE_CentroDocenteTable =
            "CREATE TABLE " + CentroDocenteTable.TABLE_NAME + " (" +
                    CentroDocenteTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    CentroDocenteTable.COLUMN_NAME_DOCENTEID + " INTEGER," +
                    CentroDocenteTable.COLUMN_NAME_CENTROID + " INTEGER," +
                    CentroDocenteTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    CentroDocenteTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_CentroDocenteTable =
            "DROP TABLE IF EXISTS " + CentroDocenteTable.TABLE_NAME;





    public static class CentrosTable implements BaseColumns{
        public static final String TABLE_NAME="centros";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_NOMBRE="nombre";
        public static final String COLUMN_NAME_CODIGOSACE="codigo_sace";
        public static final String COLUMN_NAME_DIRECCION="direccion";
        public static final String COLUMN_NAME_TELEFONO="telefono";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";
        public static final String COLUMN_NAME_REMEMBERTOKEN="remember_token";

    }
    private static final String SQL_CREATE_CentrosTable =
            "CREATE TABLE " + CentrosTable.TABLE_NAME + " (" +
                    CentrosTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    CentrosTable.COLUMN_NAME_NOMBRE + " TEXT," +
                    CentrosTable.COLUMN_NAME_CODIGOSACE + " TEXT," +
                    CentrosTable.COLUMN_NAME_DIRECCION + " TEXT," +
                    CentrosTable.COLUMN_NAME_TELEFONO + " TEXT," +
                    CentrosTable.COLUMN_NAME_REMEMBERTOKEN + " TEXT," +
                    CentrosTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    CentrosTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_CentrosTable =
            "DROP TABLE IF EXISTS " + CentrosTable.TABLE_NAME;





    public static class CentrosModalidadesTable implements BaseColumns{
        public static final String TABLE_NAME="centros_modalidades";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_CENTROID="centro_id";
        public static final String COLUMN_NAME_MODALIDADID="modalidad_id";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";

    }
    private static final String SQL_CREATE_CentrosModalidadesTable =
            "CREATE TABLE " + CentrosModalidadesTable.TABLE_NAME + " (" +
                    CentrosModalidadesTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    CentrosModalidadesTable.COLUMN_NAME_CENTROID + " INTEGER," +
                    CentrosModalidadesTable.COLUMN_NAME_MODALIDADID + " INTEGER," +
                    CentrosModalidadesTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    CentrosModalidadesTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_CentrosModalidadesTable =
            "DROP TABLE IF EXISTS " + CentrosTable.TABLE_NAME;



    public static class DetallesAsistenciasTable implements BaseColumns{
        public static final String TABLE_NAME="detallesasistencias";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_ALUMNO_ID ="alumno_id";
        public static final String COLUMN_NAME_ENCABEZADOASISTENCIA_ID ="encabezadoasistencia_id";
        public static final String COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID="encabezadoasistencia_movil_id";
        public static final String COLUMN_NAME_ESTADO="estado";
        public static final String COLUMN_NAME_EXCUSA="excusa";
        public static final String COLUMN_NAME_MOVILID="movil_id";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";
        public static final String COLUMN_NAME_SINCRONIZACION_SERVER ="sincronizar_servidor";
    }
    private static final String SQL_CREATE_DetallesAsistenciasTable =
            "CREATE TABLE " + DetallesAsistenciasTable.TABLE_NAME + " (" +
                    DetallesAsistenciasTable.COLUMN_NAME_MOVILID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DetallesAsistenciasTable.COLUMN_NAME_ID + " INTEGER," +
                    DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID + " INTEGER," +
                    DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID + " INTEGER," +
                    DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID + " INTEGER," +
                    DetallesAsistenciasTable.COLUMN_NAME_ESTADO + " INTEGER," +
                    DetallesAsistenciasTable.COLUMN_NAME_EXCUSA + " INTEGER," +
                    DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER + " INTEGER,"+
                    DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_DetallesAsistenciasTable =
            "DROP TABLE IF EXISTS " + DetallesAsistenciasTable.TABLE_NAME;





    public static class DocentesTable implements BaseColumns{
        public static final String TABLE_NAME="docentes";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_USERNAME="username";
        public static final String COLUMN_NAME_NOMBRE="nombre";
        public static final String COLUMN_NAME_APELLIDO="apellido";
        public static final String COLUMN_NAME_GENERO="genero";
        public static final String COLUMN_NAME_DIRECCION="direccion";
        public static final String COLUMN_NAME_USERSACE="user_sace";
        public static final String COLUMN_NAME_PASSWORDSACE="password_sace";
        public static final String COLUMN_NAME_TELEFONO="telefono";
        public static final String COLUMN_NAME_EMAIL="email";
        public static final String COLUMN_NAME_PASSWORD="password";
        public static final String COLUMN_NAME_SINCRONIZACIONSERVER="sincronizar_servidor";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";
        public static final String COLUMN_NAME_REMEMBERTOKEN="remember_token";

    }
    private static final String SQL_CREATE_DocentesTable =
            "CREATE TABLE " + DocentesTable.TABLE_NAME + " (" +
                    DocentesTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    DocentesTable.COLUMN_NAME_USERNAME + " TEXT," +
                    DocentesTable.COLUMN_NAME_NOMBRE + " TEXT," +
                    DocentesTable.COLUMN_NAME_APELLIDO + " TEXT," +
                    DocentesTable.COLUMN_NAME_GENERO + " INTEGER," +
                    DocentesTable.COLUMN_NAME_DIRECCION + " TEXT," +
                    DocentesTable.COLUMN_NAME_USERSACE + " TEXT," +
                    DocentesTable.COLUMN_NAME_PASSWORDSACE + " TEXT," +
                    DocentesTable.COLUMN_NAME_TELEFONO + " TEXT," +
                    DocentesTable.COLUMN_NAME_EMAIL + " TEXT," +
                    DocentesTable.COLUMN_NAME_PASSWORD + " TEXT,"+
                    DocentesTable.COLUMN_NAME_SINCRONIZACIONSERVER + " INTEGER," +
                    DocentesTable.COLUMN_NAME_REMEMBERTOKEN + " TEXT," +
                    DocentesTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    DocentesTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_DocentesTable =
            "DROP TABLE IF EXISTS " + DocentesTable.TABLE_NAME;






    public static class EncabezadoAsistenciasTable implements BaseColumns{
        public static final String TABLE_NAME="encabezadoasistencias";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_SECCIONID="seccion_id";
        public static final String COLUMN_NAME_ASIGNATURAID="asignatura_id";
        public static final String COLUMN_NAME_FECHA="fecha";
        public static final String COLUMN_NAME_MOVILID="movil_id";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";
        public static final String COLUMN_NAME_SINCRONIZACIONSERVER="sincronizar_servidor";

    }
    private static final String SQL_CREATE_EncabezadoAsistenciasTable =
            "CREATE TABLE " + EncabezadoAsistenciasTable.TABLE_NAME + " (" +
                    EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EncabezadoAsistenciasTable.COLUMN_NAME_ID + " INTEGER," +
                    EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID + " INTEGER," +
                    EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID + " INTEGER," +
                    EncabezadoAsistenciasTable.COLUMN_NAME_FECHA + " TEXT," +
                    EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER + " INTEGER," +
                    EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_EncabezadoAsistenciasTable =
            "DROP TABLE IF EXISTS " + EncabezadoAsistenciasTable.TABLE_NAME;






    public static class ImagesTable implements BaseColumns{
        public static final String TABLE_NAME="images";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_DESCRIPCION="description";
        public static final String COLUMN_NAME_THUMBNAIL="thumbnail";
        public static final String COLUMN_NAME_IMAGELINK="imageLink";
        public static final String COLUMN_NAME_USERID="user_id";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";

    }
    private static final String SQL_CREATE_ImagesTable =
            "CREATE TABLE " + ImagesTable.TABLE_NAME + " (" +
                    ImagesTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    ImagesTable.COLUMN_NAME_TITLE + " TEXT," +
                    ImagesTable.COLUMN_NAME_DESCRIPCION + " TEXT," +
                    ImagesTable.COLUMN_NAME_THUMBNAIL + " TEXT," +
                    ImagesTable.COLUMN_NAME_IMAGELINK + " INTEGER," +
                    ImagesTable.COLUMN_NAME_USERID + " TEXT," +
                    ImagesTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    ImagesTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_ImagesTable =
            "DROP TABLE IF EXISTS " + ImagesTable.TABLE_NAME;






    public static class MatriculasTable implements BaseColumns{
        public static final String TABLE_NAME="matriculas";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_ALUMNOID="alumno_id";
        public static final String COLUMN_NAME_SECCIONID="seccion_id";
        public static final String COLUMN_NAME_YEAR="year";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";


    }
    private static final String SQL_CREATE_MatriculasTable =
            "CREATE TABLE " + MatriculasTable.TABLE_NAME + " (" +
                    MatriculasTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    MatriculasTable.COLUMN_NAME_ALUMNOID + " INTEGER," +
                    MatriculasTable.COLUMN_NAME_SECCIONID + " INTEGER," +
                    MatriculasTable.COLUMN_NAME_YEAR + " INTEGER," +
                    MatriculasTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    MatriculasTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_MatriculasTable =
            "DROP TABLE IF EXISTS " + MatriculasTable.TABLE_NAME;




    public static class ModalidadesTable implements BaseColumns{
        public static final String TABLE_NAME="modalidades";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_ALIAS="alias";
        public static final String COLUMN_NAME_NOMBRE="nombre";
        public static final String COLUMN_NAME_OBSERVACIONES="observaciones";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";

    }
    private static final String SQL_CREATE_ModalidadesTable =
            "CREATE TABLE " + ModalidadesTable.TABLE_NAME + " (" +
                    ModalidadesTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    ModalidadesTable.COLUMN_NAME_ALIAS + " TEXT," +
                    ModalidadesTable.COLUMN_NAME_NOMBRE + " TEXT," +
                    ModalidadesTable.COLUMN_NAME_OBSERVACIONES + " TEXT," +
                    ModalidadesTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    ModalidadesTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_ModalidadesTable =
            "DROP TABLE IF EXISTS " + ModalidadesTable.TABLE_NAME;






    public static class NotaAcumulativosTable implements BaseColumns{
        public static final String TABLE_NAME="notaacumulativos";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_ALUMNO_ID ="alumno_id";
        public static final String COLUMN_NAME_ACUMULATIVO_ID ="acumulativo_id";
        public static final String COLUMN_NAME_ACUMULATIVO_MOVIL_ID ="acumulativo_movil_id";
        public static final String COLUMN_NAME_NOTA="nota";
        public static final String COLUMN_NAME_SINCRONIZACION_SERVER ="sincronizar_servidor";
        public static final String COLUMN_NAME_MOVIL_ID ="movil_id";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";

    }
    private static final String SQL_CREATE_NotaAcumulativosTable =
            "CREATE TABLE " + NotaAcumulativosTable.TABLE_NAME + " (" +
                    NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NotaAcumulativosTable.COLUMN_NAME_ID + " INTEGER," +
                    NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID + " INTEGER," +
                    NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID + " INTEGER," +
                    NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID + " INTEGER," +
                    NotaAcumulativosTable.COLUMN_NAME_NOTA + " REAL," +
                    NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER + " INTEGER," +
                    NotaAcumulativosTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_NotaAcumulativosTable =
            "DROP TABLE IF EXISTS " + NotaAcumulativosTable.TABLE_NAME;




    public static class PeriodosTable implements BaseColumns{
        public static final String TABLE_NAME="periodos";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_FECHAINICIA="fecha_inicio";
        public static final String COLUMN_NAME_FECHAFINAL="fecha_final";
        public static final String COLUMN_NAME_ESTADO="estado";
        public static final String COLUMN_NAME_OBSERVACIONES="observaciones";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";

    }
    private static final String SQL_CREATE_PeriodosTable =
            "CREATE TABLE " + PeriodosTable.TABLE_NAME + " (" +
                    PeriodosTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    PeriodosTable.COLUMN_NAME_FECHAINICIA + " TEXT," +
                    PeriodosTable.COLUMN_NAME_FECHAFINAL + " TEXT," +
                    PeriodosTable.COLUMN_NAME_ESTADO + " INTEGER," +
                    PeriodosTable.COLUMN_NAME_OBSERVACIONES + " REAL," +
                    PeriodosTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    PeriodosTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_PeriodosTable =
            "DROP TABLE IF EXISTS " + PeriodosTable.TABLE_NAME;




    public static class SeccionsTable implements BaseColumns{
        public static final String TABLE_NAME="seccions";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_MODALIDADID="modalidad_id";
        public static final String COLUMN_NAME_CURSO="curso";
        public static final String COLUMN_NAME_SECCION="seccion";
        public static final String COLUMN_NAME_JORNADA="jornada";
        public static final String COLUMN_NAME_CENTROID="centro_id";
        public static final String COLUMN_NAME_PERIODOID="periodo_id";
        public static final String COLUMN_NAME_SINCRONIZACIONSERVER="sincronizar_servidor";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";


    }
    private static final String SQL_CREATE_SeccionsTable =
            "CREATE TABLE " + SeccionsTable.TABLE_NAME + " (" +
                    SeccionsTable.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    SeccionsTable.COLUMN_NAME_MODALIDADID + " INTEGER," +
                    SeccionsTable.COLUMN_NAME_CURSO + " TEXT," +
                    SeccionsTable.COLUMN_NAME_SECCION + " TEXT," +
                    SeccionsTable.COLUMN_NAME_JORNADA + " TEXT," +
                    SeccionsTable.COLUMN_NAME_CENTROID + " INTEGER," +
                    SeccionsTable.COLUMN_NAME_PERIODOID + " INTEGER," +
                    SeccionsTable.COLUMN_NAME_SINCRONIZACIONSERVER + " INTEGER," +
                    SeccionsTable.COLUMN_NAME_CREATEDAT + " TEXT," +
                    SeccionsTable.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_SeccionsTable =
            "DROP TABLE IF EXISTS " + SeccionsTable.TABLE_NAME;




    public static class TipoAcumulativos implements BaseColumns{
        public static final String TABLE_NAME="tipoacumulativos";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_DESCRIPCION="descripcion";
        public static final String COLUMN_NAME_CREATEDAT="created_at";
        public static final String COLUMN_NAME_UPDATEDAT="updated_at";


    }
    private static final String SQL_CREATE_TipoAcumulativos =
            "CREATE TABLE " + TipoAcumulativos.TABLE_NAME + " (" +
                    TipoAcumulativos.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TipoAcumulativos.COLUMN_NAME_DESCRIPCION + " TEXT," +
                    TipoAcumulativos.COLUMN_NAME_CREATEDAT + " TEXT," +
                    TipoAcumulativos.COLUMN_NAME_UPDATEDAT + " TEXT)";

    private static final String SQL_DELETE_TipoAcumulativos =
            "DROP TABLE IF EXISTS " + TipoAcumulativos.TABLE_NAME;




}
