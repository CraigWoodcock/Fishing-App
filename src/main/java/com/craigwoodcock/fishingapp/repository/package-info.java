/**
 * Repository package containing data access interfaces for the Fishing Application.
 *
 * <p>This package provides Spring Data JPA repositories for database operations.
 * Each repository interface extends JpaRepository to provide standard CRUD operations
 * and custom query methods.</p>
 *
 * <p>Available repositories:
 * <ul>
 *   <li>UserRepository - User account management</li>
 *   <li>SessionRepository - Fishing session data access</li>
 *   <li>AnglerRepository - Angler profile management</li>
 *   <li>AnglerSessionRepository - Managing angler-session relationships</li>
 *   <li>CatchRepository - Fish catch record access</li>
 *   <li>JwtTokenRepository - JWT token storage and retrieval</li>
 * </ul>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.repository;