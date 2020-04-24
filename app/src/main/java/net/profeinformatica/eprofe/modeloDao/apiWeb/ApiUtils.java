package net.profeinformatica.eprofe.modeloDao.apiWeb;

public class ApiUtils {

   public static final String BASE_URL = "http://192.168.2.100/api/v1/";
   //public static final String BASE_URL = "https://minerva.profeinformatica.net/api/v1/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
