package com.kumar.springbootlearning.pattern.strategy.service;

import com.kumar.springbootlearning.pattern.strategy.FileMeta;
import com.kumar.springbootlearning.pattern.strategy.exception.DownloadServiceException;
import com.nimbusds.jose.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.Objects;

@Slf4j
@Service
public class PublicUrlDownloadService {
    public static final String INVALID_URL = "Invalid URL";
    private final WebClient publicWebClient;

    public PublicUrlDownloadService(@Qualifier(value = "publicWebClient") WebClient publicWebClient) {
        this.publicWebClient = publicWebClient;
    }

    /**
     * Validates whether a given URL is accessible by sending a HEAD request.
     *
     * @param url the URL to be validated
     * @return true if the URL is valid, false otherwise
     * @throws DownloadServiceException if an error occurs while validating the URL
     */
    public boolean validateUrl(String url) throws DownloadServiceException {
        return Boolean.TRUE.equals(publicWebClient
                .head()
                .uri(url)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse == null || !clientResponse.statusCode().is2xxSuccessful()) {
                        log.warn("Validation failed: Non-200 response code for URL {}", url);
                        throw new DownloadServiceException(url, "error.badUrl", INVALID_URL);
                    }
                    return Mono.just(true);
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("Validation failed: Exception occurred while validating URL {}", e.getMessage());
                    throw new DownloadServiceException(url, "error.badUrl", "Unable to access url", e);
                })
                .block());
    }

    /**
     * Downloads a file from a given URL and returns its metadata.
     *
     * @param url the URL of the file to be downloaded
     * @return the metadata of the downloaded file, or null if an error occurs
     * @throws DownloadServiceException if an error occurs while downloading the file
     */
    public FileMeta downloadUrl(String url) throws DownloadServiceException {
        try {
            return publicWebClient.get()
                    .uri(url)
                    .exchangeToMono(clientResponse -> {
                        HttpHeaders headers = clientResponse.headers().asHttpHeaders();
                        return clientResponse.bodyToMono(byte[].class).map(fileContent -> {
                            if (fileContent == null || fileContent.length == 0) {
                                log.error("An unexpected error occurred while downloading the  URL {}", url);
                                throw new DownloadServiceException(url, "error.download.emptyContent", "Missing Content");
                            }
                            final String fileName = getFileName(headers, url);
                            FileMeta fileMeta = new FileMeta();
                            fileMeta.setFileName(fileName);
                            fileMeta.setFileContent(Base64.encode(fileContent).toString());
                            fileMeta.setFileSize((long) fileContent.length);
                            fileMeta.setFileType(guessMimeType(headers, fileContent));
                            return fileMeta;
                        });


//                        byte[] fileContent = clientResponse.bodyToMono(byte[].class).block();
//                        if (fileContent == null || fileContent.length == 0) {
//                            log.error("An unexpected error occurred while downloading the  URL {}", url);
//                            throw new DownloadServiceException(url, "error.download.emptyContent", "Missing Content");
//                        }
//                        final String fileName = getFileName(headers, url);
//                        FileMeta fileMeta = new FileMeta();
//                        fileMeta.setFileName(fileName);
//                        fileMeta.setFileContent(Base64.encode(fileContent).toString());
//                        fileMeta.setFileSize((long) fileContent.length);
//                        fileMeta.setFileType(guessMimeType(fileContent));
//                        return Mono.just(fileMeta);
                    })
                    .onErrorResume(Exception.class, e -> {
                        log.error("URL download failed with Exception: {}", e.getMessage());
                        throw new DownloadServiceException(url, "error.download.invalidURL", INVALID_URL, e);
                    })
                    .block();
        } catch (Exception e) {
            String errorMessage = String.format("An unexpected error occurred while downloading the URL %s: %s", url, e.getMessage());
            log.error(errorMessage);
            throw new DownloadServiceException(url, "error.download.invalidURL", INVALID_URL, e);
        }
    }

    private String getFileName(HttpHeaders headers, String url) {
        String fileName = headers.getContentDisposition().getFilename();
        if (fileName == null) {
            URI uri = URI.create(url);
            final String uriPath = uri.getPath();
            fileName = uriPath.substring(uriPath.lastIndexOf('/') + 1);
        }
        return fileName;
    }

    private String guessMimeType(HttpHeaders headers, byte[] fileContent) {
        try {
            String contentType = Objects.requireNonNull(headers.getContentType()).getType();
            if (!StringUtils.hasText(contentType)) {
                InputStream inputStream = new ByteArrayInputStream(fileContent);
                return URLConnection.guessContentTypeFromStream(inputStream);
            }
            return contentType;
        } catch (IOException e) {
            log.warn("Unable to guess mime type due to {}, returning null", e.getMessage());
            return null;
        }
    }

}