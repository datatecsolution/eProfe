package net.profeinformatica.eprofe.modeloDao.apiWeb;



import android.content.Intent;

import net.profeinformatica.eprofe.modelo.Acumulativo;
import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.DetalleAsistencia;
import net.profeinformatica.eprofe.modelo.Docente;
import net.profeinformatica.eprofe.modelo.EncabezadoAsistencia;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modelo.Periodo;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modelo.TipoAcumulativo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface ApiService {

    @PUT("alumnos/{id}")
    Observable<Alumno> updateAlumno(@Path("id") Integer id, @Query("nombre") String nombre, @Query("apellido") String apellido, @Query("genero") Integer genero,@Query("telefono") String telefono,@Query("email") String email);


    @GET("tipoacumlativos")
    Observable<List<TipoAcumulativo>> getTipoAcumulativos();

    @GET("periodos")
    Observable<List<Periodo>> getPeriodos();

    @DELETE("asistencias/{id}")
    Observable<EncabezadoAsistencia> deleteAsistencia(@Path("id") Integer id);

    @Headers("Content-Type: application/json")
    @PUT("asistencias/{id}")
    Observable<EncabezadoAsistencia> updateAsistencia(@Path("id") Integer id, @Body EncabezadoAsistencia encabezadoAsistencia);

    @PUT("detallesasistencia/{id}")
    Observable<DetalleAsistencia> updateDetalleAsistencia(@Path("id") Integer id, @Query("alumno_id") Integer idAlumno, @Query("encabezadoasistencia_id") Integer idEncabezado, @Query("estado") Integer estado);

    @GET("asistencias/{id}")
    Observable<EncabezadoAsistencia> getAsistencia(@Path("id") Integer id);

    @GET("asistencias/docente")
    Observable<List<EncabezadoAsistencia>> getAsistencias(@Query("docente_id") Integer docenteId, @Query("seccion_id") Integer seccionId);

    @GET("secciones/docente")
    Observable<List<Seccion>> getSecciones(@Query("docente_id") Integer docenteId);

    @GET("usersace/sicronizar")
    Observable<List<Seccion>> sicronizarSace(@Query("id") Integer docenteId);

    @GET("secciones/{id}")
    Observable<Seccion> getSeccion(@Path("id") Integer id);

    @Headers("Content-Type: application/json")
    @PUT("secciones/update_periodo")
    Observable<Seccion> updateSecciones( @Body List<Seccion> secciones);


    @GET("asignaturas/buscar_asignaturas_seccion")
    Observable<List<Asignatura>> getAsignaturas(@Query("seccion_id") Integer seccion_id);

    @GET("asignaturas/docente")
    Observable<List<Asignatura>> getAsignaturasDocente(@Query("docente_id") Integer docenteId,@Query("seccion_id") Integer seccionId);

    @POST("docentes")
    Observable<Docente> saveDocente(@Query("nombre") String nombre,@Query("apellido") String apellido,
                                    @Query("genero") Integer genero,@Query("direccion") String direccion,
                                    @Query("user_sace") String user_sace, @Query("password_sace") String password_sace,
                                    @Query("email") String email, @Query("telefono") String telefono);

    @GET("matriculas/buscar_seccion_year")
    Observable<List<Matricula>> getAlumnosSeccion(@Query("year") String year, @Query("seccion_id") Integer seccion_id);

    @Headers("Content-Type: application/json")
    @POST("asistencias")
    Observable<EncabezadoAsistencia> saveEncabezadoAsistencia(@Body EncabezadoAsistencia encabezadoAsistencia);

    @POST("detallesasistencia")
    Observable<DetalleAsistencia> saveDetalleAsistencia(@Query("alumno_id") Integer idAlumno, @Query("encabezadoasistencia_id") Integer idEncabezado, @Query("estado") Integer estado);

    @Headers("Content-Type: application/json")
    @POST("acumulativos")
    Observable<Acumulativo> saveAcumulativo(@Body Acumulativo acumulativo);

    @POST("notaacumulativos")
    Observable<NotaAcumulativo> saveNotaAcumulativo(@Query("alumno_id") Integer idAlumno, @Query("acumulativo_id") Integer idAcumulativo, @Query("nota") Double nota);

    @GET("notaacumulativos/buscar_asignatura")
    Observable<List<NotaAcumulativo>> getNotaAsignaturaAlumno(@Query("asignatura_id") Integer idAsignatura, @Query("parcial") String parcial, @Query("seccion_id") Integer idSeccion, @Query("alumno_id") Integer idAlumno);

    @GET("acumulativos/docente")
    Observable<List<Acumulativo>> getAcumulativos(@Query("docente_id") Integer idDocente);

    @GET("acumulativos/seccion_parcial")
    Observable<List<Acumulativo>> getAcumulativosSeccionParcial(@Query("docente_id") Integer idDocente, @Query("seccion_id") Integer idSeccion, @Query("parcial") String parcial, @Query("asignatura_id") Integer idAsignatura);

    @GET("acumulativos/docente_seccion")
    Observable<List<Acumulativo>> getAcumulativosDocenteSeccion(@Query("docente_id") Integer idDocente, @Query("seccion_id") Integer idSeccion);

    @DELETE("acumulativos/{id}")
    Observable<Acumulativo> deleteAcumulativo(@Path("id") Integer id);

    @GET("acumulativos/{id}")
    Observable<Acumulativo> getAcumulativo(@Path("id") Integer id);

    @Headers("Content-Type: application/json")
    @PUT("acumulativos/{id}")
    Observable<Acumulativo> updateAcumulativo(@Path("id") Integer id, @Body Acumulativo acumulativo);

    @PUT("notaacumulativos/{id}")
    Observable<NotaAcumulativo> updateNotaAcumulativo(@Path("id") Integer id, @Query("alumno_id") Integer idAlumno, @Query("acumulativo_id") Integer idAcumulativo, @Query("nota") double nota);

    @Headers("Content-Type: application/json")
    @PUT("notaacumulativos/actualizar_notas")
    Observable<NotaAcumulativo> updateNotaAcumulativos(@Body Acumulativo acumulativo);

}
