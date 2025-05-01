/**
 * Entity package containing JPA entities for the Fishing Application.
 *
 * <p>This package contains entity classes that:
 * <ul>
 *   <li>Map to database tables using JPA annotations</li>
 *   <li>Define relationships between data models</li>
 *   <li>Implement business model validations</li>
 * </ul>
 *
 * <p>Key entities:
 * <ul>
 *   <li>User - User account and authentication data</li>
 *   <li>Session - Fishing session details and metadata</li>
 *   <li>Angler - Individual angler information</li>
 *   <li>AnglerSession - Many-to-many relationship between anglers and sessions</li>
 *   <li>Catch - Fish catch records and details</li>
 *   <li>JwtToken - JWT token storage for authentication</li>
 * </ul>
 *
 * <p>Entity relationships:
 * <ul>
 *   <li>User -> Sessions: One-to-Many</li>
 *   <li>Session <-> Anglers: Many-to-Many (via AnglerSession)</li>
 *   <li>Session -> Catches: One-to-Many</li>
 * </ul>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.model.entity;