package com.paringer.medisafe.model.rest;

import com.paringer.medisafe.model.rest.data.HttpError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static HttpError parseError(Response<?> response) {
        Converter<ResponseBody, HttpError> converter =
                ServiceGenerator.retrofit
                        .responseBodyConverter(HttpError.class, new Annotation[0]);

        HttpError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new HttpError();
        }

        return error;
    }

}