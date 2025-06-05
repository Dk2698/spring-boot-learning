package com.kumar.springbootlearning.mvc.error;

import com.kumar.springbootlearning.mvc.exception.*;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String ERRORS_KEY = "errors";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BindingResult result = ex.getBindingResult();

        List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream().map(f -> new FieldErrorVM(f.getObjectName()
                        .replaceFirst("DTO$", ""), f.getField(),
                        StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode(), f.getCode()))
                .collect(Collectors.toList());
        ex.getBody().setProperty(FIELD_ERRORS_KEY, fieldErrors);
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public final ResponseEntity<Object> handleException(ConstraintViolationException ex, WebRequest request) throws Exception {
//        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
//        List<FieldErrorVM> fieldErrors = ex.getConstraintViolations().stream()
//                .map((f) -> {
//                    final String bean = f.getLeafBean().getClass().getSimpleName().replaceFirst("DTO$", "");
//                    String property = f.getPropertyPath().toString();
//                    final String[] propertyPaths = property.split("\\.");
//                    if (propertyPaths.length > 2) {
//                        property = Arrays.stream(propertyPaths).skip(2).collect(Collectors.joining("."));
//                    }
//                    f.getMessage();
//                    String messageTemplate = f.getMessageTemplate();
//                    if (messageTemplate.startsWith("{jakarta.validation.constraints")) {
//                        //{jakarta.validation.constraints.NotBlank.message}
//                        messageTemplate = "error." + messageTemplate.substring(32, messageTemplate.length() - 9);
//                    }
//                    return new FieldErrorVM(bean, property, f.getMessage(), messageTemplate);
//                })
//                .collect(Collectors.toList());
//        problemDetail.setProperty(FIELD_ERRORS_KEY, fieldErrors);
//        final Map<String, FieldErrorVM> collect = fieldErrors.stream().collect(Collectors.toMap(FieldErrorVM::getField, fieldErrorVM -> fieldErrorVM));
//        problemDetail.setProperty(ERRORS_KEY, collect);
//
//        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }

    @ExceptionHandler(ImmutablePropertyException.class)
    public final ResponseEntity<Object> handleException(ImmutablePropertyException ex, WebRequest request) throws Exception {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        FieldErrorVM fieldError = new FieldErrorVM(ex.getEntityName(), ex.getFieldName(), ex.getMessage(), ex.getErrorKey());
        problemDetail.setProperty(FIELD_ERRORS_KEY, List.of(fieldError));
        problemDetail.setProperty(ERRORS_KEY, Map.of(fieldError.getField(), fieldError));

        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UniqueConstraintException.class)
    public final ResponseEntity<Object> handleException(UniqueConstraintException ex, WebRequest request) throws Exception {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        FieldErrorVM fieldError = new FieldErrorVM(ex.getEntityName(), ex.getFieldName(), ex.getMessage(), ex.getErrorKey());
        problemDetail.setProperty(FIELD_ERRORS_KEY, List.of(fieldError));
        problemDetail.setProperty(ERRORS_KEY, Map.of(fieldError.getField(), fieldError));
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ReferentialConstraintException.class)
    public final ResponseEntity<Object> handleException(ReferentialConstraintException ex, WebRequest request) throws Exception {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        FieldErrorVM fieldError = new FieldErrorVM(ex.getEntityName(), ex.getFieldName(), ex.getMessage(), ex.getErrorKey());
        problemDetail.setProperty(FIELD_ERRORS_KEY, List.of(fieldError));
        problemDetail.setProperty(ERRORS_KEY, Map.of(fieldError.getField(), fieldError));
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleException(ValidationException ex, WebRequest request) throws Exception {
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        FieldErrorVM fieldError = new FieldErrorVM("", ex.getAttributeName(), ex.getMessage(), ex.getErrorKey());
        problemDetail.setProperty(FIELD_ERRORS_KEY, List.of(fieldError));
        problemDetail.setProperty(ERRORS_KEY, Map.of(fieldError.getField(), fieldError));
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    //    @ExceptionHandler(MethodArgumentNotValidException.class)
    //    ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    //        BindingResult result = e.getBindingResult();
    //        List<FieldErrorVM> fieldErrors = result
    //                .getFieldErrors()
    //                .stream()
    //                .map(f ->
    //                        new FieldErrorVM(
    //                                f.getObjectName().replaceFirst("DTO$", ""),
    //                                f.getField(),
    //                                StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()
    //                        )
    //                    )
    //                .collect(Collectors.toList());
    //        return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage())
    //                .title("Bookmark not found")
    //                .type(URI.create("https://api.xyz.com/errors/validations"))
    //                .property("errorCategory", "Validation")
    //                .property("timestamp", Instant.now())
    //                .property("errors", fieldErrors)
    //                .build();
    //    }
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("timestamp", ex.getTimestamp());
//        errorDetails.put("message", ex.getMessage());
//        errorDetails.put("errorCode", ex.getErrorCode());
//        errorDetails.put("details", ex.getDetails());
//        errorDetails.put("status", HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                ex.getTimestamp(),
//                ex.getMessage(),
//                ex.getErrorCode(),
//                ex.getDetails(),
//                HttpStatus.NOT_FOUND.value(),
//                request.getRequestURI()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(InvalidRequestException.class)
//    public ResponseEntity<?> handleInvalidRequest(InvalidRequestException ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("timestamp", LocalDateTime.now());
//        errorDetails.put("message", ex.getMessage());
//        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGenericException(Exception ex) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("timestamp", LocalDateTime.now());
//        errorDetails.put("message", "Internal Server Error");
//        errorDetails.put("details", ex.getMessage());
//        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/errors/not-found"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errorCode", "RESOURCE_NOT_FOUND");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ProblemDetail handleInvalidRequest(InvalidRequestException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid Request");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/errors/invalid-request"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errorCode", "INVALID_REQUEST");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/errors/internal"));
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errorCode", "INTERNAL_ERROR");
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//        problemDetail.setTitle("Validation Failed");
//        problemDetail.setDetail("One or more fields have invalid values.");
//        problemDetail.setType(URI.create("https://example.com/errors/validation"));
//        problemDetail.setInstance(URI.create(request.getRequestURI()));
//        problemDetail.setProperty("errorCode", "VALIDATION_ERROR");
//        problemDetail.setProperty("timestamp", LocalDateTime.now());
//
//        // Collect field errors
//        var fieldErrors = ex.getBindingResult().getFieldErrors()
//                .stream()
//                .collect(Collectors.toMap(
//                        error -> error.getField(),
//                        error -> error.getDefaultMessage(),
//                        (existing, replacement) -> existing // merge function if duplicate fields
//                ));
//
//        problemDetail.setProperty("fieldErrors", fieldErrors);
//        return problemDetail;
//    }
}