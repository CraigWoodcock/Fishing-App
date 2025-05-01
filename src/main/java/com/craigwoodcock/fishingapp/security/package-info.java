/**
 * Security package containing security implementations for the Fishing Application.
 *
 * <p>This package provides security-related classes and implementations:
 * <ul>
 *   <li>Custom authentication handlers</li>
 *   <li>Security filters</li>
 *   <li>Access control implementations</li>
 * </ul>
 *
 * <p>Key components:
 * <ul>
 *   <li>CustomAuthenticationEntryPoint - Handles unauthenticated requests</li>
 *   <li>CustomAccessDeniedHandler - Handles unauthorized access attempts</li>
 *   <li>JwtAuthenticationFilter - JWT token validation filter</li>
 * </ul>
 *
 * <p>The security package works in conjunction with the config package's SecurityConfig
 * to provide complete application security.</p>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.security;