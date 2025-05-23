package com.cagongu.tourbe.processor;

import com.cagongu.tourbe.configuration.Environment;
import com.cagongu.tourbe.configuration.PartnerInfo;
import com.cagongu.tourbe.shared.exception.MoMoException;
import com.cagongu.tourbe.shared.utils.Execute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */

public abstract class AbstractProcess<T, V> {

    protected PartnerInfo partnerInfo;
    protected Environment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(Environment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public abstract V execute(T request) throws MoMoException;
}
