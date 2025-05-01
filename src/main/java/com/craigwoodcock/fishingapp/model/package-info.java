/**
 * Model package containing all data models, DTOs, and entities for the Fishing Application.
 *
 * <p>This package is organized into three subpackages:
 * <ul>
 *   <li>dto - Data Transfer Objects for API requests/responses</li>
 *   <li>entity - JPA entities representing database tables</li>
 *   <li>id - Composite key classes for entities with multiple primary keys</li>
 * </ul>
 *
 * <p>Key entities include:
 * <ul>
 *   <li>User - Application user information</li>
 *   <li>Session - Fishing session details</li>
 *   <li>Angler - Angler profile data</li>
 *   <li>Catch - Fish catch records</li>
 *   <li>AnglerSession - Association between anglers and sessions</li>
 * </ul>
 *
 * @author Craig Woodcock
 * @version 1.0
 */
package com.craigwoodcock.fishingapp.model;