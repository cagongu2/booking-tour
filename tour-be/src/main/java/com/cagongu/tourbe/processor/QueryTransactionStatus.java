package com.cagongu.tourbe.processor;


import com.cagongu.tourbe.configuration.Environment;
import com.cagongu.tourbe.enums.Language;
import com.cagongu.tourbe.model.momo.HttpResponse;
import com.cagongu.tourbe.model.momo.QueryStatusTransactionRequest;
import com.cagongu.tourbe.model.momo.QueryStatusTransactionResponse;
import com.cagongu.tourbe.shared.constants.Parameter;
import com.cagongu.tourbe.shared.exception.MoMoException;
import com.cagongu.tourbe.shared.utils.Encoder;
import com.cagongu.tourbe.shared.utils.LogUtils;

public class QueryTransactionStatus extends AbstractProcess<QueryStatusTransactionRequest, QueryStatusTransactionResponse> {
    public QueryTransactionStatus(Environment environment) {
        super(environment);
    }

    public static QueryStatusTransactionResponse process(Environment env, String orderId, String requestId) throws Exception {
        try {
            QueryTransactionStatus m2Processor = new QueryTransactionStatus(env);

            QueryStatusTransactionRequest request = m2Processor.createQueryTransactionRequest(orderId, requestId);
            QueryStatusTransactionResponse queryTransResponse = m2Processor.execute(request);

            return queryTransResponse;
        } catch (Exception exception) {
            LogUtils.error("[QueryTransactionProcess] "+ exception);
        }
        return null;
    }

    @Override
    public QueryStatusTransactionResponse execute(QueryStatusTransactionRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, QueryStatusTransactionRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint().getQueryUrl(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[QueryTransactionResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            QueryStatusTransactionResponse queryStatusTransactionResponse = getGson().fromJson(response.getData(), QueryStatusTransactionResponse.class);
            String responserawData = Parameter.REQUEST_ID + "=" + queryStatusTransactionResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + queryStatusTransactionResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + queryStatusTransactionResponse.getMessage() +
                    "&" + Parameter.RESULT_CODE + "=" + queryStatusTransactionResponse.getResultCode();

            LogUtils.info("[QueryTransactionResponse] rawData: " + responserawData);

            return queryStatusTransactionResponse;

        } catch (Exception exception) {
            LogUtils.error("[QueryTransactionResponse] "+ exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    public QueryStatusTransactionRequest createQueryTransactionRequest(String orderId, String requestId) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[QueryTransactionRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new QueryStatusTransactionRequest(partnerInfo.getPartnerCode(), orderId, requestId, Language.EN, signRequest);
        } catch (Exception e) {
            LogUtils.error("[QueryTransactionRequest] "+ e);
        }

        return null;
    }
}
