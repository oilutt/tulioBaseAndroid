package oilutt.baseproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.TypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JsonParser {
    private static final Map<Class, JsonDeserializer> sTypeAdapterMap = new HashMap<>();

    private JsonParser() {
        super();
    }

    public static <T> void registerTypeAdapter(Class<T> clazz, JsonDeserializer<T> jsonDeserializationContext) {
        sTypeAdapterMap.put(clazz, jsonDeserializationContext);
    }

    public static void unregisterTypeAdapter(Class clazz) {
        sTypeAdapterMap.remove(clazz);
    }

    /**
     * Serializa qualquer objeto para uma String Json.
     *
     * @param source Objeto que será serializado
     * @return Json em String.
     */
    public static String toJson(Object source) {
        return toJson(source, false);
    }

    /**
     * Serializa qualquer objeto para uma String Json.
     *
     * @param source Objeto que será serializado
     * @return Json em String.
     */
    public static String toJsonExcludeFields(Object source) {
        return toJson(source, true);
    }

    /**
     * Serializa qualquer objeto para uma String Json.
     *
     * @param source
     * @param excluedFields Configura o Gson para excluor todos os campos que possuam a annotation
     *                      {@link com.google.gson.annotations.Expose} de sua Serialização ou Deserialização.
     * @return
     */
    private static String toJson(Object source, boolean excluedFields) {
        final GsonBuilder builder = new GsonBuilder();

        if (excluedFields) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }

        if (!sTypeAdapterMap.isEmpty()) {
            for (Map.Entry<Class, JsonDeserializer> entry : sTypeAdapterMap.entrySet()) {
                builder.registerTypeAdapter(entry.getKey(), entry.getValue());
            }
        }

        final Gson gson = builder.serializeNulls().create();
        return gson.toJson(source);
    }

    /**
     * Deserializa um json, passado via parâmetro, para um objeto.
     *
     * @param json                 String Json
     * @param type                 Tipo do objeto de destino
     * @param excluedFields        Configura o Gson para excluor todos os campos que possuam a annotation
     *                             {@link com.google.gson.annotations.Expose} de sua Serialização ou Deserialização.
     * @param typeAdapterFactories Um @{code TypeAdapterFactory} que define como o objeto será deserializado.
     * @param <T>                  Tipo de Retorno do Objeto
     * @return Objeto deserializado.
     */
    public static <T> T toObject(String json, Type type, boolean excluedFields,
                                 TypeAdapterFactory... typeAdapterFactories) {
        final GsonBuilder builder = new GsonBuilder();

        for (TypeAdapterFactory typeAdapterFactory : typeAdapterFactories) {
            builder.registerTypeAdapterFactory(typeAdapterFactory);
        }

        if (excluedFields) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }

        if (!sTypeAdapterMap.isEmpty()) {
            for (Map.Entry<Class, JsonDeserializer> entry : sTypeAdapterMap.entrySet()) {
                builder.registerTypeAdapter(entry.getKey(), entry.getValue());
            }
        }

        final Gson gson = builder.serializeNulls().create();
        return gson.fromJson(json, type);
    }

    /**
     * Deserializa um json, passado via parâmetro, para um objeto.
     *
     * @param json                 String Json
     * @param type                 Tipo do objeto de destino
     * @param typeAdapterFactories Um @{code TypeAdapterFactory} que define como o objeto será deserializado.
     * @param <T>                  Tipo de Retorno do Objeto
     * @return Objeto deserializado.
     */
    public static <T> T toObject(String json, Type type, TypeAdapterFactory... typeAdapterFactories) {
        return toObject(json, type, false, typeAdapterFactories);
    }

    /**
     * Deserializa um json, passado via parâmetro, para um objeto.
     *
     * @param json String Json
     * @param type Classe do objeto de destino
     * @param <T>  Tipo de Retorno do Objeto
     * @return Objeto deserializado.
     */
    public static <T> T toObject(String json, Class<T> type) {
        return toObject(json, type, false, new TypeAdapterFactory[0]);
    }

    /**
     * Deserializa um json, passado via parâmetro, para uma Lista de Objetos.
     *
     * @param json String Json
     * @param type Tipo do objeto de destino
     * @param <T>  Tipo dos Objetos da Lista.
     * @return Lista de objetos.
     */
    public static <T> List<T> toList(String json, Type type) {
        return toObject(json, type, new TypeAdapterFactory[0]);
    }

    /**
     * Deserializa um json, passado via parâmetro, para um Objeto, ignorando os campos que possuirem a annotation
     * {@link com.google.gson.annotations.Expose} em sua definição.
     *
     * @param json String Json
     * @param type Classe do objeto de destino
     * @param <T>  Tipo de Retorno do Objeto
     * @return Objeto deserializado.
     */
    public static <T> T toObjectExcludeFields(String json, Class<T> type) {
        return toObject(json, type, true, new TypeAdapterFactory[0]);
    }

    /**
     * Deserializa um json, passado via parâmetro, para uma Lista de Objeto, ignorando os campos que possuirem a
     * annotation {@link com.google.gson.annotations.Expose} em sua definição.
     *
     * @param json String Json
     * @param type Tipo do objeto de destino
     * @param <T>  Tipo dos Objetos da Lista.
     * @return Lista de objetos.
     */
    public static <T> List<T> toListExcludeFields(String json, Type type) {
        return toObject(json, type, true, new TypeAdapterFactory[0]);
    }
}