/**
 * Exception package containing custom exceptions and exception handlers for the Fishing Application.
 *
 * <p>This package contains:
 * <ul>
 *   <li>Custom exception classes for application-specific errors</li>
 *   <li>Global exception handlers for both API and web interfaces</li>
 *   <li>Error response models</li>
 * </ul>
 *
 * <p>Key components:
 * <ul>
 *   <li>GlobalApiExceptionHandler - Handles exceptions for REST API endpoints</li>
 *   <li>GlobalWebExceptionHandler - Handles exceptions for web interface</li>
 *   <li>ApiErrorResponse - Standardized API error response structure</li>
 *   <li>Session/User/Auth related custom exceptions</li>
 * </ul>
 *
 * <p>Exception handling is separated between API and web interfaces to provide
 * appropriate error responses for each (JSON for API, error pages for web).</p>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.exception;