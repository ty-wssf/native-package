package com.hj.rminf.app;

import io.nop.api.core.beans.ApiRequest;
import io.nop.api.core.beans.ApiResponse;
import io.nop.api.core.beans.WebContentBean;
import io.nop.api.core.context.ContextProvider;
import io.nop.api.core.util.FutureHelper;
import io.nop.commons.util.StringHelper;
import io.nop.core.exceptions.ErrorMessageManager;
import io.nop.core.resource.IResource;
import io.nop.file.core.*;
import io.nop.graphql.core.utils.GraphQLResponseHelper;
import io.nop.http.api.HttpApiConstants;
import io.nop.solon.service.SolonWebHelper;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import org.noear.solon.annotation.*;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.UploadedFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * @author wyl
 * @date 2024年08月04日 19:12
 */
@Controller
public class SolonFileService extends AbstractGraphQLFileService {

    protected Map<String, Object> getHeaders() {
        Map<String, Object> headers = new HashMap();
        Context.current().headersMap().forEach((name, list) -> {
            headers.put(name, list.get(0));
        });
        return headers;
    }

    @Mapping(FileConstants.PATH_UPLOAD)
    @Post
    public ApiResponse<?> upload(Context context) {
        String locale = ContextProvider.currentLocale();

        CompletionStage<ApiResponse<?>> res;
        try {
            UploadedFile uploadedFile = context.file("file");
            InputStream is = uploadedFile.getContent();
            String fileName = StringHelper.fileFullName(uploadedFile.getName());

            UploadRequestBean input = buildUploadRequestBean(is,
                    fileName,
                    uploadedFile.getContentSize(),
                    uploadedFile.getContentType(),
                    context::param);

            res = uploadAsync(buildApiRequest(context, input));
        } catch (IOException e) {
            res = FutureHelper.success(ErrorMessageManager.instance().buildResponse(locale, e));
        }
        context.headerSet(HttpApiConstants.HEADER_CONTENT_TYPE, HttpApiConstants.CONTENT_TYPE_JSON);
        return FutureHelper.syncGet(res);
    }

    @Mapping(FileConstants.PATH_DOWNLOAD + "/{fileId}")
    @Get
    public File download(String fileId, String contentType, Context context) {
        // System.out.println(FutureHelper.syncGet(doDownload(fileId, contentType, context)));
        return FutureHelper.syncGet(doDownload(fileId, contentType, context));
    }

    public CompletionStage<File> doDownload(String fileId, String contentType,
                                            Context request) {
        DownloadRequestBean req = buildDownloadRequestBean(fileId, contentType);

        return downloadAsync(buildApiRequest(request, req)).thenApply(res -> {
            Response response = buildFileResponse(res);
            return (File) response.getEntity();
        });
    }

    public static Response buildFileResponse(ApiResponse<WebContentBean> response) {
        return GraphQLResponseHelper.consumeWebContent(response, (invokeHeaderSet, content, status) -> {
            Response.ResponseBuilder builder = Response.status(status);
            invokeHeaderSet.accept(builder::header);

            if (content instanceof IResource) {
                IResource resource = (IResource) content;
                File file = resource.toFile();

                if (file != null) {
                    builder.entity(file);
                } else {
                    builder.entity((StreamingOutput) resource::writeToStream);
                }
            } else if (content instanceof byte[]
                    || content instanceof String
                    || content instanceof File
                    || content instanceof StreamingOutput) {
                builder.entity(content);
            } else if (content != null) {
                builder.entity("INVALID CONTENT TYPE");
            }

            return builder.build();
        });
    }


    protected <T> ApiRequest<T> buildApiRequest(Context req, T data) {
        ApiRequest<T> ret = new ApiRequest<>();
        ret.setData(data);
        req.headersMap().entrySet().stream().forEach(entry -> {
            ret.setHeader(entry.getKey(), entry.getValue());
        });
        return ret;
    }

    protected UploadRequestBean buildUploadRequestBean(
            InputStream is, String fileName, long fileSize, String contentType, Function<String, String> paramGetter
    ) {
        String mimeType = MediaTypeHelper.getMimeType(contentType, StringHelper.fileExt(fileName));

        UploadRequestBean request = new UploadRequestBean(is, fileName, fileSize, mimeType);

        request.setBizObjName(paramGetter.apply(FileConstants.PARAM_BIZ_OBJ_NAME));
        request.setFieldName(paramGetter.apply(FileConstants.PARAM_FIELD_NAME));

        return request;
    }

    protected DownloadRequestBean buildDownloadRequestBean(String fileId, String contentType) {
        DownloadRequestBean request = new DownloadRequestBean();

        request.setFileId(fileId);
        request.setContentType(contentType);

        return request;
    }

    protected String transformSolonResponse(Map<String, Object> headers, String body, int status) {
        Context.current().status(status);
        SolonWebHelper.setResponseHeader(Context.current(), headers);
        return body;
    }

}
